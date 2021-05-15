//import Commands_utilities.Commands.Commands.Prefix;

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

    public static void main(String args[]) throws Exception {
        JDABuilder jdabuilder = JDABuilder.createDefault("ODE4MTE5NDQ3NTYxNjMzNzk0.YETbHA.Gv99DsiwyhAzL53kL3G2eDQCNzw");
        jda = null;
        List<GatewayIntent> gatewayIntents = new ArrayList<>();
        gatewayIntents.add(GatewayIntent.GUILD_MEMBERS);
        gatewayIntents.add(GatewayIntent.GUILD_MESSAGES);
        gatewayIntents.add(GatewayIntent.GUILD_VOICE_STATES);

        /*
        straciłem cierpliwość wiec albo znajdziemy sposób na naprawę tego albo stworzymy własną wersję :P
        CommandClientBuilder builder = new CommandClientBuilder();

        builder.setPrefix("!");
        builder.setHelpWord("help");


        CommandClient client = builder.build();

        jdabuilder.addEventListeners(client);
        //client.addCommand(new Commands_utilities.Commands.Commands.Prefix);

        */
        CommandsManager manager = new CommandsManager("!","help");




        PingPong pingpong = new PingPong();
        RoleReactions role = new RoleReactions();
        Prefix prefix = new Prefix(manager);
        Filter filter = new Filter();
        //Kick kick = new Kick();
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
      //  jdabuilder.disableCache(EnumSet.of(
       //         CacheFlag.CLIENT_STATUS,
        //        CacheFlag.ACTIVITY,
        //        CacheFlag.EMOTE
       // ));
        jdabuilder.enableCache(CacheFlag.VOICE_STATE);
        jdabuilder.enableIntents(gatewayIntents);
        jdabuilder.addEventListeners(role);
        jdabuilder.addEventListeners(pingpong);
        //jdabuilder.addEventListeners(prefix);
        jdabuilder.addEventListeners(filter);
        //jdabuilder.addEventListeners(kick);
        //jdabuilder.addEventListeners(clear);
        //jdabuilder.addEventListeners(manager);
        jdabuilder.addEventListeners(spam);
        jdabuilder.addEventListeners(customCommandsSniffer);

        //jdabuilder.setActivity(Activity.playing("Type: "+manager.getPrefix()+manager.getHelpWord()+ " for command list :D"));
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
