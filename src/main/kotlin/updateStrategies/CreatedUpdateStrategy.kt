package updateStrategies

import Shipment
import ShippingUpdate

class CreatedUpdateStrategy : UpdateStrategy {
    //Create also triggers the creation of a shipment object
    override fun handleUpdate(shipment: Shipment, update: ShippingUpdate) {
        shipment.status = "created"
        shipment.addUpdate(update)
    }
}