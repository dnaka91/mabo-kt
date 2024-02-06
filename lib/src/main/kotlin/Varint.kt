package rocks.dnaka91.mabo

import java.math.BigInteger
import java.nio.ByteBuffer
import kotlin.ULong
import kotlin.math.min

internal object Zigzag {
    fun encodeI16(value: Short): UShort =
        ((value.toInt() shl 1) xor (value.toInt() shr 15)).toUShort()

    fun decodeI16(value: UShort): Short =
        ((value.toInt() shr 1) xor (-(value.toInt() and 0b1))).toShort()

    fun encodeI32(value: Int): UInt = ((value shl 1) xor (value shr 31)).toUInt()

    fun decodeI32(value: UInt): Int = ((value shr 1).toInt() xor (-(value and 0b1u).toInt()))

    fun encodeI64(value: Long): ULong = ((value shl 1) xor (value shr 63)).toULong()

    fun decodeI64(value: ULong): Long = ((value shr 1).toLong() xor (-(value and 0b1u).toLong()))

    fun encodeI128(value: BigInteger): BigInteger = ((value shl 1) xor (value shr 127))

    fun decodeI128(value: BigInteger): BigInteger =
        ((value shr 1) xor (-(value and 0b1.toBigInteger())))
}

internal object Varint {
    fun encodeU16(v: UShort): Pair<ByteArray, Int> {
        var value = v.toUInt()
        val buf = ByteArray(3)

        for (i in buf.indices) {
            buf[i] = (value and 0xffu).toByte()
            if (value < 128u) {
                return buf to i + 1
            }

            buf[i] = (buf[i].toUInt() or 0x80u).toByte()
            value = value shr 7
        }

        return buf to buf.size
    }

    fun decodeU16(bb: ByteBuffer): Result<UShort> {
        var value = 0u

        for (i in 0..<min(bb.remaining(), 3)) {
            val b = bb.get().toUInt()
            value = value or ((b and 0x7fu) shl (7 * i))

            if (b and 0x80u == 0u) {
                return Result.success(value.toUShort())
            }
        }

        return Result.failure(DecodeIntException())
    }

    fun sizeU16(v: UShort): Int =
        if (v == 0u.toUShort()) 1 else (UShort.SIZE_BITS - v.countLeadingZeroBits() + 6) / 7

    fun encodeU32(v: UInt): Pair<ByteArray, Int> {
        var value = v
        val buf = ByteArray(5)

        for (i in buf.indices) {
            buf[i] = (value and 0xffu).toByte()
            if (value < 128u) {
                return buf to i + 1
            }

            buf[i] = (buf[i].toUInt() or 0x80u).toByte()
            value = value shr 7
        }

        return buf to buf.size
    }

    fun decodeU32(bb: ByteBuffer): Result<UInt> {
        var value = 0u

        for (i in 0..<min(bb.remaining(), 5)) {
            val b = bb.get().toUInt()
            value = value or ((b and 0x7fu) shl (7 * i))

            if (b and 0x80u == 0u) {
                return Result.success(value)
            }
        }

        return Result.failure(DecodeIntException())
    }

    fun sizeU32(v: UInt): Int =
        if (v == 0u) 1 else (UInt.SIZE_BITS - v.countLeadingZeroBits() + 6) / 7

    fun encodeU64(v: ULong): Pair<ByteArray, Int> {
        var value = v
        val buf = ByteArray(10)

        for (i in buf.indices) {
            buf[i] = (value and 0xffu).toByte()
            if (value < 128u) {
                return buf to i + 1
            }

            buf[i] = (buf[i].toUInt() or 0x80u).toByte()
            value = value shr 7
        }

        return buf to buf.size
    }

    fun decodeU64(bb: ByteBuffer): Result<ULong> {
        var value = 0uL

        for (i in 0..<min(bb.remaining(), 10)) {
            val b = bb.get().toULong()
            value = value or ((b and 0x7fu) shl (7 * i))

            if (b and 0x80u == 0uL) {
                return Result.success(value)
            }
        }

        return Result.failure(DecodeIntException())
    }

    fun sizeU64(v: ULong): Int =
        if (v == 0uL) 1 else (ULong.SIZE_BITS - v.countLeadingZeroBits() + 6) / 7

    fun encodeU128(v: BigInteger): Pair<ByteArray, Int> {
        var value = v
        val buf = ByteArray(19)

        for (i in buf.indices) {
            buf[i] = (value and 0xff.toBigInteger()).toByte()
            if (value < 128.toBigInteger()) {
                return buf to i + 1
            }

            buf[i] = (buf[i].toUInt() or 0x80u).toByte()
            value = value shr 7
        }

        return buf to buf.size
    }

    fun decodeU128(bb: ByteBuffer): Result<BigInteger> {
        var value = BigInteger.ZERO

        for (i in 0..<min(bb.remaining(), 19)) {
            val b = bb.get().toInt().toBigInteger()
            value = value or ((b and 0x7f.toBigInteger()) shl (7 * i))

            if (b and 0x80.toBigInteger() == BigInteger.ZERO) {
                return Result.success(value)
            }
        }

        return Result.failure(DecodeIntException())
    }

    fun sizeU128(v: BigInteger): Int = if (v == BigInteger.ZERO) 1 else (v.bitLength() + 6) / 7

    fun encodeI16(v: Short): Pair<ByteArray, Int> = encodeU16(Zigzag.encodeI16(v))

    fun decodeI16(bb: ByteBuffer): Result<Short> =
        decodeU16(
            bb,
        )
            .map { Zigzag.decodeI16(it) }

    fun sizeI16(v: Short): Int = sizeU16(Zigzag.encodeI16(v))

    fun encodeI32(v: Int): Pair<ByteArray, Int> = encodeU32(Zigzag.encodeI32(v))

    fun decodeI32(bb: ByteBuffer): Result<Int> =
        decodeU32(
            bb,
        )
            .map { Zigzag.decodeI32(it) }

    fun sizeI32(v: Int): Int = sizeU32(Zigzag.encodeI32(v))

    fun encodeI64(v: Long): Pair<ByteArray, Int> = encodeU64(Zigzag.encodeI64(v))

    fun decodeI64(bb: ByteBuffer): Result<Long> =
        decodeU64(
            bb,
        )
            .map { Zigzag.decodeI64(it) }

    fun sizeI64(v: Long): Int = sizeU64(Zigzag.encodeI64(v))

    fun encodeI128(v: BigInteger): Pair<ByteArray, Int> = encodeU128(Zigzag.encodeI128(v))

    fun decodeI128(bb: ByteBuffer): Result<BigInteger> =
        decodeU128(
            bb,
        )
            .map { Zigzag.decodeI128(it) }

    fun sizeI128(v: BigInteger): Int = sizeU128(Zigzag.encodeI128(v))
}
