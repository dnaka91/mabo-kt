package rocks.dnaka91.mabo.buf

import rocks.dnaka91.mabo.FieldId
import rocks.dnaka91.mabo.VariantId
import rocks.dnaka91.mabo.Varint
import java.math.BigInteger
import java.nio.ByteBuffer

public interface Encode {
    public fun encode(w: ByteBuffer)
}

public object Encoder {
    @JvmStatic
    public fun encodeBool(
        w: ByteBuffer,
        value: Boolean,
    ) {
        w.put(if (value) 1 else 0)
    }

    @JvmStatic
    public fun encodeU8(
        w: ByteBuffer,
        value: Byte,
    ) {
        w.put(value)
    }

    @JvmStatic
    public fun encodeU16(
        w: ByteBuffer,
        value: Short,
    ) {
        val (buf, size) = Varint.encodeU16(value.toUShort())
        w.put(buf.copyOf(size))
    }

    @JvmStatic
    public fun encodeU32(
        w: ByteBuffer,
        value: Int,
    ) {
        val (buf, size) = Varint.encodeU32(value.toUInt())
        w.put(buf.copyOf(size))
    }

    @JvmStatic
    public fun encodeU64(
        w: ByteBuffer,
        value: Long,
    ) {
        val (buf, size) = Varint.encodeU64(value.toULong())
        w.put(buf.copyOf(size))
    }

    @JvmStatic
    public fun encodeU128(
        w: ByteBuffer,
        value: BigInteger,
    ) {
        val (buf, size) = Varint.encodeU128(value)
        w.put(buf.copyOf(size))
    }

    @JvmStatic
    public fun encodeI8(
        w: ByteBuffer,
        value: Byte,
    ) {
        w.put(value)
    }

    @JvmStatic
    public fun encodeI16(
        w: ByteBuffer,
        value: Short,
    ) {
        val (buf, size) = Varint.encodeI16(value)
        w.put(buf.copyOf(size))
    }

    @JvmStatic
    public fun encodeI32(
        w: ByteBuffer,
        value: Int,
    ) {
        val (buf, size) = Varint.encodeI32(value)
        w.put(buf.copyOf(size))
    }

    @JvmStatic
    public fun encodeI64(
        w: ByteBuffer,
        value: Long,
    ) {
        val (buf, size) = Varint.encodeI64(value)
        w.put(buf.copyOf(size))
    }

    @JvmStatic
    public fun encodeI128(
        w: ByteBuffer,
        value: BigInteger,
    ) {
        val (buf, size) = Varint.encodeI128(value)
        w.put(buf.copyOf(size))
    }

    @JvmStatic
    public fun encodeF32(
        w: ByteBuffer,
        value: Float,
    ) {
        w.putFloat(value)
    }

    @JvmStatic
    public fun encodeF64(
        w: ByteBuffer,
        value: Double,
    ) {
        w.putDouble(value)
    }

    @JvmStatic
    public fun encodeString(
        w: ByteBuffer,
        value: String,
    ) {
        encodeBytes(w, value.toByteArray(Charsets.UTF_8))
    }

    @JvmStatic
    public fun encodeBytes(
        w: ByteBuffer,
        value: ByteArray,
    ) {
        encodeU64(w, value.size.toLong())
        w.put(value)
    }

    @JvmStatic
    public fun <T> encodeVec(
        w: ByteBuffer,
        value: List<T>,
        encode: (ByteBuffer, T) -> Unit,
    ) {
        encodeU64(w, value.size.toLong())
        value.forEach { encode(w, it) }
    }

    @JvmStatic
    public fun <K, V> encodeHashMap(
        w: ByteBuffer,
        value: Map<K, V>,
        encodeKey: (ByteBuffer, K) -> Unit,
        encodeValue: (ByteBuffer, V) -> Unit,
    ) {
        encodeU64(w, value.size.toLong())
        value.forEach { (k, v) ->
            encodeKey(w, k)
            encodeValue(w, v)
        }
    }

    @JvmStatic
    public fun <T> encodeHashSet(
        w: ByteBuffer,
        value: Set<T>,
        encode: (ByteBuffer, T) -> Unit,
    ) {
        encodeU64(w, value.size.toLong())
        value.forEach { encode(w, it) }
    }

    @JvmStatic
    public fun <T> encodeOption(
        w: ByteBuffer,
        value: T?,
        encode: (ByteBuffer, T) -> Unit,
    ) {
        if (value != null) {
            w.put(1)
            encode(w, value)
        } else {
            w.put(0)
        }
    }

    @JvmStatic
    public fun <T> encodeArray(
        w: ByteBuffer,
        value: Array<T>,
        encode: (ByteBuffer, T) -> Unit,
    ) {
        encodeU64(w, value.size.toLong())
        value.forEach { encode(w, it) }
    }

    @JvmStatic
    public fun encodeFieldId(
        w: ByteBuffer,
        id: FieldId,
    ) {
        encodeU32(w, id.intoRaw())
    }

    @JvmStatic
    public fun encodeVariantId(
        w: ByteBuffer,
        id: VariantId,
    ) {
        encodeU32(w, id.value)
    }

    @JvmStatic
    public fun encodeField(
        w: ByteBuffer,
        id: FieldId,
        encode: (ByteBuffer) -> Unit,
    ) {
        encodeFieldId(w, id)
        encode(w)
    }

    @JvmStatic
    public fun <T> encodeFieldOption(
        w: ByteBuffer,
        id: FieldId,
        value: T?,
        encode: (ByteBuffer, T) -> Unit,
    ) {
        if (value != null) {
            encodeFieldId(w, id)
            encode(w, value)
        }
    }
}
