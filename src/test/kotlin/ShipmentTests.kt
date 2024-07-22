import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import kotlin.test.Test

class ShipmentTests {

    class TestShipment(id: String) : Shipment(id) {
        override val shipmentType: String = "test"
        override val shipmentError: String = "Test shipment error."

        override fun deliveryRequirements() {
            // Implementation not required for this test
        }
    }

    class TestObserver : ShipmentObserver {
        var updatesReceived = 0

        override fun update(shipment: Shipment) {
            updatesReceived++
        }

        override fun stopTracking(id: String) {

        }
    }

    @Test
    fun testAddNote() {
        val shipment = TestShipment("123")
        shipment.addNote("Test note")
        assertTrue(shipment.notes.contains("Test note"))
    }

    @Test
    fun testAddUpdate() {
        val shipment = TestShipment("123")
        val update = ShippingUpdate("test", "123", 0L, null)
        shipment.addUpdate(update)
        assertTrue(shipment.updateHistory.contains(update))
    }

    @Test
    fun testAddObserver() {
        val shipment = TestShipment("123")
        val observer = TestObserver()
        shipment.addObserver(observer)
        assertEquals(0, observer.updatesReceived)

        shipment.addNote("Test note")
        assertEquals(1, observer.updatesReceived)

        shipment.addUpdate(ShippingUpdate("test", "123", 0L, null))
        assertEquals(2, observer.updatesReceived)
    }

    @Test
    fun testRemoveObserver() {
        val shipment = TestShipment("123")
        val observer = TestObserver()
        shipment.addObserver(observer)
        assertEquals(0, observer.updatesReceived)

        shipment.addNote("Test note")
        assertEquals(1, observer.updatesReceived)

        shipment.removeObserver(observer)
        shipment.addUpdate(ShippingUpdate("test", "123", 0L, null))
        assertEquals(1, observer.updatesReceived) // Should remain 1 as observer is removed
    }

    @Test
    fun testNotifyObservers() {
        val shipment = TestShipment("123")
        val observer1 = TestObserver()
        val observer2 = TestObserver()

        shipment.addObserver(observer1)
        shipment.addObserver(observer2)

        assertEquals(0, observer1.updatesReceived)
        assertEquals(0, observer2.updatesReceived)

        shipment.addNote("Test note")
        assertEquals(1, observer1.updatesReceived)
        assertEquals(1, observer2.updatesReceived)
    }
}