package Passive;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.IPermissionHolder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.RoleAction;

import java.awt.*;
import java.util.*;
import java.util.List;


public class Spam extends ListenerAdapter {
    private static ListMultimap<String, String> multimap = ArrayListMultimap.create();
    private HashMap breaches = new HashMap();

    //private String name;
    //private String time;
    static private int messages_limit = 5;
    static private int time_exceed = 4;
    private Role mutedRole;
    private static int limit = 5;



    public void onMessageReceived(MessageReceivedEvent e){
        if(e.getAuthor().getName().equals("TestBot123456")){
            // nie rob nic
        } else {
            String user = e.getAuthor().getName();
            String acttime = getTime();
            //name = user;
            //time = acttime;
            multimap.put(user, acttime);
            spamcheck(e.getAuthor().getName(), e);
        }
    }
    public void spamcheck(String name, MessageReceivedEvent e){
        if(multimap.get(name).size() > messages_limit){ //5
            multimap.get(name).remove(0);
        }
        List times = multimap.get(name);
        int timesLen = times.size();
        if (timesLen >= messages_limit) { //5
            long end = Long.parseLong((String)times.get(timesLen-1));
            long start = Long.parseLong((String) times.get(0));
            if((end-start) <= time_exceed){
                try {
                    List<Role> roles = e.getGuild().getRolesByName("mutedforspam", true);
                    Role role = roles.get(0);
                    mutedRole = role;
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
                if(mutedRole == null){
                    RoleAction mutedRoleUp;
                    mutedRoleUp = e.getGuild().createRole().setColor(Color.BLACK).setName("MutedForSpam");
                    mutedRole = mutedRoleUp.complete();
                    e.getGuild().getGuildChannelById(e.getChannel().getId()).upsertPermissionOverride((IPermissionHolder) mutedRole).deny(Permission.MESSAGE_WRITE).queue();
                }
                if(breaches.get(e.getAuthor().getName()) == null) {
                    breaches.put(e.getAuthor().getName(), 1);
                    e.getChannel().sendMessage("To twoje pierwsze ostrzeżenie! Nie spamuj!").queue();
                } else {
                    int nr_of_breaches = (int) breaches.get(e.getAuthor().getName());
                    nr_of_breaches++;
                    breaches.put(e.getAuthor().getName(), nr_of_breaches);
                    e.getChannel().sendMessage("To Twoje kolejne ostrzeżenie za spam, za "+limit+ " ostrzeżenia czeka Cię wyciszenie!").queue();
                    if(nr_of_breaches == limit){
                        e.getGuild().addRoleToMember(e.getMember().getId(), mutedRole).queue();
                        e.getChannel().sendMessage("Użytkownik "+ name+ " został wyciszony za spam").queue();
                    }
                }
            }
        }
    }

    public String getTime() {
        long timestamp = System.currentTimeMillis() / 1000;
        return Long.toString(timestamp);
    }
    public static void setMessages(int nr_mess){
        messages_limit = nr_mess;
        multimap.clear();
    }
    public static void setTime_exceed(int nr_time){
        time_exceed = nr_time;
    }
    public static void setBreachesLimit(int nr_breaches){
        limit = nr_breaches;
    }
}
