package ru.job4j.dream.model;

import java.util.Objects;

public class Candidate {
    private int id;
    private String name;
    private String photoId;

    public Candidate(int id, String name, String photoId) {
        this.id = id;
        this.name = name;
        this.photoId = photoId;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
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
        Candidate candidate = (Candidate) o;
        return id == candidate.id && name.equals(candidate.name)
                && photoId.equals(candidate.photoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
