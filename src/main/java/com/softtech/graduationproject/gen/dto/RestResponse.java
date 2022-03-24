package com.softtech.graduationproject.gen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestResponse<T> implements Serializable {

    private T data;
    private Date responseDate;
    private boolean isSuccess;
    private String message;

    public RestResponse(T data, boolean isSuccess) {
        this.data = data;
        this.isSuccess = isSuccess;
        responseDate = new Date();
    }

    public static <T> RestResponse<T> of(T t) {
        RestResponse<T> response = new RestResponse<>(t, true);
        response.message = "Successful";
        return response;
    }

    public static <T> RestResponse<T> error(T t) {
        return new RestResponse<>(t, false);
    }

    public static <T> RestResponse<T> empty() {
        RestResponse<T> response = new RestResponse<>(null, true);
        response.message = "Successful";
        return response;
    }
}
