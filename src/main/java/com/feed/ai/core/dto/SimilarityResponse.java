package com.feed.ai.core.dto;

import lombok.Data;

@Data
public class SimilarityResponse {
    private double cosineSimilarity;
    private double jaccardSimilarity;
    private double levenshteinSimilarity;
}
