package ipca.example.converter

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ipca.example.converter.ui.theme.Red

@Composable
fun ConversionTypeButton(
    conversionType: ConversionType,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val buttonColors = if (isSelected) {
        ButtonDefaults.buttonColors(
            containerColor = Red,
            contentColor = Color.White
        )
    } else {
        ButtonDefaults.buttonColors(
            containerColor = Color.DarkGray,
            contentColor = Color.LightGray
        )
    }

    Button(
        modifier = Modifier.padding(horizontal = 4.dp),
        onClick = onClick,
        colors = buttonColors
    ) {
        Text(
            text = conversionType.displayName,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}