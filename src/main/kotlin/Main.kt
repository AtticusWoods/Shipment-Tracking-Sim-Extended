import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.launch

@Composable
@Preview
fun App() {
    val simulator = remember { TrackingSimulator() }
    val viewHelper = remember { TrackerViewHelper() }
    val coroutineScope = rememberCoroutineScope()
    var inputText by remember { mutableStateOf("") }

    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = inputText,
                onValueChange = { inputText = it },
                label = { Text("Enter Shipment ID") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    coroutineScope.launch {
                        viewHelper.trackShipment(inputText, simulator)
                    }
                    println("button Pressed")
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Track Shipment")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (viewHelper.shipmentId.value.isNotEmpty()) {
                shipmentDetails(viewHelper)
            }
        }
    }
}

@Composable
fun shipmentDetails(viewHelper: TrackerViewHelper) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        item {
            Text("Shipment ID: ${viewHelper.shipmentId.value}", style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Status: ${viewHelper.shipmentStatus.value}")
            Spacer(modifier = Modifier.height(8.dp))
            Text("Expected Delivery Date: ${viewHelper.expectedDeliveryDate.value}")
            Spacer(modifier = Modifier.height(8.dp))
            Text("Notes:")
            viewHelper.shipmentNotes.value.forEach { note ->
                Text("- $note")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text("Update History:")
            viewHelper.shipmentUpdateHistory.value.forEach { update ->
                Text("- $update")
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
