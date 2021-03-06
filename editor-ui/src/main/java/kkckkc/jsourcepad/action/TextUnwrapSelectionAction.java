package kkckkc.jsourcepad.action;

import kkckkc.jsourcepad.model.Buffer;
import kkckkc.jsourcepad.model.Window;
import kkckkc.jsourcepad.util.action.BaseAction;
import kkckkc.syntaxpane.model.Interval;

import java.awt.event.ActionEvent;

public class TextUnwrapSelectionAction extends BaseAction {
    private final Window window;

	public TextUnwrapSelectionAction(Window window) {
		this.window = window;
        setActionStateRules(ActionStateRules.HAS_ACTIVE_DOC);
	}

	@Override
    public void performAction(ActionEvent e) {
		Buffer activeBuffer = window.getDocList().getActiveDoc().getActiveBuffer();

        Interval selectionOrCurrentParagraph = activeBuffer.getSelectionOrCurrentParagraph();
        String text = activeBuffer.getText(selectionOrCurrentParagraph);

        text = text.replaceAll("\n", "");

		activeBuffer.replaceText(selectionOrCurrentParagraph, text, null);
    }


    @Override
    protected void actionContextUpdated() {
        if (ActionStateRules.TEXT_SELECTED.shouldBeEnabled(actionContext)) {
            putValue(NAME, "Unwrap Selection");
        } else {
            putValue(NAME, "Unwrap Paragraph");
        }
    }

}