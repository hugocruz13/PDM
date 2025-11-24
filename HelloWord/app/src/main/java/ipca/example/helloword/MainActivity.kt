package ipca.example.helloword

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.tooling.preview.Preview
import ipca.example.helloword.ui.theme.HelloWordTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Permite escrever de um lado ao outro do ecrã
        setContent {
            HelloWordTheme { // Define tema, cor, fonte etc..
                Scaffold (modifier = Modifier.fillMaxSize()) {innerPadding ->
                        Greet(modifier = Modifier.padding(paddingValues = innerPadding))
                }
            }
        }
    }
}


