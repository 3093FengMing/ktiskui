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

package be.bluexin.mcui.api.screens;

import net.minecraft.resources.ResourceLocation;
import org.joml.Vector2dc;

import javax.annotation.Nullable;

/**
 * Part of saoui
 * Icons are used to display any form on screen.
 * Implement this to add your own custom icons.
 *
 * @author Bluexin
 */
@FunctionalInterface
public interface IIcon {

    /**
     * Called when this icon needs to be drawn on screen, at given x and y coordinates.
     * The given coordinates should be the upper-left corner of this icon.
     *
     * @param x x-coordinate to start drawing from
     * @param y y-coordinate to start drawing from
     */
    default void glDraw(int x, int y) { this.glDraw(x, y, 0); }

    void glDraw(int x, int y, float z);

    default void glDraw(Vector2dc pos, float z) {
        glDraw((int) pos.x(), (int) pos.y(), z);
    }

    default void glDrawUnsafe(int x, int y) {
        this.glDraw(x, y);
    }

    default void glDrawUnsafe(Vector2dc pos) {
        glDrawUnsafe((int) pos.x(), (int) pos.y());
    }

    default int getWidth() {
        return 16;
    }

    default int getHeight() {
        return 16;
    }

    @Nullable
    default ResourceLocation getRL() {
        return null;
    }
}
