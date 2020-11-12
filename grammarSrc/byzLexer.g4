lexer grammar byzLexer;

// Afona or Ypostaseis (Mutes or Hypostases)
MIKRON_ISON : '𝁗' -> skip ;
VAREIA_NEO : ('𝁘' | 'B092') -> skip ;
PIASMA_NEO : '𝁙' -> skip ;
PSIFISTON_NEO : ('𝁚' | 'B047') -> skip ;
OMALON : ('𝁛' | 'L093') -> skip ;
ANTIKENOMA : ('𝁜' | 'B109') -> skip ;
LYGISMA : '𝁝' -> skip ;
PARAKLITIKI_NEO : '𝁞' -> skip ;
PARAKALESMA_NEO : '𝁟' -> skip ;
ETERON_PARAKALESMA : ('𝁠' | 'B063') -> skip ;
KYLISMA : '𝁡' -> skip ;
ANTIKENOKYLISMA : '𝁢' -> skip ;
TROMIKON_NEO : '𝁣' -> skip ;
EKSTREPTON : '𝁤' -> skip ;
SYNAGMA_NEO : '𝁥' -> skip ;
SYRMA : '𝁦' -> skip ;
CHOREVMA_NEO : '𝁧' -> skip ;
EPEGERMA : '𝁨' -> skip ;
SEISMA_NEO : '𝁩' -> skip ;
XIRON_KLASMA : '𝁪' -> skip ;
TROMIKOPSIFISTON : '𝁫' -> skip ;
PSIFISTOLYGISMA : '𝁬' -> skip ;
TROMIKOLYGISMA : '𝁭' -> skip ;
TROMIKOPARAKALESMA : '𝁮' -> skip ;
PSIFISTOPARAKALESMA : '𝁯' -> skip ;
TROMIKOSYNAGMA : '𝁰' -> skip ;
PSIFISTOSYNAGMA : '𝁱' -> skip ;
GORGOSYNTHETON : '𝁲' -> skip ;
ARGOSYNTHETON : '𝁳' -> skip ;
ETERON_ARGOSYNTHETON : '𝁴' -> skip ;
OYRANISMA_NEO : '𝁵' -> skip ;
THEMATISMOS_ESO : '𝁶' -> skip ;
THEMATISMOS_EXO : '𝁷' -> skip ;
THEMA_APLOUN : '𝁸' -> skip ;
THES_KAI_APOTHES : '𝁹' -> skip ;
KATAVASMA : '𝁺' -> skip ;
ENDOFONON : '𝁻' -> skip ;
YFEN_KATO : ('𝁼' | 'I122') -> skip ;
YFEN_ANO : ('𝁽' | 'I090') -> skip ;

// Leimmata or Siopes (Leimmas or Silencers)
LEIMMA_ENOS_CHRONOU : '𝂊' | 'L092' ;
LEIMMA_DYO_CHRONON : '𝂋' -> skip ;
LEIMMA_TRION_CHRONON : '𝂌' -> skip ;
LEIMMA_TESSARON_CHRONON : '𝂍' -> skip ;
LEIMMA_IMISEOS_CHRONOU : '𝂎' -> skip ;

// Synagmata or Gorgotites (Synagmas or Quickeners), are found in gorgotites.g4

// Agogika (Conduits)
AGOGI_POLI_ARGI : ('𝂚' | 'X115') -> skip ;
AGOGI_ARGOTERI : ('𝂛' | 'X100') -> skip ;
AGOGI_ARGI : ('𝂜' | 'X102') -> skip ;
AGOGI_METRIA : ('𝂝' | 'X106') -> skip ;
AGOGI_MESI : '𝂞' -> skip ;
AGOGI_GORGI : ('𝂟' | 'X107') -> skip ;
AGOGI_GORGOTERI : ('𝂠' | 'X108') -> skip ;
AGOGI_POLI_GORGI : ('𝂡' | 'X059') -> skip ;

