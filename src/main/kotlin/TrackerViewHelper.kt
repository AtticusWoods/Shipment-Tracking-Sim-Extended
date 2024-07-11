import androidx.compose.runtime.*

class TrackerViewHelper : ShipmentObserver {
    val shipmentId = mutableStateOf("")
    val shipmentNotes = mutableStateOf(listOf<String>())
    val shipmentUpdateHistory = mutableStateOf(listOf<String>())
    val expectedDeliveryDate = mutableStateOf("")
    val shipmentStatus = mutableStateOf("")

    private val trackedShipments = mutableMapOf<String, Shipment>()

    fun trackShipment(id: String, simulator: TrackingSimulator) {
        val shipment = simulator.findShipment(id)
        if (shipment != null) {
            shipment.addObserver(this)
            trackedShipments[id] = shipment
            update(shipment)
        } else {
            println("Shipment not found: $id")
        }
    }

    override fun update(shipment: Shipment) {
        // Grabs shipment info to display changes
        shipmentId.value = shipment.id
        shipmentNotes.value = shipment.notes
        shipmentUpdateHistory.value = shipment.updateHistory.map {
            "Shipment changed to ${it.updateType} on ${it.timestamp}"
        }
        expectedDeliveryDate.value = shipment.expectedDeliveryDateTimestamp.toString()
        shipmentStatus.value = shipment.status
    }
}