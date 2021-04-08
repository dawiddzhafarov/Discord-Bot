package Commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Kick extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e){
        String[] args = e.getMessage().getContentRaw().split("\\s+");
        if (args[0].equalsIgnoreCase( Prefix.prefix+ "kick")){
        //if (e.getMessage().getContentRaw().startsWith(args[0])){
            if(e.getMember().hasPermission(Permission.KICK_MEMBERS)) {
                for (Member member : e.getMessage().getMentionedMembers()) {
                    member.kick().queue();
                    e.getChannel().sendMessage("Użytkownik " + e.getMember().getUser().getName() + " wyrzucił " + member.getUser().getName()).queue();
                }
            } else {
                e.getChannel().sendMessage("Nie masz uprawnień do wyrzucania użytkowników").queue();
            }
        }
    }
}
