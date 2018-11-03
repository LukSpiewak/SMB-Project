package smb.pja.smbproject.first.list;

import java.io.Serializable;

public class Item implements Serializable {

    private Integer id;
    private String productName;
    private Float price;
    private Integer amount;
    private boolean isBought;

    public Item(String productName, Float price, Integer amount, boolean isBought) {
        this.productName = productName;
        this.price = price;
        this.amount = amount;
        this.isBought = isBought;
    }

    public Item(Integer id, String productName, Float price, Integer amount, boolean isBought) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.amount = amount;
        this.isBought = isBought;
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
        return isBought;
    }

    public void setBuy(boolean bought) {
        isBought = bought;
    }
}
