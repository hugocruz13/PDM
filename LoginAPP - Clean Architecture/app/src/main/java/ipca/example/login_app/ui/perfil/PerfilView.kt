package ipca.example.login_app.ui.perfil


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import ipca.example.login_app.ui.components.BackTopBar
import ipca.example.login_app.ui.components.UserInfoItem
import ipca.example.login_app.ui.theme.LoginAPPTheme

@Composable
fun PerfilView(
    modifier: Modifier = Modifier, navController: NavHostController
) {
    val viewModel: PerfilViewModel = viewModel()
    val uiState by viewModel.uiState

    PerfilViewContent(
        modifier, uiState, navController, onLogout = {
            viewModel.logout()
            navController.navigate("welcome")
        })

    LaunchedEffect(Unit) {
        viewModel.fetchUser()
    }
}

@Composable
fun PerfilViewContent(
    modifier: Modifier = Modifier,
    uiState: PerfilState,
    navController: NavHostController,
    onLogout: () -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (uiState.error != null) {
            Text(
                uiState.error.toString(),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp),
                textAlign = TextAlign.Center
            )
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                BackTopBar(title = "Perfil do Utilizador", navController = navController)

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Spacer(modifier = Modifier.height(32.dp))

                    AsyncImage(
                        model = uiState.user.img,
                        contentDescription = "Imagem de Perfil",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = uiState.user.name ?: "Nome Indisponível",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = uiState.user.email ?: "email.indisponivel@email.com",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        UserInfoItem(
                            icon = Icons.Default.Person,
                            label = "Nome",
                            value = uiState.user.name ?: "Nome Indisponível"
                        )

                        UserInfoItem(
                            icon = Icons.Default.Email,
                            label = "Email",
                            value = uiState.user.email ?: "email.indisponivel@email.com"
                        )

                        UserInfoItem(
                            icon = Icons.Default.Phone,
                            label = "Contacto",
                            value = uiState.user.contact?.toString() ?: "Não disponível"
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = { navController.navigate("purchases") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Minhas Compras", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = onLogout,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFD32F2F)
                        )
                    ) {
                        Text(
                            "Terminar Sessão",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PerfilViewContentPreview() {
    LoginAPPTheme {
        PerfilViewContent(
            uiState = PerfilState(),
            navController = NavHostController(LocalContext.current),
            onLogout = {}
        )
    }
}