package kkckkc.jsourcepad.bundleeditor.command;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import kkckkc.jsourcepad.bundleeditor.BasicBundleDocPresenter;
import kkckkc.jsourcepad.bundleeditor.model.BundleDocImpl;
import kkckkc.jsourcepad.model.bundle.CommandBundleItem;

import java.util.Map;

public class CommandBundleDocPresenter extends BasicBundleDocPresenter {

    private BiMap<String, String> inputMap = HashBiMap.create();
    private BiMap<String, String> secondaryInputMap = HashBiMap.create();
    private BiMap<String, String> outputMap = HashBiMap.create();
    private BiMap<String, String> saveMap = HashBiMap.create();

    public CommandBundleDocPresenter() {
        super();

        initMapping();
    }

    private void initMapping() {
        inputMap.put(CommandBundleDocViewImpl.INPUT_ENTIRE_DOCUMENT, CommandBundleItem.INPUT_DOCUMENT);
        inputMap.put(CommandBundleDocViewImpl.INPUT_NONE, CommandBundleItem.INPUT_NONE);
        inputMap.put(CommandBundleDocViewImpl.INPUT_SELECTED_TEXT, CommandBundleItem.INPUT_SELECTION);

        secondaryInputMap.put(CommandBundleDocViewImpl.INPUT2_CHARACTER, CommandBundleItem.INPUT_CHARACTER);
        secondaryInputMap.put(CommandBundleDocViewImpl.INPUT2_DOCUMENT, CommandBundleItem.INPUT_DOCUMENT);
        secondaryInputMap.put(CommandBundleDocViewImpl.INPUT2_LINE, CommandBundleItem.INPUT_LINE);
        secondaryInputMap.put(CommandBundleDocViewImpl.INPUT2_NOTHING, "nop");
        secondaryInputMap.put(CommandBundleDocViewImpl.INPUT2_SCOPE, CommandBundleItem.INPUT_SCOPE);
        secondaryInputMap.put(CommandBundleDocViewImpl.INPUT2_WORD, CommandBundleItem.INPUT_WORD);

        outputMap.put(CommandBundleDocViewImpl.OUTPUT_INSERT_AS_TEXT, CommandBundleItem.OUTPUT_AFTER_SELECTED_TEXT);
        outputMap.put(CommandBundleDocViewImpl.OUTPUT_CREATE_NEW_DOCUMENT, CommandBundleItem.OUTPUT_CREATE_NEW_DOCUMENT);
        outputMap.put(CommandBundleDocViewImpl.OUTPUT_DISCARD, CommandBundleItem.OUTPUT_DISCARD);
        outputMap.put(CommandBundleDocViewImpl.OUTPUT_INSERT_AS_SNIPPET, CommandBundleItem.OUTPUT_INSERT_AS_SNIPPET);
        outputMap.put(CommandBundleDocViewImpl.OUTPUT_REPLACE_DOCUMENT, CommandBundleItem.OUTPUT_REPLACE_DOCUMENT);
        outputMap.put(CommandBundleDocViewImpl.OUTPUT_REPLACE_SELECTED_TEXT, CommandBundleItem.OUTPUT_REPLACE_SELECTED_TEXT);
        outputMap.put(CommandBundleDocViewImpl.OUTPUT_SHOW_AS_HTML, CommandBundleItem.OUTPUT_SHOW_AS_HTML);
        outputMap.put(CommandBundleDocViewImpl.OUTPUT_SHOW_AS_TOOLTIP, CommandBundleItem.OUTPUT_SHOW_AS_TOOLTIP);

        saveMap.put(CommandBundleDocViewImpl.SAVE_NOTHING, "nop");
        saveMap.put(CommandBundleDocViewImpl.SAVE_ALL_FILES_IN_PROJECT, CommandBundleItem.BEFORE_SAVE_ALL);
        saveMap.put(CommandBundleDocViewImpl.SAVE_CURRENT_FILE, CommandBundleItem.BEFORE_SAVE_ACTIVE);
    }

    @Override
    public void init() {
        super.init();

        BundleDocImpl bDoc = (BundleDocImpl) doc;
        CommandBundleDocViewImpl cView = (CommandBundleDocViewImpl) view;
        Map<String, Object> plist = bDoc.getPlist();

        cView.getSecondayInput().setSelectedIndex(0);
        cView.getInput().setSelectedIndex(0);
        cView.getOutput().setSelectedIndex(0);
        cView.getSave().setSelectedIndex(0);


        String fallbackInput = secondaryInputMap.inverse().get(plist.get("fallbackInput"));
        if (fallbackInput != null) cView.getSecondayInput().setSelectedItem(fallbackInput);

        String input = inputMap.inverse().get(plist.get("input"));
        if (input != null) cView.getInput().setSelectedItem(input);

        String output = outputMap.inverse().get(plist.get("output"));
        if (output != null) cView.getOutput().setSelectedItem(output);

        String beforeRunningCommand = saveMap.inverse().get(plist.get("beforeRunningCommand"));
        if (beforeRunningCommand != null) cView.getSave().setSelectedItem(beforeRunningCommand);

        register(cView.getSecondayInput());
        register(cView.getInput());
        register(cView.getOutput());
        register(cView.getSave());
    }

    @Override
    protected void saveCallback() {
        super.saveCallback();

        BundleDocImpl bDoc = (BundleDocImpl) doc;
        CommandBundleDocViewImpl cView = (CommandBundleDocViewImpl) view;
        Map plist = bDoc.getPlist();

        if (cView.getInput().getSelectedItem().equals(CommandBundleDocViewImpl.INPUT_SELECTED_TEXT)) {
            plist.put("fallbackInput", secondaryInputMap.get(cView.getSecondayInput().getSelectedItem()));
        } else {
            plist.remove("fallbackInput");
        }
        plist.put("input", inputMap.get(cView.getInput().getSelectedItem()));
        plist.put("output", outputMap.get(cView.getOutput().getSelectedItem()));
        plist.put("beforeRunningCommand", saveMap.get(cView.getSave().getSelectedItem()));
    }
}
