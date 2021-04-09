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
        help ="This command creates new role, after command prefix enter name of a new role, then 3 numbers(0-255) (RGB) separated by ',' without spaces!" +
                " If you want to delete specific roles, use command: !role del <name> <name>..";
        aliases = Arrays.asList("r","ro");
    }

    @Override
    protected void execute(MessageReceivedEvent e) {
        String[] args = e.getMessage().getContentRaw().split("\\s+");
        if (!args[1].equalsIgnoreCase("del")){
            try {
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
            } catch (Exception exc){
                e.getChannel().sendMessage("Błędne dane").queue();
            }
        } else {
            if (args.length == 3) {
                try {
                    e.getGuild().getRolesByName(args[2], true).get(0).delete().queue();
                    e.getChannel().sendMessage("Usunięto rolę " + e.getGuild().getRolesByName(args[2], true).get(0).getName()).queue();
                } catch (Exception ex){
                    e.getChannel().sendMessage("Błędne dane!").queue();
                }
            } else if ((args.length > 3)){
                try {
                    for (int i = 2; i < args.length; i++) {
                        e.getGuild().getRolesByName(args[i], true).get(0).delete().queue();
                        e.getChannel().sendMessage("Usunięto rolę " + e.getGuild().getRolesByName(args[i], true).get(0).getName()).queue();
                    }
                } catch (Exception ex2){
                    e.getChannel().sendMessage("Błędne dane!").queue();
                }
            }
        }
    }
}

