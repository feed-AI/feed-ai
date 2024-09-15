package com.feed.ai.core.service;

import com.feed.ai.core.dto.SimilarityResponse;

public interface NewsServiceFinder {
    SimilarityResponse calculateAllSimilarities(String content1, String content2);
}
