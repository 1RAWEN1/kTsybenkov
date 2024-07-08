package ru.ktsybenkov.tgBot.record;

import ru.ktsybenkov.tgBot.entity.Product;

public class ProductCount {
    private Product product;
    private int count;

    public ProductCount(Product product, int count) {
        this.product = product;
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void increaseCount(int count) {
        this.count += count;
    }
}
