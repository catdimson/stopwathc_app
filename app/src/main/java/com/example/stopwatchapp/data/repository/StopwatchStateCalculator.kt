package com.example.stopwatchapp.data.repository

import com.example.stopwatchapp.data.ElapsedTimeCalculator
import com.example.stopwatchapp.domain.TimestampProvider
import com.example.stopwatchapp.ui.StopwatchState

class StopwatchStateCalculator(
    private val timestampProvider: TimestampProvider,
    private val elapsedTimeCalculator: ElapsedTimeCalculator,
) {
    fun calculateRunningState(oldState: StopwatchState): StopwatchState.Running =
        when (oldState) {
            is StopwatchState.Running -> oldState
            is StopwatchState.Paused -> {
                StopwatchState.Running(
                    startTime = timestampProvider.getMilliseconds(),
                    elapsedTime = oldState.elapsedTime
                )
            }
        }

    fun calculatePausedState(oldState: StopwatchState): StopwatchState.Paused =
        when (oldState) {
            is StopwatchState.Running -> {
                val elapsedTime = elapsedTimeCalculator.calculate(oldState)
                StopwatchState.Paused(elapsedTime = elapsedTime)
            }
            is StopwatchState.Paused -> oldState
        }
}