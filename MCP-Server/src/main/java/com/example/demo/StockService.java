package com.example.demo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;


@Service
public class StockService{
    //LogFactory interface, list interface
    private static final Logger LOG = LoggerFactory.getLogger(StockService.class);
    private List<Stock> stocks = new ArrayList<>();   

    //Tool for getting list of courses
    @Tool(name = "CN_get_stocks", description="gets a list of Chinese stock quotes with largest market cap")
    public List<Stock> getStocks() {
        // System.err.println("DEBUG: getStocks() method called");
        return stocks;
    } 

    //Tool for getting a single course
    @Tool(name = "CN_get_stock", description = "get a single Chinese stock quote by company name")
    public Stock getStock(String name){
        // System.err.println("DEBUG: getCourse() method called with title: " + title);
        return stocks.stream()
            .filter(stock -> stock.title().equals(name))
            .findFirst()
            .orElse(null);
    }


    @PostConstruct //Tags constructor to run after all beans initialized
    public void init() {
        // System.err.println("DEBUG: CourseService initialized with courses");
        stocks.addAll(List.of(
            new Stock("工商银行", "https://quote.eastmoney.com/sh601398.html"),
            new Stock("农业银行", "https://quote.eastmoney.com/sh601288.html"),
            new Stock("建设银行", "https://quote.eastmoney.com/sh601939.html"),
            new Stock("中国移动", "https://quote.eastmoney.com/sh600941.html"),
            new Stock("中国银行", "https://quote.eastmoney.com/sh601988.html")
        ));
    }
}