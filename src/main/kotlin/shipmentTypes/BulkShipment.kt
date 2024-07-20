package shipmentTypes

import Shipment

class BulkShipment(id: String) : Shipment(id) {
    override val shipmentType = "bulk"
    override fun deliveryRequirements(deliveryTime: Long): Boolean {
        TODO("Not yet implemented")
    }

}