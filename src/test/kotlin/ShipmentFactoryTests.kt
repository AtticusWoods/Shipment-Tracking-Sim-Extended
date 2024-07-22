import org.junit.Assert.*
import shipmentTypes.BulkShipment
import shipmentTypes.ExpressShipment
import shipmentTypes.OvernightShipment
import shipmentTypes.StandardShipment
import kotlin.test.Test

class ShipmentFactoryTests {

    @Test
    fun testCreateStandardShipment() {
        val shipment = ShipmentFactory.createShipment("standard", "123")
        assertTrue(shipment is StandardShipment)
        assertEquals("123", shipment.id)
    }

    @Test
    fun testCreateExpressShipment() {
        val shipment = ShipmentFactory.createShipment("express", "456")
        assertTrue(shipment is ExpressShipment)
        assertEquals("456", shipment.id)
    }

    @Test
    fun testCreateOvernightShipment() {
        val shipment = ShipmentFactory.createShipment("overnight", "789")
        assertTrue(shipment is OvernightShipment)
        assertEquals("789", shipment.id)
    }

    @Test
    fun testCreateBulkShipment() {
        val shipment = ShipmentFactory.createShipment("bulk", "012")
        assertTrue(shipment is BulkShipment)
        assertEquals("012", shipment.id)
    }

    @Test
    fun testCreateUnknownShipment() {
        val exception = assertThrows(IllegalArgumentException::class.java) {
            ShipmentFactory.createShipment("unknown", "999")
        }
        assertEquals("Unknown shipment type: unknown", exception.message)
    }
}