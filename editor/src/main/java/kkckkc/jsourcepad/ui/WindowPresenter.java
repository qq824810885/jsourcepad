package kkckkc.jsourcepad.ui;

import kkckkc.jsourcepad.Presenter;
import kkckkc.jsourcepad.action.bundle.BundleAction;
import kkckkc.jsourcepad.action.bundle.BundleJMenuItem;
import kkckkc.jsourcepad.model.Application;
import kkckkc.jsourcepad.model.Doc;
import kkckkc.jsourcepad.model.DocList;
import kkckkc.jsourcepad.model.Window;
import kkckkc.jsourcepad.model.bundle.BundleManager;
import kkckkc.jsourcepad.util.action.MenuFactory;
import kkckkc.jsourcepad.util.messagebus.DispatchStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;


public class WindowPresenter implements Presenter<WindowView>, DocList.Listener {

	private Window window;
	private WindowView windowView;
	private BundleManager bundleManager;
	private JFrame frame;
	
	@Autowired
	public void setWindow(Window window) {
	    this.window = window;
    }
	
	@Autowired
	public void setBundleManager(BundleManager bundleManager) {
	    this.bundleManager = bundleManager;
    }
	
	@Autowired
    public void setView(WindowView view) {
	    this.windowView = view;
    }


	@PostConstruct
    public void init() throws Exception {
		frame = windowView.getJFrame();
		frame.setTitle("JSourcePad");
		
		frame.setLocationRelativeTo(null);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Application.get().getWindowManager().closeWindow(window);
			}
		});

		frame.addWindowFocusListener(new WindowFocusListener() {
			public void windowLostFocus(WindowEvent e) {
				window.topic(Window.FocusListener.class).post().focusLost(window);
			}
			
			public void windowGainedFocus(WindowEvent e) {
				window.topic(Window.FocusListener.class).post().focusGained(window);
			}
		});
		
		JMenuBar mb = windowView.getMenubar();
		MenuFactory menuFactory = new MenuFactory();

		mb.add(menuFactory.buildMenu("File", window.getActionManager().getActionGroup("file-menu"), null, false));
		mb.add(menuFactory.buildMenu("Edit", window.getActionManager().getActionGroup("edit-menu"), null, false));
		mb.add(menuFactory.buildMenu("View", window.getActionManager().getActionGroup("view-menu"), null, false));
		mb.add(menuFactory.buildMenu("Text", window.getActionManager().getActionGroup("text-menu"), null, false));
		mb.add(menuFactory.buildMenu("Navigation", window.getActionManager().getActionGroup("navigation-menu"), null, false));
		mb.add(menuFactory.buildMenu("Bundles", bundleManager.getBundleActionGroup(), new MenuFactory.ItemBuilder() {
			public JMenuItem build(Action action) {
				return new BundleJMenuItem((BundleAction) action);
			}
		}, true));

		window.topic(DocList.Listener.class).subscribe(DispatchStrategy.ASYNC_EVENT, this);
        frame.setVisible(true);
    }
	
	
	
	public void dispose() {
		this.frame.dispose();
	}

	public void closed(int index, Doc doc) {
		this.frame.setTitle("JSourcePad");
	}

	public void created(Doc doc) {
	}

	public void selected(int index, Doc doc) {
		this.frame.setTitle("JSourcePad - " + doc.getTitle());
	}

	public Container getContainer() {
	    return this.windowView.getJFrame();
    }

	
}