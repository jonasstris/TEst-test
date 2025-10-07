package com.example.bursdagapp385499.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.bursdagapp385499.data.Person
import com.example.bursdagapp385499.repositories.PersonRepository
import com.example.bursdagapp385499.workers.SmsWorker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Calendar
import java.util.concurrent.TimeUnit

class PersonViewmodel(
    private val repository: PersonRepository,
    application: Application
) : AndroidViewModel(application) {

    val person = repository.allPersons.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val sharedPref = application.getSharedPreferences("birthday_prefs", Context.MODE_PRIVATE)

    private val _smsMessage = MutableStateFlow(sharedPref.getString("smsMessage", "Gratulerer med dagen!") ?: "Gratulerer med dagen!")
    val smsMessageFlow: StateFlow<String> get() = _smsMessage

    fun saveSmsMessage(message: String) {
        _smsMessage.value = message
        sharedPref.edit().putString("smsMessage", message).apply()
    }

    fun deleteSmsMessage() {
        _smsMessage.value = ""
        sharedPref.edit().remove("smsMessage").apply()
    }

    // SMS aktivert
    var smsChecked: Boolean
        get() = sharedPref.getBoolean("smsChecked", false)
        set(value) {
            sharedPref.edit().putBoolean("smsChecked", value).apply()
            if (value) startSMS(getApplication()) else stopSMS(getApplication())
        }


    fun startSMS(context: Context) {
        val workManager = WorkManager.getInstance(context)
        val smsWorkRequest = PeriodicWorkRequestBuilder<SmsWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(calculateInitialDelay(), TimeUnit.MILLISECONDS)
            .build()
        workManager.enqueueUniquePeriodicWork(
            "daily_sms",
            ExistingPeriodicWorkPolicy.REPLACE,
            smsWorkRequest
        )
    }

    fun stopSMS(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork("daily_sms")
    }

    private fun calculateInitialDelay(): Long {
        val now = Calendar.getInstance()
        val due = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 9)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }
        if (due.before(now)) due.add(Calendar.DAY_OF_MONTH, 1)
        return due.timeInMillis - now.timeInMillis
    }

    // Person CRUD
    fun addPerson(person: Person) = viewModelScope.launch { repository.insert(person) }
    fun updatePerson(person: Person) = viewModelScope.launch { repository.update(person) }
    fun deletePerson(person: Person) = viewModelScope.launch { repository.delete(person) }
}

class PersonViewModelFactory(
    private val repository: PersonRepository,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PersonViewmodel::class.java)) {
            return PersonViewmodel(repository=repository, application=application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}