//package lipeng.db
//
//import com.sleepycat.je.*
//import com.sleepycat.compat.DbCompat.openDatabase
//import com.sleepycat.je.Database
//import java.io.File
//
//fun openDb(dbDir: File, dbName: String): Database? {
//    val env = Environment(dbDir, readOnlyEnv())
//    val db = env.openDatabase(null, dbName, readOnlyDb())
//    return db
//}
//
//fun readOnlyDb(): DatabaseConfig = DatabaseConfig().apply {
//    allowCreate = false
//    readOnly = true
//    transactional = false
//}
//
//fun readOnlyEnv(): EnvironmentConfig = EnvironmentConfig().apply {
//    this.allowCreate = false
//    this.readOnly = true
//    this.transactional = false
//}
//
//fun Database.read(key: String): String? {
//    return this.readBinary(key)?.toString()
//}
//
//fun Database.readBinary(key: String): ByteArray? {
//    val value = DatabaseEntry()
//    this.get(null, key.asEntry(), value, LockMode.DEFAULT)
//    return value.data
//}
//
//fun Database.delete(key: String) {
//    this.delete(null, key.asEntry())
//}
//
//fun String.asEntry(): DatabaseEntry {
//    return DatabaseEntry(this.toByteArray())
//}
//
//fun ByteArray.asEntry(): DatabaseEntry {
//    return DatabaseEntry(this)
//}