package grammar

import grammar.ByzLexer.*
import org.antlr.v4.runtime.tree.ParseTree

// replace all characters with the simplest ones
class Rewriter1 : ByzBaseListener() {
    
    val text: StringBuilder = StringBuilder()
    var missingLetter: String? = null

    override fun exitNewArktikiMartyria(ctx: ByzParser.NewArktikiMartyriaContext) {
        text.append(ctx.text)
        println(ctx.text)
    }

    override fun exitClusterType2(ctx: ByzParser.ClusterType2Context) {
        // concatenate arxigramma and syllables
        var syllable: String = (ctx.ARXIGRAMMA()?.text?.drop(1)?:"") + (missingLetter?:"") + ctx.syllable().joinToString(separator = "", transform = { it.text })
        if (missingLetter != null) missingLetter = null
        if (syllable.length>1) syllable.takeLast(2).let {
            if (it[0] == it[1]) {
                syllable = syllable.dropLast(1)
                missingLetter = it[0].toString()
            }
        }
        var fthora1 = ""
        var fthora2 = ""
        ctx.fthoraMeEndeixi().firstOrNull()?.let {
            if (it.fthora().yfesi() != null || it.fthora().diesi() != null) fthora1 = it.text
            else fthora2 = it.text
        }
        val fthora = fthora1 + fthora2
        val gorgotita = ctx.tChar().firstOrNull { it.gorgotita() != null }?.text ?: ""
        val argia = ctx.tChar().firstOrNull { it.argia() != null }?.text ?: ""
        val tChar = gorgotita + argia
        if (ctx.qChar() is ByzParser.KentimaToTheRightOfOligonContext)
            text.append(concatContexts(qChar1 = plus2, tChar1 = tChar, fthora1 = fthora, syllable1 = syllable))
        else when(ctx.qChar().start.type) {
            ISON_NEO ->
                text.append(concatContexts(qChar1 = ison, tChar1 = tChar, fthora1 = fthora, syllable1 = syllable))
            APOSTROFOS_NEO ->
                text.append(concatContexts(qChar1 = minus1, tChar1 = tChar, fthora1 = fthora, syllable1 = syllable))
            YPORROI, YPORROI_OVER_OLIGON, YPORROI_OVER_PETASTI ->
                text.append(concatContexts(qChar1 = minus1, tChar1 = tChar, fthora1 = fthora, qChar2 = minus1, syllable1 = syllable))
            OLIGON_NEO, PETASTI, KENTIMATA_NEO_MESO ->
                text.append(concatContexts(qChar1 = plus1, tChar1 = tChar, fthora1 = fthora, syllable1 = syllable))
            KENTIMA_UNDER_OLIGON, OLIGON_OVER_PETASTI, ANTIKENOMA_UNDER_KENTIMA_UNDER_OLIGON ->
                text.append(concatContexts(qChar1 = plus2, tChar1 = tChar, fthora1 = fthora, syllable1 = syllable))
            OLIGON_ABOVE_KENTIMATA ->
                text.append(concatContexts(qChar1 = plus1, tChar1 = gorgotita, fthora1 = fthora1, qChar2 = plus1, tChar2 = argia, fthora2 = fthora2, syllable1 = syllable))
            KENTIMATA_ABOVE_OLIGON ->
                text.append(concatContexts(qChar1 = plus1, fthora1 = fthora1, qChar2 = plus1, tChar2 = tChar, syllable1 = syllable))
            /*KENTIMA_OVER_OLIGON, */KENTIMA_OVER_PETASTI ->
                text.append(concatContexts(qChar1 = plus3, tChar1 = tChar, fthora1 = fthora, syllable1 = syllable))
            /*YPSILI_AT_RIGHT_END_OF_OLIGON, */YPSILI_AT_RIGHT_END_OF_PETASTI ->
                text.append(concatContexts(qChar1 = plus4, tChar1 = tChar, fthora1 = fthora, syllable1 = syllable))
            /*YPSILI_AT_LEFT_END_OF_OLIGON, */YPSILI_AT_LEFT_END_OF_PETASTI ->
                text.append(concatContexts(qChar1 = plus5, tChar1 = tChar, fthora1 = fthora, syllable1 = syllable))
            /*YPSILI_NEXT_TO_KENTIMA_OVER_OLIGON, */YPSILI_NEXT_TO_KENTIMA_OVER_PETASTI ->
                text.append(concatContexts(qChar1 = plus6, tChar1 = tChar, fthora1 = fthora, syllable1 = syllable))
            /*YPSILI_OVER_KENTIMA_OVER_OLIGON, */YPSILI_OVER_KENTIMA_OVER_PETASTI ->
                text.append(concatContexts(qChar1 = plus7, tChar1 = tChar, fthora1 = fthora, syllable1 = syllable))
            /*TWO_IPSILES_OVER_OLIGON, */TWO_IPSILES_OVER_PETASTI ->
                text.append(concatContexts(qChar1 = plus8, tChar1 = tChar, fthora1 = fthora, syllable1 = syllable))
            /*TWO_IPSILES_OVER_KETNIMATA_OVER_OLIGON, */TWO_IPSILES_OVER_KETNIMATA_OVER_PETASTI ->
                text.append(concatContexts(qChar1 = plus9, tChar1 = tChar, fthora1 = fthora, syllable1 = syllable))
//            YPSILI_OVER_KENTIMA_OVER_OLIGON_AND_YPSILI_TO_LEFT -> {}
//            YPSILI_OVER_KENTIMA_OVER_PETASTI_AND_YPSILI_RIGHT -> {}
            THREE_YPSILES_OVER_OLIGON, THREE_YPSILES_OVER_PETASTI ->
                text.append(concatContexts(qChar1 = plus12, tChar1 = tChar, fthora1 = fthora, syllable1 = syllable))
            THREE_YPSILES_OVER_OLIGON_KENTIMATA_IN_MIDDLE, THREE_YPSILES_OVER_PETASTI_KENTIMATA_IN_MIDDLE ->
                text.append(concatContexts(qChar1 = plus13, tChar1 = tChar, fthora1 = fthora, syllable1 = syllable))
            CONTINUOUS_ELAFRON, CONTINUOUS_ELAFRON_OVER_PETASTI ->
                text.append(concatContexts(qChar1 = minus1, tChar1 = gorgon, qChar2 = minus1, tChar2 = tChar, syllable2 = syllable))
            APOSTROPHOS_OVER_PETASTI, APOSTROPHOS_OVER_OLIGON ->
                text.append(concatContexts(qChar1 = minus1, tChar1 = tChar, fthora1 = fthora, syllable1 = syllable))
            ELAFRON, ELAPHRON_OVER_PETASTI, ELAPHRON_OVER_OLIGON ->
                text.append(concatContexts(qChar1 = minus2, tChar1 = tChar, fthora1 = fthora, syllable1 = syllable))
            ELAPHRON_AND_KENTIMATA_OVER_OLIGON ->
                text.append(concatContexts(qChar1 = minus2, fthora1 = fthora, qChar2 = plus1, tChar2 = tChar, syllable1 = syllable))
            TWO_APOSTROPHOI_IN_A_ROW ->
                text.append(concatContexts(qChar1 = minus1, fthora1 = fthora, qChar2 = minus1, tChar2 = tChar, syllable1 = syllable))
            // TODO continue from here
            ELAPHRON_OVER_APOSTROPHOS_OVER_ISON, ELAPHRON_OVER_APOSTROPHOS_OVER_PETASTI ->
                text.append(concatContexts(qChar1 = minus3, tChar1 = tChar, fthora1 = fthora, syllable1 = syllable))
            HAMILI_OVER_OLIGON, HAMILI_OVER_PETASTI, CHAMILI ->
                text.append(concatContexts(qChar1 = minus4, tChar1 = tChar, fthora1 = fthora, syllable1 = syllable))
//                HAMILI_OVER_HAMILI
            HAMILI_OVER_ELAPHRON_OVER_APOSTROPHOS_OVER_PETASTI ->
                text.append(concatContexts(qChar1 = minus7, tChar1 = tChar, fthora1 = fthora, syllable1 = syllable))
            HAMILI_OVER_HAMILI_OVER_PETASTI ->
                text.append(concatContexts(qChar1 = minus8, tChar1 = tChar, fthora1 = fthora, syllable1 = syllable))
            HAMILI_OVER_HAMILI_OVER_APOSTROPHOS_OVER_PETASTI ->
                text.append(concatContexts(qChar1 = minus9, tChar1 = tChar, fthora1 = fthora, syllable1 = syllable))
            ISON_OVER_PETASTI, ISON_OVER_OLIGON ->
                text.append(concatContexts(qChar1 = ison, tChar1 = tChar, fthora1 = fthora, syllable1 = syllable))
            APOSTROPHOS_UNDER_ISON ->
                text.append(concatContexts(qChar1 = ison, fthora2 = fthora, qChar2 = minus1, tChar2 = tChar, syllable1 = syllable))
            APOSTROPHOS_AND_KENTIMATA_OVER_OLIGON ->
                text.append(concatContexts(qChar1 = minus1, fthora1 = fthora, qChar2 = plus1, tChar2 = tChar, syllable1 = syllable))
            ISON_AND_KENTIMATA_OVER_OLIGON ->
                text.append(concatContexts(qChar1 = plus1, fthora1 = fthora, qChar2 = plus1, tChar2 = tChar, syllable1 = syllable))
            CONTINUOUS_ELAFRON_AND_KENTIMATA_OVER_OLIGON -> {
                text.append(concatContexts(qChar1 = minus1, tChar1 = gorgon, qChar2 = minus1, fthora2 = fthora, syllable2 = syllable))
                text.append(concatContexts(qChar1 = plus1, tChar1 = tChar))
            }
            ELAPHRON_OVER_APOSTROPHOS_AND_KENTIMATA_OVER_OLIGON ->
                text.append(concatContexts(qChar1 = minus3, fthora1 = fthora, qChar2 = plus1, tChar2 = tChar, syllable1 = syllable))
            HAMILI_AND_KENTIMATA_OVER_OLIGON ->
                text.append(concatContexts(qChar1 = minus4, fthora1 = fthora, qChar2 = plus1, tChar2 = tChar, syllable1 = syllable))
            YPORROI_AND_KENTIMATA_OVER_OLIGON -> {
                text.append(concatContexts(qChar1 = minus1, tChar1 = tChar, fthora1 = fthora, qChar2 = minus1, syllable1 = syllable))
                text.append(plus1)
            }
            else -> text.append(concatContexts(qChar1 = ctx.qChar().text, tChar1 = tChar, fthora1 = fthora, syllable1 = syllable))
        }

        /*println(text.toString())
        text.clear()*/
    }

    private fun concatContexts(
            qChar1: String,
            tChar1: String = "",
            fthora1: String = "",
            syllable1: String = "",
            qChar2: String = "",
            tChar2: String = "",
            fthora2: String = "",
            syllable2: String = ""
    ): String = qChar1 + tChar1 + fthora1 + qChar2 + tChar2 + fthora2 + syllable1

    private fun ParseTree.asGorgotitaContext(): ByzParser.GorgotitaContext? =
            if (this is ByzParser.GorgotitaContext) this else null

    private fun ByzParser.TCharContext.translate() {
        TODO("translate tChars")
    }

    private companion object {
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
        const val gorgon = "\uD834\uDC8F"
        const val minus2 = "\uD834\uDC55" // elafron -2
        const val minus3 = "B108" // ELAPHRON_OVER_APOSTROPHOos -3
        const val minus4 = "\uD834\uDC56" // hamili -4
        const val minus7 = "L118" // HAMILI_OVER_ELAPHRON_OVER_APOSTROPHos -7
        const val minus8 = "L098" // HAMILI_OVER_HAMIli -8
        const val minus9 = "L110" // HAMILI_OVER_HAMILI_OVER_APOSTROPHos -9
        const val ison = "𝁆" // 0
    }
}