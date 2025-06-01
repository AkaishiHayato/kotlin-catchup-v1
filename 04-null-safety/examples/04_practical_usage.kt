/**
 * Kotlin の Null 安全性の実践的な使用例
 */

// データベースアクセスのシミュレーション
class Database {
    private val users = mutableMapOf<Int, User>()
    private val orders = mutableMapOf<Int, Order>()
    
    fun getUser(id: Int): User? {
        return users[id]
    }
    
    fun getOrder(id: Int): Order? {
        return orders[id]
    }
    
    fun addUser(user: User) {
        users[user.id] = user
    }
    
    fun addOrder(order: Order) {
        orders[order.id] = order
    }
}

// データクラス
data class User(
    val id: Int,
    val name: String,
    val email: String?,
    val address: Address?
)

data class Address(
    val street: String,
    val city: String,
    val country: String
)

data class Order(
    val id: Int,
    val userId: Int,
    val items: List<OrderItem>,
    val status: OrderStatus
)

data class OrderItem(
    val productId: Int,
    val quantity: Int,
    val price: Double
)

enum class OrderStatus {
    PENDING,
    PROCESSING,
    SHIPPED,
    DELIVERED,
    CANCELLED
}

// Null 安全性の設計パターン
class UserService(private val database: Database) {
    // Null 安全性のベストプラクティス
    fun getUserInfo(userId: Int): UserInfo? {
        val user = database.getUser(userId) ?: return null
        
        return UserInfo(
            name = user.name,
            email = user.email ?: "No email provided",
            address = user.address?.let { address ->
                "${address.street}, ${address.city}, ${address.country}"
            } ?: "No address provided"
        )
    }
    
    // 例外処理との組み合わせ
    fun updateUserAddress(userId: Int, address: Address): Result<Unit> {
        val user = database.getUser(userId) ?: return Result.failure(
            IllegalArgumentException("User not found: $userId")
        )
        
        val updatedUser = user.copy(address = address)
        database.addUser(updatedUser)
        
        return Result.success(Unit)
    }
    
    // コレクションでの Null 安全性
    fun getActiveOrders(userId: Int): List<OrderInfo> {
        val user = database.getUser(userId) ?: return emptyList()
        
        return database.getOrder(userId)?.let { order ->
            if (order.status != OrderStatus.CANCELLED) {
                listOf(OrderInfo(
                    orderId = order.id,
                    totalAmount = order.items.sumOf { it.price * it.quantity },
                    status = order.status
                ))
            } else {
                emptyList()
            }
        } ?: emptyList()
    }
}

// データ転送オブジェクト
data class UserInfo(
    val name: String,
    val email: String,
    val address: String
)

data class OrderInfo(
    val orderId: Int,
    val totalAmount: Double,
    val status: OrderStatus
)

// Null 安全性の実践的な使用例
class PracticalExamples {
    // 設定の読み込み
    data class AppConfig(
        val database: DatabaseConfig?,
        val logging: LoggingConfig?
    )
    
    data class DatabaseConfig(
        val host: String,
        val port: Int,
        val username: String?,
        val password: String?
    )
    
    data class LoggingConfig(
        val level: String,
        val file: String?
    )
    
    fun loadConfig(): AppConfig {
        return AppConfig(
            database = DatabaseConfig(
                host = "localhost",
                port = 3306,
                username = "admin",
                password = null
            ),
            logging = LoggingConfig(
                level = "INFO",
                file = "app.log"
            )
        )
    }
    
    // API レスポンスの処理
    data class ApiResponse<T>(
        val data: T?,
        val error: String?,
        val status: Int
    )
    
    fun <T> processResponse(response: ApiResponse<T>): Result<T> {
        return when {
            response.status == 200 && response.data != null -> {
                Result.success(response.data)
            }
            response.error != null -> {
                Result.failure(RuntimeException(response.error))
            }
            else -> {
                Result.failure(RuntimeException("Unknown error"))
            }
        }
    }
    
    // オプショナルな依存関係
    class Service(
        private val database: Database,
        private val cache: Cache? = null
    ) {
        fun getData(id: Int): String {
            return cache?.get(id) ?: database.getUser(id)?.name ?: "Not found"
        }
    }
    
    class Cache {
        private val data = mutableMapOf<Int, String>()
        
        fun get(id: Int): String? {
            return data[id]
        }
        
        fun set(id: Int, value: String) {
            data[id] = value
        }
    }
}

fun main() {
    // データベースの初期化
    val database = Database()
    
    // ユーザーの追加
    val user = User(
        id = 1,
        name = "Taro",
        email = "taro@example.com",
        address = Address("123 Main St", "Tokyo", "Japan")
    )
    database.addUser(user)
    
    // 注文の追加
    val order = Order(
        id = 1,
        userId = 1,
        items = listOf(
            OrderItem(1, 2, 1000.0),
            OrderItem(2, 1, 2000.0)
        ),
        status = OrderStatus.PROCESSING
    )
    database.addOrder(order)
    
    // ユーザーサービスの使用
    val userService = UserService(database)
    
    // ユーザー情報の取得
    val userInfo = userService.getUserInfo(1)
    println("ユーザー情報: $userInfo")
    
    // 存在しないユーザーの情報取得
    val nonExistentUserInfo = userService.getUserInfo(2)
    println("存在しないユーザー: $nonExistentUserInfo")
    
    // アドレスの更新
    val newAddress = Address("456 Oak St", "Osaka", "Japan")
    val updateResult = userService.updateUserAddress(1, newAddress)
    println("アドレス更新結果: $updateResult")
    
    // アクティブな注文の取得
    val activeOrders = userService.getActiveOrders(1)
    println("アクティブな注文: $activeOrders")
    
    // 実践的な使用例
    val examples = PracticalExamples()
    
    // 設定の読み込み
    val config = examples.loadConfig()
    println("データベース設定: ${config.database}")
    println("ロギング設定: ${config.logging}")
    
    // API レスポンスの処理
    val successResponse = PracticalExamples.ApiResponse(
        data = "Success",
        error = null,
        status = 200
    )
    
    val errorResponse = PracticalExamples.ApiResponse<String>(
        data = null,
        error = "Not found",
        status = 404
    )
    
    println("成功レスポンス: ${examples.processResponse(successResponse)}")
    println("エラーレスポンス: ${examples.processResponse(errorResponse)}")
    
    // オプショナルな依存関係
    val cache = PracticalExamples.Cache()
    cache.set(1, "Cached data")
    
    val serviceWithCache = PracticalExamples.Service(database, cache)
    val serviceWithoutCache = PracticalExamples.Service(database)
    
    println("キャッシュあり: ${serviceWithCache.getData(1)}")
    println("キャッシュなし: ${serviceWithoutCache.getData(1)}")
} 