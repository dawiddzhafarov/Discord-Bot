package Commands_utilities;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.util.List;

public abstract class Command {
    protected String name;
    protected String help;
    protected List<String> aliases;

    protected void execute(MessageReceivedEvent e){};

    public String getName() {
        return name;
    }

    public String getHelp() {
        return help;
    }

    public List<String> getAliases() {
        return aliases;
    }

}
