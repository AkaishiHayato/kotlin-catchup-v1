/**
 * Kotlin のコレクション操作の基本的な使い方
 */

// データクラスの定義
data class Student(
    val id: Int,
    val name: String,
    val grade: Int,
    val scores: List<Int>
)

// コレクション操作の使用例
class StudentManager {
    private val students = mutableListOf<Student>()
    
    fun addStudent(student: Student) {
        students.add(student)
    }
    
    // 変換操作
    fun getStudentNames(): List<String> {
        return students.map { it.name }
    }
    
    fun getStudentScores(): List<List<Int>> {
        return students.map { it.scores }
    }
    
    fun getFlattenedScores(): List<Int> {
        return students.flatMap { it.scores }
    }
    
    // フィルタリング
    fun getStudentsByGrade(grade: Int): List<Student> {
        return students.filter { it.grade == grade }
    }
    
    fun getHighScoringStudents(threshold: Int): List<Student> {
        return students.filter { it.scores.average() >= threshold }
    }
    
    // 集約操作
    fun getAverageScore(): Double {
        return students.flatMap { it.scores }.average()
    }
    
    fun getTotalScore(): Int {
        return students.flatMap { it.scores }.sum()
    }
    
    fun getMaxScore(): Int {
        return students.flatMap { it.scores }.maxOrNull() ?: 0
    }
    
    // グループ化
    fun getStudentsByGrade(): Map<Int, List<Student>> {
        return students.groupBy { it.grade }
    }
    
    // 並び替え
    fun getSortedStudents(): List<Student> {
        return students.sortedBy { it.name }
    }
    
    fun getStudentsSortedByScore(): List<Student> {
        return students.sortedByDescending { it.scores.average() }
    }
}

fun main() {
    val manager = StudentManager()
    
    // サンプルデータの追加
    manager.addStudent(Student(1, "Taro", 1, listOf(80, 85, 90)))
    manager.addStudent(Student(2, "Hanako", 1, listOf(95, 90, 85)))
    manager.addStudent(Student(3, "Jiro", 2, listOf(70, 75, 80)))
    manager.addStudent(Student(4, "Yoko", 2, listOf(85, 90, 95)))
    
    // 変換操作の使用
    println("学生の名前: ${manager.getStudentNames()}")
    println("学生のスコア: ${manager.getStudentScores()}")
    println("全スコア: ${manager.getFlattenedScores()}")
    
    // フィルタリングの使用
    println("1年生: ${manager.getStudentsByGrade(1)}")
    println("高得点者（85点以上）: ${manager.getHighScoringStudents(85)}")
    
    // 集約操作の使用
    println("平均点: ${manager.getAverageScore()}")
    println("合計点: ${manager.getTotalScore()}")
    println("最高点: ${manager.getMaxScore()}")
    
    // グループ化の使用
    println("学年ごとの学生: ${manager.getStudentsByGrade()}")
    
    // 並び替えの使用
    println("名前順: ${manager.getSortedStudents()}")
    println("スコア順: ${manager.getStudentsSortedByScore()}")
    
    // コレクション操作の組み合わせ
    val result = manager.getStudentsByGrade(1)
        .filter { it.scores.average() >= 85 }
        .map { it.name }
        .sorted()
    println("1年生の高得点者（名前順）: $result")
    
    // その他のコレクション操作
    val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    
    // パーティション
    val (even, odd) = numbers.partition { it % 2 == 0 }
    println("偶数: $even, 奇数: $odd")
    
    // グループ化と集約
    val groupedSum = numbers.groupBy { it % 3 }
        .mapValues { it.value.sum() }
    println("3で割った余りごとの合計: $groupedSum")
    
    // ウィンドウ操作
    val windows = numbers.windowed(3, 2, true)
    println("3要素のウィンドウ: $windows")
    
    // チャンク
    val chunks = numbers.chunked(3)
    println("3要素のチャンク: $chunks")
    
    // コレクションの変換
    val set = numbers.toSet()
    val array = numbers.toTypedArray()
    val map = numbers.associateWith { it * it }
    
    println("セット: $set")
    println("配列: ${array.joinToString()}")
    println("マップ: $map")
    
    // コレクションの検索
    val firstEven = numbers.first { it % 2 == 0 }
    val lastOdd = numbers.last { it % 2 == 1 }
    val anyGreaterThan5 = numbers.any { it > 5 }
    val allPositive = numbers.all { it > 0 }
    
    println("最初の偶数: $firstEven")
    println("最後の奇数: $lastOdd")
    println("5より大きい数は存在する: $anyGreaterThan5")
    println("すべて正の数: $allPositive")
    
    // コレクションの結合
    val list1 = listOf(1, 2, 3)
    val list2 = listOf(4, 5, 6)
    val combined = list1 + list2
    val zipped = list1.zip(list2)
    
    println("結合: $combined")
    println("zip: $zipped")
} 