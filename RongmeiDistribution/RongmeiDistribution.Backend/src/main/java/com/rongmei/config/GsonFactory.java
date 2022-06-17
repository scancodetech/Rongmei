package com.rongmei.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rongmei.config.jsonAdapter.SpringfoxJsonToGsonAdapter;
import springfox.documentation.spring.web.json.Json;

public class GsonFactory {
    public static Gson get() {
        return new GsonBuilder()
                .registerTypeAdapter(Json.class, new SpringfoxJsonToGsonAdapter())
                .create();
    }
}
