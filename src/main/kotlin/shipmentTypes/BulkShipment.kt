package shipmentTypes

import Shipment

class BulkShipment(id: String) : Shipment(id) {
    private val shipmentType = "bulk"
    override fun deliveryRequirements(deliveryTime: Long): Boolean {
        TODO("Not yet implemented")
    }

}