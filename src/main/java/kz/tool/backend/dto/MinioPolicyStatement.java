package kz.tool.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record MinioPolicyStatement(
        @JsonProperty("Effect")
        String effect,
        @JsonProperty("Principal")
        String principal,
        @JsonProperty("Action")
        List<String> action,
        @JsonProperty("Resource")
        List<String> resource
) {}
