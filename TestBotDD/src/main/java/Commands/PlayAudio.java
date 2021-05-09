package Commands;

import Commands_utilities.Command;
import LavaPlayer.GuildMusicManager;
import LavaPlayer.PlayerManager;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class PlayAudio extends Command {
    public PlayAudio(){
        name = "play";
        help = "Use !play <yt_link> or !play <song_name> to play a song. Use !play info to get info about current song."+ "\n" +
        "Use !play to resume played song.";
        aliases = Arrays.asList("pl", "p");
    }
    @Override
    protected void execute(MessageReceivedEvent e) {
        String[] args = e.getMessage().getContentRaw().split("\\s+");
        if (args.length == 1){
            //botJoin(e);
            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(e.getGuild());
            musicManager.getTrackScheduler().getPlayer().setPaused(false); //resuming song
            e.getChannel().sendMessage("The song has been resumed").queue();

        } else if (args[1].equalsIgnoreCase("info")){ //get info
            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(e.getGuild());
            final AudioPlayer audioPlayer = musicManager.getAudioPlayer();
            final AudioTrack track = audioPlayer.getPlayingTrack();
            if(track == null){
                e.getChannel().sendMessage("There is no track playing!").queue();
            } else {
                AudioTrackInfo info = track.getInfo();
                String author = info.author;
                String title = info.title;
                long duration = info.length/1000;
                long minutes = TimeUnit.SECONDS.toMinutes(duration);
                long seconds = TimeUnit.SECONDS.toSeconds(duration) - (minutes * 60);
                String link_url = info.uri;
                e.getChannel().sendMessageFormat("Currently playing: " + title + " by " + author + "; Duration: " + minutes + ":" + seconds + " " + link_url).queue();
            }
        } else {
            botJoin(e); //if not in voice channel, then join

            String link =""; //searching yt for a song
            int argsLen = args.length;
            for (int i = 1; i<argsLen;i++){
                link += args[i] + " ";
            }

            if(!isUrl(link)){
                link = "ytsearch:" + link;
            }

            PlayerManager.getInstance().loadAndPlay(e.getTextChannel(), link);//playing song
        }
    }

    public void botJoin(MessageReceivedEvent e) {

        final Member self = e.getGuild().getSelfMember(); //bot
        final GuildVoiceState selfvoiceState = self.getVoiceState(); //botVoiceState
        final Member member = e.getMember(); //member
        assert member != null;
        final GuildVoiceState memberVoiceState = member.getVoiceState(); //memberVoiceState
        assert memberVoiceState != null;

        if (!memberVoiceState.inVoiceChannel()) { //Member not in voice channel
            e.getChannel().sendMessage("You need to join a voice channel!").queue();
            return;
        } else {
            final AudioManager audioManager = e.getGuild().getAudioManager();
            final VoiceChannel memberChannel = memberVoiceState.getChannel();
            audioManager.openAudioConnection(memberChannel);
            assert memberChannel != null;
            assert selfvoiceState != null;
            if (selfvoiceState.inVoiceChannel()) return;
            else e.getChannel().sendMessageFormat("Connecting to the voice channel", memberChannel.getName()).queue();
        }
    }
    private boolean isUrl(String url) { //checking if link is an url
        try {
            new URL(url);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

}
