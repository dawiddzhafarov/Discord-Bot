package Commands_utilities;

import Commands.RoleCommands;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;

public class CommandsManager extends ListenerAdapter {
    private String prefix;
    private String helpWord;
    static private List<Command> commands = new ArrayList<>();
    private JDA jda;

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
                EmbedBuilder info = new EmbedBuilder();
                info.setTitle("Command list");
                info.setDescription("Jakieś info o tym bocie");
                info.setColor(0xd4901c);
                MessageEmbed embed = info.build(); //musiałem to zrobić tutaj bo inaczej nie działało :V
                e.getAuthor().openPrivateChannel().queue((privateChannel) -> {
                    privateChannel.sendMessage(embed).queue();
                });
                info.clear();
                for (Command command : commands) {
                    EmbedBuilder commandInfo = new EmbedBuilder();
                    commandInfo.setTitle(command.getName());
                    commandInfo.setDescription(command.getAliases().toString());
                    commandInfo.addField(command.getName()+" Info",command.getHelp(),false);
                    commandInfo.setColor(0xd4901c);
                    MessageEmbed commandEmbed = commandInfo.build(); //musiałem to zrobić tutaj bo inaczej nie działało :V
                    e.getAuthor().openPrivateChannel().queue((privateChannel) -> {
                        privateChannel.sendMessage(commandEmbed).queue();
                    });
                    info.clear();
                }
                //info.addField("Twórcy","TurboFirma",false);
            }
            else {
                //e.getChannel().sendMessage("jakies info o qoute").queue();
                for (Command command : commands) {

                    if (mess.matches( command.getName()) || command.getAliases().contains(mess)) {
                        if(e.getMember().hasPermission(Permission.valueOf("ADMINISTRATOR")) || RoleCommands.roleCheck(e.getMember().getRoles(),mess)) {
                            command.execute(e);
                        }
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

    static public List<Command> getCommands() {
        return commands;
    }
    static public boolean commandExist(String name){
        for(Command command : commands){
            if(command.getName().equals(name) || command.getAliases().contains(name)){
                return true;
            }
        }
        return false;
    }
}
