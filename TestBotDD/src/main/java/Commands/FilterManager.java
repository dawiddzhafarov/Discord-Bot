package Commands;

import Commands_utilities.Command;
import Passive.Filter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;


public class FilterManager  extends Command {



    public FilterManager() {
        name = "filter";
        help = "This commend allows to manage filter";
        aliases = Arrays.asList("f","flt");

    }

    @Override
    protected void execute(MessageReceivedEvent e) {

        String messageRaw = e.getMessage().getContentRaw();
        String[] message = messageRaw.split(" ");
        if(message.length == 1){
            e.getChannel().sendMessage("jakies info o filter").queue();
        }else if (message[1].equalsIgnoreCase("add")){
            for(int i = 2; i<message.length;i++) {
                Filter.add(message[i]);
            }
            e.getChannel().sendMessage("Swears has been added.").queue();
        }else if(message[1].equalsIgnoreCase("remove")){
            for(int i = 2; i<message.length;i++) {
                if(!Filter.delete(message[i])){
                    e.getChannel().sendMessage("Nie ma takiego").queue();
                }

            }
        }else if(message[1].equalsIgnoreCase("toggle")){
            if(message[2].equalsIgnoreCase("message")) {
                Filter.setCommunicat(!Filter.isCommunicat());
            }else {
                Filter.setFilter(!Filter.isFilter());
            }

        }
    }
}
