package com.nadila.blogapi.controller;

import com.nadila.blogapi.enums.ResponseStatus;
import com.nadila.blogapi.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/openAi")
public class OpenAiController {

    private final OpenAiChatModel openAiChatModel;

    @GetMapping
    public ResponseEntity<ApiResponse> getAiPost(@RequestBody String message){
        try {
            String response = openAiChatModel.call(message);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ResponseStatus.SUCCESS,"Answer get",response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(ResponseStatus.ERROR,e.getMessage(),null));
        }
    }

}
