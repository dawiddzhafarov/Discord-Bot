package Passive;

import Commands.RolePost;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RoleReactions extends ListenerAdapter {

    final long channelid = 818779964098084905L;
    final long roleid = 818781151388106752L;

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent e) {
        String message = e.getChannel().retrieveMessageById(e.getMessageId()).complete().getContentRaw();
        if (RolePost.getPostMap().containsKey(message)) {
            Member reactee = e.getMember();
            for(String whichRole: RolePost.getPostMap().get(message)) {
                if(whichRole.split("=")[0].equals(e.getReactionEmote().getEmoji())) {
                    if(e.getGuild().getRolesByName(whichRole.split("=")[1].replace("=",""),true).size()>0) {
                        Role role = e.getGuild().getRolesByName(whichRole.split("=")[1].replace("=", ""), true).get(0);
                        e.getGuild().addRoleToMember(reactee, role).queue();
                    }
                }
            }
        }
    }

    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent e){
        String message = e.getChannel().retrieveMessageById(e.getMessageId()).complete().getContentRaw();
        if (RolePost.getPostMap().containsKey(message)) {
            Member reactee = e.getMember();
            for(String whichRole: RolePost.getPostMap().get(message)) {
                if(whichRole.split("=")[0].equals(e.getReactionEmote().getEmoji())) {
                    if(e.getGuild().getRolesByName(whichRole.split("=")[1].replace("=",""),true).size()>0) {
                        Role role = e.getGuild().getRolesByName(whichRole.split("=")[1].replace("=", ""), true).get(0);
                        e.getGuild().removeRoleFromMember(e.getUserId(), role).queue();
                    }
                }
            }
        }
    }
}
