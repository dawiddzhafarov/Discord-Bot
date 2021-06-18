package Commands;

import Commands_utilities.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.util.Arrays;

public class Mute extends Command {
    public Mute(){
        name = "mute";
        help = "Use !mute @Member to mute specific user. Use !mute -1 @Member to unmute specific user";
        aliases = Arrays.asList("m", "mu");
    }
    @Override
    protected void execute(MessageReceivedEvent e){
        String args[] = e.getMessage().getContentRaw().split("\\s+");
        if(e.getMember().hasPermission(Permission.VOICE_MUTE_OTHERS)) {
            try {
                if (args.length == 2) {
                    Member member = e.getMessage().getMentionedMembers().get(0);
                    member.mute(true).queue();
                    e.getChannel().sendMessage("Wyciszono użytkownika " + e.getMessage().getMentionedMembers().get(0).getUser().getName()).queue();
                } else if ((args.length != 2) && (args[1].equalsIgnoreCase("-1"))) {
                    Member member = e.getMessage().getMentionedMembers().get(0);
                    member.mute(false).queue();
                    e.getChannel().sendMessage("Odciszono użytkownika " + e.getMessage().getMentionedMembers().get(0).getUser().getName()).queue();
                }
            } catch (Exception ex) {
                e.getChannel().sendMessage("Błędna komenda! Wpisz !help w celu uzyskania pomocy!").queue();
            }
        } else {
            e.getChannel().sendMessage("Nie masz uprawnień do wyciszania/odciszania użytkowników").queue();
        }
    }
}
