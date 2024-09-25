package com.example.resistencias.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp



@Preview(showBackground = true)
@Composable
fun inicio() {
    var banda1 by remember { mutableStateOf("") }
    var banda2 by remember { mutableStateOf("") }
    var multiplicador by remember { mutableStateOf("") }
    var tolerancia by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }

    val valoresColor = listOf("Negro", "Cafe", "Rojo", "Naranja", "Amarillo", "Verde", "Azul", "Morado", "Gris", "Blanco")
    val multiplicadorValor = listOf("Negro", "Cafe", "Rojo", "Naranja", "Amarillo", "Verde", "Azul", "Morado", "Gris", "Blanco")
    val toleranciaValor = listOf("Dorado", "Plateado", "Ninguno")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF333333))
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Calculadora de resistencias",
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))



        Spacer(modifier = Modifier.height(12.dp))

        Text(text = "Seleccione cada color de las resistencias:",
             color = Color.White)

        DropdownMenuComponent(label = "Banda 1", options = valoresColor, selectedOption = banda1) { banda1 = it }
        DropdownMenuComponent(label = "Banda 2", options = valoresColor, selectedOption = banda2) { banda2 = it }
        DropdownMenuComponent(label = "Multiplicador", options = multiplicadorValor, selectedOption = multiplicador) { multiplicador = it }
        DropdownMenuComponent(label = "Tolerancia", options = toleranciaValor, selectedOption = tolerancia) { tolerancia = it }


        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = {
                resultado = calcularResistancia(banda1, banda2, multiplicador, tolerancia)
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray,
                    contentColor = Color.White
            )
            ) {
                Text(
                    text = "Calcular",
                    modifier = Modifier.padding(2.dp),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = {
                banda1 = ""
                banda2 = ""
                multiplicador = ""
                tolerancia = ""
                resultado = ""
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray,
                    contentColor = Color.White
                )

                ) {
                Text(
                    text = "Reiniciar",
                    modifier = Modifier.padding(2.dp),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Resultado: $resultado",
            color = Color.White)
    }
}

@Composable
fun DropdownMenuComponent(label: String, options: List<String>, selectedOption: String, onSelect: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // Botón para mostrar el menú desplegable
        Button(onClick = { expanded = !expanded },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray,
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()) {
            Text(text = if (selectedOption.isEmpty()) label else selectedOption)

        }

        // Menú desplegable
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color.Transparent)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        expanded = false
                        onSelect(option)
                    },

                )
            }
        }
    }
}

fun calcularResistancia(banda1: String, banda2: String, multiplicador: String, tolerancia: String): String {
    val valoresColor = mapOf(
        "Negro" to 0, "Cafe" to 1, "Rojo" to 2, "Naranja" to 3,
        "Amarillo" to 4, "Verde" to 5, "Azul" to 6, "Morado" to 7,
        "Gris" to 8, "Blanco" to 9
    )

    val multiplicadorValor = mapOf(
        "Negro" to 1, "Cafe" to 10, "Rojo" to 100, "Naranja" to 1_000,
        "Amarillo" to 10_000, "Verde" to 100_000, "Azul" to 1_000_000, "Morado" to 10_000_000,
        "Gris" to 100_000_000, "Blanco" to 1_000_000_000
    )

    val toleranciaValor = mapOf(
        "Dorado" to 5, "Plateado" to 10, "Ninguno" to 20
    )

    val primerDigito = valoresColor[banda1] ?: 0
    val segundoDigito = valoresColor[banda2] ?: 0
    val valorMultiplicador = multiplicadorValor[multiplicador] ?: 1
    val valorTolerancia = toleranciaValor[tolerancia] ?: 20

    val valorResistencia = (primerDigito * 10 + segundoDigito) * valorMultiplicador

    return "$valorResistencia Ω ±$valorTolerancia%"
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    inicio()
}