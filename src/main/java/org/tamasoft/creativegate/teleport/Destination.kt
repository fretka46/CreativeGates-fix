package org.tamasoft.creativegate.teleport

import com.fasterxml.jackson.annotation.JsonIgnore
import org.bukkit.Bukkit
import org.bukkit.World
import java.io.Serializable

class Destination() : Serializable {

    lateinit var world: String
    lateinit var location: BlockLocation
    lateinit var heading: Heading

    constructor(world: String, location: BlockLocation, heading: Heading) : this() {
        this.world = world
        this.location = location
        this.heading = heading
    }


    @JsonIgnore
    fun getBukkitWorld(): World {
        return Bukkit.getWorld(world) ?: throw IllegalStateException("The world $world does not exist.")
    }

    override fun toString(): String {
        return "($world, $location, $heading)"
    }
}