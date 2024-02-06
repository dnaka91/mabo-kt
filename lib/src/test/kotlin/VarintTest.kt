package rocks.dnaka91.mabo

import java.math.BigInteger
import java.nio.ByteBuffer
import kotlin.test.Test
import kotlin.test.assertEquals

class ZigzagTest {
    @Test
    fun roundTripI16() {
        assertEquals(0, Zigzag.decodeI16(Zigzag.encodeI16(0)))
        assertEquals(1, Zigzag.decodeI16(Zigzag.encodeI16(1)))
        assertEquals(Short.MIN_VALUE, Zigzag.decodeI16(Zigzag.encodeI16(Short.MIN_VALUE)))
        assertEquals(Short.MAX_VALUE, Zigzag.decodeI16(Zigzag.encodeI16(Short.MAX_VALUE)))
    }

    @Test
    fun roundTripI32() {
        assertEquals(0, Zigzag.decodeI32(Zigzag.encodeI32(0)))
        assertEquals(1, Zigzag.decodeI32(Zigzag.encodeI32(1)))
        assertEquals(Int.MIN_VALUE, Zigzag.decodeI32(Zigzag.encodeI32(Int.MIN_VALUE)))
        assertEquals(Int.MAX_VALUE, Zigzag.decodeI32(Zigzag.encodeI32(Int.MAX_VALUE)))
    }

    @Test
    fun roundTripI64() {
        assertEquals(0, Zigzag.decodeI64(Zigzag.encodeI64(0)))
        assertEquals(1, Zigzag.decodeI64(Zigzag.encodeI64(1)))
        assertEquals(Long.MIN_VALUE, Zigzag.decodeI64(Zigzag.encodeI64(Long.MIN_VALUE)))
        assertEquals(Long.MAX_VALUE, Zigzag.decodeI64(Zigzag.encodeI64(Long.MAX_VALUE)))
    }

    @Test
    fun roundTripI128() {
        val min = BigInteger.valueOf(-2).pow(127)
        val max = BigInteger.valueOf(2).pow(127) - BigInteger.ONE

        assertEquals(BigInteger.ZERO, Zigzag.decodeI128(Zigzag.encodeI128(BigInteger.ZERO)))
        assertEquals(BigInteger.ONE, Zigzag.decodeI128(Zigzag.encodeI128(BigInteger.ONE)))
        assertEquals(min, Zigzag.decodeI128(Zigzag.encodeI128(min)))
        assertEquals(max, Zigzag.decodeI128(Zigzag.encodeI128(max)))
    }
}

class VarintTest {
    private fun <T> roundTrip(
        value: T,
        encode: (T) -> Pair<ByteArray, Int>,
        decode: (ByteBuffer) -> Result<T>,
    ): Result<T> {
        val (buf, size) = encode(value)
        return decode(ByteBuffer.wrap(buf, 0, size))
    }

    @Test
    fun roundTripU16() {
        assertEquals<Result<UShort>>(
            Result.success(1u),
            roundTrip(1u, Varint::encodeU16, Varint::decodeU16),
        )
        assertEquals(
            Result.success(UShort.MIN_VALUE),
            roundTrip(UShort.MIN_VALUE, Varint::encodeU16, Varint::decodeU16),
        )
        assertEquals(
            Result.success(UShort.MAX_VALUE),
            roundTrip(UShort.MAX_VALUE, Varint::encodeU16, Varint::decodeU16),
        )
    }

    @Test
    fun roundTripU32() {
        assertEquals(Result.success(1u), roundTrip(1u, Varint::encodeU32, Varint::decodeU32))
        assertEquals(
            Result.success(UInt.MIN_VALUE),
            roundTrip(UInt.MIN_VALUE, Varint::encodeU32, Varint::decodeU32),
        )
        assertEquals(
            Result.success(UInt.MAX_VALUE),
            roundTrip(UInt.MAX_VALUE, Varint::encodeU32, Varint::decodeU32),
        )
    }

