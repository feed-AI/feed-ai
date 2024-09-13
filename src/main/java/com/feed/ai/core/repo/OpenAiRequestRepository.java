package com.feed.ai.core.repo;

import com.feed.ai.core.entity.OpenAiRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpenAiRequestRepository extends JpaRepository<OpenAiRequest, Long> {
}
