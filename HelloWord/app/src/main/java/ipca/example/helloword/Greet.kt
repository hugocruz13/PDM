package ipca.example.helloword

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun Greet(modifier: Modifier = Modifier) {

    var name by remember { mutableStateOf(value = "John") }
    var greet by remember { mutableStateOf(value = "") }


    Column(modifier = modifier.fillMaxSize()) {
        TextField(
            value = name,
            onValueChange = {
                name = it
            }
        )

        Button(onClick = { greet = "Olá $name!!"}) {
            Text(text = "Cumprimentar")
        }

        Text(text = "Olá sou o Hugo Cruz!")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetPreview() {
    Greet()
}
