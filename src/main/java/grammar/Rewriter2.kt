package grammar

import grammar.ByzLexer.*
import org.antlr.v4.runtime.TokenStream
import org.antlr.v4.runtime.TokenStreamRewriter
import org.antlr.v4.runtime.tree.ParseTree

class Rewriter2(tokens: TokenStream) : ByzBaseListener() {
    internal val rewriter: TokenStreamRewriter = TokenStreamRewriter(tokens)
    val plus1 = "\uD834\uDC47" // oligon 1
    val plus2 = plus1 + "\uD834\uDCF1" // kentimaUnderOligon 2
    val plus3 = "B102" // kentimaOverOligon 3
    val plus4 = "B103" // YPSILI_AT_RIGHT_END_OF_OLIGon 4
    val plus5 = "L115" // YPSILI_AT_LEFT_END_OF_OLIGon 5
    val plus13 = "L059" // THREE_YPSILES_OVER_OLIGON_KENTIMATA_IN_MIDDle 13
    val minus1 = "\uD834\uDC51" // apostrophos -1
    val gorgon = "\uD834\uDC8F"
    val minus2 = "\uD834\uDC55" // elafron -2
    val minus3 = "B108" // ELAPHRON_OVER_APOSTROPHOos -3
    val minus4 = "\uD834\uDC56" // hamili -4
    val minus7 = "L118" // HAMILI_OVER_ELAPHRON_OVER_APOSTROPHos -7
    val minus8 = "L098" // HAMILI_OVER_HAMIli -8
    val minus9 = "L110" // HAMILI_OVER_HAMILI_OVER_APOSTROPHos -9
    val ison = "𝁆" // 0

    override fun enterQChar(ctx: ByzParser.QCharContext) {
        val firstChild = ctx.getChild(0)
        if (firstChild is ByzParser.KentimaToTheRightOfOligonContext)
            rewriter.replace(firstChild.start, firstChild.stop, "\uD834\uDC47\uD834\uDCF1")
        else when(ctx.start.type) {
            ISON_NEO -> rewriter.replace(ctx.start.tokenIndex, "\uD834\uDC46")
            APOSTROFOS_NEO -> rewriter.replace(ctx.start.tokenIndex, "\uD834\uDC51")
            YPORROI, YPORROI_OVER_OLIGON, YPORROI_OVER_PETASTI -> ctx.ifParentHasSyllable(minus1)
            OLIGON_NEO, PETASTI, KENTIMATA_NEO_MESO -> rewriter.replace(ctx.start.tokenIndex, plus1)
            KENTIMA_UNDER_OLIGON, OLIGON_OVER_PETASTI, ANTIKENOMA_UNDER_KENTIMA_UNDER_OLIGON -> ctx.replace(plus2)
            OLIGON_ABOVE_KENTIMATA -> {
                val gorgotita = ctx.getParent().children.firstOrNull { it is ByzParser.GorgotitaContext }
                        ?.asGorgotitaContext()?.delete()?.text?:""
                ctx.ifParentHasSyllable(plus1, gorgotita + plus1)
            }
            KENTIMATA_ABOVE_OLIGON -> ctx.ifParentHasSyllable(plus1)
            /*KENTIMA_OVER_OLIGON, */KENTIMA_OVER_PETASTI -> ctx.replace(plus3)
            /*YPSILI_AT_RIGHT_END_OF_OLIGON, */YPSILI_AT_RIGHT_END_OF_PETASTI -> ctx.replace(plus4)
            /*YPSILI_AT_LEFT_END_OF_OLIGON, */YPSILI_AT_LEFT_END_OF_PETASTI -> ctx.replace(plus5)
            YPSILI_NEXT_TO_KENTIMA_OVER_OLIGON, YPSILI_NEXT_TO_KENTIMA_OVER_PETASTI -> {}
            YPSILI_OVER_KENTIMA_OVER_OLIGON, YPSILI_OVER_KENTIMA_OVER_PETASTI -> {}
            TWO_IPSILES_OVER_OLIGON, TWO_IPSILES_OVER_PETASTI -> {}
            TWO_IPSILES_OVER_KETNIMATA_OVER_OLIGON, TWO_IPSILES_OVER_KETNIMATA_OVER_PETASTI -> {}
            YPSILI_OVER_KENTIMA_OVER_OLIGON_AND_YPSILI_TO_LEFT -> {}
            YPSILI_OVER_KENTIMA_OVER_PETASTI_AND_YPSILI_RIGHT -> {}
            THREE_YPSILES_OVER_OLIGON, THREE_YPSILES_OVER_PETASTI -> rewriter.replace(ctx.start.tokenIndex, "L108")
            THREE_YPSILES_OVER_OLIGON_KENTIMATA_IN_MIDDLE, THREE_YPSILES_OVER_PETASTI_KENTIMATA_IN_MIDDLE ->
                ctx.replace(plus13)
            CONTINUOUS_ELAFRON, CONTINUOUS_ELAFRON_OVER_PETASTI -> rewriter.replace(ctx.start.tokenIndex, minus1 + gorgon + minus1)
            APOSTROPHOS_OVER_PETASTI, APOSTROPHOS_OVER_OLIGON -> rewriter.replace(ctx.start.tokenIndex, minus1)
            ELAPHRON_OVER_PETASTI, ELAPHRON_OVER_OLIGON -> rewriter.replace(ctx.start.tokenIndex, minus2)
            ELAPHRON_AND_KENTIMATA_OVER_OLIGON -> ctx.ifParentHasSyllable(minus2, plus1)
            TWO_APOSTROPHOI_IN_A_ROW -> ctx.ifParentHasSyllable(minus1)
            ELAPHRON_OVER_APOSTROPHOS_OVER_ISON, ELAPHRON_OVER_APOSTROPHOS_OVER_PETASTI -> ctx.replace(minus3)
            HAMILI_OVER_OLIGON, HAMILI_OVER_PETASTI, CHAMILI -> ctx.replace(minus4)
//                HAMILI_OVER_HAMILI
            HAMILI_OVER_ELAPHRON_OVER_APOSTROPHOS_OVER_PETASTI -> ctx.replace(minus7)
            HAMILI_OVER_HAMILI_OVER_PETASTI -> ctx.replace(minus8)
            HAMILI_OVER_HAMILI_OVER_APOSTROPHOS_OVER_PETASTI -> ctx.replace(minus9)
            ISON_OVER_PETASTI, ISON_OVER_OLIGON -> ctx.replace(ison)
            APOSTROPHOS_UNDER_ISON -> ctx.ifParentHasSyllable(ison, minus1)
            APOSTROPHOS_AND_KENTIMATA_OVER_OLIGON -> ctx.ifParentHasSyllable(minus1, plus1)
            ISON_AND_KENTIMATA_OVER_OLIGON -> ctx.ifParentHasSyllable(ison)
            CONTINUOUS_ELAFRON_AND_KENTIMATA_OVER_OLIGON -> ctx.replace(minus1 + gorgon + minus1)
            ELAPHRON_OVER_APOSTROPHOS_AND_KENTIMATA_OVER_OLIGON -> ctx.ifParentHasSyllable(minus3, plus1)
            HAMILI_AND_KENTIMATA_OVER_OLIGON -> ctx.ifParentHasSyllable(minus4, plus1)
            YPORROI_AND_KENTIMATA_OVER_OLIGON -> {
                val gorgotita = ctx.getParent().children.firstOrNull { it is ByzParser.GorgotitaContext }
                        ?.asGorgotitaContext()?.delete()?.text?:""
                ctx.ifParentHasSyllable(minus1, gorgotita + minus1 + plus1)
            }
        }
    }

