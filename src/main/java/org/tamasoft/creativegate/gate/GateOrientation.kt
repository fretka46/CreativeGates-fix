package org.tamasoft.creativegate.gate

import org.tamasoft.creativegate.util.YawUtil.getYaw
import org.bukkit.block.BlockFace
import org.tamasoft.creativegate.teleport.BlockLocation
import java.util.LinkedHashSet
import java.util.Collections

enum class GateOrientation(expandFaces: Collection<BlockFace>) {

    NS(LinkedHashSet(mutableSetOf(BlockFace.NORTH, BlockFace.SOUTH, BlockFace.UP, BlockFace.DOWN))),
    WE(LinkedHashSet(mutableSetOf(BlockFace.WEST, BlockFace.EAST, BlockFace.UP, BlockFace.DOWN)));


    val expandFaces: Set<BlockFace>

    fun getExitFace(exitBlockCoords: BlockLocation, gateBlockCoords: BlockLocation): BlockFace {
        val mod: Int
        return if (this == NS) {
            mod = exitBlockCoords.blockX - gateBlockCoords.blockX
            if (mod > 0) {
                BlockFace.WEST
            } else {
                BlockFace.EAST
            }
        } else {
            mod = exitBlockCoords.blockZ - gateBlockCoords.blockZ
            if (mod > 0) {
                BlockFace.NORTH
            } else {
                BlockFace.SOUTH
            }
        }
    }

    fun getExitYaw(exit: BlockLocation, gate: BlockLocation): Float {
        return getYaw(getExitFace(exit, gate))!!
    }

    init {
        val expandFacesTemp: Set<BlockFace> = LinkedHashSet(expandFaces)
        this.expandFaces = Collections.unmodifiableSet(expandFacesTemp)
    }
}