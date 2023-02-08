package com.example.uptodo.services.module

interface LogService {
    fun logNonFatalCrash(throwable: Throwable)
}