package com.quipu.stock.dto;

public class StockPriceDTO {
    private String uuid;
    private String stockName;
    private String price;


    public StockPriceDTO() {
    }

    public StockPriceDTO(String uuid, String stockName, String price) {
        this.uuid = uuid;
        this.stockName = stockName;
        this.price = price;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
