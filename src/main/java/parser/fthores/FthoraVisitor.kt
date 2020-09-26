package parser.fthores

import grammar.ByzBaseVisitor
import grammar.ByzParser

class FthoraVisitor: ByzBaseVisitor<FthoraChar>() {
    override fun visitYfesiApli(ctx: ByzParser.YfesiApliContext?) = FthoraChar(commas = -1)
    override fun visitYfesiMonogrammos(ctx: ByzParser.YfesiMonogrammosContext?) = FthoraChar(commas = -4)
    override fun visitYfesiDigrammos(ctx: ByzParser.YfesiDigrammosContext?) = FthoraChar(commas = -5)
    // TODO YFESIS_TRIGRAMMOS_OKTO_DODEKATA, have to change grammar file
    override fun visitDiesiApli(ctx: ByzParser.DiesiApliContext?) = FthoraChar(commas = 1)
    override fun visitDiesiMonogrammos(ctx: ByzParser.DiesiMonogrammosContext?) = FthoraChar(commas = 4)
    override fun visitDiesiDigrammos(ctx: ByzParser.DiesiDigrammosContext?) = FthoraChar(commas = 5)
    // TODO DIESIS_TRIGRAMMOS_OKTO_DODEKATA, have to change grammar file
}
