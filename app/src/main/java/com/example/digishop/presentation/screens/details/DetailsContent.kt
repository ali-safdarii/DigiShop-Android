package com.example.digishop.presentation.screens.details

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digishop.presentation.theme.backgroundColor
import com.example.digishop.presentation.theme.gray
import com.example.digishop.presentation.theme.skyBlue
import com.example.digishop.presentation.theme.white
import com.example.digishop.domain.product.model.Product
import com.example.digishop.domain.product.model.ProductColor
import com.example.digishop.presentation.screens.details.sections.DetailsBottomSection
import com.example.digishop.presentation.screens.details.sections.CommentSection
import com.example.digishop.presentation.screens.details.sections.GuaranteesSection
import com.example.digishop.presentation.screens.details.sections.MetasAndReviewSection
import com.example.digishop.presentation.screens.details.sections.TopSection
import com.example.digishop.presentation.theme.*
import com.example.digishop.utils.parseColor

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailsContent(
    modifier: Modifier,
    state: DetailsState,
    navigateToMetaScreen: (Product) -> Unit,
    navigateToReviewScreen: (Product) -> Unit,
    navigateToGalleryScreen: (Product) -> Unit,
    onDeleteButton: () -> Unit,
    onDecrementButton: () -> Unit,
    onIncrementButton: () -> Unit,
    onAddButton: () -> Unit,
    onColorSelected: (color: ProductColor) -> Unit,
    product: Product

) {


    Column(
        modifier = modifier
    ) {

        LazyColumn(
            Modifier
                .fillMaxSize()
                .weight(1f)

        ) {


            item {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(backgroundColor)
                )


                TopSection(
                    toGalleryScreen = {
                        navigateToGalleryScreen(it)
                    },
                    product = product
                )


                ColorListSection(
                    product = product,
                    onColorSelected = onColorSelected
                )

              //  ColorList(colors = colorList, onColorSelected = onColorSelected)


                GuaranteesSection()
                MetasAndReviewSection(
                    product = product,
                    toReviewScreen = navigateToReviewScreen,
                    toMetaScreen = navigateToMetaScreen
                )
                //  CenterBodyContent(product)
                //  RelatedProductTitle()
                //  RelatedProductList {}

                CommentSection(product = product)

                Spacer(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .height(100.dp)
                )

            }
        } //End LazyColumn

        DetailsBottomSection(
            onAdd = onAddButton,
            onDelete = onDeleteButton,
            onDecrement = onDecrementButton,
            onIncrease = onIncrementButton,
            product = product,
            totalPrice = state.totalPrice,
            state = state,
        )

    }
}

@Composable
private fun ColorList(
    modifier: Modifier = Modifier,
    colors: List<ProductColor>,
    onColorSelected: (ProductColor) -> Unit
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
    ) {
        items(
            items = colors,
            key = { item -> item.id }
        ) {color ->

            TextChipWithIcon(
                item = color,
                onClick = {
                   onColorSelected(color)
                },
                isSelected = color.isSelected,

                text = color.colorName,
            )
        }
    }
}


@Composable
private fun ColorListSection(
    modifier: Modifier = Modifier,
    product: Product,
    onColorSelected: (ProductColor) -> Unit
) {

    val defaultColorId = product.defualtColorId
    val selectedIndex = remember {
        mutableIntStateOf(
            product.colors.indexOfFirst { it.id == defaultColorId }.coerceAtLeast(0)
        )
    }


    var colorName by remember { mutableStateOf(product.colors.getOrNull(selectedIndex.intValue)?.colorName ?: "") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(white)
    ) {


        Text(
            text = "رنگ : $colorName",
            Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyMedium,
        )

        LazyRow(
            modifier = modifier.fillMaxWidth(),
        ) {
            itemsIndexed(product.colors) { index, item ->
                TextChipWithIcon(
                    item = item,
                    onClick = {
                        selectedIndex.intValue = index
                        colorName = item.colorName
                        onColorSelected(item)
                    },
                    isSelected = selectedIndex.intValue == index,

                    text = item.colorName,
                )

            }
        }
    }


}


@Composable
private fun TextChipWithIcon(
    item: ProductColor,
    isSelected: Boolean,
    text: String,
    onClick: () -> Unit
) {
    // source = https://betterprogramming.pub/custom-jetpack-compose-chips-5609e742c54b


    val backgroundColor = parseColor(item.colorCode)



    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = white
        ),
        modifier = Modifier
            .padding(start = 8.dp, bottom = 8.dp),
        border = BorderStroke(width = 1.dp, color = if (isSelected) skyBlue else Color.Transparent)


    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable {
                    onClick()
                }
                .wrapContentSize()

        ) {


            Box(
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp, bottom = 8.dp, end = 0.dp)
                    .wrapContentSize()
                    .padding(PaddingValues(0.dp))
            ) {
                Box(modifier = Modifier
                    .size(20.dp)
                    .border(
                        width = if (item.colorCode == "#ffffff") 1.dp else 0.dp,
                        color = if (item.colorCode == "#ffffff") gray else Color.Transparent,
                        shape = CircleShape
                    )
                    .clip(CircleShape)
                    .background(backgroundColor)
                    .clickable { onClick() }
                ) {
                    if (isSelected)
                        Icon(
                            imageVector = Icons.Rounded.Check,
                            contentDescription = "Check_chip",
                            tint = if (item.colorCode == "#ffffff") Color.Black else Color.White,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(PaddingValues(2.dp))
                        )
                }
            }

            Spacer(modifier = Modifier.width(8.dp)) // Add spacer with margin
            Text(
                text = text,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 0.dp, end = 24.dp)
            )
        }
    }

}

