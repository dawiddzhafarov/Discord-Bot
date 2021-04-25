package Passive;

import Commands.CustomCommands;
import Commands.Prefix;
import Commands_utilities.CommandsManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Arrays;

public class CustomCommandsSniffer extends ListenerAdapter { //nie mkiałem pomyśłu na nazwę jak coś lepszego masz to zmieniń :P
    @Override
    public void onMessageReceived(MessageReceivedEvent e){
        if(e.getMessage().getContentRaw().startsWith(Prefix.prefix)) {
            String message = e.getMessage().getContentRaw().split(" ")[0].replace(Prefix.prefix,"");
            if(CustomCommands.getCommandsMap().containsKey(message)){
                e.getChannel().sendMessage(CustomCommands.getCommandsMap().get(message)).queue();
            }
        }
    }
}
