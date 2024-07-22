
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import shipmentTypes.BulkShipment
import shipmentTypes.ExpressShipment
import shipmentTypes.OvernightShipment
import shipmentTypes.StandardShipment
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.test.assertTrue


class ShipmentTypeTest {

    @Test
    fun testStandardShipment() {
        val shipment = StandardShipment("123")
        val createdTimestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) * 1000
        shipment.addUpdate(ShippingUpdate("created", "123", createdTimestamp, null))
        shipment.deliveryRequirements()

        assertTrue(shipment.notes.isEmpty(), "Standard shipment should not have any errors.")
    }

    @Test
    fun testExpressShipment() {
        val shipment = ExpressShipment("123")
        val createdTimestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) * 1000
        val invalidDeliveryTimestamp = createdTimestamp + (4 * 24 * 60 * 60 * 1000) // 4 days in milliseconds

        shipment.addUpdate(ShippingUpdate("created", "123", createdTimestamp, null))
        shipment.expectedDeliveryDateTimestamp = invalidDeliveryTimestamp
        shipment.deliveryRequirements()

        assertTrue(shipment.notes.contains(shipment.shipmentError), "Express shipment should have an error if delivery date is more than 3 days.")
    }

    @Test
    fun testOvernightShipment() {
        val shipment = OvernightShipment("123")
        val createdTimestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) * 1000
        val invalidDeliveryTimestamp = createdTimestamp + (2 * 24 * 60 * 60 * 1000) // 2 days in milliseconds

        shipment.addUpdate(ShippingUpdate("created", "123", createdTimestamp, null))
        shipment.expectedDeliveryDateTimestamp = invalidDeliveryTimestamp
        shipment.deliveryRequirements()

        assertTrue(shipment.notes.contains(shipment.shipmentError), "Overnight shipment should have an error if delivery date is not the next day.")
    }

    @Test
    fun testBulkShipment() {
        val shipment = BulkShipment("123")
        val createdTimestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) * 1000
        val invalidDeliveryTimestamp = createdTimestamp + (2 * 24 * 60 * 60 * 1000) // 2 days in milliseconds

        shipment.addUpdate(ShippingUpdate("created", "123", createdTimestamp, null))
        shipment.expectedDeliveryDateTimestamp = invalidDeliveryTimestamp
        shipment.deliveryRequirements()

        assertTrue(shipment.notes.contains(shipment.shipmentError), "Bulk shipment should have an error if delivery date is sooner than 3 days.")
    }
}