/**
 * Kotlin のコルーチンを使用したチャネルの例
 */

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

// 基本的なチャネル
class BasicChannels {
    fun demonstrateChannel() = runBlocking {
        val channel = Channel<Int>()
        
        launch {
            for (i in 1..5) {
                delay(100)
                channel.send(i)
            }
            channel.close()
        }
        
        for (value in channel) {
            println("受信: $value")
        }
    }
    
    // バッファ付きチャネル
    fun demonstrateBufferedChannel() = runBlocking {
        val channel = Channel<Int>(Channel.UNLIMITED)
        
        launch {
            for (i in 1..5) {
                delay(100)
                channel.send(i)
                println("送信: $i")
            }
            channel.close()
        }
        
        delay(1000)
        
        for (value in channel) {
            println("受信: $value")
        }
    }
    
    // 複数の送信者
    fun demonstrateMultipleSenders() = runBlocking {
        val channel = Channel<Int>()
        
        val sender1 = launch {
            for (i in 1..3) {
                delay(100)
                channel.send(i)
                println("送信者1: $i")
            }
        }
        
        val sender2 = launch {
            for (i in 4..6) {
                delay(150)
                channel.send(i)
                println("送信者2: $i")
            }
        }
        
        launch {
            sender1.join()
            sender2.join()
            channel.close()
        }
        
        for (value in channel) {
            println("受信: $value")
        }
    }
}

// チャネルの実践的な使用例
class PracticalChannels {
    // 生産者-消費者パターン
    fun demonstrateProducerConsumer() = runBlocking {
        val channel = Channel<Int>()
        
        val producer = launch {
            for (i in 1..5) {
                delay(100)
                channel.send(i)
                println("生産: $i")
            }
            channel.close()
        }
        
        val consumer = launch {
            for (value in channel) {
                delay(200)
                println("消費: $value")
            }
        }
        
        producer.join()
        consumer.join()
    }
    
    // チャネルの変換
    fun demonstrateChannelTransformation() = runBlocking {
        val numbers = Channel<Int>()
        val squares = Channel<Int>()
        
        launch {
            for (i in 1..5) {
                numbers.send(i)
            }
            numbers.close()
        }
        
        launch {
            for (value in numbers) {
                squares.send(value * value)
            }
            squares.close()
        }
        
        for (value in squares) {
            println("二乗: $value")
        }
    }
    
    // チャネルの結合
    fun demonstrateChannelMerge() = runBlocking {
        val channel1 = Channel<Int>()
        val channel2 = Channel<Int>()
        val merged = Channel<Int>()
        
        launch {
            for (i in 1..3) {
                delay(100)
                channel1.send(i)
            }
            channel1.close()
        }
        
        launch {
            for (i in 4..6) {
                delay(150)
                channel2.send(i)
            }
            channel2.close()
        }
        
        launch {
            for (value in channel1) {
                merged.send(value)
            }
            for (value in channel2) {
                merged.send(value)
            }
            merged.close()
        }
        
        for (value in merged) {
            println("結合: $value")
        }
    }
}

// チャネルのエラー処理
class ChannelErrorHandling {
    fun demonstrateErrorHandling() = runBlocking {
        val channel = Channel<Int>()
        
        val sender = launch {
            try {
                for (i in 1..5) {
                    if (i == 3) {
                        throw Exception("送信エラー")
                    }
                    channel.send(i)
                }
            } catch (e: Exception) {
                println("送信者でエラー発生: ${e.message}")
            } finally {
                channel.close()
            }
        }
        
        try {
            for (value in channel) {
                println("受信: $value")
            }
        } catch (e: Exception) {
            println("受信者でエラー発生: ${e.message}")
        }
        
        sender.join()
    }
    
    // タイムアウト処理
    fun demonstrateTimeout() = runBlocking {
        val channel = Channel<Int>()
        
        launch {
            for (i in 1..5) {
                delay(100)
                channel.send(i)
            }
            channel.close()
        }
        
        try {
            withTimeout(300) {
                for (value in channel) {
                    println("受信: $value")
                }
            }
        } catch (e: TimeoutCancellationException) {
            println("タイムアウト発生")
        }
    }
}

fun main() {
    // 基本的なチャネル
    val basic = BasicChannels()
    basic.demonstrateChannel()
    basic.demonstrateBufferedChannel()
    basic.demonstrateMultipleSenders()
    
    // 実践的なチャネル
    val practical = PracticalChannels()
    practical.demonstrateProducerConsumer()
    practical.demonstrateChannelTransformation()
    practical.demonstrateChannelMerge()
    
    // チャネルのエラー処理
    val errorHandling = ChannelErrorHandling()
    errorHandling.demonstrateErrorHandling()
    errorHandling.demonstrateTimeout()
} 