package com.feed.ai.core.dto;

import com.feed.ai.core.entity.User;
import com.feed.ai.core.entity.WebScraper;
import com.feed.ai.core.enums.NewsType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebScraperDTO {

    private Long id;
    private String websiteUrl;
    private NewsType newsType;
    private String scrapedData;
    private LocalDateTime timestamp;
    private Long userId;

    public static WebScraperDTO fromEntity(WebScraper webScraper) {
        return WebScraperDTO.builder()
                .id(webScraper.getId())
                .websiteUrl(webScraper.getWebsiteUrl())
                .newsType(webScraper.getNewsType())
                .scrapedData(webScraper.getScrapedData())
                .timestamp(webScraper.getTimestamp())
                .userId(webScraper.getUser() != null ? webScraper.getUser().getId() : null)
                .build();
    }

    public WebScraper toEntity(User user) {
        return WebScraper.builder()
                .id(this.id)
                .websiteUrl(this.websiteUrl)
                .newsType(this.newsType)
                .scrapedData(this.scrapedData)
                .timestamp(this.timestamp != null ? this.timestamp : LocalDateTime.now())
                .user(user)
                .build();
    }
}
