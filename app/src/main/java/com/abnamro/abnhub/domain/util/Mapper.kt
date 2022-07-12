package com.abnamro.abnhub.domain.util

interface Mapper<T, R> {

    fun map(t: T): R

    fun mapList(list: List<T>): List<R> {
        return list.map { map(it) }
    }
}
