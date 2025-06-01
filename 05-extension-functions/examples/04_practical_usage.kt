/**
 * Kotlin の拡張関数の実践的な使用例
 */

// ユーティリティ関数
class StringUtils {
    fun String.toTitleCase(): String {
        return split(" ").joinToString(" ") { word ->
            word.lowercase().replaceFirstChar { it.uppercase() }
        }
    }
    
    fun String.toSlug(): String {
        return this
            .lowercase()
            .replace(Regex("[^a-z0-9\\s-]"), "")
            .replace(Regex("\\s+"), "-")
    }
    
    fun String.truncate(maxLength: Int): String {
        return if (length <= maxLength) this else "${substring(0, maxLength)}..."
    }
}

// コレクションの拡張
class CollectionUtils {
    fun <T> List<T>.second(): T? {
        return if (size >= 2) get(1) else null
    }
    
    fun <T> List<T>.penultimate(): T? {
        return if (size >= 2) get(size - 2) else null
    }
    
    fun <T> List<T>.swap(index1: Int, index2: Int): List<T> {
        val result = toMutableList()
        val temp = result[index1]
        result[index1] = result[index2]
        result[index2] = temp
        return result
    }
    
    fun <T> List<T>.splitAt(index: Int): Pair<List<T>, List<T>> {
        return Pair(take(index), drop(index))
    }
}

// Null 安全性との組み合わせ
class NullSafetyUtils {
    fun String?.orEmpty(): String {
        return this ?: ""
    }
    
    fun <T> List<T>?.orEmpty(): List<T> {
        return this ?: emptyList()
    }
    
    fun <T> T?.orElse(default: T): T {
        return this ?: default
    }
    
    fun <T> T?.ifNotNull(block: (T) -> Unit) {
        if (this != null) {
            block(this)
        }
    }
}

// カスタム DSL
class HtmlDsl {
    class Tag(val name: String) {
        private val attributes = mutableMapOf<String, String>()
        private val children = mutableListOf<Any>()
        
        fun attr(name: String, value: String) {
            attributes[name] = value
        }
        
        fun add(child: Any) {
            children.add(child)
        }
        
        override fun toString(): String {
            val attrs = attributes.entries.joinToString(" ") { (key, value) ->
                "$key=\"$value\""
            }
            val attrStr = if (attrs.isNotEmpty()) " $attrs" else ""
            
            return if (children.isEmpty()) {
                "<$name$attrStr/>"
            } else {
                val childrenStr = children.joinToString("")
                "<$name$attrStr>$childrenStr</$name>"
            }
        }
    }
    
    fun html(block: HtmlDsl.() -> Tag): String {
        return block().toString()
    }
    
    fun Tag.div(block: Tag.() -> Unit) {
        val div = Tag("div")
        block(div)
        add(div)
    }
    
    fun Tag.p(text: String) {
        add(Tag("p").apply { add(text) })
    }
    
    fun Tag.h1(text: String) {
        add(Tag("h1").apply { add(text) })
    }
    
    fun Tag.span(text: String) {
        add(Tag("span").apply { add(text) })
    }
}

// 実践的な使用例
class PracticalExamples {
    // 日付の拡張関数
    fun String.toDate(): java.time.LocalDate? {
        return try {
            java.time.LocalDate.parse(this)
        } catch (e: Exception) {
            null
        }
    }
    
    // 数値の拡張関数
    fun Int.toBinary(): String {
        return Integer.toBinaryString(this)
    }
    
    fun Int.toHex(): String {
        return Integer.toHexString(this)
    }
    
    // ファイルの拡張関数
    fun String.readFile(): String? {
        return try {
            java.io.File(this).readText()
        } catch (e: Exception) {
            null
        }
    }
    
    fun String.writeFile(content: String): Boolean {
        return try {
            java.io.File(this).writeText(content)
            true
        } catch (e: Exception) {
            false
        }
    }
}

fun main() {
    // ユーティリティ関数の使用
    val stringUtils = StringUtils()
    val title = "hello world"
    val slug = "Hello, World!"
    val longText = "This is a very long text that needs to be truncated"
    
    println("タイトルケース: ${title.toTitleCase()}")
    println("スラッグ: ${slug.toSlug()}")
    println("切り詰め: ${longText.truncate(20)}")
    
    // コレクションの拡張の使用
    val collectionUtils = CollectionUtils()
    val list = listOf(1, 2, 3, 4, 5)
    
    println("2番目の要素: ${list.second()}")
    println("最後から2番目の要素: ${list.penultimate()}")
    println("入れ替え: ${list.swap(0, 4)}")
    println("分割: ${list.splitAt(2)}")
    
    // Null 安全性との組み合わせの使用
    val nullSafetyUtils = NullSafetyUtils()
    val nullableString: String? = null
    val nullableList: List<Int>? = null
    
    println("空文字列: ${nullableString.orEmpty()}")
    println("空リスト: ${nullableList.orEmpty()}")
    println("デフォルト値: ${nullableString.orElse("default")}")
    
    nullableString.ifNotNull {
        println("Null でない: $it")
    }
    
    // カスタム DSL の使用
    val htmlDsl = HtmlDsl()
    val html = htmlDsl.html {
        Tag("html").apply {
            div {
                h1("Hello, World")
                p("This is a paragraph")
                span("This is a span")
            }
        }
    }
    println(html)
    
    // 実践的な使用例
    val practical = PracticalExamples()
    
    // 日付の変換
    val dateStr = "2024-03-15"
    val date = dateStr.toDate()
    println("日付: $date")
    
    // 数値の変換
    val number = 42
    println("2進数: ${number.toBinary()}")
    println("16進数: ${number.toHex()}")
    
    // ファイル操作
    val filename = "test.txt"
    val content = "Hello, World!"
    
    if (filename.writeFile(content)) {
        println("ファイルに書き込み成功")
        println("ファイルの内容: ${filename.readFile()}")
    } else {
        println("ファイルの書き込みに失敗")
    }
    
    // 拡張関数の組み合わせ
    val complexExample = "Hello, World!"
        .toTitleCase()
        .toSlug()
        .truncate(10)
    
    println("複合的な例: $complexExample")
    
    // 実践的な DSL の使用
    val userHtml = htmlDsl.html {
        Tag("html").apply {
            div {
                attr("class", "user-profile")
                h1("User Profile")
                div {
                    attr("class", "user-info")
                    p("Name: John Doe")
                    p("Age: 30")
                    p("Email: john@example.com")
                }
            }
        }
    }
    println(userHtml)
} 