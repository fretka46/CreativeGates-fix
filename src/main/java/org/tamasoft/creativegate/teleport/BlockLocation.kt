package org.tamasoft.creativegate.teleport

import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.HashCodeBuilder
import org.bukkit.Location
import org.bukkit.block.Block
import kotlin.math.floor

class BlockLocation() {

    var blockX: Int = 0
    var blockY: Int = 0
    var blockZ: Int = 0

    constructor(blockX: Int, blockY: Int, blockZ: Int) : this() {
        this.blockX = blockX
        this.blockY = blockY
        this.blockZ = blockZ
    }

    companion object {
        fun fromBlock(block: Block): BlockLocation {
            return BlockLocation(block.x, block.y, block.z)
        }

        fun fromLocation(location: Location): BlockLocation {
            return BlockLocation(
                floor(location.x).toInt(),
                floor(location.y).toInt(),
                floor(location.z).toInt()
            )
        }
    }

    override fun hashCode(): Int {
        return HashCodeBuilder()
            .append(blockX)
            .append(blockY)
            .append(blockZ)
            .build()
    }

    override fun equals(other: Any?): Boolean {
        return if (other is BlockLocation) {
            EqualsBuilder()
                .append(blockX, other.blockX)
                .append(blockY, other.blockY)
                .append(blockZ, other.blockZ)
                .build()
        } else {
            false
        }
    }

    override fun toString(): String {
        return "($blockX, $blockY, $blockZ)"
    }
}