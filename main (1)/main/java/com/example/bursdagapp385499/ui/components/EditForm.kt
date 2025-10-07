package com.example.bursdagapp385499.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.bursdagapp385499.data.Person
import com.example.bursdagapp385499.ui.viewmodel.PersonViewmodel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import com.example.bursdagapp385499.R


@Composable
fun EditForm(
    person: Person,
    vm: PersonViewmodel,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    var name by remember { mutableStateOf(person.name ?: "") }
    var numberStr by remember { mutableStateOf(person.phoneNumber.toString()) }
    var birthday by remember { mutableStateOf(person.birthday.format(formatter)) }

    Column(modifier = modifier.padding(8.dp)) {
        TextField(value = name,
            onValueChange = { name = it },
            label = { Text(text= stringResource(R.string.input_name)) },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedTextColor = MaterialTheme.colorScheme.onSurface
            )
        )
        TextField(value = numberStr,
            onValueChange = { numberStr = it },
            label = { Text("Telefonnummer") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedTextColor = MaterialTheme.colorScheme.onSurface
            )
        )
        TextField(value = birthday,
            onValueChange = { birthday = it },
            label = { Text("Bursdag (dd.MM.yyyy)") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedTextColor = MaterialTheme.colorScheme.onSurface
            ))

        Button(onClick = {
            try {
                val phone = numberStr.toIntOrNull() ?: throw IllegalArgumentException("Ugyldig telefonnummer")
                val parsed = LocalDate.parse(birthday, formatter)
                val updatedPerson = person.copy(name = name, phoneNumber = phone, birthday = parsed)
                vm.updatePerson(updatedPerson)
                onClose()
            } catch (e: Exception) {
                // TODO: vis snackbar/feilmelding
            }
        },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            )
            ) {
            Text(text= stringResource(R.string.save_button))
        }
    }
}