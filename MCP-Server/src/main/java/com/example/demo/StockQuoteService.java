
//java -jar target/MCPServer-0.0.1-SNAPSHOT.jar
package com.example.demo;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class StockQuoteService {

    private final String BASE_URL = "https://financialmodelingprep.com/stable/quote";
    private final RestClient restClient;
    private final String FMP_API_KEY;
    

    public StockQuoteService(@Value("${FMP_API_KEY}") String apiKey) {
        this.FMP_API_KEY = apiKey;
        this.restClient = RestClient.builder()
            .baseUrl(BASE_URL)
            .build();
    }

    /* 
    // Returns detailed stock Quote including:
    // - Symbol, Name, Price
    // - Change percentage and amount
    // - Volume and price range
    */
    @Tool(description = "Get stock quote for a specific symbol")
    public String getStockQuoteBySymbol(String symbol) {
        String fullUrl = String.format("?symbol=%s&apikey=%s", symbol, FMP_API_KEY);

        try {
            return restClient.get()
                .uri(fullUrl)
                .retrieve()
                .body(String.class);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching stock quote: " + e.getMessage());
        }
    }
}