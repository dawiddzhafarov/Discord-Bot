package Commands_utilities;

import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.List;

public abstract class Command {
    private String name;
    private String help;
    private List<String> aliases;

    public Command(String name) {
        this.name = name;
    }
    public Command(String name, String help) {
        this.name = name;
        this.help = help;
    }
    public Command(String name, String help, String[] aliases) {
        this.name = name;
        this.help = help;
        this.aliases = Arrays.asList(aliases);
    }

    protected void execute(MessageReceivedEvent e){};

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }
}
