package lipeng

import org.apache.commons.codec.binary.Hex
import java.io.DataInputStream
import java.io.InputStream

typealias Int2 = Short
typealias Int4 = Int
typealias Int8 = Long
typealias VarInt = Long
typealias Hash32 = String

data class BlockHeader(
        val version: Int4,
        val previousBlockHeaderHash: Hash32,
        val merkleRootHash: Hash32,
        val time: Int4,
        val nBits: Int4,
        val nonce: Int4
)

data class Transaction(
        val versionNumber: Int4,
        val inCount: VarInt,
        val inputs: List<TxInput>,
        val outCount: VarInt,
        val outputs: List<TxOutput>,
        val lockTime: Int4
)

data class TxInput(
        val prevTxHash: Hash32,
        val prevTxOutIndex: Int4,
        val txInScriptLength: VarInt,
        val txInScriptSig: String,
        val seqNumber: Int4
)

data class TxOutput(
        val value: Int8,
        val txOutScriptLength: VarInt,
        val txOutScript: String
)


data class Block(
        val magicNumber: Int4,
        val size: Int4,
        val header: BlockHeader,
        val transactionCount: VarInt,
        val transactions: List<Transaction>
)

fun readBlock(input: InputStream): Block {
    var transactionCount: VarInt = 0
    return Block(
            magicNumber = int4(input),
            size = int4(input),
            header = BlockHeader(
                    version = int4(input),
                    previousBlockHeaderHash = hash32(input),
                    merkleRootHash = hash32(input),
                    time = int4(input),
                    nBits = int4(input),
                    nonce = int4(input)
            ),
            transactionCount = run { transactionCount = varInt(input); transactionCount },
            transactions = (0 until transactionCount).map {
                var inCount: VarInt = 0
                var outCount: VarInt = 0
                Transaction(
                        versionNumber = int4(input),
                        inCount = run { inCount = varInt(input); inCount },
                        inputs = (0 until inCount).map {
                            var txInScriptLength: VarInt = 0
                            TxInput(
                                    prevTxHash = hash32(input),
                                    prevTxOutIndex = int4(input),
                                    txInScriptLength = run { txInScriptLength = varInt(input); txInScriptLength },
                                    txInScriptSig = bytes(input, txInScriptLength.toInt()).toHex(),
                                    seqNumber = int4(input)
                            )
                        },
                        outCount = run { outCount = varInt(input); outCount },
                        outputs = (0 until outCount).map {
                            var txOutScriptLength: VarInt = 0
                            TxOutput(
                                    value = int8(input),
                                    txOutScriptLength = run { txOutScriptLength = varInt(input);txOutScriptLength },
                                    txOutScript = bytes(input, txOutScriptLength.toInt()).toHex()
                            )
                        },
                        lockTime = int4(input)
                )
            }
    )
}

fun ByteArray.toHex(): String {
    return Hex.encodeHexString(this)
}
