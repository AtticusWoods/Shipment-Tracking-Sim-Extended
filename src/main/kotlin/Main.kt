import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
fun App(simulator: TrackingSimulator) {
    //val simulator = remember { TrackingSimulator() }
    val viewHelpers = remember { mutableStateListOf<TrackerViewHelper>() }
    val coroutineScope = rememberCoroutineScope()
    var inputText by remember { mutableStateOf("") }


    MaterialTheme {

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                TextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    label = { Text("Enter Shipment ID") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                val viewHelper = TrackerViewHelper()
                                viewHelpers.add(viewHelper)
                                viewHelper.trackShipment(inputText, simulator)
                            }
                        },
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        Text("Track Shipment")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
            items(viewHelpers) { viewHelper ->
                if (viewHelper.shipmentId.value.isNotEmpty()) {
                    shipmentDetails(viewHelper)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

        }
    }
}

@Composable
fun shipmentDetails(viewHelper: TrackerViewHelper) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
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

fun main() = application {
    val simulator = TrackingSimulator()
    val coroutineScope = rememberCoroutineScope()
    coroutineScope.launch { simulator.runSimulation("test.txt") }


    Window(onCloseRequest = ::exitApplication) {
        App(simulator)
    }
}
