package com.example.bursdagapp385499.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.bursdagapp385499.ui.viewmodel.PersonViewmodel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import com.example.bursdagapp385499.R
import com.example.bursdagapp385499.data.Person

@Composable
fun AddForm (
    vm: PersonViewmodel,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }
    var birthday by remember { mutableStateOf("") }

    val format = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    Column(
        modifier = Modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally, // Sentrerer feltet
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(text= stringResource(R.string.input_name)) },
            modifier = Modifier
                .widthIn(min = 300.dp, max = 400.dp) // Minimum og maksimum bredde
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = number,
            onValueChange = { number = it },
            label = { Text(text= stringResource(R.string.input_number)) },
            modifier = Modifier
                .widthIn(min = 300.dp, max = 400.dp)
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = birthday,
            onValueChange = { birthday = it },
            label = { Text(text= stringResource(R.string.input_birthday)) },
            modifier = Modifier
                .widthIn(min = 300.dp, max = 400.dp)
                .padding(bottom = 8.dp)
        )

        Row {
            Button(onClick = onClose, modifier = Modifier.weight(3f)) {
                Text(text= stringResource(R.string.cancel_button))
            }

            Button(
                onClick = {
                    try {
                        val date = LocalDate.parse(birthday, format)
                        val newPerson =
                            Person(name = name, birthday = date, phoneNumber = number.toInt())
                        vm.addPerson(newPerson)
                        // Tøm feltene etterpå
                        name = ""
                        number = ""
                        birthday = ""
                        onClose() // skjul skjemaet igjen
                    } catch (e: Exception) {
                    }
                },
                modifier = Modifier.weight(3f)
            ) {
                Text(text= stringResource(R.string.save_button))
            }

        }
    }

}
