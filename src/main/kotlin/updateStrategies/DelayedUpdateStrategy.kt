package updateStrategies

import Shipment
import ShippingUpdate

class DelayedUpdateStrategy : UpdateStrategy {
    override fun handleUpdate(shipment: Shipment, update: ShippingUpdate) {
        shipment.status = "delayed"
        shipment.expectedDeliveryDateTimestamp = update.otherInfo?.toLongOrNull() ?: shipment.expectedDeliveryDateTimestamp
        shipment.addUpdate(update)
    }
}