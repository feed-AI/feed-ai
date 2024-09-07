package com.feed.ai.news.adapter;

import com.feed.ai.news.dto.NewsArticle;

import java.util.List;

public interface NewsCrawler {
    List<NewsArticle> getNews();
}