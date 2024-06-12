package GUI;

import Model.DBModel;
import Model.UpdateFlags;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

/**
 * Popup window which prompts the user to login using their student ID
 * @author Clayton Roberts and Skye Pooley
 */
public class LoginView extends JDialog implements InteractivePanel, Observer
{
    private final JTextField usernameField;
    private final JButton loginButton;
    
    public LoginView(Frame owner){
        super(owner, "Login", true);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Student ID:"));
        usernameField = new JTextField();
        add(usernameField);

        loginButton = new JButton("Login");
        add(loginButton);

        setSize(300, 150);
        setLocationRelativeTo(owner);
    }

    public void addActionListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }

    /**
     * Get updates from the model to know whether the login was successful
     * @param obs     the Model
     * @param obj     the update flags from model
     * @author Skye Pooley
     */
    public void update(Observable obs, Object obj) {
        if (!(obs instanceof DBModel)) { return; }
        if (!(obj instanceof UpdateFlags)) { return; }
        UpdateFlags flags = (UpdateFlags) obj;

        if (flags.loginFail) { this.showLoginError(); }
    }

    public String getUsername(){
        return usernameField.getText();
    }
    
    public void showLoginError(){
        JOptionPane.showMessageDialog(this, "Username Does not Exist", "Login Failed", JOptionPane.ERROR_MESSAGE);
    }
}
