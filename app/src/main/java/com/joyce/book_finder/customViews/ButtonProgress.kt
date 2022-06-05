package com.joyce.book_finder.customViews

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joyce.book_finder.theme.PRIMARY
import com.joyce.book_finder.theme.Shapes

@Composable
fun ButtonProgress(
    modifier: Modifier = Modifier,
    text: String = "",
    loadingText: String = "",
    isLoading: Boolean = false,
    shape: Shape = Shapes.medium,
    borderColor: Color = Color.LightGray,
    backgroundColor: Color = PRIMARY,
    progressIndicatorColor: Color = Color.White,
    onClicked: () -> Unit
) {

    Surface(
        modifier = modifier.clickable {
            onClicked()
        },
        shape = shape,
        border = BorderStroke(width = 1.dp, color = borderColor),
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .padding(
                    start = 12.dp,
                    end = 16.dp,
                    top = 12.dp,
                    bottom = 12.dp
                )
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Text(
                color = Color.White,
                text = if (isLoading) loadingText else text
            )
            if (isLoading) {
                Spacer(modifier = Modifier.width(16.dp))
                CircularProgressIndicator(
                    modifier = Modifier
                        .height(16.dp)
                        .width(16.dp),
                    strokeWidth = 2.dp,
                    color = progressIndicatorColor
                )
                onClicked()
            }
        }
    }
}

@Composable
@Preview
private fun GoogleButtonPreview() {
    ButtonProgress(
        text = "Sign Up with Google",
        loadingText = "Creating Account...",
        onClicked = {}
    )
}