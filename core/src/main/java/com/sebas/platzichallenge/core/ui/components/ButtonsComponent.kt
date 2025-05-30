package com.sebas.platzichallenge.core.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sebas.platzichallenge.core.ui.theme.Green
import com.sebas.platzichallenge.core.ui.theme.OnPrimary

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    textButton: String,
    isEnable: Boolean = false,
    onClickAction: () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        enabled = isEnable,
        shape = RoundedCornerShape(12.dp),
        onClick = {
            onClickAction()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Green,
            contentColor = OnPrimary
        )
    ) {
        Text(textButton)
    }
}

@Composable
fun TextButton(
    modifier: Modifier = Modifier,
    textButton: String,
    onClickAction: () -> Unit
) {
    TextButton(
        onClick = {
            onClickAction()
        },
        modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Green
        )
    ) {
        Text(textButton)
    }
}
