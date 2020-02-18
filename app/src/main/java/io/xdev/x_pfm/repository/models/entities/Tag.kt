package io.xdev.x_pfm.repository.models.entities

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import java.io.Serializable

class Tag : BaseObservable, Serializable {
    @get:Bindable
    var title: String? = null
    private var icon: Int = 0
    var color: Int = 0

    constructor(title: String, icon: Int, color: Int) {
        this.title = title
        this.icon = icon
        this.color = color
    }

    constructor()
}
