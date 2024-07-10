package updateStrategies

import Shipment
import ShippingUpdate

class LocationUpdateStrategy : UpdateStrategy {
    override fun handleUpdate(shipment: Shipment, update: ShippingUpdate) {
        shipment.currentLocation = update.otherInfo ?: shipment.currentLocation
        shipment.addUpdate(update)
    }
}