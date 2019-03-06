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
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


public class ThunkBot {
    private static boolean annoy = false;
    private static String prefix = "-";
    private static PrintWriter pw;
    private static boolean collect = false;
    private static final File output = new File("output.txt");
    private static String ownerID = "142404845234683904";
    private static String channelAnnoy;



    public static void main(String[] args) throws FileNotFoundException{
        Scanner in = new Scanner(new File("input1.txt"));
        final DiscordAPI api = Javacord.getApi(in.nextLine(), true);
        api.connectBlocking();

        api.setGame("Type " + prefix + " help for commands");



        api.registerListener(new MessageCreateListener() {
            public void onMessageCreate(DiscordAPI discordAPI, Message message) {
                if(message.getContent().equalsIgnoreCase(prefix + "startCollection")  && message.getAuthor().getId().equals(ownerID)){
                    try {
                        message.reply("Collecting...");
                        pw = new PrintWriter(output);
                        collect = true;
                    } catch (FileNotFoundException e) { e.printStackTrace(); }

                }

                if(collect && message.getAttachments().size() != 0)
                    pw.println(message.getChannelReceiver() + ":     User: " +message.getAuthor() + " Attachments:  "  + message.getAttachments());

                if (!message.getAuthor().isBot()) {
                    if (message.getContent().equalsIgnoreCase(prefix + "ping")) {
                        Calendar date = Calendar.getInstance();
                        long m2 = date.getTimeInMillis();
                        long m1 = message.getCreationDate().getTimeInMillis();

                        message.reply(message.getAuthor().getMentionTag() + " Pong! :ping_pong: " + Long.toString(m1-m2) + "ms");
                    }
                    String msg = message.getContent().toLowerCase();
                    msg = msg.replaceAll("[^a-zA-Z0-9 -]", "");
                    msg = msg.replaceAll(" ", "");
                    if(msg.contains("incel") || msg.contains("incei") || msg.contains("lncel") || msg.contains("lncei")){
                        message.delete();
                    }
                    /*
                     if(message.getContent().startsWith(prefix + "plinko")){
                        Integer numToPlace = Integer.valueOf(message.getContent().substring(prefix.length() + 6).trim());
                        Random rand = new Random();
                        String[] prizes = {"100", "0", "5", "100", "5", "0", "100"};
                        message.reply("Beginning Plinko: Dropping coin at the number " + numToPlace +" slot!");
                        String result = "|     0     |     1     |     2     |     3     |     4     |     5     |     6     |\n";
                        for(int i = 0; i < 10; i++){

                            for(int j = 0; j < 6; j++){
                                if(j != numToPlace){
                                    result += "|             ";
                                } else {
                                    result += "|     ©     ";
                                }
                            }


                            int direction = rand.nextInt(2);
                            if(numToPlace == 6){
                                direction = 0;
                            }
                            if(numToPlace == 0){
                                direction = 1;
                            }
                            if(direction == 0){
                                numToPlace -= 1;
                            } else if(direction == 1){
                                numToPlace += 1;
                            }

                            result += "|\n";
                        }
                        result += "|   100   |   -0-   |   -5-   |   100   |   -5-   |   -0-   |   100   |\nCongrats, your coin fell into slot " + numToPlace +". You have won: " + prizes[numToPlace];
                        message.reply(result);


                    }
                    */

                    if(message.getContent().equalsIgnoreCase(prefix +"bars")){
                        ArrayList<String> bars = new ArrayList<String>();
                        ArrayList<String> emojis = new ArrayList<String>();

                        Random rand = new Random();
                        File f = new File("lilwayne.txt");
                        File f1 = new File("emoji.txt");
                        Scanner in = null;
                        try {
                            in = new Scanner(f);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        while(in.hasNextLine()){
                            bars.add(in.nextLine());
                        }

                        try {
                            in = new Scanner(f1);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        while(in.hasNextLine()){
                            emojis.add(in.nextLine());
                        }

                        message.reply(bars.get(rand.nextInt(bars.size())) + " " + emojis.get(rand.nextInt(emojis.size())) + emojis.get(rand.nextInt(emojis.size())));

                    }

                    if (message.getContent().startsWith(prefix + "prefix") && message.getAuthor().getId().equals(ownerID)) {
                        String newPrefix = message.getContent().substring(prefix.length() + 6);
                        message.reply("Prefix has been changed from " + prefix + " to " + newPrefix);
                        prefix = newPrefix.trim();
                        api.setGame("Type " + prefix + "help for commands");

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
                        e.addField(prefix + "startCollection", "Starts collecting messages", changeInline);
                        e.addField(prefix + "stopCollection", "Stops collecting messages", changeInline);
                        e.addField(prefix + "curse", "Curse someone :bat:", changeInline);
                        e.addField(prefix + "bars", "Show some sick bars at random", changeInline);

                        message.reply("", e);


                    }

                    if (message.getContent().startsWith(prefix + "avatar")) {
                        String s = String.valueOf(message.getContent());
                        s = s.replaceAll("[^0-9]+", "");
                        User u = api.getCachedUserById(s);
                        EmbedBuilder e = new EmbedBuilder();
                        String url = String.valueOf(u.getAvatarUrl());
                        e.setImage(url);
                        e.setColor(Color.RED);
                        message.reply("", e);
                    }

                    if (message.getContent().equalsIgnoreCase(prefix + "annoyOn") && message.getAuthor().getId().equals(ownerID)) {
                        message.reply("Annoy Mode On!");
                        channelAnnoy = message.getChannelReceiver().getId();
                        annoy = true;
                    }


                    if (message.getContent().equalsIgnoreCase(prefix + "annoyOff")  && message.getAuthor().getId().equals(ownerID)) {
                        message.reply("Annoy Mode Off!");
                        annoy = false;
                    }

                    if (message.getContent().startsWith(prefix + "say")) {
                        message.reply( String.valueOf(message.getContent()).substring(5));
                        message.delete();

                    }
                    if (message.getContent().startsWith(prefix + "trim")  && message.getAuthor().getId().equals(ownerID)) {
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

                    if(message.getContent().equals(prefix + "stopCollection")  && message.getAuthor().getId().equals(ownerID)){
                        message.reply("Stopping Collection...");
                        pw.close();
                        try {
                            Scanner in = new Scanner(new File("output.txt"));
                            while(in.hasNextLine()){
                                api.getCachedUserById(ownerID).sendMessage(in.nextLine());
                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }


                    }

                    if(message.getContent().startsWith(prefix + "curse") && message.getAuthor().getId().equals(ownerID)){
                        String s = String.valueOf(message.getContent());
                        s = s.replaceAll("[^0-9]+", "");
                        User u = api.getCachedUserById(s);
                        message.reply("I will now annoy " + u.getMentionTag());
                        //u.sendMessage("I have been sent to annoy you :rage:!! Type 'stop' to stop");
                        for(int i = 10; i >= 0; i--){
                            if(i == 0){
                                u.sendMessage("You have survived...");
                                break;
                            }
                            u.sendMessage("I have been sent to annoy you :rage:...you have " + i + " more messages in queue");

                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }

                    }


                    if(message.isPrivateMessage()){
                        api.getCachedUserById(ownerID).sendMessage(message.toString() + " " + message.getAttachments());
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
                        e.addField("Playing: ", u.getGame(), true);
                        e.setThumbnail(String.valueOf(u.getAvatarUrl()));

                        message.reply("", e);
                    }

                }
            }
        });

        api.registerListener(new TypingStartListener() {
            public void onTypingStart(DiscordAPI discordAPI, User user, Channel channel) {
                if(annoy && channel.getId().equals(channelAnnoy))
                    channel.sendMessage(user.getMentionTag() + " stop typing! :rage:");
            }
        });

    }
}
