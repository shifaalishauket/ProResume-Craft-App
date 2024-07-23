package com.pro.resume.craft.Api;

import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ChatGptApi {
    @POST("engines/text-davinci-003/completions")
    Call<ChatGptResponse> getChatGptResponse(
            @Header("Authorization") String apiKey,
            @Body Map<String, Object> body
    );

    @POST("chat/completions")
    Call<ChatGptResponse> getChatComGptResponse(
            @Header("Authorization") String apiKey,
            @Body JSONObject body
    );
}