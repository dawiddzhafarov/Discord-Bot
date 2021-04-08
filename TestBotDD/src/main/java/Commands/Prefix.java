package Commands;

import Commands_utilities.Command;
import Commands_utilities.CommandsManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Prefix extends ListenerAdapter {
    static public String prefix = "!";
    private CommandsManager manager;

    public Prefix(CommandsManager manager){
        prefix = manager.getPrefix();
        this.manager = manager;
    }
    public void updatePrefix(){
        prefix = manager.getPrefix();
    }
    public String getPrefix(){
        return prefix;
    }

    public void onMessageReceived(MessageReceivedEvent e){
        String[] message = e.getMessage().getContentRaw().split(" ");
        if(message.length == 1 && message[0].equals(prefix+"prefix")) {
            e.getChannel().sendMessage("To change prefix enter it after command !prefix.").queue();
        }else if(message.length == 2 && message[0].equals(prefix+"prefix")){
            prefix = message[1];
            manager.setPrefix(prefix);
            updatePrefix();;
            e.getChannel().sendMessage("Prefix has been changed to: "+prefix).queue();
        }
    }
}
