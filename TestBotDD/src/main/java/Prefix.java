import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Prefix extends ListenerAdapter {
    static public String prefix = "!";

    public void onMessageReceived(MessageReceivedEvent e){
        String[] message = e.getMessage().getContentRaw().split(" ");
        if(message.length == 1 && message[0].equals(prefix+"prefix")) {
            e.getChannel().sendMessage("To change prefix enter it after command !prefix.").queue();
        }else if(message.length == 2 && message[0].equals(prefix+"prefix")){
            prefix = message[1];
            e.getChannel().sendMessage("Prefix has been changed to: "+prefix).queue();
        }
    }
}
