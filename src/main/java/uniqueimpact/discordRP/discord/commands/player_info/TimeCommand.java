package uniqueimpact.discordRP.discord.commands.player_info;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import uniqueimpact.discordRP.discord.commands.Command;
import uniqueimpact.discordRP.utils.TimeKeeper;

public class TimeCommand implements Command {

    @Override
    public void run(SlashCommandInteractionEvent command) {
        command.reply( "The current in-game time is " + TimeKeeper.getCurrentTime()).queue();
    }

}
