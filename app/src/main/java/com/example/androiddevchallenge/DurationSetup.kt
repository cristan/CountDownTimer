package com.example.androiddevchallenge

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.ui.theme.MyTheme

@Composable
fun DurationSetup(navController: NavHostController) {
    Surface(color = MaterialTheme.colors.background) {
        val (seconds, setSeconds) = remember { mutableStateOf("") }
        val (minutes, setMinutes) = remember { mutableStateOf("") }
        val (hours, setHours) = remember { mutableStateOf("") }

        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MyInputField(title = "Hours", text = hours, setText = setHours)
                MyInputField(title = "Minutes", text = minutes, setText = setMinutes)
                MyInputField(title = "Seconds", text = seconds, setText = setSeconds)
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        val duration =
                            seconds.toInt() + (minutes.toInt() * 60) + (hours.toInt() * 3600)
                        navController.navigate("countdown/$duration")
                    }
                ) {
                    Text(text = "Start")
                }
            }
        }
    }
}

@Composable
fun MyInputField(title: String, text: String, setText: (String) -> Unit) {
    OutlinedTextField(
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        value = text,
        onValueChange = { setText(it) },
        label = { Text(title) }
    )
}

private fun TextFieldValue.toInt() = this.text.toIntOrNull() ?: 0

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
private fun LightPreview() {
    MyTheme {
        DurationSetup(rememberNavController())
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
private fun DarkPreview() {
    MyTheme(darkTheme = true) {
        DurationSetup(rememberNavController())
    }
}