package Commands;

import Commands_utilities.Command;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.RoleAction;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Roles extends Command {
    public Roles(){
        name = "role";
        help ="This command creates new role, after command prefix enter name of a new role, then numbers(0-255) separated by ',' without spaces!";
        aliases = Arrays.asList("r","ro");
    }

    @Override
    protected void execute(MessageReceivedEvent e) {
        try {
            String[] args = e.getMessage().getContentRaw().split("\\s+");
            if (args.length < 2) {
                e.getChannel().sendMessage("Specify name for a new role").queue();
            } else {
                RoleAction newrole = e.getGuild().createRole();
                newrole.setName(args[1]);
                String[] colors = args[2].split(",");
                Integer[] rgb = new Integer[3];
                for (int i = 0; i < 3; i++) {
                    rgb[i] = Integer.parseInt(colors[i]);
                }
                Color color = new Color(rgb[0], rgb[1], rgb[2]);
                newrole.setColor(color);
                newrole.complete();
                //dodac setPermission do argumentów i w kodzie
            }
        } catch (Exception err){
            err.printStackTrace();
        }
    }
}
