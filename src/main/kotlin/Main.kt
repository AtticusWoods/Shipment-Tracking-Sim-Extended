import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.sql.Date
import java.text.SimpleDateFormat

@Composable
@Preview
fun App(simulator: TrackingSimulator) {
    val viewHelpers = remember { mutableStateListOf<TrackerViewHelper>() }
    val coroutineScope = rememberCoroutineScope()
    var inputText by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var updateString by remember { mutableStateOf("") }



    MaterialTheme {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                // Input for shipment ID
                TextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    label = { Text("Enter Shipment ID") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                //Button to submit Shipment ID
                Box(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                val viewHelper = TrackerViewHelper()
                                viewHelper.trackShipment(inputText, simulator)
                                if (viewHelper.shipmentId.value.isNotEmpty()) {
                                    viewHelpers.add(viewHelper)
                                } else {
                                    showError = true
                                    errorMessage = "$inputText is not a valid shipment!"
                                    coroutineScope.launch {
                                        delay(3000)
                                        showError = false
                                    }
                                }

                            }
                        },
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        Text("Track Shipment")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                AnimatedVisibility(
                    visible = showError,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colors.error,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
            //Tracked Shipments
            items(viewHelpers) { viewHelper ->
                if (viewHelper.shipmentId.value.isNotEmpty()) {
                    shipmentDetails(viewHelper) {
                        viewHelpers.remove(viewHelper)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            // Input for shipment update
            item {
                TextField(
                    value = updateString,
                    onValueChange = { updateString = it },
                    label = { Text("Enter Shipment Update") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Button to submit Shipment Update
                Box(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                val update = simulator.splitUpdate(updateString)
                                simulator.processUpdate(update)
                                updateString = ""
                            }
                        },
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        Text("Add Shipment Update")
                    }
                }
            }
        }
    }
}

//@Composable
//fun InputWindow(onAddUpdate: (String) -> Unit) {
//    var updateString by remember { mutableStateOf("") }
//
//    Column(modifier = Modifier.padding(16.dp)) {
//        Text("Add Shipment Update")
//        Spacer(modifier = Modifier.height(8.dp))
//
//        TextField(
//            value = updateString,
//            onValueChange = {
//                updateString = it
//            },
//            label = { Text("Update String") }
//        )
//        Spacer(modifier = Modifier.height(8.dp))
//
//        Button(onClick = {
//            onAddUpdate(updateString)
//        }) {
//            Text("Add Update")
//        }
//    }
//}



@Composable
fun shipmentDetails(viewHelper: TrackerViewHelper, onStopTracking: () -> Unit) {
    // Display of the tracked Shipment
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        Text("Shipment ID: ${viewHelper.shipmentId.value}", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Status: ${viewHelper.shipmentStatus.value}")
        Spacer(modifier = Modifier.height(8.dp))
        Text("Location: ${viewHelper.shipmentLocation.value}")
        Spacer(modifier = Modifier.height(8.dp))
        Text("Expected Delivery Date: ${SimpleDateFormat("yyyy.MM.dd HH:mm").format(Date(viewHelper.expectedDeliveryDate.value.toLong()))}")
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
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onStopTracking,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Stop Tracking")
        }
    }
}

fun main() = application {
    val simulator = TrackingSimulator()
    val coroutineScope = rememberCoroutineScope()
    // Using the test.txt at root for the assignment
    //coroutineScope.launch { simulator.runSimulation("test.txt") }


    Window(onCloseRequest = ::exitApplication) {
        //pass the active simulator to the gui
        App(simulator)
    }
}