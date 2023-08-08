package uniqueimpact.discordRP.discord.commands.player_customise;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import uniqueimpact.discordRP.discord.commands.Command;
import uniqueimpact.discordRP.things.Chara;
import uniqueimpact.discordRP.utils.InvalidInputException;

public class SetCommand implements Command {

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

        String path = command.getFullCommandName();

        switch (path) {

            case "set description":
                description(command, character);
                break;

            case "set name":
                name(command, character);
                break;

            case "set picture":
                picture(command, character);
                break;

            default:
                command.reply("Invalid command path: " + path).queue();

        }

    }

    private void picture(SlashCommandInteractionEvent command, Chara character) {

        String currentPicture = character.getPicture();

        TextInput pictureTextInput = TextInput.create("newPicture", "Character Picture", TextInputStyle.SHORT)
                .setMinLength(1)
                .setMaxLength(200)
                .setValue(currentPicture)
                .build();

        Modal replyModal = Modal.create("SCP " + character.getChannel(), "Set Character Picture")
                .addComponents(ActionRow.of(pictureTextInput))
                .build();

        command.replyModal(replyModal).queue();

    }

    private void name(SlashCommandInteractionEvent command, Chara character) {

        String currentName = character.getDisplayName();

        TextInput nameTextInput = TextInput.create("newName", "Character Display Name", TextInputStyle.SHORT)
                .setMinLength(1)
                .setMaxLength(32)
                .setValue(currentName)
                .build();

        Modal replyModal = Modal.create("SCN " + character.getChannel(), "Set Character Display Name")
                .addComponents(ActionRow.of(nameTextInput))
                .build();

        command.replyModal(replyModal).queue();

    }

    private void description(SlashCommandInteractionEvent command, Chara character) {

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
