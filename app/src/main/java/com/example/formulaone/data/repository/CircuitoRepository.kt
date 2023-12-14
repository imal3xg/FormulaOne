package com.example.formulaone.data.repository

import com.example.formulaone.data.api.circuitoApi.CircuitoApiRepository
import com.example.formulaone.data.api.circuitoApi.asEntityModelList
import com.example.formulaone.data.api.pilotoApi.PilotoApiRepository
import com.example.formulaone.data.api.pilotoApi.asEntityModelList
import com.example.formulaone.data.local.CircuitoDBRepository
import com.example.formulaone.data.local.asListCircuit
import com.example.formulaone.data.local.asListPiloto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CircuitoRepository @Inject constructor(
    private val dbRepository: CircuitoDBRepository,
    private val apiRepository: CircuitoApiRepository,
    private val apiRepository2: PilotoApiRepository
) {
    val allCircuitos: Flow<List<Circuito>>
        get() = dbRepository.allCircuitos
            .map { entities ->
                withContext(Dispatchers.Default) {
                    entities.asListCircuit()
                }
            }

    val allPilotos: Flow<List<Piloto>>
        get() = dbRepository.allPilotos
            .map { entities ->
                withContext(Dispatchers.Default) {
                    entities.asListPiloto()
                }
            }


    suspend fun refreshList() {
        withContext(Dispatchers.IO) {
            try {
                val apiCircuito = apiRepository.getAllCircuitos()
                val apiPiloto = apiRepository2.getAllPilotos()
                dbRepository.insert(apiCircuito.asEntityModelList())
                dbRepository.insertt(apiPiloto.asEntityModelList())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}