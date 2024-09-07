package com.feed.ai.dto;

import com.feed.ai.entity.User;
import com.feed.ai.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String username;
    private String email;
    private Role role;
    private List<WebScraperDTO> webScrapers;
    private List<OpenAiRequestDTO> openAiRequests;

    public static UserDTO fromEntity(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .webScrapers(user.getWebScrapers() != null ?
                        user.getWebScrapers().stream()
                                .map(WebScraperDTO::fromEntity)
                                .collect(Collectors.toList())
                        : null)
                .openAiRequests(user.getOpenAiRequests() != null ?
                        user.getOpenAiRequests().stream()
                                .map(OpenAiRequestDTO::fromEntity)
                                .collect(Collectors.toList())
                        : null)
                .build();
    }

    public User toEntity(User user) {
        return User.builder()
                .id(this.id)
                .username(this.username)
                .email(this.email)
                .password(null) //handle this (logic to handle this)
                .role(this.role)
                .webScrapers(this.webScrapers != null ?
                        this.webScrapers.stream()
                                .map(dto -> dto.toEntity(user))
                                .collect(Collectors.toList())
                        : null)
                .openAiRequests(this.openAiRequests != null ?
                        this.openAiRequests.stream()
                                .map(dto -> dto.toEntity(user))
                                .collect(Collectors.toList())
                        : null)
                .build();
    }
}
