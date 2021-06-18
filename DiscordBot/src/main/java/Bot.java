import Commands.*;
import Commands_utilities.CommandsManager;
import Passive.CustomCommandsSniffer;
import Passive.Filter;
import Passive.RoleReactions;
import Passive.Spam;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;

public class Bot {
    static public JDA jda;

    public static void main(String args[]) {
        JDABuilder jdabuilder = JDABuilder.createDefault("ODE4MTE5NDQ3NTYxNjMzNzk0.YETbHA.0SNt4HdUP9qbuXdbsJesCnu47ig");
        jda = null;
        List<GatewayIntent> gatewayIntents = new ArrayList<>();
        gatewayIntents.add(GatewayIntent.GUILD_MEMBERS);
        gatewayIntents.add(GatewayIntent.GUILD_MESSAGES);
        gatewayIntents.add(GatewayIntent.GUILD_VOICE_STATES);

        CommandsManager manager = new CommandsManager("!","help");

        PingPong pingpong = new PingPong();
        RoleReactions role = new RoleReactions();
        Prefix prefix = new Prefix(manager);
        Filter filter = new Filter();
        FilterManager filterManager = new FilterManager();
        SpamManager spamManager = new SpamManager();
        CustomCommandsSniffer customCommandsSniffer = new CustomCommandsSniffer();
        Clear clear = new Clear();
        Quote quote = new Quote();
        Kick kick = new Kick();
        Roles roles = new Roles();
        Mute mute = new Mute();
        Spam spam = new Spam();
        PlayAudio play = new PlayAudio();
        Join join = new Join();
        StopAudio stop = new StopAudio();
        SkipAudio skipAudio = new SkipAudio();
        Leave leave = new Leave();
        CustomCommands customCommands = new CustomCommands();
        RolePost rolePost = new RolePost();
        RoleCommands roleCommands = new RoleCommands();

        manager.addCommand(leave);
        manager.addCommand(skipAudio);
        manager.addCommand(stop);
        manager.addCommand(join);
        manager.addCommand(play);
        manager.addCommand(quote);
        manager.addCommand(prefix);
        manager.addCommand(clear);
        manager.addCommand(kick);
        manager.addCommand(roles);
        manager.addCommand(mute);
        manager.addCommand(filterManager);
        manager.addCommand(spamManager);
        manager.addCommand(customCommands);
        manager.addCommand(rolePost);
        manager.addCommand(roleCommands);

        jdabuilder.enableCache(CacheFlag.VOICE_STATE);
        jdabuilder.enableIntents(gatewayIntents);
        jdabuilder.addEventListeners(role);
        jdabuilder.addEventListeners(pingpong);
        jdabuilder.addEventListeners(filter);
        jdabuilder.addEventListeners(spam);
        jdabuilder.addEventListeners(customCommandsSniffer);

        try {
            jda = jdabuilder.build();
            manager.addJDA(jda);
            jda.addEventListener(manager);
            jda.getPresence().setActivity(Activity.playing("Type: "+manager.getPrefix()+manager.getHelpWord()+ " for command list :D"));
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}
