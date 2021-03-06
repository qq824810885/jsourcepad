package kkckkc.jsourcepad.action;

import kkckkc.jsourcepad.model.Buffer;
import kkckkc.jsourcepad.model.Window;
import kkckkc.jsourcepad.model.settings.TabProjectSettings;
import kkckkc.jsourcepad.util.action.BaseAction;
import kkckkc.syntaxpane.model.TextInterval;

import java.awt.event.ActionEvent;

public class TextConvertSpacesToTabsAction extends BaseAction {
    private final Window window;

	public TextConvertSpacesToTabsAction(Window window) {
		this.window = window;
        setActionStateRules(ActionStateRules.HAS_ACTIVE_DOC);
	}

	@Override
    public void performAction(ActionEvent e) {
        TabProjectSettings ts = window.getProject().getSettingsManager().get(TabProjectSettings.class);

		Buffer activeBuffer = window.getDocList().getActiveDoc().getActiveBuffer();

        TextInterval selectionOrCurrentLine = activeBuffer.getSelectionOrCurrentLine();
        String text = selectionOrCurrentLine.getText();

        StringBuilder builder = new StringBuilder();
        for (int j = 0; j < ts.getTabSize(); j++) builder.append(" ");

        text = text.replace(builder.toString(), "\t");

		activeBuffer.replaceText(selectionOrCurrentLine, text, null);
    }

}