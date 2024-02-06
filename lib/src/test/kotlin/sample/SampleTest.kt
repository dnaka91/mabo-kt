@file:Suppress("NAME_SHADOWING")

package rocks.dnaka91.mabo.sample

import rocks.dnaka91.mabo.END_MARKER
import rocks.dnaka91.mabo.FieldEncoding
import rocks.dnaka91.mabo.FieldId
import rocks.dnaka91.mabo.VariantId
import rocks.dnaka91.mabo.buf.Decode
import rocks.dnaka91.mabo.buf.Decoder
import rocks.dnaka91.mabo.buf.Encode
import rocks.dnaka91.mabo.buf.Encoder
import rocks.dnaka91.mabo.buf.MissingFieldException
import rocks.dnaka91.mabo.buf.Size
import rocks.dnaka91.mabo.buf.Sizer
import rocks.dnaka91.mabo.buf.UnknownVariantException
import java.nio.ByteBuffer
import kotlin.test.Test
import kotlin.test.assertEquals

class SampleTest {
    private data class Sample(
        val age: Byte,
        val name: String,
        val gender: Boolean?,
        val aliases: List<String>,
    ) : Encode, Size {
        override fun encode(w: ByteBuffer) {
            Encoder.encodeField(w, FieldId(1, FieldEncoding.Fixed1)) { w ->
                Encoder.encodeU8(w, this.age)
            }
            Encoder.encodeField(w, FieldId(2, FieldEncoding.LengthPrefixed)) { w ->
                Encoder.encodeString(w, this.name)
            }
            Encoder.encodeFieldOption(w, FieldId(3, FieldEncoding.Fixed1), this.gender) { w, v ->
                Encoder.encodeBool(w, v)
            }
            Encoder.encodeField(w, FieldId(4, FieldEncoding.LengthPrefixed)) { w ->
                Encoder.encodeVec(w, this.aliases) { w, v ->
                    Encoder.encodeString(w, v)
                }
            }
            Encoder.encodeU32(w, END_MARKER)
        }

        override fun size(): Int =
            Sizer.sizeField(1) { Sizer.sizeU8(this.age) } +
                Sizer.sizeField(2) { Sizer.sizeString(this.name) } +
                Sizer.sizeFieldOption(3, this.gender) { v -> Sizer.sizeBool(v) } +
                Sizer.sizeField(4) { Sizer.sizeVec(this.aliases) { Sizer.sizeString(it) } } +
                Sizer.sizeU32(END_MARKER)

        companion object : Decode<Sample> {
            override fun decode(r: ByteBuffer): Result<Sample> =
                runCatching {
                    var age: Byte? = null
                    var name: String? = null
                    var gender: Boolean? = null
                    var aliases: List<String>? = null

                    while (true) {
                        val id = Decoder.decodeFieldId(r).getOrThrow()
                        when (id.value) {
                            1 -> age = Decoder.decodeU8(r).getOrThrow()
                            2 -> name = Decoder.decodeString(r).getOrThrow()
                            3 -> gender = Decoder.decodeBool(r).getOrThrow()
                            4 ->
                                aliases =
                                    Decoder.decodeVec(
                                        r,
                                    ) { r -> Decoder.decodeString(r) }.getOrThrow()
                            END_MARKER -> break
                        }
                    }

                    Sample(
                        age = age ?: throw MissingFieldException(1, "age"),
                        name = name ?: throw MissingFieldException(2, "name"),
                        gender = gender,
                        aliases = aliases ?: throw MissingFieldException(4, "aliases"),
                    )
                }
        }
    }

    @Test
    fun roundtrip() {
        val value =
            Sample(
                age = 25,
                name = "Bob",
                gender = true,
                aliases = listOf("MrB", "Bobby"),
            )

        val size = value.size()
        assertEquals(2 + 5 + 2 + (2 + 4 + 6) + 1, size)

        val buf = ByteBuffer.allocate(size)
        value.encode(buf)
        buf.flip()

        val decoded = Sample.decode(buf).getOrThrow()
        assertEquals(value, decoded)
    }

    private data object SampleUnit : Decode<SampleUnit>, Encode, Size {
        override fun decode(r: ByteBuffer): Result<SampleUnit> = Result.success(SampleUnit)

        override fun encode(w: ByteBuffer) {
        }

        override fun size(): Int = 0
    }

