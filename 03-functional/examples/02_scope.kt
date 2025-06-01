/**
 * Kotlin のスコープ関数の基本的な使い方
 */

// データクラスの定義
data class Person(
    var name: String,
    var age: Int,
    var city: String
)

// スコープ関数の使用例
class UserManager {
    private val users = mutableListOf<Person>()
    
    fun addUser(name: String, age: Int, city: String) {
        // let の使用例
        val user = Person(name, age, city).let {
            // 初期化時の追加の処理
            if (it.age < 0) it.age = 0
            if (it.name.isBlank()) it.name = "Unknown"
            it
        }
        users.add(user)
    }
    
    fun updateUser(index: Int, block: Person.() -> Unit) {
        // run の使用例
        users.getOrNull(index)?.run {
            block()
            println("ユーザーを更新: $this")
        }
    }
    
    fun processUser(index: Int) {
        // with の使用例
        val user = users.getOrNull(index)
        with(user) {
            if (this != null) {
                println("ユーザー情報の処理:")
                println("名前: $name")
                println("年齢: $age")
                println("都市: $city")
            }
        }
    }
    
    fun createUser(name: String, age: Int, city: String) {
        // apply の使用例
        val user = Person("", 0, "").apply {
            this.name = name
            this.age = age
            this.city = city
        }
        users.add(user)
    }
    
    fun logUser(index: Int) {
        // also の使用例
        users.getOrNull(index)?.also {
            println("ユーザーをログに記録: $it")
        }
    }
}

// スコープ関数の組み合わせ
class Configuration {
    private var host: String = ""
    private var port: Int = 0
    private var timeout: Int = 0
    
    fun configure(block: Configuration.() -> Unit): Configuration {
        block()
        return this
    }
    
    override fun toString(): String {
        return "Configuration(host='$host', port=$port, timeout=$timeout)"
    }
}

fun main() {
    // UserManager の使用例
    val manager = UserManager()
    
    // let の使用
    manager.addUser("Taro", 25, "Tokyo")
    manager.addUser("", -1, "Osaka")  // 無効なデータ
    
    // run の使用
    manager.updateUser(0) {
        age += 1
        city = "Yokohama"
    }
    
    // with の使用
    manager.processUser(0)
    
    // apply の使用
    manager.createUser("Hanako", 30, "Kyoto")
    
    // also の使用
    manager.logUser(1)
    
    // スコープ関数の組み合わせ
    val config = Configuration().apply {
        host = "localhost"
        port = 8080
        timeout = 5000
    }.also {
        println("設定を適用: $it")
    }
    
    // スコープ関数の使い分け
    val person = Person("Jiro", 35, "Fukuoka")
    
    // let: 非nullの場合の処理
    person.let {
        println("let: ${it.name}は${it.age}歳です")
    }
    
    // run: オブジェクトの初期化と結果の計算
    val description = person.run {
        "run: $nameは$age歳で$cityに住んでいます"
    }
    println(description)
    
    // with: 複数のプロパティへのアクセス
    with(person) {
        println("with: 名前=$name, 年齢=$age, 都市=$city")
    }
    
    // apply: オブジェクトの設定
    val newPerson = Person("", 0, "").apply {
        name = "Saburo"
        age = 40
        city = "Sapporo"
    }
    println("apply: $newPerson")
    
    // also: 追加の処理
    person.also {
        println("also: ユーザーをログに記録: $it")
    }
    
    // スコープ関数のチェーン
    val result = person
        .let { it.copy(age = it.age + 1) }
        .apply { city = "Nagoya" }
        .run { "更新後: $nameは$age歳で$cityに住んでいます" }
    println(result)
    
    // null安全性との組み合わせ
    val nullablePerson: Person? = null
    
    nullablePerson?.let {
        println("非nullの場合の処理: $it")
    } ?: println("nullの場合の処理")
    
    // スコープ関数と例外処理
    try {
        person.apply {
            age = -1  // 無効な値
            if (age < 0) throw IllegalArgumentException("年齢は0以上である必要があります")
        }
    } catch (e: IllegalArgumentException) {
        println("エラー: ${e.message}")
    }
} 