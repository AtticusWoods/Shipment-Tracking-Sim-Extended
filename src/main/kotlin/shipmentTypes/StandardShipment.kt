package shipmentTypes

import Shipment

class StandardShipment(id: String) : Shipment(id) {
    override val shipmentType = "standard"
    override val shipmentError: String = ""
    override fun deliveryRequirements() {
        //standard has no requirements
    }
}