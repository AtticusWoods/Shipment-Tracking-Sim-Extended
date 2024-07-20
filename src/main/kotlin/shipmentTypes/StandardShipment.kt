package shipmentTypes

import Shipment

class StandardShipment(id: String) : Shipment(id) {
    override val shipmentType = "standard"
    override fun deliveryRequirements(deliveryTime: Long): Boolean {
        TODO("Not yet implemented")
    }
}