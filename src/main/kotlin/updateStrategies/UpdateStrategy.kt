package updateStrategies

import Shipment
import ShippingUpdate

interface UpdateStrategy {
    fun handleUpdate(shipment: Shipment, update: ShippingUpdate)
}