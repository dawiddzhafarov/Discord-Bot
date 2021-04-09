package Passive;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.*;

public class Filter extends ListenerAdapter {
    //String[] swears = new String[]{"Cunt", "Fuck", "Motherfucker", "Gash", "Minge", "Prick", "Punani", "Pussy", "Snatch","Twat"};
    static private List<String> swears = new LinkedList<String>(Arrays.asList("cunt", "fuck", "motherfucker", "gash", "minge", "prick", "punani", "pussy", "snatch","twat"));
    private TreeMap<Long, String> userMap = new TreeMap<>();
    //Nie moglem znaleźć nigdzie listy przeklęństw :|
    //plany na później: dodawanie słów, wyłączanie, timeouty?
    static private boolean filter = true;
    static private boolean communicat = true;
    public void onMessageReceived(MessageReceivedEvent e) {
        if (!e.getAuthor().isBot() && filter) {
            String message = e.getMessage().getContentRaw().toLowerCase();
            boolean delete = false;
            List<String> swearList = new ArrayList<>();
            for (String word : swears) {
                if (message.matches(".*\\b" + word.toLowerCase() + "\\b.*")) {
                    delete = true;
                    swearList.add(word);
                }

            }

            if (delete) {
                userMap.put(e.getMessage().getTimeCreated().toInstant().toEpochMilli(),e.getAuthor().getAsTag() );
                int numberOfBreaches = 0;
                for(Map.Entry<Long,String> entry : userMap.entrySet()) {
                    if(entry.getKey()+1800000<e.getMessage().getTimeCreated().toInstant().toEpochMilli()){//usuwa wpis jeswli starcszy niż 30 min :P
                        userMap.remove(entry.getKey());
                    }
                    if(entry.getValue().equals(e.getAuthor().getAsTag())){
                        numberOfBreaches++;
                    }
                }
                if(numberOfBreaches==3){
                    e.getMember().modifyNickname("szatan").queue();
                }

                if (communicat) {
                    e.getChannel().sendMessage("Idziesz się zmażyć w piekle za to przekleństwo!").queue();
                    e.getAuthor().openPrivateChannel().queue((privateChannel) -> {
                        privateChannel.sendMessage(swearList.toString()).queue();
                    });
                }
                //e.getMember().getUser().openPrivateChannel().queue((privateChannel) -> {privateChannel.sendMessage(swearList.toString()).queue();});
                e.getMessage().delete().queue();
            }

        }
    }
    public static void add(String word){
        swears.add(word.toLowerCase());
    }

    static public boolean delete(String word){
        return swears.remove(word.toLowerCase());
    }

    public static void setFilter(boolean filter) {
        Filter.filter = filter;
    }

    public static void setCommunicat(boolean communicat) {
        Filter.communicat = communicat;
    }

    public static boolean isFilter() {
        return filter;
    }

    public static boolean isCommunicat() {
        return communicat;
    }
}
