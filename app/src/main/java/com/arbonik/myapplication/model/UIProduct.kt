package com.arbonik.myapplication.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.arbonik.myapplication.BR

class UIProduct : BaseObservable(), Product{
    @get:Bindable
    override var weight : String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.weight)
        }

    @get:Bindable
    override var height : String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.height)
        }

    @get:Bindable
    override var width : String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.width)
        }

    @get:Bindable
    override var lenght : String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.lenght)
        }
}