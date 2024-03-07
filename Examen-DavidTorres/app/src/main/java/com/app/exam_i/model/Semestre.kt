package com.app.exam_i.model

import android.os.Parcel
import android.os.Parcelable

data class Semestre(
    var nombre: String,
    var año: Int,
    var fechaInicio: String,
    var activo: Boolean,
    var numeroTotalMateria: MutableList<Materia>
):
    Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString()!!,
            parcel.readInt(),
            parcel.readString()!!,
            parcel.readByte() != 0.toByte(),
            mutableListOf<Materia>().apply {
                parcel.readList(this, Materia::class.java.classLoader)
            }
        )

    override fun toString(): String {
        return "${nombre} - ${año}"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeInt(año)
        parcel.writeString(fechaInicio.toString())
        parcel.writeByte(if (activo) 1 else 0)
        parcel.writeList(numeroTotalMateria)
    }
    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Semestre> {
        override fun createFromParcel(parcel: Parcel): Semestre {
            return Semestre(parcel)
        }

        override fun newArray(size: Int): Array<Semestre?> {
            return arrayOfNulls(size)
        }
    }
}