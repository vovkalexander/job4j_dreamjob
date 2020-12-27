package ru.job4j.dream.model;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class StoreStub implements Store {
    private final Map<Integer, Post> postStore = new HashMap<>();
    private int postId = 0;

    @Override
    public Collection<Post> findAllPosts() {
        return new ArrayList<Post>(this.postStore.values());
    }

    @Override
    public   Collection<Candidate> findAllCandidates() {
        return null;
    }

    @Override
    public void save(Post post) {
        post.setId(this.postId++);
        this.postStore.put(post.getId(), post);
    }

    @Override
    public void save(Candidate candidate) {
    }

    @Override
    public Post findPostById(int id) {
        return this.postStore.get(id);
    }

    @Override
    public Candidate findCandidateById(int id) {
        return null;
    }

    @Override
    public void deleteCandidate(String id) {
    }

    @Override
    public void save(User user) {
    }

    @Override
    public   User findUserByDate(String name, String email) {
        return null;
    }
}
