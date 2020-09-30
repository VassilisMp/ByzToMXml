grammar Byz;
import byzLexer, gorgotites, argies, fthores, fonitika;

//test : (capWord | syllable)+ ;
// level 2
newScore: newArktikiMartyria? (clusterType2|martyria)+ ;

score2 : arktikiMartyria? clusterType2+ ;

clusterType2 : ARXIGRAMMA? syllable? qChar
        (
            syllable? tChar* fthoraMeEndeixi?
            syllable? fthoraMeEndeixi? tChar*
            tChar* syllable? fthoraMeEndeixi?
            tChar* fthoraMeEndeixi? syllable?
//            fthoraMeEndeixi? syllable? tChar*
//            fthoraMeEndeixi? tChar* syllable?
        )
        pause?
        ;

// level 1
score : text* emptyCluster* arktikiMartyria? (text | cluster)+ ;

emptyCluster : LEFT_PARENTHESIS RIGHT_PARENTHESIS ;

//eteronAlone : (LEFT_PARENTHESIS ETERON_PARAKALESMA RIGHT_PARENTHESIS) ;

fthoraMeEndeixi : fthora endixiFthoggou? ;

//fthoraAlone : (LEFT_PARENTHESIS fthora RIGHT_PARENTHESIS) ;

pause : LEIMMA_ENOS_CHRONOU (APLI | DIPLI | TRIPLI)* gorgotita? ;

/*rythmika :
        DIASTOLI_APLI_MIKRI
        | DIASTOLI_APLI_MEGALI
        | DIASTOLI_DIPLI
        | DIASTOLI_THESEOS
        | SIMANSIS_THESEOS
        | SIMANSIS_THESEOS_DISIMOU
        | SIMANSIS_THESEOS_TRISIMOU
        | SIMANSIS_THESEOS_TETRASIMOU
        | SIMANSIS_ARSEOS
        | SIMANSIS_ARSEOS_DISIMOU
        | SIMANSIS_ARSEOS_TRISIMOU
        | SIMANSIS_ARSEOS_TETRASIMOU
        ;*/

cluster : /*fthoraAlone?*/ LEFT_PARENTHESIS /*text?*/
        ( capWord
            | syllable
            | tChar
            | qChar
            | fthoraMeEndeixi
//            | fthora
//            | qualChar
            | martyria
            | ARXIGRAMMA
//            | agogi
            | pause
//            | isokratima
//            | alla
//            | rythmika
//            | text
        )* RIGHT_PARENTHESIS
//        eteronAlone?
        ;
        /*|   qChar SYLLABLE? fChar? tChar?
        |   qChar tChar? SYLLABLE? fChar
        |   qChar tChar? fChar? SYLLABLE
        |   qChar fChar? SYLLABLE? tChar
        |   qChar fChar? tChar? SYLLABLE*/

tChar : gorgotita | argia ;

/*qualChar :
        PSIFISTON_NEO
        | PSIFISTON_PALAIO
        | ANTIKENOMA
        | ANTIKENOMA_DEXIA
        | OMALON_ARISTERA
        | OMALON
        | OMALON_DEXIA
        | ETERON_PARAKALESMA
        | ETERON_PARAKALESMA_DEXIA
        | ETERON_PARAKALESMA_PALAIO
        | ETERON_PARAKALESMA_MEGALO
        ;*/

grammaMartyrias :
        ARKTIKO_NI
        | ARKTIKO_PA
        | ARKTIKO_VOU
        | ARKTIKO_GA
        | ARKTIKO_DI
        | ARKTIKO_KE
        | ARKTIKO_ZO
        | LOW_GA
        | LOW_DI
        | LOW_KE
        ;

martyrikoSimio :
        APOSTROFOI_TELOUS_ICHIMATOS? (MARTYRIA_TETARTOS_ICHOS | MARTYRIA_ALLI_PROTOS_ICHOS | MARTYRIA_ALLI_DEYTEROS_ICHOS)
        | APOSTROFOI_TELOUS_ICHIMATOS? (MARTYRIA_ALLI_PROTOS_ICHOS_LOW | MARTYRIA_ALLI_DEYTEROS_ICHOS_LOW | MARTYRIA_TETARTOS_ICHOS_LOW)
        | MARTYRIA_LEGETOS_ICHOS
        | MARTYRIA_TRIFONIAS
        | MARTYRIA_VARYS_ICHOS
        | MARTYRIA_NENANO
        | MARTYRIA_TRIFONIAS_LOW
        | MARTYRIA_NENANO_LOW
        ;

