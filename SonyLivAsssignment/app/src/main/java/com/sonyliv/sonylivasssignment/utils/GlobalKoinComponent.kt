package com.sonyliv.sonylivasssignment.utils

import androidx.work.WorkManager
import org.koin.core.KoinComponent
import org.koin.core.inject

object GlobalKoinComponent : KoinComponent {
    val workManager: WorkManager by inject()
}

