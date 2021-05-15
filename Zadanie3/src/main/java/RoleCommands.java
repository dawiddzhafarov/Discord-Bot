import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.util.Arrays;
import java.util.List;

public class RoleCommands extends Command {

    private static ListMultimap<String, String> roleMap = ArrayListMultimap.create();

    public RoleCommands() {
        name = "rolecommands";
        help = "Use !rolecommands <RoleName or everyone> commands: <command names or all> to allow chosen role use specified commands" +"\n"+
                "Example: !rolecommands everyone commands: clear rolepost kick  to allow every user to use commands clear, rolepost and kick"+"\n"+
                "!rolecommands Mod commands: all  to allow users with role Mod to use all commands"+"\n"+
                "Use !rolecommands remove <RoleName or everyone> commands: <command names or all> to remove specified commands from list of allowed commands fo specified role" +"\n"+
                "Example: !rolecommands remove everyone commands: clear rolepost kick to disallow every user from using commands clear, rolepost and kick";
        aliases = Arrays.asList("rc", "rolecom");
    }
    @Override
    protected void execute(MessageReceivedEvent e) {
        String[] message = e.getMessage().getContentRaw().split(" ");

        if(message.length > 1){
            if(message.length > 2 && e.getMessage().getContentRaw().contains("commands:")){
                if(message[1].equals("remove")){
                    int j = 3;
                    String role = message[2];
                    while (!message[j].equals("commands:")) {
                        role = role + " " + message[j];
                        j++;
                    }
                    for (int i = j + 1; i < message.length; i++) {
                        if (message[i].equals("all")) {
                            roleMap.removeAll(role);
                        } else {
                            roleMap.remove(role, message[i]);
                        }
                    }
                }else {
                    int j = 2;
                    String role = message[1];
                    while (!message[j].equals("commands:")) {
                        role = role + " " + message[j];
                        j++;
                    }
                    for (int i = j + 1; i < message.length; i++) {
                        if (message[i].equals("all")) {
                            for (Command command : CommandsManager.getCommands()) {
                                if(!roleMap.containsEntry(role, command.getName())) {
                                    roleMap.put(role, command.getName());
                                }
                            }
                        } else {
                            if(!roleMap.containsEntry(role, message[i])) {
                                roleMap.put(role, message[i]);
                            }
                        }
                    }
                }
            }else {
                e.getChannel().sendMessage("Please specify role and list commands after commands:. For more info please use !help to see how to use this command").queue();
            }
        }else{
            e.getChannel().sendMessage("Please use !help to see how to use this command").queue();
        }

    }
    public static boolean roleCheck(List<Role> roles, String name){
        for(Role role : roles){
            if(roleMap.get(role.getName()).contains(name)){
                return true;
            }
        }
        if (roleMap.get("everyone").contains(name)){
            return true;
        }
        return false;
    }
}
