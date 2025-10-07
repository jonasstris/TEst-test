package com.example.bursdagapp385499.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.bursdagapp385499.ui.viewmodel.PersonViewmodel
import com.example.bursdagapp385499.R
import kotlin.properties.ReadOnlyProperty

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrefScreen(navController: NavHostController, vm: PersonViewmodel, modifier: Modifier) {

    val context = LocalContext.current

    var smsCheckedState by remember { mutableStateOf(vm.smsChecked) }
    var message by remember { mutableStateOf("") }
    var deleteMessage by remember { mutableStateOf(false) }

    val smsMessage by  vm.smsMessageFlow.collectAsState()



    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                modifier= Modifier.background(MaterialTheme.colorScheme.background),
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigate("front") }
                    )
                    {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }},
                colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        navigationIconContentColor = MaterialTheme.colorScheme.secondary)
            )}
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {

            Text(
                text = stringResource(R.string.pref_text),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(8.dp)
            )

            Spacer(modifier.height(14.dp))

            HorizontalDivider()

            Spacer(modifier.height(10.dp))


            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = stringResource(R.string.sms_title),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.bodyMedium
                )

                Switch(
                    checked = smsCheckedState,
                    onCheckedChange = {
                        smsCheckedState = it
                        vm.smsChecked = it
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.onSecondary,
                        checkedTrackColor = MaterialTheme.colorScheme.tertiary,
                        uncheckedThumbColor = MaterialTheme.colorScheme.onSecondary,
                        uncheckedTrackColor = MaterialTheme.colorScheme.background
                    ),
                    modifier = Modifier.padding(4.dp)
                )

            }

            HorizontalDivider()

            OutlinedTextField(
                value = message,
                onValueChange = { message = it },
                modifier = Modifier.fillMaxWidth()
                        .padding(8.dp)
                        .height(100.dp)
                        .background(MaterialTheme.colorScheme.surface),
                placeholder = {Text(smsMessage)},
                )

                Row {
                    Button(
                        onClick = {
                            vm.saveSmsMessage(message)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    { Text(text = stringResource(R.string.save_button)) }

                    Button(
                        onClick = {
                            deleteMessage = true

                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    { Text(text = stringResource(R.string.delete_message)) }

                }

            if (deleteMessage) {
                DeleteMessage(vm = vm, context = context, onClose = { deleteMessage = false })


            }
        }
    }
}

@Composable
fun DeleteMessage(
    modifier: Modifier = Modifier,
    context: Context,
    vm: PersonViewmodel,
    onClose: () -> Unit) {

    Column(
        modifier = modifier
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {

        Text(
            text = stringResource(R.string.delete_sms),
            color = MaterialTheme.colorScheme.onSurface
        )

        Row {
            Button(onClick = {
                vm.deleteSmsMessage()
                onClose()},
                colors= ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                ),
                modifier = Modifier.weight(1f)
            ) { Text(text = stringResource(R.string.delete_button)) }

            Button(
                onClick = { onClose() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary),
                modifier = Modifier.weight(1f)
                ) { Text(text = stringResource(R.string.cancel_button)) }

        }
    }
}
