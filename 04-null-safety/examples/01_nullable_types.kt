/**
 * Kotlin の Null 許容型と非 Null 型の基本的な使い方
 */

// 非 Null 型のクラス
class User(
    val name: String,
    val age: Int
)

// Null 許容型のプロパティを持つクラス
class Profile(
    val user: User?,
    val email: String?,
    val phone: String?
)

// Null 許容型のパラメータを持つ関数
fun createUser(name: String?, age: Int?): User? {
    // Null チェック
    if (name == null || age == null) {
        return null
    }
    
    // 非 Null 型として扱える
    return User(name, age)
}

// Null 許容型の戻り値を持つ関数
fun findUser(id: Int): User? {
    return if (id > 0) {
        User("User$id", 20 + id)
    } else {
        null
    }
}

// Null 許容型のコレクション
fun processUsers(users: List<User?>): List<String> {
    return users.mapNotNull { user ->
        user?.name
    }
}

// Null 許容型の型パラメータ
class Container<T>(
    private val value: T?
) {
    fun getValue(): T? = value
    
    fun getValueOrDefault(default: T): T {
        return value ?: default
    }
}

// Null 許容型の拡張プロパティ
val String?.isNullOrEmpty: Boolean
    get() = this == null || this.isEmpty()

// Null 許容型の拡張関数
fun String?.toUpperCaseOrNull(): String? {
    return this?.toUpperCase()
}

fun main() {
    // 非 Null 型の使用
    val user = User("Taro", 25)
    println("ユーザー: ${user.name}, ${user.age}歳")
    
    // Null 許容型の使用
    val profile = Profile(user, "taro@example.com", null)
    println("プロフィール: ${profile.user?.name}, ${profile.email}, ${profile.phone}")
    
    // Null チェック
    val name: String? = "Hanako"
    val age: Int? = 30
    
    if (name != null && age != null) {
        val newUser = User(name, age)
        println("新しいユーザー: ${newUser.name}, ${newUser.age}歳")
    }
    
    // Null 許容型の関数呼び出し
    val user1 = createUser("Jiro", 35)
    val user2 = createUser(null, 40)
    
    println("ユーザー1: ${user1?.name}, ${user1?.age}歳")
    println("ユーザー2: ${user2?.name}, ${user2?.age}歳")
    
    // Null 許容型のコレクション処理
    val users = listOf(
        User("Alice", 20),
        null,
        User("Bob", 25),
        null,
        User("Charlie", 30)
    )
    
    val names = processUsers(users)
    println("ユーザー名: $names")
    
    // Null 許容型の型パラメータ
    val container = Container<String>("Hello")
    println("コンテナの値: ${container.getValue()}")
    println("デフォルト値: ${container.getValueOrDefault("Default")}")
    
    val nullContainer = Container<String>(null)
    println("Null コンテナの値: ${nullContainer.getValue()}")
    println("Null コンテナのデフォルト値: ${nullContainer.getValueOrDefault("Default")}")
    
    // Null 許容型の拡張プロパティと関数
    val text: String? = "Hello"
    val nullText: String? = null
    
    println("テキストは空か: ${text.isNullOrEmpty}")
    println("Null テキストは空か: ${nullText.isNullOrEmpty}")
    
    println("大文字: ${text.toUpperCaseOrNull()}")
    println("Null の大文字: ${nullText.toUpperCaseOrNull()}")
    
    // Null 許容型の型チェック
    val any: Any? = "Hello"
    val number: Any? = 42
    
    if (any is String) {
        println("文字列: $any")
    }
    
    if (number is Int) {
        println("数値: $number")
    }
    
    // Null 許容型の型キャスト
    val stringValue = any as? String
    val intValue = number as? Int
    
    println("文字列値: $stringValue")
    println("数値: $intValue")
    
    // Null 許容型の例外処理
    try {
        val nullUser: User? = null
        println(nullUser!!.name)  // NullPointerException が発生
    } catch (e: NullPointerException) {
        println("NullPointerException が発生しました: ${e.message}")
    }
} 