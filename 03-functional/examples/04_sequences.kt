/**
 * Kotlin のシーケンスの基本的な使い方
 */

// シーケンスの生成と操作
class NumberProcessor {
    // シーケンスの生成
    fun generateNumbers(): Sequence<Int> {
        return sequence {
            var number = 1
            while (true) {
                yield(number++)
            }
        }
    }
    
    // 中間操作と終端操作
    fun processNumbers(limit: Int): List<Int> {
        return generateNumbers()
            .take(limit)
            .filter { it % 2 == 0 }
            .map { it * it }
            .toList()
    }
    
    // 遅延評価の確認
    fun demonstrateLazyEvaluation(): Sequence<Int> {
        return sequence {
            println("シーケンスの生成開始")
            for (i in 1..5) {
                println("要素 $i を生成")
                yield(i)
            }
        }
    }
    
    // シーケンスの変換
    fun transformSequence(): Sequence<String> {
        return generateNumbers()
            .take(5)
            .map { "Number: $it" }
    }
    
    // シーケンスの結合
    fun combineSequences(): Sequence<Int> {
        val seq1 = sequenceOf(1, 2, 3)
        val seq2 = sequenceOf(4, 5, 6)
        return seq1 + seq2
    }
    
    // シーケンスのグループ化
    fun groupSequence(): Map<Int, List<Int>> {
        return generateNumbers()
            .take(10)
            .groupBy { it % 3 }
    }
}

// シーケンスのパフォーマンス比較
class PerformanceComparison {
    private val largeList = (1..1_000_000).toList()
    
    // リストを使用した処理
    fun processWithList(): List<Int> {
        return largeList
            .filter { it % 2 == 0 }
            .map { it * it }
            .take(10)
    }
    
    // シーケンスを使用した処理
    fun processWithSequence(): List<Int> {
        return largeList.asSequence()
            .filter { it % 2 == 0 }
            .map { it * it }
            .take(10)
            .toList()
    }
}

// シーケンスの生成方法
class SequenceGenerator {
    // シーケンスの生成方法1: sequenceOf
    fun generateWithSequenceOf(): Sequence<Int> {
        return sequenceOf(1, 2, 3, 4, 5)
    }
    
    // シーケンスの生成方法2: asSequence
    fun generateWithAsSequence(): Sequence<Int> {
        return listOf(1, 2, 3, 4, 5).asSequence()
    }
    
    // シーケンスの生成方法3: generateSequence
    fun generateWithGenerateSequence(): Sequence<Int> {
        return generateSequence(1) { it + 1 }
    }
    
    // シーケンスの生成方法4: sequence ビルダー
    fun generateWithBuilder(): Sequence<Int> {
        return sequence {
            yield(1)
            yieldAll(listOf(2, 3, 4))
            yield(5)
        }
    }
}

fun main() {
    val processor = NumberProcessor()
    val performance = PerformanceComparison()
    val generator = SequenceGenerator()
    
    // シーケンスの基本操作
    println("処理された数値: ${processor.processNumbers(10)}")
    
    // 遅延評価の確認
    println("\n遅延評価の確認:")
    val lazySeq = processor.demonstrateLazyEvaluation()
    println("シーケンスを生成しました")
    
    println("最初の要素を取得:")
    println(lazySeq.first())
    
    println("残りの要素を取得:")
    lazySeq.forEach { println(it) }
    
    // シーケンスの変換
    println("\nシーケンスの変換:")
    processor.transformSequence().forEach { println(it) }
    
    // シーケンスの結合
    println("\nシーケンスの結合:")
    processor.combineSequences().forEach { println(it) }
    
    // シーケンスのグループ化
    println("\nシーケンスのグループ化:")
    processor.groupSequence().forEach { (key, value) ->
        println("余り $key: $value")
    }
    
    // パフォーマンス比較
    println("\nパフォーマンス比較:")
    val startTime1 = System.currentTimeMillis()
    performance.processWithList()
    val endTime1 = System.currentTimeMillis()
    println("リスト処理時間: ${endTime1 - startTime1}ms")
    
    val startTime2 = System.currentTimeMillis()
    performance.processWithSequence()
    val endTime2 = System.currentTimeMillis()
    println("シーケンス処理時間: ${endTime2 - startTime2}ms")
    
    // シーケンスの生成方法
    println("\nシーケンスの生成方法:")
    
    println("sequenceOf:")
    generator.generateWithSequenceOf().forEach { println(it) }
    
    println("\nasSequence:")
    generator.generateWithAsSequence().forEach { println(it) }
    
    println("\ngenerateSequence:")
    generator.generateWithGenerateSequence()
        .take(5)
        .forEach { println(it) }
    
    println("\nsequence ビルダー:")
    generator.generateWithBuilder().forEach { println(it) }
    
    // シーケンスの操作例
    val numbers = sequenceOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    
    // 中間操作
    val evenSquares = numbers
        .filter { it % 2 == 0 }
        .map { it * it }
    
    // 終端操作
    println("\n偶数の2乗:")
    evenSquares.forEach { println(it) }
    
    // 集約操作
    val sum = evenSquares.sum()
    val average = evenSquares.average()
    val max = evenSquares.maxOrNull()
    
    println("\n集約結果:")
    println("合計: $sum")
    println("平均: $average")
    println("最大値: $max")
    
    // シーケンスの変換と結合
    val seq1 = sequenceOf(1, 2, 3)
    val seq2 = sequenceOf(4, 5, 6)
    
    val combined = seq1 + seq2
    val zipped = seq1.zip(seq2)
    
    println("\n結合とzip:")
    println("結合: ${combined.toList()}")
    println("zip: ${zipped.toList()}")
} 