package com.feed.ai.news.service;

import com.feed.ai.news.dto.NewsArticle;
import java.util.List;

public interface NewsService {
    List<NewsArticle> getNews();
}
