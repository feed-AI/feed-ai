package com.feed.ai.enums;

import io.micrometer.common.util.StringUtils;

import java.util.Optional;

public enum Role {
    ADMIN,
    MODERATOR;

    public static Optional<Role> get(String value) {
        if (StringUtils.isEmpty(value)) return Optional.empty();
        try {
            return Optional.of(Role.valueOf(value.toUpperCase()));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
