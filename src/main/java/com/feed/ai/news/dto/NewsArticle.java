package com.feed.ai.news.dto;

import lombok.Data;

import java.util.Map;

@Data
public class NewsArticle {
    private String title;
    private String url;
    private String author;
    private String image;
    private Map<String, Object> content;

    public NewsArticle(String title, String url, String author, String image, Map<String, Object> content) {
        this.title = title;
        this.url = url;
        this.author = author;
        this.image = image;
        this.content = content;
    }
}