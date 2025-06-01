/**
 * Kotlin のレシーバー付き関数型の基本的な使い方
 */

// 基本的なレシーバー付き関数型
class StringBuilder {
    private val content = StringBuilder()
    
    fun build(block: StringBuilder.() -> Unit): String {
        block()
        return content.toString()
    }
    
    fun append(text: String) {
        content.append(text)
    }
    
    fun appendLine(text: String = "") {
        content.append(text).append("\n")
    }
}

// レシーバー付き関数型の使用例
class ReceiverExamples {
    fun demonstrateBasicReceiver() {
        val result = StringBuilder().build {
            append("Hello")
            append(", ")
            append("World")
        }
        println(result)
    }
    
    fun demonstrateNestedReceiver() {
        val result = StringBuilder().build {
            append("Outer")
            appendLine()
            append("  ")
            append("Inner")
        }
        println(result)
    }
}

// カスタム DSL の作成
class HtmlBuilder {
    private val content = StringBuilder()
    
    fun build(block: HtmlBuilder.() -> Unit): String {
        block()
        return content.toString()
    }
    
    fun tag(name: String, block: HtmlBuilder.() -> Unit) {
        content.append("<$name>")
        block()
        content.append("</$name>")
    }
    
    fun text(text: String) {
        content.append(text)
    }
}

// ビルダーパターンの実装
class PersonBuilder {
    private var name: String = ""
    private var age: Int = 0
    private var address: String = ""
    
    fun build(block: PersonBuilder.() -> Unit): Person {
        block()
        return Person(name, age, address)
    }
    
    fun name(value: String) {
        name = value
    }
    
    fun age(value: Int) {
        age = value
    }
    
    fun address(value: String) {
        address = value
    }
}

data class Person(
    val name: String,
    val age: Int,
    val address: String
)

// レシーバー付き関数型の実践的な使用例
class PracticalReceivers {
    // 設定ビルダー
    class ConfigBuilder {
        private val config = mutableMapOf<String, Any>()
        
        fun build(block: ConfigBuilder.() -> Unit): Map<String, Any> {
            block()
            return config
        }
        
        fun set(key: String, value: Any) {
            config[key] = value
        }
    }
    
    // メニュービルダー
    class MenuBuilder {
        private val items = mutableListOf<MenuItem>()
        
        fun build(block: MenuBuilder.() -> Unit): List<MenuItem> {
            block()
            return items
        }
        
        fun item(name: String, price: Int) {
            items.add(MenuItem(name, price))
        }
    }
    
    data class MenuItem(
        val name: String,
        val price: Int
    )
    
    // フォームビルダー
    class FormBuilder {
        private val fields = mutableListOf<FormField>()
        
        fun build(block: FormBuilder.() -> Unit): List<FormField> {
            block()
            return fields
        }
        
        fun textField(name: String, label: String) {
            fields.add(FormField(name, label, "text"))
        }
        
        fun numberField(name: String, label: String) {
            fields.add(FormField(name, label, "number"))
        }
        
        fun emailField(name: String, label: String) {
            fields.add(FormField(name, label, "email"))
        }
    }
    
    data class FormField(
        val name: String,
        val label: String,
        val type: String
    )
}

fun main() {
    // 基本的なレシーバー付き関数型の使用
    val examples = ReceiverExamples()
    examples.demonstrateBasicReceiver()
    examples.demonstrateNestedReceiver()
    
    // HTML DSL の使用
    val html = HtmlBuilder().build {
        tag("html") {
            tag("body") {
                tag("h1") {
                    text("Hello, World")
                }
                tag("p") {
                    text("This is a paragraph")
                }
            }
        }
    }
    println(html)
    
    // ビルダーパターンの使用
    val person = PersonBuilder().build {
        name("Taro")
        age(25)
        address("Tokyo")
    }
    println(person)
    
    // 実践的な使用例
    val practical = PracticalReceivers()
    
    // 設定ビルダーの使用
    val config = PracticalReceivers.ConfigBuilder().build {
        set("host", "localhost")
        set("port", 8080)
        set("debug", true)
    }
    println("設定: $config")
    
    // メニュービルダーの使用
    val menu = PracticalReceivers.MenuBuilder().build {
        item("コーヒー", 300)
        item("紅茶", 250)
        item("ケーキ", 400)
    }
    println("メニュー:")
    menu.forEach { item ->
        println("  ${item.name}: ${item.price}円")
    }
    
    // フォームビルダーの使用
    val form = PracticalReceivers.FormBuilder().build {
        textField("name", "名前")
        emailField("email", "メールアドレス")
        numberField("age", "年齢")
    }
    println("フォーム:")
    form.forEach { field ->
        println("  ${field.label} (${field.type}): ${field.name}")
    }
    
    // レシーバー付き関数型の組み合わせ
    val complexHtml = HtmlBuilder().build {
        tag("html") {
            tag("head") {
                tag("title") {
                    text("Complex Example")
                }
            }
            tag("body") {
                tag("div") {
                    tag("h1") {
                        text("User Information")
                    }
                    tag("p") {
                        text("Name: ${person.name}")
                    }
                    tag("p") {
                        text("Age: ${person.age}")
                    }
                    tag("p") {
                        text("Address: ${person.address}")
                    }
                }
            }
        }
    }
    println(complexHtml)
} 