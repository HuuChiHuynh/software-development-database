package customer.invoice.system;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author C00261172
 */
public class Account {

    private String username;
    private String email;
    // password

    private int accountId = 0;
    private String sessionId = "";

    private static Account instance = null;

    public int getAccountId() {
        return accountId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public static Account getInstance() {
        if (instance == null) {
            instance = new Account();
        }
        return instance;
    }

    private Account() {

    }

    public Packet login(String username, String password) {
        Packet accountIdPacket = getAccountId(username);
        if (accountIdPacket.getResult() != PacketResult.SUCCESS) {
            return accountIdPacket;
        }
        accountId = (int) accountIdPacket.getInformation();
        Packet sessionIdPacket = requestLogin(username, password);
        if (sessionIdPacket.getResult() == PacketResult.SUCCESS) {
            sessionId = (String) sessionIdPacket.getInformation();
            this.username = username;
            Packet detailsPacket = requestDetails(accountId, sessionId);
            if (detailsPacket.getResult() == PacketResult.SUCCESS) {
                List<Object> details = ((List<List<Object>>) detailsPacket.getInformation()).get(0);
                this.email = (String) details.get(0);
//                this.phoneNumber = (String) details.get(1);
//                this.address = (String) details.get(2);
//                this.eircode = (String) details.get(3);
                System.out.println("Logged in.");
                return new Packet(PacketResult.SUCCESS);
            }
            return detailsPacket;
        }
        return sessionIdPacket;
    }

    public void signout() {
        accountId = 0;
        sessionId = "";
    }

    public Packet getAccountType() {
        Packet sessionIdPacket = checkSessionId(accountId, sessionId);
        if (sessionIdPacket.getResult() == PacketResult.SUCCESS) {
            DatabaseHandler handler = DatabaseHandler.getInstance();
            Object[] info = {accountId};
            List<List<Object>> companyList = handler.get("companyId, name FROM Application.Company WHERE accountId = ?", info, 1);
            if (handler.isConnected()) {
                if (companyList.size() >= 1) {
                    List<Object> company = companyList.get(0);
                    ArrayList<Object> information = new ArrayList<>();
                    information.add(AccountType.COMPANY);
                    information.add(company.get(0));
                    information.add(company.get(1));
                    return new Packet(PacketResult.SUCCESS, information);
                }
                if (handler.isConnected()) {
                    List<List<Object>> result_2 = handler.get("accountId FROM Application.Customer WHERE accountId = ?", info, 1);
                    if (result_2.size() >= 1) {
                        return new Packet(PacketResult.SUCCESS, AccountType.CUSTOMER);
                    }
                    return new Packet(PacketResult.SUCCESS, AccountType.NULL);
                }
            }
            return new Packet(PacketResult.CONNECTION_ERROR);
        }
        return sessionIdPacket;
    }

    public static Packet createAccount(String username, String password, String email, String address, String eircode, String phoneNumber) {
        username = username.toLowerCase();
        Packet accountIdPacket = getAccountId(username);
        if (accountIdPacket.getResult() == PacketResult.BAD_REQUEST) { // BAD REQUEST = No Account
            Object[] args = {0, username, password, email, address, eircode, phoneNumber};
            DatabaseHandler handler = DatabaseHandler.getInstance();
            if (handler.isConnected()) {
                boolean success = handler.insert("Account(accountId,username,password,email,address,eircode,phoneNumber) VALUES (?,?,?,?,?,?,?)", args);
                if (success) {
                    return new Packet(PacketResult.BAD_REQUEST);
                } else {
                    return new Packet(PacketResult.DATABASE_ERROR);
                }
            } else {
                return new Packet(PacketResult.CONNECTION_ERROR);
            }
        }
        return accountIdPacket;
    }

    public Packet setAccountTypeToCustomer(String firstName, String lastName, java.util.Date dob) {
        Packet sessionIdPacket = checkSessionId(accountId, sessionId);
        if (sessionIdPacket.getResult() == PacketResult.SUCCESS) {
            Packet accountTypePacket = getAccountType();
            if (accountTypePacket.getResult() != PacketResult.SUCCESS) {
                return accountTypePacket;
            }
            java.sql.Date sqlDOB = new java.sql.Date(dob.getTime());
            if ((AccountType) accountTypePacket.getInformation() == AccountType.NULL) {
                DatabaseHandler handler = DatabaseHandler.getInstance();
                if (handler.isConnected()) {
                    Object[] args = {0, accountId, firstName, lastName, dob};
                    boolean success = handler.insert("Customer(customerId, accountId, firstName, lastName, dob) VALUES (?,?,?,?,?)", args);
                    if (success) {
                        return new Packet(PacketResult.SUCCESS);
                    }
                    return new Packet(PacketResult.DATABASE_ERROR);
                }
                return new Packet(PacketResult.CONNECTION_ERROR);
            }
            return new Packet(PacketResult.ACCESS_DENIED);
        }
        return sessionIdPacket;
    }

    public Packet setAccountTypeToCompany(String name, String website) {
        Packet sessionIdPacket = checkSessionId(accountId, sessionId);
        if (sessionIdPacket.getResult() == PacketResult.SUCCESS) {
            Packet accountTypePacket = getAccountType();
            if (accountTypePacket.getResult() != PacketResult.SUCCESS) {
                return accountTypePacket;
            }
            if ((AccountType) accountTypePacket.getInformation() == AccountType.NULL) {
                DatabaseHandler handler = DatabaseHandler.getInstance();
                if (handler.isConnected()) {
                    Object[] args = {0, accountId, name, website};
                    boolean success = handler.insert("Company(companyId, accountId, name, website) VALUES (?,?,?,?)", args);
                    if (success) {
                        return new Packet(PacketResult.SUCCESS);
                    }
                    return new Packet(PacketResult.DATABASE_ERROR);
                } else {
                    return new Packet(PacketResult.CONNECTION_ERROR);
                }
            }
        }
        return sessionIdPacket;
    }

    private static Packet getAccountId(String username) {
        Object[] info = {username};
        DatabaseHandler handler = DatabaseHandler.getInstance();
        if (handler.isConnected()) {
            List<List<Object>> result = handler.get("accountId FROM Application.Account WHERE username = ?", info, 1);
            if (result.size() >= 1) {
                return new Packet(PacketResult.SUCCESS, (int) result.get(0).get(0));
            } else {
                return new Packet(PacketResult.BAD_REQUEST);
            }
        }
        return new Packet(PacketResult.CONNECTION_ERROR);
    }

    private static Packet canLogin(String username, String password) {
        Object[] info = {username, password};
        DatabaseHandler handler = DatabaseHandler.getInstance();
        if (handler.isConnected()) {
            List<List<Object>> result = handler.get("accountId FROM Application.Account WHERE username = ? AND password = ?", info, 1);
            if (result.size() >= 1) {
                return new Packet(PacketResult.SUCCESS, (int) result.get(0).get(0) > 0);
            } else {
                return new Packet(PacketResult.ACCESS_DENIED);
            }
        }
        return new Packet(PacketResult.CONNECTION_ERROR);
    }

    /**
     * *
     * Checks if account id and session id matches inside of the SessionId
     * database table. [USED TO CHECK IF USER IS SIGNED IN OR ACCOUNT EXISTS]
     *
     * @param accountId Account Id of user
     * @param sessionId Session Id of current session
     * @return (PacketResult.SUCCESS) if the session id exists
     */
    public static Packet checkSessionId(int accountId, String sessionId) {
        Object[] info = {accountId, sessionId};
        DatabaseHandler handler = DatabaseHandler.getInstance();
        if (handler.isConnected()) {
            // Check if session id with account id exists in Session id table
            List<List<Object>> result = handler.get("accountId FROM Application.SessionId WHERE accountId = ? AND sessionId = ?", info, 1);
            if (result.size() >= 1) {
                return new Packet(PacketResult.SUCCESS);
            } else {
                return new Packet(PacketResult.BAD_REQUEST);
            }
        }
        return new Packet(PacketResult.CONNECTION_ERROR);
    }

    /**
     * Checks both the username and password inputted If the username and
     * password are found then Create / Update the session id and upload it into
     * the database
     *
     * @param username
     * @param password
     * @return (PacketResult.SUCCESS and sessionId) if successful
     */
    private static Packet requestLogin(String username, String password) {
        Packet accountIdPacket = getAccountId(username); // Does username exist
        if (accountIdPacket.getResult() != PacketResult.SUCCESS) { // Check if packet was successful
            return accountIdPacket;
        }
        int accountId = (int) accountIdPacket.getInformation();
        if (accountId > 0) { // Verify if account username exists
            Packet canLoginPacket = canLogin(username, password); // Attempt login with username and password
            if (canLoginPacket.getResult() == PacketResult.SUCCESS) {
                String sessionId = UUID.randomUUID().toString(); // Create session id
                DatabaseHandler handler = DatabaseHandler.getInstance();
                if (handler.isConnected()) {
                    Object[] info = {username, password};
                    // Attempt to get session id from username and password
                    List<List<Object>> result = handler.get("Application.SessionId.sessionId FROM Application.SessionId JOIN Application.Account ON Application.Account.accountId = Application.SessionId.accountId AND Application.Account.username = ? AND Application.Account.password = ?", info, 1);
                    if (result.isEmpty()) { // No session id exists
                        if (handler.isConnected()) {
                            Object[] args = {accountId, sessionId};
                            boolean success = handler.insert("SessionId(accountId,sessionId) VALUES (?,?)", args);
                            // Create session id
                            if (success) {
                                return new Packet(PacketResult.SUCCESS, sessionId);
                            } else {
                                return new Packet(PacketResult.DATABASE_ERROR);
                            }
                        } else {
                            return new Packet(PacketResult.CONNECTION_ERROR);
                        }
                    } else { // Session id already exists
                        Object[] args = {sessionId, accountId};
                        if (handler.isConnected()) {
                            // Update session id
                            boolean success = handler.update("SessionId SET sessionId=? WHERE accountId=?", args);
                            if (success) {
                                return new Packet(PacketResult.SUCCESS, sessionId);
                            } else {
                                return new Packet(PacketResult.DATABASE_ERROR);
                            }
                        } else {
                            return new Packet(PacketResult.CONNECTION_ERROR);
                        }
                    }
                } else {
                    return new Packet(PacketResult.CONNECTION_ERROR);
                }
            } else {
                return canLoginPacket;
            }
        }
        return new Packet(PacketResult.ERROR_OCCURRED); // No account exists??? (This should never happen)
    }

    private static Packet requestDetails(int accountId, String sessionId) {
        Packet sessionIdPacket = checkSessionId(accountId, sessionId);
        if (sessionIdPacket.getResult() != PacketResult.SUCCESS) {
            return sessionIdPacket;
        }
        DatabaseHandler handler = DatabaseHandler.getInstance();
        if (handler.isConnected()) {
            Object[] info = {accountId};
            List<List<Object>> result = handler.get("email, phoneNumber, address, eircode FROM Application.Account WHERE accountId = ?", info, 1);
            if (!result.isEmpty()) {
                return new Packet(PacketResult.SUCCESS, result); // List<List<Object>>
            } else {
                return new Packet(PacketResult.DATABASE_ERROR);
            }
        } else {
            return new Packet(PacketResult.CONNECTION_ERROR);
        }
    }
    
    public static Packet companyVerify() {
        Account account = Account.getInstance();
        Packet accountTypePacket = account.getAccountType();
        if (accountTypePacket.getResult() == PacketResult.SUCCESS) {
            ArrayList<Object> information = (ArrayList<Object>) accountTypePacket.getInformation();
            AccountType accountType = (AccountType) information.get(0);
            if (accountType == AccountType.COMPANY) {
                return new Packet(PacketResult.SUCCESS);
            }
            return new Packet(PacketResult.ACCESS_DENIED);
        }
        return accountTypePacket;
    }
    
}
