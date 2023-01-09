package com.flesher.ytdl.Properties;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Properties {

    @JsonProperty("title")
    private String title;

    @JsonProperty("formats")
    private List<Formats> formats;


}
