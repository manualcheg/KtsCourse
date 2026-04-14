package com.manualcheg.ktscourse.common.util

class NameHelper {
    fun getRocketName(id: String?): String {
        return when (id) {
            "5e9d0d95eda69955f709d1eb" -> "Falcon 1"
            "5e9d0d95eda69973a8c74197", "5e9d0d95eda69973a809d1ec" -> "Falcon 9"
            "5e9d0d95eda69974dbd83851", "5e9d0d95eda69974db09d1ed" -> "Falcon Heavy"
            "5e9d0d96eda699382d09d1ee" -> "Starship"
            else -> "Rocket ${id ?: "Unknown"}"
        }
    }

    fun getLaunchpadName(id: String?): String {
        return when (id) {
            "5e9e4501f509094ba4566f84" -> "VAFB SLC 4E"
            "5e9e4502f5090995de566f86" -> "KSC LC 39A"
            "5e9e4502f509094188566f88" -> "CCSFS SLC 40"
            "5e9e4502f509092b78566f87" -> "Kwajalein Atoll"
            "5e9e4502f509090123566f89" -> "Starbase"
            else -> "Launchpad ${id ?: "Unknown"}"
        }
    }
}
