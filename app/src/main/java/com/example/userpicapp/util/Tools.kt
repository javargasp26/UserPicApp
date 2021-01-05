package com.example.userpicapp.util

import androidx.core.util.Preconditions.checkArgument

class Tools {

    fun getDate(date : String) : String{
        var newDate = date.substring(4,10)
        val dateDay = newDate.substring(4)
        val suffix = getDayOfMonthSuffix(dateDay.toInt())

        newDate = newDate.substring(0,4) + dateDay.toInt() + suffix
        return newDate
    }

    private fun getDayOfMonthSuffix(n: Int): String? {

        return if (n in 11..13) {
            "th"
        } else when (n % 10) {
            1 -> "st"
            2 -> "nd"
            3 -> "rd"
            else -> "th"
        }
    }
}