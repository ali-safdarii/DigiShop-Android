package com.example.digishop.presentation.screens.auth.login_register.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.digishop.presentation.theme.darkGray
import com.example.digishop.presentation.theme.skyBlueDark
import com.example.digishop.presentation.theme.textSecondryColor

@Composable
fun AuthInputField(
    modifier: Modifier = Modifier,
    //hintText:String,
    labelText: String,
    leadingIcon: ImageVector,
    textValue: String,
    keyboardType: KeyboardType = KeyboardType.Ascii,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    tintColor: Color,
    onValueChanged: (String) -> Unit,
    trailingIcon: ImageVector? = null,
    onTrailingIconClick: (() -> Unit)?,
) {

    OutlinedTextField(
        colors = OutlinedTextFieldDefaults.colors(
            //   unfocusedBorderColor = backgroundColor,
            cursorColor = skyBlueDark,
            focusedBorderColor = skyBlueDark
        ),
        modifier = modifier,
        value = textValue,
        onValueChange = onValueChanged,
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                tint = tintColor
            )
        },

        trailingIcon = {
            if (trailingIcon != null) {
                Icon(
                    imageVector = trailingIcon,
                    contentDescription = null,
                    tint = tintColor,
                    modifier = Modifier
                        .clickable {
                            if (onTrailingIconClick != null) onTrailingIconClick()
                        }
                )
            }
        },
        placeholder = {
            Text(
                text = labelText,
                style = MaterialTheme.typography.labelLarge,
                color = textSecondryColor
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = visualTransformation
    )

}


@Preview(showBackground = true)
@Composable
fun CustomTextFieldPreview() {
    AuthInputField(
        modifier = Modifier.fillMaxWidth(),
        labelText = "Enter Password",
        leadingIcon = Icons.Outlined.Key,
        textValue = "TextInput",
        tintColor = darkGray,
        trailingIcon = Icons.Outlined.Visibility,
        onValueChanged = {},
        visualTransformation = PasswordVisualTransformation(),
        onTrailingIconClick = {},
    )
}