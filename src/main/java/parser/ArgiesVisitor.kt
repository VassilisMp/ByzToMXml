package parser

import grammar.ByzBaseVisitor
import grammar.ByzParser.*

class ArgiesVisitor: ByzBaseVisitor<Tchar>() {

    override fun visitKlasma(ctx: KlasmaContext?) = apli
    override fun visitApli(ctx: ApliContext?) = apli
    override fun visitDipli(ctx: DipliContext?) = Tchar(0, -2, false)
    override fun visitTripli(ctx: TripliContext?) = Tchar(0, -3, false)

    companion object {
        val apli = Tchar(0, -1, false)
    }
}