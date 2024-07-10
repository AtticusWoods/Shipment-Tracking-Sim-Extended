import kotlinx.coroutines.*
import updateStrategies.*
import java.io.File

class TrackingSimulator {
    private val shipments = mutableListOf<Shipment>()
    private val updateStrategies = mapOf(
        "created" to CreatedUpdateStrategy(),
        "shipped" to ShippedUpdateStrategy(),
        "location" to LocationUpdateStrategy(),
        "delivered" to DeliveredUpdateStrategy(),
        "delayed" to DelayedUpdateStrategy(),
        "lost" to LostUpdateStrategy(),
        "canceled" to CanceledUpdateStrategy(),
        "noteadded" to NoteAddedUpdateStrategy()
    )

    fun findShipment(id: String): Shipment? {
        println(shipments.find { it.id == id })
        return shipments.find { it.id == id }
    }

    fun addShipment(shipment: Shipment) {
        shipments.add(shipment)
    }

    suspend fun runSimulation(filePath: String) {
        File(filePath).useLines { lines ->
            lines.forEach { line ->
                val parts = line.split(",")
                val updateType = parts[0]
                val shipmentId = parts[1]
                val timestamp = parts[2].toLong()
                val otherInfo = if (parts.size > 3) parts[3] else null
                val update = ShippingUpdate(updateType, shipmentId, timestamp, otherInfo)
                processUpdate(update)
                delay(1000L)
                // Testing
                //println(update)
            }
        }
    }

    fun processUpdate(update: ShippingUpdate) {
        when (update.updateType) {
            "created" -> {
                // Create a new shipment and add it to the list
                val newShipment = Shipment(update.shipmentId)
                newShipment.addUpdate(update)
                addShipment(newShipment)
                println("Shipment created: ${update.shipmentId}")
            }
            else -> {
                val shipment = findShipment(update.shipmentId)
                if (shipment != null) {
                    val strategy = updateStrategies[update.updateType]
                    strategy?.handleUpdate(shipment, update) ?: println("No strategy found for update type: ${update.updateType}")
                } else {
                    println("Shipment not found: ${update.shipmentId}")
                }
            }
        }
    }
}