    private sealed class SampleEnum : Encode, Size {
        override fun encode(w: ByteBuffer) {
            when (this) {
                is One -> Encoder.encodeVariantId(w, VariantId(1))
                is Two -> Encoder.encodeVariantId(w, VariantId(2))
                is Three -> Encoder.encodeVariantId(w, VariantId(3))
            }
        }

        override fun size(): Int =
            when (this) {
                is One -> Sizer.sizeVariantId(1)
                is Two -> Sizer.sizeVariantId(2)
                is Three -> Sizer.sizeVariantId(3)
            }

        companion object : Decode<SampleEnum> {
            override fun decode(r: ByteBuffer): Result<SampleEnum> =
                runCatching {
                    val id = Decoder.decodeVariantId(r).getOrThrow()
                    when (id.value) {
                        1 -> One.decode(r).getOrThrow()
                        2 -> Two.decode(r).getOrThrow()
                        3 -> Three.decode(r).getOrThrow()
                        else -> throw UnknownVariantException(id.value)
                    }
                }
        }

        private data class One(
            val value: Int,
        ) : SampleEnum(), Encode, Size {
            override fun encode(w: ByteBuffer) {
                super.encode(w)
                Encoder.encodeField(w, FieldId(1, FieldEncoding.Varint)) { w ->
                    Encoder.encodeU32(w, this.value)
                }
                Encoder.encodeU32(w, END_MARKER)
            }

            override fun size(): Int =
                super.size() +
                    Sizer.sizeField(1) { Sizer.sizeU32(this.value) } +
                    Sizer.sizeU32(END_MARKER)

            companion object : Decode<One> {
                override fun decode(r: ByteBuffer): Result<One> =
                    runCatching {
                        var value: Int? = null

                        while (true) {
                            val id = Decoder.decodeFieldId(r).getOrThrow()
                            when (id.value) {
                                1 -> value = Decoder.decodeU32(r).getOrThrow()
                                END_MARKER -> break
                            }
                        }

                        One(
                            value ?: throw MissingFieldException(1, "value"),
                        )
                    }
            }
        }

        private data object Two : SampleEnum(), Decode<Two>, Encode, Size {
            override fun decode(r: ByteBuffer): Result<Two> = Result.success(Two)
        }

        private data class Three(
            val n0: String,
            val n1: String?,
        ) : SampleEnum(), Encode, Size {
            override fun encode(w: ByteBuffer) {
                super.encode(w)
                Encoder.encodeField(w, FieldId(1, FieldEncoding.LengthPrefixed)) { w ->
                    Encoder.encodeString(w, this.n0)
                }
                Encoder.encodeFieldOption(
                    w,
                    FieldId(2, FieldEncoding.LengthPrefixed),
                    this.n1,
                ) { w, v ->
                    Encoder.encodeString(w, v)
                }
                Encoder.encodeU32(w, END_MARKER)
            }

            override fun size(): Int =
                super.size() +
                    Sizer.sizeField(1) { Sizer.sizeString(this.n0) } +
                    Sizer.sizeFieldOption(1, this.n1) { v -> Sizer.sizeString(v) } +
                    Sizer.sizeU32(END_MARKER)

            companion object : Decode<Three> {
                override fun decode(r: ByteBuffer): Result<Three> =
                    runCatching {
                        var n0: String? = null
                        var n1: String? = null

                        while (true) {
                            val id = Decoder.decodeFieldId(r).getOrThrow()
                            when (id.value) {
                                1 -> n0 = Decoder.decodeString(r).getOrThrow()
                                2 -> n1 = Decoder.decodeString(r).getOrThrow()
                                END_MARKER -> break
                            }
                        }

                        Three(
                            n0 ?: throw MissingFieldException(1, null),
                            n1,
                        )
                    }
            }
        }
    }

    private data class SampleStructGen<T>(val inner: T) : Encode, Size
        where T : Encode, T : Size {
        override fun encode(w: ByteBuffer) {
            Encoder.encodeField(w, FieldId(1, FieldEncoding.LengthPrefixed)) { w ->
                this.inner.encode(w)
            }
            Encoder.encodeU32(w, END_MARKER)
        }

        override fun size(): Int = Sizer.sizeField(1) { this.inner.size() }

        companion object : Decode<SampleStructGen<*>> {
            override fun decode(r: ByteBuffer): Result<SampleStructGen<*>> {
                TODO("Not yet implemented")
            }
        }
    }
}
