package com.rafayet.oneuiisland.island

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight

@Composable
fun IslandAnimation(
    message: String = "OneUI Island",
    expanded: Boolean = false
) {
    val width by animateDpAsState(
        targetValue = if (expanded) 260.dp else 175.dp,
        animationSpec = spring(
            dampingRatio = 0.75f,
            stiffness = 300f
        ),
        label = "IslandWidth"
    )

    val height by animateDpAsState(
        targetValue = if (expanded) 85.dp else 42.dp,
        animationSpec = spring(
            dampingRatio = 0.75f,
            stiffness = 300f
        ),
        label = "IslandHeight"
    )

    Box(
        modifier = Modifier
            .width(width)
            .height(height)
            .background(
                Color.Black,
                RoundedCornerShape(30.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            color = Color.White,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
