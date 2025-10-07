package org.tamasoft.creativegate.util

import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.tamasoft.creativegate.CreativeGate
import org.tamasoft.creativegate.gate.GateOrientation

object FloodUtil {

    val faceToOrientation = mapOf(
        Pair(BlockFace.NORTH, GateOrientation.NS),
        Pair(BlockFace.SOUTH, GateOrientation.NS),
        Pair(BlockFace.EAST, GateOrientation.WE),
        Pair(BlockFace.WEST, GateOrientation.WE),
    )

    /**
     * Given a void block, finds the connected void blocks as well as a frame of non-void blocks surrounding it.
     * If the given block is non-void, returns null
     */
    fun getGateFloodInfo(clickedBlock: Block, clickedFace: BlockFace): FloodInfo? {
        val startBlock = clickedBlock.getRelative(clickedFace)
        val gateOrientation = faceToOrientation[clickedFace]
            ?: return null
        val portalBlocks = FloodCalculator(gateOrientation)
            .calcFloodBlocks(startBlock)
            .getFoundBlocks()
            ?: return null

        // Add in the frame as well
        val expandedBlocks = expandedByOne(portalBlocks, gateOrientation.expandFaces)
        val frameBlocks = SetUtils.diff(expandedBlocks, portalBlocks)
        return FloodInfo(gateOrientation, portalBlocks, frameBlocks)
    }

    /**
     * Given a set of blocks, expands the set by adding all adjacent blocks reachable by one step in the given
     * directions.
     */
    private fun expandedByOne(blocks: Set<Block>, expandFaces: Set<BlockFace>): Set<Block> {
        val ret: MutableSet<Block> = HashSet(blocks)
        for (block in blocks) {
            for (face in expandFaces) {
                ret.add(block.getRelative(face))
            }
        }
        return ret
    }

    class FloodInfo(
        var gateOrientation: GateOrientation,
        var portalBlocks: Set<Block>,
        var frameBlocks: Set<Block>
    )

    /**
     * Given a block, recursively walks void adjacent blocks and returns the set with all of them, or null if the area
     * is too large.
     */
    private class FloodCalculator(
        orientation: GateOrientation
    ) {
        private val foundBlocks: MutableSet<Block> = HashSet()
        private val expandFaces: Set<BlockFace>
        private val maxArea: Int = CreativeGate.configuration.maxArea
        private var aborted = false
        fun getFoundBlocks(): Set<Block>? {
            return if (aborted || foundBlocks.isEmpty()) {
                null
            } else {
                foundBlocks
            }
        }

        fun calcFloodBlocks(startBlock: Block): FloodCalculator {
            // Too large, abort
            if (foundBlocks.size > maxArea) {
                if (!aborted) {
                    aborted = true
                }
                return this
            }

            // Already analysed this one
            if (foundBlocks.contains(startBlock)) {
                return this
            }

            // Found void, find its void neighbours
            if (VoidUtil.isVoid(startBlock)) {
                foundBlocks.add(startBlock)
                for (face in expandFaces) {
                    val potentialBlock = startBlock.getRelative(face)
                    calcFloodBlocks(potentialBlock)
                }
            }
            return this
        }

        init {
            expandFaces = orientation.expandFaces
        }
    }
}