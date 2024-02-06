package rocks.dnaka91.mabo

/**
 * Identifier for a single struct or enum variant field.
 *
 * This type contains the actual identifier, plus additional information that is encoded together
 * with it. It allows for convenient en- and decoding of the information.
 *
 * @property value The real decoded field identifier.
 * @property encoding Encoding information for field skipping.
 * @constructor Create a new instance of a field identifier.
 */
@JvmRecord
public data class FieldId(
    val value: Int,
    val encoding: FieldEncoding,
) {
    /**
     * Convert the field identifier into a raw [UInt], which contains all its information.
     */
    public fun intoRaw(): Int = this.value shl 3 or this.encoding.value.toInt()

    public companion object {
        /**
         * Convert from a raw [UInt] into the field identifier.
         *
         * This returns `null` if the raw value contains an unknown field encoding.
         */
        @JvmStatic
        public fun fromRaw(value: Int): FieldId? =
            FieldEncoding.fromRaw(value)?.let { encoding ->
                FieldId(
                    value = value shr 3,
                    encoding = encoding,
                )
            }
    }
}

/**
 * Minimum detail about how a field is encoded, which allows to skip over a field if it's unknown.
 */
public enum class FieldEncoding(internal val value: UInt) {
    /**
     * Variable-length integer.
     */
    Varint(0u),

    /**
     * Arbitrary content prefixed with its byte length.
     */
    LengthPrefixed(1u),

    /**
     * 1-byte fixed width data.
     */
    Fixed1(2u),

    /**
     * 4-byte fixed width data.
     */
    Fixed4(3u),

    /**
     * 8-byte fixed width data.
     */
    Fixed8(4u),
    ;

    public companion object {
        /**
         * Try to convert the raw field identifier (which carries the encoding information) into a
         * known field encoding.
         */
        @JvmStatic
        public fun fromRaw(value: Int): FieldEncoding? =
            when (value.toUInt() and 0b111u) {
                Varint.value -> Varint
                LengthPrefixed.value -> LengthPrefixed
                Fixed1.value -> Fixed1
                Fixed4.value -> Fixed4
                Fixed8.value -> Fixed8
                else -> null
            }
    }
}

/**
 * Identifier for a single enum variant.
 *
 * Currently, this is identical to the raw value it contains, but might be extended to encode
 * additional information like the [FieldId] in the future.
 *
 * @property value The real decoded variant identifier.
 * @constructor Create a new instance of a variant identifier.
 */
@JvmRecord
public data class VariantId(val value: Int)
