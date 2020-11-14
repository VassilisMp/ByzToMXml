package parser.visitors

import grammar.ByzBaseVisitor
import grammar.ByzParser.*
import org.antlr.v4.runtime.Parser
import org.antlr.v4.runtime.tree.ParseTree
import org.audiveris.proxymusic.Key
import org.audiveris.proxymusic.Step
import parser.Tchar
import west.Note.Companion.RestNote

// replace all characters with the simplest ones
class ScoreVisitor(val parser: Parser) : ByzBaseVisitor<Unit>() {

    val text: StringBuilder = StringBuilder()
    val elements: MutableList<Any> = mutableListOf()
    private val quantityCharVisitor = QuantityCharVisitor(PitchOf(Step.G, 4))
    var key: Key? = null
    var counter = 0
    var syllable: String = ""
    private var prevSyllable: String = ""

    private fun String.mapLetters() = replace("\uD834\uDCE7", "ου").replace("\uD834\uDCE8", "στ")

    override fun visitCluster(ctx: ClusterContext): Unit = with(ctx) {
        fun getArxigramma() = ARXIGRAMMA()?.text?.drop(1) ?: ""
        // concatenate arxigramma and syllables and possible missing letter that was on the previous cluster by mistake
        println(++counter)
        prevSyllable = syllable
        fun takeFirstLetter(): String {
            return text()?.text?.run {
                if (hasSurrogatePairAt(lastIndex-1)) takeLast(2)
                else takeLast(1)
            }?.mapLetters() ?: ""
        }
        syllable = getArxigramma() + takeFirstLetter() +
                letters().joinToString(separator = "", transform = { it.text }).mapLetters()
        val gorgotita = tChar().mapNotNull { visitGorgotita(it) }.firstOrNull()
        val argia = tChar().mapNotNull { visitArgia(it) }.firstOrNull()
        elements.addAll(quantityCharVisitor.visit(prevSyllable = prevSyllable, syllable = syllable, gorgotita = gorgotita, argia = argia, tree = qChar()))
        // visit pause or return if null
        if (pause() != null) elements.addAll(visitPause(ctx))
        println(ctx.toStringTree(parser))
    }

    override fun visitStrangeCluster(ctx: StrangeClusterContext) = with(ctx) {
        cluster().children.addAll(tChar())
        cluster().children.addAll(fthoraMeEndeixi())
        visitCluster(cluster())
    }

    override fun visitNewArktikiMartyria(ctx: NewArktikiMartyriaContext?) {
        val pitchnKey = visitArktikiMartyria(ctx)
        if (pitchnKey != null) {
            quantityCharVisitor.lastPitch = pitchnKey.pitch
            key = pitchnKey.key
        }
    }

    companion object {
        private val argiesVisitor = ArgiesVisitor()
        private fun visitArgia(ctx: TCharContext): Tchar? = argiesVisitor.visit(ctx)
        private val gorgotitesVisitor = GorgotitesVisitor()
        private fun visitGorgotita(ctx: TCharContext) = gorgotitesVisitor.visit(ctx)
        private val pauseVisitor = object : ByzBaseVisitor<List<Any>>() {
            override fun visit(tree: ParseTree?): List<Any> = super.visit(tree) ?: emptyList()
            override fun visitPause(ctx: PauseContext): List<Any> {
                val times = when {
                    ctx.APLI().isNotEmpty() -> -2
                    ctx.DIPLI().isNotEmpty() -> -3
                    ctx.TRIPLI().isNotEmpty() -> -4
                    else -> -1
                }
                return listOfNotNull(RestNote(), Tchar(division = times), gorgotitesVisitor.visit(ctx.gorgotita()))
            }
        }
        private fun visitPause(ctx: ParseTree?) = pauseVisitor.visit(ctx)
        private val arktikiMartyriaVisitor = ArktikiMartyriaVisitor()
        private fun visitArktikiMartyria(ctx: NewArktikiMartyriaContext?) = arktikiMartyriaVisitor.visit(ctx)
    }
}