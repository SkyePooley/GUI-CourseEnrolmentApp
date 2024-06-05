package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController implements ActionListener
{
    private final LoginView loginView;
    private boolean loggedIn = false;

    public LoginController(Frame frame) {
        loginView = new LoginView(frame);
        loginView.addLoginListener(this);
    }

    public void showLoginDialog(){
        loginView.setVisible(true);
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = loginView.getUsername();
        String password = loginView.getPassword();

        if ("admin".equals(username) && "admin".equals(password)) {
            loggedIn = true;
            loginView.dispose();
        } else {
            JOptionPane.showMessageDialog(loginView, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
