package Commands;

import Commands_utilities.Command;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.util.Arrays;

public class RolePost extends Command {// nie mam weny do nazw dzisiaj :V
    private static ListMultimap<String, String> postMap = ArrayListMultimap.create();

    public RolePost() {
        name = "rolepost";
        help = "Type " + Prefix.prefix + "rolepost <emote>=<role> <emote>=<role> ... content: <post content> to create reaction post" + "\n" +
                "For example: " + Prefix.prefix + "command :thinking:=New User content: React using :thinking: to be granted role New User" ;

        aliases = Arrays.asList("rp", "rolep");
    }

    @Override
    protected void execute(MessageReceivedEvent e) {
        if(e.getMember().hasPermission(Permission.valueOf("MANAGE_ROLES")) || RoleCommands.roleCheck(e.getMember().getRoles(),name)) {
            String messageRaw = e.getMessage().getContentRaw();
            String[] message = messageRaw.split(" ");
            if (message.length > 1) {

                //e.getChannel().sendMessage(String.join(" ", Arrays.copyOfRange(message, messageRaw.indexOf("content:"), message.length))).queue();
                String substring = messageRaw.substring(messageRaw.indexOf("content: ") + 9);
                e.getChannel().sendMessage(substring).queue();
                e.getMessage().delete().queue();

                int i = 1;
                while (!message[i].equals("content:")) {
                    if (message[i].split("=").length == 2) {
                        int j = i + 1;
                        String text = message[i];
                        while (!(message[j].split("=").length == 2) && !message[j].equals("content:")) {
                            text = text + " " + message[j];
                            j++;
                        }
                        postMap.put(substring, text);
                    }
                    i++;
                }
            }
        }
    }

    public static ListMultimap<String, String> getPostMap() {
        return postMap;
    }
}
