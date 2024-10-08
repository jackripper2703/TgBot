package org.example.state

object EventState {
    var isWaitingForDescription: Boolean = false
    var selectedDate: String? = null

    fun resetEventState() {
        isWaitingForDescription = false
        selectedDate = null
    }
}
