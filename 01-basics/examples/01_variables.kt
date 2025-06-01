/**
 * Kotlinの変数とデータ型の基本的な使い方
 */

// 変数の宣言
// var: 可変変数
// val: 不変変数（Javaのfinalに相当）
fun main() {
    // 型推論を使用した変数宣言
    var name = "Taro"  // String型
    val age = 25       // Int型
    
    // 明示的な型宣言
    var height: Double = 175.5
    val isStudent: Boolean = true
    
    // 変数の値の変更
    name = "Jiro"      // varで宣言した変数は変更可能
    // age = 26        // valで宣言した変数は変更不可（コンパイルエラー）
    
    // Null安全性
    var nullableName: String? = null  // 型の後に?を付けることでnullを許容
    var nonNullName: String = "Taro"  // 通常の変数はnullを許容しない
    
    // 安全呼び出し演算子（?.）
    println(nullableName?.length)  // nullの場合はnullを返す
    
    // Elvis演算子（?:）
    val length = nullableName?.length ?: 0  // nullの場合は0を返す
    
    // 非null表明演算子（!!）
    // nullableName!!.length  // nullの場合はNullPointerExceptionをスロー
    
    // 型変換
    val intNumber = 100
    val longNumber: Long = intNumber.toLong()  // 明示的な型変換が必要
    
    // 数値リテラル
    val binary = 0b1010        // 2進数
    val hex = 0xFF            // 16進数
    val long = 100L           // Long型
    val float = 100.0f        // Float型
    val double = 100.0        // Double型
    
    // 文字リテラル
    val char = 'A'
    
    // 文字列テンプレート
    println("名前: $name, 年齢: $age")
    println("身長: ${height}cm")
    
    // 複数行文字列
    val multiLine = """
        複数行の
        文字列を
        記述できます
    """.trimIndent()
    
    println(multiLine)
} 