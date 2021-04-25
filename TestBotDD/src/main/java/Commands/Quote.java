package Commands;

import Commands_utilities.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.util.*;

public class Quote extends Command {
    List<String> qouteList = new LinkedList<String>(); //chyba jest szybsze niż arraylist
    private Random rand = new Random();

    public Quote() {
        //super("qoute", "This commend allows to save qoutes.");
        name = "qoute";
        help = "Use !quote <quote> to save quotes..";
        aliases = Arrays.asList("q","quo");
    }

    @Override
    protected void execute(MessageReceivedEvent e) {
        String messageRaw = e.getMessage().getContentRaw();
        String[] message = messageRaw.split(" ");
        if(message.length == 1){
            e.getChannel().sendMessage("jakies info o qoute").queue();
        }else if (message[1].equalsIgnoreCase("add")){
            qouteList.add(messageRaw.substring(messageRaw.toLowerCase().indexOf("add")+1));
            e.getChannel().sendMessage("Qoute has been added").queue();
        }else if(message[1].equalsIgnoreCase("random")){
            e.getChannel().sendMessage(qouteList.get(rand.nextInt(qouteList.size()))).queue();
        }
    }
}
