package GUI;

import Model.DBModel;

import java.awt.event.ActionListener;

/**
 * Enforces addActionListener methods for all classes which contain interactable components.
 * @author Skye Pooley
 */
public interface InteractivePanel {
    public void addActionListener(ActionListener listener);
}
