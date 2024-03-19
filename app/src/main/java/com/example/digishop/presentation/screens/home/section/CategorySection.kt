package com.example.digishop.presentation.screens.home.section

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import coil.transform.CircleCropTransformation
import com.example.digishop.R
import com.example.digishop.data.remote.responses.product_response.Category
import com.example.digishop.presentation.component.NonlazyGrid
import com.example.digishop.presentation.theme.dividerColor


@Composable
 fun CategoryList(
    categories: List<Category>,
    onCategoryClick: (Category) -> Unit
) {
    val itemCount = categories.size


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NonlazyGrid(
            columns = 3, itemCount = itemCount, modifier = Modifier.padding(16.dp)
        ) { index ->
            val category = categories[index]
            val firstRow = index == 0
            val lastRow = index == categories.size - 1
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.White)
                    .wrapContentHeight()
                    .clickable {
                        onCategoryClick(category)
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {


                val requestBuilder = ImageRequest.Builder(LocalContext.current)
                    .data(category.imageSizes.indexArray.mediumImageUrl)
                    .scale(Scale.FIT)
                    .size(Size.ORIGINAL)
                    .crossfade(true)
                    .transformations(CircleCropTransformation())

                    .build()

                AsyncImage(
                    model = requestBuilder,
                    modifier = Modifier
                        //  .size(100.dp)
                        .padding(8.dp)
                        .border(1.dp, dividerColor, shape = CircleShape)
                        .shadow(2.dp, shape = CircleShape),
                    //  contentScale = ContentScale.Fit,
                    contentDescription = category.name,
                    error = painterResource(R.drawable.not_found),

                    )

                Spacer(
                    modifier = Modifier
                        .height(8.dp)
                        .fillMaxWidth()
                )

                Text(category.name, style = MaterialTheme.typography.labelMedium)


            }
        }
    }
}

