/**
 * Kotlin のコルーチンを使用した非同期処理の例
 */

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

// 基本的な非同期処理
class AsyncExamples {
    fun demonstrateAsyncAwait() = runBlocking {
        val time = measureTimeMillis {
            val result1 = async { fetchData(1) }
            val result2 = async { fetchData(2) }
            val result3 = async { fetchData(3) }
            
            println("結果1: ${result1.await()}")
            println("結果2: ${result2.await()}")
            println("結果3: ${result3.await()}")
        }
        
        println("実行時間: ${time}ms")
    }
    
    private suspend fun fetchData(id: Int): String {
        delay(1000)
        return "データ $id"
    }
    
    // 並行処理
    fun demonstrateConcurrent() = runBlocking {
        val time = measureTimeMillis {
            val results = listOf(1, 2, 3, 4, 5).map { id ->
                async {
                    fetchData(id)
                }
            }.awaitAll()
            
            println("結果: $results")
        }
        
        println("実行時間: ${time}ms")
    }
    
    // 非同期処理の組み合わせ
    fun demonstrateCombined() = runBlocking {
        val time = measureTimeMillis {
            val result1 = async { fetchData(1) }
            val result2 = async { fetchData(2) }
            
            val combined = async {
                val data1 = result1.await()
                val data2 = result2.await()
                "$data1 + $data2"
            }
            
            println("組み合わせ結果: ${combined.await()}")
        }
        
        println("実行時間: ${time}ms")
    }
}

// 実践的な非同期処理
class PracticalAsync {
    // データの取得と処理
    fun fetchAndProcessData() = runBlocking {
        val time = measureTimeMillis {
            val data = async { fetchData() }
            val processed = async { processData(data.await()) }
            
            println("処理結果: ${processed.await()}")
        }
        
        println("実行時間: ${time}ms")
    }
    
    private suspend fun fetchData(): List<Int> {
        delay(1000)
        return listOf(1, 2, 3, 4, 5)
    }
    
    private suspend fun processData(data: List<Int>): List<Int> {
        delay(1000)
        return data.map { it * 2 }
    }
    
    // 複数の非同期処理の結果を集約
    fun aggregateResults() = runBlocking {
        val time = measureTimeMillis {
            val results = listOf(
                async { fetchData1() },
                async { fetchData2() },
                async { fetchData3() }
            ).awaitAll()
            
            val aggregated = results.flatten().sum()
            println("集約結果: $aggregated")
        }
        
        println("実行時間: ${time}ms")
    }
    
    private suspend fun fetchData1(): List<Int> {
        delay(1000)
        return listOf(1, 2, 3)
    }
    
    private suspend fun fetchData2(): List<Int> {
        delay(1500)
        return listOf(4, 5, 6)
    }
    
    private suspend fun fetchData3(): List<Int> {
        delay(2000)
        return listOf(7, 8, 9)
    }
}

// 非同期処理のエラー処理
class AsyncErrorHandling {
    fun demonstrateErrorHandling() = runBlocking {
        try {
            val result = async {
                try {
                    fetchDataWithError()
                } catch (e: Exception) {
                    "エラー発生: ${e.message}"
                }
            }.await()
            
            println("結果: $result")
        } catch (e: Exception) {
            println("非同期処理でエラー発生: ${e.message}")
        }
    }
    
    private suspend fun fetchDataWithError(): String {
        delay(1000)
        throw Exception("データ取得エラー")
    }
    
    // 複数の非同期処理のエラー処理
    fun demonstrateMultipleErrorHandling() = runBlocking {
        val results = listOf(1, 2, 3).map { id ->
            async {
                try {
                    fetchDataWithPossibleError(id)
                } catch (e: Exception) {
                    "エラー (ID: $id): ${e.message}"
                }
            }
        }.awaitAll()
        
        println("結果: $results")
    }
    
    private suspend fun fetchDataWithPossibleError(id: Int): String {
        delay(1000)
        if (id == 2) {
            throw Exception("ID $id でエラー発生")
        }
        return "データ $id"
    }
}

fun main() {
    // 基本的な非同期処理
    val async = AsyncExamples()
    async.demonstrateAsyncAwait()
    async.demonstrateConcurrent()
    async.demonstrateCombined()
    
    // 実践的な非同期処理
    val practical = PracticalAsync()
    practical.fetchAndProcessData()
    practical.aggregateResults()
    
    // 非同期処理のエラー処理
    val errorHandling = AsyncErrorHandling()
    errorHandling.demonstrateErrorHandling()
    errorHandling.demonstrateMultipleErrorHandling()
} 