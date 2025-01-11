package controller;

import mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import model.User;
import util.PasswordHasher;

public class UserController {
    private final SqlSessionFactory sqlSessionFactory;

    public UserController(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public boolean verifyPassword(String email, String password) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            User user = mapper.findByEmail(email);
            if (user != null) {
                String storedPassword = user.getPassword();
                return PasswordHasher.checkPassword(password, storedPassword);
            }
            return false;
        }
    }

    public void updatePassword(String email, String newPassword) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            String hashedPassword = PasswordHasher.hashPassword(newPassword);
            mapper.updatePasswordByEmail(email, hashedPassword);
            session.commit();
        }
    }
} 