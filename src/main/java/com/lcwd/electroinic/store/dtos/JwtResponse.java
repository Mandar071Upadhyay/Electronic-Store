package com.lcwd.electroinic.store.dtos;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class JwtResponse {
    private String jwtToken;
    private UserDTO user;
}
