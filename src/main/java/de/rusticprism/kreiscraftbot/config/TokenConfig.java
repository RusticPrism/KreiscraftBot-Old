package de.rusticprism.kreiscraftbot.config;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.rusticprism.kreiscraftbot.utils.Tokens;

import java.io.*;
import java.util.Arrays;

public class TokenConfig {
    private Tokens tokens;

    public TokenConfig() {
        try {
            load();
        } catch (IOException e) {
            System.out.println("Error Reading Token Config");
        }
    }

    public void load() throws IOException {
        File file = new File(getClass().getResource("/tokens.json").getFile());
        Gson gson = new Gson();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder lines = new StringBuilder();
        reader.lines().forEach(lines::append);
        this.tokens = gson.fromJson(String.valueOf(lines), Tokens.class);
    }

    public String getToken(String key) {
        return key.equalsIgnoreCase("bottoken") ? tokens.getBottoken() : tokens.getDevtoken();
    }
}
