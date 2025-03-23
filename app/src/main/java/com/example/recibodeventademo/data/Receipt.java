package com.example.recibodeventademo.data;

import java.io.Serializable;
import java.util.Map;

public class Receipt implements Serializable {
    private Issuer issuer;
    private Client client;
    private Map<String, Integer> productCounts;
    private double total;

    public Receipt() {
    }

    public Issuer getIssuer() {
        return issuer;
    }

    public void setIssuer(Issuer issuer) {
        this.issuer = issuer;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Map<String, Integer> getProductCounts() {
        return productCounts;
    }

    public void setProductCounts(Map<String, Integer> productCounts) {
        this.productCounts = productCounts;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
