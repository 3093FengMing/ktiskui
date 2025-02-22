package be.bluexin.mcui.screens.util

import be.bluexin.mcui.util.Client
import be.bluexin.mcui.SoundCore
import be.bluexin.mcui.api.screens.IIcon
import be.bluexin.mcui.play
import be.bluexin.mcui.util.IconCore
import net.minecraft.client.gui.toasts.GuiToast
import net.minecraft.client.gui.toasts.IToast
import net.minecraft.client.renderer.GlStateManager

class NotificationAlert(val icon: IIcon, val title: String, val subtitle: String) : IToast {

    private var firstDrawTime: Long = 0
    private var newDisplay = true

    override fun draw(toastGui: GuiToast, delta: Long): IToast.Visibility {
        if (newDisplay) {
            firstDrawTime = delta
            newDisplay = false
            SoundCore.MESSAGE.play()
        }

        toastGui.minecraft.textureManager.bindTexture(IToast.TEXTURE_TOASTS)
        GlStateManager.color(1.0f, 1.0f, 1.0f)
        toastGui.drawTexturedModalRect(0, 0, 0, 96, 160, 32)
        icon.glDraw(6, 8)

        if (subtitle.isEmpty()) {
            toastGui.minecraft.fontRenderer.drawString(title, 25, 12, -11534256)
        } else {
            toastGui.minecraft.fontRenderer.drawString(title, 25, 7, -11534256)
            toastGui.minecraft.fontRenderer.drawString(subtitle, 25, 18, -16777216)
        }
        return if (delta - firstDrawTime < 5000L) IToast.Visibility.SHOW else IToast.Visibility.HIDE
    }

    companion object {
        fun new(icon: IconCore, title: String, subtitle: String) {
            Client.mc.toastGui.add(NotificationAlert(icon, title, subtitle))
        }
    }
}
