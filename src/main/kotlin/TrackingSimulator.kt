import kotlinx.coroutines.*
import updateStrategies.*
import java.io.File

class TrackingSimulator {
    val shipments = mutableListOf<Shipment>()

    //List of strategies, this should be the only place you need to add a new one other than creating the new strat
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
            }
        }
    }

    fun processUpdate(update: ShippingUpdate) {
        when (update.updateType) {
            //created is special among the strategies
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
