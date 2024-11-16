package kz.tool.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record MinioPolicySettings(
        @JsonProperty("version")
        String version,
        @JsonProperty("statement")
        List<MinioPolicyStatement> statement
) {}
