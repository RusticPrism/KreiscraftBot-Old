package de.rusticprism.kreiscraftbot.utils;

import de.rusticprism.kreiscraftbot.KreiscraftBot;
import de.rusticprism.kreiscraftbot.config.ConfigManager;
import de.rusticprism.kreiscraftbot.config.StempelConfig;
import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageProcessor;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.List;

public class StempelUtil {
    public static File getPodest(String one,String second, String third) {
        File file;
        try {
            file = new File(KreiscraftBot.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        file = new File(file.getParentFile().getPath() + "/Podest.png");
        System.out.println(file.getAbsolutePath());
        ImagePlus image = IJ.openImage(file.getParentFile().getPath() + "/Podest.png");
        ImageProcessor ip = image.getProcessor();
        ip.setColor(Color.BLACK);
        ip.setFont(new Font("Arimo",Font.BOLD,22));
        ip.drawString(one,300 - image.getImage().getGraphics().getFontMetrics().stringWidth(one) / 2 - 20,260);
        ip.drawString(second,160 - image.getImage().getGraphics().getFontMetrics().stringWidth(second) / 2 - 20,280);
        ip.drawString(third,460 - image.getImage().getGraphics().getFontMetrics().stringWidth(third) / 2 - 20,300);
        image.setProcessor(ip);
        BufferedImage image1 = image.getBufferedImage();
        File file1 = new File(file.getParentFile() + "/output.png");
        try {
            ImageIO.write(image1,"png",file1);
        } catch (IOException e) {
            System.out.println("Error: Couldn't write Image");
        }
        return file1;
    }
    public static List<String> getFirst(int amount, Guild guild) {
        StempelConfig config = ConfigManager.getConfig(StempelConfig.class);
        HashMap<Member, Integer> stempel = new HashMap<>();
        for (Member user : guild.getMembers()) {
            if(user.getUser().isBot() || user.getUser().isSystem()) {
                continue;
            }
            stempel.put(user, config.getStempel(guild, user.getId()));
        }
        stempel = sortMap(stempel);
        return stempel.keySet().stream().limit(amount).map(member -> member.getNickname() == null || member.getNickname().length() > 10 ? member.getUser().getName() : member.getNickname()).toList();
    }
    public static List<Member> getFirstMember(int amount, Guild guild) {
        StempelConfig config = ConfigManager.getConfig(StempelConfig.class);
        HashMap<Member, Integer> stempel = new HashMap<>();
        for (Member user : guild.getMembers()) {
            if(user.getUser().isBot() || user.getUser().isSystem()) {
                continue;
            }
            stempel.put(user, config.getStempel(guild, user.getId()));
        }
        stempel = sortMap(stempel);
        return stempel.keySet().stream().limit(amount).toList();
    }
    public static HashMap<Member, Integer> sortMap(HashMap<Member, Integer> map) {
        LinkedHashMap<Member, Integer> sortedMap = new LinkedHashMap<>();
        List<Integer> list = new ArrayList<>();
        for (Map.Entry<Member, Integer> entry : map.entrySet()) {
            list.add(entry.getValue());
        }
        Collections.sort(list);
        Collections.reverse(list);
        for (int num : list) {
            for (Map.Entry<Member, Integer> entry : map.entrySet()) {
                if (entry.getValue().equals(num)) {
                    sortedMap.put(entry.getKey(), num);
                }
            }
        }
        return sortedMap;
    }
}
