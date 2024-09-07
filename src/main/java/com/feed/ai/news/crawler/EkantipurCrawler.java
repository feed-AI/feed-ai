package com.feed.ai.news.crawler;

import com.feed.ai.news.dto.NewsArticle;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EkantipurCrawler {

    @Retryable(interceptor = "retryData", retryFor = Exception.class)
    public List<NewsArticle> scrapeNews() {
        List<NewsArticle> newsList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("https://ekantipur.com/news").get();
            Elements articles = doc.select("article.normal");

            for (Element article : articles) {
                String title = article.select("h2 a").text();
                String articleUrl = article.select("h2 a").attr("href");
                articleUrl = "https://ekantipur.com" + articleUrl;
                String author = article.select("div.author a").text();
                String image = article.select("div.image img").attr("data-src");

                Map<String, Object> content = fetchArticleContent(articleUrl);

                NewsArticle newsArticle = new NewsArticle(title, articleUrl, author, image, content);
                newsList.add(newsArticle);
            }
        } catch (Exception e) {
            log.error("Error to fetch data from article Url: {}", e.getMessage());
        }
        return newsList;
    }

    public Map<String, Object> fetchArticleContent(String articleUrl) {
        Map<String, Object> result = new HashMap<>();
        try {
            Document doc = Jsoup.connect(articleUrl).get();
            Element contentDiv = doc.selectFirst("div.description.current-news-block");

            if (contentDiv != null) {
                Elements paragraphs = contentDiv.select("p");
                String content = paragraphs.stream()
                        .map(Element::text)
                        .collect(Collectors.joining("\n"));

                Elements images = contentDiv.select("img[data-src]");
                List<String> imageUrls = images.stream()
                        .map(img -> img.attr("data-src"))
                        .toList();

                result.put("content", content);
                result.put("content-images", imageUrls);
            } else {
                log.info("Content not found for URL: {}", articleUrl);
            }
        } catch (Exception e) {
            log.error("Failed to fetch article content from URL: {}", articleUrl);
        }
        return result;
    }
}