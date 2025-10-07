package org.tamasoft.creativegate.util

import org.bukkit.block.BlockFace

object YawUtil {
    @JvmStatic
    fun getYaw(face: BlockFace?): Float? {
        when (face) {
            BlockFace.NORTH -> return 0f
            BlockFace.EAST -> return 90f
            BlockFace.SOUTH -> return 180f
            BlockFace.WEST -> return 270f
            BlockFace.UP -> return null
            BlockFace.DOWN -> return null
            BlockFace.NORTH_EAST -> return 45f
            BlockFace.NORTH_WEST -> return 315f
            BlockFace.SOUTH_EAST -> return 135f
            BlockFace.SOUTH_WEST -> return 225f
            BlockFace.WEST_NORTH_WEST -> return 292.5f
            BlockFace.NORTH_NORTH_WEST -> return 337.5f
            BlockFace.NORTH_NORTH_EAST -> return 22.5f
            BlockFace.EAST_NORTH_EAST -> return 67.5f
            BlockFace.EAST_SOUTH_EAST -> return 112.5f
            BlockFace.SOUTH_SOUTH_EAST -> return 157.5f
            BlockFace.SOUTH_SOUTH_WEST -> return 202.5f
            BlockFace.WEST_SOUTH_WEST -> return 247.5f
            BlockFace.SELF -> return null
            else -> return null
        }
    }
}