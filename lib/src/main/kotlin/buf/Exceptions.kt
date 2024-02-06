package rocks.dnaka91.mabo.buf

public sealed class DecodeException : Throwable()

public class InsufficientDataException : DecodeException()

public class DecodeIntException : DecodeException()

public class NonUtf8Exception : DecodeException()

public data class MissingFieldException(val id: Int, val name: String?) : DecodeException()

public data class UnknownFieldEncodingException(val encoding: Int) : DecodeException()

public data class UnknownVariantException(val variant: Int) : DecodeException()

public class ZeroException : DecodeException()
