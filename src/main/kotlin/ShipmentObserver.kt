interface ShipmentObserver {
    fun update(shipment: Shipment)
    fun stopTracking(id: String)
}