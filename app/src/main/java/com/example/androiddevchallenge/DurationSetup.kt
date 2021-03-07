/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.cabaret
import com.example.androiddevchallenge.ui.theme.spindle

@Composable
fun DurationSetup(navController: NavHostController) {
    Surface(color = MaterialTheme.colors.background) {
        val (seconds, setSeconds) = rememberSaveable { mutableStateOf("") }
        val (minutes, setMinutes) = rememberSaveable { mutableStateOf("") }
        val (hours, setHours) = rememberSaveable { mutableStateOf("") }

        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {
                MyInputField(title = "Hours", text = hours, setText = setHours, .3333f)
                MyInputField(title = "Minutes", text = minutes, setText = setMinutes, .5f)
                MyInputField(title = "Seconds", text = seconds, setText = setSeconds, 1f)
            }
            Spacer(modifier = Modifier.height(20.dp))

            FancyButton(
                text = "START",
                onClick = {
                    val duration = seconds.toIntSafe() + (minutes.toIntSafe() * 60) +
                        (hours.toIntSafe() * 3600)
                    navController.navigate("countdown/$duration")
                }
            )
        }
    }
}

@Composable
fun FancyButton(
    text: String,
    useGradient: Boolean = true,
    onClick: () -> Unit = { },
) {
    Button(
        modifier = Modifier.clip(shape = RoundedCornerShape(percent = 50)),
        colors = ButtonDefaults.buttonColors(backgroundColor = if (useGradient) Color.Transparent else spindle),
        contentPadding = PaddingValues(),
        onClick = { onClick() },
    ) {
        val fancyGradient = Brush.horizontalGradient(listOf(Color(0xFFD73874), Color(0xFFE8636E)))
        val modifier = if (useGradient) Modifier.background(fancyGradient) else Modifier
        Box(
            modifier = modifier.padding(horizontal = 32.dp, vertical = 16.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = text, color = if (useGradient) Color.Unspecified else cabaret)
        }
    }
}

@Composable
fun MyInputField(title: String, text: String, setText: (String) -> Unit, fraction: Float) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(fraction = fraction).padding(horizontal = 4.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        value = text,
        onValueChange = setText,
        label = { Text(title) }
    )
}

private fun String.toIntSafe() = this.toIntOrNull() ?: 0

@Preview(widthDp = 360, heightDp = 640)
@Composable
private fun Preview() {
    MyTheme {
        DurationSetup(rememberNavController())
    }
}
