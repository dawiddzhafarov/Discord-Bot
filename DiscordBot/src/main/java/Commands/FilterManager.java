package Commands;

import Commands_utilities.Command;
import Passive.Filter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
public class FilterManager  extends Command {

    public FilterManager() {
        name = "filter";
        help = "Type "+Prefix.prefix+"filter for state of the filter"+"\n"+
                "Type "+Prefix.prefix+"filter add <swears> to add banned words"+"\n"+
                "Example: "+Prefix.prefix+"filter add fish dummy lol"+"\n"+
                "Type "+Prefix.prefix+"filter remove <swear> to remove word from the list"+"\n"+
                "Example: "+Prefix.prefix+"filter remove fuck"+"\n"+
                "Type "+Prefix.prefix+"toggle to toggle state of the filter"+"\n"+
                "Type "+Prefix.prefix+"toggle message to toggle channel message"+"\n"+
                "Type "+Prefix.prefix+"toggle kick to toggle if user should be kicked after last breach"+ "\n"+
                "Type "+Prefix.prefix+"toggle ban to toggle if user should be banned after last breach"+"\n"+
                "Type "+Prefix.prefix+"filter mutedtime <times in minutes> to set time outs after breaches"+"\n"+
                "Example: "+Prefix.prefix+"filter mutedtime 0.1 0.2 0.5 1 18"+"\n"+
                "Type "+Prefix.prefix+"filter mutedtime to see time outs after breaches"+"\n"+
                "Type "+Prefix.prefix+"filter period <time in minutes> to set time period in which filter counts breaches"+"\n"+
                "Example: "+Prefix.prefix+"filter period 30"+"\n"+
                "Type "+Prefix.prefix+"filter period to see period in which filter counts breaches"+"\n"+
                "After their 6+ breach user will be:"+"\n"+ "If kick=off timeout for 18 minutes"+"\n"+ "If kick=on but ban=off kicked "+"\n"+"If kick=on but ban=on banned"+"\n"+
                "Type "+Prefix.prefix+"filter list for list of banned words";
        aliases = Arrays.asList("f","flt");
    }

    @Override
    protected void execute(MessageReceivedEvent e) {

        String messageRaw = e.getMessage().getContentRaw();
        String[] message = messageRaw.split(" ");
        if(message.length == 1){
            e.getChannel().sendMessage("Fiter is used to moderate use of banned words.").queue();

            if(Filter.isFilter()) {
                e.getChannel().sendMessage("Filter is turned on.").queue();
            }else {
                e.getChannel().sendMessage("Filter is turned off.").queue();
            }
            if(Filter.isCommunicat()) {
                e.getChannel().sendMessage("Channel message is turned on.").queue();
            }else {
                e.getChannel().sendMessage("Channel message  is turned off.").queue();
            }
            if(Filter.isKick()) {
                e.getChannel().sendMessage("Kicking is turned on.").queue();
            }else {
                e.getChannel().sendMessage("Kicking is turned off.").queue();
            }
            if(Filter.isBan()) {
                e.getChannel().sendMessage("Banning is turned on.").queue();
            }else {
                e.getChannel().sendMessage("Banning is turned off.").queue();
            }

        }else if (message[1].equalsIgnoreCase("add")){
            for(int i = 2; i<message.length;i++) {
                Filter.add(message[i]);
            }
            e.getChannel().sendMessage("Swears has been added.").queue();
        }else if(message[1].equalsIgnoreCase("remove")){
            for(int i = 2; i<message.length;i++) {
                if(!Filter.delete(message[i])){
                    e.getChannel().sendMessage("Phrase isn't on the list.").queue();
                }
            }
        }else if(message[1].equalsIgnoreCase("toggle")){
            if(message.length == 2){
                Filter.setFilter(!Filter.isFilter());
                if(Filter.isFilter()) {
                    e.getChannel().sendMessage("Filter has been turned on.").queue();
                }else {
                    e.getChannel().sendMessage("Filter has been turned off.").queue();
                }
            }else if(message[2].equalsIgnoreCase("message")) {
                Filter.setCommunicat(!Filter.isCommunicat());
                if(Filter.isCommunicat()) {
                    e.getChannel().sendMessage("Channel message has been turned on.").queue();
                }else {
                    e.getChannel().sendMessage("Channel message  has been turned off.").queue();
                }
            }else if(message[2].equalsIgnoreCase("kick")){
                Filter.setKick(!Filter.isKick());
                if(Filter.isKick()) {
                    e.getChannel().sendMessage("Kicking has been turned on.").queue();
                }else {
                    e.getChannel().sendMessage("Kicking has been turned off.").queue();
                }
            }else if(message[2].equalsIgnoreCase("ban")) {
                Filter.setBan(!Filter.isBan());
                if(Filter.isBan()) {
                    e.getChannel().sendMessage("Banning has been turned on.").queue();
                }else {
                    e.getChannel().sendMessage("Banning has been turned off.").queue();
                }
            }

        }else if(message[1].equalsIgnoreCase("mutedtime")){
            if(message.length==2){
                e.getChannel().sendMessage("Filter muted time is set to: "+Filter.getMutedTime()).queue();
            }else {
                List<Double> times = new LinkedList<>();
                for (int i = 2; i < message.length; i++) {
                    try {
                        times.add(Double.valueOf(message[i]));
                    } catch (Exception IllegalArgumentException) {
                        e.getChannel().sendMessage("Please use use format provided in following example: !filter mutedtime 0.1 0.2 1 10").queue();
                        break;
                    }
                }
                Filter.setMutedTime(times);
            }
        }else if(message[1].equalsIgnoreCase("list")){
            e.getChannel().sendMessage("The following words are banned on this server: "+Filter.getSwears().toString()).queue();
        }else if(message[1].equalsIgnoreCase("period")){
            if(message.length==2){
                e.getChannel().sendMessage("Filter period is set to: "+Filter.getTimePeriode()).queue();
            }else {
                try {
                    Filter.setTimePeriode(Double.parseDouble(message[2]));
                } catch (Exception IllegalArgumentException) {
                    e.getChannel().sendMessage("Please use use format provided in following example: !filter period 0.2").queue();
                }
            }
        }
    }
}
