package com.example.purplebank.data.transaction

import java.io.Serializable

data class Amount(
    val units: Int,
    val subUnits: Int
) : Serializable