package Passive;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Spam extends ListenerAdapter {
    ArrayList times = new ArrayList();
    ArrayList names = new ArrayList();

    public void onMessageReceived(MessageReceivedEvent e){
       /*
        String user = e.getAuthor().getName();
        times.add(getTime());
        names.add(user);
        System.out.println(times);
        */
        //denerwowało mnie wypisywanie więc zakomentowałem, gdybym zapomniał od komentować sr :P

        //trzeba wymyslic jakis sposob na sprawdzenie czy ten sam uzytkownik wyslal np 3/5 wiadomosci w czasie np 2sekund lub
        //jesli odstep miedzy wysylanymi wiadomosciami jest mniejszy niz np 2 sekundy - wtedy warning/mute
    }

    public String getTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(cal.getTime());
    }
}
