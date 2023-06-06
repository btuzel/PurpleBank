package com.example.purplebank

import androidx.compose.ui.test.junit4.ComposeTestRule
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

abstract class BaseRobot(val composeTestRule: ComposeTestRule)

@OptIn(ExperimentalContracts::class)
inline fun <T : BaseRobot, R> T.execute(block: T.() -> R) {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    block()
}