package parser

import grammar.ByzBaseVisitor
import grammar.ByzParser.ClusterType2Context
import grammar.ByzParser.NewArktikiMartyriaContext

// replace all characters with the simplest ones
class ScoreVisitor : ByzBaseVisitor<Unit>() {

    val text: StringBuilder = StringBuilder()
    var missingLetter: String? = null
    val elements: MutableList<Any> = mutableListOf()
    private val quantityCharVisitor = QuantityCharVisitor()

    override fun visitClusterType2(ctx: ClusterType2Context) {
//        print(ctx.text + "  ")
        // concatenate arxigramma and syllables and possible missing letter that was on the previous cluster by mistake
        var syllable: String = ctx.getArxigramma() +
                (missingLetter ?: "") +
                ctx.syllable().joinToString(separator = "", transform = { it.text })
        if (missingLetter != null) missingLetter = null
        // if two last letters of the syllable are the same it means, the last belongs to the next note
        if (syllable.length>1) syllable.takeLast(2).let {
            if (it[0] == it[1]) {
                syllable = syllable.dropLast(1)
                missingLetter = it[0].toString()
            }
        }
        val gorgotita = ctx.gorgotita()
//        println(gorgotita)
        val argia = ctx.argies()
//        println(argia)
        elements.addAll(quantityCharVisitor.visit(syllable = syllable, gorgotita = gorgotita, argia = argia, tree = ctx.qChar()))
    }

    private fun ClusterType2Context.getArxigramma(): String = (this.ARXIGRAMMA()?.text?.drop(1) ?: "")
    private fun ClusterType2Context.gorgotita(): Tchar? = tChar().map { gorgotitesVisitor.visit(it) }.firstOrNull()
    private fun ClusterType2Context.argies(): Tchar? = tChar().map { argiesVisitor.visit(it) }.firstOrNull()

    private companion object {
        private val argiesVisitor = ArgiesVisitor()
        private val gorgotitesVisitor = GorgotitesVisitor()
        const val plus1 = "\uD834\uDC47" // oligon 1
        const val plus2 = plus1 + "\uD834\uDCF1" // kentimaUnderOligon 2
        const val plus3 = "B102" // kentimaOverOligon 3
        const val plus4 = "B103" // YPSILI_AT_RIGHT_END_OF_OLIGon 4
        const val plus5 = "L115" // YPSILI_AT_LEFT_END_OF_OLIGon 5
        const val plus6 = "L100" // YPSILI_NEXT_TO_KENTIMA_OVER_OLIGON 6
        const val plus7 = "L102" // YPSILI_OVER_KENTIMA_OVER_OLIGON 7
        const val plus8 = "L103" // TWO_IPSILES_OVER_OLIGON 8
        const val plus9 = "L104" // TWO_IPSILES_OVER_KETNIMATA_OVER_OLIGON
        const val plus12 = "L108" // THREE_YPSILES_OVER_OLIGON 12
        const val plus13 = "L059" // THREE_YPSILES_OVER_OLIGON_KENTIMATA_IN_MIDDle 13
        const val minus1 = "\uD834\uDC51" // apostrophos -1
        const val minus2 = "\uD834\uDC55" // elafron -2
        const val minus3 = "B108" // ELAPHRON_OVER_APOSTROPHOos -3
        const val minus4 = "\uD834\uDC56" // hamili -4
        const val minus7 = "L118" // HAMILI_OVER_ELAPHRON_OVER_APOSTROPHos -7
        const val minus8 = "L098" // HAMILI_OVER_HAMIli -8
        const val minus9 = "L110" // HAMILI_OVER_HAMILI_OVER_APOSTROPHos -9
        const val ison = "𝁆" // 0

        const val gorgon = "\uD834\uDC8F"
        const val gorgonParestigmenoAristera = "\uD834\uDC90"
        const val gorgonParestigmenoDexia = "\uD834\uDC91"
        const val digorgon = "\uD834\uDC92"
        const val digorgonParestigmenoAristeraKato = "\uD834\uDC93"
        const val digorgonParestigmenoAristeraAno = "\uD834\uDC94"
        const val digorgonParestigmenoDexia = "\uD834\uDC95"
        const val trigorgon = "\uD834\uDC96"
        const val trigorgonParestigmenoAristeraKato = "L166"
        const val trigorgonParestigmenoAristeraPano = "L056"
        const val trigorgonParestigmenoDexia = "L057"
        const val ARGON = "\uD834\uDC97"
        const val IMIDIARGON = "\uD834\uDC98"
        const val DIARGON = "\uD834\uDC99"
    }

    class ArktikiMartyriaVisitor: ByzBaseVisitor<Unit>() {
        override fun visitNewArktikiMartyria(ctx: NewArktikiMartyriaContext?) = null
    }
}