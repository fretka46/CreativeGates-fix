package org.tamasoft.creativegate.util

import org.apache.commons.lang3.mutable.MutableBoolean
import org.bukkit.Location

// http://mc.kev009.com/Protocol
// -----------------------------
// Smoke Directions 
// -----------------------------
// Direction 	ID	Direction
//				0	South - East
//				1	South
//				2	South - West
//				3	East
//				4	(Up or middle ?)
//				5	West
//				6	North - East
//				7	North
//				8	North - West
//-----------------------------

object SmokeUtil {

    val fakeExplosion: MutableBoolean = MutableBoolean(false)

    @JvmOverloads
    fun fakeExplosion(location: Location, power: Float = 4f) {
        synchronized(fakeExplosion) {
            fakeExplosion.setValue(true)
            location.world.createExplosion(location.x, location.y, location.z, power, false, false)
            fakeExplosion.setValue(false)
        }
    }
}