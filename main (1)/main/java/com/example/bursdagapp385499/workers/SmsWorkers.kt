package com.example.bursdagapp385499.workers

import android.content.Context
import android.content.pm.PackageManager
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.bursdagapp385499.data.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SmsWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            // Hent database og DAO
            val db = AppDatabase.getDatabase(applicationContext)
            val dao = db.personDao()

            // Hent lagret SMS-melding fra SharedPreferences
            val sharedPref =
                applicationContext.getSharedPreferences("birthday_prefs", Context.MODE_PRIVATE)
            val smsMessage = sharedPref.getString("smsMessage", "Gratulerer med dagen!")
                ?: "Gratulerer med dagen!"

            // Dagens dato
            val today = LocalDate.now()

            // Hent alle personer fra DB
            val allPersons = dao.getAllPersons().first() // suspend-funksjon i DAO

            for (person in allPersons) {
                try {
                    // Parse birthday-streng til LocalDate
                    val birthdayDate: LocalDate = person.birthday

                    // Sjekk om det er bursdag i dag
                    if (birthdayDate.dayOfMonth == today.dayOfMonth && birthdayDate.month == today.month) {

                        // Sjekk permisjon
                        if (ContextCompat.checkSelfPermission(
                                applicationContext,
                                android.Manifest.permission.SEND_SMS
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            // Få riktig SmsManager avhengig av Android-versjon
                            val smsManager = getSmsManager(applicationContext)
                            smsManager.sendTextMessage(
                                person.phoneNumber.toString(),
                                null,
                                smsMessage,
                                null,
                                null
                            )
                            Log.d("SMS", "Sent to ${person.name} (${person.phoneNumber})")
                        } else {
                            Log.d("SMS", "Permission not granted")
                        }
                    }

                } catch (e: Exception) {
                    Log.e("SMS", "Could not parse birthday for ${person.name}", e)
                }
            }

            Result.success()
        } catch (e: Exception) {
            Log.e("SMS", "Error in SmsWorker", e)
            Result.failure()
        }
    }


    private fun getSmsManager(context: Context): SmsManager {
        @Suppress("DEPRECATION")
        return SmsManager.getDefault()
    }
}

