package com.hospital.model.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiDataResponse<T> {

    private Boolean success;

    private String message;

    private T data;
}