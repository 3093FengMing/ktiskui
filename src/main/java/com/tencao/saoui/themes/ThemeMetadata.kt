package com.tencao.saoui.themes

import net.minecraft.util.ResourceLocation

data class ThemeMetadata(
    val id: ResourceLocation,
    /**
     * Root for the theme's configuration files (hud.json, settings.json, ...)
     */
    val themeRoot: ResourceLocation,
    val name: String,
    val type: ThemeFormat,
    val fragments: Map<ResourceLocation, ResourceLocation>
) {
    /**
     * Root for the theme's textures
     */
    val texturesRoot = ResourceLocation(themeRoot.resourceDomain, "textures/${id.resourcePath}/")
}

enum class ThemeFormat(val hudFileSuffix: String, val loader: () -> AbstractThemeLoader) {
    XML("hud.xml", { XmlThemeLoader }),
    JSON("hud.json", { JsonThemeLoader });

    companion object {
        private val fromFileExtension = values().associateBy { it.hudFileSuffix.substringAfterLast('.') }

        fun fromFile(fileName: String): ThemeFormat? = fromFileExtension.values.firstOrNull { fileName.endsWith(it.hudFileSuffix) }
        fun fromFileExtension(fileName: String): ThemeFormat? = fromFileExtension[fileName.substringAfterLast('.')]
    }
}
