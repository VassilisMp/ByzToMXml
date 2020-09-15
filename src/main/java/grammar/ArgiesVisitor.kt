package grammar

import grammar.ByzParser.*

class ArgiesVisitor: ByzBaseVisitor<Tchar>() {

    override fun visitKlasma(ctx: KlasmaContext?) = Tchar(0, -1, false)
    override fun visitApli(ctx: ApliContext?) = Tchar(0, -1, false)
    override fun visitDipli(ctx: DipliContext?) = Tchar(0, -2, false)
    override fun visitTripli(ctx: TripliContext?) = Tchar(0, -3, false)
}