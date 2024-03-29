package com.example.formulaone.data.api.circuitoApi

import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CircuitoApiRepository @Inject constructor(private val service: CircuitoService){

    suspend fun getAllCircuitos(): List<CircuitoApiModel> {
        val simpleList = service.api.getAllCircuitos()
        return simpleList.mrdata.raceTable.races.mapNotNull { circuitoListItem ->
            try {
                val response = service.api.getDetailCircuito(circuitoListItem.round)
                response.mrdata.raceTable.races[0].asApiModel()
            } catch (e: Exception) {
                Log.e("Error", "Error al obtener detalles del circuito para la ronda ${circuitoListItem.round}", e)
                null
            }
        }
    }
}