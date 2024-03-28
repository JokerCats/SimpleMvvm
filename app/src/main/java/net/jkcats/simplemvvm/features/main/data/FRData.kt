package net.jkcats.simplemvvm.features.main.data

import android.graphics.Rect

class FRData {
    companion object {
        const val UNKNOWN_NAME = "other"
    }

    var idx: Int = 0
    var conf: String? = null
    var mask: String? = null //does user wear mask (support on 1.4620.100JP.1 later version)
    var name: String? = null //user name  (if stranger, this value will be "null" or "@#$")
    var rect: Rect? = null //face rect
    var age: String? = null
    var gender: String? = null
    var faceid: Long = 0
}