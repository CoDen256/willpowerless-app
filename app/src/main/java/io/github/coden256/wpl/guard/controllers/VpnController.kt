package io.github.coden256.wpl.guard.controllers

import android.util.Log
import io.github.coden256.wpl.guard.core.Owner
import io.github.coden256.wpl.judge.Action
import io.github.coden256.wpl.judge.JudgeRuling

class VpnController(
    private val owner: Owner
) {
    fun onRulings(rulings: List<JudgeRuling>){
        val target = rulings.firstOrNull {it.action == Action.FORCE}
        Log.i("GuardVpnController", "Setting vpn by: $target out of $rulings")

        target?.let { owner.setAlwaysOnVpn(it.path) }

        rulings.forEach {
            if (it.action == Action.BLOCK){
                owner.removeAlwaysOnVpn(it.path)
            }
        }
    }
}