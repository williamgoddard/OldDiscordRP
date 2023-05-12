package uniqueimpact.discordRP.discord.commands.fun;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import uniqueimpact.discordRP.discord.commands.Command;

public class SecretCommand implements Command {

    @Override
    public String run(SlashCommandInteractionEvent command) {

        String commandString = command.getOption("command").getAsString();

        switch (commandString.toLowerCase()) {
            case "secret": return "yep you did it well done you found a secret command";
            case "unique": return "Please subscribe! https://www.youtube.com/user/TheUniqueImpact";
            case "traitor": return "Maybe the real traitor is the friends we make along the way...";
            case "mastermind": return "The mastermind is... Junko Enoshima!";
            case "nibbles": return "https://media.discordapp.net/attachments/226490521520963584/800151579386183720/Nibbles.png";
            case "spoilers": return "https://m.media-amazon.com/images/I/51xdQEDj1tL._AC_SL1001_.jpg";
            case "refrigerator": return "how the heck did you find this one?!";
            case "joke": return "What do you see if you look in a mirror?";
            case "monkey": return "Mmmmm... Monke.";
            case "arcade": return "\"Maybe we can't all change the world. Maybe it's enough just to do good for the short time that we're here.\" - Arcade Gannon, 2281 CE";
            case "69": return "Nice";
            default: return "Nope, that's not a secret command.";
        }

    }

}
