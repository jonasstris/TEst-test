package com.example.bursdagapp385499.ui.screens

import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.bursdagapp385499.ui.components.AddForm
import com.example.bursdagapp385499.ui.components.FriendList
import com.example.bursdagapp385499.ui.viewmodel.PersonViewmodel
import com.example.bursdagapp385499.R


@Composable
fun FrontScreen(navController: NavHostController, vm: PersonViewmodel) {

    //Styrer om man kan se input-felt
    var showAddForm by remember { mutableStateOf(false) }

    Scaffold { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .background(MaterialTheme.colorScheme.background)) {

            Text(text= stringResource(R.string.front_text),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(30.dp)
                    .align(Alignment.CenterHorizontally))

            Spacer(modifier = Modifier.height(2.dp))

            Row(modifier = Modifier.padding(innerPadding)) {
                IconButton(
                    onClick = { showAddForm = true }
                ) { Icon(Icons.Default.Add,
                    tint = MaterialTheme.colorScheme.secondary,
                    contentDescription = "Add Friend") }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                        navController.navigate("pref")
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.onSecondary,
                        containerColor = MaterialTheme.colorScheme.secondary)
                ) { Text(text= stringResource(R.string.pref_title)) }

            }

            Box { //må legge inn i en boks for å unngå at friendlist havner foran addform
                FriendList(vm = vm)
                if (showAddForm) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize()
                            .padding(4.dp),
                    ) {
                        AddForm(vm = vm, onClose = { showAddForm = false })
                    }
                }
            }


            }

        }
    }