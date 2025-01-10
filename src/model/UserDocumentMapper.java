package model;

import java.util.List;

public interface UserDocumentMapper {
    List<UserDocument> selectAll();
    UserDocument selectByKtp(String ktpNumber);
    void insert(UserDocument userDocument);
    void update(UserDocument userDocument);
    void delete(String ktpNumber);
}