// Ichimata and Martyrika (Ichimas and Evidentials)
MARTYRIA_PROTOS_ICHOS : '𝂢' -> skip ;
MARTYRIA_ALLI_PROTOS_ICHOS : '𝂣' | 'B033' ;
MARTYRIA_DEYTEROS_ICHOS : '𝂤' -> skip ;
MARTYRIA_ALLI_DEYTEROS_ICHOS : '𝂥' | 'B037' ;
MARTYRIA_TRITOS_ICHOS : '𝂦' | 'I041' ;
MARTYRIA_TRIFONIAS : '𝂧' | 'B035' ;
MARTYRIA_TETARTOS_ICHOS : '𝂨' | 'B038' ;
MARTYRIA_TETARTOS_LEGETOS_ICHOS : '𝂩' -> skip ;
MARTYRIA_LEGETOS_ICHOS : '𝂪' | 'B064' ;
MARTYRIA_PLAGIOS_ICHOS : '𝂫' | 'I126' ;
ISAKIA_TELOUS_ICHIMATOS : '𝂬' -> skip ;
APOSTROFOI_TELOUS_ICHIMATOS : '𝂭' | 'B096' ;
FANEROSIS_TETRAFONIAS : '𝂮' | 'I045' ;
FANEROSIS_MONOFONIAS : '𝂯' -> skip ;
FANEROSIS_DIFONIAS : '𝂰' | 'I048' ;
MARTYRIA_VARYS_ICHOS : '𝂱' | 'B094' ;
MARTYRIA_PROTOVARYS_ICHOS : '𝂲' -> skip ;
MARTYRIA_PLAGIOS_TETARTOS_ICHOS : '𝂳' -> skip ;
GORTHMIKON_N_APLOUN : '𝂴' -> skip ;
GORTHMIKON_N_DIPLOUN : '𝂵' -> skip ;

// Rythmika (Rhythmics)
DIASTOLI_APLI_MIKRI : ('𝃚' | 'B111' | 'B079') -> skip ;
DIASTOLI_APLI_MEGALI : '𝃛' -> skip ;
DIASTOLI_DIPLI : ('𝃜' | 'B112') -> skip ;
DIASTOLI_THESEOS : ('𝃝' | 'B091') -> skip ;
SIMANSIS_THESEOS : '𝃞' -> skip ;
SIMANSIS_THESEOS_DISIMOU : '𝃟' -> skip ;
SIMANSIS_THESEOS_TRISIMOU : '𝃠' -> skip ;
SIMANSIS_THESEOS_TETRASIMOU : '𝃡' -> skip ;
SIMANSIS_ARSEOS : '𝃢' -> skip ;
SIMANSIS_ARSEOS_DISIMOU : '𝃣' -> skip ;
SIMANSIS_ARSEOS_TRISIMOU : '𝃤' -> skip ;
SIMANSIS_ARSEOS_TETRASIMOU : '𝃥' -> skip ;

// MK rythmics
DIASTOLI_APLI_MIKRI_ON_QCHAR : 'B080' -> skip ;
DIASTOLI_DIPLI_ON_QCHAR : 'B123' -> skip ;
METER_2_LEFT_ON_QCHAR : 'F037' -> skip ;
METER_2_MIDDLE_ON_QCHAR : 'F053' -> skip ;
METER_3_LEFT_ON_QCHAR : 'F054' -> skip ;
METER_3_MIDDLE_ON_QCHAR : 'F094' -> skip ;
METER_4_LEFT_ON_QCHAR : 'F055' -> skip ;
METER_4_MIDDLE_ON_QCHAR : 'F038' -> skip ;
METER_5_ON_DIASTOLI_DIPLI : 'B093' -> skip ;
METER_6_ON_DIASTOLI_DIPLI : 'B125' -> skip ;
METER_7_ON_DIASTOLI_DIPLI : 'B124' -> skip ;
METER_8_ON_DIASTOLI_DIPLI : 'F089' -> skip ;

// Grammata (Letters)
DIGRAMMA_GG : '𝃦' ;
DIFTOGGOS_OU : '𝃧' ;
STIGMA : '𝃨' ;
ARKTIKO_PA : '𝃩' | 'B049' ;
ARKTIKO_VOU : '𝃪' | 'B050' ;
ARKTIKO_GA : '𝃫' | 'B051' ;
ARKTIKO_DI : '𝃬' | 'B052' ;
ARKTIKO_KE : '𝃭' | 'B053' ;
ARKTIKO_ZO : '𝃮' | 'B054' ;
ARKTIKO_NI : '𝃯' | 'B055' ;
/*NH : 'B055' ;
PA : 'B049' ;
BOY : 'B050' ;
GA : 'B051' ;
DI : 'B052' ;
KE : 'B053' ;
ZW : 'B054' ;*/

