import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RoleReactions extends ListenerAdapter {

    final long channelid = 818779964098084905L;
    final long roleid = 818781151388106752L;

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent e) {
        if (e.getTextChannel().getIdLong() == channelid) {
            Member reactee = e.getMember();
            e.getGuild().addRoleToMember(reactee, e.getJDA().getRoleById(roleid)).queue();
        }
    }

    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent e){
        if (e.getTextChannel().getIdLong() == channelid) {
            e.getGuild().removeRoleFromMember(e.getUserId(), e.getJDA().getRoleById(roleid)).queue();
        }
    }
}
