package shipmentTypes

import Shipment

class ExpressShipment(id: String) : Shipment(id) {
    override val shipmentType = "express"
    override fun deliveryRequirements(deliveryTime: Long): Boolean {
        TODO("Not yet implemented")
    }
}