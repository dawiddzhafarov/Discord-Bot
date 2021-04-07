//import Commands_utilities.Prefix;

import Commands.Quote;
import Commands_utilities.Clear;
import Commands_utilities.CommandsManager;
import Commands_utilities.Kick;
import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;


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
        //client.addCommand(new Commands_utilities.Prefix);

        */
        CommandsManager manager = new CommandsManager("!","help");




        PingPong pingpong = new PingPong();
        RoleReactions role = new RoleReactions();
        Prefix prefix = new Prefix(manager);
        Filter filter = new Filter();
        Kick kick = new Kick();
        Clear clear = new Clear();

        Quote quote = new Quote();

        manager.addCommand(quote);

        jdabuilder.enableIntents(gatewayIntents);
        jdabuilder.addEventListeners(role);
        jdabuilder.addEventListeners(pingpong);
        jdabuilder.addEventListeners(prefix);
        jdabuilder.addEventListeners(filter);
        jdabuilder.addEventListeners(kick);
        jdabuilder.addEventListeners(clear);
        //jdabuilder.addEventListeners(manager);

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
