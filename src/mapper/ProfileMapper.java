package mapper;

import model.Profile;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ProfileMapper {

    @Select("SELECT COUNT(*) FROM users WHERE id = #{userId}")
    int checkUserExists(int userId);

    @Insert("INSERT INTO profiles (user_id, nama, foto, alamat, tanggal_lahir) " +
            "VALUES (#{userId}, #{nama}, #{foto}, #{alamat}, #{tanggalLahir})")
    void insertProfile(Profile profile);

    default void insertProfileWithValidation(Profile profile) throws Exception {

        if (checkUserExists(profile.getUserId()) == 0) {
            throw new Exception("User ID tidak valid, pastikan user_id ada di tabel users");
        }

        insertProfile(profile);
    }
}
