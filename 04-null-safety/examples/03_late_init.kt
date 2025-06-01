/**
 * Kotlin のレイト初期化の基本的な使い方
 */

// レイト初期化の使用例
class LateInitExample {
    // lateinit プロパティ
    lateinit var name: String
    lateinit var email: String
    
    // 初期化チェック
    fun isInitialized(): Boolean {
        return ::name.isInitialized && ::email.isInitialized
    }
    
    // 初期化
    fun initialize(name: String, email: String) {
        this.name = name
        this.email = email
    }
    
    // 初期化前のアクセス
    fun getName(): String {
        return if (::name.isInitialized) {
            name
        } else {
            "Not initialized"
        }
    }
}

// レイト初期化の制約
class LateInitConstraints {
    // プリミティブ型は lateinit にできない
    // lateinit var age: Int  // コンパイルエラー
    
    // Null 許容型は lateinit にできない
    // lateinit var nullableName: String?  // コンパイルエラー
    
    // val プロパティは lateinit にできない
    // lateinit val constant: String  // コンパイルエラー
}

// レイト初期化の実践的な使用例
class UserManager {
    private lateinit var database: Database
    private lateinit var cache: Cache
    
    // 初期化
    fun initialize(database: Database, cache: Cache) {
        this.database = database
        this.cache = cache
    }
    
    // 初期化チェック
    fun isReady(): Boolean {
        return ::database.isInitialized && ::cache.isInitialized
    }
    
    // データの取得
    fun getUser(id: Int): User? {
        if (!isReady()) {
            throw IllegalStateException("UserManager is not initialized")
        }
        
        return cache.getUser(id) ?: database.getUser(id)
    }
}

// モッククラス
class Database {
    fun getUser(id: Int): User? {
        return if (id > 0) {
            User("User$id", "user$id@example.com")
        } else {
            null
        }
    }
}

class Cache {
    private val users = mutableMapOf<Int, User>()
    
    fun getUser(id: Int): User? {
        return users[id]
    }
    
    fun setUser(id: Int, user: User) {
        users[id] = user
    }
}

data class User(
    val name: String,
    val email: String
)

// レイト初期化と例外処理
class ExceptionHandling {
    private lateinit var config: Config
    
    fun initialize(config: Config) {
        this.config = config
    }
    
    fun getConfig(): Config {
        if (!::config.isInitialized) {
            throw IllegalStateException("Config is not initialized")
        }
        return config
    }
}

data class Config(
    val host: String,
    val port: Int
)

fun main() {
    // 基本的なレイト初期化の使用
    val example = LateInitExample()
    
    println("初期化前: ${example.isInitialized()}")
    println("名前: ${example.getName()}")
    
    example.initialize("Taro", "taro@example.com")
    
    println("初期化後: ${example.isInitialized()}")
    println("名前: ${example.name}")
    println("メール: ${example.email}")
    
    // レイト初期化の例外
    try {
        val uninitialized = LateInitExample()
        println(uninitialized.name)  // UninitializedPropertyAccessException が発生
    } catch (e: UninitializedPropertyAccessException) {
        println("プロパティが初期化されていません: ${e.message}")
    }
    
    // 実践的な使用例
    val userManager = UserManager()
    
    println("初期化前: ${userManager.isReady()}")
    
    try {
        userManager.getUser(1)  // IllegalStateException が発生
    } catch (e: IllegalStateException) {
        println("エラー: ${e.message}")
    }
    
    val database = Database()
    val cache = Cache()
    
    userManager.initialize(database, cache)
    
    println("初期化後: ${userManager.isReady()}")
    
    val user = userManager.getUser(1)
    println("ユーザー: $user")
    
    // レイト初期化と例外処理
    val exceptionHandling = ExceptionHandling()
    
    try {
        exceptionHandling.getConfig()  // IllegalStateException が発生
    } catch (e: IllegalStateException) {
        println("エラー: ${e.message}")
    }
    
    exceptionHandling.initialize(Config("localhost", 8080))
    
    val config = exceptionHandling.getConfig()
    println("設定: ${config.host}:${config.port}")
    
    // レイト初期化の制約
    val constraints = LateInitConstraints()
    // 以下のコードはコンパイルエラーになります
    // constraints.age = 25
    // constraints.nullableName = "Taro"
    // constraints.constant = "Constant"
} 