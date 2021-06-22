package com.plcoding.spotifycloneyt.other

open class Event<out T>(private val data: T) { // explanation 7:24 https://www.youtube.com/watch?v=ujSaJ1f7j8c&list=PLQkwcJG4YTCT-lTlkOmE-PpRkuyNXq6sW&index=10&ab_channel=PhilippLacknerPhilippLackner

    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled){
            null
        } else {
            hasBeenHandled = true
            data
        }
    }

    fun peekContent() = data
}