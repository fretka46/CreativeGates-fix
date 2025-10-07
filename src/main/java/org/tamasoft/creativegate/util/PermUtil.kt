package org.tamasoft.creativegate.util

import org.bukkit.permissions.Permissible

object PermUtil {

    private const val BASE = "CreativeGates"

    // Checks specific level (supports wildcard)
    fun hasLevel(p: Permissible, level: Int): Boolean =
        p.hasPermission("$BASE.$level") || p.hasPermission("$BASE.*")

    // Highest level the player has (0 if none)
    fun highestLevel(p: Permissible, min: Int = 1, max: Int = 20): Int =
        (max downTo min).firstOrNull { hasLevel(p, it) } ?: 0
}