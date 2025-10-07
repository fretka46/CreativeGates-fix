package org.tamasoft.creativegate.util

import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.ChatColor
import org.bukkit.Material
import java.util.*
import java.util.regex.Pattern

object TxtUtil {
    private val parseReplacements: MutableMap<String, String>
    private val parsePattern: Pattern
    private val curlyPlaceholderPattern = Pattern.compile("\\{([a-zA-Z0-9_]+)\\}")

    fun parse(string: String): String {
        val ret = StringBuilder()
        val matcher = parsePattern.matcher(string)
        while (matcher.find()) {
            matcher.appendReplacement(ret, parseReplacements[matcher.group(0)])
        }
        matcher.appendTail(ret)
        return ret.toString()
    }

    fun parse(string: String, vararg args: Any?): String {
        return String.format(parse(string), *args)
    }

    // Simple translation: replace {placeholders} then apply legacy <tag> color parsing
    fun sendTranslation(player: org.bukkit.entity.Player, key: String) {
        val mm = MiniMessage.miniMessage()
        player.sendMessage(mm.deserialize(key))
    }

    fun sendTranslation(player: org.bukkit.entity.Player, key: String, tagResolver: Array<TagResolver>) {
        val mm = MiniMessage.miniMessage()
        player.sendMessage(mm.deserialize(key, *tagResolver))
    }

    fun upperCaseFirst(string: String?): String? {
        if (string == null) return null
        return if (string.isEmpty()) string else string.substring(0, 1)
            .uppercase(Locale.getDefault()) + string.substring(1)
    }

    @JvmOverloads
    fun implode(list: Array<Any?>, glue: String, format: String? = null): String {
        val ret = StringBuilder()
        for (i in list.indices) {
            val item = list[i]
            val str = item?.toString() ?: "NULL"
            if (i != 0) {
                ret.append(glue)
            }
            if (format != null) {
                ret.append(String.format(format, str))
            } else {
                ret.append(str)
            }
        }
        return ret.toString()
    }

    @JvmOverloads
    fun implode(coll: Collection<*>, glue: String, format: String? = null): String {
        return implode(coll.toTypedArray(), glue, format)
    }

    fun implodeCommaAndDot(objects: Collection<*>, format: String?, comma: String, and: String, dot: String): String {
        if (objects.isEmpty()) return ""
        if (objects.size == 1) {
            return implode(objects, comma, format)
        }
        val ourObjects: MutableList<Any?> = ArrayList(objects)
        var lastItem = ourObjects[ourObjects.size - 1].toString()
        var nextToLastItem = ourObjects[ourObjects.size - 2].toString()
        if (format != null) {
            lastItem = String.format(format, lastItem)
            nextToLastItem = String.format(format, nextToLastItem)
        }
        val merge = nextToLastItem + and + lastItem
        ourObjects[ourObjects.size - 2] = merge
        ourObjects.removeAt(ourObjects.size - 1)
        return implode(ourObjects, comma, format) + dot
    }

    fun implodeCommaAndDot(objects: Collection<*>, comma: String, and: String, dot: String): String {
        return implodeCommaAndDot(objects, null, comma, and, dot)
    }

    fun implodeCommaAnd(objects: Collection<*>, comma: String, and: String): String {
        return implodeCommaAndDot(objects, comma, and, "")
    }

    private var PATTERN_ENUM_SPLIT = Pattern.compile("[\\s_]+")
    private fun getNicedEnumString(str: String): String {
        val parts: MutableList<String?> = ArrayList()
        for (part in PATTERN_ENUM_SPLIT.split(str.lowercase(Locale.getDefault()))) {
            parts.add(upperCaseFirst(part))
        }
        return implode(parts, "")
    }

    private fun <T : Enum<T>> getNicedEnum(enumObject: T): String {
        return getNicedEnumString(enumObject.name)
    }

    fun getMaterialName(material: Material): String {
        return getNicedEnum(material)
    }