    override fun enterGorgotita(ctx: ByzParser.GorgotitaContext) {
        when(ctx.start.type) {

        }
    }

    /*companion object {
        private val yporroiLiteralsMap = mapOf(

                GORGON_LEFT_DOT_USED_ON_YPORROI to "L170",
                GORGON_RIGHT_DOT_USED_ON_YPORROI to "L172",
                DIGORGON_USED_ON_YPORROI to "L165",
                DIGORGON_LEFT_DOT_USED_ON_YPORROI to "L167",
                TRIGORGON_USED_ON_YPORROI to "L168",
                TRIGORGON_LEFT_DOT_USED_ON_YPORROI to "L166",
                DIGORGON_USED_ON_L116_YPORROI  to "L163" ,
                TRIGORGON_USED_ON_L116_YPORROI  to "L162" ,
                GORGON_LEFT_DOT_USED_ON_L116_YPORROI  to "L161" ,
                GORGON_RIGHT_DOT_USED_ON_L116_YPORROI  to "L033" ,
                DIGORGON_LEFT_DOT_USED_ON_L116_YPORROI  to "L035" ,
                DIGORGON_RIGHT_DOT_USED_ON_L116_YPORROI  to "L037" ,
                TRIGORGON_LEFT_DOT_USED_ON_L116_YPORROI  to "L038" ,
                TRIGORGON_RIGHT_DOT_USED_ON_L116_YPORROI  to "L040"
        )
    }*/

    private fun ByzParser.QCharContext.ifParentHasSyllable(first: String, second: String = first) =
        getParent().children[1].let { child ->
            if (child is ByzParser.SyllableContext) {
                rewriter.delete(child.start, child.stop)
                rewriter.replace(this.start.tokenIndex, first + child.text + second)
            } else rewriter.replace(this.start.tokenIndex, first + second)
        }

    private fun ByzParser.QCharContext.replace(replacement: String) = rewriter.replace(this.start.tokenIndex, replacement)

    private fun ByzParser.GorgotitaContext.delete(): ByzParser.GorgotitaContext {
        rewriter.delete(this.start.tokenIndex)
        return this
    }

    private fun ParseTree.asGorgotitaContext(): ByzParser.GorgotitaContext? =
        if (this is ByzParser.GorgotitaContext) this else null
}