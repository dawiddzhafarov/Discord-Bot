package Commands;

import Commands_utilities.Command;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.RoleAction;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Roles extends Command {
    public Roles() {
        name = "role";
        help = "This command creates new role: Type !role create <RoleName> 100,100,100 to create new role and set it's color to RGB:100,100,100" +
                "Type !role remove <RoleName> @mentionedUsers to remove role from users. Type !role delete <RoleNames> to delete roles from server";
        aliases = Arrays.asList("r", "ro");
    }

    @Override
    protected void execute(MessageReceivedEvent e) {
        String[] args = e.getMessage().getContentRaw().split(" ");
        System.out.println(args);
        if(args.length <= 2){
            e.getChannel().sendMessage("Wpisz !help w celu uzyskania pomocy!").queue();
        }
        if (args[1].equalsIgnoreCase("create")) {
            try {
                RoleAction newrole = e.getGuild().createRole();
                newrole.setName(args[2]);
                String[] colors = args[3].split(",");
                Integer[] rgb = new Integer[3];
                for (int i = 0; i < 3; i++) {
                    rgb[i] = Integer.parseInt(colors[i]);
                }
                Color color = new Color(rgb[0], rgb[1], rgb[2]);
                newrole.setColor(color);
                newrole.complete();
                //dodac setPermission do argumentów i w kodzie
            } catch (Exception exc) {
                e.getChannel().sendMessage("Błędne dane").queue();
            }
        } else if ((args[1].equalsIgnoreCase("remove")) && (args.length >= 4)){
            try {
                List<Role> roles = e.getGuild().getRolesByName(args[2], true);
                System.out.println(roles.get(0));
                List<Member> members = e.getMessage().getMentionedMembers();
                for (Member member : members) {
                    e.getGuild().removeRoleFromMember(member, roles.get(0)).queue();
                    e.getChannel().sendMessage("Usunięto rolę " + roles.get(0).getName() + " używkownikowi " + member.getUser().getName()).queue();
                }
            } catch (Exception exc2) {
                e.getChannel().sendMessage("Błędne nazwy roli bądź użytkowników!").queue();
            }
        } else if ((args[1].equalsIgnoreCase("delete")) && (args.length == 3)) {
            try {
                e.getGuild().getRolesByName(args[2], true).get(0).delete().queue();
                e.getChannel().sendMessage("Usunięto rolę " + e.getGuild().getRolesByName(args[2], true).get(0).getName()).queue();
            } catch (Exception ex) {
                e.getChannel().sendMessage("Nie ma takiej roli!").queue();
            }
        } else if ((args[1].equalsIgnoreCase("delete")) && (args.length > 3)) {
            try {
                for (int i = 2; i < args.length; i++) {
                    e.getGuild().getRolesByName(args[i], true).get(0).delete().queue();
                    e.getChannel().sendMessage("Usunięto rolę " + e.getGuild().getRolesByName(args[i], true).get(0).getName()).queue();
                }
            } catch (Exception ex2) {
                e.getChannel().sendMessage("Błędne nazwy ról!").queue();
            }
        }
    }
}

