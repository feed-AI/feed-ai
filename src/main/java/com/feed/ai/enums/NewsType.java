package com.feed.ai.enums;

import io.micrometer.common.util.StringUtils;

import java.util.Optional;

public enum NewsType {
    KANTIPUR,
    ONLINE_KHABAR,
    NEPALI_TIMES;

    public static Optional<NewsType> get(String value) {
        if (StringUtils.isEmpty(value)) return Optional.empty();
        try {
            return Optional.of(NewsType.valueOf(value.toUpperCase()));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}