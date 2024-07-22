package shipmentTypes

import Shipment
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit

class ExpressShipment(id: String) : Shipment(id) {
    override val shipmentType = "express"
    override val shipmentError = "Error: An express shipment should not have an expected delivery date of more than 3 days after the shipment was created."
    override fun deliveryRequirements() {
        val createdDate = updateHistory.first().timestamp.let {
            LocalDateTime.ofEpochSecond(it/1000, 0, ZoneOffset.UTC)
        }
        val estDeliveryDate = LocalDateTime.ofEpochSecond(expectedDeliveryDateTimestamp/1000, 0, ZoneOffset.UTC)
        println(ChronoUnit.DAYS.between(createdDate, estDeliveryDate))
        if (ChronoUnit.DAYS.between(createdDate, estDeliveryDate) > 3) {
            addNote(shipmentError)
        }
    }
}