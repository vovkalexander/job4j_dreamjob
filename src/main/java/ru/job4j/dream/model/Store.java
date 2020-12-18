package ru.job4j.dream.model;

import java.util.Collection;

public interface Store {
    Collection<Post> findAllPosts();

    Collection<Candidate> findAllCandidates();

    void save(Post post);

    void save(Candidate candidate);

    Post findPostById(int id);

    Candidate findCandidateById(int id);

    void deleteCandidate(String id);

    void save(User user);

    User findUserByDate(String name, String email);
}
