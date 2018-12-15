package lipeng

import com.github.kittinunf.fuel.Fuel
import java.io.File

val root = File("./blockchain_com_data")

fun main(args: Array<String>) {
    (99..1000).map { height ->
        val url = "https://blockchain.info/block-height/$height?format=json&key=048bfd09-ded9-44d9-beca-e1336238fd71"
        val response = Fuel.get(url).responseString()
        save(height, response.third.get())
    }

}

fun save(height: Int, content: String) {
    val file = File(root, "$height.json")
    file.writeText(content)
    println(file.absoluteFile)
}