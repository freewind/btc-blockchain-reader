package lipeng

import com.google.gson.GsonBuilder
import java.io.File

val dataDir = File("./btc-data/blocks")
val targetDir = File("./btc-data-parsed/blocks")

fun main(args: Array<String>) {
    val blockFiles = getBlockFiles(dataDir)
    println(blockFiles)
    for (blockFile in blockFiles) {
        val input = blockFile.inputStream()

        (0..1).map {
            val block = readBlock(input)
            println(block)
            println(toJson(block))

            verifyBlock(block)
        }
    }
}

fun verifyBlock(block: Block) {
    TODO
}

fun getBlockFiles(dataDir: File): List<File> {
    return dataDir.listFiles().filter { it.name.startsWith("blk") && it.name.endsWith(".dat") }
}


fun toJson(obj: Any): String {
    val gson = GsonBuilder().setPrettyPrinting().create()
    return gson.toJson(obj)
}

