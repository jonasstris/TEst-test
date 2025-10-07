package com.example.bursdagapp385499

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.bursdagapp385499.data.AppDatabase
import com.example.bursdagapp385499.repositories.PersonRepository
import com.example.bursdagapp385499.ui.components.NavigationGraph
import com.example.bursdagapp385499.ui.theme.BursdagApp385499Theme
import com.example.bursdagapp385499.ui.viewmodel.PersonViewModelFactory
import com.example.bursdagapp385499.ui.viewmodel.PersonViewmodel
import android.Manifest


class MainActivity : ComponentActivity() {

    private val askPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {isGranted: Boolean ->
        if (isGranted) {
            Log.d("SMS","Allowed to send")
        } else {
            Log.d("SMS","Not allowed to send sms")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)  //lager databasen her
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "person_db"
        ).build()

        //sjekker om man har tilgang til sms
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
            == PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("SMS","Allowed to send sms")
        } else
        {
            askPermission.launch(Manifest.permission.SEND_SMS)
        }

        val repository = PersonRepository(db.personDao())

        val factory = PersonViewModelFactory(repository, application)
        val viewModel = ViewModelProvider(this, factory).get(PersonViewmodel::class.java)

        //lager repository og viewmodel her
        enableEdgeToEdge()
        setContent {
            BursdagApp385499Theme {
                MyApp(vm = viewModel)
            }
        }
    }

    @Composable
    fun MyApp(modifier: Modifier = Modifier, vm: PersonViewmodel) {
        val navController = rememberNavController()
        NavigationGraph(navController = navController, vm = vm)
    }
}