PSIFISTON_PALAIO : 'P045' -> skip ;
ANTIKENOMA_DEXIA : 'B077' -> skip ;
OMALON_ARISTERA : 'B044' -> skip ;
OMALON_DEXIA : 'B060 ' -> skip ;
ETERON_PARAKALESMA_DEXIA : 'L125' -> skip ;
ETERON_PARAKALESMA_PALAIO : 'P095' -> skip ;
ETERON_PARAKALESMA_MEGALO : 'B250' -> skip ;
YFEN_KATO_MIKRO : 'I120' -> skip ;
YFEN_ANO_MIKRO : 'I088' -> skip ;

APLI_USED_NEXT_TO_LEIMMA : 'L124' ;

// MK ENDEIKSEIS FTHOGWN
ENDIXI_NH_ARISTERA : 'I099' -> skip ;
ENDIXI_NH_DEXIA : 'I067' -> skip ;
ENDIXI_PA_ARISTERA : 'I118' -> skip ;
ENDIXI_PA_DEXIA : 'I086' -> skip ;
ENDIXI_BOY_ARISTERA : 'I098' -> skip ;
ENDIXI_BOY_DEXIA : 'I066' -> skip ;
ENDIXI_GA_ARISTERA : 'I110' -> skip ;
ENDIXI_GA_DEXIA : 'I078' -> skip ;
ENDIXI_DI_ARISTERA : 'I109' -> skip ;
ENDIXI_DI_DEXIA : 'I077' -> skip ;
ENDIXI_KE_ARISTERA : 'I044' -> skip ;
ENDIXI_KE_DEXIA : 'I060' -> skip ;
ENDIXI_ZW_ARISTERA : 'I046' -> skip ;
ENDIXI_ZW_DEXIA : 'I062' -> skip ;
ENDIXI_DI_KATO_ARISTERA : 'I047' -> skip ;
ENDIXI_PA_KATO_ARISTERA : 'I063' -> skip ;

// MK ENDIXI ISOKRATIMATOS
ISOKRATIMA_NH : ('I100' | 'I068') -> skip ;
ISOKRATIMA_PA : ('I102' | 'I070') -> skip ;
ISOKRATIMA_BOY : ('I103' | 'I071') -> skip ;
ISOKRATIMA_GA : ('I104' | 'I072') -> skip ;
ISOKRATIMA_DI : ('I106' | 'I074') -> skip ;
ISOKRATIMA_KE : ('I107' | 'I075') -> skip ;
ISOKRATIMA_ZW : ('I115' | 'I083') -> skip ;
ISOKRATIMA_MAZI : ('I097' | 'I065') -> skip ;
ISOKRATIMA_ANW_ZW : ('I108' | 'I076') -> skip ;
ISOKRATIMA_KATW_DI : ('I059' | 'I058') -> skip ;
ISOKRATIMA_KATW_KE : ('I039' | 'I034') -> skip ;

// MK DYNAMICS
SIGA : 'I130' -> skip ;
KANONIKA : 'I131' -> skip ;
DINATA : 'I132' -> skip ;

// MK ISON DYNAMICS
ISON_SIGA : 'I133' -> skip ;
ISON_KANONIKA : 'I134' -> skip ;
ISON_DINATA : 'I135' -> skip ;

// MK GRAMMATA MARTYRIWN
GRAMMA_MARTYRIAS_TONOS : 'B126' ;
LOW_GA : 'F122' ;
LOW_DI : 'F097' ;
LOW_KE : 'F115' ;

// MK MARTYRIES
MARTYRIA_NENANO : 'B036' ;

MARTYRIA_ALLI_PROTOS_ICHOS_LOW : 'F083' ;
MARTYRIA_ALLI_DEYTEROS_ICHOS_LOW : 'F120' ;
MARTYRIA_TRIFONIAS_LOW : 'F090' ;
MARTYRIA_TETARTOS_ICHOS_LOW : 'F065' ;
MARTYRIA_NENANO_LOW : 'F088' ;

