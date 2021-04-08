package Commands_utilities;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandEvent;

public class Prefix extends Command {
    static public String prefix = "!";
    public Prefix(){
        this.name = "prefix";
        this.aliases = new String[]{"p"};
        this.help = "Changes command prefix.";

    }

    protected void execute(CommandEvent e){
        String[] message = e.getMessage().getContentRaw().split(" ");
        if(message.length == 1) {
            e.getChannel().sendMessage("Info jak zmienić prompt").queue();
        }else if(message.length == 2){
            prefix = message[1];
            e.getChannel().sendMessage("Commands.Prefix has been changed to: "+ prefix).queue();
        }
    }
}
