class ShippingUpdate(
    val updateType: String,
    val shipmentId: String,
    val timestamp: Long,
    val otherInfo: String? = null,
    val previousStatus: String? = null,
    val newStatus: String? = null
)