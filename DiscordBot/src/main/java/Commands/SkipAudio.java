package Commands;

import Commands_utilities.Command;
import LavaPlayer.GuildMusicManager;
import LavaPlayer.PlayerManager;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.util.Arrays;

public class SkipAudio extends Command {
    public SkipAudio() {
        name = "skip";
        help = "Use !skip to skip current song. You need to be in the same voice channel as bot to skip a song.";
        aliases = Arrays.asList("s", "sk");
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

        if(memberVoiceState.getChannel() == selfvoiceState.getChannel()) { //check if in the same voice channel

            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(e.getGuild());
            final AudioPlayer audioPlayer = musicManager.getAudioPlayer();

            if (audioPlayer.getPlayingTrack() == null) { //if no track playing
                e.getChannel().sendMessage("There is no track playing!").queue();
                return;
            } else { //if is,then skip
                musicManager.getTrackScheduler().nextTrack();
                e.getChannel().sendMessage("Skipped current track").queue();
            }
        } else {
            e.getChannel().sendMessage("You need to be in the same voice channel to skip a song!").queue();
        }
    }
}
