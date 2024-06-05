/**
 * @author Clayton
 */
package GUI;

import javax.swing.*;
import java.awt.*;

public class RemovePanel extends JPanel
{
    public RemovePanel()
    {
        setLayout(new GridLayout(1, 2));
        JToggleButton addButton = new JToggleButton("Add");
        JToggleButton removeButton = new JToggleButton("Remove");
        ButtonGroup group = new ButtonGroup();
        group.add(addButton);
        group.add(removeButton);
        addButton.setSelected(true);
        
        add(addButton);
        add(removeButton);
    }
}
