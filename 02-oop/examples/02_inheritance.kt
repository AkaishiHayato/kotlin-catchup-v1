/**
 * Kotlin の継承とインターフェースの基本的な使い方
 */

// インターフェースの定義
interface Animal {
    val name: String
    fun makeSound()
    fun move()
}

// 抽象クラスの定義
abstract class Pet(
    override val name: String
) : Animal {
    // 抽象メソッド
    abstract fun play()
    
    // 具象メソッド
    override fun move() {
        println("$name は歩きます")
    }
}

// クラスの継承
class Dog(
    name: String,
    val breed: String
) : Pet(name) {
    override fun makeSound() {
        println("$name はワンワン鳴きます")
    }
    
    override fun play() {
        println("$name はボールで遊びます")
    }
}

// 別のクラスの継承
class Cat(
    name: String,
    val color: String
) : Pet(name) {
    override fun makeSound() {
        println("$name はニャーニャー鳴きます")
    }
    
    override fun play() {
        println("$name は毛糸で遊びます")
    }
}

// インターフェースの実装
class Bird(
    override val name: String,
    val canFly: Boolean
) : Animal {
    override fun makeSound() {
        println("$name はチュンチュン鳴きます")
    }
    
    override fun move() {
        if (canFly) {
            println("$name は飛びます")
        } else {
            println("$name は歩きます")
        }
    }
}

// 複数のインターフェースの実装
interface Swimmer {
    fun swim()
}

interface Flyer {
    fun fly()
}

class Duck(
    override val name: String
) : Animal, Swimmer, Flyer {
    override fun makeSound() {
        println("$name はガーガー鳴きます")
    }
    
    override fun move() {
        println("$name は歩きます")
    }
    
    override fun swim() {
        println("$name は泳ぎます")
    }
    
    override fun fly() {
        println("$name は飛びます")
    }
}

// シールドクラス（継承を制限する）
sealed class Shape {
    class Circle(val radius: Double) : Shape()
    class Rectangle(val width: Double, val height: Double) : Shape()
    class Triangle(val base: Double, val height: Double) : Shape()
}

// シールドクラスの使用
fun calculateArea(shape: Shape): Double {
    return when (shape) {
        is Shape.Circle -> Math.PI * shape.radius * shape.radius
        is Shape.Rectangle -> shape.width * shape.height
        is Shape.Triangle -> shape.base * shape.height / 2
    }
}

fun main() {
    // 継承の使用
    val dog = Dog("ポチ", "柴犬")
    dog.makeSound()
    dog.move()
    dog.play()
    
    val cat = Cat("タマ", "三毛")
    cat.makeSound()
    cat.move()
    cat.play()
    
    // インターフェースの使用
    val bird = Bird("ピヨ", true)
    bird.makeSound()
    bird.move()
    
    // 複数のインターフェースの実装
    val duck = Duck("ドナルド")
    duck.makeSound()
    duck.move()
    duck.swim()
    duck.fly()
    
    // シールドクラスの使用
    val circle = Shape.Circle(5.0)
    val rectangle = Shape.Rectangle(4.0, 6.0)
    val triangle = Shape.Triangle(3.0, 4.0)
    
    println("円の面積: ${calculateArea(circle)}")
    println("長方形の面積: ${calculateArea(rectangle)}")
    println("三角形の面積: ${calculateArea(triangle)}")
    
    // 型チェックとキャスト
    val shapes = listOf(circle, rectangle, triangle)
    for (shape in shapes) {
        when (shape) {
            is Shape.Circle -> println("円: 半径 = ${shape.radius}")
            is Shape.Rectangle -> println("長方形: 幅 = ${shape.width}, 高さ = ${shape.height}")
            is Shape.Triangle -> println("三角形: 底辺 = ${shape.base}, 高さ = ${shape.height}")
        }
    }
} 