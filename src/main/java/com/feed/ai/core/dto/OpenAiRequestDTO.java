package com.feed.ai.core.dto;

import com.feed.ai.core.entity.OpenAiRequest;
import com.feed.ai.core.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenAiRequestDTO {

    private Long id;
    private String requestData;
    private String responseData;
    private LocalDateTime timestamp;
    private Long userId;

    public static OpenAiRequestDTO fromEntity(OpenAiRequest openAiRequest) {
        return OpenAiRequestDTO.builder()
                .id(openAiRequest.getId())
                .requestData(openAiRequest.getRequestData())
                .responseData(openAiRequest.getResponseData())
                .timestamp(openAiRequest.getTimestamp())
                .userId(openAiRequest.getUser() != null ? openAiRequest.getUser().getId() : null)
                .build();
    }

    public OpenAiRequest toEntity(User user) {
        return OpenAiRequest.builder()
                .id(this.id)
                .requestData(this.requestData)
                .responseData(this.responseData)
                .timestamp(this.timestamp != null ? this.timestamp : LocalDateTime.now())
                .user(user)
                .build();
    }
}
