package com.devbilal.domain.usecase.diary

import com.devbilal.domain.repository.DiaryEntryRepository
import com.devbilal.domain.util.today
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus

class GetStreakCountUseCase(
    private val repository: DiaryEntryRepository
) {
    operator fun invoke(): Flow<Int> {
        return repository.getAllEntries().map { entries ->
            if (entries.isEmpty()) return@map 0

            val dates = entries.map { it.date }.distinct().sortedDescending()
            val today = LocalDate.today()
            
            if (dates.isEmpty()) return@map 0
            
            val latestDate = dates.first()

            // If the latest entry is not today or yesterday, the streak is broken
            if (latestDate != today && latestDate != today.minus(1, DateTimeUnit.DAY)) {
                return@map 0
            }

            var streak = 0
            for (i in dates.indices) {
                val expectedDate = latestDate.minus(i, DateTimeUnit.DAY)
                if (dates[i] == expectedDate) {
                    streak++
                } else {
                    break
                }
            }
            streak
        }
    }
}
