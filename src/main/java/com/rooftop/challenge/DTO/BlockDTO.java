package com.rooftop.challenge.DTO;

import lombok.Getter;
import lombok.Setter;

public class BlockDTO {
    private @Getter
    @Setter String[] data;
    private @Getter
    @Setter Integer chunkSize;
    private @Getter
    @Setter Integer length;
}
