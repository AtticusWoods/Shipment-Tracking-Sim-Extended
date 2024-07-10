package updateStrategies

import Shipment
import ShippingUpdate

class CreatedUpdateStrategy : UpdateStrategy {
    override fun handleUpdate(shipment: Shipment, update: ShippingUpdate) {
        shipment.status = "created"
        shipment.addUpdate(update)
    }
}