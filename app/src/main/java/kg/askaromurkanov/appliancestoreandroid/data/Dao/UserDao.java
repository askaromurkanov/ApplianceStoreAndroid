package kg.askaromurkanov.appliancestoreandroid.data.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


import kg.askaromurkanov.appliancestoreandroid.data.models.User;

@Dao
public interface UserDao {
    @Query("SELECT * FROM User")
    List<User> getAll();

    @Query("SELECT * FROM User WHERE id = :id")
    User getById(long id);

    @Query("SELECT * FROM User WHERE email = :email")
    User getUser(String email);

    @Query("SELECT * FROM User WHERE email = :email AND password = :password")
    User getUser(String email, String password);

    @Query("SELECT MAX(id) FROM User")
    int getMaxId();

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);
    @Query("DELETE FROM User")
    public void cleanTable();
}
