/**
 * Kotlin のコルーチンの基本的な使い方
 */

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

// 基本的なコルーチン
class BasicCoroutines {
    fun launchCoroutine() = runBlocking {
        println("メインスレッド: ${Thread.currentThread().name}")
        
        launch {
            println("コルーチン1: ${Thread.currentThread().name}")
            delay(1000)
            println("コルーチン1: 1秒後")
        }
        
        launch {
            println("コルーチン2: ${Thread.currentThread().name}")
            delay(500)
            println("コルーチン2: 0.5秒後")
        }
        
        println("メインスレッド: コルーチン起動後")
    }
    
    // スコープの使用
    fun demonstrateScope() = runBlocking {
        val scope = CoroutineScope(Dispatchers.Default)
        
        scope.launch {
            println("Default スコープ: ${Thread.currentThread().name}")
            delay(1000)
            println("Default スコープ: 1秒後")
        }
        
        scope.launch(Dispatchers.IO) {
            println("IO スコープ: ${Thread.currentThread().name}")
            delay(500)
            println("IO スコープ: 0.5秒後")
        }
        
        delay(2000)
    }
    
    // ジョブの使用
    fun demonstrateJob() = runBlocking {
        val job = launch {
            repeat(5) { i ->
                println("ジョブ: $i")
                delay(500)
            }
        }
        
        delay(1000)
        println("ジョブの状態: ${job.isActive}")
        
        job.cancel()
        println("ジョブの状態: ${job.isActive}")
    }
    
    // コンテキストの使用
    fun demonstrateContext() = runBlocking {
        val dispatcher = Dispatchers.Default + CoroutineName("CustomContext")
        
        launch(dispatcher) {
            println("カスタムコンテキスト: ${Thread.currentThread().name}")
            println("コルーチン名: ${coroutineContext[CoroutineName]}")
        }
    }
}

// 非同期処理の基本
class AsyncBasics {
    fun demonstrateAsync() = runBlocking {
        val time = measureTimeMillis {
            val result1 = async { fetchData(1) }
            val result2 = async { fetchData(2) }
            
            println("結果1: ${result1.await()}")
            println("結果2: ${result2.await()}")
        }
        
        println("実行時間: ${time}ms")
    }
    
    private suspend fun fetchData(id: Int): String {
        delay(1000)
        return "データ $id"
    }
}

// スコープ関数の使用
class ScopeFunctions {
    fun demonstrateWithContext() = runBlocking {
        val result = withContext(Dispatchers.IO) {
            "IO スレッドで実行"
        }
        println(result)
    }
    
    fun demonstrateCoroutineScope() = runBlocking {
        coroutineScope {
            launch {
                delay(1000)
                println("スコープ内のコルーチン1")
            }
            
            launch {
                delay(500)
                println("スコープ内のコルーチン2")
            }
        }
        println("スコープ終了")
    }
}

// 実践的な使用例
class PracticalExamples {
    fun demonstrateParallelProcessing() = runBlocking {
        val items = listOf(1, 2, 3, 4, 5)
        val results = items.map { item ->
            async {
                processItem(item)
            }
        }.awaitAll()
        
        println("処理結果: $results")
    }
    
    private suspend fun processItem(item: Int): Int {
        delay(1000)
        return item * 2
    }
    
    fun demonstrateTimeout() = runBlocking {
        try {
            withTimeout(1000) {
                delay(2000)
                println("タイムアウト後")
            }
        } catch (e: TimeoutCancellationException) {
            println("タイムアウト発生")
        }
    }
}

fun main() {
    // 基本的なコルーチンの使用
    val basic = BasicCoroutines()
    basic.launchCoroutine()
    
    // スコープの使用
    basic.demonstrateScope()
    
    // ジョブの使用
    basic.demonstrateJob()
    
    // コンテキストの使用
    basic.demonstrateContext()
    
    // 非同期処理の基本
    val async = AsyncBasics()
    async.demonstrateAsync()
    
    // スコープ関数の使用
    val scope = ScopeFunctions()
    scope.demonstrateWithContext()
    scope.demonstrateCoroutineScope()
    
    // 実践的な使用例
    val practical = PracticalExamples()
    practical.demonstrateParallelProcessing()
    practical.demonstrateTimeout()
} 