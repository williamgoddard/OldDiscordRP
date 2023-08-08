package uniqueimpact.discordRP.discord.commands.player_customise;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import uniqueimpact.discordRP.discord.commands.Command;
import uniqueimpact.discordRP.things.Chara;
import uniqueimpact.discordRP.utils.InvalidInputException;

public class SetDescCommand implements Command {

    @Override
    public void run(SlashCommandInteractionEvent command) {

        String channelId = command.getChannel().getId();
        Chara character;
        try {
            character = roleplay.findCharacterByChannel(channelId);
        } catch (InvalidInputException e) {
            command.reply(e.getMessage()).queue();
            return;
        }

        String currentDesc = character.getDescription();

        TextInput descTextInput = TextInput.create("newDesc", "Character Description", TextInputStyle.PARAGRAPH)
                .setMinLength(1)
                .setMaxLength(1500)
                .setValue(currentDesc)
                .build();

        Modal replyModal = Modal.create("SCD " + character.getChannel(), "Set Character Description")
                .addComponents(ActionRow.of(descTextInput))
                .build();

        command.replyModal(replyModal).queue();

    }

}
