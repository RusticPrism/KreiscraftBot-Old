package de.rusticprism.kreiscraftbot.music.utils;

import com.google.gson.Gson;
import de.rusticprism.kreiscraftbot.token.Tokens;
import de.rusticprism.kreiscraftbot.utils.EmbedCreator;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.TimeUnit;

public class SpotifyMusicPlayer {
    private static final HttpClient client = HttpClient.newHttpClient();
    private static String accesstoken = "";
    public static SpotifyApi api = SpotifyApi.builder()
            .build();

    public static String getSongName(String link, TextChannel channel) {
        try {
            return api.getTrack(getTrackIdOfLink(link)).build().execute().getName();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            if (e.getMessage().equalsIgnoreCase("Invalid access token")) {
                accesstoken = getAccessToken(Tokens.SPOTIFY_CLIENT_ID.getToken(), Tokens.SPOTIFY_CLIEN_SECRET.getToken());
                api.setAccessToken(accesstoken);
                return getSongName(link, channel);
            } else {
                channel.sendMessageEmbeds(EmbedCreator.builder(e.getMessage().toUpperCase(), Color.RED).build())
                        .complete().delete().queueAfter(15, TimeUnit.SECONDS);
                return "NULL";
            }
        }
    }

    public static String getAccessToken(String clientid, String clientsecret) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://accounts.spotify.com/api/token"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=client_credentials&client_id=" + clientid + "&client_secret=" + clientsecret))
                .build();
        try {
            AuthTokenResponse response = new Gson().fromJson(client.send(request, HttpResponse.BodyHandlers.ofString()).body(), AuthTokenResponse.class);
            accesstoken = response.access_token;
            return accesstoken;
        } catch (IOException | InterruptedException e) {
            return "NULL";
        }
    }

    public static String getTrackIdOfLink(String link) {
        // https://open.spotify.com/track/37ZJ0p5Jm13JPevGcx4SkF?si=2495cbf120044cd6
        link = link.replace("https://open.spotify.com/track/", "");
        link = link.substring(0, link.indexOf("?"));
        return link;
    }

    private static class AuthTokenResponse {
        public String access_token;
        public String token_type;
        public String expires_in;
    }
}
