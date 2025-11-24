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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CoPresent
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ipca.example.login_app.ui.components.PasswordTextField
import ipca.example.login_app.ui.theme.Blue
import ipca.example.login_app.ui.theme.LoginAPPTheme

@Composable
fun RegisterView(
    modifier: Modifier = Modifier, navController: NavHostController = rememberNavController()
) {
    val viewModel: RegisterViewModel = viewModel()
    val uiState by viewModel.uiState

    Box(modifier = modifier.fillMaxSize()) {
        if (uiState.loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
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
                    text = "Criar Conta",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(20.dp)
                )

                ClickableText(
                    text = AnnotatedString("Login"),
                    modifier = Modifier
                        .padding(20.dp)
                        .align(Alignment.CenterVertically),
                    style = TextStyle(
                        color = Blue,
                        fontSize = 16.sp,
                    ),
                    onClick = { navController.navigate("login") })
            }
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                TextField(
                    value = uiState.name ?: "",
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(20.dp, 10.dp),
                    label = { Text("Name") },
                    onValueChange = { viewModel.updateName(it) },
                    leadingIcon = {
                        Icon(
                            Icons.Default.CoPresent,
                            contentDescription = "Email Icon"
                        )
                    })


                TextField(
                    value = uiState.contact?.toString() ?: "",
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(20.dp, 10.dp),
                    label = { Text("Contact") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Next
                    ),
                    onValueChange = { newString ->
                        val newInt = newString.toIntOrNull()
                        if (newString.isEmpty() || newInt != null) {
                            viewModel.updateContact(newInt ?: 0)
                        }
                    },
                    leadingIcon = { Icon(Icons.Default.Phone, contentDescription = "Phone Icon") },
                )

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

            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { viewModel.registar({ navController.navigate("welcome") }) },
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .height(50.dp),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text("Registar", fontSize = 16.sp)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterViewPreview() {
    LoginAPPTheme {
        RegisterView()
    }
}