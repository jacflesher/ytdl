package com.flesher.ytdl.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flesher.ytdl.Clients.RapidClient;
import com.flesher.ytdl.Properties.DownloadResponse;
import com.flesher.ytdl.Properties.Properties;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RapidService {
    RapidClient rapidClient;
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public RapidService(RapidClient rapidClient){
        this.rapidClient = rapidClient;
    }

    public DownloadResponse downloadVideo(String vcode) throws Exception{
        DownloadResponse downloadResponse = new DownloadResponse();
        Response response = this.rapidClient.getYouTubeVideoDetails(vcode);
        int responseCode = response.code();
        String responseBody = response.body().string();
        response.close();
        if (responseCode == 200){
            Properties properties = mapper.readValue(responseBody, Properties.class);
            downloadResponse.setTitle(properties.getTitle());
            String getUrl = null;
            for (int i = 0; i < properties.getFormats().size(); i++){
                if (properties.getFormats().get(i).getMimeType().contains("mp4")){
                    if (properties.getFormats().get(i).getWidth() == 1280){
                        getUrl = properties.getFormats().get(i).getUrl();
                    }
                }
            }
            if (getUrl == null){
                for (int i = 0; i < properties.getFormats().size(); i++){
                    if (properties.getFormats().get(i).getMimeType().contains("mp4")){
                        if (properties.getFormats().get(i).getWidth() == 640){
                            getUrl = properties.getFormats().get(i).getUrl();
                        }
                    }
                }
            }
            if (getUrl == null){
                for (int i = 0; i < properties.getFormats().size(); i++){
                    if (properties.getFormats().get(i).getMimeType().contains("mp4")){
                        if (properties.getFormats().get(i).getWidth() == 480){
                            getUrl = properties.getFormats().get(i).getUrl();
                        }
                    }
                }
            }
            if (getUrl == null) {
                downloadResponse.setError("Error: Unable to match expected video format");
            } else {
                downloadResponse.setUrl(getUrl);
            }

        } else {
            downloadResponse.setError("Error: " + responseCode);
        }
        return downloadResponse;
    }
}
