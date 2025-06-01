/**
 * Kotlin のコルーチンを使用したエラー処理の例
 */

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

// 基本的なエラー処理
class BasicErrorHandling {
    fun demonstrateTryCatch() = runBlocking {
        try {
            launch {
                throw Exception("コルーチンでエラー発生")
            }
        } catch (e: Exception) {
            println("エラーをキャッチ: ${e.message}")
        }
    }
    
    // 非同期処理のエラー処理
    fun demonstrateAsyncError() = runBlocking {
        try {
            val result = async {
                throw Exception("非同期処理でエラー発生")
            }.await()
        } catch (e: Exception) {
            println("非同期処理のエラーをキャッチ: ${e.message}")
        }
    }
    
    // 複数のコルーチンのエラー処理
    fun demonstrateMultipleErrors() = runBlocking {
        val results = listOf(1, 2, 3).map { id ->
            async {
                try {
                    if (id == 2) {
                        throw Exception("ID $id でエラー発生")
                    }
                    "成功: $id"
                } catch (e: Exception) {
                    "エラー (ID: $id): ${e.message}"
                }
            }
        }.awaitAll()
        
        println("結果: $results")
    }
}

// スーパーバイザージョブ
class SupervisorJobExamples {
    fun demonstrateSupervisorJob() = runBlocking {
        val supervisor = SupervisorJob()
        
        val scope = CoroutineScope(supervisor)
        
        scope.launch {
            throw Exception("コルーチン1でエラー発生")
        }
        
        scope.launch {
            delay(100)
            println("コルーチン2は正常に実行")
        }
        
        delay(1000)
    }
    
    // スーパーバイザースコープ
    fun demonstrateSupervisorScope() = runBlocking {
        supervisorScope {
            val job1 = launch {
                throw Exception("コルーチン1でエラー発生")
            }
            
            val job2 = launch {
                delay(100)
                println("コルーチン2は正常に実行")
            }
            
            try {
                job1.join()
            } catch (e: Exception) {
                println("コルーチン1のエラーをキャッチ: ${e.message}")
            }
            
            job2.join()
        }
    }
}

// キャンセル処理
class CancellationExamples {
    fun demonstrateCancellation() = runBlocking {
        val job = launch {
            try {
                repeat(5) { i ->
                    delay(100)
                    println("実行中: $i")
                }
            } catch (e: CancellationException) {
                println("キャンセルされました")
                throw e
            }
        }
        
        delay(250)
        job.cancel()
        job.join()
    }
    
    // キャンセル不可能な処理
    fun demonstrateNonCancellable() = runBlocking {
        val job = launch {
            try {
                repeat(5) { i ->
                    delay(100)
                    println("実行中: $i")
                }
            } finally {
                withContext(NonCancellable) {
                    println("クリーンアップ処理")
                    delay(100)
                    println("クリーンアップ完了")
                }
            }
        }
        
        delay(250)
        job.cancel()
        job.join()
    }
}

// タイムアウト処理
class TimeoutExamples {
    fun demonstrateTimeout() = runBlocking {
        try {
            withTimeout(300) {
                repeat(5) { i ->
                    delay(100)
                    println("実行中: $i")
                }
            }
        } catch (e: TimeoutCancellationException) {
            println("タイムアウト発生")
        }
    }
    
    // タイムアウトとリトライ
    fun demonstrateTimeoutWithRetry() = runBlocking {
        var retryCount = 0
        val maxRetries = 3
        
        while (retryCount < maxRetries) {
            try {
                withTimeout(300) {
                    delay(200)
                    println("処理成功")
                }
                break
            } catch (e: TimeoutCancellationException) {
                retryCount++
                println("タイムアウト発生 (試行 $retryCount/$maxRetries)")
                delay(100)
            }
        }
    }
}

// 実践的なエラー処理
class PracticalErrorHandling {
    // エラーハンドリングの組み合わせ
    fun demonstrateCombinedErrorHandling() = runBlocking {
        supervisorScope {
            val job1 = launch {
                try {
                    withTimeout(300) {
                        delay(200)
                        throw Exception("処理エラー")
                    }
                } catch (e: Exception) {
                    println("ジョブ1のエラー: ${e.message}")
                }
            }
            
            val job2 = launch {
                try {
                    withTimeout(300) {
                        delay(100)
                        println("ジョブ2は成功")
                    }
                } catch (e: Exception) {
                    println("ジョブ2のエラー: ${e.message}")
                }
            }
            
            job1.join()
            job2.join()
        }
    }
    
    // エラー回復
    fun demonstrateErrorRecovery() = runBlocking {
        var attempt = 0
        val maxAttempts = 3
        
        while (attempt < maxAttempts) {
            try {
                val result = withTimeout(300) {
                    if (attempt == 1) {
                        throw Exception("一時的なエラー")
                    }
                    "処理成功"
                }
                println(result)
                break
            } catch (e: Exception) {
                attempt++
                println("エラー発生 (試行 $attempt/$maxAttempts): ${e.message}")
                delay(100)
            }
        }
    }
}

fun main() {
    // 基本的なエラー処理
    val basic = BasicErrorHandling()
    basic.demonstrateTryCatch()
    basic.demonstrateAsyncError()
    basic.demonstrateMultipleErrors()
    
    // スーパーバイザージョブ
    val supervisor = SupervisorJobExamples()
    supervisor.demonstrateSupervisorJob()
    supervisor.demonstrateSupervisorScope()
    
    // キャンセル処理
    val cancellation = CancellationExamples()
    cancellation.demonstrateCancellation()
    cancellation.demonstrateNonCancellable()
    
    // タイムアウト処理
    val timeout = TimeoutExamples()
    timeout.demonstrateTimeout()
    timeout.demonstrateTimeoutWithRetry()
    
    // 実践的なエラー処理
    val practical = PracticalErrorHandling()
    practical.demonstrateCombinedErrorHandling()
    practical.demonstrateErrorRecovery()
} 