package com.lcwd.electroinic.store.dtos;

import lombok.*;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageResponse {
private String fileName;
    private String message;
    private boolean success;
    private HttpStatus httpStatus;
}
