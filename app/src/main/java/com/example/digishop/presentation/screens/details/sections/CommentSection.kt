package com.example.digishop.presentation.screens.details.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.ContactSupport
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.digishop.domain.product.model.Product
import com.example.digishop.data.remote.responses.product_response.Comment
import com.example.digishop.presentation.theme.dividerColor
import com.example.digishop.presentation.theme.gray
import com.example.digishop.presentation.theme.skyBlue
import com.example.digishop.presentation.theme.textSecondryColor
import com.example.digishop.presentation.theme.white
import java.util.UUID

@Composable
 fun CommentSection(product: Product) {

        Surface(
            modifier = Modifier
                .padding(top = 12.dp)
                .wrapContentHeight()
                .fillMaxWidth(),
            color = white

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier.weight(1f), // This will take up the available space to push the text to the right
                        contentAlignment = Alignment.CenterStart // Align text to the start (right)
                    ) {
                        Text(
                            "دیدگاه کاربران",
                            modifier = Modifier
                                .padding(start = 8.dp, end = 8.dp, top = 8.dp),
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }

                    if (product.comments.isNotEmpty()) {
                        Text(
                            "${product.comments.size} نظر ",
                            modifier = Modifier
                                .padding(start = 8.dp, end = 8.dp, top = 8.dp),
                            style = MaterialTheme.typography.labelMedium,
                            color = skyBlue
                        )
                    }
                }


                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                )

                LazyRow {
                    items(
                        items = product.comments,
                        key = { comment ->
                            comment.id
                        }
                    ) { comment ->

                        CommentItems(comment)

                    }
                }

                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(top = 24.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {


                        Row {
                            Icon(
                                imageVector = Icons.Outlined.ChatBubbleOutline,
                                contentDescription = UUID.randomUUID().toString()
                            )
                            Text(
                                "دیدگاه خود را درباره کالا بنویسید",
                                modifier = Modifier.padding(start = 8.dp),
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }

                        Icon(
                            imageVector = Icons.Outlined.ArrowBackIosNew,
                            contentDescription = UUID.randomUUID().toString(),
                            tint = gray,
                            modifier = Modifier.size(16.dp)
                        )


                    }


                    Text(
                        "۵ امتیاز دیجی کلاب پس از تایید شدن دیدگاه، با رفتن به صفحه ماموریت های دیجی کلاب امتیازتان را دریافت کنید",
                        modifier = Modifier.padding(start = 8.dp, top = 6.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = textSecondryColor

                    )
                }

                Divider(
                    color = dividerColor, // You can specify the color of the divider
                    thickness = 1.dp,   // You can specify the thickness of the divider
                    modifier = Modifier
                        .fillMaxWidth() // Make the divider span the full width
                        .padding(all = 16.dp)
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.ContactSupport,
                        contentDescription = UUID.randomUUID().toString()
                    )
                    Text(
                        "پرسش و پاسخ",
                        modifier = Modifier
                            .padding(start = 8.dp),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }

                Spacer(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .height(6.dp)
                )

            }
        }


}


@Composable
private fun CommentItems(comment: Comment) {
    Card(
        modifier = Modifier
            .padding(all = 12.dp)
            .width(300.dp)
            .height(200.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(PaddingValues(8.dp)),
        ) {
            val (textTitle, textBody, textDate, textuserName) = createRefs()

            Text(
                text = comment.title ?: " بدون عنوان ",
                modifier = Modifier.constrainAs(textTitle) {
                    top.linkTo(parent.top, margin = 8.dp)
                    start.linkTo(parent.start, margin = 8.dp)
                },
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = "این یک متن ساختی است",
                modifier = Modifier.constrainAs(textBody) {
                    top.linkTo(textTitle.bottom, margin = 16.dp)
                    //    bottom.linkTo(textDate.top, margin = 8.dp)
                    start.linkTo(parent.start, margin = 8.dp)
                },
                style = MaterialTheme.typography.labelMedium,

                )

            Text(
                text = "سه ماه پیش",
                modifier = Modifier.constrainAs(textDate) {
                    bottom.linkTo(parent.bottom, margin = 8.dp)
                    start.linkTo(parent.start, margin = 8.dp)
                },
                style = MaterialTheme.typography.labelSmall,
                color = textSecondryColor

            )

            Text(
                text = "نام کاربری",
                modifier = Modifier.constrainAs(textuserName) {
                    bottom.linkTo(parent.bottom, margin = 8.dp)
                    start.linkTo(textDate.end, margin = 8.dp)
                },
                style = MaterialTheme.typography.labelSmall,
                color = textSecondryColor

            )

        }

    }
}