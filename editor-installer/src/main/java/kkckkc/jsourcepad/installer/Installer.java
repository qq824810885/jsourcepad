package kkckkc.jsourcepad.installer;

import com.google.common.base.Function;
import com.google.common.io.Files;
import kkckkc.jsourcepad.bundleeditor.installer.BundleInstallerDialog;
import kkckkc.jsourcepad.util.Config;
import kkckkc.jsourcepad.util.Network;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Installer implements Function<Runnable, Boolean> {
    private BundleInstallerDialog bundleInstallerDialog;

    @Autowired
    public void setBundleInstallerDialog(BundleInstallerDialog bundleInstallerDialog) {
        this.bundleInstallerDialog = bundleInstallerDialog;
    }

    @Override
    public Boolean apply(Runnable continuation) {
        try {

            // Copy themes
            try {
                Config.getThemesFolder().mkdirs();
                File themeFolder = new File(Config.getApplicationFolder(), "Shared/Themes");
                if (themeFolder.listFiles() != null) {
                    for (File theme : themeFolder.listFiles()) {
                        Files.copy(theme, new File(Config.getThemesFolder(), theme.getName()));
                    }
                }
            } catch (IOException ioe) {
                throw new RuntimeException(ioe);
            }

            Config.getBundlesFolder().mkdirs();

            // Do you want to install more bundles
            int option = JOptionPane.showOptionDialog(null,
                    "You have no bundles installed. Do you want to run the bundle installer now?\n" +
                    "You can run it later at any time, using the menu Bundles > Install Bundles",
                    "Run Bundle Installer Now?",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new String[] { "Yes, Run Bundle Installer Now", "No, I'll Run it Later" },
                    "Yes, Run Bundle Installer Now");
            if (option == 0) {
                if (! Network.checkConnectivity()) {
                    JOptionPane.showMessageDialog(null,
                        "It seems that the Internet is not reachable. It might be due to network connectivity\n" +
                        "problems. It can also that you need to enter proxy server details before connecting. \n" +
                        "Proxy settings can be configured from the Preferences dialog and you can install bundles later.",
                        "Network Connectivity",
                        JOptionPane.ERROR_MESSAGE);
                } else {
                    bundleInstallerDialog.show();
                }
            }

            continuation.run();

        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
        
        return true;
    }
}
