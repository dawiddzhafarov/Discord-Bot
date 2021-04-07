import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Filter extends ListenerAdapter {
    //String[] swears = new String[]{"Cunt", "Fuck", "Motherfucker", "Gash", "Minge", "Prick", "Punani", "Pussy", "Snatch","Twat"};
    List<String> swears = Arrays.asList("Cunt", "Fuck", "Motherfucker", "Gash", "Minge", "Prick", "Punani", "Pussy", "Snatch","Twat");
    //Nie moglem znaleźć nigdzie listy przeklęństw :|
    //plany na później: dodawanie słów, wyłączanie, timeouty?
    public void onMessageReceived(MessageReceivedEvent e) {
        if (!e.getAuthor().isBot()) {
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
                e.getChannel().sendMessage("Idziesz się zmażyć w piekle za to przekleństwo!").queue();
                e.getAuthor().openPrivateChannel().queue((privateChannel) -> {
                    privateChannel.sendMessage(swearList.toString()).queue();
                });
                //e.getMember().getUser().openPrivateChannel().queue((privateChannel) -> {privateChannel.sendMessage(swearList.toString()).queue();});
                e.getMessage().delete().queue();
            }

        }
    }

}
