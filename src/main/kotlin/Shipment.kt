//Shipment class that gets observed
abstract class Shipment(val id: String) {
    var status: String = "created"
    val notes = mutableListOf<String>()
    val updateHistory = mutableListOf<ShippingUpdate>()
    var expectedDeliveryDateTimestamp: Long = 0
    var currentLocation: String = ""
    private val observers = mutableListOf<ShipmentObserver>()
    abstract val shipmentType: String

    fun addNote(note: String) {
        notes.add(note)
        notifyObservers()
    }

    fun addUpdate(update: ShippingUpdate) {
        updateHistory.add(update)
        notifyObservers()
    }

    fun addObserver(observer: ShipmentObserver) {
        observers.add(observer)
    }

    fun removeObserver(observer: ShipmentObserver) {
        observers.remove(observer)
    }

    private fun notifyObservers() {
        observers.forEach { it.update(this) }
    }

    abstract fun deliveryRequirements(deliveryTime: Long): Boolean
}