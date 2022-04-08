package customer.invoice.system;

import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author C00261172
 */
public class LoginAccountForm extends javax.swing.JFrame {

    /**
     * Creates new form SignUpForm
     *
     * @param component
     */
    public LoginAccountForm(Component component) {
        initComponents();
        this.setLocationRelativeTo(component);
        ErrorLabel.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        UsernameTF = new javax.swing.JTextField();
        LoginButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        PasswordTF = new javax.swing.JPasswordField();
        SignUpButton = new javax.swing.JButton();
        ErrorLabel = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login Account");
        setResizable(false);

        jLabel2.setText("Username");

        LoginButton.setText("Login");
        LoginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginButtonActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(0, 153, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Login Account");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel3.setText("Password");

        SignUpButton.setText("Sign Up Instead");
        SignUpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SignUpButtonActionPerformed(evt);
            }
        });

        ErrorLabel.setForeground(new java.awt.Color(255, 51, 51));

        jButton1.setText("Forgot Password");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(UsernameTF)
                    .addComponent(PasswordTF, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LoginButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SignUpButton)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(ErrorLabel)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(UsernameTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PasswordTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(ErrorLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(LoginButton)
                        .addComponent(SignUpButton)))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LoginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoginButtonActionPerformed
        String username = UsernameTF.getText();
        if (username.isBlank()) {
            ErrorLabel.setText("* Please enter your username");
            ErrorLabel.setVisible(true);
            return;
        }
        String password = PasswordTF.getText();
        if (password.isBlank()) {
            ErrorLabel.setText("* Please enter your password");
            ErrorLabel.setVisible(true);
            return;
        }

        Account account = Account.getInstance();
        Packet loginPacket = account.login(UsernameTF.getText(), PasswordTF.getText());
        switch (loginPacket.getResult()) {
            case SUCCESS:
                Packet accountTypePacket = account.getAccountType();
                switch (accountTypePacket.getResult()) {
                    /* Start */
                    case SUCCESS:
                        ArrayList<Object> accountInformation = (ArrayList<Object>) accountTypePacket.getInformation();
                        AccountType accountType = (AccountType) accountInformation.get(0);
                        switch (accountType) {
                            case COMPANY:
                                new CompanyMenu(this).setVisible(true);
                                break;
                            case CUSTOMER:
                                System.out.println("Customer Menu Here");
                                break;
                            case NULL:
                                new SelectAccountType(this).setVisible(true);
                                break;
                        }
                        break;
                    case CONNECTION_ERROR:
                        ErrorLabel.setText("* A connection error.");
                        ErrorLabel.setVisible(true);
                        break;
                    default:
                        JOptionPane.showMessageDialog(this, "An Error has Occurred!",
                                "Error Occurred", JOptionPane.ERROR_MESSAGE);
                        break;
                }
                /* End */
                dispose();
                break;
            case ACCESS_DENIED:
                ErrorLabel.setText("* Incorrect Password. Try again or click 'Forgot Password to reset it");
                ErrorLabel.setVisible(true);
                break;
            case DATABASE_ERROR:
                ErrorLabel.setText("* An error occurred inside of the database query");
                ErrorLabel.setVisible(true);
                break;
            case ERROR_OCCURRED:
                ErrorLabel.setText("* An error occurred");
                ErrorLabel.setVisible(true);
                break;
            case CONNECTION_ERROR:
                ErrorLabel.setText("* A connection error.");
                ErrorLabel.setVisible(true);
                break;
            default:
                ErrorLabel.setText("* Incorrect Password. Try again or click 'Forgot Password to reset it");
                ErrorLabel.setVisible(true);
                break;
        }
    }//GEN-LAST:event_LoginButtonActionPerformed

    private void SignUpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SignUpButtonActionPerformed
        new SignUpAccountForm(this).setVisible(true);
        dispose();
    }//GEN-LAST:event_SignUpButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ErrorLabel;
    private javax.swing.JButton LoginButton;
    private javax.swing.JPasswordField PasswordTF;
    private javax.swing.JButton SignUpButton;
    private javax.swing.JTextField UsernameTF;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
