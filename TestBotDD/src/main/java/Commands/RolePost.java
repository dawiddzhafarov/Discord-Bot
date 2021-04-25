package Commands;

import Commands_utilities.Command;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.collections4.MultiMap;

import java.util.Arrays;
import java.util.HashMap;

public class RolePost extends Command {// nie mam weny do nazw dzisiaj :V
    private static ListMultimap<String, String> postMap = ArrayListMultimap.create();

    public RolePost() {
        name = "rolepost";
        help = "Type " + Prefix.prefix + "command add <name of new command> <what will bot respond with> to add new command. " + "\n" +
                "For example: " + Prefix.prefix + "command add mitochondria is a powerhouse of the cell" + "\n" +
                "Type " + Prefix.prefix + "command remove <name of created command> to delete. " + "\n" +
                "For example: " + Prefix.prefix + "command remove mitochondria " + "\n" +
                "Type " + Prefix.prefix + "command list to get a list of created commands";

        aliases = Arrays.asList("rp", "dunno :V");
    }

    @Override
    protected void execute(MessageReceivedEvent e) {
        String messageRaw = e.getMessage().getContentRaw();
        String[] message = messageRaw.split(" ");
        if(message.length>1){

            //e.getChannel().sendMessage(String.join(" ", Arrays.copyOfRange(message, messageRaw.indexOf("content:"), message.length))).queue();
            String substring = messageRaw.substring(messageRaw.indexOf("content: ") + 9);
            e.getChannel().sendMessage(substring).queue();
            e.getMessage().delete().queue();

            int i =1;
            while (!message[i].equals("content:")){
                if(message[i].split("=").length==2){
                    int j = i+1;
                    String text = message[i];
                    while(!(message[j].split("=").length==2)&&!message[j].equals("content:")){
                        text = text+" "+ message[j];
                        j++;
                    }
                    postMap.put(substring,text);
                }
                i++;
            }
        }
    }

    public static ListMultimap<String, String> getPostMap() {
        return postMap;
    }
}
