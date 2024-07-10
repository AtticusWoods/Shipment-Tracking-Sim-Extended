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
        "doteadded" to NoteAddedUpdateStrategy()
    )

    fun findShipment(id: String): Shipment? {
        return shipments.find { it.id == id }
    }

    fun addShipment(shipment: Shipment) {
        shipments.add(shipment)
    }

    fun runSimulation() {
        // Read updates from file and call processUpdate for each update
    }

    fun processUpdate(update: ShippingUpdate) {
        val shipment = findShipment(update.shipmentId)
        if (shipment != null) {
            val strategy = updateStrategies[update.updateType]
            strategy?.handleUpdate(shipment, update) ?: println("No strategy found for update type: ${update.updateType}")
        } else {
            println("Shipment not found: ${update.shipmentId}")
        }
    }
}
