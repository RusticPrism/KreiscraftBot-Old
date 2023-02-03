package de.rusticprism.kreiscraftbot.utils;

import de.rusticprism.kreiscraftbot.KreiscraftBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.TimeZone;

public class EmbedCreator {
    private final EmbedBuilder builder;
    public static String getFooter() {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        final String date = format.format(new Date());
        return "1.0.0 - " + date;
    }
    public static MessageEmbed noPermission() {
        return createembed("You don´t have the Permission to perform that Command!", Color.red);
    }
    public static MessageEmbed createembed(String title, String description, Color color) {
        return new EmbedCreator(title,description,color).build();
    }
    public static MessageEmbed createembed(String description, Color color) {
        return new EmbedCreator(" ",description,color).build();
    }
    public static EmbedCreator builder(String title,Color color) {
        return new EmbedCreator(title,color);
    }
    public static EmbedCreator builder(String title,String description,Color color) {
        return new EmbedCreator(title,description,color);
    }
    private EmbedCreator(String title,String description,Color color) {
        this.builder = new EmbedBuilder();
        builder.setTitle(title);
        builder.setDescription(description);
        builder.setColor(color);
        builder.setFooter(getFooter(), KreiscraftBot.bot.getJDA().getSelfUser().getAvatarUrl());
    }
    private EmbedCreator(String title,Color color) {
        this.builder = new EmbedBuilder();
        builder.setTitle(title);
        builder.setColor(color);
        builder.setFooter(getFooter(), KreiscraftBot.bot.getJDA().getSelfUser().getAvatarUrl());
    }
    public EmbedCreator setTitle(String title, String icon) {
        builder.setTitle(title,icon);
        return this;
    }
    public EmbedCreator setAuthor(String author) {
        builder.setAuthor(author);
        return this;
    }
    public EmbedCreator setAuthor(String author,String icon) {
        builder.setAuthor(author, null,icon);
        return this;
    }
    public EmbedCreator setImage(String url) {
        builder.setImage(url);
        return this;
    }
    public EmbedCreator setThumbnail(String url) {
        builder.setThumbnail(url);
        return this;
    }
    public EmbedCreator setTimestamp(String timestamp) {
        builder.setTimestamp(Time.valueOf(timestamp).toInstant());
        return this;
    }
    public EmbedCreator setDescription(String description) {
        builder.setDescription(description);
        return this;
    }
    public EmbedCreator setTimeStampNow() {
        return setTimestamp(Instant.now().toString());
    }
    public MessageEmbed build() {
        return builder.build();
    }

}
