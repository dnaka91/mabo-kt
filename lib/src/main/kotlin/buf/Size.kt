package rocks.dnaka91.mabo.buf

import rocks.dnaka91.mabo.Varint
import java.math.BigInteger

public interface Size {
    public fun size(): Int
}

@Suppress("UNUSED_PARAMETER")
public object Sizer {
    @JvmStatic
    public fun sizeBool(value: Boolean): Int = 1

    @JvmStatic
    public fun sizeU8(value: Byte): Int = 1

    @JvmStatic
    public fun sizeU16(value: Short): Int = Varint.sizeU16(value.toUShort())

    @JvmStatic
    public fun sizeU32(value: Int): Int = Varint.sizeU32(value.toUInt())

    @JvmStatic
    public fun sizeU64(value: Long): Int = Varint.sizeU64(value.toULong())

    @JvmStatic
    public fun sizeU128(value: BigInteger): Int = Varint.sizeU128(value)

    @JvmStatic
    public fun sizeI8(value: Byte): Int = 1

    @JvmStatic
    public fun sizeI16(value: Short): Int = Varint.sizeI16(value)

    @JvmStatic
    public fun sizeI32(value: Int): Int = Varint.sizeI32(value)

    @JvmStatic
    public fun sizeI64(value: Long): Int = Varint.sizeI64(value)

    @JvmStatic
    public fun sizeI128(value: BigInteger): Int = Varint.sizeI128(value)

    @JvmStatic
    public fun sizeF32(value: Float): Int = 4

    @JvmStatic
    public fun sizeF64(value: Double): Int = 8

    @JvmStatic
    public fun sizeString(value: String): Int = sizeBytes(value.toByteArray(Charsets.UTF_8))

    @JvmStatic
    public fun sizeBytes(value: ByteArray): Int = sizeU64(value.size.toLong()) + value.size

    @JvmStatic
    public fun <T> sizeVec(
        value: List<T>,
        size: (T) -> Int,
    ): Int = sizeU64(value.size.toLong()) + value.sumOf(size)

    @JvmStatic
    public fun <K, V> sizeHashMap(
        value: Map<K, V>,
        sizeKey: (K) -> Int,
        sizeValue: (V) -> Int,
    ): Int =
        sizeU64(value.size.toLong()) +
            value.entries.sumOf { (k, v) -> sizeKey(k) + sizeValue(v) }

    @JvmStatic
    public fun <T> sizeHashSet(
        value: Set<T>,
        size: (T) -> Int,
    ): Int = sizeU64(value.size.toLong()) + value.sumOf(size)

    @JvmStatic
    public fun <T> sizeOption(
        value: T?,
        size: (T) -> Int,
    ): Int =
        if (value != null) {
            sizeU8(1) + size(value)
        } else {
            sizeU8(0)
        }

    @JvmStatic
    public fun <T> sizeArray(
        value: Array<T>,
        size: (T) -> Int,
    ): Int = sizeU64(value.size.toLong()) + value.sumOf(size)

    @JvmStatic
    public fun sizeFieldId(id: Int): Int = sizeU32(id shl 3)

    @JvmStatic
    public fun sizeVariantId(id: Int): Int = sizeU32(id)

    @JvmStatic
    public fun sizeField(
        id: Int,
        size: () -> Int,
    ): Int = sizeFieldId(id) + size()

    @JvmStatic
    public fun <T> sizeFieldOption(
        id: Int,
        value: T?,
        size: (T) -> Int,
    ): Int =
        if (value != null) {
            sizeFieldId(id) + size(value)
        } else {
            0
        }
}
