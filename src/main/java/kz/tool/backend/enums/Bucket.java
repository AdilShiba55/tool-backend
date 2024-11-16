package kz.tool.backend.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Bucket {
    PUBLIC("public", true),
    PRIVATE("private", false);

    private final String name;
    private final Boolean open;
}
