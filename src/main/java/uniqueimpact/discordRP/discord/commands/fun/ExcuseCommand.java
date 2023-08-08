package uniqueimpact.discordRP.discord.commands.fun;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import uniqueimpact.discordRP.discord.commands.Command;

import java.util.Random;

public class ExcuseCommand implements Command {

    Random random = new Random();

    @Override
    public MessageCreateData run(SlashCommandInteractionEvent command) {

        String excuse = command.getOption("event") != null ? "\"" + command.getOption("event").getAsString() : "\"I can't right now";
        excuse += " because ";
        String[] attackers = {"Monokuma", "the mastermind", "my evil twin", "Sans Undertale", "UniqueImpact", "my aunt", "Mr. Blobby", "the Numbertaker",
                "Bill Cipher", "Perry the Platypus", "Dr. Doofenshmirtz", "the Queen", "my imaginary friend", "the Kellogg Company", "Spongebob Squarepants",
                "Jeff Bezos", "Mr. Bean", "Shrek", "God", "my little sister", "a ninja", "the Doctor", "Sherlock Holmes", "Boris Johnson", "my butler",
                "a creeper", "your father", "Darth Vader", "Gruntilda", "my landlord", "my psychologist", "my teacher", "your mother", "a very scary man",
                "Father Christmas", "the tooth fairy", "my arch nemesis", "my third cousin's cat", "my pet giraffe", "an enormous gerbil", "everyone in France",
                "Will Smith", "the Pope", "the United Nations", "Elon Musk", "Thomas the Tank Engine", "Tinky Winky", "Vladamir Putin", "Sonic", "Mario",
                "my tennis instructor", "the Discord moderator", "an FBI agent", "Harry Potter", "Voldemort", "a man in a banana costume", "a magician",
                "a lawn gnome", "a creepy doll", "Bob the Builder", "Robbie Rotten", "Iron Man", "Thor", "Spiderman", "Batman", "a highly advanced AI",
                "an e-girl", "an e-boy", "my \"best friend\"", "the tickle monster", "a newborn baby", "Captain Jack Sparrow", "Dory", "Thanos", "my boss",
                "a hideous monster", "an eagle", "Joe Biden", "Nintendo", "Lightning McQueen", "a Karen", "the kraken", "someone", "Microsoft"};
        String[] actions = {"hiding under", "attacking", "spying on", "stealing", "plotting to murder", "getting away with", "trying to marry", "eating",
                "destroying", "punching", "terrorising", "licking", "vandalising", "sitting on", "kidnapping", "verbally assaulting", "gently caressing",
                "trying to buy", "growling at", "looking menacingly at", "about to become", "playing chess against", "systematically destroying", "wearing",
                "setting fire to", "escaping with", "secretly replacing", "shouting at", "whispering at", "knocking over", "pretending to be", "repossessing",
                "pointing a gun at", "trying to bribe", "preparing a nuclear assault against", "about to yeet away", "splashing water at", "dabbing on",
                "beating up", "rapidly approaching", "assassinating", "sneaking up on", "plotting a dastardly scheme against", "tickling", "poking", "fighting",
                "galloping away upon", "now", "claiming to be more important than", "suing", "hiding", "claiming ownership of", "chasing", "frightening",
                "declaring war on", "catapulting", "incinerating", "ravaging", "overenthusiastically interested in", "jealous about", "watering", "pawning off"};
        String[] victims = {"my fridge", "my mother", "my father", "me", "you", "my entire family", "my homework", "my hopes and dreams", "my house", "an egg",
                "my beard", "my toes", "my bed", "my career", "the children", "the moon", "Antarctica", "the sea", "my favourite mug", "my shadow", "my money",
                "my front door", "my car", "all of the birds", "the ozone layer", "my tomatoes", "my orange juice", "the Eiffel Tower", "my Xbox 360 Kinect",
                "a zebra", "my cat", "the TV remote", "my phone", "my shampoo", "my local supermarket", "my enormous yacht", "my washing machine", "my curtains",
                "one million dollars", "the entire neighbourhood", "the biscuit tin", "my lunch", "the White House", "my trousers", "my hat", "my best friend",
                "everyone except me", "my childhood", "my kneecaps", "the crown jewels", "the Mona Lisa", "my bin", "everything", "my garden", "my bathroom",
                "my allotment", "my computer", "my pencil case", "my toaster", "Ireland", "France", "my crops", "my lemons", "the internet", "the WiFi",
                "my wife", "my husband"};
        int attackerNum = random.nextInt(attackers.length);
        int actionNum = random.nextInt(actions.length);
        int victimNum = random.nextInt(victims.length);
        excuse += attackers[attackerNum] + " is " + actions[actionNum] + " " + victims[victimNum] + ".\"";
        return new MessageCreateBuilder().setContent(excuse).build();

    }

}
