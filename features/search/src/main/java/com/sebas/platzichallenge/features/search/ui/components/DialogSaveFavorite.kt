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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp


@Composable
fun DialogSaveFavorite(
    isUpdate: Boolean,
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
        title = { Text(
            if(!isUpdate)
            stringResource(
                com.sebas.platzichallenge.features.search.R.string.title_save_favorite
            )
            else
                stringResource(
                    com.sebas.platzichallenge.features.search.R.string.title_update_favorite
                )
        ) },
        text = {
            Column {
                Text(
                    stringResource(
                        com.sebas.platzichallenge.features.search.R.string.label_input_category
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = categoryInput,
                    onValueChange = {
                        categoryInput = it
                    },
                    placeholder = { Text(
                        stringResource(
                            com.sebas.platzichallenge.features.search.R.string.placeholder_category
                        )
                    ) },
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
                Text(
                    if(isUpdate)
                    stringResource(
                        com.sebas.platzichallenge.features.search.R.string.text_button_update
                    )
                    else
                        stringResource(
                            com.sebas.platzichallenge.features.search.R.string.text_button_save
                        )
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text(
                    stringResource(
                        com.sebas.platzichallenge.features.search.R.string.text_button_cancel
                    )
                )
            }
        }
    )
}
