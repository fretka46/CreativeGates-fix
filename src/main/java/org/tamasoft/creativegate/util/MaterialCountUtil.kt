package org.tamasoft.creativegate.util

import org.bukkit.Material
import org.bukkit.block.Block
import java.util.*
import java.util.stream.Collectors

object MaterialCountUtil {
    fun count(blocks: Collection<Block>): Map<Material, Long> {
        return blocks.stream()
            .collect(Collectors.groupingBy(
                { it.type },
                { EnumMap(org.bukkit.Material::class.java) },
                Collectors.counting()
            ))
    }

    fun has(me: Map<Material, Long>, req: Map<Material, Long>): Boolean {
        return req.entries.stream()
            .allMatch { reqEntry -> me.getOrDefault(reqEntry.key, 0) >= reqEntry.value }
    }

    fun desc(materialCounts: Map<Material, Long>): String {
        data class MaterialRequirement(val materialName: String, val count: Long)
        val parts = materialCounts.entries.stream()
            .map { MaterialRequirement(TxtUtil.getMaterialName(it.key), it.value)}
            .map { TxtUtil.parse("<v>${it.count} <k>${it.materialName}") }
            .collect(Collectors.toList())
        return TxtUtil.implodeCommaAnd(parts, TxtUtil.parse("<i>, "), TxtUtil.parse(" <i>and "))
    }
}