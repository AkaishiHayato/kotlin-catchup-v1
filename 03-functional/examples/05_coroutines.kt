/**
 * Kotlin のコルーチンの基本的な使い方
 */

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

// 基本的なコルーチン
class CoroutineBasics {
    // コルーチンの起動
    fun launchCoroutine() = runBlocking {
        println("メインスレッド: ${Thread.currentThread().name}")
        
        launch {
            println("コルーチン1: ${Thread.currentThread().name}")
            delay(1000)
            println("コルーチン1: 1秒後に実行")
        }
        
        launch {
            println("コルーチン2: ${Thread.currentThread().name}")
            delay(500)
            println("コルーチン2: 0.5秒後に実行")
        }
        
        println("メインスレッド: コルーチンを起動しました")
    }
    
    // 非同期処理
    suspend fun fetchData(): String {
        delay(1000)
        return "データを取得しました"
    }
    
    fun asyncExample() = runBlocking {
        val result = async {
            fetchData()
        }
        
        println("非同期処理を開始しました")
        println("結果: ${result.await()}")
    }
    
    // スコープとコンテキスト
    fun scopeExample() = runBlocking {
        val scope = CoroutineScope(Dispatchers.Default)
        
        scope.launch {
            println("Defaultスレッド: ${Thread.currentThread().name}")
        }
        
        scope.launch(Dispatchers.IO) {
            println("IOスレッド: ${Thread.currentThread().name}")
        }
        
        scope.launch(Dispatchers.Main) {
            println("Mainスレッド: ${Thread.currentThread().name}")
        }
        
        delay(1000)
    }
}

// チャネルとフロー
class ChannelAndFlow {
    // チャネルの使用
    fun channelExample() = runBlocking {
        val channel = Channel<Int>()
        
        launch {
            for (i in 1..5) {
                channel.send(i)
                delay(100)
            }
            channel.close()
        }
        
        for (value in channel) {
            println("受信: $value")
        }
    }
    
    // フローの使用
    fun flowExample() = runBlocking {
        val flow = flow {
            for (i in 1..5) {
                emit(i)
                delay(100)
            }
        }
        
        flow.collect { value ->
            println("フロー値: $value")
        }
    }
    
    // フローの変換
    fun flowTransformExample() = runBlocking {
        val flow = flow {
            for (i in 1..5) {
                emit(i)
                delay(100)
            }
        }
        
        flow
            .map { it * it }
            .filter { it % 2 == 0 }
            .collect { value ->
                println("変換後の値: $value")
            }
    }
}

// 例外処理
class ExceptionHandling {
    // コルーチンの例外処理
    fun exceptionExample() = runBlocking {
        val handler = CoroutineExceptionHandler { _, exception ->
            println("例外が発生しました: ${exception.message}")
        }
        
        val scope = CoroutineScope(Dispatchers.Default + handler)
        
        scope.launch {
            throw RuntimeException("テスト例外")
        }
        
        delay(1000)
    }
    
    // スーパーバイザージョブ
    fun supervisorExample() = runBlocking {
        val supervisor = SupervisorJob()
        val scope = CoroutineScope(Dispatchers.Default + supervisor)
        
        scope.launch {
            throw RuntimeException("子コルーチン1の例外")
        }
        
        scope.launch {
            delay(100)
            println("子コルーチン2は正常に実行されます")
        }
        
        delay(1000)
    }
}

// コルーチンの実践的な使用例
class PracticalExample {
    private val scope = CoroutineScope(Dispatchers.Default)
    
    // データの取得と処理
    suspend fun fetchAndProcessData(): List<String> {
        return coroutineScope {
            val data1 = async { fetchData1() }
            val data2 = async { fetchData2() }
            
            listOf(data1.await(), data2.await())
        }
    }
    
    private suspend fun fetchData1(): String {
        delay(1000)
        return "データ1"
    }
    
    private suspend fun fetchData2(): String {
        delay(500)
        return "データ2"
    }
    
    // 並行処理
    fun parallelProcessing() = runBlocking {
        val results = mutableListOf<Deferred<Int>>()
        
        for (i in 1..5) {
            val result = scope.async {
                processItem(i)
            }
            results.add(result)
        }
        
        val finalResults = results.awaitAll()
        println("並行処理の結果: $finalResults")
    }
    
    private suspend fun processItem(item: Int): Int {
        delay(100)
        return item * item
    }
}

fun main() {
    val basics = CoroutineBasics()
    val channelAndFlow = ChannelAndFlow()
    val exceptionHandling = ExceptionHandling()
    val practicalExample = PracticalExample()
    
    // 基本的なコルーチンの使用
    println("基本的なコルーチン:")
    basics.launchCoroutine()
    
    // 非同期処理
    println("\n非同期処理:")
    basics.asyncExample()
    
    // スコープとコンテキスト
    println("\nスコープとコンテキスト:")
    basics.scopeExample()
    
    // チャネルとフロー
    println("\nチャネル:")
    channelAndFlow.channelExample()
    
    println("\nフロー:")
    channelAndFlow.flowExample()
    
    println("\nフローの変換:")
    channelAndFlow.flowTransformExample()
    
    // 例外処理
    println("\n例外処理:")
    exceptionHandling.exceptionExample()
    
    println("\nスーパーバイザージョブ:")
    exceptionHandling.supervisorExample()
    
    // 実践的な使用例
    println("\n実践的な使用例:")
    runBlocking {
        val results = practicalExample.fetchAndProcessData()
        println("取得したデータ: $results")
        
        practicalExample.parallelProcessing()
    }
    
    // コルーチンのキャンセル
    println("\nコルーチンのキャンセル:")
    runBlocking {
        val job = launch {
            try {
                repeat(1000) { i ->
                    println("ジョブ: $i")
                    delay(100)
                }
            } catch (e: CancellationException) {
                println("ジョブがキャンセルされました")
            }
        }
        
        delay(1000)
        job.cancel()
        job.join()
    }
    
    // コルーチンのタイムアウト
    println("\nコルーチンのタイムアウト:")
    runBlocking {
        try {
            withTimeout(1000) {
                repeat(1000) { i ->
                    println("タイムアウトテスト: $i")
                    delay(100)
                }
            }
        } catch (e: TimeoutCancellationException) {
            println("タイムアウトが発生しました")
        }
    }
} 