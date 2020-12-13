package ru.job4j.dream.model;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemStore {
    private static final MemStore INST = new MemStore();
    private static final AtomicInteger POST_ID = new AtomicInteger(4);
    private static final AtomicInteger CANDIDATE_ID = new AtomicInteger(4);
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private MemStore()  {
        posts.put(1, new Post(1, "Junior Java Job", "for beginners", "2020-05-07"));
        posts.put(2, new Post(2, "Middle Java Job", "for experienced", "2020-03-29"));
        posts.put(3, new Post(3, "Senior Java Job", "for professional", "2019-03-29"));
        candidates.put(1, new Candidate(1, "Junior Java", ""));
        candidates.put(2, new Candidate(2, "Middle Java", ""));
        candidates.put(3, new Candidate(3, "Senior Java", ""));
    }

    public static MemStore instOf()  {
        return INST;
    }

    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }

    public void save(Post post) {
        if (post.getId() == 0) {
            post.setId(POST_ID.incrementAndGet());
        }
        posts.put(post.getId(), post);
    }

   public Post findById(int id) {
        return posts.get(id);
    }

    public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            candidate.setId(CANDIDATE_ID.incrementAndGet());
        }
        candidates.put(candidate.getId(), candidate);
    }

    public Candidate findId(int id) {
       return candidates.get(id);
   }
}
