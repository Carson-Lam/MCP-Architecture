    //java -jar target/MCPServer-0.0.1-SNAPSHOT.jar
    package com.example.demo;
    import java.util.List;
    import java.util.Map;
    import java.util.stream.Collectors;

    import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
    import com.fasterxml.jackson.annotation.JsonProperty;

    import org.springframework.ai.tool.annotation.Tool;
    import org.springframework.stereotype.Service;
    import org.springframework.web.client.RestClient;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;


    @Service
    public class WeatherService {

        private static final String BASE_URL = "https://api.weather.gov";
        private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

        private final RestClient restClient;

        public WeatherService() {
            this.restClient = RestClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader("Accept", "application/geo+json")
                .defaultHeader("User-Agent", "WeatherApiClient/1.0 (your@email.com)")
                .build();
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Points(@JsonProperty("properties") Props properties){
            @JsonIgnoreProperties(ignoreUnknown = true)
            public record Props(@JsonProperty("forecast") String forecast) {
            }
        }

        public record Forecast(@JsonProperty("properties") Props properties){
            @JsonIgnoreProperties(ignoreUnknown = true)
            public record Props(@JsonProperty("periods") List<Period> periods) {
            }

            @JsonIgnoreProperties(ignoreUnknown = true)
            public record Period(@JsonProperty("number") Integer number, @JsonProperty("name") String name,
                    @JsonProperty("startTime") String startTime, @JsonProperty("endTime") String endTime,
                    @JsonProperty("isDaytime") Boolean isDayTime, @JsonProperty("temperature") Integer temperature,
                    @JsonProperty("temperatureUnit") String temperatureUnit,
                    @JsonProperty("temperatureTrend") String temperatureTrend,
                    @JsonProperty("probabilityOfPrecipitation") Map probabilityOfPrecipitation,
                    @JsonProperty("windSpeed") String windSpeed, @JsonProperty("windDirection") String windDirection,
                    @JsonProperty("icon") String icon, @JsonProperty("shortForecast") String shortForecast,
                    @JsonProperty("detailedForecast") String detailedForecast) {
            }
        }

        /* 
        // Returns detailed forecast including:
        // - Temperature and unit
        // - Wind speed and direction
        // - Detailed forecast description
        */
    @Tool(description = "Get weather forecast for a specific latitude/longitude")
    public String getWeatherForecastByLocation(
        double latitude,   // Latitude coordinate
        double longitude   // Longitude coordinate
    ) {
        logger.info("WeatherService.getWeatherForecastByLocation called with lat={}, lon={}", latitude, longitude);
        try {
            logger.info("Requesting /points/{},{} from weather.gov", latitude, longitude);
            var points = restClient.get()
                .uri("/points/{latitude},{longitude}", latitude, longitude)
                .retrieve()
                .body(Points.class);
            logger.info("Received points: {}", points);

            logger.info("Requesting forecast from: {}", points.properties().forecast());
            var forecast = restClient.get()
                .uri(points.properties().forecast())
                .retrieve()
                .body(Forecast.class);
            logger.info("Received forecast: {}", forecast);

            String periodsJson = forecast.properties().periods().stream().map(p -> {
                return String.format("""
                    {
                        "period": "%s",
                        "temperature": %s,
                        "temperatureUnit": "%s",
                        "windSpeed": "%s",
                        "windDirection": "%s",
                        "detailedForecast": "%s"
                    }""",
                    p.name(), p.temperature(), p.temperatureUnit(), p.windSpeed(), p.windDirection(), p.detailedForecast().replace("\"", "\\\""));
            }).collect(Collectors.joining(",\n"));

            String result = String.format("""
                {
                    "jsonrpc": "2.0",
                    "result": {
                        "periods": [
                            %s
                        ]
                    },
                    "id": 1
                }""", periodsJson);
            logger.info("Returning result from WeatherService");
            return result;
        } catch (Exception e) {
            logger.error("Exception in getWeatherForecastByLocation", e);
            throw e;
        }
    }
}