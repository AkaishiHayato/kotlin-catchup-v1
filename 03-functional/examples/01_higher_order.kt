/**
 * Kotlin の高階関数とラムダ式の基本的な使い方
 */

// 関数型の定義
typealias Operation = (Int, Int) -> Int

// 高階関数の実装
fun calculate(x: Int, y: Int, operation: Operation): Int {
    return operation(x, y)
}

// 関数を返す関数
fun getOperation(operationType: String): Operation {
    return when (operationType) {
        "add" -> { a, b -> a + b }
        "subtract" -> { a, b -> a - b }
        "multiply" -> { a, b -> a * b }
        "divide" -> { a, b -> a / b }
        else -> throw IllegalArgumentException("未知の演算: $operationType")
    }
}

// ラムダ式を使用した関数
val square: (Int) -> Int = { x -> x * x }

// 型推論を活用したラムダ式
val double = { x: Int -> x * 2 }

// ラムダ式の簡略化
val numbers = listOf(1, 2, 3, 4, 5)
val evenNumbers = numbers.filter { it % 2 == 0 }

// 関数リテラル
fun processNumbers(numbers: List<Int>, processor: (Int) -> Int): List<Int> {
    return numbers.map(processor)
}

// ラムダ式とスコープ
class Calculator {
    private var lastResult: Int = 0
    
    fun calculate(x: Int, y: Int, operation: Operation) {
        lastResult = operation(x, y)
    }
    
    fun getLastResult(): Int = lastResult
}

// ラムダ式とレシーバ
fun buildString(builder: StringBuilder.() -> Unit): String {
    val stringBuilder = StringBuilder()
    stringBuilder.builder()
    return stringBuilder.toString()
}

// ラムダ式とクロージャ
fun createCounter(): () -> Int {
    var count = 0
    return { count++ }
}

fun main() {
    // 基本的な高階関数の使用
    val sum = calculate(5, 3) { a, b -> a + b }
    println("5 + 3 = $sum")
    
    val difference = calculate(10, 4) { a, b -> a - b }
    println("10 - 4 = $difference")
    
    // 関数を返す関数の使用
    val add = getOperation("add")
    val multiply = getOperation("multiply")
    
    println("5 + 3 = ${add(5, 3)}")
    println("5 * 3 = ${multiply(5, 3)}")
    
    // ラムダ式の使用
    println("5の2乗: ${square(5)}")
    println("5の2倍: ${double(5)}")
    
    // ラムダ式の簡略化
    println("偶数のみ: $evenNumbers")
    
    // 関数リテラルの使用
    val doubled = processNumbers(numbers) { it * 2 }
    println("2倍にした数: $doubled")
    
    // ラムダ式とスコープ
    val calculator = Calculator()
    calculator.calculate(10, 5) { a, b -> a + b }
    println("計算結果: ${calculator.getLastResult()}")
    
    // ラムダ式とレシーバ
    val message = buildString {
        append("Hello, ")
        append("World!")
    }
    println(message)
    
    // ラムダ式とクロージャ
    val counter = createCounter()
    println("カウント: ${counter()}")
    println("カウント: ${counter()}")
    println("カウント: ${counter()}")
    
    // 複数のラムダ式の組み合わせ
    val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val result = numbers
        .filter { it % 2 == 0 }
        .map { it * it }
        .reduce { acc, num -> acc + num }
    println("偶数の2乗の合計: $result")
    
    // ラムダ式とnull安全性
    val nullableNumbers = listOf(1, null, 3, null, 5)
    val nonNullNumbers = nullableNumbers.filterNotNull()
    println("nullを除外した数: $nonNullNumbers")
    
    // ラムダ式と例外処理
    try {
        val divide = getOperation("divide")
        println("10 / 0 = ${divide(10, 0)}")
    } catch (e: ArithmeticException) {
        println("エラー: ${e.message}")
    }
    
    // ラムダ式と型推論
    val operations = mapOf(
        "add" to { a: Int, b: Int -> a + b },
        "subtract" to { a: Int, b: Int -> a - b }
    )
    
    println("5 + 3 = ${operations["add"]?.invoke(5, 3)}")
    println("5 - 3 = ${operations["subtract"]?.invoke(5, 3)}")
} 