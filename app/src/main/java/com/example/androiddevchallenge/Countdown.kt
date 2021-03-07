package com.example.androiddevchallenge

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieAnimationSpec
import com.airbnb.lottie.compose.rememberLottieAnimationState
import com.example.androiddevchallenge.ui.theme.MyTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CountdownViewModel : ViewModel() {

    private val _duration = MutableLiveData<Int>()
    val duration: LiveData<Int> = _duration

    fun setInitialDuration(duration: Int) {
        viewModelScope.launch {
            for(currentValue in duration downTo 0) {
                _duration.value = currentValue
                delay(1000)
            }
        }
    }
}

@Composable
fun CountDown(viewModel: CountdownViewModel) {
    val observedDuration by viewModel.duration.observeAsState()

    Column {
        Loader()
        Text(observedDuration.toString())
    }
}

@Composable
fun Loader() {
    val animationSpec = remember { LottieAnimationSpec.RawRes(R.raw.loading_loop_animation_7743) }
    val animationState =
        rememberLottieAnimationState(autoPlay = true, repeatCount = Integer.MAX_VALUE)
    LottieAnimation(
        animationSpec,
        animationState = animationState,
        modifier = Modifier.size(100.dp)
    )
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
private fun LightPreview() {
    MyTheme {
        CountDown(viewModel())
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
private fun DarkPreview() {
    MyTheme(darkTheme = true) {
        CountDown(viewModel())
    }
}