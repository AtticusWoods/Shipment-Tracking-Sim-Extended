package updateStrategies

import Shipment
import ShippingUpdate

class LostUpdateStrategy : UpdateStrategy {
    override fun handleUpdate(shipment: Shipment, update: ShippingUpdate) {
        shipment.status = "lost"
        shipment.addUpdate(update)
    }
}