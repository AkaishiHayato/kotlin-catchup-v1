/**
 * Kotlin のオブジェクト宣言とコンパニオンオブジェクトの基本的な使い方
 */

// シングルトンオブジェクトの定義
object Logger {
    private var logLevel = "INFO"
    
    fun setLogLevel(level: String) {
        logLevel = level
    }
    
    fun log(message: String) {
        println("[$logLevel] $message")
    }
    
    fun error(message: String) {
        println("[ERROR] $message")
    }
    
    fun debug(message: String) {
        if (logLevel == "DEBUG") {
            println("[DEBUG] $message")
        }
    }
}

// コンパニオンオブジェクトを持つクラス
class User private constructor(
    val id: Int,
    val name: String
) {
    // コンパニオンオブジェクト
    companion object {
        // ファクトリーメソッド
        fun create(id: Int, name: String): User {
            return User(id, name)
        }
        
        // 定数
        const val MAX_ID = 1000
        
        // プライベートコンストラクタを使用するファクトリーメソッド
        fun createWithValidation(id: Int, name: String): User? {
            return if (id in 1..MAX_ID && name.isNotEmpty()) {
                User(id, name)
            } else {
                null
            }
        }
    }
}

// 名前付きコンパニオンオブジェクト
class Configuration {
    companion object Factory {
        fun create(): Configuration {
            return Configuration()
        }
    }
}

// インターフェースを実装するコンパニオンオブジェクト
interface Factory<T> {
    fun create(): T
}

class Product {
    companion object : Factory<Product> {
        override fun create(): Product {
            return Product()
        }
    }
}

// オブジェクト式（無名クラス）
interface ClickListener {
    fun onClick()
}

class Button {
    private var listener: ClickListener? = null
    
    fun setClickListener(listener: ClickListener) {
        this.listener = listener
    }
    
    fun click() {
        listener?.onClick()
    }
}

fun main() {
    // シングルトンオブジェクトの使用
    Logger.log("アプリケーションが起動しました")
    Logger.setLogLevel("DEBUG")
    Logger.debug("デバッグ情報")
    Logger.error("エラーが発生しました")
    
    // コンパニオンオブジェクトの使用
    val user1 = User.create(1, "Taro")
    println("ユーザー: ${user1.name}")
    
    // バリデーション付きのファクトリーメソッド
    val user2 = User.createWithValidation(2000, "Jiro")
    println("無効なIDのユーザー: $user2")
    
    val user3 = User.createWithValidation(100, "Hanako")
    println("有効なユーザー: ${user3?.name}")
    
    // 名前付きコンパニオンオブジェクトの使用
    val config = Configuration.Factory.create()
    
    // インターフェースを実装するコンパニオンオブジェクトの使用
    val product = Product.create()
    
    // オブジェクト式の使用
    val button = Button()
    button.setClickListener(object : ClickListener {
        override fun onClick() {
            println("ボタンがクリックされました")
        }
    })
    button.click()
    
    // オブジェクト式の簡略化
    val button2 = Button()
    button2.setClickListener(object : ClickListener {
        override fun onClick() = println("2番目のボタンがクリックされました")
    })
    button2.click()
    
    // シングルトンの一意性の確認
    val logger1 = Logger
    val logger2 = Logger
    println("同じインスタンス: ${logger1 === logger2}")
    
    // コンパニオンオブジェクトの定数の使用
    println("最大ID: ${User.MAX_ID}")
    
    // コンパニオンオブジェクトの拡張関数
    fun User.Companion.createAdmin(): User {
        return create(0, "Admin")
    }
    
    val admin = User.createAdmin()
    println("管理者: ${admin.name}")
} 