package model;

import java.util.List;

public interface UserDocumentMapper {
    List<UserDocument> selectAll();
    UserDocument selectById(int id);
    void insert(UserDocument userDocument);
    void update(UserDocument userDocument);
    void delete(int id);
}
