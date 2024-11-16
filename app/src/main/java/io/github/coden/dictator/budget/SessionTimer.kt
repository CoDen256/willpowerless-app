package io.github.coden.dictator.budget

import android.os.CountDownTimer

class SessionTimer(private val totalTime: Int, private val onTick: (Int) -> Unit, private val onFinish: () -> Unit) {
    private val countDownTimer = object : CountDownTimer(totalTime * 1000L, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            val remainingTime = (millisUntilFinished / 1000).toInt()
            onTick(remainingTime)
        }

        override fun onFinish() {
           onFinish.invoke()
        }
    }

    fun start() {
        countDownTimer.start()
    }

    fun cancel() {
        countDownTimer.cancel()
    }
}