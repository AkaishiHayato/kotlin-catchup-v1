/**
 * Kotlinの関数の基本的な使い方
 */

// 基本的な関数定義
fun greet(name: String): String {
    return "こんにちは、$nameさん！"
}

// 単一式関数
fun square(x: Int) = x * x

// デフォルト引数
fun greetWithTime(name: String, time: String = "朝") = "こんにちは、$nameさん！$timeですね。"

// 名前付き引数
fun createUser(name: String, age: Int, email: String) = "ユーザー: $name, 年齢: $age, メール: $email"

// 可変長引数
fun sum(vararg numbers: Int): Int {
    return numbers.sum()
}

// 関数型パラメータ
fun calculate(x: Int, y: Int, operation: (Int, Int) -> Int): Int {
    return operation(x, y)
}

// 高階関数
fun processNumbers(numbers: List<Int>, processor: (Int) -> Int): List<Int> {
    return numbers.map(processor)
}

// 拡張関数
fun String.addExclamation() = "$this!"

// 中置関数
infix fun Int.times(str: String) = str.repeat(this)

fun main() {
    // 基本的な関数の呼び出し
    println(greet("Taro"))
    
    // 単一式関数の呼び出し
    println("5の2乗: ${square(5)}")
    
    // デフォルト引数の使用
    println(greetWithTime("Taro"))  // デフォルト値を使用
    println(greetWithTime("Taro", "夜"))  // 引数を指定
    
    // 名前付き引数の使用
    println(createUser(
        name = "Taro",
        age = 25,
        email = "taro@example.com"
    ))
    
    // 可変長引数の使用
    println("合計: ${sum(1, 2, 3, 4, 5)}")
    
    // 関数型パラメータの使用
    val add = { x: Int, y: Int -> x + y }
    val multiply = { x: Int, y: Int -> x * y }
    
    println("加算: ${calculate(5, 3, add)}")
    println("乗算: ${calculate(5, 3, multiply)}")
    
    // 高階関数の使用
    val numbers = listOf(1, 2, 3, 4, 5)
    val doubled = processNumbers(numbers) { it * 2 }
    println("2倍: $doubled")
    
    // 拡張関数の使用
    val message = "こんにちは"
    println(message.addExclamation())
    
    // 中置関数の使用
    println(3 times "Kotlin ")
    
    // ラムダ式
    val numbers2 = listOf(1, 2, 3, 4, 5)
    val evenNumbers = numbers2.filter { it % 2 == 0 }
    println("偶数: $evenNumbers")
    
    // スコープ関数
    val person = Person("Taro", 25)
    
    // let
    person.let {
        println("名前: ${it.name}")
        println("年齢: ${it.age}")
    }
    
    // with
    with(person) {
        println("名前: $name")
        println("年齢: $age")
    }
    
    // run
    val result = person.run {
        "名前: $name, 年齢: $age"
    }
    println(result)
    
    // apply
    val newPerson = Person("", 0).apply {
        name = "Jiro"
        age = 30
    }
    println("新しい人物: ${newPerson.name}, ${newPerson.age}")
}

// データクラス
data class Person(var name: String, var age: Int) 