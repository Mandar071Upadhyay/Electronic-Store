package com.lcwd.electroinic.store.dtos;

import lombok.*;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponseMessage {
private String message;
private boolean success;
private HttpStatus httpStatus;
}
