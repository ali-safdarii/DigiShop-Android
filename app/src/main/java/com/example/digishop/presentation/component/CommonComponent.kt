package com.example.digishop.presentation.component

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.ehsanmsz.mszprogressindicator.progressindicator.BallBeatProgressIndicator
import com.ehsanmsz.mszprogressindicator.progressindicator.BallSpinFadeLoaderProgressIndicator
import com.example.digishop.R
import com.example.digishop.data.remote.responses.product_response.Discount
import com.example.digishop.domain.cart.model.CartSummery
import com.example.digishop.presentation.theme.buttonPrimaryColor
import com.example.digishop.presentation.theme.primary500
import com.example.digishop.presentation.theme.primaryColor
import com.example.digishop.presentation.theme.skyBlue
import com.example.digishop.presentation.theme.textSecondryColor
import com.example.digishop.presentation.theme.white
import com.example.digishop.presentation.theme.zinc800
import com.example.digishop.utils.priceFormat


@Composable
fun LoadingState() {
    Box(
        Modifier
            .padding(16.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center

    ) {
        BallSpinFadeLoaderProgressIndicator(color = primary500)
    }
}


@Composable
fun QtyLoading(isLoading: Boolean, qty: Int, modifier: Modifier = Modifier) {
    AnimatedVisibility(visible = isLoading) {

        BallBeatProgressIndicator(
            modifier = modifier.padding(
                start = 2.dp,
                end = 2.dp
            ),
            color = primary500,
            ballCount = 3,
            maxBallDiameter = 12.dp,
        )
    }

    if (!isLoading)
        Text(
            text = qty.toString(),
            modifier = modifier.padding(start = 2.dp, end = 2.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = primaryColor
        )
}


@Composable
fun ErrorState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(white),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = R.drawable.undraw_cancel_re_pkdm,
            contentDescription = "Not_found_404",
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .size(250.dp)
                .padding(16.dp)
                .padding(16.dp)
        )

        Text(
            text = stringResource(id = R.string.err_unknown),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(start = 8.dp, top = 0.dp)
        )

    }
}

@Composable
fun EmptyState() {
    Box(
        Modifier
            .padding(16.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center

    ) {
        Text(
            text = stringResource(R.string.empty_list_message),
            style = MaterialTheme.typography.bodyLarge

        )
    }
}



@Composable
fun CustomHorizontalIndicator(
    modifier: Modifier = Modifier,
    pageCount: Int,
    currentPage: Int,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Center,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically

) {
    Row(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment
    ) {
        repeat(pageCount) { iteration ->
            val color = if (currentPage == iteration) Color.DarkGray else Color.LightGray
            val size by animateDpAsState(
                targetValue = if (iteration == currentPage) {
                    8.dp
                } else if (iteration > 3) {
                    4.dp
                } else {
                    6.dp
                }, label = ""
            )

            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .background(color, CircleShape)
                    .size(size)
            )

        }
    }
}

@Composable
fun CachedPagerImage(page: Int, imageUrl: String, content: @Composable (uri: Uri) -> Unit) {
    val imageUri = remember { mutableStateOf<Uri?>(null) }

    DisposableEffect(page) {
        // Load the image only if the page (index) has changed
        imageUri.value = imageUrl.toUri()
        onDispose {
            // Clean up when the composable is disposed
            imageUri.value = null
        }
    }

    val uri = imageUri.value

    if (uri != null) {
        content(uri)
    } else {
        // Placeholder or loading indicator
        LoadingState()
    }
}


@Composable
fun CBadgeBox(value: Int, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .width(35.dp)
            .height(22.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(buttonPrimaryColor),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = "${value}%",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            color = white,
        )
    }
}


@Composable
fun priceStyle(totalPrice: Int, fontSize: TextUnit = 16.sp): AnnotatedString {
    val annotatedString = buildAnnotatedString {

        withStyle(
            style = SpanStyle(
                fontFamily = FontFamily(
                    Font(R.font.vazirmatn_medium)
                ),
                fontSize = fontSize,
                fontWeight = FontWeight.Medium
            )
        ) {
            append(" ${priceFormat(totalPrice.toDouble())} ")
        }

        withStyle(
            style = SpanStyle(
                fontFamily = FontFamily(
                    Font(R.font.cinema)
                ),
                fontSize = 12.sp,
            )
        ) {
            append("تومان")
        }
    }
    return annotatedString
}


@Composable
fun PriceRow(discount: Discount?, modifier: Modifier = Modifier, originalPrice: Int) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(start = 12.dp, end = 12.dp)
            .fillMaxWidth()
        // .background(Color.Green),

    ) {
        // Discount Box
        if (discount != null && discount.percentage > 0) {
            CBadgeBox(
                value = discount.percentage,
                modifier.weight(.2f)
            )
        } else {
            // Spacer to fill the space
            Spacer(modifier = Modifier.width(35.dp))
        }

        // Spacer to maintain space
        Spacer(modifier = Modifier.width(8.dp))


        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End,
            modifier = modifier.weight(.8f)
            //   .background(Color.Yellow)
        ) {
            discount?.let {
                // Discount is available
                Text(
                    text = priceStyle(it.finalPrice),
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "$originalPrice",
                    style = MaterialTheme.typography.labelSmall,
                    color = textSecondryColor,
                    textDecoration = TextDecoration.LineThrough,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(end = 24.dp, top = 2.dp)
                )
            } ?: run {
                // No discount available
                Text(
                    text = priceStyle(originalPrice)
                )
            }
        }
    }
}

@Composable
fun TextIconButton(modifier: Modifier, onclick: () -> Unit, text: String) {
    TextButton(
        onClick = onclick,
        modifier = modifier
    ) {
        Text(
            text = text,
            color = skyBlue,
            style = MaterialTheme.typography.labelSmall,
            fontSize = 13.sp
        )

        Icon(
            imageVector = Icons.Outlined.KeyboardArrowLeft,
            tint = skyBlue,
            contentDescription = "",
            modifier = Modifier.size(20.dp),

            )
    }
}


@Composable
fun StickyBottom(
    onClick: () -> Unit,
    buttonText: String,
    label: String,
    cartSummery: CartSummery
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            8.dp
        ),
    ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                PrimaryButton(
                    onClick = onClick,
                    text = buttonText,
                    modifier = Modifier
                     //   .height(45.dp)
                        .weight(.4f)
                )


                Box(
                    modifier = Modifier
                        .weight(.6f),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Column(
                        modifier = Modifier.padding(end = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = label,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.labelSmall,
                            color = zinc800,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Thin
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = priceStyle(cartSummery.total)
                        )
                    }
                }
        }
    }
}