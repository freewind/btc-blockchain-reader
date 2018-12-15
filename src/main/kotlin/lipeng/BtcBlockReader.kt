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

        (0..1).map { height ->
            val block = readBlock(input)
            val json = toJson(block)

//            verifyBlock(block)
            File(targetDir, "${height}.json").writeText(json)
        }
    }
}

fun verifyBlock(block: Block) {
//    TODO
}

fun getBlockFiles(dataDir: File): List<File> {
    return dataDir.listFiles().filter { it.name.matches("""blk\d+.dat""".toRegex()) }
}

fun toJson(obj: Any): String {
    val gson = GsonBuilder().setPrettyPrinting().create()
    return gson.toJson(obj)
}

