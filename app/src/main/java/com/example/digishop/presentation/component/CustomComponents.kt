package com.example.digishop.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digishop.R
import com.example.digishop.presentation.theme.darkGray
import com.example.digishop.presentation.theme.skyBlue
import com.example.digishop.presentation.theme.yellow


@Preview
@Composable
fun CustomRatingBar() {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .border(
                border = BorderStroke(0.dp, Color.White),
                shape = RoundedCornerShape(16.dp),

                )
            .background(Color.White)
            .wrapContentSize()
            .padding(PaddingValues(horizontal = 12.dp)),
        contentAlignment = Alignment.Center

    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.wrapContentSize()

        ) {
            Text(
                text = "4.5",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W600
                ),
            )
            IconButton(
                onClick = { },
                modifier = Modifier
                    .padding(start = 4.dp)
                    .border(0.dp, Color.White, shape = CircleShape)
                    .size(24.dp),


                ) {
                Icon(
                    painter = painterResource(id = R.drawable.star_icon),
                    contentDescription = "content description",
                    modifier = Modifier
                        .size(24.dp)
                        .padding(4.dp),
                    tint = yellow


                )
            }


        }
    }
}


@Composable
fun ExpandedText(
    text: String,
    expandedText: String,
    expandedTextButton: String,
    shrinkTextButton: String,
    modifier: Modifier = Modifier,
    softWrap: Boolean = true,
    textStyle: TextStyle = LocalTextStyle.current,
    expandedTextStyle: TextStyle = LocalTextStyle.current,
    expandedTextButtonStyle: TextStyle = LocalTextStyle.current,
    shrinkTextButtonStyle: TextStyle = LocalTextStyle.current,
) {

    var isExpanded by remember { mutableStateOf(false) }

    val textHandler =
        "${if (isExpanded) expandedText else text} ${if (isExpanded) shrinkTextButton else expandedTextButton}"

    val annotatedString = buildAnnotatedString {
        withStyle(
            if (isExpanded) expandedTextStyle.toSpanStyle() else textStyle.toSpanStyle()
        ) {
            append(if (isExpanded) expandedText else text)
        }

        append("  ")

        withStyle(
            if (isExpanded) shrinkTextButtonStyle.toSpanStyle() else expandedTextButtonStyle.toSpanStyle()
        ) {
            append(if (isExpanded) shrinkTextButton else expandedTextButton)
        }

        addStringAnnotation(
            tag = "expand_shrink_text_button",
            annotation = if (isExpanded) shrinkTextButton else expandedTextButton,
            start = textHandler.indexOf(if (isExpanded) shrinkTextButton else expandedTextButton),
            end = textHandler.indexOf(if (isExpanded) shrinkTextButton else expandedTextButton) + if (isExpanded) expandedTextButton.length else shrinkTextButton.length
        )
    }

    ClickableText(
        text = annotatedString,
        softWrap = softWrap,
        modifier = modifier,
        onClick = {
            annotatedString
                .getStringAnnotations(
                    "expand_shrink_text_button",
                    it,
                    it
                )
                .firstOrNull()?.let { stringAnnotation ->
                    isExpanded = stringAnnotation.item == expandedTextButton
                }
        }
    )
}


@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int = 6,
    onOtpTextChange: (String, Boolean) -> Unit
) {
    LaunchedEffect(Unit) {
        if (otpText.length > otpCount) {
            throw IllegalArgumentException("Otp text value must not have more than otpCount: $otpCount characters")
        }
    }
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        BasicTextField(
            modifier = modifier,
            value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
            onValueChange = {
                if (it.text.length <= otpCount) {
                    onOtpTextChange.invoke(it.text, it.text.length == otpCount)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            decorationBox = {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(otpCount) { index ->
                        CharView(
                            index = index,
                            text = otpText
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        )
    }


}

@Composable
private fun CharView(
    index: Int,
    text: String
) {
    val isFocused = text.length == index
    val char = when {
        index == text.length -> ""
        index > text.length -> ""
        else -> text[index].toString()
    }

    Box(
        modifier = Modifier
            .size(50.dp)
            .border(
                1.dp, when {
                    isFocused -> skyBlue
                    else -> darkGray
                }, RoundedCornerShape(8.dp)
            )
            .padding(2.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = char,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }

}


@Composable
fun NonlazyGrid(
    columns: Int,
    itemCount: Int,
    modifier: Modifier = Modifier,
    content: @Composable() (Int) -> Unit
) {
    Column(modifier = modifier) {
        var rows = (itemCount / columns)
        if (itemCount.mod(columns) > 0) {
            rows += 1
        }

        for (rowId in 0 until rows) {
            val firstIndex = rowId * columns

            Row {
                for (columnId in 0 until columns) {
                    val index = firstIndex + columnId
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        if (index < itemCount) {
                            content(index)
                        }
                    }
                }
            }
        }
    }
}


// Custom layout to control the order of measurement
@Composable
fun CustomLayout(
    modifier: Modifier = Modifier,
    content: @Composable (Modifier) -> Unit
) {
    Layout(
        modifier = modifier,
        content = { content(Modifier.fillMaxSize()) }
    ) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }

        layout(constraints.maxWidth, constraints.maxHeight) {
            placeables.forEach { placeable ->
                placeable.place(0, 0)
            }
        }
    }
}
































