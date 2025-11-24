package ipca.example.converter

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ipca.example.converter.ui.theme.ConverterTheme
import ipca.example.converter.ui.theme.Red

@Composable
fun ConverterButton(modifier: Modifier = Modifier, label: String, onNumPressed: (String) -> Unit) {
    Button(
        modifier = modifier
            .padding(5.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Red),
        onClick = { onNumPressed(label) }) {
        Text(
            label, style = if (label.count() == 1)
                MaterialTheme.typography.displayMedium else
                MaterialTheme.typography.titleMedium
        )
    }
}

@Preview
@Composable
fun PreviewConverterButton() {
    ConverterTheme {
        ConverterButton(label = "Convert", onNumPressed = {})
    }
}