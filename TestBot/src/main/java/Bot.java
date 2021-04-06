import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;

public class Bot {

    public static void main(String args[]) throws Exception {
        JDABuilder jdabuilder = new JDABuilder().createDefault("ODE4MTE5NDQ3NTYxNjMzNzk0.YETbHA.Gv99DsiwyhAzL53kL3G2eDQCNzw");
        JDA jda = null;
        List<GatewayIntent> gatewayIntents = new ArrayList<>();
        gatewayIntents.add(GatewayIntent.GUILD_MEMBERS);


        PingPong pingpong = new PingPong();
        RoleReactions role = new RoleReactions();
        Prefix prompt = new Prefix();

        jdabuilder.enableIntents(gatewayIntents);
        jdabuilder.addEventListeners(role);
        jdabuilder.addEventListeners(pingpong);
        jdabuilder.addEventListeners(prompt);
        jdabuilder.setActivity(Activity.playing("o wielką stawkę"));
        try {
            jda = jdabuilder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }

    }
}
