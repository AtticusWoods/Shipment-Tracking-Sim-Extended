package shipmentTypes

import Shipment
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit

class OvernightShipment(id: String): Shipment(id) {
    override val shipmentType = "overnight"
    override val shipmentError = "Error: An overnight shipment should have an expected delivery date of the day after it was created."
    override fun deliveryRequirements() {
        val createdDate = updateHistory.first().timestamp.let {
            LocalDateTime.ofEpochSecond(it, 0, ZoneOffset.UTC)
        }
        val estDeliveryDate = LocalDateTime.ofEpochSecond(expectedDeliveryDateTimestamp, 0, ZoneOffset.UTC)
        if (ChronoUnit.DAYS.between(createdDate, estDeliveryDate) > 1) {
            addNote(shipmentError)
        }
    }
}