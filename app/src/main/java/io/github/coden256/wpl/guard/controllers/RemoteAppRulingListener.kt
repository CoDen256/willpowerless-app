package io.github.coden256.wpl.guard.controllers

import android.content.Context
import io.github.coden256.wpl.guard.GuardClientConnector
import io.github.coden256.wpl.guard.Ruling
import io.github.coden256.wpl.judge.JudgeRuling

class RemoteAppRulingListener (
    val target: String
) {

    private val connector = GuardClientConnector(target)

    fun connect(context: Context) = connector.connect(context)

    fun disconnect(context: Context) = connector.diconnect(context)

    fun onRulings(rulings: List<JudgeRuling>) {
        connector()?.onRulings(rulings.map { it.toRuling()})
    }

    fun JudgeRuling.toRuling() = Ruling().also {
        it.action = action.toString()
        it.path = path
    }
}