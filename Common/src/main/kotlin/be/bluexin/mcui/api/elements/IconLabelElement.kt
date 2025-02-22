/*
 * Copyright (C) 2016-2019 Arnaud 'Bluexin' Solé
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package be.bluexin.mcui.api.elements

import com.tencao.saomclib.utils.math.BoundingBox2D
import org.joml.Vector2d
import com.tencao.saomclib.utils.math.vec
import be.bluexin.mcui.GLCore
import be.bluexin.mcui.api.elements.registry.DrawType
import be.bluexin.mcui.api.screens.IIcon
import be.bluexin.mcui.config.OptionCore
import be.bluexin.mcui.resources.StringNames
import be.bluexin.mcui.screens.unaryPlus
import be.bluexin.mcui.util.ColorUtil
import kotlin.math.max

/**
 * Part of saoui by Bluexin, released under GNU GPLv3.
 *
 * @author Bluexin
 */
open class IconLabelElement(icon: IIcon, open var label: String = "", pos: Vector2d = Vector2d.ZERO, override val description: MutableList<String> = mutableListOf()) : IconElement(icon, pos, width = 84, height = 18) {

    override val boundingBox get() = BoundingBox2D(pos, pos + vec(width, height))
    override var idealBoundingBox: BoundingBox2D
        get() = BoundingBox2D(pos, pos + vec(max(26 + GLCore.glStringWidth(label), width), height))
        set(value) { // FIXME: this makes it so unintuitive :shock:
            width = value.widthI()
        }
    override val childrenXOffset get() = width + 5
    override var visible: Boolean
        get() = super.visible
        set(value) {
            if (value && !super.visible) {
                opacity = 0f
                if (listed) {
                    +basicAnimation(this, "opacity") {
                        from = 0f
                        to = 1f
                        duration = 4f
                    }
                }
            }
            super.visible = value
        }

    /**
     * @see IElement.drawBackground
     */
    override fun drawBackground(mouse: Vector2d, partialTicks: Float) {
        if (!canDraw) return
        GLCore.pushMatrix()
        if (scale != Vector2d.ONE) GLCore.glScalef(scale.xf, scale.yf, 1f)
        val mouseCheck = mouse in this || selected
        GLCore.glBlend(true)
        GLCore.depth(true)
        GLCore.color(ColorUtil.multiplyAlpha(getColor(mouse), transparency))
        GLCore.glBindTexture(StringNames.slot)
        GLCore.glTexturedRectV2(pos.x, pos.y, width = width.toDouble(), height = height.toDouble(), srcX = 0.0, srcY = 40.0, srcWidth = 84.0, srcHeight = 18.0)
        if (mouseCheck && OptionCore.MOUSE_OVER_EFFECT.isEnabled && !disabled) {
            mouseOverEffect()
        }
        GLCore.glBlend(false)
        GLCore.depth(false)
        GLCore.color(1f, 1f, 1f, 1f)
        drawChildren(mouse, partialTicks, DrawType.BACKGROUND)
        GLCore.popMatrix()
    }

    /**
     * @see IElement.draw
     */
    override fun draw(mouse: Vector2d, partialTicks: Float) {
        if (!canDraw) return
        GLCore.pushMatrix()
        GLCore.glBlend(true)
        val color = ColorUtil.multiplyAlpha(getTextColor(mouse), transparency)
        GLCore.color(color)
        if (icon.rl != null) GLCore.glBindTexture(icon.rl!!)
        icon.glDrawUnsafe(pos + vec(1, 1))
        if (this.highlighted || (mouse in this || selected)) {
            GLCore.glString(label, pos + vec(22, height / 2), color, shadow = OptionCore.TEXT_SHADOW.isEnabled, centered = true)
        } else {
            GLCore.glString(label, pos + vec(22, height / 2), color, shadow = false, centered = true)
        }
        GLCore.glBlend(false)
        GLCore.color(1f, 1f, 1f, 1f)
        drawChildren(mouse, partialTicks, DrawType.DRAW)
        GLCore.popMatrix()
    }

    /**
     * @see IElement.drawForeground
     */
    override fun drawForeground(mouse: Vector2d, partialTicks: Float) {
        if (!canDraw) return
        GLCore.pushMatrix()
        if (mouse in this) {
            drawHoveringText(mouse)
            GLCore.lighting(false)
            GLCore.depth(false)
        }
        drawChildren(mouse, partialTicks, DrawType.FOREGROUND)
        GLCore.popMatrix()
    }

    override fun toString(): String {
        return "NeoIconLabelElement(label='$label', width=$width, height=$height)\n\t${super.toString()}"
    }
}
