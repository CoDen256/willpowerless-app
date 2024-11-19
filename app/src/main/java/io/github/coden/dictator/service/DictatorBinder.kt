package io.github.coden.dictator.service

object DictatorBinder: Dictator.Stub() {
    override fun shouldBlock(): Boolean {
        return true
    }
}