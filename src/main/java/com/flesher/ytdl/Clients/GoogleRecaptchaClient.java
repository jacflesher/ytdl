package com.flesher.ytdl.Clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flesher.ytdl.Properties.Creds;
import com.flesher.ytdl.Properties.RecaptchaProperties;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GoogleRecaptchaClient {
    ObjectMapper mapper = new ObjectMapper();
    Creds creds;

    @Autowired
    public GoogleRecaptchaClient(Creds creds){
        this.creds = creds;
    }

    public Boolean validCaptcha(String captchaToken){

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(
                mediaType, 
                "secret=" + creds.getGooglerecaptchakey() + "&response=" + captchaToken);
        Request request = new Request.Builder()
                .url("https://www.google.com/recaptcha/api/siteverify")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response;
        try {
            response = client.newCall(request).execute();
            String responseBody = response.body().string();
            log.info("responseBody: " + responseBody);
            int responseCode = response.code();
            if (responseCode == 200){
                RecaptchaProperties recaptchaProperties = mapper.readValue(responseBody, RecaptchaProperties.class);
                if (recaptchaProperties.getSuccess() == true){
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
}
