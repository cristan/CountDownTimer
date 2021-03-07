package com.example.androiddevchallenge

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.cardinal

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
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = cardinal, contentColor = Color.White),
                onClick = {
                    val duration = seconds.toIntSafe() + (minutes.toIntSafe() * 60) +
                            (hours.toIntSafe() * 3600)
                    navController.navigate("countdown/$duration")
                }
            ) {
                Text(text = "Start")
            }
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