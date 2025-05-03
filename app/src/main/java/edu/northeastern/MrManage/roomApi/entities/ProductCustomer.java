package edu.northeastern.MrManage.roomApi.entities;

public class ProductCustomer {
    private String productName;
    private Long customerId;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public ProductCustomer(String productName, Long customerId) {
        this.productName = productName;
        this.customerId = customerId;
    }

    public ProductCustomer() {
    }
}
