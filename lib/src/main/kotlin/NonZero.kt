package rocks.dnaka91.mabo

import java.math.BigInteger

public data class NonZeroUByte(
    private val value: Byte,
) : Comparable<NonZeroUByte> {
    init {
        require(value != 0.toByte()) {
            "Integer value mustn't be zero"
        }
    }

    public fun get(): Byte = this.value

    override fun compareTo(other: NonZeroUByte): Int = this.value.compareTo(other.value)
}

public data class NonZeroUShort(
    private val value: Short,
) : Comparable<NonZeroUShort> {
    init {
        require(value != 0u.toShort()) {
            "Integer value mustn't be zero"
        }
    }

    public fun get(): Short = this.value

    override fun compareTo(other: NonZeroUShort): Int = this.value.compareTo(other.value)
}

public data class NonZeroUInt(
    private val value: Int,
) : Comparable<NonZeroUInt> {
    init {
        require(value != 0) {
            "Integer value mustn't be zero"
        }
    }

    public fun get(): Int = this.value

    override fun compareTo(other: NonZeroUInt): Int = this.value.compareTo(other.value)
}

public data class NonZeroULong(
    private val value: Long,
) : Comparable<NonZeroULong> {
    init {
        require(value != 0L) {
            "Integer value mustn't be zero"
        }
    }

    public fun get(): Long = this.value

    override fun compareTo(other: NonZeroULong): Int = this.value.compareTo(other.value)
}

public data class NonZeroBigInteger(
    private val value: BigInteger,
) : Comparable<NonZeroBigInteger> {
    init {
        require(value != BigInteger.ZERO) {
            "Integer value mustn't be zero"
        }
    }

    public fun get(): BigInteger = this.value

    override fun compareTo(other: NonZeroBigInteger): Int = this.value.compareTo(other.value)
}

public data class NonZeroByte(
    private val value: Byte,
) : Comparable<NonZeroByte> {
    init {
        require(value != 0.toByte()) {
            "Integer value mustn't be zero"
        }
    }

    public fun get(): Byte = this.value

    override fun compareTo(other: NonZeroByte): Int = this.value.compareTo(other.value)
}

public data class NonZeroShort(
    private val value: Short,
) : Comparable<NonZeroShort> {
    init {
        require(value != 0.toShort()) {
            "Integer value mustn't be zero"
        }
    }

    public fun get(): Short = this.value

    override fun compareTo(other: NonZeroShort): Int = this.value.compareTo(other.value)
}

public data class NonZeroInt(
    private val value: Int,
) : Comparable<NonZeroInt> {
    init {
        require(value != 0) {
            "Integer value mustn't be zero"
        }
    }

    public fun get(): Int = this.value

    override fun compareTo(other: NonZeroInt): Int = this.value.compareTo(other.value)
}

public data class NonZeroLong(
    private val value: Long,
) : Comparable<NonZeroLong> {
    init {
        require(value != 0L) {
            "Integer value mustn't be zero"
        }
    }

    public fun get(): Long = this.value

    override fun compareTo(other: NonZeroLong): Int = this.value.compareTo(other.value)
}

public data class NonZeroFloat(
    private val value: Float,
) : Comparable<NonZeroFloat> {
    init {
        require(value != 0.0f) {
            "Integer value mustn't be zero"
        }
    }

    public fun get(): Float = this.value

    override fun compareTo(other: NonZeroFloat): Int = this.value.compareTo(other.value)
}

public data class NonZeroDouble(
    private val value: Double,
) : Comparable<NonZeroDouble> {
    init {
        require(value != 0.0) {
            "Integer value mustn't be zero"
        }
    }

    public fun get(): Double = this.value

    override fun compareTo(other: NonZeroDouble): Int = this.value.compareTo(other.value)
}

public data class NonZeroString(private val value: String) : Comparable<String>, CharSequence {
    init {
        require(value.isNotEmpty()) {
            "String value mustn't be empty"
        }
    }

    public fun get(): String = this.value

    override fun compareTo(other: String): Int = this.value.compareTo(other)

    override val length: Int
        get() = this.value.length

    override fun get(index: Int): Char = this.value[index]

    override fun subSequence(
        startIndex: Int,
        endIndex: Int,
    ): CharSequence =
        this.value.subSequence(
            startIndex,
            endIndex,
        )
}

public data class NonZeroBytes(private val value: ByteArray) {
    init {
        require(value.isNotEmpty()) {
            "Bytes value mustn't be empty"
        }
    }

    public fun get(): ByteArray = this.value

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NonZeroBytes

        return value.contentEquals(other.value)
    }

    override fun hashCode(): Int {
        return value.contentHashCode()
    }
}

public data class NonZeroVec<out C : List<T>, T>(private val value: C) : List<T> {
    init {
        require(value.isNotEmpty()) {
            "List value mustn't be empty"
        }
    }

    public fun get(): C = this.value

    override val size: Int
        get() = this.value.size

    override fun contains(element: T): Boolean = this.value.contains(element)

    override fun containsAll(elements: Collection<T>): Boolean = this.value.containsAll(elements)

    override fun get(index: Int): T = this.value[index]

    override fun indexOf(element: T): Int = this.value.indexOf(element)

    override fun isEmpty(): Boolean = false

    override fun iterator(): Iterator<T> = this.value.iterator()

    override fun lastIndexOf(element: T): Int = this.value.lastIndexOf(element)

    override fun listIterator(): ListIterator<T> = this.value.listIterator()

    override fun listIterator(index: Int): ListIterator<T> = this.value.listIterator(index)

    override fun subList(
        fromIndex: Int,
        toIndex: Int,
    ): List<T> =
        this.value.subList(
            fromIndex,
            toIndex,
        )
}

public data class NonZeroHashMap<out C : Map<K, V>, K, V>(private val value: C) : Map<K, V> {
    init {
        require(value.isNotEmpty()) {
            "Map value mustn't be empty"
        }
    }

    public fun get(): C = this.value

    override val entries: Set<Map.Entry<K, V>>
        get() = this.value.entries
    override val keys: Set<K>
        get() = this.value.keys
    override val size: Int
        get() = this.value.size
    override val values: Collection<V>
        get() = this.value.values

    override fun containsKey(key: K): Boolean = this.value.containsKey(key)

    override fun containsValue(value: V): Boolean = this.value.containsValue(value)

    override fun get(key: K): V? = this.value[key]

    override fun isEmpty(): Boolean = false
}

public data class NonZeroHashSet<out C : Set<T>, T>(private val value: C) : Set<T> {
    init {
        require(value.isNotEmpty()) {
            "Set value mustn't be empty"
        }
    }

    public fun get(): C = this.value

    override val size: Int
        get() = this.value.size

    override fun contains(element: T): Boolean = this.value.contains(element)

    override fun containsAll(elements: Collection<T>): Boolean = this.value.containsAll(elements)

    override fun isEmpty(): Boolean = false

    override fun iterator(): Iterator<T> = this.value.iterator()
}
