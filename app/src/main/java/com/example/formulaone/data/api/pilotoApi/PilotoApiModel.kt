package com.example.formulaone.data.api.pilotoApi

import com.example.formulaone.data.api.circuitoApi.CircuitoApiModel
import com.example.formulaone.data.local.CircuitoEntity
import com.example.formulaone.data.local.PilotoEntity
import com.google.gson.annotations.SerializedName

data class PilotoApiModel(
    val driverId: String,
    val permanentNumber: String,
    val code: String,
    val name: String,
    val surname: String,
    val fecNac: String,
    val nation: String
)

data class PilotoListApiModel(
    val pilotoList: List<PilotoApiModel>
)

data class Root(
    @SerializedName("MRData")
    val mrdata: Mrdata,
)

data class Mrdata(
    val xmlns: String,
    val series: String,
    val url: String,
    val limit: String,
    val offset: String,
    val total: String,
    @SerializedName("DriverTable")
    val driverTable: DriverTable,
)

data class DriverTable(
    val season: String,
    @SerializedName("Drivers")
    val drivers: List<Driver>,
)

data class Driver(
    val driverId: String,
    val permanentNumber: String,
    val code: String,
    val givenName: String,
    val familyName: String,
    val dateOfBirth: String,
    val nationality: String,
){
    fun asApiModel(): PilotoApiModel {
        return PilotoApiModel(
            driverId,
            permanentNumber,
            code,
            givenName,
            familyName,
            dateOfBirth,
            nationality
        )
    }
}

fun List<PilotoApiModel>.asEntityModelList(): List<PilotoEntity> {
    return this.map {
        PilotoEntity(
            it.driverId,
            it.permanentNumber,
            it.code,
            it.name,
            it.surname,
            it.fecNac,
            it.nation,
        )
    }
}