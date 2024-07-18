package shipmentTypes

import Shipment

class ExpressShipment(id: String) : Shipment(id) {
    private val shipmentType = "express"
    override fun deliveryRequirements(deliveryTime: Long): Boolean {
        TODO("Not yet implemented")
    }
}