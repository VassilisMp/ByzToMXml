package grammar

import Byzantine.ByzStep
import Byzantine.MartirikoSimio
import Byzantine.Martyria
import grammar.ByzLexer.*
import org.antlr.v4.runtime.TokenStream
import org.antlr.v4.runtime.TokenStreamRewriter
import org.antlr.v4.runtime.tree.TerminalNode
import org.audiveris.proxymusic.Step

class RewriterListener(tokens: TokenStream) : ByzBaseListener() {
    internal val rewriter: TokenStreamRewriter = TokenStreamRewriter(tokens)
    private var arxigramma: TerminalNode? = null
    private var syllable: ByzParser.SyllableContext? = null
    private var inMusic: Boolean = false
    private var lastStep: Step? = null
    private var lastMartyria: Martyria? = null

    // put arxigramma and syllables in the correct places, meaning after quantity chars
    override fun visitTerminal(node: TerminalNode?) {
        if (node?.symbol?.type == ARXIGRAMMA &&
                rewriter.tokenStream[node.symbol.tokenIndex + 1]?.type == RIGHT_PARENTHESIS) {
            arxigramma = node
            /*for (i in (node.symbol.tokenIndex - 1 .. 0)) {
                    val token: Token = rewriter.tokenStream[node.symbol.tokenIndex - 1]
                    println(rewriter.tokenStream[node.symbol.tokenIndex-1])
                }*/
        }
    }

    override fun enterSyllable(ctx: ByzParser.SyllableContext) {
        return when {
            ctx.getParent()::class.java == ByzParser.TextContext::class.java -> return
            ctx.getParent()::class.java == ByzParser.ArktikiMartyriaContext::class.java -> {
                rewriter.delete(ctx.start, ctx.stop)
                return
            }
            ctx.getParent().children[1] == ctx || !inMusic -> {
                syllable = ctx
//                println(syllable?.text)
            }
            else -> ctx.getParent().children.subList(0, ctx.getParent().children.indexOf(ctx)).asReversed().forEach { pt ->
                if (pt::class.java == ByzParser.SyllableContext::class.java) syllable = ctx
                if (pt::class.java == ByzParser.QCharContext::class.java) return
            }
        }
    }


    override fun exitFthoraMeEndeixi(ctx: ByzParser.FthoraMeEndeixiContext?) {
//        println(ctx?.text)
//        rewriter.tokenStream.
//        print(ctx?.getStart())
    }

    override fun enterQChar(ctx: ByzParser.QCharContext) {
//        println(ctx?.text)
        inMusic = true
        var flag = false
        arxigramma?.let {
            rewriter.delete(it.symbol)
            rewriter.insertAfter(ctx.stop, it.text.drop(1))
            arxigramma = null
            flag = true
        }
        syllable?.let {
            var insertIndex = ctx.stop.tokenIndex
            if (flag) insertIndex = insertIndex.plus(1)
            rewriter.insertAfter(insertIndex, it.text)
            it.getTokens(CAP_LETTER).forEach { token -> rewriter.delete(token.symbol.tokenIndex) }
            it.getTokens(SMALL_LETTER).forEach { token -> rewriter.delete(token.symbol.tokenIndex) }
            syllable = null
        }
        // TODO run qchars
//        ctx?.start
//        super.enterQChar(ctx)
    }

    // removes useless text
    override fun enterText(ctx: ByzParser.TextContext) = rewriter.delete(ctx.start, ctx.stop)

    override fun enterCapWord(ctx: ByzParser.CapWordContext) = rewriter.delete(ctx.start, ctx.stop)

    // remove martyrias
    override fun enterMartyria(ctx: ByzParser.MartyriaContext) =
        if (rewriter.tokenStream[ctx.start.tokenIndex - 1].type == LEFT_PARENTHESIS
                && rewriter.tokenStream[ctx.stop.tokenIndex + 1].type == RIGHT_PARENTHESIS) {
            rewriter.delete(ctx.start.tokenIndex-1, ctx.stop.tokenIndex+1)
        } else rewriter.delete(ctx.start, ctx.stop)

    override fun enterPlagiosTetartoyArktikiMartyria(ctx: ByzParser.PlagiosTetartoyArktikiMartyriaContext?) {
        lastMartyria = Martyria(0, ByzStep.NH, MartirikoSimio.NEAGIE, 9)
        lastStep
//        super.enterPlagiosTetartoyArktikiMartyria(ctx)
    }
}