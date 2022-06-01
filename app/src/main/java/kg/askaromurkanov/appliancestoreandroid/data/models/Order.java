package kg.askaromurkanov.appliancestoreandroid.data.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Order {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String address;
    private String extraInfo;
    private long productId;
    private long userId;
    private int active;

    public Order(String name, String address, String extraInfo, long productId, long userId, int active) {
        this.name = name;
        this.address = address;
        this.extraInfo = extraInfo;
        this.productId = productId;
        this.userId = userId;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}
