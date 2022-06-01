package kg.askaromurkanov.appliancestoreandroid.data.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Basket {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long userId;
    private long productId;

    public Basket(long userId, long productId){
        this.userId = userId;
        this.productId = productId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }
}
