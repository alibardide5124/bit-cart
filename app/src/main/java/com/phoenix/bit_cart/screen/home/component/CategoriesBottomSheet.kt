package com.phoenix.bit_cart.screen.home.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CategoriesBottomSheet(
    initialCategory: String,
    categories: List<String>,
    onClickApply: (String) -> Unit
) {
    var selectedCategory by remember { mutableStateOf(initialCategory) }

    Column(Modifier.fillMaxWidth().padding(16.dp)) {
        CategoryItem(
            modifier = Modifier.fillMaxWidth(),
            name = "All Categories",
            isSelected = selectedCategory.isBlank(),
            onSelect = { selectedCategory = "" }
        )
        HorizontalDivider(Modifier.padding(vertical = 12.dp))
        categories.forEach {
            CategoryItem(
                modifier = Modifier.fillMaxWidth(),
                name = it,
                isSelected = it == selectedCategory,
                onSelect = { selectedCategory = it }
            )
        }
        Button(
            onClick = { onClickApply(selectedCategory) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Apply",
                fontSize = 18.sp
            )

        }
    }
}

@Composable
private fun CategoryItem(
    modifier: Modifier = Modifier,
    name: String,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Text(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .then(
                if (isSelected)
                    Modifier.border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onBackground,
                        shape = RoundedCornerShape(8.dp)
                    ) else Modifier
            )
            .clickable { onSelect() }
            .padding(vertical = 12.dp, horizontal = 16.dp),
        text = name,
        fontSize = 18.sp,
    )
}