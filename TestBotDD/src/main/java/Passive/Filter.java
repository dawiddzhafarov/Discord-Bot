package Passive;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.IPermissionHolder;
import net.dv8tion.jda.api.entities.PermissionOverride;
import net.dv8tion.jda.api.entities.Role;
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
    static private boolean kick = false;
    static private boolean ban = false;
    static private double timePeriode = 30;

    static private List<Double> mutedTime = new LinkedList<>();
    private Role muteRole;

    public Filter() {
        mutedTime.add(0.5);
        mutedTime.add(1.);
        mutedTime.add(5.);

    }

    public void onMessageReceived(MessageReceivedEvent e) {
        if(muteRole==null){
            for(Role role : e.getGuild().getRoles()){
                if(role.getName().equals("MutedForInappropriateLanguage")){
                    muteRole = role;
                    break;
                }
            }
            if(muteRole==null) {
                RoleAction muteRoleBuild;
                muteRoleBuild = e.getGuild().createRole();
                muteRoleBuild.setColor(Color.BLACK);
                muteRoleBuild.setName("MutedForInappropriateLanguage");
                muteRole = muteRoleBuild.complete();


                for (GuildChannel channel : e.getGuild().getChannels()) {
                    channel.upsertPermissionOverride((IPermissionHolder) muteRole).deny(Permission.MESSAGE_WRITE).queue();
                }
            /*
            boolean first = false;
            List<Role> roles = e.getGuild().getRoles();
            for(Role role : roles){
                //role.getPermissions().contains(Permission.MESSAGE_WRITE);
                for(GuildChannel channel : e.getGuild().getChannels()){
                    List<PermissionOverride> permissionOverridesList = channel.getRolePermissionOverrides();

                }
                if(role.getPermissionsRaw()==muteRole.getPermissionsRaw()){
                    if(first) {
                        muteRole.delete().queue();
                        muteRole = role;
                        break;
                    }
                    first=true;
                }
            }
            */
            }

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
                    if(entry.getKey()+timePeriode*60000<e.getMessage().getTimeCreated().toInstant().toEpochMilli()){//usuwa wpis jeswli starcszy niż 30 min :P
                        userMap.remove(entry.getKey());
                    }
                    if(entry.getValue().equals(e.getAuthor().getAsTag())){
                        numberOfBreaches++;
                    }
                }
                if(numberOfBreaches!=0 && mutedTime.get(numberOfBreaches-1)!=null){
                    //e.getMember().modifyNickname("szatan").queue();

                    //e.getMember().getPermissions().remove(Permission.MESSAGE_WRITE);
                    //e.getGuild().getGuildChannelById(e.getChannel().getId()).createPermissionOverride((IPermissionHolder) e.getMember()).deny(Permission.MESSAGE_WRITE).queue();
                    e.getGuild().addRoleToMember(e.getMember(),muteRole).queue();
                    //e.getGuild().getGuildChannelById(e.getChannel().getId()).upsertPermissionOverride((IPermissionHolder) e.getMember()).deny(Permission.MESSAGE_WRITE).queue();

                    new java.util.Timer().schedule(
                            new java.util.TimerTask() {
                                @Override
                                public void run() {
                                    //e.getMember().getPermissions().add(Permission.MESSAGE_WRITE);
                                    //e.getGuild().getGuildChannelById(e.getChannel().getId()).createPermissionOverride((IPermissionHolder) e.getMember()).grant(Permission.MESSAGE_WRITE).queue();
                                    //e.getGuild().getGuildChannelById(e.getChannel().getId()).upsertPermissionOverride((IPermissionHolder) e.getMember()).grant(Permission.MESSAGE_WRITE).queue();
                                    //e.getGuild().getGuildChannelById(e.getChannel().getId()).upsertPermissionOverride((IPermissionHolder) e.getMember()).clear(Permission.MESSAGE_WRITE).queue();
                                    e.getGuild().removeRoleFromMember(e.getMember(),muteRole).queue();
                                    System.out.println("Zwracam prawo: "+e.getMember().getUser().getName());
                                    return;
                                }
                            },(int) (mutedTime.get(numberOfBreaches-1)*60000)

                    );

                //nie da się modyfikować getPermision więc to nie działa a i run nie wiem czy jest dobry :/

                }else if(mutedTime.size()<numberOfBreaches){
                    if(kick) {
                        if(ban) {
                            e.getMember().ban(0,"For multiple filter breaches");
                        }else {
                            e.getMember().kick().queue();
                        }
                    }else{
                        e.getGuild().addRoleToMember(e.getMember(),muteRole).queue();
                        new java.util.Timer().schedule(
                                new java.util.TimerTask() {
                                    @Override
                                    public void run() {
                                        e.getGuild().removeRoleFromMember(e.getMember(),muteRole).queue();
                                        System.out.println("Zwracam prawo: "+e.getMember().getUser().getName());
                                        return;
                                    }
                                },((int) (mutedTime.get(mutedTime.size())-1)*60000)
                        );
                    }
                }

                if (communicat) {
                    e.getChannel().sendMessage(e.getAuthor().getName()+" we don't tolerate such words on this server!").queue();
                    e.getAuthor().openPrivateChannel().queue((privateChannel) -> {
                        privateChannel.sendMessage("We don't tolerate such words on this server: "+swearList.toString()).queue();
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

    public static void setMutedTime(List<Double> time) {
        mutedTime = time;
    }
    public static void clearMutedTime() {
        mutedTime.clear();
    }

    public static boolean isKick() {
        return kick;
    }

    public static void setKick(boolean kick) {
        Filter.kick = kick;
    }

    public static boolean isBan() {
        return ban;
    }

    public static void setBan(boolean ban) {
        Filter.ban = ban;
    }

    public static List<String> getSwears() {
        return swears;
    }

    public static double getTimePeriode() {
        return timePeriode;
    }

    public static void setTimePeriode(double timePeriode) {
        Filter.timePeriode = timePeriode;
    }

    public static List<Double> getMutedTime() {
        return mutedTime;
    }

}
