package Passive;

import Commands.CustomCommands;
import Commands.Prefix;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CustomCommandsSniffer extends ListenerAdapter {
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
