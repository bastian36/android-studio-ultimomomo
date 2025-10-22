package ui.reminders

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import database.AppDatabase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import model.Reminder

class RemindersViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val reminderDao = database.reminderDao()
    
    private val _reminders = MutableStateFlow<List<Reminder>>(emptyList())
    val reminders: StateFlow<List<Reminder>> = _reminders.asStateFlow()
    
    init {
        viewModelScope.launch {
            reminderDao.getAllReminders().collect {
                _reminders.value = it
            }
        }
    }
    //dejaste la caga aweonao
    
    fun addReminder(title: String, description: String, date: String, time: String) {
        viewModelScope.launch {
            val reminder = Reminder(
                title = title,
                description = description,
                date = date,
                time = time
            )
            reminderDao.insertReminder(reminder)
        }
    }
    
    fun deleteReminder(reminder: Reminder) {
        viewModelScope.launch {
            reminderDao.deleteReminder(reminder)
        }
    }
    
    fun updateReminder(reminder: Reminder) {
        viewModelScope.launch {
            reminderDao.updateReminder(reminder)
        }
    }
    
    fun toggleComplete(reminder: Reminder) {
        viewModelScope.launch {
            val updatedReminder = reminder.copy(isCompleted = !reminder.isCompleted)
            reminderDao.updateReminder(updatedReminder)
        }
    }
}