package kkckkc.jsourcepad.model;

import com.google.common.base.Predicate;
import kkckkc.jsourcepad.model.settings.SettingsManager;

import java.io.File;
import java.util.List;

public interface Project {
	interface FileChangeListener {
        void renamed(File newFile, File oldFile);
        void removed(File file);
		void created(File file);
	}

    interface RefreshListener {
        public void refresh(File file);
    }

	public List<File> findFile(String query);
	public File getProjectDir();
	public void refresh(File file);

	public String getProjectRelativePath(String path);
	
	public List<File> getSelectedFiles();
	public void setSelectedFiles(List<File> paths);

    public SettingsManager getSettingsManager();

    public Predicate<File> getFilePredicate();

    public void register(File file);
    public void unregister(File file);
}
