package com.example.server.service;

import com.example.server.DTO.NewsDTO;
import com.example.server.DTO.WeatherResponseDTO;
import com.example.server.model.RootResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {
    private final WebClient client;
    @Value("${news.api_key}")
    private String apiKey;
    @Cacheable("newsCache")
    public List<NewsDTO> getNews(){
        RootResponse rootResponse;
        try {
            rootResponse = client.get()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("https")
                            .host("newsapi.org")
                            .path("/v2/top-headlines")
                            .queryParam("sources", "bbc-news")
                            .queryParam("pageSize", 3)
                            .queryParam("apiKey", apiKey)
                            .build())
                    .retrieve()
                    .bodyToMono(RootResponse.class)
                    .block();
        }catch (Exception e) {
            throw new RuntimeException("Failed request for news API " + e.getMessage(), e);
        }
        if (rootResponse == null){
            throw new RuntimeException("Failed to get news data");
        }
        List<NewsDTO> articles = rootResponse.getArticles().stream()
                .map(article -> NewsDTO.builder()
                        .url(article.getUrl())
                        .title(article.getTitle())
                        .urlToImage(article.getUrlToImage())
                        .build())
                .toList();
        return articles;
    }
}
