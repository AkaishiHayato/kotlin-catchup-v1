/**
 * Kotlinの文字列操作の基本的な使い方
 */

fun main() {
    // 文字列の基本操作
    val str = "Hello, Kotlin!"
    
    // 文字列の長さ
    println("長さ: ${str.length}")
    
    // 文字列の結合
    val str1 = "Hello"
    val str2 = "Kotlin"
    val combined = str1 + " " + str2
    println("結合: $combined")
    
    // 文字列テンプレート
    val name = "Taro"
    val age = 25
    println("名前: $name, 年齢: $age")
    println("${name}は${age}歳です")
    
    // 複数行文字列
    val multiLine = """
        これは
        複数行の
        文字列です
    """.trimIndent()
    println(multiLine)
    
    // 文字列の部分取得
    val text = "Hello, Kotlin!"
    println("最初の5文字: ${text.substring(0, 5)}")
    println("最後の6文字: ${text.substring(text.length - 6)}")
    
    // 文字列の分割
    val csv = "apple,banana,orange"
    val fruits = csv.split(",")
    println("フルーツ: $fruits")
    
    // 文字列の置換
    val replaced = text.replace("Kotlin", "Java")
    println("置換後: $replaced")
    
    // 文字列のトリミング
    val padded = "  Hello, Kotlin!  "
    println("トリミング前: '$padded'")
    println("トリミング後: '${padded.trim()}'")
    
    // 大文字・小文字の変換
    println("大文字: ${text.uppercase()}")
    println("小文字: ${text.lowercase()}")
    
    // 文字列の比較
    val str3 = "Hello"
    val str4 = "hello"
    println("等しい: ${str3 == str4}")
    println("大文字小文字を無視して等しい: ${str3.equals(str4, ignoreCase = true)}")
    
    // 文字列の検索
    println("'o'の最初の位置: ${text.indexOf('o')}")
    println("'Kotlin'の位置: ${text.indexOf("Kotlin")}")
    
    // 文字列の開始・終了の確認
    println("'Hello'で始まる: ${text.startsWith("Hello")}")
    println("'!'で終わる: ${text.endsWith("!")}")
    
    // 文字列のパディング
    val number = "42"
    println("左パディング: ${number.padStart(5, '0')}")
    println("右パディング: ${number.padEnd(5, '*')}")
    
    // 文字列の繰り返し
    println("繰り返し: ${"Na ".repeat(3)}Batman!")
    
    // 文字列の空チェック
    val empty = ""
    val blank = "   "
    println("空文字列: ${empty.isEmpty()}")
    println("空白のみ: ${blank.isBlank()}")
    
    // 文字列のフォーマット
    val price = 1000
    val formatted = "価格: %,d円".format(price)
    println(formatted)
    
    // 文字列の正規表現
    val pattern = "\\d+".toRegex()
    val textWithNumbers = "商品番号: 12345, 価格: 1000円"
    val numbers = pattern.findAll(textWithNumbers).map { it.value }.toList()
    println("数字: $numbers")
} 