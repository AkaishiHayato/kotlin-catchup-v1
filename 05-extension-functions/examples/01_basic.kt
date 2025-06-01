/**
 * Kotlin の拡張関数の基本的な使い方
 */

// 基本的な拡張関数
fun String.addHello(): String {
    return "Hello, $this"
}

// 複数のパラメータを持つ拡張関数
fun String.addPrefix(prefix: String): String {
    return "$prefix$this"
}

// ジェネリックな拡張関数
fun <T> List<T>.secondOrNull(): T? {
    return if (size >= 2) get(1) else null
}

// 拡張関数のオーバーロード
fun Int.times(str: String): String {
    return str.repeat(this)
}

fun String.times(n: Int): String {
    return this.repeat(n)
}

// 拡張関数のスコープ
class ExtensionScope {
    fun String.addWorld(): String {
        return "$this, World"
    }
    
    fun demonstrateScope() {
        val result = "Hello".addWorld()
        println(result)
    }
}

// 拡張関数の優先順位
class ExtensionPriority {
    fun String.addExclamation(): String {
        return "$this!"
    }
    
    fun demonstratePriority() {
        val str = "Hello"
        println(str.addExclamation())  // クラス内の拡張関数が優先
    }
}

// 拡張関数の可視性
private fun String.addPrivate(): String {
    return "$this (private)"
}

// 拡張関数の実装
class StringExtensions {
    fun String.addCustom(prefix: String, suffix: String): String {
        return "$prefix$this$suffix"
    }
    
    fun String.addCustomWithDefault(prefix: String = "【", suffix: String = "】"): String {
        return "$prefix$this$suffix"
    }
}

// 拡張関数の使用例
class ExtensionExamples {
    fun demonstrateBasicExtensions() {
        val name = "Taro"
        println(name.addHello())
        println(name.addPrefix("Mr. "))
    }
    
    fun demonstrateGenericExtensions() {
        val list = listOf(1, 2, 3, 4, 5)
        println("2番目の要素: ${list.secondOrNull()}")
        
        val emptyList = emptyList<Int>()
        println("空リストの2番目の要素: ${emptyList.secondOrNull()}")
    }
    
    fun demonstrateOverloadedExtensions() {
        val str = "Hello"
        val n = 3
        
        println(str.times(n))
        println(n.times(str))
    }
    
    fun demonstrateCustomExtensions() {
        val extensions = StringExtensions()
        val text = "Kotlin"
        
        println(text.addCustom("【", "】"))
        println(text.addCustomWithDefault())
        println(text.addCustomWithDefault(prefix = "「", suffix = "」"))
    }
}

fun main() {
    // 基本的な拡張関数の使用
    val name = "Taro"
    println(name.addHello())
    println(name.addPrefix("Mr. "))
    
    // ジェネリックな拡張関数の使用
    val numbers = listOf(1, 2, 3, 4, 5)
    println("2番目の数: ${numbers.secondOrNull()}")
    
    val strings = listOf("A", "B", "C")
    println("2番目の文字列: ${strings.secondOrNull()}")
    
    // 拡張関数のオーバーロード
    val str = "Hello"
    val n = 3
    
    println(str.times(n))
    println(n.times(str))
    
    // スコープのデモンストレーション
    val scope = ExtensionScope()
    scope.demonstrateScope()
    
    // 優先順位のデモンストレーション
    val priority = ExtensionPriority()
    priority.demonstratePriority()
    
    // カスタム拡張関数の使用
    val examples = ExtensionExamples()
    examples.demonstrateBasicExtensions()
    examples.demonstrateGenericExtensions()
    examples.demonstrateOverloadedExtensions()
    examples.demonstrateCustomExtensions()
    
    // 拡張関数の実践的な使用例
    val text = "Kotlin"
    val extensions = StringExtensions()
    
    println(text.addCustom("【", "】"))
    println(text.addCustomWithDefault())
    println(text.addCustomWithDefault(prefix = "「", suffix = "」"))
    
    // 拡張関数の組み合わせ
    val result = text
        .addCustom("【", "】")
        .addHello()
        .addPrefix("Mr. ")
    
    println("組み合わせた結果: $result")
} 