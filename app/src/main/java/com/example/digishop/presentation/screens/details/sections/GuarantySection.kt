package com.example.digishop.presentation.screens.details.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Store
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digishop.R
import com.example.digishop.presentation.theme.colorRating
import com.example.digishop.presentation.theme.dividerColor
import com.example.digishop.presentation.theme.gray
import com.example.digishop.presentation.theme.textSecondryColor
import com.example.digishop.presentation.theme.white
import java.util.UUID

@Composable
fun GuaranteesSection() {

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
                    .padding(12.dp),
                // horizontalAlignment = Alignment.End
            ) {

                Text(
                    "فروشنده",
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp, top = 8.dp),
                    style = MaterialTheme.typography.bodyLarge,
                )

                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(top = 24.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {


                        Row() {
                            Icon(
                                imageVector = Icons.Outlined.Store,
                                contentDescription = UUID.randomUUID().toString()
                            )
                            Text(
                                "ماکان تجارت آروین",
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

                    Row(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth(),
                        //   horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            " 70%",
                            modifier = Modifier.padding(start = 8.dp),
                            style = MaterialTheme.typography.bodySmall,
                            color = colorRating
                        )
                        Text(
                            "  رضایت  خریداران",
                            style = MaterialTheme.typography.bodySmall,
                            color = textSecondryColor
                        )

                        Spacer(
                            modifier = Modifier
                                .padding(8.dp)
                                .height(15.dp)
                                .width(1.dp)
                                .background(gray)
                        )


                        Text(
                            "عملکرد خیلی خوب",

                            style = MaterialTheme.typography.bodySmall,
                            color = textSecondryColor
                        )

                    }
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
                        painter = painterResource(id = R.drawable.shield_check_3917603),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        //tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        "ماکان تجارت آروین",
                        modifier = Modifier
                            .padding(start = 8.dp),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }

                Divider(
                    color = dividerColor,
                    thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 16.dp)
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = UUID.randomUUID().toString()
                    )
                    Text(
                        "ماکان تجارت آروین", modifier = Modifier
                            .padding(start = 8.dp),
                        fontSize = 15.sp
                    )
                }

            }

        }


}