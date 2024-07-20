package shipmentTypes

import Shipment

class OvernightShipment(id: String): Shipment(id) {
    override val shipmentType = "overnight"
    override fun deliveryRequirements(deliveryTime: Long): Boolean {
        TODO("Not yet implemented")
    }
}