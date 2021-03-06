/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 * 
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [Aug 27, 2014, 8:55:00 PM (GMT)]
 */
package vazkii.botania.client.core.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;

import org.lwjgl.opengl.GL11;

import vazkii.botania.client.core.helper.ShaderHelper;
import vazkii.botania.client.lib.LibResources;
import vazkii.botania.common.item.equipment.bauble.ItemFlightTiara;
import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public final class TiaraWingRenderHandler {
	
	private static ResourceLocation textureHalo = new ResourceLocation(LibResources.MISC_HALO);
	
	@SubscribeEvent
	public void onPlayerRender(RenderPlayerEvent.Specials.Post event) {
		InventoryBaubles inv = PlayerHandler.getPlayerBaubles(event.entityPlayer);
		if(inv.getStackInSlot(0) != null && inv.getStackInSlot(0).getItem() instanceof ItemFlightTiara) {
			int meta = inv.getStackInSlot(0).getItemDamage();
			if(meta > 0 && meta <= ItemFlightTiara.wingIcons.length) {
				IIcon icon = ItemFlightTiara.wingIcons[meta - 1];
				Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationItemsTexture);

				boolean flying = event.entityPlayer.capabilities.isFlying;

				float rz = 120F;
				float rx = 20F + (float) ((Math.sin((double) (event.entityPlayer.ticksExisted + event.partialRenderTick) * (flying ? 0.4F : 0.2F)) + 0.5F) * (flying ? 30F : 5F));
				float ry = 0F;
				float h = 0.2F;
				float i = 0.15F;
				float s = 1F;

				GL11.glPushMatrix();
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				GL11.glColor4f(1F, 1F, 1F, 1F);

				switch(meta) {
				case 1 : { // Jibril
					h = 0.4F;
					break;
				}
				case 2 : { // Sephiroth
					s = 1.3F;
					break;
				}
				case 3 : { // Cirno
					h = -0.1F;
					rz = 0F;
					rx = 0F;
					i = 0.3F;
					break;
				}
				case 4 : { // Fire
					int light = 15728880;
					int lightmapX = light % 65536;
					int lightmapY = light / 65536;
					OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lightmapX, lightmapY);
					break;
				}
				case 5 : { // Kuroyukihime
					h = 0.8F;
					rz = 180F;
					ry = -rx;
					rx = 0F;
					s = 2F;
					break;
				}
				case 6 : { // Random Devil
					rz = 150F;
					break;
				}
				case 7 : { // Lyfa
					h = -0.1F;
					rz = 0F;
					ry = -rx;
					rx = 0F;
					GL11.glColor4f(1F, 1F, 1F, 0.5F + (float) Math.cos((double) (event.entityPlayer.ticksExisted + event.partialRenderTick) * 0.3F) * 0.2F);
					break;
				}
				}

				float f = icon.getMinU();
				float f1 = icon.getMaxU();
				float f2 = icon.getMinV();
				float f3 = icon.getMaxV();
				float sr = 1F / s;

				if(event.entityPlayer.isSneaking())
					GL11.glRotatef(28.64789F, 1.0F, 0.0F, 0.0F);

				GL11.glTranslatef(0F, h, i);

				GL11.glRotatef(rz, 0F, 0F, 1F);
				GL11.glRotatef(rx, 1F, 0F, 0F);
				GL11.glRotatef(ry, 0F, 1F, 0F);
				GL11.glScalef(s, s, s);
				ItemRenderer.renderItemIn2D(Tessellator.instance, f1, f2, f, f3, icon.getIconWidth(), icon.getIconHeight(), 1F / 32F);
				GL11.glScalef(sr, sr, sr);
				GL11.glRotatef(-ry, 0F, 1F, 0F);
				GL11.glRotatef(-rx, 1F, 0F, 0F);
				GL11.glRotatef(-rz, 0F, 0F, 1F);

				if(meta != 2) { // Sephiroth
					GL11.glScalef(-1F, 1F, 1F);
					GL11.glRotatef(rz, 0F, 0F, 1F);
					GL11.glRotatef(rx, 1F, 0F, 0F);
					GL11.glRotatef(ry, 0F, 1F, 0F);
					GL11.glScalef(s, s, s);
					ItemRenderer.renderItemIn2D(Tessellator.instance, f1, f2, f, f3, icon.getIconWidth(), icon.getIconHeight(), 1F / 32F);
					GL11.glScalef(sr, sr, sr);
					GL11.glRotatef(-ry, 1F, 0F, 0F);
					GL11.glRotatef(-rx, 1F, 0F, 0F);
					GL11.glRotatef(-rz, 0F, 0F, 1F);
				}

				GL11.glColor3f(1F, 1F, 1F);
				GL11.glPopMatrix();
				
				// Jibril's Halo
				if(meta == 1) 
					renderHalo(event.entityPlayer, event.partialRenderTick);
			}
		}
	}
	
	private void renderHalo(EntityPlayer player, float partialTicks) {
		float yaw = player.prevRotationYawHead + (player.rotationYawHead - player.prevRotationYawHead) * partialTicks;
		float yawOffset = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * partialTicks;
		float pitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * partialTicks;
		
		GL11.glPushMatrix();
		GL11.glRotatef(yawOffset, 0, -1, 0);
		GL11.glRotatef(yaw - 270, 0, 1, 0);
		GL11.glRotatef(pitch, 0, 0, 1);	

		GL11.glShadeModel(GL11.GL_SMOOTH);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		
		Minecraft.getMinecraft().renderEngine.bindTexture(textureHalo);
		
		GL11.glTranslated(0, -player.eyeHeight + (player.isSneaking() ? 0.0625 : 0), 0);
		GL11.glRotated(30, 1, 0, -1);
		GL11.glTranslatef(-0.1F, -0.5F, -0.1F);
		GL11.glRotatef(player.ticksExisted + partialTicks, 0, 1, 0);
		
		Tessellator tes = Tessellator.instance;
		ShaderHelper.useShader(ShaderHelper.halo);
		tes.startDrawingQuads();
		tes.addVertexWithUV(-0.75, 0, -0.75, 0, 0);
		tes.addVertexWithUV(-0.75, 0, 0.75, 0, 1);
		tes.addVertexWithUV(0.75, 0, 0.75, 1, 1);
		tes.addVertexWithUV(0.75, 0, -0.75, 1, 0);
		tes.draw();
		ShaderHelper.releaseShader();
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}
}
