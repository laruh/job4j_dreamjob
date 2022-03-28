package ru.job4j.dreamjob.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDBStore {
    private final BasicDataSource pool;

    public UserDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public User getUser(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getInt("id"), resultSet.getString("name"));
    }

    public User add(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement statement = cn.prepareStatement("INSERT INTO users(name) VALUES(?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, user.getName());
            statement.execute();
            try (ResultSet id = statement.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement statement =  cn.prepareStatement("SELECT * FROM users")
        ) {
            try (ResultSet it = statement.executeQuery()) {
                while (it.next()) {
                    users.add(getUser(it));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
}
