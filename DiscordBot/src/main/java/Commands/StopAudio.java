package Commands;

import Commands_utilities.Command;
import LavaPlayer.GuildMusicManager;
import LavaPlayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.util.Arrays;

public class StopAudio extends Command {
    public StopAudio() {
        name = "stop";
        help = "Use !stop to stop a song. You need to be in the same voice channel as bot.";
        aliases = Arrays.asList("sto", "st");
    }

    public void execute(MessageReceivedEvent e){
        //get info about bot and member
        final Member self = e.getGuild().getSelfMember();
        final GuildVoiceState selfvoiceState = self.getVoiceState();
        final Member member = e.getMember();
        assert member != null;
        final GuildVoiceState memberVoiceState = member.getVoiceState();
        assert memberVoiceState != null;
        assert selfvoiceState != null;

        if(memberVoiceState.getChannel() == selfvoiceState.getChannel()){ //if in the same voice channel
            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(e.getGuild());
            musicManager.getTrackScheduler().getPlayer().setPaused(true); //pause a song
            e.getChannel().sendMessage("The song has been stopped.").queue();
        } else {
            e.getChannel().sendMessage("You need to be in the same voice channel as bot to stop a song!").queue();
        }
    }
}
