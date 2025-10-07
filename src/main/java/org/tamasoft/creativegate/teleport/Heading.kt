package org.tamasoft.creativegate.teleport

import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.HashCodeBuilder

class Heading() {

    var pitch: Float = 0.0f
    var yaw: Float = 0.0f

    constructor(pitch: Float, yaw: Float) : this() {
        this.pitch = pitch
        this.yaw = yaw
    }

    override fun hashCode(): Int {
        return HashCodeBuilder()
            .append(pitch)
            .append(yaw)
            .build()
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Heading) {
            EqualsBuilder()
                .append(pitch, other.pitch)
                .append(yaw, other.yaw)
                .build()
        } else {
            false
        }
    }

    override fun toString(): String {
        return "(pitch=$pitch, yaw=$yaw)"
    }
}