import org.junit.Assert.assertEquals
import org.junit.Test
import updateStrategies.CanceledUpdateStrategy
import Shipment
import ShippingUpdate
import updateStrategies.CreatedUpdateStrategy

class CanceledUpdateStrategyTest {

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

    
}
