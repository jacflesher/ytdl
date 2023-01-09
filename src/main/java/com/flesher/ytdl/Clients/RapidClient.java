package com.flesher.ytdl.Clients;

import com.flesher.ytdl.Properties.Creds;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RapidClient {
    Creds creds;

    @Autowired
    public RapidClient(Creds creds){
        this.creds = creds;
    }

    public Response getYouTubeVideoDetails(String videoCode) throws Exception{
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(creds.getRapidurl() + videoCode)
                .method("GET", null)
                .addHeader("X-RapidAPI-Host", creds.getRapidhost())
                .addHeader("X-RapidAPI-Key", creds.getRapidkey())
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }


}
