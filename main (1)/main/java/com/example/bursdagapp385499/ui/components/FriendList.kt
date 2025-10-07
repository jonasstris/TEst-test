package com.example.bursdagapp385499.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.bursdagapp385499.ui.viewmodel.PersonViewmodel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.bursdagapp385499.data.Person
import java.time.format.DateTimeFormatter
import com.example.bursdagapp385499.R


@Composable
fun FriendList(
    modifier: Modifier = Modifier,
    vm: PersonViewmodel) {

    val personList by vm.person.collectAsState()
    val format = DateTimeFormatter.ofPattern("dd.MM.yyyy")//etter tips fra GPT

    var personToEdit by remember { mutableStateOf<Person?>(null) }
    var personToDelete by remember { mutableStateOf<Person?>(null) }

    LazyColumn (modifier = modifier
        .background(MaterialTheme.colorScheme.surface)) {
        items(personList) { person: Person ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("${person.name}",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyLarge)
                    Text("${person.phoneNumber}",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyMedium)
                    Text("${person.birthday.format(format)}",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyMedium)
                }

                Row {
                    IconButton(onClick = { personToEdit = person }) {
                        Icon(Icons.Default.Edit,
                            contentDescription = "edit",
                            tint = MaterialTheme.colorScheme.tertiary)
                    }
                    IconButton(onClick = { personToDelete = person }) {
                        Icon(Icons.Default.Delete,
                            tint = MaterialTheme.colorScheme.tertiary,
                            contentDescription = "delete")
                    }
                }
            }
            HorizontalDivider()
        }
    }

// 🟢 Edit dialog
personToEdit?.let { person ->
    Dialog(onDismissRequest = { personToEdit = null }) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 8.dp,
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            EditForm(
                person = person,
                vm = vm,
                onClose = { personToEdit = null },
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

// 🔴 Delete dialog
personToDelete?.let { person ->
    AlertDialog(
        onDismissRequest = { personToDelete = null },
        title = { Text(text= stringResource(R.string.delete_title),
            color = MaterialTheme.colorScheme.onSurface) },
        text = {
            DeleteForm(
                person = person,
                vm = vm,
                onClose = { personToDelete = null }
            )
        },
        confirmButton = {},
        containerColor = MaterialTheme.colorScheme.surface
    )

}
}

