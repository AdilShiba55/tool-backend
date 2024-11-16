package kz.tool.backend.dto;

import kz.tool.backend.enums.Bucket;
import lombok.Builder;

import java.util.Date;
import java.util.UUID;

@Builder
public record FileInfoContent(
        UUID id,
        Bucket bucket,
        Date dtCreate,
        byte[] content
) {}
