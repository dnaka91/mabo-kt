package rocks.dnaka91.mabo.buf

import rocks.dnaka91.mabo.FieldId
import rocks.dnaka91.mabo.NonZeroBigInteger
import rocks.dnaka91.mabo.NonZeroByte
import rocks.dnaka91.mabo.NonZeroBytes
import rocks.dnaka91.mabo.NonZeroHashMap
import rocks.dnaka91.mabo.NonZeroHashSet
import rocks.dnaka91.mabo.NonZeroInt
import rocks.dnaka91.mabo.NonZeroLong
import rocks.dnaka91.mabo.NonZeroShort
import rocks.dnaka91.mabo.NonZeroString
import rocks.dnaka91.mabo.NonZeroUByte
import rocks.dnaka91.mabo.NonZeroUInt
import rocks.dnaka91.mabo.NonZeroULong
import rocks.dnaka91.mabo.NonZeroUShort
import rocks.dnaka91.mabo.NonZeroVec
import rocks.dnaka91.mabo.VariantId
import rocks.dnaka91.mabo.Varint
import java.math.BigInteger
import java.nio.ByteBuffer

public interface Decode<out T> {
    public fun decode(r: ByteBuffer): Result<T>
}

public object Decoder {
    @JvmStatic
    public fun decodeBool(r: ByteBuffer): Result<Boolean> =
        ensureSize(r, 1).map {
            r.get() != 0.toByte()
        }

    @JvmStatic
    public fun decodeU8(r: ByteBuffer): Result<Byte> =
        ensureSize(r, 1).map {
            r.get()
        }

    @JvmStatic
    public fun decodeU16(r: ByteBuffer): Result<Short> = Varint.decodeU16(r).map { it.toShort() }

    @JvmStatic
    public fun decodeU32(r: ByteBuffer): Result<Int> = Varint.decodeU32(r).map { it.toInt() }

    @JvmStatic
    public fun decodeU64(r: ByteBuffer): Result<Long> = Varint.decodeU64(r).map { it.toLong() }

    @JvmStatic
    public fun decodeU128(r: ByteBuffer): Result<BigInteger> = Varint.decodeU128(r)

    @JvmStatic
    public fun decodeI8(r: ByteBuffer): Result<Byte> =
        ensureSize(r, 1).map {
            r.get()
        }

    @JvmStatic
    public fun decodeI16(r: ByteBuffer): Result<Short> = Varint.decodeI16(r)

    @JvmStatic
    public fun decodeI32(r: ByteBuffer): Result<Int> = Varint.decodeI32(r)

    @JvmStatic
    public fun decodeI64(r: ByteBuffer): Result<Long> = Varint.decodeI64(r)

    @JvmStatic
    public fun decodeI128(r: ByteBuffer): Result<BigInteger> = Varint.decodeI128(r)

    @JvmStatic
    public fun decodeF32(r: ByteBuffer): Result<Float> = ensureSize(r, 4).map { r.getFloat() }

    @JvmStatic
    public fun decodeF64(r: ByteBuffer): Result<Double> = ensureSize(r, 8).map { r.getDouble() }

    @JvmStatic
    public fun decodeString(r: ByteBuffer): Result<String> =
        decodeBytes(r).map { it.toString(Charsets.UTF_8) }

    @JvmStatic
    public fun decodeBytes(r: ByteBuffer): Result<ByteArray> =
        decodeU64(r).map { it.toInt() }.mapCatching { size ->
            ensureSize(r, size).getOrThrow()

            val buf = ByteArray(size)
            r.get(buf)

            buf
        }

    @JvmStatic
    public fun <T> decodeVec(
        r: ByteBuffer,
        decode: (ByteBuffer) -> Result<T>,
    ): Result<List<T>> =
        decodeU64(r).map { it.toInt() }.mapCatching { size ->
            val vec = ArrayList<T>(size)
            for (i in 0..<size) {
                vec.add(decode(r).getOrThrow())
            }
            vec
        }

    @JvmStatic
    public fun <K, V> decodeHashMap(
        r: ByteBuffer,
        decodeKey: (ByteBuffer) -> Result<K>,
        decodeValue: (ByteBuffer) -> Result<V>,
    ): Result<Map<K, V>> =
        decodeU64(r).map { it.toInt() }.mapCatching { size ->
            val map = HashMap<K, V>(size)
            for (i in 0..<size) {
                map[decodeKey(r).getOrThrow()] = decodeValue(r).getOrThrow()
            }
            map
        }

    @JvmStatic
    public fun <T> decodeHashSet(
        r: ByteBuffer,
        decode: (ByteBuffer) -> Result<T>,
    ): Result<Set<T>> =
        decodeU64(r).map { it.toInt() }.mapCatching { size ->
            val set = HashSet<T>(size)
            for (i in 0..<size) {
                set.add(decode(r).getOrThrow())
            }
            set
        }

