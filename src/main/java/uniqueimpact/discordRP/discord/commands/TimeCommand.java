package uniqueimpact.discordRP.discord.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import uniqueimpact.discordRP.utils.TimeKeeper;

public class TimeCommand implements Command {

    @Override
    public String run(SlashCommandInteractionEvent command) {
        return "The current in-game time is " + TimeKeeper.getCurrentTime();
    }

}
