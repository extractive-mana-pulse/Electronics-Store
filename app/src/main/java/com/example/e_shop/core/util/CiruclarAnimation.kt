package com.example.e_shop.core.util

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlin.math.absoluteValue
import kotlin.math.sqrt

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalComposeUiApi::class,
    ExperimentalPagerApi::class
)
@Composable
fun AnimatedImageSlider(
    imageSlider: List<String?>,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { imageSlider.size }
    )
    var offsetY by remember { mutableFloatStateOf(0f) }

    Box(
        modifier
            .fillMaxSize()
            .padding(24.dp)
            .pointerInteropFilter {
                offsetY = it.y
                false
            }, contentAlignment = Alignment.Center
    ) {
        HorizontalPager(
            modifier = modifier
                .size(350.dp, 500.dp)
                .clip(RoundedCornerShape(16.dp)),
            state = pagerState
        ) { pageIndex ->
            Box {
                val pageOffset = pagerState.calculateOffsetForPage(pageIndex)

                AsyncImage(
                    modifier = Modifier
                        .graphicsLayer {
                            translationX = size.width * pageOffset

                            val endOffset = pagerState.endOffsetForPage(pageIndex)
                            shape = CirclePath(
                                progress = 1f - endOffset.absoluteValue,
                                origin = Offset(size.width, offsetY)
                            )
                            clip = true

                            val absoluteOffset =
                                pagerState.calculateOffsetForPage(pageIndex).absoluteValue
                            val scale = 1f + (absoluteOffset.absoluteValue * .3f)

                            scaleX = scale
                            scaleY = scale

                            val startOffset = pagerState.startOffsetForPage(pageIndex)
                            alpha = (2f - startOffset) / 2f
                        },
                    model = imageSlider[pageIndex],
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
        Box(
            modifier = modifier.padding(16.dp),
            contentAlignment = Alignment.BottomCenter,
        ) {
            Row(horizontalArrangement = Arrangement.Center) {
                repeat(imageSlider.size) { iteration ->
                    val color = if (pagerState.currentPage == iteration) Color.White else Color.Gray
                    Box(
                        modifier = modifier
                            .padding(4.dp)
                            .size(8.dp)
                            .background(color, CircleShape)
                    )
                }
            }
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun PagerState.calculateOffsetForPage(page: Int) = (currentPage - page) + currentPageOffsetFraction

@OptIn(ExperimentalFoundationApi::class)
fun PagerState.endOffsetForPage(page: Int): Float {
    return calculateOffsetForPage(page).coerceAtMost(0f)
}

@OptIn(ExperimentalFoundationApi::class)
fun PagerState.startOffsetForPage(page: Int): Float {
    return calculateOffsetForPage(page).coerceAtLeast(0f)
}

class CirclePath(private val progress: Float, private val origin: Offset = Offset(0f, 0f)) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val center = Offset(
            x = size.center.x - ((size.center.x - origin.x) * (1f - progress)),
            y = size.center.y - ((size.center.y - origin.y) * (1f - progress)),
        )
        val radius = (sqrt(
            size.height * size.height + size.width * size.width
        ) * .5f) * progress

        return Outline.Generic(
            Path().apply {
                addOval(
                    Rect(
                        center = center,
                        radius = radius,
                    )
                )
            }
        )
    }
}