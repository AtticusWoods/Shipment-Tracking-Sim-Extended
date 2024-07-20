package shipmentTypes

import Shipment
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit

class BulkShipment(id: String) : Shipment(id) {
    override val shipmentType = "bulk"
    override val shipmentError = "Error: A Bulk shipment should not have an expected delivery date sooner than 3 days after it was created."
    override fun deliveryRequirements() {
        val createdDate = updateHistory.first().timestamp.let {
            LocalDateTime.ofEpochSecond(it, 0, ZoneOffset.UTC)
        }

        val estDeliveryDate = LocalDateTime.ofEpochSecond(expectedDeliveryDateTimestamp, 0, ZoneOffset.UTC)
        if (ChronoUnit.DAYS.between(createdDate, estDeliveryDate) > 3) {
            addNote(shipmentError)
        }
    }

}