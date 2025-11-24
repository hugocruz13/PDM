package ipca.example.login_app.ui.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ipca.example.login_app.ui.components.PasswordTextField
import ipca.example.login_app.ui.theme.Blue
import ipca.example.login_app.ui.theme.LoginAPPTheme

@Composable
fun LoginView(
    modifier: Modifier = Modifier, navController: NavHostController = rememberNavController()
) {
    val viewModel: LoginViewModel = hiltViewModel()
    val uiState by viewModel.uiState

    Box(modifier = modifier.fillMaxSize()) {
        if (uiState.loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else if (uiState.error != null) {
            Text(
                uiState.error!!,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp),
                textAlign = TextAlign.Center
            )
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Bem-vindo(a)",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(20.dp)
                )

                ClickableText(
                    text = AnnotatedString("Registar"),
                    modifier = Modifier
                        .padding(20.dp)
                        .align(Alignment.CenterVertically),
                    style = TextStyle(
                        color = Blue,
                        fontSize = 16.sp,
                    ),
                    onClick = { navController.navigate("registar") })
            }
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                TextField(
                    value = uiState.email ?: "",
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(20.dp, 10.dp),
                    label = { Text("Email") },
                    onValueChange = { viewModel.updateEmail(it) },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email Icon") })

                PasswordTextField(
                    value = uiState.password ?: "",
                    onValueChange = { viewModel.updatePassword(it) },
                    modifier = modifier
                )


                Button(
                    onClick = {
                        viewModel.login({ navController.navigate("home") })
                    },
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .height(50.dp),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text("Login", fontSize = 16.sp)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    LoginAPPTheme {
        LoginView()
    }
}