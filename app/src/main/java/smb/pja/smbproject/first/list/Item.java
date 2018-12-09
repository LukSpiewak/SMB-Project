package smb.pja.smbproject.first.list;

import java.io.Serializable;

public class Item implements Serializable {

    private Integer id;
    private String productName;
    private Float price;
    private Integer amount;
    private Boolean bought;

    public Item() {
    }

    public Item(String productName, Float price, Integer amount, Boolean bought) {
        this.productName = productName;
        this.price = price;
        this.amount = amount;
        this.bought = bought;
    }

    public Item(Integer id, String productName, Float price, Integer amount, Boolean bought) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.amount = amount;
        this.bought = bought;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public boolean isBought() {
        return bought;
    }

    public void setBuy(boolean bought) {
        bought = bought;
    }
}
