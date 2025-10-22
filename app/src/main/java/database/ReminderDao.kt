package database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import model.Reminder

@Dao
interface ReminderDao {
    @Query("SELECT * FROM reminders ORDER BY date ASC, time ASC")
    fun getAllReminders(): Flow<List<Reminder>>
    
    @Insert
    suspend fun insertReminder(reminder: Reminder)
    
    @Update
    suspend fun updateReminder(reminder: Reminder)
    
    @Delete
    suspend fun deleteReminder(reminder: Reminder)
}
//hola