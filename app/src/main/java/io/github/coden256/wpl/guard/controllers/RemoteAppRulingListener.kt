package io.github.coden256.wpl.guard.controllers

import android.content.Context
import android.util.Log
import io.github.coden256.wpl.guard.GuardClientConnector
import io.github.coden256.wpl.guard.Ruling
import io.github.coden256.wpl.judge.JudgeRuling

class RemoteAppRulingListener (
    val target: String
) {

    private val connector = GuardClientConnector(target)

    fun onRulings(context: Context, rulings: List<JudgeRuling>) {
        connector.bind(context,
            onConnected = { client ->
                Log.i("GuardRemoteAppRulingListener", "Sending rulings: $rulings")
                client.onRulings(rulings.map { it.toRuling() })

                connector.unbind(context)
            },
            onDisconnected = {}
        )
    }

    fun JudgeRuling.toRuling() = Ruling().also {
        it.action = action.toString()
        it.path = path
    }
}