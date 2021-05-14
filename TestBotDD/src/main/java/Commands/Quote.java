package Commands;

import Commands_utilities.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.codehaus.plexus.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

public class Quote extends Command {
    List<String> qouteList = new LinkedList<String>(); //chyba jest szybsze niż arraylist
    private Random rand = new Random();

    public Quote() {
        //super("qoute", "This commend allows to save qoutes.");
        name = "quote";
        help = "Use !quote add <quote> to save quotes"+"\n"+
        "Example: !quote add To be, or not to be?"+"\n"+
                "Use !quote remove <number> to remove quote"+"\n"+
                "Example: !quote remove 2 will remove a second quote from the list"+"\n"+
                "Use !quote list to see whole list of quotes"+"\n"+
                "Use !quote random to see a random quote"+"\n"+
                "Use !quote <number> to see chosen quote"+"\n"+
                "Example: !quote 2 will display a second quote from the list";
        aliases = Arrays.asList("q","quo");
    }

    @Override
    protected void execute(MessageReceivedEvent e) {
        String messageRaw = e.getMessage().getContentRaw();
        String[] message = messageRaw.split(" ");
        if(message.length == 1){
            e.getChannel().sendMessage("Quote is used for saving quotes").queue();
        }else if (message[1].equalsIgnoreCase("add")){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
            Date date = new Date(System.currentTimeMillis());

            qouteList.add(messageRaw.substring(messageRaw.toLowerCase().indexOf("add")+3)+"-"+formatter.format(date));

            e.getChannel().sendMessage("Quote has been added").queue();
        }else if(message[1].equalsIgnoreCase("random")){
            e.getChannel().sendMessage(qouteList.get(rand.nextInt(qouteList.size()))).queue();
        }else if(StringUtils.isNumeric(message[1])){
            if(qouteList.size()>=Integer.parseInt(message[1]) && 0<Integer.parseInt(message[1])) {
                e.getChannel().sendMessage(qouteList.get(Integer.parseInt(message[1])-1)).queue();
            }
        }else if(message[1].equalsIgnoreCase("remove")&&message.length == 3){
            if(StringUtils.isNumeric(message[2])) {
                if (qouteList.size() >= Integer.parseInt(message[2]) && 0 < Integer.parseInt(message[2])) {
                    qouteList.remove(Integer.parseInt(message[2]) - 1);
                    e.getChannel().sendMessage("Quote has been removed.").queue();
                }
            }
        }else if(message[1].equalsIgnoreCase("list")){
            for(int i =0;i< qouteList.size();i++) {
                e.getChannel().sendMessage(i+1 + " Qoute: "+qouteList.get(i)).queue();
            }
        }
    }
}
