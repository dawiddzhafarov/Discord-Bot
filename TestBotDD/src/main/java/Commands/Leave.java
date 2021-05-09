package Commands;

import Commands_utilities.Command;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.Arrays;

public class Leave extends Command {
    public Leave(){
        name = "leave";
        help = "Use !leave to tell bot to leave a voice channel you're currently in.";
        aliases = Arrays.asList("le", "l","begone");
    }

    public void execute(MessageReceivedEvent e){
        //get info about bot and member
        final Member self = e.getGuild().getSelfMember();
        final GuildVoiceState selfvoiceState = self.getVoiceState();
        assert selfvoiceState != null;
        final Member member = e.getMember();
        assert member != null;
        final GuildVoiceState memberVoiceState = member.getVoiceState();
        assert memberVoiceState != null;

        if((selfvoiceState.inVoiceChannel()) && (selfvoiceState.getChannel() == memberVoiceState.getChannel())){ //if in the same voice channel
            final AudioManager audioManager = e.getGuild().getAudioManager();
            audioManager.closeAudioConnection();
            e.getChannel().sendMessage("I have left the voice channel \uD83D\uDD0A").queue();
        } else if (!selfvoiceState.inVoiceChannel()){ //if bot not in a voice channel
            e.getChannel().sendMessage("I am not in a voice channel anymore!").queue();
        } else { //if user not in the same voice channel as bot
            e.getChannel().sendMessage("You have to be in the same voice channel as bot to tell him to leave").queue();
        }
    }
}


