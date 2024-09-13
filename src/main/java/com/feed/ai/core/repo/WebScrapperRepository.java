package com.feed.ai.core.repo;

import com.feed.ai.core.entity.WebScraper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebScrapperRepository extends JpaRepository<WebScraper, Long> {
}
