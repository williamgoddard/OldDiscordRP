package uniqueimpact.discordRP.discord.commands.fun;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import uniqueimpact.discordRP.discord.commands.Command;

import java.util.Random;

public class RollCommand implements Command {

    private static Random random = new Random();

    @Override
    public void run(SlashCommandInteractionEvent command) {

        Integer sides = command.getOption("sides") != null ? command.getOption("sides").getAsInt() : 20;
        Integer count = command.getOption("count") != null ? command.getOption("count").getAsInt() : 1;

        String output = "Rolling `" + count + "` dice with `" + sides + "` sides:\n";
        int total = 0;
        for (int i = 0; i < count; i++) {
            int roll = random.nextInt(sides) + 1;
            output += "`" + roll + "` ";
            total += roll;
        }
        output += "\nTotal: `" + total + "`";

        command.reply(output).queue();

    }

}
