package ipca.example.login_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.AndroidEntryPoint
import ipca.example.login_app.ui.auth.LoginView
import ipca.example.login_app.ui.auth.RegisterView
import ipca.example.login_app.ui.auth.WelcomeView
import ipca.example.login_app.ui.cart.CartView
import ipca.example.login_app.ui.home.HomeView
import ipca.example.login_app.ui.perfil.PerfilView
import ipca.example.login_app.ui.product.ProductDetailView
import ipca.example.login_app.ui.purchases.PurchasesView
import ipca.example.login_app.ui.theme.LoginAPPTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            LoginAPPTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = "welcome"
                    ) {
                        composable("welcome") {
                            WelcomeView(navController = navController)
                        }

                        composable("login") {
                            LoginView(navController = navController)
                        }

                        composable("registar") {
                            RegisterView(navController = navController)
                        }

                        composable("home") {
                            HomeView(navController = navController)
                        }

                        composable("perfil") {
                            PerfilView(navController = navController)
                        }

                        composable("cart") {
                            CartView(navController = navController)
                        }

                        composable("product/{productId}") { backStackEntry ->
                            val productId = backStackEntry.arguments?.getString("productId") ?: ""
                            ProductDetailView(navController = navController, idProduct = productId)
                        }

                        composable("purchases")
                        {
                            PurchasesView(navController = navController)
                        }
                    }

                    LaunchedEffect(Unit) {
                        val userId = Firebase.auth.currentUser?.uid
                        if (userId != null) {
                            navController.navigate("home")
                        }
                    }
                }
            }
        }
    }
}
