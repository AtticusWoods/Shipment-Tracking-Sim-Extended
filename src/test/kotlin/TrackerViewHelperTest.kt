import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test


class TrackingViewHelperTest {

    @Test
    fun `test addShipment adds`() {
        // Create necessary objects
        val shipment = Shipment("ABC123")
        val simulator = TrackingSimulator()
        val tracker = TrackerViewHelper()

        //add shipment to simulator
        simulator.addShipment(shipment)

        //track shipment
        tracker.trackShipment("ABC123", simulator)

        //Check if updated properly
        assertEquals("ABC123", tracker.shipmentId.value)
        assertEquals("created", tracker.shipmentStatus.value)
        assertEquals("", tracker.shipmentLocation.value)
        assertEquals(0, tracker.shipmentNotes.value.size)
        assertEquals(0, tracker.shipmentUpdateHistory.value.size)
        assertEquals("0", tracker.expectedDeliveryDate.value)

        //
        simulator.processUpdate(ShippingUpdate("lost", "ABC123", 100, "yeah"))

        //Check if updated properly
        assertEquals("ABC123", tracker.shipmentId.value)
        assertEquals("lost", tracker.shipmentStatus.value)
        assertEquals("", tracker.shipmentLocation.value)
        assertEquals(0, tracker.shipmentNotes.value.size)
        assertEquals(1, tracker.shipmentUpdateHistory.value.size)
        assertEquals("0", tracker.expectedDeliveryDate.value)

    }

}