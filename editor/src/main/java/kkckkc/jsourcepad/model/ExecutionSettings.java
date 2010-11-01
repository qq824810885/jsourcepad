package kkckkc.jsourcepad.model;

import kkckkc.jsourcepad.model.SettingsManager.Setting;
import kkckkc.utils.Os;

public class ExecutionSettings implements Setting {

	private String[] args;

	public ExecutionSettings() {
	}

	public ExecutionSettings(String... args) {
        this.args = args;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public ExecutionSettings getDefault() {
        if (Os.isWindows()) {
            return new ExecutionSettings("c:/cygwin/bin/bash.exe", "--login", "-c");
        } else {
            return new ExecutionSettings("bash", "--login", "-c");
        }
    }

}
