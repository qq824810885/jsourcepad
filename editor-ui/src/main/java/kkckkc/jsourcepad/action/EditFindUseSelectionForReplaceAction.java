package kkckkc.jsourcepad.action;

import kkckkc.jsourcepad.model.Buffer;
import kkckkc.jsourcepad.model.Doc;
import kkckkc.jsourcepad.model.Finder;
import kkckkc.jsourcepad.util.action.BaseAction;

import java.awt.event.ActionEvent;

public class EditFindUseSelectionForReplaceAction extends BaseAction {
	public EditFindUseSelectionForReplaceAction() {
        setActionStateRules(ActionStateRules.HAS_ACTIVE_DOC);
	}

    @Override
    public void performAction(ActionEvent e) {
        Doc activeDoc = actionContext.get(ActionContextKeys.ACTIVE_DOC);
		Buffer buffer = activeDoc.getActiveBuffer();

        String replacementText = "";
        if (buffer.getSelection() != null) {
            replacementText = buffer.getSelection().getText();
        }

        Finder finder = buffer.getFinder();
        Finder newFinder;
        if (finder == null) {
            newFinder = buffer.newFinder(buffer.getCompleteDocument(), "", new Finder.Options());
        } else {
            newFinder = buffer.newFinder(buffer.getCompleteDocument(), "", finder.getOptions());
        }
        newFinder.setReplacement(replacementText);
    }

}