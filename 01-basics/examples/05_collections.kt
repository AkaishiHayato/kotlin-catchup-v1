/**
 * Kotlinのコレクションの基本的な使い方
 */

fun main() {
    // List（リスト）
    println("=== List ===")
    // 不変リスト
    val immutableList = listOf(1, 2, 3, 4, 5)
    println("不変リスト: $immutableList")
    
    // 可変リスト
    val mutableList = mutableListOf(1, 2, 3)
    mutableList.add(4)
    mutableList.add(5)
    println("可変リスト: $mutableList")
    
    // リストの操作
    println("最初の要素: ${immutableList.first()}")
    println("最後の要素: ${immutableList.last()}")
    println("要素数: ${immutableList.size}")
    println("要素の合計: ${immutableList.sum()}")
    println("平均: ${immutableList.average()}")
    
    // リストのフィルタリング
    val evenNumbers = immutableList.filter { it % 2 == 0 }
    println("偶数: $evenNumbers")
    
    // リストの変換
    val doubled = immutableList.map { it * 2 }
    println("2倍: $doubled")
    
    // リストのソート
    val unsorted = listOf(5, 2, 8, 1, 9)
    println("昇順: ${unsorted.sorted()}")
    println("降順: ${unsorted.sortedDescending()}")
    
    // Set（セット）
    println("\n=== Set ===")
    // 不変セット
    val immutableSet = setOf(1, 2, 3, 3, 4, 4, 5)
    println("不変セット: $immutableSet")  // 重複は自動的に除去される
    
    // 可変セット
    val mutableSet = mutableSetOf(1, 2, 3)
    mutableSet.add(4)
    mutableSet.add(4)  // 重複は無視される
    println("可変セット: $mutableSet")
    
    // セットの操作
    println("要素数: ${immutableSet.size}")
    println("3を含む: ${immutableSet.contains(3)}")
    
    // セットの演算
    val set1 = setOf(1, 2, 3, 4)
    val set2 = setOf(3, 4, 5, 6)
    println("和集合: ${set1.union(set2)}")
    println("積集合: ${set1.intersect(set2)}")
    println("差集合: ${set1.subtract(set2)}")
    
    // Map（マップ）
    println("\n=== Map ===")
    // 不変マップ
    val immutableMap = mapOf(
        "apple" to "りんご",
        "banana" to "バナナ",
        "orange" to "オレンジ"
    )
    println("不変マップ: $immutableMap")
    
    // 可変マップ
    val mutableMap = mutableMapOf(
        "apple" to "りんご",
        "banana" to "バナナ"
    )
    mutableMap["orange"] = "オレンジ"
    println("可変マップ: $mutableMap")
    
    // マップの操作
    println("キーの一覧: ${immutableMap.keys}")
    println("値の一覧: ${immutableMap.values}")
    println("appleの翻訳: ${immutableMap["apple"]}")
    println("存在しないキー: ${immutableMap["grape"]}")
    
    // マップの反復
    println("\nマップの反復:")
    for ((key, value) in immutableMap) {
        println("$key -> $value")
    }
    
    // コレクションの変換
    println("\n=== コレクションの変換 ===")
    val list = listOf(1, 2, 3, 4, 5)
    
    // List to Set
    val set = list.toSet()
    println("List to Set: $set")
    
    // List to Map
    val map = list.associateWith { it * it }
    println("List to Map: $map")
    
    // コレクションの操作
    println("\n=== コレクションの操作 ===")
    val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    
    // フィルタリング
    println("偶数: ${numbers.filter { it % 2 == 0 }}")
    println("5より大きい数: ${numbers.filter { it > 5 }}")
    
    // 変換
    println("2倍: ${numbers.map { it * 2 }}")
    println("文字列に変換: ${numbers.map { "数値: $it" }}")
    
    // 集約
    println("合計: ${numbers.sum()}")
    println("平均: ${numbers.average()}")
    println("最大値: ${numbers.maxOrNull()}")
    println("最小値: ${numbers.minOrNull()}")
    
    // グループ化
    val grouped = numbers.groupBy { it % 2 == 0 }
    println("偶数・奇数でグループ化: $grouped")
    
    // 並び替え
    println("昇順: ${numbers.sorted()}")
    println("降順: ${numbers.sortedDescending()}")
    
    // スライス
    println("最初の3つ: ${numbers.take(3)}")
    println("最後の3つ: ${numbers.takeLast(3)}")
    println("3から5まで: ${numbers.slice(2..4)}")
    
    // 重複の除去
    val withDuplicates = listOf(1, 2, 2, 3, 3, 3, 4, 4, 4, 4)
    println("重複除去: ${withDuplicates.distinct()}")
} 