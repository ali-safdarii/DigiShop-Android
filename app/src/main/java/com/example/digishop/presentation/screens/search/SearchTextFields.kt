package com.example.digishop.presentation.screens.search

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.digishop.presentation.theme.MainAppTheme
import com.example.digishop.presentation.theme.backgroundColor
import com.example.digishop.presentation.theme.gray
import com.example.digishop.presentation.theme.skyBlueDark
import com.example.digishop.presentation.theme.white


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTextField(
    searchText: String,
    onValueChanged: (String) -> Unit,
    onSearchWithText: ((String) -> Unit)?,
    onSearch: (() -> Unit)?
) {

    // 1. Make sure that you can request focus when you need to, for example, when a user
    //    clicks the red 'x' to remove existing text.
    val searchFocusRequester = remember { FocusRequester() }

    // 2. Next, keep track of when the search icon is tapped; this is used later for an
    //    animation.
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    //    This animation makes the search icon bigger when it's pressed, to
    //    indicate a responsive design.
    val searchIconSize by animateFloatAsState(
        targetValue = 1.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )
        Surface(
            Modifier
                .fillMaxWidth()
                .background(color = white)
            // .padding(8.dp)
            ,
            shadowElevation = 12.dp,
            tonalElevation = 12.dp,
            color = white,
        ) {
            Column(
                Modifier
                    .wrapContentSize()
                    .padding(16.dp)
                    .background(color = backgroundColor, shape = RoundedCornerShape(size = 8.dp)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                OutlinedTextField(
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = backgroundColor,
                        cursorColor = skyBlueDark,
                        focusedBorderColor = skyBlueDark
                    ),
                    // 3. Add KeyboardOptions & KeyboardActions to change the keyboard enter
                    //    button into a 'Search' icon.
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            //  4. Always trim your search text, and require a minimum length.

                            onSearchWithText?.let {
                                it(searchText.trim())
                            } ?: onSearch?.let { it() }

                        }
                    ),
                    //  shape = RectangleShape,
                    modifier = Modifier
                        .height(56.dp)
                        .fillMaxWidth()
                        .focusRequester(searchFocusRequester),  // track when search icon is pressed.
                    value = searchText,
                    onValueChange = { onValueChanged(it) },
                    leadingIcon = {

                        Row {
                            if (searchText.isNotEmpty()) {
                                IconButton(onClick = {          // clear search text, and request focus
                                    onValueChanged("")
                                    searchFocusRequester.requestFocus()
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Clear",
                                        tint = gray
                                    )
                                }
                            }

                        }
                    },
                    trailingIcon = {
                        IconButton(
                            interactionSource = interactionSource,
                            colors = IconButtonDefaults.iconButtonColors(
                                disabledContentColor = gray,
                                contentColor = gray
                            ),
                            onClick = {
                                onSearchWithText?.let { it(searchText.trim()) }
                                    ?: onSearch?.let { it() }
                            }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search for products",
                                Modifier.scale(if (isPressed) searchIconSize else 1f) // pressed: make bigger
                            )
                        }
                    }
                )

            }
        }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchField() {

    var searchText by rememberSaveable {
        mutableStateOf("s")
    }

    MainAppTheme {
        SearchTextField(searchText = searchText,
            onValueChanged = { searchText = it },
            onSearchWithText = {}) {
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchFieldClose() {

    var searchText by rememberSaveable {
        mutableStateOf("sunflower oil")
    }

    MainAppTheme {
        SearchTextField(searchText = searchText,
            onValueChanged = { searchText = it },
            onSearchWithText = {}) {
        }
    }
}