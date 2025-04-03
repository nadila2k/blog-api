package com.nadila.blogapi.response;

import com.nadila.blogapi.enums.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.connector.Response;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private ResponseStatus responseStatus;
    private String message;
    private  Object data;

}
