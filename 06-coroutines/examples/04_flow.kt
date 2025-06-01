/**
 * Kotlin のコルーチンを使用した Flow の例
 */

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

// 基本的な Flow
class BasicFlow {
    fun demonstrateFlow() = runBlocking {
        val flow = flow {
            for (i in 1..5) {
                delay(100)
                emit(i)
            }
        }
        
        flow.collect { value ->
            println("受信: $value")
        }
    }
    
    // Flow の変換
    fun demonstrateFlowTransformation() = runBlocking {
        val flow = flow {
            for (i in 1..5) {
                delay(100)
                emit(i)
            }
        }
        
        flow
            .map { it * it }
            .filter { it % 2 == 0 }
            .collect { value ->
                println("変換後: $value")
            }
    }
    
    // 複数の Flow
    fun demonstrateMultipleFlows() = runBlocking {
        val flow1 = flow {
            for (i in 1..3) {
                delay(100)
                emit(i)
            }
        }
        
        val flow2 = flow {
            for (i in 4..6) {
                delay(150)
                emit(i)
            }
        }
        
        flow1.merge(flow2).collect { value ->
            println("結合: $value")
        }
    }
}

// Flow の実践的な使用例
class PracticalFlow {
    // データの取得と処理
    fun demonstrateDataProcessing() = runBlocking {
        val flow = flow {
            for (i in 1..5) {
                delay(100)
                emit(i)
            }
        }
        
        flow
            .map { fetchData(it) }
            .map { processData(it) }
            .collect { result ->
                println("処理結果: $result")
            }
    }
    
    private suspend fun fetchData(id: Int): String {
        delay(100)
        return "データ $id"
    }
    
    private suspend fun processData(data: String): String {
        delay(100)
        return "$data (処理済み)"
    }
    
    // Flow の結合
    fun demonstrateFlowCombination() = runBlocking {
        val flow1 = flow {
            for (i in 1..3) {
                delay(100)
                emit(i)
            }
        }
        
        val flow2 = flow {
            for (i in 4..6) {
                delay(150)
                emit(i)
            }
        }
        
        flow1.combine(flow2) { a, b ->
            "$a + $b"
        }.collect { result ->
            println("結合結果: $result")
        }
    }
    
    // Flow のバッファリング
    fun demonstrateBuffering() = runBlocking {
        val flow = flow {
            for (i in 1..5) {
                delay(100)
                emit(i)
            }
        }
        
        flow
            .buffer()
            .collect { value ->
                delay(200)
                println("バッファ: $value")
            }
    }
}

// Flow のエラー処理
class FlowErrorHandling {
    fun demonstrateErrorHandling() = runBlocking {
        val flow = flow {
            for (i in 1..5) {
                if (i == 3) {
                    throw Exception("Flow エラー")
                }
                delay(100)
                emit(i)
            }
        }
        
        flow
            .catch { e ->
                println("エラー発生: ${e.message}")
                emit(-1)
            }
            .collect { value ->
                println("受信: $value")
            }
    }
    
    // タイムアウト処理
    fun demonstrateTimeout() = runBlocking {
        val flow = flow {
            for (i in 1..5) {
                delay(100)
                emit(i)
            }
        }
        
        try {
            flow
                .timeout(300)
                .collect { value ->
                    println("受信: $value")
                }
        } catch (e: TimeoutCancellationException) {
            println("タイムアウト発生")
        }
    }
}

// Flow の実践的な使用例
class AdvancedFlow {
    // 状態の管理
    fun demonstrateStateFlow() = runBlocking {
        val stateFlow = MutableStateFlow(0)
        
        launch {
            for (i in 1..5) {
                delay(100)
                stateFlow.value = i
            }
        }
        
        stateFlow.collect { value ->
            println("状態: $value")
        }
    }
    
    // 共有フロー
    fun demonstrateSharedFlow() = runBlocking {
        val sharedFlow = MutableSharedFlow<Int>()
        
        launch {
            for (i in 1..5) {
                delay(100)
                sharedFlow.emit(i)
            }
        }
        
        launch {
            sharedFlow.collect { value ->
                println("受信者1: $value")
            }
        }
        
        launch {
            sharedFlow.collect { value ->
                println("受信者2: $value")
            }
        }
        
        delay(1000)
    }
    
    // Flow の変換と結合
    fun demonstrateFlowTransformation() = runBlocking {
        val flow1 = flow {
            for (i in 1..3) {
                delay(100)
                emit(i)
            }
        }
        
        val flow2 = flow {
            for (i in 4..6) {
                delay(150)
                emit(i)
            }
        }
        
        flow1
            .zip(flow2) { a, b ->
                "$a + $b"
            }
            .collect { result ->
                println("結合結果: $result")
            }
    }
}

fun main() {
    // 基本的な Flow
    val basic = BasicFlow()
    basic.demonstrateFlow()
    basic.demonstrateFlowTransformation()
    basic.demonstrateMultipleFlows()
    
    // 実践的な Flow
    val practical = PracticalFlow()
    practical.demonstrateDataProcessing()
    practical.demonstrateFlowCombination()
    practical.demonstrateBuffering()
    
    // Flow のエラー処理
    val errorHandling = FlowErrorHandling()
    errorHandling.demonstrateErrorHandling()
    errorHandling.demonstrateTimeout()
    
    // 高度な Flow
    val advanced = AdvancedFlow()
    advanced.demonstrateStateFlow()
    advanced.demonstrateSharedFlow()
    advanced.demonstrateFlowTransformation()
} 