    @JvmStatic
    public fun decodeNonZeroU8(r: ByteBuffer): Result<NonZeroUByte> =
        decodeU8(r).mapCatching {
            if (it == 0.toByte()) {
                throw ZeroException()
            }
            NonZeroUByte(it)
        }

    @JvmStatic
    public fun decodeNonZeroU16(r: ByteBuffer): Result<NonZeroUShort> =
        decodeU16(r).mapCatching {
            if (it == 0.toShort()) {
                throw ZeroException()
            }
            NonZeroUShort(it)
        }

    @JvmStatic
    public fun decodeNonZeroU32(r: ByteBuffer): Result<NonZeroUInt> =
        decodeU32(r).mapCatching {
            if (it == 0) {
                throw ZeroException()
            }
            NonZeroUInt(it)
        }

    @JvmStatic
    public fun decodeNonZeroU64(r: ByteBuffer): Result<NonZeroULong> =
        decodeU64(r).mapCatching {
            if (it == 0L) {
                throw ZeroException()
            }
            NonZeroULong(it)
        }

    @JvmStatic
    public fun decodeNonZeroU128(r: ByteBuffer): Result<NonZeroBigInteger> =
        decodeU128(r).mapCatching {
            if (it == BigInteger.ZERO) {
                throw ZeroException()
            }
            NonZeroBigInteger(it)
        }

    @JvmStatic
    public fun decodeNonZeroI8(r: ByteBuffer): Result<NonZeroByte> =
        decodeI8(r).mapCatching {
            if (it == 0.toByte()) {
                throw ZeroException()
            }
            NonZeroByte(it)
        }

    @JvmStatic
    public fun decodeNonZeroI16(r: ByteBuffer): Result<NonZeroShort> =
        decodeI16(r).mapCatching {
            if (it == 0.toShort()) {
                throw ZeroException()
            }
            NonZeroShort(it)
        }

    @JvmStatic
    public fun decodeNonZeroI32(r: ByteBuffer): Result<NonZeroInt> =
        decodeI32(r).mapCatching {
            if (it == 0) {
                throw ZeroException()
            }
            NonZeroInt(it)
        }

    @JvmStatic
    public fun decodeNonZeroI64(r: ByteBuffer): Result<NonZeroLong> =
        decodeI64(r).mapCatching {
            if (it == 0L) {
                throw ZeroException()
            }
            NonZeroLong(it)
        }

    @JvmStatic
    public fun decodeNonZeroI128(r: ByteBuffer): Result<NonZeroBigInteger> =
        decodeI128(r).mapCatching {
            if (it == BigInteger.ZERO) {
                throw ZeroException()
            }
            NonZeroBigInteger(it)
        }

    @JvmStatic
    public fun decodeNonZeroString(r: ByteBuffer): Result<NonZeroString> =
        decodeString(r).mapCatching {
            if (it.isEmpty()) {
                throw ZeroException()
            }
            NonZeroString(it)
        }

    @JvmStatic
    public fun decodeNonZeroBytes(r: ByteBuffer): Result<NonZeroBytes> =
        decodeBytes(r).mapCatching {
            if (it.isEmpty()) {
                throw ZeroException()
            }
            NonZeroBytes(it)
        }

    @JvmStatic
    public fun <T> decodeNonZeroVec(
        r: ByteBuffer,
        decode: (ByteBuffer) -> Result<T>,
    ): Result<NonZeroVec<List<T>, T>> =
        decodeVec(r, decode).mapCatching {
            if (it.isEmpty()) {
                throw ZeroException()
            }
            NonZeroVec(it)
        }

    @JvmStatic
    public fun <K, V> decodeNonZeroHashMap(
        r: ByteBuffer,
        decodeKey: (ByteBuffer) -> Result<K>,
        decodeValue: (ByteBuffer) -> Result<V>,
    ): Result<NonZeroHashMap<Map<K, V>, K, V>> =
        decodeHashMap(r, decodeKey, decodeValue).mapCatching {
            if (it.isEmpty()) {
                throw ZeroException()
            }
            NonZeroHashMap(it)
        }

    @JvmStatic
    public fun <T> decodeNonZeroHashSet(
        r: ByteBuffer,
        decode: (ByteBuffer) -> Result<T>,
    ): Result<NonZeroHashSet<Set<T>, T>> =
        decodeHashSet(r, decode).mapCatching {
            if (it.isEmpty()) {
                throw ZeroException()
            }
            NonZeroHashSet(it)
        }

    @JvmStatic
    public fun decodeFieldId(r: ByteBuffer): Result<FieldId> =
        decodeU32(r).mapCatching {
            FieldId.fromRaw(it) ?: throw UnknownFieldEncodingException(it and 0b111)
        }

    @JvmStatic
    public fun decodeVariantId(r: ByteBuffer): Result<VariantId> =
        decodeU32(r).map { VariantId(it) }

    @JvmStatic
    private fun ensureSize(
        r: ByteBuffer,
        size: Int,
    ): Result<Unit> {
        return if (r.remaining() < size) {
            Result.failure(InsufficientDataException())
        } else {
            Result.success(Unit)
        }
    }
}
