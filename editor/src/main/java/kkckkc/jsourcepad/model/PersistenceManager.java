package kkckkc.jsourcepad.model;

public interface PersistenceManager {
    public void saveAndDiscard(String key, Object object);
    public void save(String key, Object object);
    public <T> void save(Class<T> key, T object);

    public Object load(String key);
    public <T> T load(Class<T> key);

    public void remove(String key);
    public void remove(Class key);
}
