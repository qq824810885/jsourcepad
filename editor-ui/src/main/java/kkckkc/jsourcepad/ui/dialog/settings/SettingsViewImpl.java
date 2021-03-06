package kkckkc.jsourcepad.ui.dialog.settings;

import kkckkc.jsourcepad.util.ui.BaseJDialog;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class SettingsViewImpl extends BaseJDialog implements SettingsView {
    private JTabbedPane tabbedPane;
    private JButton okButton;
    private JButton applyButton;
    private JButton cancelButton;

    public SettingsViewImpl() {
        super(null, true);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        setTitle("Settings");

        Container p = getContentPane();
        p.setLayout(new MigLayout("insets dialog", "[grow,15sp::][]", "[grow,15sp::]u[]"));

        tabbedPane = new JTabbedPane();
        okButton = new JButton("OK");
        applyButton = new JButton("Apply");
        cancelButton = new JButton("Cancel");

        p.add(tabbedPane, "grow,wrap,span");
        p.add(okButton, "tag ok,span 3");
        p.add(applyButton, "tag apply");
        p.add(cancelButton, "tag cancel");

        pack();
    }

    @Override
    public void addSettingsPanel(String name, JPanel panel) {
        tabbedPane.add(name, panel);
        pack();
    }

    @Override
    public void setSettingsPanelEnabledState(String name, boolean enabled) {
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            if (tabbedPane.getTitleAt(i).equals(name)) {
                tabbedPane.setEnabledAt(i, enabled);
            }
        }
    }

    @Override
    public JDialog getJDialog() {
        return this;
    }

    @Override
    public JButton getOkButton() {
        return okButton;
    }

    @Override
    public JButton getApplyButton() {
        return applyButton;
    }

    @Override
    public JButton getCancelButton() {
        return cancelButton;
    }

}
