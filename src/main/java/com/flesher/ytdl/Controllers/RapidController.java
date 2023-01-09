package com.flesher.ytdl.Controllers;

import com.flesher.ytdl.Clients.GoogleRecaptchaClient;
import com.flesher.ytdl.Properties.DownloadRequest;
import com.flesher.ytdl.Properties.DownloadResponse;
import com.flesher.ytdl.Services.RapidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RapidController {
    RapidService rapidService;
    GoogleRecaptchaClient googleRecaptchaClient;

    @Autowired
    public RapidController(RapidService rapidService, GoogleRecaptchaClient googleRecaptchaClient){
        this.rapidService = rapidService;
        this.googleRecaptchaClient = googleRecaptchaClient;
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping(path = "/download/{vcode}/{captcha}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<DownloadResponse> download(@PathVariable String vcode, @PathVariable String captcha) {
        DownloadResponse downloadResponse;
        HttpStatus httpStatus;
        String validate = validateInput(vcode);
        if (googleRecaptchaClient.validCaptcha(captcha) == false){
            downloadResponse = DownloadResponse.builder()
                    .error("CAPTCHA validation failed. Please try again.")
                    .build();
            httpStatus = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(downloadResponse, httpStatus);
        }
        if (validate.equals("OK")){
            try{
                DownloadResponse dr = this.rapidService.downloadVideo(vcode);
                httpStatus = HttpStatus.ACCEPTED;
                downloadResponse = DownloadResponse.builder()
                        .title(dr.getTitle())
                        .url(dr.getUrl())
                        .build();
            } catch (Exception e){
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                downloadResponse = DownloadResponse.builder()
                        .error(e.getMessage())
                        .build();
            }
        } else {
            httpStatus = HttpStatus.BAD_REQUEST;
            downloadResponse = DownloadResponse.builder()
                    .error(validate)
                    .build();
        }
        return new ResponseEntity<>(downloadResponse, httpStatus);
    }

    private String validateInput(String vcode){
        if (!vcode.matches("[a-zA-Z0-9-?_?]{10,12}")){
            return "Invalid Video Code, should look similar to \"Uq1ckViVlS0\" at the end of a YouTube video URL";
        } else {
            return "OK";
        }
    }
}
