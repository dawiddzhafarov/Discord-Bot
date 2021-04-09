//import Commands_utilities.Commands.Commands.Prefix;

import Commands.*;
import Commands_utilities.CommandsManager;
import Passive.Filter;
import Passive.Spam;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

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

        Clear clear = new Clear();
        Quote quote = new Quote();
        Kick kick = new Kick();
        Roles roles = new Roles();
        Mute mute = new Mute();
        Spam spam = new Spam();

        manager.addCommand(quote);
        manager.addCommand(prefix);
        manager.addCommand(clear);
        manager.addCommand(kick);
        manager.addCommand(roles);
        manager.addCommand(mute);
        manager.addCommand(filterManager);

        jdabuilder.enableIntents(gatewayIntents);
        jdabuilder.addEventListeners(role);
        jdabuilder.addEventListeners(pingpong);
        //jdabuilder.addEventListeners(prefix);
        jdabuilder.addEventListeners(filter);
        //jdabuilder.addEventListeners(kick);
        //jdabuilder.addEventListeners(clear);
        //jdabuilder.addEventListeners(manager);
        jdabuilder.addEventListeners(spam);

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
