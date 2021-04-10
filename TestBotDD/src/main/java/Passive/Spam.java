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
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;


public class Spam extends ListenerAdapter {
    private static ListMultimap<String, String> multimap = ArrayListMultimap.create();
    private final long roleid = 830377700417994772L; //id roli bez mozliwosci pisania
    private HashMap breaches = new HashMap();

    private String name;
    private String time;
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
            name = user;
            time = acttime;
            multimap.put(user, acttime);
            spamcheck(e.getAuthor().getName(), e);
        }

    }
    public void spamcheck(String name, MessageReceivedEvent e){


        if(multimap.get(name).size() > messages_limit){ //5
            multimap.get(name).remove(0);
        }
        System.out.println(messages_limit);
        List times = multimap.get(name);
        int timesLen = times.size();
        System.out.println(times);
        System.out.println(timesLen);
        if (timesLen >= messages_limit) { //5
            long end = Long.parseLong((String)times.get(timesLen-1)); //timesLen-1 /4
            long start = Long.parseLong((String) times.get(0)); //timesLen-5
            if((end-start) <= time_exceed){
                //e.getGuild().addRoleToMember(e.getMember().getId(), e.getJDA().getRoleById(roleid)).queue();
                if(mutedRole == null){
                    RoleAction mutedRoleUp;
                    mutedRoleUp = e.getGuild().createRole().setColor(Color.BLACK).setName("Muted for Spam");
                    mutedRole = mutedRoleUp.complete();
                    e.getGuild().getGuildChannelById(e.getChannel().getId()).upsertPermissionOverride((IPermissionHolder) mutedRole).deny(Permission.MESSAGE_WRITE).queue();
                }
                if(breaches.get(e.getAuthor().getName()) == null) {
                    breaches.put(e.getAuthor().getName(), 1);
                    e.getChannel().sendMessage("To twoje pierwsze ostrzeżenie! Nie spamuj!").queue();
                } else {
                    //int nr_of_breaches = Integer.parseInt((String) breaches.get(e.getAuthor().getName()));//if exist
                    int nr_of_breaches = (int) breaches.get(e.getAuthor().getName());
                    nr_of_breaches++;
                    breaches.put(e.getAuthor().getName(), nr_of_breaches);
                    e.getChannel().sendMessage("To Twoje kolejne ostrzeżenie za spam, za "+limit+ " ostrzeżenia czeka Cię wyciszenie!").queue();
                    if(nr_of_breaches == limit){
                        e.getGuild().addRoleToMember(e.getMember().getId(), mutedRole).queue();
                        e.getChannel().sendMessage("Użytkownik "+ name+ " został wyciszony za spam").queue();
                    }
                }
                //e.getGuild().addRoleToMember(e.getMember().getId(), mutedRole).queue();
                //e.getChannel().sendMessage("Użytkownik "+ name+ " został wyciszony za spam").queue();
            }
        }
    }

    public String getTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        long timestamp = System.currentTimeMillis() / 1000;
        //return sdf.format(cal.getTime());
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
