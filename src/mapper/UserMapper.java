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

        // Periksa apakah NIK KTP ada di database
        @Select("SELECT COUNT(*) FROM users WHERE ktp = #{ktp}")
        int checkKTPExists(String ktp);

    // Update password berdasarkan NIK KTP
    @Update("UPDATE users SET password = #{password} WHERE ktp = #{ktp}")
    void updatePasswordByKTP(@Param("ktp") String ktp, @Param("password") String password);

    @Update("UPDATE users SET password = #{password} WHERE email = #{email}")
    void updatePasswordByEmail(@Param("email") String email, @Param("password") String password);




}



