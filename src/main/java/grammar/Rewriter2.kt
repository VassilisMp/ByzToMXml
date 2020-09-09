package grammar

import grammar.ByzLexer.*
import org.antlr.v4.runtime.TokenStream
import org.antlr.v4.runtime.TokenStreamRewriter
import org.antlr.v4.runtime.tree.ParseTree

class Rewriter2(tokens: TokenStream) : ByzBaseListener() {
    internal val rewriter: TokenStreamRewriter = TokenStreamRewriter(tokens)
    val text: StringBuilder = StringBuilder()

    /*override fun exitClusterType2(ctx: ByzParser.ClusterType2Context) {
        println(ctx.text)
    }*/

    override fun enterQChar(ctx: ByzParser.QCharContext) {
        val firstChild = ctx.getChild(0)
        if (firstChild is ByzParser.KentimaToTheRightOfOligonContext)
            rewriter.replace(firstChild.start, firstChild.stop, Companion.plus2)
        else when(ctx.start.type) {
            ISON_NEO -> ctx.replace(Companion.ison)
            APOSTROFOS_NEO -> ctx.replace(Companion.minus1)
            YPORROI, YPORROI_OVER_OLIGON, YPORROI_OVER_PETASTI -> ctx.replaceMultiple(Companion.minus1)
            OLIGON_NEO, PETASTI, KENTIMATA_NEO_MESO -> ctx.replace(Companion.plus1)
            KENTIMA_UNDER_OLIGON, OLIGON_OVER_PETASTI, ANTIKENOMA_UNDER_KENTIMA_UNDER_OLIGON -> ctx.replace(Companion.plus2)
            OLIGON_ABOVE_KENTIMATA -> {
                val gorgotita = ctx.getParent().children.firstOrNull { it is ByzParser.GorgotitaContext }
                        ?.asGorgotitaContext()?.delete()?.text?:""
                ctx.replaceMultiple(Companion.plus1, gorgotita + Companion.plus1)
            }
            KENTIMATA_ABOVE_OLIGON -> ctx.replaceMultiple(Companion.plus1)
            /*KENTIMA_OVER_OLIGON, */KENTIMA_OVER_PETASTI -> ctx.replace(Companion.plus3)
            /*YPSILI_AT_RIGHT_END_OF_OLIGON, */YPSILI_AT_RIGHT_END_OF_PETASTI -> ctx.replace(Companion.plus4)
            /*YPSILI_AT_LEFT_END_OF_OLIGON, */YPSILI_AT_LEFT_END_OF_PETASTI -> ctx.replace(Companion.plus5)
            /*YPSILI_NEXT_TO_KENTIMA_OVER_OLIGON, */YPSILI_NEXT_TO_KENTIMA_OVER_PETASTI -> ctx.replace(Companion.plus6)
            /*YPSILI_OVER_KENTIMA_OVER_OLIGON, */YPSILI_OVER_KENTIMA_OVER_PETASTI -> ctx.replace(Companion.plus7)
            /*TWO_IPSILES_OVER_OLIGON, */TWO_IPSILES_OVER_PETASTI -> ctx.replace(Companion.plus8)
            /*TWO_IPSILES_OVER_KETNIMATA_OVER_OLIGON, */TWO_IPSILES_OVER_KETNIMATA_OVER_PETASTI -> ctx.replace(Companion.plus9)
//            YPSILI_OVER_KENTIMA_OVER_OLIGON_AND_YPSILI_TO_LEFT -> {}
//            YPSILI_OVER_KENTIMA_OVER_PETASTI_AND_YPSILI_RIGHT -> {}
            THREE_YPSILES_OVER_OLIGON, THREE_YPSILES_OVER_PETASTI -> ctx.replace(Companion.plus12)
            THREE_YPSILES_OVER_OLIGON_KENTIMATA_IN_MIDDLE, THREE_YPSILES_OVER_PETASTI_KENTIMATA_IN_MIDDLE -> ctx.replace(Companion.plus13)
            CONTINUOUS_ELAFRON, CONTINUOUS_ELAFRON_OVER_PETASTI -> ctx.replace(Companion.minus1 + Companion.gorgon + Companion.minus1)
            APOSTROPHOS_OVER_PETASTI, APOSTROPHOS_OVER_OLIGON -> ctx.replace(Companion.minus1)
            ELAPHRON_OVER_PETASTI, ELAPHRON_OVER_OLIGON -> ctx.replace(Companion.minus2)
            ELAPHRON_AND_KENTIMATA_OVER_OLIGON -> ctx.replaceMultiple(Companion.minus2, Companion.plus1)
            TWO_APOSTROPHOI_IN_A_ROW -> ctx.replaceMultiple(Companion.minus1)
            ELAPHRON_OVER_APOSTROPHOS_OVER_ISON, ELAPHRON_OVER_APOSTROPHOS_OVER_PETASTI -> ctx.replace(Companion.minus3)
            HAMILI_OVER_OLIGON, HAMILI_OVER_PETASTI, CHAMILI -> ctx.replace(Companion.minus4)
//                HAMILI_OVER_HAMILI
            HAMILI_OVER_ELAPHRON_OVER_APOSTROPHOS_OVER_PETASTI -> ctx.replace(Companion.minus7)
            HAMILI_OVER_HAMILI_OVER_PETASTI -> ctx.replace(Companion.minus8)
            HAMILI_OVER_HAMILI_OVER_APOSTROPHOS_OVER_PETASTI -> ctx.replace(Companion.minus9)
            ISON_OVER_PETASTI, ISON_OVER_OLIGON -> ctx.replace(Companion.ison)
            APOSTROPHOS_UNDER_ISON -> ctx.replaceMultiple(Companion.ison, Companion.minus1)
            APOSTROPHOS_AND_KENTIMATA_OVER_OLIGON -> ctx.replaceMultiple(Companion.minus1, Companion.plus1)
            ISON_AND_KENTIMATA_OVER_OLIGON -> ctx.replaceMultiple(Companion.ison)
            CONTINUOUS_ELAFRON_AND_KENTIMATA_OVER_OLIGON -> ctx.replace(Companion.minus1 + Companion.gorgon + Companion.minus1)
            ELAPHRON_OVER_APOSTROPHOS_AND_KENTIMATA_OVER_OLIGON -> ctx.replaceMultiple(Companion.minus3, Companion.plus1)
            HAMILI_AND_KENTIMATA_OVER_OLIGON -> ctx.replaceMultiple(Companion.minus4, Companion.plus1)
            YPORROI_AND_KENTIMATA_OVER_OLIGON -> {
                val gorgotita = ctx.getParent().children.firstOrNull { it is ByzParser.GorgotitaContext }
                        ?.asGorgotitaContext()?.delete()?.text?:""
                ctx.replaceMultiple(Companion.minus1, gorgotita + Companion.minus1 + Companion.plus1)
            }
        }
    }

