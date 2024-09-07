package com.feed.ai.news.service.impl;

import com.feed.ai.news.dto.NewsArticle;
import com.feed.ai.news.adapter.NewsCrawler;
import com.feed.ai.news.service.NewsService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsCrawler newsCrawler;

    public NewsServiceImpl(NewsCrawler newsCrawler) {
        this.newsCrawler = newsCrawler;
    }

    @Override
    public List<NewsArticle> getNews() {
        return newsCrawler.getNews();
    }
}
