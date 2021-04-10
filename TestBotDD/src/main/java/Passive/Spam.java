package Passive;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.text.SimpleDateFormat;
import java.util.*;


public class Spam extends ListenerAdapter {
    ListMultimap<String, String> multimap = ArrayListMultimap.create();
    final long roleid = 830377700417994772L; //id roli bez mozliwosci pisania
    final long everyoneid = 818109491005358111L; //id roli @everyone
    String name;
    String time;
    /* w sumie nie wiem jak to dziala na discordzie zrobilem tak ze jesli ta sama osoba wysle wiadomosc 5 razy i czas od pierwszej do ostatniej
    bedzie mniejszy niz 4sek to przypisuje tego uzytkownika do roli muted ktora nic nie moze robic ALE
    nie wiem czemu dalej moge pisac z tego "zmutowanego" konta choc już powiadomienia nie przychodzą, może widzę to bo jestem adminem?
    Jesli tak to zostawimy to bedzie trzeba dodac do klasy roles mozliwosc usuniecia tej roli zeby ktos mogl dalej pisac
    albo wymyslic cos zupelnie innego/ jakiegos cooldowny na ktore nie znalazlem jeszcze sposobu
    w kazdym razie mozesz sobie przejrzec i ogarnac jak to dziala, moze akurat ty na cos lepszego wpadniesz ;)
     */

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
        List times = multimap.get(name);
        if(multimap.get(name).size() > 5){
            multimap.get(name).remove(0);
        }
        if (times.size() >= 5) {
            long end = Long.parseLong((String)times.get(4)); //timesLen-1
            long start = Long.parseLong((String) times.get(0)); //timesLen-5
            if((end-start) <= 4){
                e.getGuild().addRoleToMember(e.getMember().getId(), e.getJDA().getRoleById(roleid)).queue();
                e.getChannel().sendMessage("Użytkownik "+ name+ " został wyciszony za spam").queue();
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
}
