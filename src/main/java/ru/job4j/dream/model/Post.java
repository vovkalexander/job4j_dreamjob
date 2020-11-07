package ru.job4j.dream.model;
import java.time.LocalDate;
import java.util.Objects;

public class Post {
    private int id;
    private String name;
    private String description;
    private LocalDate created;

    public Post(int id, String name, String description, String date)  {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created = LocalDate.parse(date);
    }

    public String getDescription() {

        return description;
    }

    public LocalDate getCreated() {
        return created;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return id == post.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

