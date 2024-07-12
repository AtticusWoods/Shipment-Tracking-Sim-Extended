import org.junit.Assert.assertEquals
import org.junit.Test


class TrackingSimulatorTests {

    @Test
    fun `test addShipment adds`() {
        // Create necessary objects
        val shipment = Shipment("ABC123")
        val simulator = TrackingSimulator()

        //add shipment to simulator
        simulator.addShipment(shipment)

        // Assert that shipment is added to shipments list
        assertEquals(1, simulator.shipments.size)

    }

    @Test
    fun `test findShipment`() {
        // Create necessary objects
        val shipment = Shipment("ABC123567")
        val simulator = TrackingSimulator()

        //add shipment to simulator
        simulator.addShipment(shipment)

        // Assert that shipment is added to shipments list
        simulator.findShipment("ABC1234567")
    }


}