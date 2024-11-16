package kz.tool.backend.dto;

import lombok.Builder;

@Builder
public record FileConvertResponse(
        byte[] content
) {}
