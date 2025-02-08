package com.example.e_shop.core.extensions

import com.example.e_shop.R

data class CategoryInfo(
    val imageResource: Int,
    val displayName: String
)

val categoryMapping = mapOf(
    "laptop" to CategoryInfo(R.drawable.laptop, "Laptop"),
    "desktop" to CategoryInfo(R.drawable.desktop, "Desktop"),
    "tablet" to CategoryInfo(R.drawable.tablet, "Tablet"),
    "mobile" to CategoryInfo(R.drawable.smartphone, "Mobile"),
    "watch" to CategoryInfo(R.drawable.watch, "Watch"),
    "display" to CategoryInfo(R.drawable.nest_display, "Display")
)

fun getCategoryInfo(category: String): CategoryInfo {
    return categoryMapping[category.lowercase()] ?: CategoryInfo(
        R.drawable.ic_launcher_foreground,
        "Category not found"
    )
}