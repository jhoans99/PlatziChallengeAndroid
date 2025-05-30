package com.sebas.platzichallenge.features.search.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun DialogSaveFavorite(
    category: String,
    onDismiss: () -> Unit,
    onSaveMovie: (String) -> Unit
) {
    var categoryInput by remember {
        mutableStateOf(category)
    }
    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        title = { Text("Guardar favorito") },
        text = {
            Column {
                Text("Ingresa la categoría para guardar:")
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = categoryInput,
                    onValueChange = {
                        categoryInput = it
                    },
                    placeholder = { Text("Categoría") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onSaveMovie(categoryInput)
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text("Cancelar")
            }
        }
    )
}
