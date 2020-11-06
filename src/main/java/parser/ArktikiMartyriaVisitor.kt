package parser

import grammar.ByzBaseVisitor
import grammar.ByzParser
import grammar.ByzParser.*
import org.audiveris.proxymusic.AccidentalValue
import org.audiveris.proxymusic.AccidentalValue.QUARTER_FLAT
import org.audiveris.proxymusic.AccidentalValue.SHARP
import org.audiveris.proxymusic.Key
import org.audiveris.proxymusic.Pitch
import org.audiveris.proxymusic.Step
import org.audiveris.proxymusic.Step.*
import parser.fthores.Martyria.Companion.ACCIDENTALS_MAP
import java.math.BigDecimal
import java.math.RoundingMode

class ArktikiMartyriaVisitor: ByzBaseVisitor<ArktikiMartyriaVisitor.PitchnKey>() {
    override fun visitPrwtosArktikiMartyria(ctx: PrwtosArktikiMartyriaContext?) = PitchnKey(PitchOf(Step.A, 4), ussakKey())

    override fun visitPrwtosTetrafwnos(ctx: PrwtosTetrafwnosContext?) = PitchnKey(PitchOf(Step.A, 4), huseyniKey())

    override fun visitPrwtosXrwmatikosArktikiMartyria(ctx: ByzParser.PrwtosXrwmatikosArktikiMartyriaContext?) = PitchnKey(PitchOf(B, 5), prwtosXrwmatikosKey())

    override fun visitPrwtosDifwnosArktikiMartyria(ctx: ByzParser.PrwtosDifwnosArktikiMartyriaContext?) =
            PitchnKey(PitchOf(Step.A, 4), createKey(keyAccidental(B, -1), keyAccidental(D, -3)))

    override fun visitDeuterosArktikiMartyria(ctx: ByzParser.DeuterosArktikiMartyriaContext?) = PitchnKey(PitchOf(D, 5), deuterosKey())

    override fun visitDeuterosVouArktikiMartyria(ctx: ByzParser.DeuterosVouArktikiMartyriaContext?) = PitchnKey(PitchOf(B, 4), deuterosKey())

    override fun visitDeuterosVouSkliroXroma(ctx: DeuterosVouSkliroXromaContext?): PitchnKey =
            super.visitDeuterosVouSkliroXroma(ctx)

    override fun visitLegetos(ctx: LegetosContext?): PitchnKey {
        return super.visitLegetos(ctx)
    }

    override fun visitPlagiosPrwtouKe(ctx: ByzParser.PlagiosPrwtouKeContext?): PitchnKey {
        return super.visitPlagiosPrwtouKe(ctx)
    }

    override fun visitVarysDiatonikosEptafwnos(ctx: ByzParser.VarysDiatonikosEptafwnosContext?): PitchnKey {
        return super.visitVarysDiatonikosEptafwnos(ctx)
    }

    override fun visitPlagiosTetartoy(ctx: ByzParser.PlagiosTetartoyContext?): PitchnKey {
        return super.visitPlagiosTetartoy(ctx)
    }

    override fun visitPlagiosTetartouTrifwnos(ctx: ByzParser.PlagiosTetartouTrifwnosContext?): PitchnKey {
        return super.visitPlagiosTetartouTrifwnos(ctx)
    }

    override fun visitDeuterosPaSkliroXromaArktikiMartyria(ctx: ByzParser.DeuterosPaSkliroXromaArktikiMartyriaContext?): PitchnKey {
        return super.visitDeuterosPaSkliroXromaArktikiMartyria(ctx)
    }

    override fun visitTritosPaArktikiMartyria(ctx: ByzParser.TritosPaArktikiMartyriaContext?): PitchnKey {
        return super.visitTritosPaArktikiMartyria(ctx)
    }

    override fun visitTritosFthoraNhArktikiMartyria(ctx: ByzParser.TritosFthoraNhArktikiMartyriaContext?): PitchnKey {
        return super.visitTritosFthoraNhArktikiMartyria(ctx)
    }

