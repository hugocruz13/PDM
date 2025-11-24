package ipca.example.login_app.ui.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ipca.example.login_app.ui.theme.Blue
import ipca.example.login_app.ui.theme.LoginAPPTheme

@Composable
fun WelcomeView(
    modifier: Modifier = Modifier, navController: NavHostController = rememberNavController()
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Bem-vindo ao\nProduct Manager",
                textAlign = TextAlign.Center,
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 40.sp
            )
            Text(
                text = "Gestão de produtos simplificada", fontSize = 15.sp, lineHeight = 35.sp
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { navController.navigate("registar") },
                colors = ButtonDefaults.buttonColors(
                    Blue
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 8.dp)
            ) {
                Text("Registar", fontSize = 16.sp)
            }
            OutlinedButton(
                onClick = { navController.navigate("login") },
                colors = ButtonDefaults.buttonColors(Transparent, Blue),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 8.dp)
            ) {
                Text("Login", fontSize = 16.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeViewPreview() {
    LoginAPPTheme {
        WelcomeView()
    }
}