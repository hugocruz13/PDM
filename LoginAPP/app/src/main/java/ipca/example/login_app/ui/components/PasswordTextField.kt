package ipca.example.login_app.ui.components


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ipca.example.login_app.ui.theme.LoginAPPTheme

@Composable
fun PasswordTextField(value: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier) {

    var passwordVisibility by rememberSaveable { mutableStateOf(false) }

    val image = if (passwordVisibility)
        Icons.Filled.Visibility
    else Icons.Filled.VisibilityOff

    val description = if (passwordVisibility) "Hide password" else "Show password"

    TextField(
        value = value,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .then(modifier),
        label = { Text("Password") },
        onValueChange = onValueChange, // Using the conventional name
        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Password Icon") },
        trailingIcon = {
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(imageVector = image, contentDescription = description)
            }
        },
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        singleLine = true
    )
}


@Preview(showBackground = true)
@Composable
fun PasswordTextFieldPreview() {
    LoginAPPTheme {
        PasswordTextField("some_password", onValueChange = {})
    }
}