import androidx.compose.runtime.*
import java.sql.Date
import java.text.SimpleDateFormat

class TrackerViewHelper : ShipmentObserver {
    val shipmentId = mutableStateOf("")
    val shipmentNotes = mutableStateOf(listOf<String>())
    val shipmentUpdateHistory = mutableStateOf(listOf<String>())
    val expectedDeliveryDate = mutableStateOf("")
    val shipmentStatus = mutableStateOf("")
    val shipmentLocation = mutableStateOf("")
    val shipmentType = mutableStateOf("")

    private val trackedShipment = mutableMapOf<String, Shipment>()

    fun trackShipment(id: String, simulator: TrackingSimulator) {
        val shipment = simulator.findShipment(id)
        if (shipment != null) {
            shipment.addObserver(this)
            trackedShipment[id] = shipment
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
            "Shipment changed to ${it.updateType} on ${SimpleDateFormat("yyyy.MM.dd HH:mm").format(Date(it.timestamp))}"
        }
        expectedDeliveryDate.value = shipment.expectedDeliveryDateTimestamp.toString()
        shipmentStatus.value = shipment.status
        shipmentLocation.value = shipment.currentLocation
        shipmentType.value = shipment.shipmentType
    }

    override fun stopTracking(id: String){
        val shipment = trackedShipment[id]
        shipment?.removeObserver(this)

    }
}