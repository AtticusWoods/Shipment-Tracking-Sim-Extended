import org.junit.Assert.assertEquals
import org.junit.Test
import Shipment
import ShippingUpdate
import updateStrategies.*

class UpdateStrategyTest {

    @Test
    fun `test CanceledUpdateStrategy handles update correctly`() {
        // Create necessary objects
        val shipment = Shipment("ABC123")
        val update = ShippingUpdate("canceled", "ABC123", 1234)

        // Create instance of CanceledUpdateStrategy
        val strategy = CanceledUpdateStrategy()

        // Apply strategy to handle update
        strategy.handleUpdate(shipment, update)

        // Assert that shipment status is updated correctly
        assertEquals("canceled", shipment.status)

        // Assert that update is added to shipment
        assertEquals(1, shipment.updateHistory.size)

    }

    @Test
    fun `test CreatedUpdateStrategy handles update correctly`() {
        // Create necessary objects
        val shipment = Shipment("ABC123")
        val update = ShippingUpdate("created", "ABC123", 1234)

        // Create instance of CanceledUpdateStrategy
        val strategy = CreatedUpdateStrategy()

        // Apply strategy to handle update
        strategy.handleUpdate(shipment, update)

        // Assert that shipment status is updated correctly
        assertEquals("created", shipment.status)

        // Assert that update is added to shipment
        assertEquals(1, shipment.updateHistory.size)

    }

    @Test
    fun `test DelayedUpdateStrategy handles update correctly`() {
        // Create necessary objects
        val shipment = Shipment("ABC123")
        val update = ShippingUpdate("delayed", "ABC123", 1234)

        // Create instance of CanceledUpdateStrategy
        val strategy = DelayedUpdateStrategy()

        // Apply strategy to handle update
        strategy.handleUpdate(shipment, update)

        // Assert that shipment status is updated correctly
        assertEquals("delayed", shipment.status)

        // Assert that update is added to shipment
        assertEquals(1, shipment.updateHistory.size)

    }

    @Test
    fun `test DeliveredUpdateStrategy handles update correctly`() {
        // Create necessary objects
        val shipment = Shipment("ABC123")
        val update = ShippingUpdate("delivered", "ABC123", 1234)

        // Create instance of CanceledUpdateStrategy
        val strategy = DeliveredUpdateStrategy()

        // Apply strategy to handle update
        strategy.handleUpdate(shipment, update)

        // Assert that shipment status is updated correctly
        assertEquals("delivered", shipment.status)

        // Assert that update is added to shipment
        assertEquals(1, shipment.updateHistory.size)

    }

    @Test
    fun `test LocationUpdateStrategy handles update correctly`() {
        // Create necessary objects
        val shipment = Shipment("ABC123")
        val update = ShippingUpdate("location", "ABC123", 1234, "Idaho")

        // Create instance of CanceledUpdateStrategy
        val strategy = LocationUpdateStrategy()

        // Apply strategy to handle update
        strategy.handleUpdate(shipment, update)

        // Assert that shipment status is not updated
        assertEquals("created", shipment.status)

        // Assert Location change
        assertEquals("Idaho", shipment.currentLocation)

        // Assert that update is added to shipment
        assertEquals(1, shipment.updateHistory.size)

    }

    @Test
    fun `test LostUpdateStrategy handles update correctly`() {
        // Create necessary objects
        val shipment = Shipment("ABC123")
        val update = ShippingUpdate("lost", "ABC123", 1234)

        // Create instance of CanceledUpdateStrategy
        val strategy = LostUpdateStrategy()

        // Apply strategy to handle update
        strategy.handleUpdate(shipment, update)

        // Assert that shipment status is updated correctly
        assertEquals("lost", shipment.status)

        // Assert that update is added to shipment
        assertEquals(1, shipment.updateHistory.size)
    }

    @Test
    fun `test NoteAddedUpdateStrategy handles update correctly`() {
        // Create necessary objects
        val shipment = Shipment("ABC123")
        val update = ShippingUpdate("noteadded", "ABC123", 1234, "Package Crushed oops")
        val update2 = ShippingUpdate("noteadded", "ABC123", 1234, "Package eaten by Godzilla")

        // Create instance of CanceledUpdateStrategy
        val strategy = NoteAddedUpdateStrategy()

        // Apply strategy to handle update
        strategy.handleUpdate(shipment, update)
        strategy.handleUpdate(shipment, update2)


        // Assert that shipment status is not updated
        assertEquals("created", shipment.status)

        assertEquals("Package Crushed oops", shipment.notes[0])
        assertEquals("Package eaten by Godzilla", shipment.notes[1])

        // Assert that update is added to shipment
        assertEquals(2, shipment.updateHistory.size)

    }

    @Test
    fun `test ShippedUpdateStrategy handles update correctly`() {
        // Create necessary objects
        val shipment = Shipment("ABC123")
        val update = ShippingUpdate("shipped", "ABC123", 1234, "2005")

        // Create instance of CanceledUpdateStrategy
        val strategy = ShippedUpdateStrategy()

        // Apply strategy to handle update
        strategy.handleUpdate(shipment, update)

        // Assert that shipment status is updated correctly
        assertEquals("shipped", shipment.status)

        // Assert that shipment time updated
        assertEquals(2005L, shipment.expectedDeliveryDateTimestamp)

        // Assert that update is added to shipment
        assertEquals(1, shipment.updateHistory.size)
    }


}
