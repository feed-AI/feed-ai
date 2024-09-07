package com.feed.ai.news.adapter;

import com.feed.ai.news.crawler.EkantipurCrawler;
import com.feed.ai.news.dto.NewsArticle;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EkantipurCrawlerAdapter implements NewsCrawler {

    private final EkantipurCrawler ekantipurCrawler;

    public EkantipurCrawlerAdapter(EkantipurCrawler ekantipurCrawler) {
        this.ekantipurCrawler = ekantipurCrawler;
    }

    @Override
    public List<NewsArticle> getNews() {
        return ekantipurCrawler.scrapeNews();
    }
}
