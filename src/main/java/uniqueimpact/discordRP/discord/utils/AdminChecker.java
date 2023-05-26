package uniqueimpact.discordRP.discord.utils;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
public class AdminChecker {

    public static boolean isAdmin(Member member) {
        return member.hasPermission(Permission.ADMINISTRATOR);
    }

}
