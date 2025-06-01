/**
 * Kotlin のクラスとオブジェクトの基本的な使い方
 */

// 基本的なクラス定義
class Person(
    val name: String,  // プロパティの宣言（valは読み取り専用）
    var age: Int       // プロパティの宣言（varは読み書き可能）
) {
    // 初期化ブロック
    init {
        println("Person インスタンスが作成されました: $name")
    }
    
    // メソッドの定義
    fun introduce() {
        println("こんにちは、私は $name です。$age 歳です。")
    }
    
    // プロパティのゲッターとセッター
    var isAdult: Boolean
        get() = age >= 20
        set(value) {
            if (value) {
                age = 20
            }
        }
}

// プライマリコンストラクタとセカンダリコンストラクタ
class Student(
    val name: String,
    var age: Int
) {
    var grade: Int = 0
    var school: String = ""
    
    // セカンダリコンストラクタ
    constructor(name: String, age: Int, grade: Int) : this(name, age) {
        this.grade = grade
    }
    
    // 別のセカンダリコンストラクタ
    constructor(name: String, age: Int, grade: Int, school: String) : this(name, age, grade) {
        this.school = school
    }
    
    fun introduce() {
        println("私は $name です。$age 歳で、$grade 年生です。")
        if (school.isNotEmpty()) {
            println("$school に通っています。")
        }
    }
}

// 内部クラス
class Outer {
    private val outerValue = "外部クラスの値"
    
    inner class Inner {
        fun printOuterValue() {
            println("外部クラスの値: $outerValue")
        }
    }
}

// ネストされたクラス
class Outer2 {
    private val outerValue = "外部クラスの値"
    
    class Nested {
        // ネストされたクラスは外部クラスのプロパティにアクセスできない
        fun printMessage() {
            println("これはネストされたクラスです")
        }
    }
}

fun main() {
    // 基本的なクラスの使用
    val person = Person("Taro", 25)
    person.introduce()
    println("成人: ${person.isAdult}")
    
    // プロパティの変更
    person.age = 30
    println("年齢を変更: ${person.age}")
    
    // セカンダリコンストラクタの使用
    val student1 = Student("Jiro", 15, 3)
    student1.introduce()
    
    val student2 = Student("Hanako", 16, 2, "東京高校")
    student2.introduce()
    
    // 内部クラスの使用
    val outer = Outer()
    val inner = outer.Inner()
    inner.printOuterValue()
    
    // ネストされたクラスの使用
    val nested = Outer2.Nested()
    nested.printMessage()
    
    // プロパティの委譲
    val lazyValue: String by lazy {
        println("遅延初期化が実行されました")
        "遅延初期化された値"
    }
    
    println("最初のアクセス: $lazyValue")
    println("2回目のアクセス: $lazyValue")  // 2回目は初期化されない
    
    // オブジェクト式（無名クラス）
    val greeter = object {
        fun greet(name: String) {
            println("こんにちは、$name さん！")
        }
    }
    greeter.greet("Taro")
} 