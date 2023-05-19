package uniqueimpact.discordRP.discord.commands.player_look;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import uniqueimpact.discordRP.discord.commands.Command;
import uniqueimpact.discordRP.discord.utils.DiscordOutputGenerator;
import uniqueimpact.discordRP.things.Inventory;
import uniqueimpact.discordRP.things.Item;
import uniqueimpact.discordRP.things.Chara;
import uniqueimpact.discordRP.utils.InvalidInputException;

import java.util.List;

public class ClothesCommand implements Command {

    @Override
    public String run(SlashCommandInteractionEvent command) {

        String channelId = command.getChannel().getId();
        Chara player;
        try {
            player = roleplay.findCharacterByChannel(channelId);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        Inventory inv = player.getClothes();
        List<Item> items = inv.getItems();

        if (items.size() == 0) {
            return "You are currently not wearing much...";
        }

        return "You are currently wearing these clothes:\n" + DiscordOutputGenerator.convertItemList(items, 1900);

    }

}
