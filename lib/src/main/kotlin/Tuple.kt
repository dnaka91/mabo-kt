@file:JvmName("Tuples")

package rocks.dnaka91.mabo

import java.io.Serializable

@JvmRecord
public data class Tuple2<out A, out B>(
    public val first: A,
    public val second: B,
) : Serializable {
    override fun toString(): String = "($first, $second)"
}

public fun <T> Tuple2<T, T>.toList(): List<T> = listOf(first, second)

@JvmRecord
public data class Tuple3<out A, out B, out C>(
    public val first: A,
    public val second: B,
    public val third: C,
) : Serializable {
    override fun toString(): String = "($first, $second, $third)"
}

public fun <T> Tuple3<T, T, T>.toList(): List<T> = listOf(first, second, third)

@JvmRecord
public data class Tuple4<out A, out B, out C, out D>(
    public val first: A,
    public val second: B,
    public val third: C,
    public val fourth: D,
) : Serializable {
    override fun toString(): String = "($first, $second, $third, $fourth)"
}

public fun <T> Tuple4<T, T, T, T>.toList(): List<T> = listOf(first, second, third, fourth)

@JvmRecord
public data class Tuple5<out A, out B, out C, out D, out E>(
    public val first: A,
    public val second: B,
    public val third: C,
    public val fourth: D,
    public val fifth: E,
) : Serializable {
    override fun toString(): String = "($first, $second, $third, $fourth, $fifth)"
}

public fun <T> Tuple5<T, T, T, T, T>.toList(): List<T> = listOf(first, second, third, fourth, fifth)

@JvmRecord
public data class Tuple6<out A, out B, out C, out D, out E, out F>(
    public val first: A,
    public val second: B,
    public val third: C,
    public val fourth: D,
    public val fifth: E,
    public val sixth: F,
) : Serializable {
    override fun toString(): String = "($first, $second, $third, $fourth, $fifth, $sixth)"
}

public fun <T> Tuple6<T, T, T, T, T, T>.toList(): List<T> =
    listOf(
        first,
        second,
        third,
        fourth,
        fifth,
        sixth,
    )

@JvmRecord
public data class Tuple7<out A, out B, out C, out D, out E, out F, out G>(
    public val first: A,
    public val second: B,
    public val third: C,
    public val fourth: D,
    public val fifth: E,
    public val sixth: F,
    public val seventh: G,
) : Serializable {
    override fun toString(): String = "($first, $second, $third, $fourth, $fifth, $sixth, $seventh)"
}

public fun <T> Tuple7<T, T, T, T, T, T, T>.toList(): List<T> =
    listOf(first, second, third, fourth, fifth, sixth, seventh)

@JvmRecord
public data class Tuple8<out A, out B, out C, out D, out E, out F, out G, out H>(
    public val first: A,
    public val second: B,
    public val third: C,
    public val fourth: D,
    public val fifth: E,
    public val sixth: F,
    public val seventh: G,
    public val eighth: H,
) : Serializable {
    override fun toString(): String =
        "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth)"
}

public fun <T> Tuple8<T, T, T, T, T, T, T, T>.toList(): List<T> =
    listOf(first, second, third, fourth, fifth, sixth, seventh, eighth)

@JvmRecord
public data class Tuple9<out A, out B, out C, out D, out E, out F, out G, out H, out I>(
    public val first: A,
    public val second: B,
    public val third: C,
    public val fourth: D,
    public val fifth: E,
    public val sixth: F,
    public val seventh: G,
    public val eighth: H,
    public val ninth: I,
) : Serializable {
    override fun toString(): String =
        "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth, $ninth)"
}

public fun <T> Tuple9<T, T, T, T, T, T, T, T, T>.toList(): List<T> =
    listOf(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth)

@JvmRecord
public data class Tuple10<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J>(
    public val first: A,
    public val second: B,
    public val third: C,
    public val fourth: D,
    public val fifth: E,
    public val sixth: F,
    public val seventh: G,
    public val eighth: H,
    public val ninth: I,
    public val tenth: J,
) : Serializable {
    override fun toString(): String =
        "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth, $ninth, $tenth)"
}

public fun <T> Tuple10<T, T, T, T, T, T, T, T, T, T>.toList(): List<T> =
    listOf(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth)

@JvmRecord
public data class Tuple11<
    out A,
    out B,
    out C,
    out D,
    out E,
    out F,
    out G,
    out H,
    out I,
    out J,
    out K,
>(
    public val first: A,
    public val second: B,
    public val third: C,
    public val fourth: D,
    public val fifth: E,
    public val sixth: F,
    public val seventh: G,
    public val eighth: H,
    public val ninth: I,
    public val tenth: J,
    public val eleventh: K,
) : Serializable {
    override fun toString(): String =
        "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth, $ninth, $tenth, " +
            "$eleventh)"
}

public fun <T> Tuple11<T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> =
    listOf(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh)

@JvmRecord
public data class Tuple12<
    out A,
    out B,
    out C,
    out D,
    out E,
    out F,
    out G,
    out H,
    out I,
    out J,
    out K,
    out L,
>(
    public val first: A,
    public val second: B,
    public val third: C,
    public val fourth: D,
    public val fifth: E,
    public val sixth: F,
    public val seventh: G,
    public val eighth: H,
    public val ninth: I,
    public val tenth: J,
    public val eleventh: K,
    public val twelfth: L,
) : Serializable {
    override fun toString(): String =
        "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth, $ninth, $tenth, " +
            "$eleventh, $twelfth)"
}

public fun <T> Tuple12<T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> =
    listOf(
        first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh,
        twelfth,
    )
