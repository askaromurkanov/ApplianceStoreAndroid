package kg.askaromurkanov.appliancestoreandroid.data.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import kg.askaromurkanov.appliancestoreandroid.data.models.Basket;

@Dao
public interface BasketDao {
    @Query("SELECT * FROM Basket")
    List<Basket> getAll();

    @Query("SELECT * FROM Basket WHERE id = :id")
    Basket getById(long id);


    @Query("SELECT productId FROM Basket WHERE userId = :userId")
    List<Integer> getProducts(int userId);

    @Query("SELECT * FROM Basket WHERE userId = :userId")
    List<Basket> getBasket(int userId);

    @Insert
    void insert(Basket basket);

    @Delete
    void delete(Basket basket);

    @Query("DELETE FROM Basket")
    void cleanTable();

    @Query("DELETE FROM Basket WHERE productId = :productId")
    void deleteByProductId(int productId);
}
