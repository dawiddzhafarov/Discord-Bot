package Passive;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.IPermissionHolder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.requests.restaction.RoleAction;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Filter extends ListenerAdapter {
    //String[] swears = new String[]{"Cunt", "Fuck", "Motherfucker", "Gash", "Minge", "Prick", "Punani", "Pussy", "Snatch","Twat"};
    static private List<String> swears = new LinkedList<String>(Arrays.asList("cunt", "fuck", "motherfucker", "gash", "minge", "prick", "punani", "pussy", "snatch","twat"));
    private TreeMap<Long, String> userMap = new TreeMap<>();
    //Nie moglem znaleźć nigdzie listy przeklęństw :|
    //plany na później: dodawanie słów, wyłączanie, timeouty?
    static private boolean filter = true;
    static private boolean communicat = true;
    private RoleAction muteRole;


    public void onMessageReceived(MessageReceivedEvent e) {
        if(muteRole==null){
            muteRole = e.getGuild().createRole();
            muteRole.setColor(Color.BLACK);
            muteRole.setName("Muted");
            //muteRole. próbowałem zabronić pisania na chacie rolą ale chyba się nie da :V

        }
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
                if(numberOfBreaches>=3){
                    e.getMember().modifyNickname("szatan").queue();

                    //e.getMember().getPermissions().remove(Permission.MESSAGE_WRITE);
                    //e.getGuild().getGuildChannelById(e.getChannel().getId()).createPermissionOverride((IPermissionHolder) e.getMember()).deny(Permission.MESSAGE_WRITE).queue();
                    e.getGuild().getGuildChannelById(e.getChannel().getId()).upsertPermissionOverride((IPermissionHolder) e.getMember()).deny(Permission.MESSAGE_WRITE).queue();

                    new java.util.Timer().schedule(
                            new java.util.TimerTask() {
                                @Override
                                public void run() {
                                    //e.getMember().getPermissions().add(Permission.MESSAGE_WRITE);
                                    //e.getGuild().getGuildChannelById(e.getChannel().getId()).createPermissionOverride((IPermissionHolder) e.getMember()).grant(Permission.MESSAGE_WRITE).queue();
                                    //e.getGuild().getGuildChannelById(e.getChannel().getId()).upsertPermissionOverride((IPermissionHolder) e.getMember()).grant(Permission.MESSAGE_WRITE).queue();
                                    e.getGuild().getGuildChannelById(e.getChannel().getId()).upsertPermissionOverride((IPermissionHolder) e.getMember()).clear(Permission.MESSAGE_WRITE).queue();
                                    System.out.println("Zwracam prawo: "+e.getMember().getUser().getName());
                                    return;
                                }
                            },
                            20000
                    );

                //nie da się modyfikować getPermision więc to nie działa a i run nie wiem czy jest dobry :/

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
