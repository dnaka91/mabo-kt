package rocks.dnaka91.mabo

import org.junit.jupiter.api.assertThrows
import java.math.BigInteger
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class NonZeroTest {
    @Test
    fun numberValid() {
        assertEquals(1, NonZeroUByte(1.toByte()).get())
        assertEquals(1, NonZeroUShort(1.toShort()).get())
        assertEquals(1, NonZeroUInt(1).get())
        assertEquals(1, NonZeroULong(1L).get())
        assertEquals(1, NonZeroByte(1.toByte()).get())
        assertEquals(1, NonZeroShort(1.toShort()).get())
        assertEquals(1, NonZeroInt(1).get())
        assertEquals(1, NonZeroLong(1L).get())
        assertEquals(BigInteger.ONE, NonZeroBigInteger(BigInteger.ONE).get())
        assertEquals(1.0f, NonZeroFloat(1.0f).get())
        assertEquals(1.0, NonZeroDouble(1.0).get())
    }

    @Test
    fun numberInvalid() {
        assertThrows<IllegalArgumentException> { NonZeroUByte(0.toByte()) }
        assertThrows<IllegalArgumentException> { NonZeroUShort(0.toShort()) }
        assertThrows<IllegalArgumentException> { NonZeroUInt(0) }
        assertThrows<IllegalArgumentException> { NonZeroULong(0L) }
        assertThrows<IllegalArgumentException> { NonZeroByte(0.toByte()) }
        assertThrows<IllegalArgumentException> { NonZeroShort(0.toShort()) }
        assertThrows<IllegalArgumentException> { NonZeroInt(0) }
        assertThrows<IllegalArgumentException> { NonZeroLong(0L) }
        assertThrows<IllegalArgumentException> { NonZeroBigInteger(BigInteger.ZERO) }
        assertThrows<IllegalArgumentException> { NonZeroFloat(0.0f) }
        assertThrows<IllegalArgumentException> { NonZeroDouble(0.0) }
    }

    @Test
    fun stringValid() {
        assertEquals("test", NonZeroString("test").get())
    }

    @Test
    fun stringInvalid() {
        assertThrows<IllegalArgumentException> { NonZeroString("") }
    }

    @Test
    fun bytesValid() {
        assertContentEquals(byteArrayOf(1), NonZeroBytes(byteArrayOf(1)).get())
    }

    @Test
    fun bytesInvalid() {
        assertThrows<IllegalArgumentException> { NonZeroBytes(byteArrayOf()) }
    }

    @Test
    fun listValid() {
        assertEquals(listOf(1), NonZeroVec(listOf(1)).get())
    }

    @Test
    fun listInvalid() {
        assertThrows<IllegalArgumentException> { NonZeroVec(listOf<Int>()) }
    }

    @Test
    fun hashMapValid() {
        assertEquals(mapOf(1 to "one"), NonZeroHashMap(mapOf(1 to "one")).get())
    }

    @Test
    fun hashMapInvalid() {
        assertThrows<IllegalArgumentException> { NonZeroHashMap(mapOf<Int, String>()) }
    }

    @Test
    fun hashSetValid() {
        assertEquals(setOf(1), NonZeroHashSet(setOf(1)).get())
    }

    @Test
    fun hashSetInvalid() {
        assertThrows<IllegalArgumentException> { NonZeroHashSet(setOf<Int>()) }
    }
}
