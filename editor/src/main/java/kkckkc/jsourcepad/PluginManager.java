package kkckkc.jsourcepad;

import java.util.*;

public class PluginManager {
    private static List<Plugin> plugins;
    private static List<Plugin> allPlugins;

    public static Iterable<Plugin> getActivePlugins() {
        if (plugins == null) {
            plugins = loadPlugins(true);
        }
        return plugins;
    }

    private static synchronized List<Plugin> loadPlugins(boolean filter) {
        ServiceLoader<Plugin> loader = ServiceLoader.load(Plugin.class);

        List<Plugin> available = new ArrayList<Plugin>();
        Iterator<Plugin> iterator = loader.iterator();
        while (iterator.hasNext()) {
            Plugin p = iterator.next();
            if (! filter || p.isEnabled()) available.add(p);
        }

        Map<String, Plugin> resolved = new LinkedHashMap<String, Plugin>();

        int previousSize = -1;
        while (available.size() != previousSize) {
            previousSize = available.size();

            Iterator<Plugin> it = available.iterator();
            while (it.hasNext()) {
                Plugin plugin = it.next();
                if (plugin.getDependsOn() == null || plugin.getDependsOn().length == 0) {
                    resolved.put(plugin.getId(), plugin);
                    it.remove();
                } else {
                    boolean allResolved = true;
                    for (String id : plugin.getDependsOn()) {
                        allResolved &= resolved.containsKey(id);
                    }
                    if (allResolved) {
                        resolved.put(plugin.getId(), plugin);
                        it.remove();
                    }
                }
            }
        }

        if (! available.isEmpty()) {
            throw new RuntimeException("Cannot load plugins due to dependency problems");
        }

        List<Plugin> dest = new ArrayList<Plugin>();
        dest.addAll(resolved.values());

        return dest;
    }

    public static Iterable<Plugin> getAllPlugins() {
        if (allPlugins == null) {
            allPlugins = loadPlugins(false);
        }
        return allPlugins;
    }
}
