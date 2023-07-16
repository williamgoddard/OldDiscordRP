package uniqueimpact.discordRP.discord.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class HelpCommand implements Command {

    @Override
    public String run(SlashCommandInteractionEvent command) {

        return """
                **Item Management Commands**
                `/take` Take an item from the room into your inventory
                `/drop` Drop an item from your inventory into the room
                `/wear` Wear an item of clothing from your inventory
                `/undress` Undress an item of clothing and hold it in your inventory
                `/takewear` Take an item from the room and wear it
                `/undressdrop` Undress an item of clothing and drop it into the room
                
                **Examination Commands**
                `/items` List all the items in the room
                `/inv` List all the items you are currently holding
                `/clothes` List all the items you are currently wearing
                
                `/look item` Look at an item in the room
                `/look inv` Look at an item you are currently holding
                `/look clothes` Look at an item you are currently wearing
                
                `/show item` Show off an item in the room
                `/show inv` Show off an item you are currently holding
                `/show clothes` Show off an item you are currently wearing
                
                `/look room` Look around at the room
                `/look character` Look at a character in the room
                `/look self` Look at yourself
                
                **Navigation Commands**
                `/doors` List all the doors you can access from here
                `/goto` Move to another room
                `/lock` Lock the door to another room
                `/unlock` Unlock the door to another room
                
                **Fun Commands**
                `/roll` Roll the dice!
                `/8ball` Ask a yes or no question to the all-knowing 8-ball to learn from its infinite wisdom!
                `/excuse` Get a quick, randomly generated excuse for anything!
                `/secret` I wonder what this command does...
                
                **Info Commands**
                `/time` Get the current in-game time
                `/help` Get a list of all the commands
                """;

    }

}
