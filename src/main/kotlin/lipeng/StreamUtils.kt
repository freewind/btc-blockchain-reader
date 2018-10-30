package lipeng

import org.apache.commons.io.IOUtils
import java.io.InputStream

enum class Endian {
    Little, Big
}

fun bytes(input: InputStream, count: Int): ByteArray {
    return IOUtils.readFully(input, count)
}

fun int8(input: InputStream, endian: Endian = Endian.Little): Int8 {
    val data = readFully(input, 8)
    if (endian == Endian.Little) data.reverse()
    return bytesToInt64(data)
}

fun int4(input: InputStream, endian: Endian = Endian.Little): Int4 {
    val data = readFully(input, 4)
    if (endian == Endian.Little) data.reverse()
    return bytesToInt32(data)
}

fun int2(input: InputStream, endian: Endian = Endian.Little): Int2 {
    val data = readFully(input, 2)
    if (endian == Endian.Little) data.reverse()
    return bytesToInt16(data)
}

fun hash32(input: InputStream): Hash32 {
    return bytes(input, 32).toHex()
}

fun readFully(input: InputStream, count: Int): ByteArray {
    return IOUtils.readFully(input, count)
}

fun varInt(input: InputStream): VarInt {
    val value = input.read()
    return when {
        value < 0xfd -> value.toLong()
        value == 0xfd -> int2(input).toLong()
        value == 0xfe -> int4(input).toLong()
        value == 0xff -> int8(input)
        else -> throw Error("invalid varint value: $value")
    }
}

fun bytesToInt64(bytes: ByteArray): Int8 {
    return (bytes[0].toLong().and(0xFF).shl(8 * 7) or
            bytes[1].toLong().and(0xFF).shl(8 * 6) or
            bytes[2].toLong().and(0xFF).shl(8 * 5) or
            bytes[3].toLong().and(0xFF).shl(8 * 4) or
            bytes[4].toLong().and(0xFF).shl(8 * 3) or
            bytes[5].toLong().and(0xFF).shl(8 * 2) or
            bytes[6].toLong().and(0xFF).shl(8 * 1) or
            bytes[7].toLong().and(0xFF))
}

fun bytesToInt32(bytes: ByteArray): Int4 {
    return (bytes[0].toInt().and(0xFF).shl(8 * 3) or
            bytes[1].toInt().and(0xFF).shl(8 * 2) or
            bytes[2].toInt().and(0xFF).shl(8 * 1) or
            bytes[3].toInt().and(0xFF))
}

fun bytesToInt16(bytes: ByteArray): Int2 {
    return (bytes[0].toInt().and(0xFF).shl(8) or
            bytes[1].toInt().and(0xFF)).toShort()
}