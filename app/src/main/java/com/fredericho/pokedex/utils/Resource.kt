package com.fredericho.pokedex.utils

sealed interface Resource<out T> {
    object Loading : Resource<Nothing>
    open class Error(open val message: String) : Resource<Nothing>
    data class Success<T>(val data: T) : Resource<T>
}