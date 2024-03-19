package com.example.digishop.presentation.screens.auth.login_register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.digishop.R
import com.example.digishop.presentation.screens.auth.login_register.component.AuthInputField
import com.example.digishop.presentation.screens.auth.login_register.component.HorizontalDottedProgressBar
import com.example.digishop.presentation.theme.darkGray
import com.example.digishop.presentation.theme.textSecondryColor




@Composable
fun LoginRegsiterContent(
    emailValue: () -> String,
    passwordValue: () -> String,
    isLoading: () -> Boolean,
    buttonEnabled: () -> Boolean,
    onTrailingPasswordIconClick: () -> Unit,
    modifier: Modifier = Modifier,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLoginButtonClick: () -> Unit,
    isPasswordShown: () -> Boolean,
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
         Spacer(modifier = Modifier.height(32.dp))


        AsyncImage(
            model = R.drawable.undraw_sign_in,
            modifier = Modifier
                .padding(12.dp)
                .padding(8.dp),
            contentDescription = "Login_vector",
        )


        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "خوش آمدید",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .align(Alignment.Start),
            //  color = textSecondryColor
        )

        Text(
            text = "برای وررود ایمیل و رمز عبور خود را وارد نمایید",
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .align(Alignment.Start),
            color = textSecondryColor
        )

        Spacer(modifier = Modifier.height(24.dp))

        AuthInputField(
            modifier = Modifier
                .padding(horizontal = 2.dp)
                .fillMaxWidth(),
            labelText = stringResource(R.string.email_label),
            leadingIcon = Icons.Outlined.Email,
            tintColor = darkGray,
            trailingIcon = null,
            onTrailingIconClick = null,
            textValue = emailValue(),
            onValueChanged = onEmailChanged,
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
                .fillMaxWidth()
        )


        AuthInputField(
            modifier = Modifier
                .padding(horizontal = 2.dp)
                .fillMaxWidth(),
            textValue = passwordValue(),
            onValueChanged = onPasswordChanged,
            labelText = stringResource(R.string.password_label),
            leadingIcon = Icons.Outlined.Key,
            tintColor = darkGray,
            trailingIcon = Icons.Outlined.Visibility,
            onTrailingIconClick = onTrailingPasswordIconClick,
            visualTransformation = if (isPasswordShown()) {
                VisualTransformation.None
            } else PasswordVisualTransformation(),
            keyboardType = KeyboardType.Password
        )

        Spacer( modifier = Modifier .height(32.dp))


        Button(
            shape = RoundedCornerShape(20),
            onClick = onLoginButtonClick,
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .fillMaxWidth()
                .height(50.dp)
        ) {
            if (isLoading()) {
                HorizontalDottedProgressBar()
            } else {
                Text(
                    text = stringResource(R.string.login_button_label),
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 15.sp
                )
            }
        }


        Spacer(modifier = Modifier.height(100.dp))

    }


}


