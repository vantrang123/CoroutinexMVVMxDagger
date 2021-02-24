package com.tom.learncoroutinexroom.data.model.error

enum class CommonError(var value: Int) {
    NETWORK_ERROR(1),
    RESULT_ERROR(2),
    SERVER_ERROR(3),
    UNAUTHENTICATED(4),
    BAD_REQUEST(5),
    UNKNOWN(8),
    PROMO_CODE_ERROR(9),
    SERVER_MAINTENANCE(10);

    fun fromInt(i: Int): CommonError {
        for (messageType in values()) {
            if (messageType.value == i) {
                return messageType
            }
        }
        return UNKNOWN
    }
}