package parser

import grammar.ByzBaseVisitor
import grammar.ByzParser.*
import org.antlr.v4.runtime.tree.ParseTree

class GorgotitesVisitor: ByzBaseVisitor<Tchar?>() {

    override fun visit(tree: ParseTree?): Tchar? = if (tree != null) super.visit(tree) else null
    override fun visitGorgon(ctx: GorgonContext?) = gorgon()
    override fun visitGorgonParestigmenoAristera(ctx: GorgonParestigmenoAristeraContext?) = Tchar(1, 2, false)
    override fun visitGorgonParestigmenoDexia(ctx: GorgonParestigmenoDexiaContext?) = Tchar(2, 2, false)
    override fun visitDigorgon(ctx: DigorgonContext?) = Tchar(0, 3, false)
    override fun visitDigorgonParestigmenoAristeraKato(ctx: DigorgonParestigmenoAristeraKatoContext?) = Tchar(1, 3, false)
    override fun visitDigorgonParestigmenoAristeraAno(ctx: DigorgonParestigmenoAristeraAnoContext?) = Tchar(2, 3, false)
    override fun visitDigorgonParestigmenoDexia(ctx: DigorgonParestigmenoDexiaContext?) = Tchar(3, 3, false)
    override fun visitTrigorgon(ctx: TrigorgonContext?) = Tchar(0, 4, false)
    override fun visitTrigorgonParestigmenoAristeraKato(ctx: TrigorgonParestigmenoAristeraKatoContext?) = Tchar(1, 4, false)
    override fun visitTrigorgonParestigmenoAristeraPano(ctx: TrigorgonParestigmenoAristeraPanoContext?) = Tchar(3, 4, false)
    override fun visitTrigorgonParestigmenoDexia(ctx: TrigorgonParestigmenoDexiaContext?) = Tchar(4, 4, false)
    override fun visitArgon(ctx: ArgonContext?) = Tchar(0, -1, true)
    override fun visitImiDiargon(ctx: ImiDiargonContext?) = Tchar(0, -2, true)
    override fun visitDiargon(ctx: DiargonContext?) = Tchar(0, -3, true)

    companion object {
        fun gorgon() = Tchar(0, 2, false)
    }
}