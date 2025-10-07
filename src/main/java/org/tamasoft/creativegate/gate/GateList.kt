package org.tamasoft.creativegate.gate

data class GateList(var gates: List<Gate>) {
    @Suppress("unused") // Jackson uses this
    constructor() : this(ArrayList())
}