package Commands_utilities;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Clear extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e){
        String[] args = e.getMessage().getContentRaw().split("\\s+");
        if (args[0].equalsIgnoreCase(Prefix.prefix + "clear")){
            if (args.length < 2) {
                e.getChannel().sendMessage("Po komendzie !clear podaj liczbę wiadomości do usunięcia!").queue();
            } else {
                List<Message> messages = e.getChannel().getHistory().retrievePast(Integer.parseInt(args[1])).complete();
                List idlist = new ArrayList();
                for (Message message : messages){
                    idlist.add(message.getId());
                }

                for(int i = 0; i < idlist.size(); i++) {
                    e.getChannel().deleteMessageById((String) idlist.get(i)).queue();
                }
            }
        }
    }
}
