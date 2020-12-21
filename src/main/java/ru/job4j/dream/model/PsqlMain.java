package ru.job4j.dream.model;

public class PsqlMain {
    public static void main(String[] args) {
        Store store = PsqlStore.instOf();
        store.save(new Post(0, "Java Job"));
        for (Post post : store.findAllPosts()) {
            System.out.println(post.getId() + " " + post.getName());
        }
        Post post = store.findPostById(1);
        post.setName("new Java Job");
        store.save(post);
        System.out.println(post.getId() + " " + post.getName());
        store.save(new Candidate(0, "Junior Java", ""));
        for (Candidate candidate : store.findAllCandidates()) {
            System.out.println(candidate.getId() + " " + candidate.getName() + " " + candidate.getPhotoId());
        }
        User user = new User();
        user.setName("Pupkin");
        user.setEmail("Pupa@gmail.com");
        user.setPassword("qwerty");
        store.save(user);
        System.out.println(store.findUserByDate("Pupa@gmail.com", "qwerty").getName());
    }
}
