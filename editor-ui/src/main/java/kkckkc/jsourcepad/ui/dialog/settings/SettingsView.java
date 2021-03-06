package kkckkc.jsourcepad.ui.dialog.settings;

import kkckkc.jsourcepad.View;

import javax.swing.*;

public interface SettingsView extends View {
    public void addSettingsPanel(String name, JPanel panel);
    public void setSettingsPanelEnabledState(String name, boolean enabled);

    public JDialog getJDialog();

    public JButton getOkButton();
    public JButton getApplyButton();
    public JButton getCancelButton();

}