    @Test
    fun roundTripU64() {
        assertEquals(Result.success(1uL), roundTrip(1uL, Varint::encodeU64, Varint::decodeU64))
        assertEquals(
            Result.success(ULong.MIN_VALUE),
            roundTrip(ULong.MIN_VALUE, Varint::encodeU64, Varint::decodeU64),
        )
        assertEquals(
            Result.success(ULong.MAX_VALUE),
            roundTrip(ULong.MAX_VALUE, Varint::encodeU64, Varint::decodeU64),
        )
    }

    @Test
    fun roundTripU128() {
        val min = BigInteger.ZERO
        val max = BigInteger.valueOf(2).pow(128) - BigInteger.ONE

        assertEquals(
            Result.success(BigInteger.ONE),
            roundTrip(BigInteger.ONE, Varint::encodeU128, Varint::decodeU128),
        )
        assertEquals(Result.success(min), roundTrip(min, Varint::encodeU128, Varint::decodeU128))
        assertEquals(Result.success(max), roundTrip(max, Varint::encodeU128, Varint::decodeU128))
    }

    @Test
    fun roundTripI16() {
        assertEquals<Result<Short>>(
            Result.success(0),
            roundTrip(0, Varint::encodeI16, Varint::decodeI16),
        )
        assertEquals<Result<Short>>(
            Result.success(1),
            roundTrip(1, Varint::encodeI16, Varint::decodeI16),
        )
        assertEquals(
            Result.success(Short.MIN_VALUE),
            roundTrip(Short.MIN_VALUE, Varint::encodeI16, Varint::decodeI16),
        )
        assertEquals(
            Result.success(Short.MAX_VALUE),
            roundTrip(Short.MAX_VALUE, Varint::encodeI16, Varint::decodeI16),
        )
    }

    @Test
    fun roundTripI32() {
        assertEquals(Result.success(0), roundTrip(0, Varint::encodeI32, Varint::decodeI32))
        assertEquals(Result.success(1), roundTrip(1, Varint::encodeI32, Varint::decodeI32))
        assertEquals(
            Result.success(Int.MIN_VALUE),
            roundTrip(Int.MIN_VALUE, Varint::encodeI32, Varint::decodeI32),
        )
        assertEquals(
            Result.success(Int.MAX_VALUE),
            roundTrip(Int.MAX_VALUE, Varint::encodeI32, Varint::decodeI32),
        )
    }

    @Test
    fun roundTripI64() {
        assertEquals(Result.success(0L), roundTrip(0L, Varint::encodeI64, Varint::decodeI64))
        assertEquals(Result.success(1L), roundTrip(1L, Varint::encodeI64, Varint::decodeI64))
        assertEquals(
            Result.success(Long.MIN_VALUE),
            roundTrip(Long.MIN_VALUE, Varint::encodeI64, Varint::decodeI64),
        )
        assertEquals(
            Result.success(Long.MAX_VALUE),
            roundTrip(Long.MAX_VALUE, Varint::encodeI64, Varint::decodeI64),
        )
    }

    @Test
    fun roundTripI128() {
        val min = BigInteger.valueOf(-2).pow(127)
        val max = BigInteger.valueOf(2).pow(127) - BigInteger.ONE

        assertEquals(
            Result.success(BigInteger.ZERO),
            roundTrip(BigInteger.ZERO, Varint::encodeI128, Varint::decodeI128),
        )
        assertEquals(
            Result.success(BigInteger.ONE),
            roundTrip(BigInteger.ONE, Varint::encodeI128, Varint::decodeI128),
        )
        assertEquals(Result.success(min), roundTrip(min, Varint::encodeI128, Varint::decodeI128))
        assertEquals(Result.success(max), roundTrip(max, Varint::encodeI128, Varint::decodeI128))
    }
}
