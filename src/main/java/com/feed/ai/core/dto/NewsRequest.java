package com.feed.ai.core.dto;

import lombok.Data;

@Data
public class NewsRequest {
    private String news1;
    private String news2;
    private String similarityType;
}

