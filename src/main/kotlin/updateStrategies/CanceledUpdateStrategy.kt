package updateStrategies

import Shipment
import ShippingUpdate

class CanceledUpdateStrategy : UpdateStrategy {
    override fun handleUpdate(shipment: Shipment, update: ShippingUpdate) {
        shipment.status = "canceled"
        shipment.addUpdate(update)
    }
}