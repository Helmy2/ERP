package org.example.erp.core.domain.exceptions

interface ExceptionMapper {
    fun map(throwable: Throwable): Throwable
}