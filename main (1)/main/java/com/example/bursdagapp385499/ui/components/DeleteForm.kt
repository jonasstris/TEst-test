package com.example.bursdagapp385499.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.bursdagapp385499.data.Person
import com.example.bursdagapp385499.ui.viewmodel.PersonViewmodel
import com.example.bursdagapp385499.R

@Composable
fun DeleteForm(
    person: Person,
    vm: PersonViewmodel,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier
        .padding(8.dp)
        .background(MaterialTheme.colorScheme.surface)) {

        Text(text= stringResource(R.string.delete_text) + " ${person.name ?: ""}?",
            color = MaterialTheme.colorScheme.onSurface)

        Spacer(Modifier.height(16.dp))

        Row {
            Button(onClick = {
                vm.deletePerson(person)
                onClose() },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary))
                {
                Text(text= stringResource(R.string.delete_button)) }

            Spacer(Modifier.width(8.dp))

            Button(onClick = { onClose() },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                )) {
                Text(text= stringResource(R.string.cancel_button))
            }
        }
    }
}