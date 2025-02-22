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

package be.bluexin.mcui.themes.util

import jakarta.xml.bind.annotation.XmlAttribute
import jakarta.xml.bind.annotation.XmlValue

/**
 * Part of saoui by Bluexin.
 *
 * @author Bluexin
 */
open class ExpressionIntermediate {

    @get:XmlAttribute(name = "cache")
    var cacheType = CacheType.PER_FRAME

    @get:XmlValue
    var expression = "" // Will get replaced by the loading
        get() {
            var f = field
            // TODO : new obf ?
//            if (LibHelper.obfuscated) f = f.replace("format(", "func_135052_a(")
            f = f.replace('\n', ' ')
                .replace("format(", "language.getOrDefault(")
            return f
        }
}
