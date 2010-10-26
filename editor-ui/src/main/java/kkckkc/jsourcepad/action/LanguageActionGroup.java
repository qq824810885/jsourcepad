package kkckkc.jsourcepad.action;

import com.google.common.collect.Maps;
import kkckkc.jsourcepad.model.Application;
import kkckkc.jsourcepad.model.Window;
import kkckkc.jsourcepad.model.bundle.Bundle;
import kkckkc.jsourcepad.model.bundle.BundleListener;
import kkckkc.jsourcepad.util.action.ActionGroup;
import kkckkc.jsourcepad.util.messagebus.DispatchStrategy;
import kkckkc.syntaxpane.parse.grammar.Language;
import kkckkc.utils.PerformanceLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Map;


public class LanguageActionGroup extends ActionGroup implements BundleListener {

	public LanguageActionGroup(Application application) {
        application.topic(BundleListener.class).subscribe(DispatchStrategy.ASYNC, this);

        rebuildMenu(application);
	}

    private void rebuildMenu(Application application) {
        clear();

        PerformanceLogger.get().enter(this, "constructor");
        Map<String, ActionGroup> keyedActionGroups = Maps.newLinkedHashMap();
        for (char c : "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()) {
            ActionGroup ag = new ActionGroup(Character.toString(c));
            keyedActionGroups.put(Character.toString(c), ag);
        }

        for (final Language l : application.getLanguageManager().getLanguages()) {
            if (! l.isStandalone()) continue;

            ActionGroup ag = keyedActionGroups.get(Character.toString(l.getName().charAt(0)).toUpperCase());
            ag.add(new LanguageAction(application, l));
        }

        for (Map.Entry<String, ActionGroup> entry : keyedActionGroups.entrySet()) {
            if (entry.getValue().size() > 0) {
                add(entry.getValue());
            }
        }
        PerformanceLogger.get().exit();

        updateDerivedComponents();
    }

    @Override
    public void bundleAdded(Bundle bundle) {
    }

    @Override
    public void bundleRemoved(Bundle bundle) {
    }

    @Override
    public void bundleUpdated(Bundle bundle) {
    }

    @Override
    public void languagesUpdated() {
        rebuildMenu(Application.get());
    }

    public static class LanguageAction extends AbstractAction {
		private Language language;
		private Application application;

		public LanguageAction(Application application, Language language) {
			super(language.getName());
			this.language = language;
			this.application = application;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			language.compile();

			Window window = application.getWindowManager().getWindow((JComponent) e.getSource());
			window.getDocList().getActiveDoc().getActiveBuffer().setLanguage(language);
		}
	}
}
