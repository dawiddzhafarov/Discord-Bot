package Commands_utilities;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;

public class CommandsManager extends ListenerAdapter {
    String prefix;
    String helpWord;
    List<Command> commands = new ArrayList<>();
    JDA jda;

    public CommandsManager(String prefix, String helpWord) {
        this.prefix = prefix;
        this.helpWord = helpWord;
        //this.jda = jda;
    }

    public void addCommand(Command command) {
        commands.add(command);
    }


    public void onMessageReceived(MessageReceivedEvent e) {
        if(e.getMessage().getContentRaw().startsWith(prefix)){
            String mess = e.getMessage().getContentRaw().split(" ")[0].toLowerCase().replace(prefix,"");
            if(mess.equals(helpWord)){
                e.getAuthor().openPrivateChannel().queue((privateChannel) -> {
                    privateChannel.sendMessage("pomocna lista").queue();
                });
            }
            else {
                for (Command command : commands) {
                    if (mess.matches(prefix + command.getName()) || command.getAliases().contains(mess)) {
                        command.execute(e);
                    }
                }
            }
        }
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
        jda.getPresence().setActivity(Activity.playing("Type: "+prefix+helpWord+ " for command list :D"));

    }

    public void setHelpWord(String helpWord) {
        this.helpWord = helpWord;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getHelpWord() {
        return helpWord;
    }
    public void addJDA(JDA jda){
        this.jda = jda;
    }
}
