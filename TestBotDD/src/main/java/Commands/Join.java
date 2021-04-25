package Commands;

import Commands_utilities.Command;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import java.util.Arrays;

public class Join extends Command {
    public Join() {
        name = "join";
        help = "Use !join to tell bot to join a voice channel you're currently in.";
        aliases = Arrays.asList("j", "jo");
    }

    protected void execute(MessageReceivedEvent e) {
        //getting info about bot and member
        final Member self = e.getGuild().getSelfMember();
        final GuildVoiceState selfvoiceState = self.getVoiceState();
        final Member member = e.getMember();
        assert member != null;
        final GuildVoiceState memberVoiceState = member.getVoiceState();
        assert memberVoiceState != null;
        final AudioManager audioManager = e.getGuild().getAudioManager();
        final VoiceChannel memberChannel = memberVoiceState.getChannel();
        //audioManager.openAudioConnection(memberChannel); przesunąłem to nizej
        assert memberChannel != null;
        assert selfvoiceState != null;


        if(memberChannel!=null) {
            audioManager.openAudioConnection(memberChannel);
            if (selfvoiceState.getChannel() == memberVoiceState.getChannel())
                e.getChannel().sendMessage("I'm already with you!").queue(); //message if already with member
            else
                e.getChannel().sendMessageFormat("Connecting to a voice channel \uD83D\uDD0A", memberChannel.getName()).queue(); //if not, then join
        }else {
            e.getChannel().sendMessage("Please join a voice channel before using this command.").queue();
        }
    }
}
