package org.tamasoft.creativegate.util

import org.bukkit.Material
import org.bukkit.block.Block
import org.tamasoft.creativegate.CreativeGate

object VoidUtil {
    fun isVoid(material: Material): Boolean {
        return CreativeGate.configuration.voidMaterials.contains(material)
    }

    fun isVoid(block: Block): Boolean {
        return isVoid(block.type)
    }
}