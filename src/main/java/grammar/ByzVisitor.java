// Generated from /home/vassilis/IdeaProjects/ByzToMXml/grammarSrc/Byz.g4 by ANTLR 4.8
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
	 * Visit a parse tree produced by {@link ByzParser#newScore}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewScore(ByzParser.NewScoreContext ctx);
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
	 * Visit a parse tree produced by {@link ByzParser#newArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewArktikiMartyria(ByzParser.NewArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#plagiosTetartoyArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlagiosTetartoyArktikiMartyria(ByzParser.PlagiosTetartoyArktikiMartyriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ByzParser#prwtosArktikiMartyria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrwtosArktikiMartyria(ByzParser.PrwtosArktikiMartyriaContext ctx);
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
}