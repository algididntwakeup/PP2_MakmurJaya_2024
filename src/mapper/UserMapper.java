package mapper;

import model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    // Insert User
    @Insert("INSERT INTO users (email, password) VALUES (#{email}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUser(User user);

    // Cari User berdasarkan email
    @Select("SELECT * FROM users WHERE email = #{email}")
    User findByEmail(@Param("email") String email);

    // Update password berdasarkan email
    @Update("UPDATE users SET password = #{password} WHERE email = #{email}")
    void updatePasswordByEmail(@Param("email") String email, @Param("password") String password);
}



