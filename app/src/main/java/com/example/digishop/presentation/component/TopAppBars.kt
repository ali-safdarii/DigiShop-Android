package com.example.digishop.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.digishop.presentation.theme.white
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import com.example.digishop.R
import com.example.digishop.presentation.theme.primaryColor
import com.example.digishop.presentation.theme.summeryTopBarTextStyle


@Composable
private fun ForceRtlTopBar(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryByIdTopAppBar(scrollBehavior: TopAppBarScrollBehavior, title: String) {
    ForceRtlTopBar {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = title,
                )
            }, scrollBehavior = scrollBehavior,

            actions = {
                IconButton(
                    onClick = { /* Handle %ack navigation */ },
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Tune,
                        contentDescription = null,
                    )
                }
            }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = white
            )
        )
    }

}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DetailScreenTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior, onNavigateUp: () -> Unit, cartItemsCount: Int?,
    onNavigateToCart: () -> Unit
) {
        TopAppBar(navigationIcon = {
            ForceRtlTopBar {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Spacer(modifier = Modifier.weight(1f)) // Push the navigation icon to the right
                    IconButton(
                        onClick = { onNavigateUp() },
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = null,
                        )
                    }
                }
            }


        }, actions = {


            Box(modifier = Modifier.clickable {  }) {
                IconButton(onClick = onNavigateToCart) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_shopping_bag),
                        contentDescription = null
                    )
                }

                if (cartItemsCount != 0) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .size(16.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center,
                    ) {

                        Text(
                            text = "$cartItemsCount",
                            style = MaterialTheme.typography.bodySmall,
                            color = white,
                        )

                    }
                }


            }


            IconButton(onClick = { /* Handle leading action 2 */ }) {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder, contentDescription = null
                )
            }



            IconButton(onClick = { /* Handle leading action 1 */ }) {
                Icon(
                    imageVector = Icons.Outlined.MoreVert, contentDescription = null
                )
            }


        }, title = { }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = white
        ),


            //colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.White),
            scrollBehavior = scrollBehavior

        )



}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreenTopAppBar(scrollBehavior: TopAppBarScrollBehavior) {

    ForceRtlTopBar {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "پیشنهاد شگفت انگیز", style = summeryTopBarTextStyle
                )
            }, scrollBehavior = scrollBehavior,

            actions = {
                IconButton(
                    onClick = { /* Handle %ack navigation */ },
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Icon(
                        tint = white,
                        imageVector = Icons.Filled.Tune,
                        contentDescription = null,
                        /*    modifier = Modifier.size(
                                30.dp
                            )*/
                    )
                }
            }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = primaryColor
            )
        )

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreenTopAppBar(behavior: TopAppBarScrollBehavior, onNavigateUp: () -> Unit) {

    ForceRtlTopBar {
        TopAppBar(
            title = { Text("آدرس و زمان ارسال", style = MaterialTheme.typography.titleLarge) },

            navigationIcon = {
                IconButton(
                    onClick = { onNavigateUp() },
                    //  modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowForward,
                        contentDescription = null,
                    )
                }
            }, colors = TopAppBarDefaults.topAppBarColors(
                containerColor = white
            ),

            scrollBehavior = behavior
        )
    }


}



