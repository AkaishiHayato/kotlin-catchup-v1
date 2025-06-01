/**
 * Kotlin の拡張プロパティの基本的な使い方
 */

// 基本的な拡張プロパティ
val String.lastChar: Char
    get() = this[length - 1]

// 計算プロパティ
val String.isPalindrome: Boolean
    get() = this == this.reversed()

// 複数の拡張プロパティ
val String.firstChar: Char
    get() = this[0]

val String.middleChar: Char?
    get() = if (length % 2 == 1) this[length / 2] else null

// 拡張プロパティの制約
class ExtensionProperties {
    // バッキングフィールドは使用できない
    // val String.backingField: String = ""  // コンパイルエラー
    
    // 初期化はできない
    // val String.initialized: String = "value"  // コンパイルエラー
}

// 拡張プロパティの実装
class StringProperties {
    val String.wordCount: Int
        get() = this.split("\\s+".toRegex()).size
    
    val String.isValidEmail: Boolean
        get() = this.matches(Regex("^[A-Za-z0-9+_.-]+@(.+)\$"))
    
    val String.isValidPhoneNumber: Boolean
        get() = this.matches(Regex("^\\d{2,4}-\\d{2,4}-\\d{4}\$"))
}

// 拡張プロパティの使用例
class PropertyExamples {
    fun demonstrateBasicProperties() {
        val text = "Hello"
        println("最後の文字: ${text.lastChar}")
        println("最初の文字: ${text.firstChar}")
        println("中央の文字: ${text.middleChar}")
    }
    
    fun demonstrateComputedProperties() {
        val palindrome = "level"
        val nonPalindrome = "hello"
        
        println("$palindrome は回文: ${palindrome.isPalindrome}")
        println("$nonPalindrome は回文: ${nonPalindrome.isPalindrome}")
    }
    
    fun demonstrateCustomProperties() {
        val properties = StringProperties()
        val text = "Hello World"
        val email = "user@example.com"
        val phone = "03-1234-5678"
        
        println("単語数: ${text.wordCount}")
        println("有効なメールアドレス: ${email.isValidEmail}")
        println("有効な電話番号: ${phone.isValidPhoneNumber}")
    }
}

// 拡張プロパティの実践的な使用例
class PracticalProperties {
    // 日付の拡張プロパティ
    val String.isValidDate: Boolean
        get() = try {
            val parts = this.split("-")
            if (parts.size != 3) return false
            
            val year = parts[0].toInt()
            val month = parts[1].toInt()
            val day = parts[2].toInt()
            
            year in 1900..2100 && month in 1..12 && day in 1..31
        } catch (e: NumberFormatException) {
            false
        }
    
    // 数値の拡張プロパティ
    val Int.isEven: Boolean
        get() = this % 2 == 0
    
    val Int.isOdd: Boolean
        get() = this % 2 == 1
    
    val Int.isPrime: Boolean
        get() {
            if (this <= 1) return false
            if (this <= 3) return true
            if (this % 2 == 0 || this % 3 == 0) return false
            
            var i = 5
            while (i * i <= this) {
                if (this % i == 0 || this % (i + 2) == 0) return false
                i += 6
            }
            return true
        }
}

fun main() {
    // 基本的な拡張プロパティの使用
    val text = "Hello"
    println("最後の文字: ${text.lastChar}")
    println("最初の文字: ${text.firstChar}")
    println("中央の文字: ${text.middleChar}")
    
    // 計算プロパティの使用
    val palindrome = "level"
    val nonPalindrome = "hello"
    
    println("$palindrome は回文: ${palindrome.isPalindrome}")
    println("$nonPalindrome は回文: ${nonPalindrome.isPalindrome}")
    
    // カスタム拡張プロパティの使用
    val properties = StringProperties()
    val sentence = "Hello World"
    val email = "user@example.com"
    val phone = "03-1234-5678"
    
    println("単語数: ${sentence.wordCount}")
    println("有効なメールアドレス: ${email.isValidEmail}")
    println("有効な電話番号: ${phone.isValidPhoneNumber}")
    
    // 実践的な使用例
    val practical = PracticalProperties()
    
    // 日付の検証
    val validDate = "2024-03-15"
    val invalidDate = "2024-13-45"
    
    println("$validDate は有効な日付: ${validDate.isValidDate}")
    println("$invalidDate は有効な日付: ${invalidDate.isValidDate}")
    
    // 数値の検証
    val numbers = listOf(2, 3, 4, 5, 6, 7, 8, 9, 10)
    
    numbers.forEach { n ->
        println("$n は:")
        println("  偶数: ${n.isEven}")
        println("  奇数: ${n.isOdd}")
        println("  素数: ${n.isPrime}")
    }
    
    // 拡張プロパティの組み合わせ
    val text2 = "Hello World"
    println("テキストの分析:")
    println("  長さ: ${text2.length}")
    println("  最後の文字: ${text2.lastChar}")
    println("  単語数: ${text2.wordCount}")
    println("  回文: ${text2.isPalindrome}")
} 