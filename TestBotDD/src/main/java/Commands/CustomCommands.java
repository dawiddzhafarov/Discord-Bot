package Commands;

import Commands_utilities.Command;
import Commands_utilities.CommandsManager;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.*;

public class CustomCommands extends Command {
    private static HashMap<String, String> commandsMap = new HashMap<String, String>();
    public CustomCommands() {
        //super("qoute", "This commend allows to save qoutes.");
        name = "command";
        help = "AAaaaaaaaAAAaaAaAA";
        aliases = Arrays.asList("com","myshit");
    }

    @Override
    protected void execute(MessageReceivedEvent e) {
        String messageRaw = e.getMessage().getContentRaw();
        String[] message = messageRaw.split(" ");
        if(message.length == 1){
            e.getChannel().sendMessage("jakies info o custocommand").queue();
        }else if (message[1].equalsIgnoreCase("add")){
            if(message.length<4){
                e.getChannel().sendMessage("napisz nazwe i treść").queue();
            }else {
                if(commandsMap.containsKey(message[2]) || CommandsManager.commandExist(message[2])){
                    e.getChannel().sendMessage("Command istnieje użyj innej nazwy :V").queue();
                }else {
                    commandsMap.put(message[2], String.join(" ", Arrays.copyOfRange(message, 3, message.length)));
                    e.getChannel().sendMessage("Command has been added").queue();
                }

            }
        }else if(message[1].equalsIgnoreCase("remove")){
            if (message.length>2){
                commandsMap.remove(message[2]);
                e.getChannel().sendMessage("Command has been remove coś dalej").queue();

            }else{
                e.getChannel().sendMessage("po remove wpisz nazwe ").queue();
            }

        }
    }

    public static HashMap<String, String> getCommandsMap() {
        return commandsMap;
    }
}
