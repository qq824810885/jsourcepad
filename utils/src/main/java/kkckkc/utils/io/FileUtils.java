package kkckkc.utils.io;

import java.io.File;
import java.io.IOException;

public class FileUtils {
	public static File newTempFile(String prefix, String suffix) throws IOException {
		File f = File.createTempFile(prefix, suffix);
		f.deleteOnExit();
		return f;
	}

    public static boolean isAncestorOf(File descendant, File ancestor) {
        if (ancestor.equals(descendant)) {
            return false;
        }

        while (descendant != null && ! ancestor.equals(descendant)) {
            descendant = descendant.getParentFile();
        }

        return descendant != null;
    }

    public static String getBaseName(File file) {
        String s = file.getName();
        if (! s.contains(".")) return s;
        return s.substring(0, s.lastIndexOf('.'));
    }

    public static String shorten(String path) {
        if (path.startsWith(System.getProperty("user.home") + File.separator)) {
            return "~" + path.substring(System.getProperty("user.home").length());
        }
        return path;
    }

    public static String expand(String text) {
        return text.replaceAll("~", System.getProperty("user.home"));
    }
}
