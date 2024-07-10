//Class for sending updates in a consistent "shape"
class ShippingUpdate(
    val updateType: String,
    val shipmentId: String,
    val timestamp: Long,
    val otherInfo: String? = null,
    val previousStatus: String? = null,
    val newStatus: String? = null
)