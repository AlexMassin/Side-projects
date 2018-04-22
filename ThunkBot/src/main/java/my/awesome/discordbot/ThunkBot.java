package my.awesome.discordbot;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.ImplDiscordAPI;
import de.btobastian.javacord.Javacord;
import de.btobastian.javacord.entities.Channel;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.entities.message.MessageHistory;
import de.btobastian.javacord.entities.message.embed.EmbedBuilder;
import de.btobastian.javacord.entities.message.impl.ImplMessageHistory;
import de.btobastian.javacord.listener.message.MessageCreateListener;
import de.btobastian.javacord.listener.message.TypingStartListener;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.TimeZone;


public class ThunkBot {
    private static boolean annoy = false;
    private static String prefix = "-";


    public static void main(String[] args) throws FileNotFoundException{
        Scanner in = new Scanner(new File("input1.txt"));
        final DiscordAPI api = Javacord.getApi(in.nextLine(), true);
        api.connectBlocking();

        api.setGame("Type " + prefix + " help for commands");
        api.registerListener(new MessageCreateListener() {

            public void onMessageCreate(DiscordAPI discordAPI, Message message) {
                if (!message.getAuthor().isBot()) {
                    if (message.getContent().equalsIgnoreCase(prefix + "ping")) {
                        message.reply(message.getAuthor().getMentionTag() + " Pong! :ping_pong:");
                        System.out.println(api.getUsers());
                    }

                    if (message.getContent().startsWith(prefix + "prefix")) {
                        String newPrefix = message.getContent().substring(prefix.length() + 6);
                        message.reply("Prefix has been changed from " + prefix + " to " + newPrefix);
                        prefix = newPrefix.trim();
                        api.setGame("Type " + prefix + " help for commands");

                    }

                    if (message.getContent().equalsIgnoreCase(prefix + "help")) {
                        EmbedBuilder e = new EmbedBuilder();
                        e.setTitle("Help");
                        e.setColor(Color.RED);
                        boolean changeInline = false;
                        e.addField(prefix + "ping", "Checks the latency of the bot", changeInline);
                        e.addField(prefix + "avatar", "Gets avatar of a specific user", changeInline);
                        e.addField(prefix + "annoyOn", "Turns Annoy Mode On :joy:", changeInline);
                        e.addField(prefix + "annoyOff", "Turns Annoy Mode Off :cry:", changeInline);
                        e.addField(prefix + "prefix", "Changes the prefix of the bot's commands", changeInline);
                        e.addField(prefix + "say", "Make the bot do your bidding and say what you want :speak_no_evil:", changeInline);
                        e.addField(prefix + "trim", "Mass deletes messages of the current channel. WIP", changeInline);
                        e.addField(prefix + "whois", "Gives a background check of a specific user", changeInline);
                        message.reply("", e);


                    }

                    if (message.getContent().startsWith(prefix + "avatar")) {
                        String s = String.valueOf(message.getContent());
                        s = s.replaceAll("[^0-9]+", "");
                        User u = api.getCachedUserById(s);
                        EmbedBuilder e = new EmbedBuilder();
                        e.setImage(String.valueOf(u.getAvatarUrl()));
                        e.setColor(Color.RED);
                        message.reply("", e);
                    }

                    if (message.getContent().equalsIgnoreCase(prefix + "annoyOn")) {
                        message.reply("Annoy Mode On!");
                        annoy = true;
                    }

                    if (message.getContent().equalsIgnoreCase(prefix + "annoyOff")) {
                        message.reply("Annoy Mode Off!");
                        annoy = false;
                    }

                    if (message.getContent().startsWith(prefix + "say")) {
                        message.reply(String.valueOf(message.getContent()).substring(5));
                        message.delete();

                    }
                    if (message.getContent().startsWith(prefix + "trim")) {
                        String s = String.valueOf(message.getContent());
                        s = s.replaceAll("[^0-9]+", "");
                        int trimInt = Integer.parseInt(s);
                        MessageHistory h = null;
                        try {
                            h = new ImplMessageHistory((ImplDiscordAPI) api, String.valueOf(message.getChannelReceiver().getId()), message.getId(), true, trimInt);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        ArrayList<Message> list = (ArrayList<Message>) (h != null ? h.getMessagesSorted() : null);
                        message.delete();

                        for (int i = 0; i < list.size(); i++) {
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Message m = list.get(i);
                            m.delete();
                            if (!m.isDeleted()) {
                                i--;
                            }
                        }


                    }


                    if (message.getContent().startsWith(prefix + "whois")) {
                        String s = String.valueOf(message.getContent());
                        s = s.replaceAll("[^0-9]+", "");
                        User u = api.getCachedUserById(s);

                        EmbedBuilder e = new EmbedBuilder();
                        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd\nHH:mm:ss");
                        format1.setTimeZone(TimeZone.getTimeZone("EST"));
                        e.setTitle("Who is " + u.getName() + "?");
                        e.setColor(Color.RED);
                        Channel c = discordAPI.getChannelById(String.valueOf(message.getChannelReceiver().getId()));

                        String nickname = u.getNickname(api.getServerById(c.getServer().getId()));
                        if (nickname == null) {
                            e.addField("Nickname:  ", "N/A", true);
                        } else {
                            e.setTitle("Who is " + nickname + "?");
                            e.addField("Name: ", u.getName(), true);

                        }
                        e.addField("Discriminator: ", "#" + u.getDiscriminator(), true);
                        e.addField("Creation Date: ", format1.format(u.getCreationDate().getTime()) + " EST ", true);
                        e.addField("ID: ", u.getId(), true);

                        e.setThumbnail(String.valueOf(u.getAvatarUrl()));

                        message.reply("", e);
                    }

                }
            }
        });

        api.registerListener(new TypingStartListener() {
            public void onTypingStart(DiscordAPI discordAPI, User user, Channel channel) {
                Random rand = new Random();

                String[] s = {"Stop Typing cuck :rage:",
                        "You're actually a cuck",
                        "I bet you have 1.66 GPA cuz your liberal brought you down :joy:",
                        "Take care of your girl before an African does :eggplant: :joy: :cry:"};

                if(annoy && !channel.getName().equals("general"))
                    channel.sendMessage(user.getMentionTag() + " " +  s[rand.nextInt(4)]);
            }
        });

    }
}
