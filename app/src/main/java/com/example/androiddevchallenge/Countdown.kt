package com.example.androiddevchallenge

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieAnimationSpec
import com.airbnb.lottie.compose.rememberLottieAnimationState
import com.example.androiddevchallenge.ui.theme.MyTheme

@Composable
fun CountDown(viewModel: CountdownViewModel) {
    val observedDuration by viewModel.duration.observeAsState()

    Surface(color = MaterialTheme.colors.background) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = observedDuration.toString(),
                fontSize = 35.sp
            )
            Loader()
//            Spacer(modifier = Modifier.height(20.dp))
//            FancyButton(
//                text = "PAUSE",
//                onClick = {
//                    viewModel.playPause()
//                },
//                useGradient = false
//            )
            Spacer(modifier = Modifier.fillMaxHeight(fraction = .5f))
        }
    }
}

@Composable
fun Loader() {
    val animationSpec = remember { LottieAnimationSpec.RawRes(R.raw.loading_loop_animation_7743) }
    val animationState =
        rememberLottieAnimationState(autoPlay = true, repeatCount = Integer.MAX_VALUE)
    LottieAnimation(
        animationSpec,
        animationState = animationState
    )
}

@Preview(widthDp = 360, heightDp = 640)
@Composable
fun PreviewCountdown() {
    MyTheme {
        CountDown(viewModel())
    }
}