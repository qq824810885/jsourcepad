package kkckkc.jsourcepad.action.bundle;

import java.awt.Container;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import kkckkc.jsourcepad.model.Application;
import kkckkc.jsourcepad.model.Window;
import kkckkc.jsourcepad.model.bundle.BundleItem;
import kkckkc.jsourcepad.model.bundle.BundleItemSupplier;

public class BundleAction extends AbstractAction {

	private BundleItemSupplier ref;

	public BundleAction(BundleItemSupplier ref) {
//		super("<html>" + ref.getName() + "&nbsp;&nbsp;&nbsp;<i>" + ref.getActivator().toString() + "</i></html>");
		super(ref.getName());
		this.ref = ref;
		this.ref.setAction(this);
    }

	public BundleItemSupplier getRef() {
	    return ref;
    }
	
	@Override
    public void actionPerformed(ActionEvent e) {
		BundleItem bi = ref.get();
		try {
			Window window = Application.get().getWindowManager().getWindow((Container) e.getSource());
	        bi.execute(window);
        } catch (Exception e1) {
	        e1.printStackTrace();
        }
    }

}
