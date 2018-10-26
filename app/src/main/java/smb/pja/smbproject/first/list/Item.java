package smb.pja.smbproject.first.list;

import android.icu.util.CurrencyAmount;

public class Item {

    private String productName;
    private Float price;
    private Integer amount;
    private boolean isPay;

    public Item(String productName, Float price, Integer amount, boolean isPay) {
        this.productName = productName;
        this.price = price;
        this.amount = amount;
        this.isPay = isPay;
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

    public boolean isPay() {
        return isPay;
    }

    public void setPay(boolean pay) {
        isPay = pay;
    }
}
