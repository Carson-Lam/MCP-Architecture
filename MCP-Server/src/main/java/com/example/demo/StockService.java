package com.example.demo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class StockService{

    //Stock class
    public record Stock(
        String code, 
        String url
    ){}

    //LogFactory interface, list interface
    private static final Logger LOG = LoggerFactory.getLogger(StockService.class);
    private List<Stock> stocks = new ArrayList<>();   

    //Tool for getting list of courses
    @Tool(name = "CN_get_stocks", description="gets a list of Chinese stock quotes with largest market cap")
    public List<Stock> getStocks() {
        return stocks;
    } 

    //Tool for getting a single course
    @Tool(name = "CN_get_stock", description = "get a single Chinese stock quote by stock code")
    public Optional<Stock> getStock(String name){
        return stocks.stream()
            .filter(stock -> stock.code().equals(name))
            .findFirst();
    }

    @PostConstruct //Tags constructor to run after all beans initialized
    public void init() {
        stocks.addAll(List.of(
            new Stock("601398", "https://quote.eastmoney.com/sh601398.html"),
            new Stock("601288", "https://quote.eastmoney.com/sh601288.html"),
            new Stock("601939", "https://quote.eastmoney.com/sh601939.html"),
            new Stock("600941", "https://quote.eastmoney.com/sh600941.html"),
            new Stock("601988", "https://quote.eastmoney.com/sh601988.html")
        ));
    }
}