martyria :
        grammaMartyrias martyrikoSimio GRAMMA_MARTYRIAS_TONOS?
        | grammaMartyrias GRAMMA_MARTYRIAS_TONOS? martyrikoSimio
        | martyrikoSimio grammaMartyrias GRAMMA_MARTYRIAS_TONOS?
        | martyrikoSimio GRAMMA_MARTYRIAS_TONOS? grammaMartyrias
        | GRAMMA_MARTYRIAS_TONOS? grammaMartyrias martyrikoSimio
        | GRAMMA_MARTYRIAS_TONOS? martyrikoSimio grammaMartyrias
//        | martyria agogiSeMartyria
        ;

/*agogiSeMartyria :
        AGOGI_SE_MARTYRIA_POLI_ARGI
        | AGOGI_SE_MARTYRIA_ARGOTERI
        | AGOGI_SE_MARTYRIA_ARGI
        | AGOGI_SE_MARTYRIA_METRIA
        | AGOGI_SE_MARTYRIA_GORGI
        | AGOGI_SE_MARTYRIA_GORGOTERI
        | AGOGI_SE_MARTYRIA_POLI_GORGI
        ;*/


arktikiMartyria :
        LEFT_PARENTHESIS? text?
        newArktikiMartyria
        martyria?
        syllable* ARXIGRAMMA* RIGHT_PARENTHESIS?
        ;

newArktikiMartyria: plagiosTetartoyArktikiMartyria | prwtosArktikiMartyria ;

plagiosTetartoyArktikiMartyria :
        HXOS_WORD MARTYRIA_PLAGIOS_ICHOS ARKTIKH_MARTYRIA_TETARTOS_ICHOS FTHOGGOS_NH_WORD
        ;

prwtosArktikiMartyria :
        HXOS_WORD ARKTIKH_MARTYRIA_PLAGIOS_A_ICHOS FANEROSIS_TETRAFONIAS FTHOGGOS_PA_WORD FTHORA_DIATONIKI_PA
        ;

endixiFthoggou :
        ENDIXI_NH_ARISTERA
        | ENDIXI_NH_DEXIA
        | ENDIXI_PA_ARISTERA
        | ENDIXI_PA_DEXIA
        | ENDIXI_BOY_ARISTERA
        | ENDIXI_BOY_DEXIA
        | ENDIXI_GA_ARISTERA
        | ENDIXI_GA_DEXIA
        | ENDIXI_DI_ARISTERA
        | ENDIXI_DI_DEXIA
        | ENDIXI_KE_ARISTERA
        | ENDIXI_KE_DEXIA
        | ENDIXI_ZW_ARISTERA
        | ENDIXI_ZW_DEXIA
        | ENDIXI_DI_KATO_ARISTERA
        | ENDIXI_PA_KATO_ARISTERA
        ;

/*
agogi :
        AGOGI_POLI_ARGI
        | AGOGI_ARGOTERI
        | AGOGI_ARGI
        | AGOGI_METRIA
        | AGOGI_MESI
        | AGOGI_GORGI
        | AGOGI_GORGOTERI
        | AGOGI_POLI_GORGI
        ;

isokratima :
        ISOKRATIMA_NH
        | ISOKRATIMA_PA
        | ISOKRATIMA_BOY
        | ISOKRATIMA_GA
        | ISOKRATIMA_DI
        | ISOKRATIMA_KE
        | ISOKRATIMA_ZW
        | ISOKRATIMA_MAZI
        | ISOKRATIMA_ANW_ZW
        | ISOKRATIMA_KATW_DI
        | ISOKRATIMA_KATW_KE
        ;

alla :
    KORONIS_HAMILA
    | KORONIS_HAMILA_STA_DEXIA
    | KORONIS_STA_DEXIA
    | STAVROS_PSILA
    | BREATH_MARK
    | KORONIS
    ;
*/

text : (syllable | LATIN_WORD | GREEK_WORD | SYMBOLS_NUMBERS | capWord )+
        | LEFT_PARENTHESIS (syllable | capWord | LATIN_WORD | GREEK_WORD | SYMBOLS_NUMBERS | text )+ RIGHT_PARENTHESIS;

capWord : CAP_LETTER CAP_LETTER+ ;
syllable :
        CAP_LETTER? SMALL_LETTER+?
        | CAP_LETTER
        ;