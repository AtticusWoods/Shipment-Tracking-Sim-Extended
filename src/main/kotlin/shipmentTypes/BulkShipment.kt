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
            LocalDateTime.ofEpochSecond(it/1000, 0, ZoneOffset.UTC)
            // Divide by 1000 to account for conversion from milliseconds to seconds
        }
        val estDeliveryDate = LocalDateTime.ofEpochSecond(expectedDeliveryDateTimestamp/1000, 0, ZoneOffset.UTC)

        if (ChronoUnit.DAYS.between(createdDate, estDeliveryDate) < 3) {
            addNote(shipmentError)
        }
    }

}