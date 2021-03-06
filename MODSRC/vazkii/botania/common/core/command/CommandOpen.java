/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 * 
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [Sep 23, 2014, 9:50:14 PM (GMT)]
 */
package vazkii.botania.common.core.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import vazkii.botania.common.item.ItemLexicon;

public class CommandOpen extends CommandBase {

	@Override
	public String getCommandName() {
		return "botania-open";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		return "<entry>";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if(sender instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) sender;
			ItemStack stack = player.getCurrentEquippedItem();
			if(stack != null && stack.getItem() instanceof ItemLexicon) {
				ItemLexicon.setForcedPage(stack, args[0]);
				ItemLexicon.setQueueTicks(stack, 3);
			} else sender.addChatMessage(new ChatComponentTranslation("botaniamisc.noLexicon").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
		}
	}

}
