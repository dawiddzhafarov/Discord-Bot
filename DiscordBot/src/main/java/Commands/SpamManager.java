package Commands;

import Commands_utilities.Command;
import Passive.Spam;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.util.Arrays;

public class SpamManager extends Command {
    public SpamManager(){
        name = "spam";
        help = "This command allows you to manage spam. Use !spam set messages to enter number of messages that will activate check for spam" +
                "Use !spam set time to set the maximal time between messages to be checked. Use !spam set breaches to set the number of breaches" +
                "before an user will be muted";
        aliases = Arrays.asList("s","spm");
    }

    protected void execute(MessageReceivedEvent e){
        String[] args = e.getMessage().getContentRaw().split(" ");

        try {
            if (args.length==1){
                e.getChannel().sendMessage("Anty-spam jest "+ Spam.getSpamStatus()).queue();
            }
            else if (args[1].equalsIgnoreCase("set") && args[2].equalsIgnoreCase("messages")){
                int nr_messages = Integer.parseInt(args[3]);
                Spam.setMessages(nr_messages);
                e.getChannel().sendMessage("Ilość wiadomości naruszających politykę anty-spam została zmieniona na "+ nr_messages).queue();
            }
            else if (args[1].equalsIgnoreCase("set") && args[2].equalsIgnoreCase("time")){
                int time = Integer.parseInt(args[3]);
                Spam.setTime_exceed(time);
                e.getChannel().sendMessage("Czas między wiadomościami naruszającymi politykę anty-spam został zmnieniony na "+ time).queue();

            }
            else if ((args[1].equalsIgnoreCase("set")) && (args[2].equalsIgnoreCase("breaches"))) {
                int nr_breaches = Integer.parseInt(args[3]);
                Spam.setBreachesLimit(nr_breaches);
                e.getChannel().sendMessage("Limit naruszeń polityki anty-spam został zmieniony na " + nr_breaches).queue();
            }
            else if (args[1].equalsIgnoreCase("toggle")){
                Spam.toggleSpam();
                if(Spam.getSpam()) e.getChannel().sendMessage("Spam został włączony").queue();
                else e.getChannel().sendMessage("Spam został wyłączony").queue();
            }
        } catch (Exception exc){
            e.getChannel().sendMessage("Niepoprawna składnia komendy! Wpisz !help w celu uzyskania infomracji").queue();
        }
    }
}
