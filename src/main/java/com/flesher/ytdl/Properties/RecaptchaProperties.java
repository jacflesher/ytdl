package com.flesher.ytdl.Properties;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecaptchaProperties {

    @JsonProperty("success")
    private Boolean success;

}
