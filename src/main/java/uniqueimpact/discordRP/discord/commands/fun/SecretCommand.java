package uniqueimpact.discordRP.discord.commands.fun;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import uniqueimpact.discordRP.discord.commands.Command;

public class SecretCommand implements Command {

    @Override
    public MessageCreateData run(SlashCommandInteractionEvent command) {
        return new MessageCreateBuilder().setContent(parseSecretCommand(command)).build();
    }

    private String parseSecretCommand(SlashCommandInteractionEvent command) {

        String commandString = command.getOption("command").getAsString();

        switch (commandString.toLowerCase()) {
            case "secret": return "yep you did it well done you found a secret command";
            case "secret secret": return "yep you did it well done you found a secret secret command";
            case "secret secret secret": return "don't push your luck";
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
            case "bad": return "unique is bad";
            case "badder": return "but jacob is badder";
            case "baddest": return "aeglen lol";
            case "jacob": return "slavery is fun!";
            case "door": return "You open the secret door and that leads you to another secret door!";
            case "doctor": return "Doctor Who?";
            case "your mother": return "is gay";
            case "fish": return "https://images-ext-1.discordapp.net/external/7Xvmvw_CtiHCDkySpEMMXCfe-pEZhkl_RBK67n7HE5k/https/media.tenor.com/qPPGBNcRJ8oAAAAd/fish-cannon.gif";
            case "clara": return "https://media.discordapp.net/attachments/585478705632247819/600780221230350339/unknown.png";
            case "dream": return "Tehehehe!";
            case "monokuma": return "Puhuhuhu!";
            case "forecast": return "Wouldn't you like to know, weather boi?";
            case "weather": return "Cloudy with a chance of uncertainty?";
            case "illuminati": return "https://cdn.discordapp.com/attachments/641755891808600076/1111787515431620709/Illuminati_confirmed_funny_vine.gif";
            case "application": return ":robot: :mega:   You're allowed to submit the application form before completing the character doc.";
            case "frick": return "heck";
            case "heck": return "frick";
            case "ping": return "You've got mail!";
            case "runescape": return "Go alch yourself";
            case "danganronpa": return "Correct";
            case "amogus": return "Sus";
            case "funyarinpa": return "https://static.wikia.nocookie.net/ninehourspersonsdoors/images/f/f7/Brainsucker.png/revision/latest?cb=20111022013411";
            default: return "Nope, that's not a secret command.";
        }

    }

}
