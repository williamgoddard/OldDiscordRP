package uniqueimpact.discordRP.discord.interactions;

import net.dv8tion.jda.api.interactions.modals.ModalInteraction;
import uniqueimpact.discordRP.things.Chara;
import uniqueimpact.discordRP.utils.InvalidInputException;

public class SetCharacterPictureModalInteract implements ModalInteract {

    @Override
    public void run(String[] interactionParts, ModalInteraction modalInteraction) {

        String channelId = modalInteraction.getChannel().getId();
        Chara character;
        try {
            character = roleplay.findCharacterByChannel(channelId);
        } catch (InvalidInputException e) {
            modalInteraction.reply(e.getMessage()).queue();
            return;
        }

        character.setPicture(modalInteraction.getValues().get(0).getAsString());

        modalInteraction.reply("The character's picture was updated successfully.").queue();

    }

}
