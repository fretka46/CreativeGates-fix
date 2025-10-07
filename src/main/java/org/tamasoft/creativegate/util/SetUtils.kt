package org.tamasoft.creativegate.util

import java.util.*


object SetUtils {

    /**
     * @param collection1
     * @param collection2
     * @param <T>
     * @return Returns the difference of 2 sets i.e. a Set of all elements which appear in set 1 but do not appear in set 2.<br></br>
     * Note that set 2 may have **more elements** which are not in set 1, but they do not appear in the difference.<br></br>
     * For example ***c1={1,2,3} c2={2,3,5,6} diff(c1,c2) = {1}***
    </T> */
    fun <T> diff(collection1: Collection<T>, collection2: Collection<T>): Set<T> {
        val difference: MutableSet<T> = HashSet(collection1)
        if (collection2 is Set<*>) {
            difference.removeIf { o: T -> collection2.contains(o) }
        } else {
            difference.removeAll(collection2.toSet())
        }
        return difference
    }
}

