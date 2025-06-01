/**
 * Kotlin の安全呼び出し演算子の基本的な使い方
 */

// データクラスの定義
data class Address(
    val street: String,
    val city: String,
    val country: String
)

data class Person(
    val name: String,
    val age: Int,
    val address: Address?
)

// 安全呼び出し演算子の使用例
class SafeCallExamples {
    // 安全呼び出し（?.）
    fun getCity(person: Person?): String? {
        return person?.address?.city
    }
    
    // Elvis 演算子（?:）
    fun getCityOrDefault(person: Person?): String {
        return person?.address?.city ?: "Unknown"
    }
    
    // 非 Null アサーション（!!）
    fun getCityUnsafe(person: Person?): String {
        return person!!.address!!.city
    }
    
    // 安全キャスト（as?）
    fun getAddressAsString(obj: Any?): String? {
        return (obj as? Person)?.address?.toString()
    }
    
    // 複数の安全呼び出しの組み合わせ
    fun getFullAddress(person: Person?): String? {
        return person?.address?.let { address ->
            "${address.street}, ${address.city}, ${address.country}"
        }
    }
    
    // 安全呼び出しと拡張関数
    fun String?.toUpperCaseSafe(): String {
        return this?.toUpperCase() ?: ""
    }
    
    // 安全呼び出しとコレクション
    fun getCities(persons: List<Person?>): List<String> {
        return persons.mapNotNull { person ->
            person?.address?.city
        }
    }
}

// 安全呼び出しの実践的な使用例
class PracticalExamples {
    // データベースアクセスのシミュレーション
    fun findPerson(id: Int): Person? {
        return if (id > 0) {
            Person(
                "User$id",
                20 + id,
                Address("Street $id", "City $id", "Country $id")
            )
        } else {
            null
        }
    }
    
    // API レスポンスの処理
    data class ApiResponse(
        val data: Map<String, Any?>?,
        val error: String?
    )
    
    fun processResponse(response: ApiResponse?): String {
        return response?.data?.get("name") as? String
            ?: response?.error
            ?: "Unknown error"
    }
    
    // 設定の読み込み
    data class Config(
        val database: DatabaseConfig?,
        val server: ServerConfig?
    )
    
    data class DatabaseConfig(
        val host: String,
        val port: Int
    )
    
    data class ServerConfig(
        val url: String,
        val timeout: Int
    )
    
    fun getDatabaseUrl(config: Config?): String {
        return config?.database?.let { db ->
            "jdbc:mysql://${db.host}:${db.port}"
        } ?: "jdbc:mysql://localhost:3306"
    }
}

fun main() {
    val safeCallExamples = SafeCallExamples()
    val practicalExamples = PracticalExamples()
    
    // 安全呼び出しの使用
    val person = Person(
        "Taro",
        25,
        Address("123 Main St", "Tokyo", "Japan")
    )
    
    val nullPerson: Person? = null
    
    println("都市（安全呼び出し）: ${safeCallExamples.getCity(person)}")
    println("都市（Null の場合）: ${safeCallExamples.getCity(nullPerson)}")
    
    // Elvis 演算子の使用
    println("都市（デフォルト値）: ${safeCallExamples.getCityOrDefault(person)}")
    println("都市（Null の場合のデフォルト値）: ${safeCallExamples.getCityOrDefault(nullPerson)}")
    
    // 非 Null アサーションの使用
    try {
        println("都市（非安全）: ${safeCallExamples.getCityUnsafe(person)}")
        println("都市（Null の場合の非安全）: ${safeCallExamples.getCityUnsafe(nullPerson)}")
    } catch (e: NullPointerException) {
        println("NullPointerException が発生しました: ${e.message}")
    }
    
    // 安全キャストの使用
    println("アドレス（安全キャスト）: ${safeCallExamples.getAddressAsString(person)}")
    println("アドレス（無効な型）: ${safeCallExamples.getAddressAsString("Not a Person")}")
    
    // 複数の安全呼び出しの組み合わせ
    println("完全なアドレス: ${safeCallExamples.getFullAddress(person)}")
    println("Null の場合の完全なアドレス: ${safeCallExamples.getFullAddress(nullPerson)}")
    
    // 安全呼び出しと拡張関数
    val text: String? = "Hello"
    val nullText: String? = null
    
    println("大文字（安全）: ${safeCallExamples.toUpperCaseSafe(text)}")
    println("大文字（Null の場合）: ${safeCallExamples.toUpperCaseSafe(nullText)}")
    
    // 安全呼び出しとコレクション
    val persons = listOf(
        person,
        null,
        Person("Hanako", 30, Address("456 Oak St", "Osaka", "Japan")),
        null
    )
    
    println("都市のリスト: ${safeCallExamples.getCities(persons)}")
    
    // 実践的な使用例
    val foundPerson = practicalExamples.findPerson(1)
    println("見つかった人物: ${foundPerson?.name}, ${foundPerson?.address?.city}")
    
    val apiResponse = PracticalExamples.ApiResponse(
        mapOf("name" to "John", "age" to 30),
        null
    )
    
    println("API レスポンスの処理: ${practicalExamples.processResponse(apiResponse)}")
    
    val config = PracticalExamples.Config(
        PracticalExamples.DatabaseConfig("localhost", 3306),
        PracticalExamples.ServerConfig("http://example.com", 5000)
    )
    
    println("データベース URL: ${practicalExamples.getDatabaseUrl(config)}")
    println("Null 設定のデータベース URL: ${practicalExamples.getDatabaseUrl(null)}")
} 