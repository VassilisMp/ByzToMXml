package parser

import grammar.ByzBaseVisitor
import grammar.ByzParser.*

class ArgiesVisitor: ByzBaseVisitor<Tchar?>() {

    override fun visitKlasma(ctx: KlasmaContext?) = Apli()
    override fun visitApli(ctx: ApliContext?) = Apli()
    override fun visitDipli(ctx: DipliContext?) = Tchar(0, -2, false)
    override fun visitTripli(ctx: TripliContext?) = Tchar(0, -3, false)

    companion object {
        fun Apli() = Tchar(0, -1, false)
    }
}