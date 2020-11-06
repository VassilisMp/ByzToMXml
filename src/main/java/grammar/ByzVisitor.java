// Generated from /home/pba/IdeaProjects/ByzToMXml/grammarSrc/Byz.g4 by ANTLR 4.8
package grammar;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ByzParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ByzVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ByzParser#score2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScore2(ByzParser.Score2Context ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#clusterType2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClusterType2(ByzParser.ClusterType2Context ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#score}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScore(ByzParser.ScoreContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#emptyCluster}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmptyCluster(ByzParser.EmptyClusterContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#fthoraMeEndeixi}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFthoraMeEndeixi(ByzParser.FthoraMeEndeixiContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#pause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPause(ByzParser.PauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#cluster}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCluster(ByzParser.ClusterContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#tChar}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTChar(ByzParser.TCharContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#grammaMartyrias}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGrammaMartyrias(ByzParser.GrammaMartyriasContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#martyrikoSimio}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMartyrikoSimio(ByzParser.MartyrikoSimioContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#martyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMartyria(ByzParser.MartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#arktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArktikiMartyria(ByzParser.ArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#endixiFthoggou}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEndixiFthoggou(ByzParser.EndixiFthoggouContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#text}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitText(ByzParser.TextContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#capWord}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCapWord(ByzParser.CapWordContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#syllable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSyllable(ByzParser.SyllableContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#gorgotita}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGorgotita(ByzParser.GorgotitaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#gorgon}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGorgon(ByzParser.GorgonContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#gorgonParestigmenoAristera}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGorgonParestigmenoAristera(ByzParser.GorgonParestigmenoAristeraContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#gorgonParestigmenoDexia}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGorgonParestigmenoDexia(ByzParser.GorgonParestigmenoDexiaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#digorgon}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDigorgon(ByzParser.DigorgonContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#digorgonParestigmenoAristeraKato}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDigorgonParestigmenoAristeraKato(ByzParser.DigorgonParestigmenoAristeraKatoContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#digorgonParestigmenoAristeraAno}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDigorgonParestigmenoAristeraAno(ByzParser.DigorgonParestigmenoAristeraAnoContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#digorgonParestigmenoDexia}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDigorgonParestigmenoDexia(ByzParser.DigorgonParestigmenoDexiaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#trigorgon}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrigorgon(ByzParser.TrigorgonContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#trigorgonParestigmenoAristeraKato}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrigorgonParestigmenoAristeraKato(ByzParser.TrigorgonParestigmenoAristeraKatoContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#trigorgonParestigmenoAristeraPano}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrigorgonParestigmenoAristeraPano(ByzParser.TrigorgonParestigmenoAristeraPanoContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#trigorgonParestigmenoDexia}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrigorgonParestigmenoDexia(ByzParser.TrigorgonParestigmenoDexiaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#argon}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgon(ByzParser.ArgonContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#imiDiargon}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImiDiargon(ByzParser.ImiDiargonContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#diargon}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDiargon(ByzParser.DiargonContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#argia}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgia(ByzParser.ArgiaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#klasma}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKlasma(ByzParser.KlasmaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#apli}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitApli(ByzParser.ApliContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#dipli}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDipli(ByzParser.DipliContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#tripli}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTripli(ByzParser.TripliContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#fthora}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFthora(ByzParser.FthoraContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#monimi}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMonimi(ByzParser.MonimiContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#yfesodiesi}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitYfesodiesi(ByzParser.YfesodiesiContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#yfesi}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitYfesi(ByzParser.YfesiContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#yfesiApli}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitYfesiApli(ByzParser.YfesiApliContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#yfesiMonogrammos}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitYfesiMonogrammos(ByzParser.YfesiMonogrammosContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#yfesiDigrammos}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitYfesiDigrammos(ByzParser.YfesiDigrammosContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#diesi}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDiesi(ByzParser.DiesiContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#diesiApli}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDiesiApli(ByzParser.DiesiApliContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#diesiMonogrammos}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDiesiMonogrammos(ByzParser.DiesiMonogrammosContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#diesiDigrammos}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDiesiDigrammos(ByzParser.DiesiDigrammosContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#fthoraDiatoniki}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFthoraDiatoniki(ByzParser.FthoraDiatonikiContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#fthoraChromatiki}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFthoraChromatiki(ByzParser.FthoraChromatikiContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#geniki}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGeniki(ByzParser.GenikiContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#chroa}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChroa(ByzParser.ChroaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#qChar}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQChar(ByzParser.QCharContext ctx);
	/**
	 * Visit a parse tree produced by the {@code zeroV}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitZeroV(ByzParser.ZeroVContext ctx);
	/**
	 * Visit a parse tree produced by the {@code alphaV}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlphaV(ByzParser.AlphaVContext ctx);
	/**
	 * Visit a parse tree produced by the {@code betaV}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBetaV(ByzParser.BetaVContext ctx);
	/**
	 * Visit a parse tree produced by the {@code gammaV}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGammaV(ByzParser.GammaVContext ctx);
	/**
	 * Visit a parse tree produced by the {@code deltaV}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeltaV(ByzParser.DeltaVContext ctx);
	/**
	 * Visit a parse tree produced by the {@code epsilonV}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEpsilonV(ByzParser.EpsilonVContext ctx);
	/**
	 * Visit a parse tree produced by the {@code sigmaTafV}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSigmaTafV(ByzParser.SigmaTafVContext ctx);
	/**
	 * Visit a parse tree produced by the {@code zetaV}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitZetaV(ByzParser.ZetaVContext ctx);
	/**
	 * Visit a parse tree produced by the {@code hetaV}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHetaV(ByzParser.HetaVContext ctx);
	/**
	 * Visit a parse tree produced by the {@code thetaV}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThetaV(ByzParser.ThetaVContext ctx);
	/**
	 * Visit a parse tree produced by the {@code iotaV}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIotaV(ByzParser.IotaVContext ctx);
	/**
	 * Visit a parse tree produced by the {@code iotaAlphaV}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIotaAlphaV(ByzParser.IotaAlphaVContext ctx);
	/**
	 * Visit a parse tree produced by the {@code iotaBetaV}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIotaBetaV(ByzParser.IotaBetaVContext ctx);
	/**
	 * Visit a parse tree produced by the {@code iotaGammaV}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIotaGammaV(ByzParser.IotaGammaVContext ctx);
	/**
	 * Visit a parse tree produced by the {@code iotaDeltaV}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIotaDeltaV(ByzParser.IotaDeltaVContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mAlphaV}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMAlphaV(ByzParser.MAlphaVContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mBetaV}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMBetaV(ByzParser.MBetaVContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mGammaV}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMGammaV(ByzParser.MGammaVContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mDeltaV}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMDeltaV(ByzParser.MDeltaVContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mEpsilonV}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMEpsilonV(ByzParser.MEpsilonVContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mSigmaTafV}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMSigmaTafV(ByzParser.MSigmaTafVContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mZetaV}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMZetaV(ByzParser.MZetaVContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mΗetaV}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMΗetaV(ByzParser.MΗetaVContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mThetaV}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMThetaV(ByzParser.MThetaVContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mIotaV}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMIotaV(ByzParser.MIotaVContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mIotaAlphaV}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMIotaAlphaV(ByzParser.MIotaAlphaVContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mIotaBetaV}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMIotaBetaV(ByzParser.MIotaBetaVContext ctx);
	/**
	 * Visit a parse tree produced by the {@code zeroAndAlphaV}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitZeroAndAlphaV(ByzParser.ZeroAndAlphaVContext ctx);
	/**
	 * Visit a parse tree produced by the {@code betaVAndApli}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBetaVAndApli(ByzParser.BetaVAndApliContext ctx);
	/**
	 * Visit a parse tree produced by the {@code deltaAndAlphaV}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeltaAndAlphaV(ByzParser.DeltaAndAlphaVContext ctx);
	/**
	 * Visit a parse tree produced by the {@code epsilonAndAlphaV}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEpsilonAndAlphaV(ByzParser.EpsilonAndAlphaVContext ctx);
	/**
	 * Visit a parse tree produced by the {@code zeroAndmAlphaV}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitZeroAndmAlphaV(ByzParser.ZeroAndmAlphaVContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mAlphaAndAlphaV}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMAlphaAndAlphaV(ByzParser.MAlphaAndAlphaVContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mBetaAndAlphaV}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMBetaAndAlphaV(ByzParser.MBetaAndAlphaVContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mGammaAndAlphaV}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMGammaAndAlphaV(ByzParser.MGammaAndAlphaVContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mDeltaAndAlphaV}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMDeltaAndAlphaV(ByzParser.MDeltaAndAlphaVContext ctx);
	/**
	 * Visit a parse tree produced by the {@code continuousElafron}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinuousElafron(ByzParser.ContinuousElafronContext ctx);
	/**
	 * Visit a parse tree produced by the {@code twoApostrofoi}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTwoApostrofoi(ByzParser.TwoApostrofoiContext ctx);
	/**
	 * Visit a parse tree produced by the {@code yporroi}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitYporroi(ByzParser.YporroiContext ctx);
	/**
	 * Visit a parse tree produced by the {@code continuousElafronAndKentimata}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinuousElafronAndKentimata(ByzParser.ContinuousElafronAndKentimataContext ctx);
	/**
	 * Visit a parse tree produced by the {@code yporroiAndKentimata}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitYporroiAndKentimata(ByzParser.YporroiAndKentimataContext ctx);
	/**
	 * Visit a parse tree produced by the {@code oligonOnKentimata}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOligonOnKentimata(ByzParser.OligonOnKentimataContext ctx);
	/**
	 * Visit a parse tree produced by the {@code kentimataOnOligon}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKentimataOnOligon(ByzParser.KentimataOnOligonContext ctx);
	/**
	 * Visit a parse tree produced by the {@code oligonOnKentimataAndApli}
	 * labeled alternative in {@link ByzParser#qChar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOligonOnKentimataAndApli(ByzParser.OligonOnKentimataAndApliContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#newArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewArktikiMartyria(ByzParser.NewArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#prwtosTetrafwnos}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrwtosTetrafwnos(ByzParser.PrwtosTetrafwnosContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#deuterosVouSkliroXroma}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeuterosVouSkliroXroma(ByzParser.DeuterosVouSkliroXromaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#tritosGa}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTritosGa(ByzParser.TritosGaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#legetos}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLegetos(ByzParser.LegetosContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#plagiosPrwtouKe}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlagiosPrwtouKe(ByzParser.PlagiosPrwtouKeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#varysDiatonikosEptafwnos}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarysDiatonikosEptafwnos(ByzParser.VarysDiatonikosEptafwnosContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#plagiosTetartoy}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlagiosTetartoy(ByzParser.PlagiosTetartoyContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#plagiosTetartouTrifwnos}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlagiosTetartouTrifwnos(ByzParser.PlagiosTetartouTrifwnosContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#prwtosArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrwtosArktikiMartyria(ByzParser.PrwtosArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#prwtosKeArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrwtosKeArktikiMartyria(ByzParser.PrwtosKeArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#prwtosKePaArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrwtosKePaArktikiMartyria(ByzParser.PrwtosKePaArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#prwtosTetrafwnosArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrwtosTetrafwnosArktikiMartyria(ByzParser.PrwtosTetrafwnosArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#prwtosXrwmatikosArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrwtosXrwmatikosArktikiMartyria(ByzParser.PrwtosXrwmatikosArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#prwtosDifwnosArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrwtosDifwnosArktikiMartyria(ByzParser.PrwtosDifwnosArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#deuterosArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeuterosArktikiMartyria(ByzParser.DeuterosArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#deuterosVouArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeuterosVouArktikiMartyria(ByzParser.DeuterosVouArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#deuterosVouSkliroXromaArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeuterosVouSkliroXromaArktikiMartyria(ByzParser.DeuterosVouSkliroXromaArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#deuterosVouSkliroXromaAlliArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeuterosVouSkliroXromaAlliArktikiMartyria(ByzParser.DeuterosVouSkliroXromaAlliArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#deuterosPaSkliroXromaArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeuterosPaSkliroXromaArktikiMartyria(ByzParser.DeuterosPaSkliroXromaArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#tritosArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTritosArktikiMartyria(ByzParser.TritosArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#tritosAlliArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTritosAlliArktikiMartyria(ByzParser.TritosAlliArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#tritosPaArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTritosPaArktikiMartyria(ByzParser.TritosPaArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#tritosFthoraNhArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTritosFthoraNhArktikiMartyria(ByzParser.TritosFthoraNhArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#tetartosDiArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTetartosDiArktikiMartyria(ByzParser.TetartosDiArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#tetartosPaArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTetartosPaArktikiMartyria(ByzParser.TetartosPaArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#legetosArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLegetosArktikiMartyria(ByzParser.LegetosArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#legetosAlliArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLegetosAlliArktikiMartyria(ByzParser.LegetosAlliArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#legetosMalakoXrwmaArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLegetosMalakoXrwmaArktikiMartyria(ByzParser.LegetosMalakoXrwmaArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#tetartosMalakoXrwmaArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTetartosMalakoXrwmaArktikiMartyria(ByzParser.TetartosMalakoXrwmaArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#tetartosNenanwArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTetartosNenanwArktikiMartyria(ByzParser.TetartosNenanwArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#tetartosKlitonArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTetartosKlitonArktikiMartyria(ByzParser.TetartosKlitonArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#plagiosPrwtouArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlagiosPrwtouArktikiMartyria(ByzParser.PlagiosPrwtouArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#plagiosPrwtouKeArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlagiosPrwtouKeArktikiMartyria(ByzParser.PlagiosPrwtouKeArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#plagiosPrwtouKeAlliArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlagiosPrwtouKeAlliArktikiMartyria(ByzParser.PlagiosPrwtouKeAlliArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#plagiosPrwtouPentafwnosArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlagiosPrwtouPentafwnosArktikiMartyria(ByzParser.PlagiosPrwtouPentafwnosArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#plagiosDeuteroyArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlagiosDeuteroyArktikiMartyria(ByzParser.PlagiosDeuteroyArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#plagiosDeuteroyNenanwArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlagiosDeuteroyNenanwArktikiMartyria(ByzParser.PlagiosDeuteroyNenanwArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#plagiosDeuteroyVouMalakoArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlagiosDeuteroyVouMalakoArktikiMartyria(ByzParser.PlagiosDeuteroyVouMalakoArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#plagiosDeuteroyDiMalakoArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlagiosDeuteroyDiMalakoArktikiMartyria(ByzParser.PlagiosDeuteroyDiMalakoArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#plagiosDeuteroyKeMalakoArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlagiosDeuteroyKeMalakoArktikiMartyria(ByzParser.PlagiosDeuteroyKeMalakoArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#plagiosDeuteroyKeMalakoDifwniaArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlagiosDeuteroyKeMalakoDifwniaArktikiMartyria(ByzParser.PlagiosDeuteroyKeMalakoDifwniaArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#plagiosDeuteroyNhArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlagiosDeuteroyNhArktikiMartyria(ByzParser.PlagiosDeuteroyNhArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#plagiosDeuteroyNhEptafwnosArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlagiosDeuteroyNhEptafwnosArktikiMartyria(ByzParser.PlagiosDeuteroyNhEptafwnosArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#varysGaArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarysGaArktikiMartyria(ByzParser.VarysGaArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#varysZwSklirosArktikiMArtyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarysZwSklirosArktikiMArtyria(ByzParser.VarysZwSklirosArktikiMArtyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#varysZwSklirosEptafwnosArktikiMArtyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarysZwSklirosEptafwnosArktikiMArtyria(ByzParser.VarysZwSklirosEptafwnosArktikiMArtyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#varysDiatonikosArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarysDiatonikosArktikiMartyria(ByzParser.VarysDiatonikosArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#varysDiatonikosEptafwnosArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarysDiatonikosEptafwnosArktikiMartyria(ByzParser.VarysDiatonikosEptafwnosArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#varysDiatonikosEptafwnosAlliArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarysDiatonikosEptafwnosAlliArktikiMartyria(ByzParser.VarysDiatonikosEptafwnosAlliArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#varysDiatonikosTetrafwnosArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarysDiatonikosTetrafwnosArktikiMartyria(ByzParser.VarysDiatonikosTetrafwnosArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#varysDiatonikosPentafwnosArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarysDiatonikosPentafwnosArktikiMartyria(ByzParser.VarysDiatonikosPentafwnosArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#plagiosTetartoyArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlagiosTetartoyArktikiMartyria(ByzParser.PlagiosTetartoyArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#plagiosTetartouAlliArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlagiosTetartouAlliArktikiMartyria(ByzParser.PlagiosTetartouAlliArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#plagiosTetartouTrifwnosArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlagiosTetartouTrifwnosArktikiMartyria(ByzParser.PlagiosTetartouTrifwnosArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#plagiosTetartouTrifwnosAlliArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlagiosTetartouTrifwnosAlliArktikiMartyria(ByzParser.PlagiosTetartouTrifwnosAlliArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#plagiosTetartouEptafwnosArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlagiosTetartouEptafwnosArktikiMartyria(ByzParser.PlagiosTetartouEptafwnosArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#plagiosTetartouEptafwnosXrwmatikosArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlagiosTetartouEptafwnosXrwmatikosArktikiMartyria(ByzParser.PlagiosTetartouEptafwnosXrwmatikosArktikiMartyriaContext ctx);
}