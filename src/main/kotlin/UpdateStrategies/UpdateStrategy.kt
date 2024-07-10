interface UpdateStrategy {
    fun handleUpdate(shipment: Shipment, update: ShippingUpdate)
}