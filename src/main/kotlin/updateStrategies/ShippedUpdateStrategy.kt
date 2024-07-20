package updateStrategies

import Shipment
import ShippingUpdate

class ShippedUpdateStrategy : UpdateStrategy {
    override fun handleUpdate(shipment: Shipment, update: ShippingUpdate) {
        shipment.status = "shipped"
        shipment.expectedDeliveryDateTimestamp = update.otherInfo?.toLongOrNull() ?: shipment.expectedDeliveryDateTimestamp
        shipment.addUpdate(update)
        shipment.deliveryRequirements()
    }
}