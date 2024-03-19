package com.example.digishop.presentation.screens.details.meta

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.digishop.presentation.component.LoadingState
import com.example.digishop.presentation.component.bottomBorder
import com.example.digishop.presentation.screens.details.DetailsViewModel
import com.example.digishop.presentation.theme.dividerColor
import com.example.digishop.presentation.theme.textSecondryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MetaScreen(
    onBackPressed: () -> Unit,
    viewModel: DetailsViewModel = hiltViewModel()
) {

    val state by viewModel.viewState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()



    if (state.isLoading)
        LoadingState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .nestedScroll(scrollBehavior.nestedScrollConnection),


        ) {
        item {
            MetaToolbar(onBack = onBackPressed, scrollBehavior)
        }

        item {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
            )
            Text(
                text = "مشخصات",
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Right,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            )


        }
        itemsIndexed(state.product?.metas ?: emptyList()) { index, item ->

            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = item.metaKey,
                        modifier = Modifier
                            .padding(start = 24.dp, end = 12.dp)
                            .weight(.4f),
                        style = MaterialTheme.typography.bodySmall,
                        color = textSecondryColor

                    )
                    Text(
                        text = item.metaValue,
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp)
                            .weight(.6f)
                            .padding(PaddingValues(16.dp))
                            .bottomBorder(1.dp, color = dividerColor)
                            .padding(bottom = 8.dp),
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,

                        )
                }

            }

        }
    }

}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun MetaToolbar(
    onBack: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        TopAppBar(
            navigationIcon = {

                IconButton(
                    onClick = { onBack() },
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                    )
                }


            },
            title = {
                Text(
                    text = "مشخصات فنی",
                    style = MaterialTheme.typography.titleLarge,
                    )
            },
            scrollBehavior = scrollBehavior,
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Color.White
            )

        )
    }
}


@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(),

    ) {
    Text(
        text = text,
        modifier = modifier
            .weight(weight)
            .padding(8.dp),
        style = textStyle,

        )
}