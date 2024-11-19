package com.stellerbyte.uptodo.services.module

interface LogService {
    fun logNonFatalCrash(throwable: Throwable)
}