/**
 * @author clayton
 */
package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends JDialog
{
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    
    public LoginView(Frame owner){
        super(owner, "Login", true);
        setLayout(new GridLayout(3, 2));
        
        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);
        
        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);
        
        loginButton = new JButton("Login");
        add(loginButton);
        
        setSize(300, 150);
        setLocationRelativeTo(owner);
    }
    
    public String getUsername(){
        return usernameField.getText();
    }
    
    public String getPassword() {
        return new String(passwordField.getPassword());
    }
    
    public JButton getLoginButton() {
        return loginButton;
    }
    
    public void addLoginListener(ActionListener listener){
        loginButton.addActionListener(listener);
    }
    
    public void showLoginError(){
        JOptionPane.showMessageDialog(this, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
    }
}
