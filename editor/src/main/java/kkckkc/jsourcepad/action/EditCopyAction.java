package kkckkc.jsourcepad.action;

import java.awt.event.ActionEvent;

import kkckkc.jsourcepad.model.Doc;
import kkckkc.jsourcepad.model.Window;
import kkckkc.jsourcepad.ui.DocPresenter;
import kkckkc.jsourcepad.util.action.BaseAction;

public class EditCopyAction extends BaseAction{
    private final Window window;

	public EditCopyAction(Window window) {
		this.window = window;
	}
	
	@Override
    public void actionPerformed(ActionEvent e) {
		Doc d = window.getDocList().getActiveDoc();
		DocPresenter dp = d.getPresenter(DocPresenter.class);
		dp.copy();
    }

}
