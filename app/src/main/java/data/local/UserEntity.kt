package data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val uid: String,
    val email: String,
    val name: String,
    val profileImagePath: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)