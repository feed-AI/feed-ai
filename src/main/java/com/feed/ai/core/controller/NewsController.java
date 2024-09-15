package com.feed.ai.core.controller;

import com.feed.ai.core.dto.NewsRequest;
import com.feed.ai.core.dto.SimilarityResponse;
import com.feed.ai.core.service.NewsServiceFinder;
import org.springframework.web.bind.annotation.*;

@RestController
public class NewsController {

    private final NewsServiceFinder newsService;

    public NewsController(NewsServiceFinder newsService) {
        this.newsService = newsService;
    }

    @PostMapping("/compare-all")
    public SimilarityResponse compareNewsAll(@RequestBody NewsRequest newsRequest) {
        String news1 = newsRequest.getNews1();
        String news2 = newsRequest.getNews2();

        SimilarityResponse similarityResponse = newsService.calculateAllSimilarities(news1, news2);
        SimilarityResponse response = new SimilarityResponse();
        response.setCosineSimilarity(similarityResponse.getCosineSimilarity());
        response.setJaccardSimilarity(similarityResponse.getJaccardSimilarity());
        response.setLevenshteinSimilarity(similarityResponse.getLevenshteinSimilarity());
        return response;
    }
}