    override fun exitClusterType2(ctx: ByzParser.ClusterType2Context) {
        text.append(ctx.qChar().text)
                .append(ctx.syllable().firstOrNull()?.text?:"")
                .append(ctx.tChar().joinToString(separator = "", transform = { it.text }))
                .append(ctx.fthoraMeEndeixi().firstOrNull()?.text?:"")
    }

    private fun ByzParser.QCharContext.replaceMultiple(first: String, second: String = first, fthoraPlace: Boolean = false) =
        with(getParent() as ByzParser.ClusterType2Context) {
            val fthora = fthoraMeEndeixi().firstOrNull()?.also { rewriter.delete(it.start, it.stop) }?.text ?: ""
            val syllable = syllable().firstOrNull()?.also { rewriter.delete(it.start, it.stop) }?.text ?: ""
            if (!fthoraPlace) rewriter.replace(this.start.tokenIndex, first + syllable + fthora + second)
            else rewriter.replace(this.start.tokenIndex, first + syllable + second + fthora)
        }

    private fun ByzParser.QCharContext.replace(replacement: String) = rewriter.replace(this.start.tokenIndex, replacement)

    private fun ByzParser.GorgotitaContext.delete(): ByzParser.GorgotitaContext {
        rewriter.delete(this.start.tokenIndex)
        return this
    }

    private fun ParseTree.asGorgotitaContext(): ByzParser.GorgotitaContext? =
        if (this is ByzParser.GorgotitaContext) this else null

    companion object {
        val plus1 = "\uD834\uDC47" // oligon 1
        val plus2 = Companion.plus1 + "\uD834\uDCF1" // kentimaUnderOligon 2
        val plus3 = "B102" // kentimaOverOligon 3
        val plus4 = "B103" // YPSILI_AT_RIGHT_END_OF_OLIGon 4
        val plus5 = "L115" // YPSILI_AT_LEFT_END_OF_OLIGon 5
        val plus6 = "L100" // YPSILI_NEXT_TO_KENTIMA_OVER_OLIGON 6
        val plus7 = "L102" // YPSILI_OVER_KENTIMA_OVER_OLIGON 7
        val plus8 = "L103" // TWO_IPSILES_OVER_OLIGON 8
        val plus9 = "L104" // TWO_IPSILES_OVER_KETNIMATA_OVER_OLIGON
        val plus12 = "L108" // THREE_YPSILES_OVER_OLIGON 12
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
    }
}