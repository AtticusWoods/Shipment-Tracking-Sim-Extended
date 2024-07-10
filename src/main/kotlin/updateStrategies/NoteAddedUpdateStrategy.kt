package updateStrategies

import Shipment
import ShippingUpdate

class NoteAddedUpdateStrategy : UpdateStrategy {
    override fun handleUpdate(shipment: Shipment, update: ShippingUpdate) {
        //Does not change status
        //shipment.status = "noteadded"
        val note = update.otherInfo ?: return  // Ensure that there is a note to add
        shipment.addNote(note)
        shipment.addUpdate(update)
    }
}