package ipca.example.apicaller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ipca.example.apicaller.ui.products.ProductDetailView
import ipca.example.apicaller.ui.products.ProductsViewList
import ipca.example.apicaller.ui.theme.APICallerTheme
import ipca.example.newsapp.ui.components.MyBottomBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            var navTitle by remember { mutableStateOf("Products") }
            var isHomeScreen by remember { mutableStateOf(true) }

            APICallerTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(), bottomBar = {
                        MyBottomBar(navController = navController)
                    }) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = "beauty"
                    ) {
                        composable("beauty") {
                            navTitle = "Beleza"
                            isHomeScreen = true
                            ProductsViewList(navController = navController, category = "beauty")
                        }

                        composable("groceries") {
                            navTitle = "Comida"
                            isHomeScreen = true
                            ProductsViewList(navController = navController, category = "groceries")
                        }

                        composable("furniture") {
                            navTitle = "Mobília"
                            isHomeScreen = true
                            ProductsViewList(navController = navController, category = "furniture")
                        }

                        composable("product_detail/{id}") { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("id")?:""
                            isHomeScreen = false

                            ProductDetailView(
                                id = id,
                                modifier = Modifier
                            )
                        }
                    }
                }
            }
        }
    }
}
