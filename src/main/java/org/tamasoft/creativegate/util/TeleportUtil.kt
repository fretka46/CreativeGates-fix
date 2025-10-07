package org.tamasoft.creativegate.util

import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Player
import org.tamasoft.creativegate.teleport.Destination
import org.tamasoft.creativegate.teleport.TeleporterException

object TeleportUtil {

    @Throws(TeleporterException::class)
    fun teleport(teleportee: Player, destination: Destination) {
        teleportPlayer(teleportee, destination)
    }

    @Throws(TeleporterException::class)
    fun teleportPlayer(player: Player, destination: Destination) {
        val location: Location = try {

            val world: World = destination.getBukkitWorld()
            val locationX = destination.location.blockX.toDouble()
            val locationY = destination.location.blockY.toDouble()
            val locationZ = destination.location.blockZ.toDouble()
            val pitch = destination.heading.pitch
            val yaw = destination.heading.yaw
            Location(world, locationX, locationY, locationZ, yaw, pitch)

        } catch (e: Exception) {
            throw TeleporterException(TxtUtil.parse("<b>Could not calculate the location: ${e.message}"))
        }

        // eject passengers and unmount before transport
        player.eject()
        player.vehicle?.eject()

        // Do the teleport
        player.teleport(location)
}
}