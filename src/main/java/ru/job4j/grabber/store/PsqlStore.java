package ru.job4j.grabber.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.grabber.model.Post;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable {

    private static final Logger LOG = LoggerFactory.getLogger(PsqlStore.class.getName());

    private Connection cn;

    /**
     * The method creates a connection from the property resources,
     * and passes this Connection instance to the global variable cn.
     */
    public PsqlStore(Properties config) {
        try {
            Class.forName(config.getProperty("driver-class-name"));
            cn = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Method writes to the database.
     * @param post - data model instance.
     */
    @Override
    public Post save(Post post) {
        try (PreparedStatement ps = cn.prepareStatement(
                "insert into post (name, text, link, created) values (?, ?, ?, ?);",
                Statement.RETURN_GENERATED_KEYS
        )) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getText());
            ps.setString(3, post.getLink());
            ps.setTimestamp(4, post.getCreated());
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                                if (generatedKeys.next()) {
                                    post.setId(generatedKeys.getString(1));
                                }
                            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    /**
     * Method selects all records in the database.
     * @return - list of posts.
     */
    @Override
    public List<Post> getAll() {
        List<Post> result = new ArrayList<>();
        try (Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery("select * from post")) {
            while (rs.next()) {
                Post post = new Post(
                        rs.getString("name"),
                        rs.getString("text"),
                        rs.getString("link"),
                        rs.getTimestamp("created")
                );
                post.setId(rs.getString("id"));
                result.add(post);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Method selects record by unique id in the database.
     * @param id
     * @return
     */
    @Override
    public Post findById(String id) {
        Post result = null;
        try (PreparedStatement st = cn.prepareStatement("select * from post where id = ?")) {
            st.setInt(1, Integer.parseInt(id));
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    result = new Post(
                            rs.getString("name"),
                            rs.getString("text"),
                            rs.getString("link"),
                            rs.getTimestamp("created")
                    );
                    result.setId(id);
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public void close() throws Exception {
        if (cn != null) {
            cn.close();
        }
    }

    public static void main(String[] args) throws IOException {
        try (InputStream in = PsqlStore.class.getClassLoader().getResourceAsStream("grabber.properties")) {
            Properties config = new Properties();
            config.load(in);
            PsqlStore psqlStore = new PsqlStore(config);
            Post post = new Post("name", "text", "link", new Timestamp(199751112231L));
            System.out.println(psqlStore.save(post));
            for (Post currentPost : psqlStore.getAll()) {
                System.out.println(currentPost);
            }
            System.out.println(psqlStore.findById("27"));
        }
    }
}
