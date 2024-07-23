package com.pro.resume.craft.Api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface OpenAiApi {
    @POST("v1/chat/completions")
    Call<OpenAiResponse> getChatCompletion(
            @Header("Authorization") String authHeader,
            @Body OpenAiRequest body
    );
}
