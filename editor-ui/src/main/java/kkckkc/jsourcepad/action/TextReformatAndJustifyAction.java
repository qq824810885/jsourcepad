package kkckkc.jsourcepad.action;

import kkckkc.jsourcepad.model.Application;
import kkckkc.jsourcepad.model.Buffer;
import kkckkc.jsourcepad.model.Window;
import kkckkc.jsourcepad.model.settings.StyleSettings;
import kkckkc.jsourcepad.util.TextUtils;
import kkckkc.jsourcepad.util.action.BaseAction;
import kkckkc.syntaxpane.model.Interval;

import java.awt.event.ActionEvent;

public class TextReformatAndJustifyAction extends BaseAction {
    private final Window window;

	public TextReformatAndJustifyAction(Window window) {
		this.window = window;
        setActionStateRules(ActionStateRules.HAS_ACTIVE_DOC);
	}

	@Override
    public void performAction(ActionEvent e) {
        StyleSettings ss = Application.get().getSettingsManager().get(StyleSettings.class);

		Buffer activeBuffer = window.getDocList().getActiveDoc().getActiveBuffer();

        Interval selectionOrCurrentParagraph = activeBuffer.getSelectionOrCurrentParagraph();
        String text = activeBuffer.getText(selectionOrCurrentParagraph);

        String[] lines = TextUtils.wrap(text, ss.getWrapColumn());

        StringBuilder builder = new StringBuilder();
        for (String line : lines) {
            builder.append(TextUtils.justifyLine(line, ss.getWrapColumn())).append("\n");
        }

        if (! text.endsWith("\n"))
            builder.setLength(builder.length() - 1);

		activeBuffer.replaceText(selectionOrCurrentParagraph, builder.toString(), null);
    }
}