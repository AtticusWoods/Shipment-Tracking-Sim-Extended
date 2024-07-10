package updateStrategies

import Shipment
import ShippingUpdate

class DeliveredUpdateStrategy : UpdateStrategy {
    override fun handleUpdate(shipment: Shipment, update: ShippingUpdate) {
        shipment.status = "delivered"
        shipment.addUpdate(update)
    }
}