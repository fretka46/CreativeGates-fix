package org.tamasoft.creativegate.util

import org.bukkit.Location
import org.bukkit.event.player.PlayerMoveEvent

object LocationComparatorUtil {
    fun isSameBlock(event: PlayerMoveEvent): Boolean {
        return isSameBlock(event.from, event.to)
    }

    private fun isSameBlock(one: Location, two: Location): Boolean {
        if (one.blockX != two.blockX) return false
        if (one.blockZ != two.blockZ) return false
        return if (one.blockY != two.blockY) false else one.world == two.world
    }
}