    override fun visitTetartosDiArktikiMartyria(ctx: ByzParser.TetartosDiArktikiMartyriaContext?): PitchnKey {
        return super.visitTetartosDiArktikiMartyria(ctx)
    }

    override fun visitTetartosPaArktikiMartyria(ctx: ByzParser.TetartosPaArktikiMartyriaContext?): PitchnKey {
        return super.visitTetartosPaArktikiMartyria(ctx)
    }

    override fun visitLegetosMalakoXrwmaArktikiMartyria(ctx: ByzParser.LegetosMalakoXrwmaArktikiMartyriaContext?): PitchnKey {
        return super.visitLegetosMalakoXrwmaArktikiMartyria(ctx)
    }

    override fun visitTetartosMalakoXrwmaArktikiMartyria(ctx: ByzParser.TetartosMalakoXrwmaArktikiMartyriaContext?): PitchnKey {
        return super.visitTetartosMalakoXrwmaArktikiMartyria(ctx)
    }

    override fun visitTetartosNenanwArktikiMartyria(ctx: ByzParser.TetartosNenanwArktikiMartyriaContext?): PitchnKey {
        return super.visitTetartosNenanwArktikiMartyria(ctx)
    }

    override fun visitTetartosKlitonArktikiMartyria(ctx: ByzParser.TetartosKlitonArktikiMartyriaContext?): PitchnKey {
        return super.visitTetartosKlitonArktikiMartyria(ctx)
    }

    override fun visitPlagiosPrwtouArktikiMartyria(ctx: ByzParser.PlagiosPrwtouArktikiMartyriaContext?): PitchnKey {
        return super.visitPlagiosPrwtouArktikiMartyria(ctx)
    }

    override fun visitPlagiosPrwtouPentafwnosArktikiMartyria(ctx: ByzParser.PlagiosPrwtouPentafwnosArktikiMartyriaContext?): PitchnKey {
        return super.visitPlagiosPrwtouPentafwnosArktikiMartyria(ctx)
    }

    override fun visitPlagiosDeuteroyArktikiMartyria(ctx: ByzParser.PlagiosDeuteroyArktikiMartyriaContext?): PitchnKey {
        return super.visitPlagiosDeuteroyArktikiMartyria(ctx)
    }

    override fun visitPlagiosDeuteroyNenanwArktikiMartyria(ctx: ByzParser.PlagiosDeuteroyNenanwArktikiMartyriaContext?): PitchnKey {
        return super.visitPlagiosDeuteroyNenanwArktikiMartyria(ctx)
    }

    override fun visitPlagiosDeuteroyVouMalakoArktikiMartyria(ctx: ByzParser.PlagiosDeuteroyVouMalakoArktikiMartyriaContext?): PitchnKey {
        return super.visitPlagiosDeuteroyVouMalakoArktikiMartyria(ctx)
    }

    override fun visitPlagiosDeuteroyDiMalakoArktikiMartyria(ctx: ByzParser.PlagiosDeuteroyDiMalakoArktikiMartyriaContext?): PitchnKey {
        return super.visitPlagiosDeuteroyDiMalakoArktikiMartyria(ctx)
    }

    override fun visitPlagiosDeuteroyKeMalakoArktikiMartyria(ctx: ByzParser.PlagiosDeuteroyKeMalakoArktikiMartyriaContext?): PitchnKey {
        return super.visitPlagiosDeuteroyKeMalakoArktikiMartyria(ctx)
    }

    override fun visitPlagiosDeuteroyKeMalakoDifwniaArktikiMartyria(ctx: ByzParser.PlagiosDeuteroyKeMalakoDifwniaArktikiMartyriaContext?): PitchnKey {
        return super.visitPlagiosDeuteroyKeMalakoDifwniaArktikiMartyria(ctx)
    }

    override fun visitPlagiosDeuteroyNhArktikiMartyria(ctx: ByzParser.PlagiosDeuteroyNhArktikiMartyriaContext?): PitchnKey {
        return super.visitPlagiosDeuteroyNhArktikiMartyria(ctx)
    }

