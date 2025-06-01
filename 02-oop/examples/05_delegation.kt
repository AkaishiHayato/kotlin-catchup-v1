/**
 * Kotlin のデリゲーションの基本的な使い方
 */

// プロパティデリゲーション
class LazyProperty {
    val lazyValue: String by lazy {
        println("初期化中...")
        "遅延初期化された値"
    }
}

// プロパティデリゲーションのカスタム実装
class ObservableProperty<T>(private var value: T) {
    private val observers = mutableListOf<(T) -> Unit>()
    
    fun addObserver(observer: (T) -> Unit) {
        observers.add(observer)
    }
    
    operator fun getValue(thisRef: Any?, property: kotlin.reflect.KProperty<*>): T {
        return value
    }
    
    operator fun setValue(thisRef: Any?, property: kotlin.reflect.KProperty<*>, newValue: T) {
        val oldValue = value
        value = newValue
        observers.forEach { it(newValue) }
    }
}

// クラスデリゲーション
interface Repository {
    fun getData(): String
    fun saveData(data: String)
}

class RepositoryImpl : Repository {
    override fun getData(): String = "データ"
    override fun saveData(data: String) {
        println("データを保存: $data")
    }
}

// デリゲーションを使用したリポジトリ
class LoggingRepository(private val repository: Repository) : Repository by repository {
    override fun getData(): String {
        println("データを取得中...")
        return repository.getData()
    }
}

// マップデリゲーション
class User(val map: Map<String, Any?>) {
    val name: String by map
    val age: Int by map
}

// プロパティデリゲーションの実装
class Delegate {
    private var value: String = ""
    
    operator fun getValue(thisRef: Any?, property: kotlin.reflect.KProperty<*>): String {
        println("${property.name}の値を取得")
        return value
    }
    
    operator fun setValue(thisRef: Any?, property: kotlin.reflect.KProperty<*>, newValue: String) {
        println("${property.name}の値を$newValueに設定")
        value = newValue
    }
}

// プロパティデリゲーションを使用するクラス
class Example {
    var delegatedProperty: String by Delegate()
    var observableProperty: String by ObservableProperty("初期値")
}

fun main() {
    // 遅延初期化の使用
    val lazyProperty = LazyProperty()
    println("最初のアクセス:")
    println(lazyProperty.lazyValue)
    println("2回目のアクセス:")
    println(lazyProperty.lazyValue)
    
    // カスタムプロパティデリゲーションの使用
    val example = Example()
    example.delegatedProperty = "新しい値"
    println(example.delegatedProperty)
    
    // 監視可能なプロパティの使用
    example.observableProperty.addObserver { newValue ->
        println("値が変更されました: $newValue")
    }
    example.observableProperty = "更新された値"
    
    // クラスデリゲーションの使用
    val repository = LoggingRepository(RepositoryImpl())
    println(repository.getData())
    repository.saveData("新しいデータ")
    
    // マップデリゲーションの使用
    val user = User(mapOf(
        "name" to "Taro",
        "age" to 25
    ))
    println("ユーザー: ${user.name}, 年齢: ${user.age}")
    
    // 複数のデリゲーションの組み合わせ
    class CombinedExample {
        val lazyValue: String by lazy { "遅延初期化" }
        var observableValue: String by ObservableProperty("初期値")
        var delegatedValue: String by Delegate()
    }
    
    val combined = CombinedExample()
    println(combined.lazyValue)
    combined.observableValue = "新しい値"
    combined.delegatedValue = "委譲された値"
    println(combined.delegatedValue)
    
    // デリゲーションの実装の確認
    val delegate = Delegate()
    var delegatedVar: String by delegate
    delegatedVar = "テスト"
    println(delegatedVar)
    
    // 複数のオブザーバーの使用
    val observable = ObservableProperty("初期値")
    observable.addObserver { println("オブザーバー1: $it") }
    observable.addObserver { println("オブザーバー2: $it") }
    observable.setValue(null, null, "新しい値")
} 