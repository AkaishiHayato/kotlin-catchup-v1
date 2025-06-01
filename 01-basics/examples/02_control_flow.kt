/**
 * Kotlinの制御構文の基本的な使い方
 */

fun main() {
    // if式（式として使用可能）
    val age = 20
    val status = if (age >= 20) "成人" else "未成年"
    println("ステータス: $status")
    
    // 複数行のif式
    val grade = if (age >= 20) {
        "成人"
    } else if (age >= 18) {
        "準成人"
    } else {
        "未成年"
    }
    println("グレード: $grade")
    
    // when式（switch文の強化版）
    val day = 3
    val dayName = when (day) {
        1 -> "月曜日"
        2 -> "火曜日"
        3 -> "水曜日"
        4 -> "木曜日"
        5 -> "金曜日"
        6, 7 -> "週末"
        else -> "不明"
    }
    println("曜日: $dayName")
    
    // when式で範囲を使用
    val score = 85
    val result = when (score) {
        in 90..100 -> "A"
        in 80..89 -> "B"
        in 70..79 -> "C"
        in 60..69 -> "D"
        else -> "F"
    }
    println("成績: $result")
    
    // forループ
    // 範囲を使用
    println("1から5まで:")
    for (i in 1..5) {
        print("$i ")
    }
    println()
    
    // ステップを指定
    println("2ずつ増加:")
    for (i in 0..10 step 2) {
        print("$i ")
    }
    println()
    
    // 降順
    println("5から1まで:")
    for (i in 5 downTo 1) {
        print("$i ")
    }
    println()
    
    // リストの要素を反復
    val fruits = listOf("りんご", "バナナ", "オレンジ")
    println("フルーツ:")
    for (fruit in fruits) {
        print("$fruit ")
    }
    println()
    
    // インデックス付きの反復
    for ((index, fruit) in fruits.withIndex()) {
        println("$index: $fruit")
    }
    
    // whileループ
    var count = 0
    while (count < 5) {
        print("$count ")
        count++
    }
    println()
    
    // do-whileループ
    var number = 0
    do {
        print("$number ")
        number++
    } while (number < 5)
    println()
    
    // breakとcontinue
    println("breakの例:")
    for (i in 1..10) {
        if (i == 5) break
        print("$i ")
    }
    println()
    
    println("continueの例:")
    for (i in 1..5) {
        if (i == 3) continue
        print("$i ")
    }
    println()
} 