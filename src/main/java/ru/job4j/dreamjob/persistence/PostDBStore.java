package ru.job4j.dreamjob.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostDBStore {
    private final BasicDataSource pool;

    public PostDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Post getPost(ResultSet resultSet) throws SQLException {
        return new Post(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getTimestamp("created").toLocalDateTime().withNano(0),
                resultSet.getBoolean("visible"),
                new City(resultSet.getInt("c_Id"), resultSet.getString("c_Name")));

    }

    public Post add(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "INSERT INTO post(name, description, created, visible, city_id)"
                             + " VALUES (?, ?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(post.getCreated()));
            ps.setBoolean(4, post.isVisible());
            ps.setInt(5, post.getCity().getId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    public void update(Post post) {
        String query = new StringBuilder()
                .append("UPDATE post SET name = ?, description = ?, visible = ?, ")
                .append("city_id = ? WHERE id = ?").toString();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(query)
        ) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setBoolean(3, post.isVisible());
            ps.setInt(4, post.getCity().getId());
            ps.setInt(5, post.getId());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        String query = new StringBuilder()
                .append("SELECT p.id, p.name, p.description, p.created, p.visible, ")
                .append("c.id as c_Id, c.name as c_Name ")
                .append("FROM post as p ")
                .append("JOIN city as c ON p.city_id = c.id ")
                .append("ORDER BY p.id").toString();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(query)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(getPost(it));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    public void deleteFrom() {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("TRUNCATE TABLE post RESTART IDENTITY")) {
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Post findById(int id) {
        String query = new StringBuilder()
                .append("SELECT p.id, p.name, p.description, p.created, p.visible, ")
                .append("c.id as c_Id, c.name as c_Name ")
                .append("FROM post as p ")
                .append("JOIN city as c ON p.city_id = c.id ")
                .append("WHERE p.id = ?").toString();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(query)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return getPost(it);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
