import shipmentTypes.BulkShipment
import shipmentTypes.ExpressShipment
import shipmentTypes.OvernightShipment
import shipmentTypes.StandardShipment

//Object is singleton in one word//
object ShipmentFactory {
    fun createShipment(type: String, id: String): Shipment {
        return when (type) {
            "standard" -> StandardShipment(id)
            "express" -> ExpressShipment(id)
            "overnight" -> OvernightShipment(id)
            "bulk" -> BulkShipment(id)
            else -> throw IllegalArgumentException("Unknown shipment type: $type")
        }
    }
}