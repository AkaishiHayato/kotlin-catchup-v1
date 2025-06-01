/**
 * Kotlin のデータクラスとシールドクラスの基本的な使い方
 */

// データクラスの定義
data class User(
    val id: Int,
    val name: String,
    val email: String,
    var age: Int
)

// データクラスの継承
data class Student(
    val id: Int,
    val name: String,
    val email: String,
    var age: Int,
    val grade: Int,
    val school: String
) : User(id, name, email, age)

// シールドクラスの定義
sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
    object Loading : Result<Nothing>()
}

// シールドクラスの使用例
class UserRepository {
    fun getUser(id: Int): Result<User> {
        return if (id > 0) {
            Result.Success(User(id, "User$id", "user$id@example.com", 20))
        } else {
            Result.Error("無効なIDです")
        }
    }
}

// データクラスの分解宣言
fun processUser(user: User) {
    val (id, name, email, age) = user
    println("ID: $id")
    println("名前: $name")
    println("メール: $email")
    println("年齢: $age")
}

// データクラスのコピー
fun updateUserAge(user: User, newAge: Int): User {
    return user.copy(age = newAge)
}

// データクラスの比較
fun compareUsers(user1: User, user2: User) {
    println("同じユーザー: ${user1 == user2}")
    println("同じ参照: ${user1 === user2}")
}

fun main() {
    // データクラスの使用
    val user1 = User(1, "Taro", "taro@example.com", 25)
    val user2 = User(1, "Taro", "taro@example.com", 25)
    val user3 = User(2, "Jiro", "jiro@example.com", 30)
    
    // データクラスの自動生成メソッド
    println("toString: $user1")
    println("hashCode: ${user1.hashCode()}")
    println("equals: ${user1 == user2}")
    
    // データクラスのコピー
    val updatedUser = user1.copy(age = 26)
    println("更新後: $updatedUser")
    
    // データクラスの分解宣言
    val (id, name, email, age) = user1
    println("分解: id=$id, name=$name, email=$email, age=$age")
    
    // データクラスの比較
    compareUsers(user1, user2)  // 同じ内容
    compareUsers(user1, user3)  // 異なる内容
    
    // シールドクラスの使用
    val repository = UserRepository()
    
    // 成功ケース
    val result1 = repository.getUser(1)
    when (result1) {
        is Result.Success -> {
            val user = result1.data
            println("ユーザー取得成功: $user")
        }
        is Result.Error -> println("エラー: ${result1.message}")
        is Result.Loading -> println("読み込み中...")
    }
    
    // エラーケース
    val result2 = repository.getUser(-1)
    when (result2) {
        is Result.Success -> {
            val user = result2.data
            println("ユーザー取得成功: $user")
        }
        is Result.Error -> println("エラー: ${result2.message}")
        is Result.Loading -> println("読み込み中...")
    }
    
    // データクラスの継承
    val student = Student(1, "Hanako", "hanako@example.com", 15, 3, "東京高校")
    println("学生: $student")
    
    // データクラスのプロパティ変更
    student.age = 16
    println("年齢更新後: $student")
    
    // データクラスのコピーと分解宣言の組み合わせ
    val (studentId, studentName, studentEmail, studentAge, grade, school) = student
    println("""
        学生情報:
        ID: $studentId
        名前: $studentName
        メール: $studentEmail
        年齢: $studentAge
        学年: $grade
        学校: $school
    """.trimIndent())
} 