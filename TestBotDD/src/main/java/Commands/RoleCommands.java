package Commands;

import Commands_utilities.Command;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.RoleAction;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class RoleCommands  extends Command {

    private static ListMultimap<String, String> roleMap = ArrayListMultimap.create();

    public RoleCommands() {
        name = "rolecommands";
        help = "Use !rolecommands <RoleName> commands: <command names> to allow chosen role use specified commands" +
                "Example: !rolecommands Mod commands: clear rolepost kick  to allow users with role Mod to use commands clear, rolepost and kick";
        aliases = Arrays.asList("rc", "rolecom");
    }

    @Override
    protected void execute(MessageReceivedEvent e) {
        String[] message = e.getMessage().getContentRaw().split(" ");

        if(message.length > 1){
            if(message.length > 2 && e.getMessage().getContentRaw().contains("commands:")){
                int j = 2;
                String role = message[1];
                while (!message[j].equals("commands:")) {
                    role = role + " " + message[j];
                    j++;
                }
                for(int i=j+1;i<message.length;i++){
                    roleMap.put(role,message[i]);
                }
            }else {
                e.getChannel().sendMessage("placeholder ale coś aby dodać komendy").queue();
            }
        }else{
            e.getChannel().sendMessage("placeholder").queue();
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
