package kkckkc.syntaxpane;

import kkckkc.syntaxpane.model.SourceDocument;
import kkckkc.syntaxpane.parse.grammar.Language;
import kkckkc.syntaxpane.parse.grammar.LanguageManager;
import kkckkc.syntaxpane.style.StyleScheme;
import kkckkc.utils.swing.Wiring;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;



public class ScrollableSourcePane extends JPanel {
	private SourceJEditorPane editorPane;
	private JScrollPane scrollPane;
	
	private StyleScheme styleScheme;
	private SourceEditorKit editorKit;
	private LineNumberMargin lineNumberPane;
	private FoldMargin foldMargin;
	private Color origBackground;

    private boolean lineNumbers;
    private boolean foldings;
    private JPanel rowHeaderPane;
    private boolean showInvisibles;
    private int wrapColumn;

    public ScrollableSourcePane(LanguageManager languageManager) {
		super(new BorderLayout());
		
		editorKit = new SourceEditorKit(this, languageManager);

		editorPane = new SourceJEditorPane();
		editorPane.setOpaque(false);
		editorPane.setEditorKit(editorKit);
		editorPane.setUI(new javax.swing.plaf.basic.BasicEditorPaneUI());
		
		Wiring.wire(this, editorPane, "font", "background", "foreground");
		
		foldMargin = new FoldMargin(editorPane);
		lineNumberPane = new LineNumberMargin(editorPane);
		
		scrollPane = new JScrollPane(editorPane);
		
		rowHeaderPane = new JPanel();
		rowHeaderPane.setLayout(new BorderLayout());

		scrollPane.setRowHeaderView(rowHeaderPane);
		scrollPane.setViewportBorder(null);

		origBackground = super.getBackground();
		setBackground(Color.WHITE);
		setForeground(Color.BLACK);

		add(scrollPane, BorderLayout.CENTER);
	}

    public boolean isLineNumbers() {
        return lineNumbers;
    }

    public void setLineNumbers(boolean lineNumbers) {
        if (this.lineNumbers == lineNumbers) return;
        this.lineNumbers = lineNumbers;
        if (this.lineNumbers) {
            this.rowHeaderPane.add(lineNumberPane, BorderLayout.WEST);
        } else {
            this.rowHeaderPane.remove(lineNumberPane);
        }
    }

    public boolean isFoldings() {
        return foldings;
    }

    public void setFoldings(boolean foldings) {
        if (this.foldings == foldings) return;
        this.foldings = foldings;
        if (this.foldings) {
            this.rowHeaderPane.add(foldMargin, BorderLayout.EAST);
        } else {
            this.rowHeaderPane.remove(foldMargin);
        }
    }

    public JScrollPane getScrollPane() {
		return scrollPane;
	}
	
	public Color getBackground() {
		return origBackground;
	}

    public void setOverwriteMode(boolean overwriteMode) {
        editorPane.setOverwriteMode(overwriteMode);
    }

	public void setStyleScheme(StyleScheme styleScheme) {
		boolean styleSchemeChanged = this.styleScheme != null;
		this.styleScheme = styleScheme;

        setBackground(this.styleScheme.getTextStyle().getBackground());
        setForeground(this.styleScheme.getTextStyle().getColor());
		
		editorPane.setCaretColor(this.styleScheme.getCaretColor());
		
		editorPane.setSelectionColor(this.styleScheme.getSelectionStyle().getBackground());
		editorPane.setSelectedTextColor(this.styleScheme.getSelectionStyle().getColor());
        editorPane.setOpaque(false);

		lineNumberPane.setBackground(this.styleScheme.getLineNumberStyle().getBackground());
		lineNumberPane.setForeground(this.styleScheme.getLineNumberStyle().getColor());
		
		foldMargin.setBackground(this.styleScheme.getLineNumberStyle().getBackground());
		foldMargin.setForeground(this.styleScheme.getLineNumberStyle().getColor());
		foldMargin.setBorderColor(this.styleScheme.getLineNumberStyle().getBorder());

        updateCurrentLineHighlighter();

        if (styleSchemeChanged) {
			repaint();
		}
	}

    private void updateCurrentLineHighlighter() {
        CurrentLinePainter.apply(new CurrentLinePainter(
            this.styleScheme.getLineSelectionColor(),
            getStyleScheme().getRightMargin().getColor(),
            getStyleScheme().getRightMargin().getBackground(),
            getWrapColumn()), editorPane);
    }

    public JEditorPane getEditorPane() {
		return editorPane;
	}

	public void setLanguage(Language lang) {
		((SourceDocument) editorPane.getDocument()).setLanguage(lang);
	}

	public StyleScheme getStyleScheme() {
		return styleScheme;
	}
	
	public SourceDocument getDocument() {
		return (SourceDocument) editorPane.getDocument();
	}
	
	public void read(Reader reader) throws IOException {
		editorPane.read(reader, null);
	}
	
	public void read(File f) throws IOException {
		editorPane.read(new FileReader(f), f);
	}
    
    public void setShowInvisibles(boolean showInvisibles) {
        if (this.showInvisibles == showInvisibles) return;
        this.showInvisibles = showInvisibles;
        repaint();
    }

    public boolean isShowInvisibles() {
        return showInvisibles;
    }

    public void setWrapColumn(int wrapColumn) {
        if (this.wrapColumn == wrapColumn) return;
        this.wrapColumn = wrapColumn;
        updateCurrentLineHighlighter();
        repaint();
    }

    public int getWrapColumn() {
        return wrapColumn;
    }

    private final class SourceJEditorPane extends JEditorPane {
        private boolean overwriteMode;
		
	    @Override
	    public void paint(Graphics graphics) {
	    	Graphics2D graphics2d = (Graphics2D) graphics;

	    	int wm = graphics.getFontMetrics().charWidth('m');
	    	
	    	Rectangle clip = graphics.getClipBounds();

            graphics2d.setColor(getStyleScheme().getTextStyle().getBackground());
            graphics2d.fillRect(clip.x, clip.y, clip.width, clip.height);

            graphics2d.setColor(getStyleScheme().getRightMargin().getBackground());
            graphics2d.fillRect(getWrapColumn() * wm, clip.y, clip.width, clip.height);

            graphics2d.setColor(getStyleScheme().getRightMargin().getColor());
            graphics2d.drawLine(getWrapColumn() * wm, clip.y, getWrapColumn() * wm, clip.y + clip.height);

	    	super.paint(graphics);
	    }

        private void setOverwriteMode(boolean overwriteMode) {
            this.overwriteMode = overwriteMode;
        }

        public void replaceSelection(String content) {
            if (isEditable() && overwriteMode && getSelectionStart() == getSelectionEnd()) {
                int pos = getCaretPosition();
                int lastPos = Math.min(getDocument().getLength(), pos+content.length());
                select(pos, lastPos);
            }
            super.replaceSelection(content);
        }

    }
}
