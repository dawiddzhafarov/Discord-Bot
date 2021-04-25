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
        name = "command";
        help = "Type "+Prefix.prefix+"command add <name of new command> <what will bot respond with> to add new command. "+"\n"+
        "For example: "+Prefix.prefix+"command add mitochondria is a powerhouse of the cell"+"\n"+
        "Type "+Prefix.prefix+"command remove <name of created command> to delete. "+"\n"+
                "For example: "+Prefix.prefix+"command remove mitochondria "+"\n"+
                "Type "+Prefix.prefix+"command list to get a list of created commands";

        aliases = Arrays.asList("com","myshit");
    }

    @Override
    protected void execute(MessageReceivedEvent e) {
        String messageRaw = e.getMessage().getContentRaw();
        String[] message = messageRaw.split(" ");
        if(message.length == 1){
            e.getChannel().sendMessage("Please follow !command with add, remove, list or use !help for more info").queue();
        }else if (message[1].equalsIgnoreCase("add")){
            if(message.length<4){
                e.getChannel().sendMessage("Please use format !command add <command name> <command content>. For example type !help.").queue();
            }else {
                if(commandsMap.containsKey(message[2]) || CommandsManager.commandExist(message[2])){
                    e.getChannel().sendMessage("Command with this name already exist.").queue();
                }else {
                    commandsMap.put(message[2], String.join(" ", Arrays.copyOfRange(message, 3, message.length)));
                    e.getChannel().sendMessage("Command has been added").queue();
                }

            }
        }else if(message[1].equalsIgnoreCase("remove")){
            if (message.length>2){
                commandsMap.remove(message[2]);
                e.getChannel().sendMessage("Command has been removed").queue();

            }else{
                e.getChannel().sendMessage("Please provide command name after remove. For example type !help").queue();
            }

        }else if(message[1].equalsIgnoreCase("list")){
            e.getChannel().sendMessage("List of created commands: "+commandsMap.keySet().toString()).queue();
        }
    }

    public static HashMap<String, String> getCommandsMap() {
        return commandsMap;
    }
}
