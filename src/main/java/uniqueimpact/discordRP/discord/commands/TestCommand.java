package uniqueimpact.discordRP.discord.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

public class TestCommand implements Command {

    @Override
    public MessageCreateData run(SlashCommandInteractionEvent command) {

        MessageCreateBuilder builder = new MessageCreateBuilder();

        builder.setContent("test");

        builder.setActionRow(StringSelectMenu.create("testMenu")
                .addOption("option1", "Option 1")
                .addOption("option2", "Option 2")
                .addOption("option3", "Option 3")
                .build());

        return builder.build();
    }

}
