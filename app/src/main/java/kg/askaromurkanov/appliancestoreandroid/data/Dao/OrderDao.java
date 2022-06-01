package kg.askaromurkanov.appliancestoreandroid.data.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import kg.askaromurkanov.appliancestoreandroid.data.models.Order;

@Dao
public interface OrderDao {
    @Query("SELECT * FROM `Order`")
    List<Order> getAll();

    @Query("SELECT * FROM `Order` WHERE id = :id")
    Order getById(long id);

    @Query("SELECT * FROM `Order` WHERE productId = :productId")
    Order getByProductId(int productId);

    @Query("SELECT * FROM `Order` WHERE userId = :userId")
    Order getByUserId(int userId);

    @Query("SELECT * FROM `Order` WHERE userId = :userId")
    List<Order> getByUser(int userId);

    @Insert
    void insert(Order order);

    @Update
    void update(Order order);

    @Delete
    void delete(Order order);

    @Query("DELETE FROM `Order`")
    public void cleanTable();

    @Query("UPDATE `Order` SET active = :activeValue")
    void deliverOrder(int activeValue);

}
