package uniqueimpact.discordRP.discord.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class TakeCommand implements Command {

    @Override
    public String run(SlashCommandInteractionEvent command) {
        String itemName = command.getOption("item").getAsString();
        int itemNum;
        try {
            itemNum = command.getOption("num").getAsInt();
        } catch (NullPointerException e) {
            itemNum = 1;
        }
        command.reply("This command will take item: " + itemName + " " + itemNum).queue();
        return null;
    }

}
