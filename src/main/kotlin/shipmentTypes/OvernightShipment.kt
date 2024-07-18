package shipmentTypes

import Shipment

class OvernightShipment(id: String): Shipment(id) {
    private val shipmentType = "overnight"
    override fun deliveryRequirements(deliveryTime: Long): Boolean {
        TODO("Not yet implemented")
    }
}