package com.example.demo;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;


@Service
public class CNStockService{

    //Stock class
    public record Stock(
        String code, 
        String url
    ){}

    //LogFactory interface, list interface
    private List<Stock> stocks = new ArrayList<>();   

    //Tool for getting list of courses
    @Tool(name = "CN_get_stocks", description="gets a list of Chinese stock quotes with largest market cap")
    public List<Stock> getStocks() {
        return stocks;
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