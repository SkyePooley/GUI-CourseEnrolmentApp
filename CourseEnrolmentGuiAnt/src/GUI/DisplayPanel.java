package GUI;

import Model.DBModel;

/**
 * Enforces update methods for all panels which display live data.
 * @author Skye Pooley
 */
public interface DisplayPanel {
    public void update(DBModel model);
}
