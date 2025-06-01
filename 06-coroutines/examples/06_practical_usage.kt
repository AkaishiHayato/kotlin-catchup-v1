/**
 * Kotlin のコルーチンの実践的な使用例
 */

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis

// ネットワークリクエスト
class NetworkRequests {
    // 基本的なリクエスト
    fun demonstrateBasicRequest() = runBlocking {
        val result = withContext(Dispatchers.IO) {
            fetchData("https://api.example.com/data")
        }
        println("データ: $result")
    }
    
    // 並行リクエスト
    fun demonstrateConcurrentRequests() = runBlocking {
        val time = measureTimeMillis {
            val results = listOf(
                async { fetchData("https://api.example.com/data1") },
                async { fetchData("https://api.example.com/data2") },
                async { fetchData("https://api.example.com/data3") }
            ).awaitAll()
            
            println("結果: $results")
        }
        
        println("実行時間: ${time}ms")
    }
    
    // リクエストのリトライ
    fun demonstrateRequestRetry() = runBlocking {
        var attempt = 0
        val maxAttempts = 3
        
        while (attempt < maxAttempts) {
            try {
                val result = withTimeout(5000) {
                    fetchData("https://api.example.com/data")
                }
                println("成功: $result")
                break
            } catch (e: Exception) {
                attempt++
                println("エラー発生 (試行 $attempt/$maxAttempts): ${e.message}")
                delay(1000)
            }
        }
    }
    
    private suspend fun fetchData(url: String): String {
        delay(1000) // ネットワークリクエストのシミュレーション
        return "データ from $url"
    }
}

// データベース操作
class DatabaseOperations {
    // 基本的な操作
    fun demonstrateBasicOperations() = runBlocking {
        withContext(Dispatchers.IO) {
            val user = User(1, "Taro", "taro@example.com")
            saveUser(user)
            val retrieved = getUser(1)
            println("ユーザー: $retrieved")
        }
    }
    
    // バッチ処理
    fun demonstrateBatchProcessing() = runBlocking {
        val users = listOf(
            User(1, "Taro", "taro@example.com"),
            User(2, "Hanako", "hanako@example.com"),
            User(3, "Jiro", "jiro@example.com")
        )
        
        withContext(Dispatchers.IO) {
            users.forEach { user ->
                saveUser(user)
            }
        }
    }
    
    // トランザクション
    fun demonstrateTransaction() = runBlocking {
        withContext(Dispatchers.IO) {
            try {
                beginTransaction()
                saveUser(User(1, "Taro", "taro@example.com"))
                saveUser(User(2, "Hanako", "hanako@example.com"))
                commitTransaction()
            } catch (e: Exception) {
                rollbackTransaction()
                println("トランザクション失敗: ${e.message}")
            }
        }
    }
    
    data class User(
        val id: Int,
        val name: String,
        val email: String
    )
    
    private suspend fun saveUser(user: User) {
        delay(100) // データベース操作のシミュレーション
    }
    
    private suspend fun getUser(id: Int): User? {
        delay(100) // データベース操作のシミュレーション
        return User(id, "Taro", "taro@example.com")
    }
    
    private suspend fun beginTransaction() {
        delay(100)
    }
    
    private suspend fun commitTransaction() {
        delay(100)
    }
    
    private suspend fun rollbackTransaction() {
        delay(100)
    }
}

// UI の更新
class UIUpdates {
    // 基本的な更新
    fun demonstrateBasicUpdate() = runBlocking {
        val scope = CoroutineScope(Dispatchers.Main)
        
        scope.launch {
            val data = withContext(Dispatchers.IO) {
                fetchData()
            }
            updateUI(data)
        }
    }
    
    // 定期的な更新
    fun demonstratePeriodicUpdate() = runBlocking {
        val scope = CoroutineScope(Dispatchers.Main)
        
        scope.launch {
            while (isActive) {
                val data = withContext(Dispatchers.IO) {
                    fetchData()
                }
                updateUI(data)
                delay(1000)
            }
        }
    }
    
    // 状態の管理
    fun demonstrateStateManagement() = runBlocking {
        val stateFlow = MutableStateFlow<UIState>(UIState.Loading)
        
        launch {
            try {
                val data = withContext(Dispatchers.IO) {
                    fetchData()
                }
                stateFlow.value = UIState.Success(data)
            } catch (e: Exception) {
                stateFlow.value = UIState.Error(e.message ?: "不明なエラー")
            }
        }
        
        stateFlow.collect { state ->
            when (state) {
                is UIState.Loading -> println("読み込み中...")
                is UIState.Success -> println("データ: ${state.data}")
                is UIState.Error -> println("エラー: ${state.message}")
            }
        }
    }
    
    sealed class UIState {
        object Loading : UIState()
        data class Success(val data: String) : UIState()
        data class Error(val message: String) : UIState()
    }
    
    private suspend fun fetchData(): String {
        delay(1000)
        return "更新されたデータ"
    }
    
    private fun updateUI(data: String) {
        println("UI 更新: $data")
    }
}

// バックグラウンド処理
class BackgroundProcessing {
    // 基本的な処理
    fun demonstrateBasicProcessing() = runBlocking {
        withContext(Dispatchers.Default) {
            val result = processData(listOf(1, 2, 3, 4, 5))
            println("処理結果: $result")
        }
    }
    
    // 並行処理
    fun demonstrateParallelProcessing() = runBlocking {
        val items = listOf(1, 2, 3, 4, 5)
        val results = items.map { item ->
            async(Dispatchers.Default) {
                processItem(item)
            }
        }.awaitAll()
        
        println("並行処理結果: $results")
    }
    
    // 進捗の報告
    fun demonstrateProgressReporting() = runBlocking {
        val flow = flow {
            val items = listOf(1, 2, 3, 4, 5)
            items.forEachIndexed { index, item ->
                val result = processItem(item)
                emit(Progress(index + 1, items.size, result))
            }
        }
        
        flow.collect { progress ->
            println("進捗: ${progress.current}/${progress.total} - 結果: ${progress.result}")
        }
    }
    
    data class Progress(
        val current: Int,
        val total: Int,
        val result: Int
    )
    
    private suspend fun processData(items: List<Int>): List<Int> {
        return items.map { processItem(it) }
    }
    
    private suspend fun processItem(item: Int): Int {
        delay(100)
        return item * 2
    }
}

fun main() {
    // ネットワークリクエスト
    val network = NetworkRequests()
    network.demonstrateBasicRequest()
    network.demonstrateConcurrentRequests()
    network.demonstrateRequestRetry()
    
    // データベース操作
    val database = DatabaseOperations()
    database.demonstrateBasicOperations()
    database.demonstrateBatchProcessing()
    database.demonstrateTransaction()
    
    // UI の更新
    val ui = UIUpdates()
    ui.demonstrateBasicUpdate()
    ui.demonstratePeriodicUpdate()
    ui.demonstrateStateManagement()
    
    // バックグラウンド処理
    val background = BackgroundProcessing()
    background.demonstrateBasicProcessing()
    background.demonstrateParallelProcessing()
    background.demonstrateProgressReporting()
} 