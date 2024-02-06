package rocks.dnaka91.mabo.buf

import java.math.BigInteger
import kotlin.test.Test
import kotlin.test.assertEquals

class SizeTest {
    @Test
    fun sizeFixed() {
        assertEquals(1, Sizer.sizeBool(false))
        assertEquals(1, Sizer.sizeBool(true))
        assertEquals(1, Sizer.sizeU8(UByte.MIN_VALUE.toByte()))
        assertEquals(1, Sizer.sizeU8(UByte.MAX_VALUE.toByte()))
        assertEquals(1, Sizer.sizeI8(Byte.MIN_VALUE))
        assertEquals(4, Sizer.sizeF32(0.0f))
        assertEquals(4, Sizer.sizeF32(Float.MIN_VALUE))
        assertEquals(4, Sizer.sizeF32(Float.MAX_VALUE))
        assertEquals(8, Sizer.sizeF64(0.0))
        assertEquals(8, Sizer.sizeF64(Double.MIN_VALUE))
        assertEquals(8, Sizer.sizeF64(Double.MAX_VALUE))
    }

    @Test
    fun sizeU16() {
        assertEquals(1, Sizer.sizeU16(0u.toShort()))
        assertEquals(3, Sizer.sizeU16(UShort.MAX_VALUE.toShort()))
    }

    @Test
    fun sizeI16() {
        assertEquals(1, Sizer.sizeI16(0.toShort()))
        assertEquals(3, Sizer.sizeI16(Short.MAX_VALUE))
        assertEquals(3, Sizer.sizeI16(Short.MIN_VALUE))
    }

    @Test
    fun sizeU32() {
        assertEquals(1, Sizer.sizeU32(0u.toInt()))
        assertEquals(3, Sizer.sizeU32(UShort.MAX_VALUE.toInt()))
        assertEquals(5, Sizer.sizeU32(UInt.MAX_VALUE.toInt()))
    }

    @Test
    fun sizeI32() {
        assertEquals(1, Sizer.sizeI32(0))
        assertEquals(3, Sizer.sizeI32(Short.MAX_VALUE.toInt()))
        assertEquals(3, Sizer.sizeI32(Short.MIN_VALUE.toInt()))
        assertEquals(5, Sizer.sizeI32(Int.MAX_VALUE))
        assertEquals(5, Sizer.sizeI32(Int.MIN_VALUE))
    }

    @Test
    fun sizeU64() {
        assertEquals(1, Sizer.sizeU64(0u.toLong()))
        assertEquals(3, Sizer.sizeU64(UShort.MAX_VALUE.toLong()))
        assertEquals(5, Sizer.sizeU64(UInt.MAX_VALUE.toLong()))
        assertEquals(10, Sizer.sizeU64(ULong.MAX_VALUE.toLong()))
    }

    @Test
    fun sizeI64() {
        assertEquals(1, Sizer.sizeI64(0u.toLong()))
        assertEquals(3, Sizer.sizeI64(Short.MAX_VALUE.toLong()))
        assertEquals(3, Sizer.sizeI64(Short.MIN_VALUE.toLong()))
        assertEquals(5, Sizer.sizeI64(Int.MAX_VALUE.toLong()))
        assertEquals(5, Sizer.sizeI64(Int.MIN_VALUE.toLong()))
        assertEquals(10, Sizer.sizeI64(Long.MAX_VALUE))
        assertEquals(10, Sizer.sizeI64(Long.MIN_VALUE))
    }

    @Test
    fun sizeU128() {
        assertEquals(1, Sizer.sizeU128(BigInteger.ZERO))
        assertEquals(3, Sizer.sizeU128(BigInteger.valueOf(2).pow(16) - BigInteger.ONE))
        assertEquals(5, Sizer.sizeU128(BigInteger.valueOf(2).pow(32) - BigInteger.ONE))
        assertEquals(10, Sizer.sizeU128(BigInteger.valueOf(2).pow(64) - BigInteger.ONE))
        assertEquals(19, Sizer.sizeU128(BigInteger.valueOf(2).pow(128) - BigInteger.ONE))
    }
}
