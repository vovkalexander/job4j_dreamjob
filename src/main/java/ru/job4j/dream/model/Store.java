package ru.job4j.dream.model;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
public class Store  {
    private static final Store INST = new Store();
    private Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private Store()  {
        posts.put(1, new Post(1, "Junior Java Job", "for beginners", "2020-05-07"));
        posts.put(2, new Post(2, "Middle Java Job", "for experienced", "2020-03-29"));
        posts.put(3, new Post(3, "Senior Java Job", "for professional", "2019-03-29"));
    }

    public static Store instOf()  {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }
}
