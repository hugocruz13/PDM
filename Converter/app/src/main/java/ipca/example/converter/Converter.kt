package ipca.example.converter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ipca.example.converter.ui.theme.ConverterTheme
import ipca.example.converter.ui.theme.Grey
import java.util.Locale

enum class ConversionType(val displayName: String) {
    KM_TO_MILES("Km → Milhas"),
    MILES_TO_KM("Milhas → Km"),
    M_TO_FEET("Metros → Pés")
}


@Composable
fun Converter(modifier: Modifier = Modifier) {

    var number by remember { mutableStateOf("") }
    var display by remember { mutableStateOf("0") }
    var selectedConversion by remember { mutableStateOf(ConversionType.KM_TO_MILES) }

    fun performConversion() {
        val inputValue = number.toDoubleOrNull()
        if (inputValue == null) {
            display = "Input inválido"
            return
        }

        val result = when (selectedConversion) {
            ConversionType.KM_TO_MILES -> inputValue * 0.621371
            ConversionType.MILES_TO_KM -> inputValue / 0.621371
            ConversionType.M_TO_FEET -> inputValue * 3.28084
        }

        display = String.format(Locale.US, "%.2f", result)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Grey),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            ConversionTypeButton(
                conversionType = ConversionType.KM_TO_MILES,
                isSelected = selectedConversion == ConversionType.KM_TO_MILES,
                onClick = { selectedConversion = ConversionType.KM_TO_MILES }
            )
            ConversionTypeButton(
                conversionType = ConversionType.MILES_TO_KM,
                isSelected = selectedConversion == ConversionType.MILES_TO_KM,
                onClick = { selectedConversion = ConversionType.MILES_TO_KM }
            )
            ConversionTypeButton(
                conversionType = ConversionType.M_TO_FEET,
                isSelected = selectedConversion == ConversionType.M_TO_FEET,
                onClick = { selectedConversion = ConversionType.M_TO_FEET }
            )
        }

        TextField(
            modifier = Modifier.padding(16.dp),
            value = number,
            onValueChange = {
                number = it
            },
            label = { Text("Valor a converter") }
        )

        ConverterButton(label = "Converter", onNumPressed = { performConversion() })

        Text(
            text = display,
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ConverterPreview() {
    ConverterTheme {
        Converter()
    }
}