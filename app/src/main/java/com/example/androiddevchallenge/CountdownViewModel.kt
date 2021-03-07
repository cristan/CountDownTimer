package com.example.androiddevchallenge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import java.text.DecimalFormat

class CountdownViewModel : ViewModel() {

    private val _duration = MutableLiveData<String>()
    val duration: LiveData<String> = _duration

    private var countDownJob: Job? = null

    fun setInitialDuration(duration: Int) = countDown(duration)

    private fun countDown(duration: Int) {
        countDownJob = viewModelScope.launch {
            for (currentValue in duration downTo 0) {
                // TODO: doesn't work for some reason
                val active = isActive
                if (active) {
                    _duration.value = formatTimePeriod(currentValue)
                    delay(1000)
                } else {
                    break
                }
            }
        }
    }

    private val twoDigitsFormat = DecimalFormat.getNumberInstance().apply {
        minimumIntegerDigits = 2
    }
    private fun formatTimePeriod(period: Int): String {
        val hours = period / 3600
        return if (hours != 0) {
            "${twoDigitsFormat.format(hours)}:${formatMinutesAndSeconds(period - (hours * 3600))}"
        } else {
            formatMinutesAndSeconds(period)
        }
    }

    private fun formatMinutesAndSeconds(period: Int): String {
        val minutes = period / 60
        val seconds = period - (minutes * 60)

        return "${twoDigitsFormat.format(minutes)}:${twoDigitsFormat.format(seconds)}"
    }

    fun playPause() {
        val job = countDownJob
        if (job != null) {
            job.cancel()
            countDownJob = null
        } else {
            countDown(11)
        }
    }
}