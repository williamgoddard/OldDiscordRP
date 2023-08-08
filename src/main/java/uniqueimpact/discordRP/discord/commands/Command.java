package uniqueimpact.discordRP.discord.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import uniqueimpact.discordRP.things.Roleplay;

public interface Command {

    static Roleplay roleplay = Roleplay.getInstance();

    public void run(SlashCommandInteractionEvent command);

}
