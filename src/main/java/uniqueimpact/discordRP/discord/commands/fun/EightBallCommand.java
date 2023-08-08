package uniqueimpact.discordRP.discord.commands.fun;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import uniqueimpact.discordRP.discord.commands.Command;

import java.util.Random;

public class EightBallCommand implements Command {

    private static Random random = new Random();

    @Override
    public void run(SlashCommandInteractionEvent command) {

        String question = command.getOption("question").getAsString();

        String output = "**You asked the 8-ball:** " + question + "\n**The 8-ball responded: **";

        int number = random.nextInt(30) + 1;
        switch (number) {
            case 1: output += ":8ball: It is certain. :8ball:"; break;
            case 2: output += ":8ball: It is decidedly so. :8ball:"; break;
            case 3: output += ":8ball: Without a doubt. :8ball:"; break;
            case 4: output += ":8ball: Yes, definitely. :8ball:"; break;
            case 5: output += ":8ball: You may rely on it. :8ball:"; break;
            case 6: output += ":8ball: As I see it, yes. :8ball:"; break;
            case 7: output += ":8ball: Most likely. :8ball:"; break;
            case 8: output += ":8ball: Outlook good. :8ball:"; break;
            case 9: output += ":8ball: Yeah sure why not. :8ball:"; break;
            case 10: output += ":8ball: Signs point to yes. :8ball:"; break;
            case 11: output += ":8ball: Reply hazy, try again. :8ball:"; break;
            case 12: output += ":8ball: I'm tired. Ask again later. :8ball:"; break;
            case 13: output += ":8ball: I cannot tell you now. :8ball:"; break;
            case 14: output += ":8ball: You will know when the time is ripe. :8ball:"; break;
            case 15: output += ":8ball: Concentrate and try again. :8ball:"; break;
            case 16: output += ":8ball: It is unclear. :8ball:"; break;
            case 17: output += ":8ball: Only time can tell. :8ball:"; break;
            case 18: output += ":8ball: How the heck would I know? :8ball:"; break;
            case 19: output += ":8ball: It seems probable. :8ball:"; break;
            case 20: output += ":8ball: It seems unlikely. :8ball:"; break;
            case 21: output += ":8ball: Don't count on it. :8ball:"; break;
            case 22: output += ":8ball: My reply is no. :8ball:"; break;
            case 23: output += ":8ball: My sources say no. :8ball:"; break;
            case 24: output += ":8ball: Outlook not so good. :8ball:"; break;
            case 25: output += ":8ball: Very doubtful. :8ball:"; break;
            case 26: output += ":8ball: No. :8ball:"; break;
            case 27: output += ":8ball: Yes. Just kidding, no. :8ball:"; break;
            case 28: output += ":8ball: I don't want to tell you. :8ball:"; break;
            case 29: output += ":8ball: You should feel a great shame for asking me that. :8ball:"; break;
            case 30: output += ":8ball: Absolutely fricking not. :8ball:"; break;
        }

        command.reply(output).queue();

    }

}
