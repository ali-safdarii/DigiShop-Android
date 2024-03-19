package com.example.digishop.presentation.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(20),
    text: String,
) {
    Button(
        shape = shape,
        onClick = onClick,
        modifier = modifier.height(45.dp),
    ) {

        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium
        )

    }
}


@Composable
fun QtyButtonIcon(
    onClick: () -> Unit,
    enabled: Boolean = true,
    @DrawableRes icon: Int

) {

    IconButton(
        onClick = onClick,
        enabled = enabled,
      //  colors = IconButtonDefaults.iconButtonColors(disabledContainerColor = black)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "Add",
            tint = MaterialTheme.colorScheme.primary
        )
    }

}