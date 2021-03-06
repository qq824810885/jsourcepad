package kkckkc.jsourcepad.ui;

import kkckkc.jsourcepad.model.Application;
import kkckkc.syntaxpane.ScrollableSourcePane;

import javax.swing.*;
import java.awt.*;

public class DocViewImpl implements DocView {

	private ScrollableSourcePane sourcePane;

	public DocViewImpl() {
		sourcePane = createScrollableSource();
	}
	
	@Override
    public JComponent getComponent() {
	    return sourcePane;
    }

    @Override
    public ScrollableSourcePane getSourcePane() {
        return sourcePane;
    }

    protected ScrollableSourcePane createScrollableSource() {
	    return new ScrollableSourcePane(Application.get().getLanguageManager());
    }

	@Override
    public void updateTabSize(int tabSize) {
		sourcePane.updateTabSize(tabSize);
    }

	@Override
    public void redraw() {
		// "Clone" font to force full redraw / recalculation
		Font font = sourcePane.getFont();
		font = font.deriveFont(font.getStyle());
		sourcePane.getEditorPane().setFont(font);
    }

}
