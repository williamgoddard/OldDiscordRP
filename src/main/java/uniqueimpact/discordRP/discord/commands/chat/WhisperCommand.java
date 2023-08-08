package uniqueimpact.discordRP.discord.commands.chat;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import uniqueimpact.discordRP.discord.commands.Command;
import uniqueimpact.discordRP.discord.utils.WebhookManager;
import uniqueimpact.discordRP.things.Chara;
import uniqueimpact.discordRP.things.Room;
import uniqueimpact.discordRP.utils.InvalidInputException;

public class WhisperCommand implements Command {

    @Override
    public MessageCreateData run(SlashCommandInteractionEvent command) {

        String characterName = command.getOption("character").getAsString();
        String message = command.getOption("message").getAsString();

        String channelId = command.getChannel().getId();
        Chara character;
        try {
            character = roleplay.findCharacterByChannel(channelId);
        } catch (InvalidInputException e) {
            return new MessageCreateBuilder().setContent(e.getMessage()).build();
        }

        Room room = character.getRoom();

        Chara otherCharacter;
        try {
            otherCharacter = room.findCharacter(characterName, false);
        } catch (InvalidInputException e) {
            return new MessageCreateBuilder().setContent(e.getMessage()).build();
        }

        if (character == otherCharacter) {
            return new MessageCreateBuilder().setContent("You can't whisper to yourself.").build();
        }

        for (Chara c : room.getCharacters()) {
            if (c != character && c != otherCharacter) {
                WebhookManager.send("*" + character.getDisplayName() + " whispers something to " + otherCharacter.getDisplayName() + ".*", character, c);
            }
        }

        WebhookManager.send("*" + character.getDisplayName() + " whispers to you:*\n" + message, character, otherCharacter);

        return new MessageCreateBuilder().setContent("You whisper to " + otherCharacter.getDisplayName() + ":\n" + message).build();

    }

}
