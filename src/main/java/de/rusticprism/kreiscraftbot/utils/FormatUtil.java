package de.rusticprism.kreiscraftbot.utils;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;

import java.util.List;

/**
 *
 * @author John Grosh <john.a.grosh@gmail.com>
 */
public class FormatUtil {
    
    public static String formatTime(long duration)
    {
        if(duration == Long.MAX_VALUE)
            return "LIVE";
        long seconds = Math.round(duration/1000.0);
        long hours = seconds/(60*60);
        seconds %= 60*60;
        long minutes = seconds/60;
        seconds %= 60;
        return (hours>0 ? hours+":" : "") + (minutes<10 ? "0"+minutes : minutes) + ":" + (seconds<10 ? "0"+seconds : seconds);
    }
        
    public static String progressBar(double percent)
    {
        StringBuilder str = new StringBuilder();
        for(int i=0; i<12; i++)
            if(i == (int)(percent*12))
                str.append("\uD83D\uDD18"); // 🔘
            else
                str.append("▬");
        return str.toString();
    }
    
    public static String volumeIcon(int volume)
    {
        if(volume == 0)
            return "\uD83D\uDD07"; // 🔇
        if(volume < 30)
            return "\uD83D\uDD08"; // 🔈
        if(volume < 70)
            return "\uD83D\uDD09"; // 🔉
        return "\uD83D\uDD0A";     // 🔊
    }
    
    public static String listOfTChannels(List<TextChannel> list, String query)
    {
        StringBuilder out = new StringBuilder(" Multiple text channels found matching \"" + query + "\":");
        for(int i=0; i<6 && i<list.size(); i++)
            out.append("\n - ").append(list.get(i).getName()).append(" (<#").append(list.get(i).getId()).append(">)");
        if(list.size()>6)
            out.append("\n**And ").append(list.size() - 6).append(" more...**");
        return out.toString();
    }
    
    public static String listOfVChannels(List<VoiceChannel> list, String query)
    {
        StringBuilder out = new StringBuilder(" Multiple voice channels found matching \"" + query + "\":");
        for (int i = 0; i < 6 && i < list.size(); i++)
            out.append("\n - ").append(list.get(i).getAsMention()).append(" (ID:").append(list.get(i).getId()).append(")");
        if (list.size() > 6)
            out.append("\n**And ").append(list.size() - 6).append(" more...**");
        return out.toString();
    }
    
    public static String listOfRoles(List<Role> list, String query)
    {
        StringBuilder out = new StringBuilder(" Multiple text channels found matching \"" + query + "\":");
        for(int i=0; i<6 && i<list.size(); i++)
            out.append("\n - ").append(list.get(i).getName()).append(" (ID:").append(list.get(i).getId()).append(")");
        if(list.size()>6)
            out.append("\n**And ").append(list.size() - 6).append(" more...**");
        return out.toString();
    }
    
    public static String filter(String input)
    {
        return input.replace("\u202E", "")
                .replace("@everyone", "@еveryone") // cyrillic letter e
                .replace("@here", "@hеre") // cyrillic letter e
                .trim();
    }
}
