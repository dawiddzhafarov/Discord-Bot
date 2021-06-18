package Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PingPong extends ListenerAdapter {
    String url = "https://discord.com/oauth2/authorize?client_id=%s&scope=bot";

    @Override
    public void onMessageReceived(MessageReceivedEvent e){
        if(e.getMessage().getContentRaw().equals(Prefix.prefix+"ping")) {
            e.getChannel().sendMessage("pong").queue();
        }
        else if (e.getMessage().getContentRaw().equals(Prefix.prefix+"time")) {
            e.getChannel().sendMessage(time()).queue();
        }
        else if (e.getMessage().getContentRaw().equals(Prefix.prefix+"invite")) {
            e.getChannel().sendMessage(String.format(url, e.getJDA().getSelfUser().getId())).queue();
        }
        if(e.getMessage().getContentRaw().equals(Prefix.prefix+"quit")) {
            e.getChannel().getJDA().shutdown();
        }
        if(e.getMessage().getContentRaw().equals(Prefix.prefix+"pong")) {
            e.getChannel().sendMessage("ping").queue();
        }
        if(e.getMessage().getContentRaw().equals(Prefix.prefix+"name")) {
            e.getChannel().sendMessage(e.getAuthor().getName()).queue();
        }
        if(e.getMessage().getContentRaw().equals(Prefix.prefix+"info")) {
            EmbedBuilder info = new EmbedBuilder();
            info.setTitle("Testowy Bot");
            info.setDescription("Jakieś info o tym bocie");
            info.addField("Twórcy","TurboFirma",false);
            info.setColor(0xd4901c);
            e.getChannel().sendMessage(info.build()).queue();
            info.clear();
        }
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent e){
        Member member = e.getMember();
        String name = e.getUser().getName();
        member.getUser().openPrivateChannel().queue(channel -> {
            channel.sendMessage("Hello " + name + "!").queue();
        });
    }

    public String time() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }
}