// STOIXEIA ARKTIKWN MARTYRIWN
HXOS_WORD : 'I096' | 'Ήχος' ;
HXOS_A_GRAMMA : 'I049' ;
HXOS_B_GRAMMA : 'I050' ;
HXOS_G_GRAMMA : 'I051' ;
HXOS_D_GRAMMA : 'I052' ;
HXOS_VARYS_WORD : 'I053' ;
FTHOGGOS_NH_WORD : 'I112' ;
FTHOGGOS_PA_WORD : 'I091' ;
FTHOGGOS_BOY_WORD : 'I093' ;
FTHOGGOS_GA_WORD : 'I092' ;
FTHOGGOS_DI_WORD : 'I080' ;
FTHOGGOS_KE_WORD : 'I123' ;
FTHOGGOS_ZW_WORD : 'I125' ;
WORD_TOS_FOR_LEGETOS : 'I037' ;
ARKTIKH_MARTYRIA_TETARTOS_ICHOS : 'I054' ;
ARKTIKH_MARTYRIA_PLAGIOS_A_ICHOS : 'I055' ;
ARKTIKH_MARTYRIA_DEYTEROS_ICHOS : 'I056' ;
ARKTIKH_MARTYRIA_TRITOS_ICHOS : 'I057' ;
ARKTIKH_MARTYRIA_MALAKO_XRWMA_BOY : 'I061' ;
ARKTIKH_MARTYRIA_AGIA : 'I094' ;
ARKTIKH_MARTYRIA_PRWTOS : 'I038' ;
ARKTIKH_MARTYRIA_DEYTEROS_ME_DIFWNH_ANAVASH : 'I042' ;
ARKTIKH_MARTYRIA_VARYS_DIATONIKOS : 'I040' ;
ARKTIKH_MARTYRIA_PA_SKLHRO_CHROMA : 'I095' ;
ARKTIKH_MARTYRIA_DI_MALAKO_CHROMA : 'I043' ;
ARKTIKH_MARTYRIA_SYNEXES_ELAPHRON_SE_FTHOGGO : 'I081' ;
ARKTIKH_MARTYRIA_TETRAFWNH_ANAVASI_SE_FTHOGGO : 'I087' ;
ARKTIKH_MARTYRIA_DIFWNI_KATAVASI_SE_FTHOGGO : 'I113' ;
ARKTIKH_MARTYRIA_TRIFWNH_ANAVASI_SE_FTHOGGO : 'I119' ;

// MK Agogika SE MARTYRIES
AGOGI_SE_MARTYRIA_POLI_ARGI : 'X083' -> skip ;
AGOGI_SE_MARTYRIA_ARGOTERI : 'X068' -> skip ;
AGOGI_SE_MARTYRIA_ARGI : 'X070' -> skip ;
AGOGI_SE_MARTYRIA_METRIA : 'X074' -> skip ;
AGOGI_SE_MARTYRIA_GORGI : 'X075' -> skip ;
AGOGI_SE_MARTYRIA_GORGOTERI : 'X076' -> skip ;
AGOGI_SE_MARTYRIA_POLI_GORGI : 'X058' -> skip ;

SPLITTER : '_' ;
ARXIGRAMMA : '@'. ;

GREEK_LETTER : [Α-Ωα-ω] ;

//LEFT_PARENTHESIS : '(' ;
//RIGHT_PARENTHESIS : ')' ;
//CAP_LETTER : [Α-Ω] ;
//SMALL_LETTER : [α-ω] ;
//SYLLABLE : CAP_LETTER? SMALL_LETTER+ ;
LATIN_WORD : LATIN_LETTER+ -> skip ;
GREEK_WORD : [ΆΈ-ΐΪ-ΰϊ-ώ]+ -> skip ;
//fragment
fragment LATIN_LETTER : [a-zA-Z] ;
SYMBOLS_NUMBERS : [0-9\-_] -> skip ;
WHITESPACE : [ \t\r\n]+ -> skip ;
ANYTHING : .+? -> skip ;