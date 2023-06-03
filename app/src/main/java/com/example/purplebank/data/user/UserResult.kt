package com.example.purplebank.data.user

sealed class UserResult {
    data class Success(val user: User) : UserResult()
    data class Failure(val failureReason: String) : UserResult()
}