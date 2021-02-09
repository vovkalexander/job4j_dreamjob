package ru.job4j.dream.model;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store {
    private static final Logger LOG = LoggerFactory.getLogger(PsqlStore.class);

    private final BasicDataSource pool = new BasicDataSource();

    private PsqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(new FileReader("db.properties"))) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public Collection<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("Select * from post")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(it.getInt("id"), it.getString("name")));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return posts;
    }

    @Override
    public Collection<Candidate> findAllCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "Select * from candidate")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(new Candidate(it.getInt("id"), it.getString("name"),
                            it.getString("photoId"),
                            it.getInt("city_id")));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return candidates;
    }

    @Override
    public void save(Post post) {
        if (post.getId() == 0) {
            create(post);
        } else {
            update(post);
        }
    }

    @Override
    public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            create(candidate);
        } else {
            update(candidate);
        }
    }

    private Post create(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO post(name) VALUES (?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, post.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return post;
    }

    private Candidate create(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO candidate(name, photoId, city_id) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getPhotoId());
            ps.setInt(3, candidate.getCityId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return candidate;
    }

    private void update(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("UPDATE post Set name = ? where id = ?")) {
            ps.setString(1, post.getName());
            ps.setInt(2, post.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }

    }

    private void update(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "UPDATE candidate Set name = ?, photoId = ?, city_id = ? where id = ?")) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getPhotoId());
            ps.setInt(3, candidate.getCityId());
            ps.setInt(4, candidate.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }

    }

    @Override
    public Post findPostById(int id) {
        Post post = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("Select * from post where id = ?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                post = new Post(rs.getInt(id), rs.getString("name"));
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return post;
    }

    @Override
    public Candidate findCandidateById(int id) {
        Candidate candidate = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("Select * from candidate where id = ?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                candidate = new Candidate(rs.getInt(id), rs.getString("name"),
                        rs.getString("photoId"), rs.getInt("city_id"));
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return candidate;
    }

    @Override
    public void deleteCandidate(String id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("DElETE from candidate where id = ?")) {
            ps.setInt(1, Integer.valueOf(id));
            ps.executeUpdate();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }

    }

    @Override
    public void saveUser(User user) {
        if (user.getId() == 0) {
            this.createUser(user);
        } else {
            this.updateUser(user);
        }
    }

    private User createUser(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO users(name, email, password) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));

                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return user;
    }

    private void updateUser(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "UPDATE users Set name = ?, email = ?, password = ? where id = ?")) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public User findUserByDate(String email, String password) {
        User user = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * from users WHERE email = ? AND password = ?")) {
            ps.setString(1, email);
            ps.setString(2, password);
            ps.execute();
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    user = new User();
                    user.setId(it.getInt("id"));
                    user.setName(it.getString("name"));
                    user.setEmail(it.getString("email"));
                    user.setPassword(it.getString("password"));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return user;
    }

    @Override
    public   Collection<City> findAllCities() {
        Collection<City> cities = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             Statement  st = cn.createStatement()) {
            try (ResultSet it = st.executeQuery("SELECT * From cities")) {
                while (it.next()) {
                    cities.add(new City(it.getInt("id"), it.getString("city")));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return cities;
    }

}


