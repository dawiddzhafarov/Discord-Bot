package Commands;

import Commands_utilities.Command;
import Commands_utilities.CommandsManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Arrays;

public class Prefix extends Command {
    static public String prefix = "!";
    private CommandsManager manager;

    public Prefix(CommandsManager manager){
        name = "prefix";
        help = "This commend allows to change prefix.";
        aliases = Arrays.asList("p","dynksprzedkomenda");
        prefix = manager.getPrefix();
        this.manager = manager;
    }
    @Override
    public void execute(MessageReceivedEvent e){
        String[] message = e.getMessage().getContentRaw().split(" ");
        if(message.length == 1) {
            e.getChannel().sendMessage("To change prefix enter it after command "+prefix+"prefix.").queue();
        }else if(message.length == 2){
            prefix = message[1];
            manager.setPrefix(prefix);
            
            e.getChannel().sendMessage("Prefix has been changed to: "+prefix).queue();
        }
    }
}