    override fun visitPlagiosDeuteroyNhEptafwnosArktikiMartyria(ctx: ByzParser.PlagiosDeuteroyNhEptafwnosArktikiMartyriaContext?): PitchnKey {
        return super.visitPlagiosDeuteroyNhEptafwnosArktikiMartyria(ctx)
    }

    override fun visitVarysGaArktikiMartyria(ctx: ByzParser.VarysGaArktikiMartyriaContext?): PitchnKey {
        return super.visitVarysGaArktikiMartyria(ctx)
    }

    override fun visitVarysZwSklirosArktikiMArtyria(ctx: ByzParser.VarysZwSklirosArktikiMArtyriaContext?): PitchnKey {
        return super.visitVarysZwSklirosArktikiMArtyria(ctx)
    }

    override fun visitVarysZwSklirosEptafwnosArktikiMArtyria(ctx: ByzParser.VarysZwSklirosEptafwnosArktikiMArtyriaContext?): PitchnKey {
        return super.visitVarysZwSklirosEptafwnosArktikiMArtyria(ctx)
    }

    override fun visitVarysDiatonikosArktikiMartyria(ctx: ByzParser.VarysDiatonikosArktikiMartyriaContext?): PitchnKey {
        return super.visitVarysDiatonikosArktikiMartyria(ctx)
    }

    override fun visitVarysDiatonikosTetrafwnosArktikiMartyria(ctx: ByzParser.VarysDiatonikosTetrafwnosArktikiMartyriaContext?): PitchnKey {
        return super.visitVarysDiatonikosTetrafwnosArktikiMartyria(ctx)
    }

    override fun visitVarysDiatonikosPentafwnosArktikiMartyria(ctx: ByzParser.VarysDiatonikosPentafwnosArktikiMartyriaContext?): PitchnKey {
        return super.visitVarysDiatonikosPentafwnosArktikiMartyria(ctx)
    }

    override fun visitPlagiosTetartouEptafwnosArktikiMartyria(ctx: ByzParser.PlagiosTetartouEptafwnosArktikiMartyriaContext?): PitchnKey {
        return super.visitPlagiosTetartouEptafwnosArktikiMartyria(ctx)
    }

    override fun visitPlagiosTetartouEptafwnosXrwmatikosArktikiMartyria(ctx: ByzParser.PlagiosTetartouEptafwnosXrwmatikosArktikiMartyriaContext?): PitchnKey {
        return super.visitPlagiosTetartouEptafwnosXrwmatikosArktikiMartyria(ctx)
    }

    data class PitchnKey(val pitch: Pitch, val key: Key)

    companion object {
        private fun keyAccidental(step: Step, commas: Int): KeyAccidental =
                KeyAccidental(step, accidentalAlter(commas), accidental(commas))
        private data class KeyAccidental(val step: Step, val alter: Float, val accidental: AccidentalValue) {
            fun toArray(): Array<Any> = arrayOf(step, alter.toBigDecimal(), accidental)
        }
        // alter calc formula
//            (accidentalCommas * 2.0 / 9).toBigDecimal().setScale(2, RoundingMode.HALF_EVEN)
        private fun createKey(vararg elements: KeyAccidental): Key = proxyMusicFactory.createKey().apply {
            elements.forEach { nonTraditionalKey.addAll(it.toArray()) }
        }
        private fun accidental(commas: Int) = ACCIDENTALS_MAP[commas] ?: throw Error("wrong accidental commas")
        private fun accidentalAlter(commas: Int): Float {
            return (commas * 2.0 / 9).toBigDecimal().setScale(2, RoundingMode.HALF_EVEN).toFloat()
        }
        // TODO use static values instead of function calls
        private fun ussakKey() = createKey(keyAccidental(B, -1))
        private fun huseyniKey() = createKey(keyAccidental(F, 4))
        private fun prwtosXrwmatikosKey() = createKey(
                keyAccidental(F, 2),
                keyAccidental(Step.C, 4),
                keyAccidental(Step.G, 4)
        )
        private fun deuterosKey() = createKey(
                keyAccidental(B, -1),
                keyAccidental(E, -3),
                keyAccidental(F, 4)
        )
    }
}