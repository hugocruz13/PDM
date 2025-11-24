package ipca.example.newsapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ipca.example.apicaller.ui.theme.APICallerTheme


@Composable
fun MyBottomBar(
    navController: NavController
){
    BottomAppBar {
        NavigationBarItem(
            selected = true,
            icon = {
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = "Beleza"
                )
            },
            label = {
                Text("Beleza")
            },
            onClick = {
                navController.navigate("beauty")
            }
        )
        NavigationBarItem(
            selected = true,
            icon = {
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = "Comida"
                )
            },
            label = {
                Text("Comida")
            },
            onClick = {
                navController.navigate("groceries")
            }
        )
        NavigationBarItem(
            selected = true,
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Mobília"
                )
            },
            label = {
                Text("Mobília")
            },
            onClick = {
                navController.navigate("furniture")
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyBottomBarPreview(){
    APICallerTheme {
        MyBottomBar(navController = rememberNavController())
    }
}