package rocks.dnaka91.mabo

import kotlin.test.Test
import kotlin.test.assertEquals

class FieldIdsTest {
    @Test
    fun ensureRawValue() {
        assertEquals(5 shl 3, FieldId(5, FieldEncoding.Varint).intoRaw())
    }

    @Test
    fun roundtrip() {
        val input = FieldId(5, FieldEncoding.LengthPrefixed)
        assertEquals(input, FieldId.fromRaw(input.intoRaw()))
    }
}

class VariantIdsTest {
    @Test
    fun ensureRawValue() {
        assertEquals(5, VariantId(5).value)
    }

    @Test
    fun roundtrip() {
        val input = VariantId(5)
        assertEquals(input, VariantId(input.value))
    }
}