    init {
        // Create the parse replacements map
        parseReplacements = HashMap()

        // Color by name
        parseReplacements["<empty>"] = ""
        parseReplacements["<black>"] = "\u00A70"
        parseReplacements["<navy>"] = "\u00A71"
        parseReplacements["<green>"] = "\u00A72"
        parseReplacements["<teal>"] = "\u00A73"
        parseReplacements["<red>"] = "\u00A74"
        parseReplacements["<purple>"] = "\u00A75"
        parseReplacements["<gold>"] = "\u00A76"
        parseReplacements["<orange>"] = "\u00A76"
        parseReplacements["<silver>"] = "\u00A77"
        parseReplacements["<gray>"] = "\u00A78"
        parseReplacements["<grey>"] = "\u00A78"
        parseReplacements["<blue>"] = "\u00A79"
        parseReplacements["<lime>"] = "\u00A7a"
        parseReplacements["<aqua>"] = "\u00A7b"
        parseReplacements["<rose>"] = "\u00A7c"
        parseReplacements["<pink>"] = "\u00A7d"
        parseReplacements["<yellow>"] = "\u00A7e"
        parseReplacements["<white>"] = "\u00A7f"
        parseReplacements["<magic>"] = "\u00A7k"
        parseReplacements["<bold>"] = "\u00A7l"
        parseReplacements["<strong>"] = "\u00A7l"
        parseReplacements["<strike>"] = "\u00A7m"
        parseReplacements["<strikethrough>"] = "\u00A7m"
        parseReplacements["<under>"] = "\u00A7n"
        parseReplacements["<underline>"] = "\u00A7n"
        parseReplacements["<italic>"] = "\u00A7o"
        parseReplacements["<em>"] = "\u00A7o"
        parseReplacements["<reset>"] = "\u00A7r"

        // Color by semantic functionality
        parseReplacements["<l>"] = "\u00A72"
        parseReplacements["<logo>"] = "\u00A72"
        parseReplacements["<a>"] = "\u00A76"
        parseReplacements["<art>"] = "\u00A76"
        parseReplacements["<n>"] = "\u00A77"
        parseReplacements["<notice>"] = "\u00A77"
        parseReplacements["<i>"] = "\u00A7e"
        parseReplacements["<info>"] = "\u00A7e"
        parseReplacements["<g>"] = "\u00A7a"
        parseReplacements["<good>"] = "\u00A7a"
        parseReplacements["<b>"] = "\u00A7c"
        parseReplacements["<bad>"] = "\u00A7c"
        parseReplacements["<k>"] = "\u00A7b"
        parseReplacements["<key>"] = "\u00A7b"
        parseReplacements["<v>"] = "\u00A7d"
        parseReplacements["<value>"] = "\u00A7d"
        parseReplacements["<h>"] = "\u00A7d"
        parseReplacements["<highlight>"] = "\u00A7d"
        parseReplacements["<c>"] = "\u00A7b"
        parseReplacements["<command>"] = "\u00A7b"
        parseReplacements["<p>"] = "\u00A73"
        parseReplacements["<parameter>"] = "\u00A73"
        parseReplacements["&&"] = "&"
        parseReplacements["§§"] = "§"

        // Color by number/char
        var i = 48
        while (i <= 122) {
            val c: Char = i.toChar()
            parseReplacements["§$c"] = "\u00A7" + c
            parseReplacements["&$c"] = "\u00A7" + c
            if (i == 57) i = 96
            i++
        }

        // Build the parse pattern and compile it
        val patternStringBuilder = StringBuilder()
        for (find in parseReplacements.keys) {
            patternStringBuilder.append('(')
            patternStringBuilder.append(Pattern.quote(find))
            patternStringBuilder.append(")|")
        }
        var patternString: String = patternStringBuilder.toString()
        patternString = patternString.substring(0, patternString.length - 1) // Remove the last |
        parsePattern = Pattern.compile(patternString)
    }


    fun stripColor(txt: String): String {
        return ChatColor.stripColor(txt)!!
    }
}