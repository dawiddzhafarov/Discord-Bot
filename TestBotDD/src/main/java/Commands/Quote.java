package Commands;

import Commands_utilities.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Quote extends Command {
    List<String> qouteList = new ArrayList<>();
    private Random rand = new Random();

    public Quote() {
        //super("qoute", "This commend allows to save qoutes.");
        name = "qoute";
        help = "This commend allows to save qoutes.";
        aliases = Arrays.asList("q","stupidshit");
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
