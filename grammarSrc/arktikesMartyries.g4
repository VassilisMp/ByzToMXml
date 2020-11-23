grammar arktikesMartyries;
import byzLexer, fthores, fonitika;


newArktikiMartyria :
    prwtosArktikiMartyria
    | prwtosTetrafwnos
    | prwtosXrwmatikosArktikiMartyria
    | prwtosDifwnosArktikiMartyria
    | deuterosArktikiMartyria
    | deuterosVouArktikiMartyria
    | deuterosVouSkliroXroma
    | deuterosPaSkliroXromaArktikiMartyria
    | tritosGa
    | tritosPaArktikiMartyria
    | tritosFthoraNhArktikiMartyria
    | tetartosDiArktikiMartyria
    | tetartosPaArktikiMartyria
    | legetos
    | legetosMalakoXrwmaArktikiMartyria
    | tetartosMalakoXrwmaArktikiMartyria
    | tetartosNenanwArktikiMartyria
    | tetartosKlitonArktikiMartyria
    | plagiosPrwtouArktikiMartyria
    | plagiosPrwtouKe
    | plagiosPrwtouPentafwnosArktikiMartyria
    | plagiosDeuteroyArktikiMartyria
    | plagiosDeuteroyNenanwArktikiMartyria
    | plagiosDeuteroyVouMalakoArktikiMartyria
    | plagiosDeuteroyDiMalakoArktikiMartyria
    | plagiosDeuteroyKeMalakoArktikiMartyria
    | plagiosDeuteroyKeMalakoDifwniaArktikiMartyria
    | plagiosDeuteroyNhArktikiMartyria
    | plagiosDeuteroyNhEptafwnosArktikiMartyria
    | varysGaArktikiMartyria
    | varysZwSklirosArktikiMArtyria
    | varysZwSklirosEptafwnosArktikiMArtyria
    | varysDiatonikosArktikiMartyria
    | varysDiatonikosEptafwnos
    | varysDiatonikosTetrafwnosArktikiMartyria
    | varysDiatonikosPentafwnosArktikiMartyria
    | plagiosTetartoy
    | plagiosTetartouTrifwnos
    | plagiosTetartouEptafwnosArktikiMartyria
    | plagiosTetartouEptafwnosXrwmatikosArktikiMartyria
    ;

prwtosTetrafwnos :
    prwtosKeArktikiMartyria
    | prwtosKePaArktikiMartyria
    | prwtosTetrafwnosArktikiMartyria
    ;

deuterosVouSkliroXroma :
    deuterosVouSkliroXromaArktikiMartyria
    | deuterosVouSkliroXromaAlliArktikiMartyria
    ;

tritosGa :
    tritosArktikiMartyria
    | tritosAlliArktikiMartyria
    ;

legetos :
    legetosArktikiMartyria
    | legetosAlliArktikiMartyria
    ;

plagiosPrwtouKe :
    plagiosPrwtouKeArktikiMartyria
    | plagiosPrwtouKeAlliArktikiMartyria
    ;

varysDiatonikosEptafwnos :
    varysDiatonikosEptafwnosArktikiMartyria
    | varysDiatonikosEptafwnosAlliArktikiMartyria
    ;

plagiosTetartoy :
    plagiosTetartoyArktikiMartyria
    | plagiosTetartouAlliArktikiMartyria
    ;

plagiosTetartouTrifwnos :
    plagiosTetartouTrifwnosArktikiMartyria
    | plagiosTetartouTrifwnosAlliArktikiMartyria
    ;

prwtosArktikiMartyria :
        HXOS_WORD ARKTIKH_MARTYRIA_PLAGIOS_A_ICHOS FANEROSIS_TETRAFONIAS FTHOGGOS_PA_WORD FTHORA_DIATONIKI_PA ;

prwtosKeArktikiMartyria :
        HXOS_WORD ARKTIKH_MARTYRIA_PLAGIOS_A_ICHOS FANEROSIS_TETRAFONIAS FTHOGGOS_KE_WORD FTHORA_DIATONIKI_KE ;

prwtosKePaArktikiMartyria :
        HXOS_WORD ARKTIKH_MARTYRIA_PLAGIOS_A_ICHOS FANEROSIS_TETRAFONIAS FTHOGGOS_KE_WORD FTHORA_DIATONIKI_PA ;

prwtosTetrafwnosArktikiMartyria :
        HXOS_WORD HXOS_A_GRAMMA YPSILI_AT_RIGHT_END_OF_OLIGON FTHORA_DIATONIKI_PA ;

prwtosXrwmatikosArktikiMartyria :
        HXOS_WORD ARKTIKH_MARTYRIA_PLAGIOS_A_ICHOS FANEROSIS_TETRAFONIAS FTHOGGOS_KE_WORD FTHORA_MALAKON_CHROMA_DIFONIAS ;

prwtosDifwnosArktikiMartyria :
        HXOS_WORD ARKTIKH_MARTYRIA_PLAGIOS_A_ICHOS FANEROSIS_TETRAFONIAS FTHOGGOS_PA_WORD FTHORA_DIATONIKI_PA OLIGON_NEO KENTIMA_NEO_MESO FTHORA_NAOS_ICHOS ;

deuterosArktikiMartyria :
        HXOS_WORD ARKTIKH_MARTYRIA_DEYTEROS_ME_DIFWNH_ANAVASH ARKTIKH_MARTYRIA_DI_MALAKO_CHROMA ;

deuterosVouArktikiMartyria :
        HXOS_WORD ARKTIKH_MARTYRIA_DEYTEROS_ME_DIFWNH_ANAVASH ARKTIKH_MARTYRIA_MALAKO_XRWMA_BOY ;

deuterosVouSkliroXromaArktikiMartyria :
        HXOS_WORD ARKTIKO_VOU APOSTROFOI_TELOUS_ICHIMATOS MARTYRIA_ALLI_DEYTEROS_ICHOS FHTORA_SKLIRON_CHROMA_VASIS ;

deuterosVouSkliroXromaAlliArktikiMartyria :
		HXOS_WORD ARKTIKH_MARTYRIA_DEYTEROS_ME_DIFWNH_ANAVASH FTHOGGOS_BOY_WORD FHTORA_SKLIRON_CHROMA_VASIS ;

deuterosPaSkliroXromaArktikiMartyria :
		HXOS_WORD ARKTIKH_MARTYRIA_DEYTEROS_ME_DIFWNH_ANAVASH ARKTIKH_MARTYRIA_PA_SKLHRO_CHROMA ;

tritosArktikiMartyria :
		HXOS_WORD MARTYRIA_TRITOS_ICHOS FTHOGGOS_GA_WORD FTHORA_DIATONIKI_NANA ;

tritosAlliArktikiMartyria :
		HXOS_WORD HXOS_G_GRAMMA FTHOGGOS_GA_WORD ;

tritosPaArktikiMartyria :
		HXOS_WORD ARKTIKH_MARTYRIA_TRITOS_ICHOS FTHOGGOS_GA_WORD FTHORA_DIATONIKI_NANA CONTINUOUS_ELAFRON ;

tritosFthoraNhArktikiMartyria :
		HXOS_WORD ARKTIKH_MARTYRIA_TRITOS_ICHOS FTHOGGOS_GA_WORD FTHORA_DIATONIKI_NI_KATO ;

tetartosDiArktikiMartyria :
		HXOS_WORD ARKTIKH_MARTYRIA_TETARTOS_ICHOS FANEROSIS_TETRAFONIAS FTHOGGOS_DI_WORD FTHORA_DIATONIKI_DI ;

tetartosPaArktikiMartyria :
		HXOS_WORD ARKTIKH_MARTYRIA_TETARTOS_ICHOS FANEROSIS_TETRAFONIAS FTHOGGOS_PA_WORD FTHORA_DIATONIKI_PA ;

legetosArktikiMartyria :
		HXOS_WORD ARKTIKH_MARTYRIA_TETARTOS_ICHOS FANEROSIS_TETRAFONIAS CONTINUOUS_ELAFRON ;

legetosAlliArktikiMartyria :
		HXOS_WORD ARKTIKO_GA MARTYRIA_LEGETOS_ICHOS WORD_TOS_FOR_LEGETOS FTHOGGOS_BOY_WORD ;

legetosMalakoXrwmaArktikiMartyria :
		HXOS_WORD ARKTIKH_MARTYRIA_TETARTOS_ICHOS FANEROSIS_TETRAFONIAS CONTINUOUS_ELAFRON ARKTIKH_MARTYRIA_MALAKO_XRWMA_BOY ;

tetartosMalakoXrwmaArktikiMartyria :
		HXOS_WORD ARKTIKH_MARTYRIA_TETARTOS_ICHOS FANEROSIS_TETRAFONIAS ARKTIKH_MARTYRIA_DI_MALAKO_CHROMA ;

tetartosNenanwArktikiMartyria :
		HXOS_WORD ARKTIKH_MARTYRIA_TETARTOS_ICHOS FANEROSIS_TETRAFONIAS FTHOGGOS_DI_WORD FTHORA_NENANO ;

tetartosKlitonArktikiMartyria :
		HXOS_WORD FTHOGGOS_DI_WORD CHROA_KLITON ;

plagiosPrwtouArktikiMartyria :
		HXOS_WORD MARTYRIA_PLAGIOS_ICHOS ARKTIKH_MARTYRIA_PLAGIOS_A_ICHOS FTHOGGOS_PA_WORD ;

plagiosPrwtouKeArktikiMartyria :
		HXOS_WORD MARTYRIA_PLAGIOS_ICHOS ARKTIKH_MARTYRIA_PLAGIOS_A_ICHOS FTHOGGOS_KE_WORD ARKTIKH_MARTYRIA_TETRAFWNH_ANAVASI_SE_FTHOGGO ;

plagiosPrwtouKeAlliArktikiMartyria :
		HXOS_WORD MARTYRIA_PLAGIOS_ICHOS ARKTIKH_MARTYRIA_PLAGIOS_A_ICHOS FTHOGGOS_PA_WORD FTHORA_DIATONIKI_PA FTHOGGOS_KE_WORD ARKTIKH_MARTYRIA_TETRAFWNH_ANAVASI_SE_FTHOGGO ;

plagiosPrwtouPentafwnosArktikiMartyria :
		HXOS_WORD MARTYRIA_PLAGIOS_ICHOS ARKTIKH_MARTYRIA_PLAGIOS_A_ICHOS FTHOGGOS_PA_WORD FTHORA_DIATONIKI_PA YPSILI_AT_LEFT_END_OF_OLIGON FTHORA_I_YFESIS_TETARTIMORION ;

plagiosDeuteroyArktikiMartyria :
		HXOS_WORD MARTYRIA_PLAGIOS_ICHOS ARKTIKH_MARTYRIA_DEYTEROS_ICHOS ARKTIKH_MARTYRIA_PA_SKLHRO_CHROMA ;

plagiosDeuteroyNenanwArktikiMartyria :
		HXOS_WORD MARTYRIA_PLAGIOS_ICHOS ARKTIKH_MARTYRIA_DEYTEROS_ICHOS KENTIMA_OVER_OLIGON FTHORA_NENANO ;

plagiosDeuteroyVouMalakoArktikiMartyria :
		HXOS_WORD MARTYRIA_PLAGIOS_ICHOS ARKTIKH_MARTYRIA_DEYTEROS_ICHOS ARKTIKH_MARTYRIA_MALAKO_XRWMA_BOY FANEROSIS_DIFONIAS ;

plagiosDeuteroyDiMalakoArktikiMartyria :
		HXOS_WORD MARTYRIA_PLAGIOS_ICHOS ARKTIKH_MARTYRIA_DEYTEROS_ICHOS KENTIMA_OVER_OLIGON ARKTIKH_MARTYRIA_DI_MALAKO_CHROMA ;

plagiosDeuteroyKeMalakoArktikiMartyria :
		HXOS_WORD MARTYRIA_PLAGIOS_ICHOS ARKTIKH_MARTYRIA_DEYTEROS_ICHOS YPSILI_AT_RIGHT_END_OF_OLIGON FTHOGGOS_KE_WORD FTHORA_MALAKON_CHROMA_DIFONIAS ;

plagiosDeuteroyKeMalakoDifwniaArktikiMartyria :
		HXOS_WORD MARTYRIA_PLAGIOS_ICHOS ARKTIKH_MARTYRIA_DEYTEROS_ICHOS FTHOGGOS_PA_WORD FTHORA_MALAKON_CHROMA_DIFONIAS FANEROSIS_DIFONIAS ;

plagiosDeuteroyNhArktikiMartyria :
		HXOS_WORD MARTYRIA_PLAGIOS_ICHOS ARKTIKH_MARTYRIA_DEYTEROS_ICHOS FTHOGGOS_NH_WORD FHTORA_SKLIRON_CHROMA_VASIS ;

plagiosDeuteroyNhEptafwnosArktikiMartyria :
		HXOS_WORD MARTYRIA_PLAGIOS_ICHOS ARKTIKH_MARTYRIA_DEYTEROS_ICHOS YPSILI_OVER_KENTIMA_OVER_OLIGON FTHOGGOS_NH_WORD FHTORA_SKLIRON_CHROMA_VASIS ;

varysGaArktikiMartyria :
		HXOS_WORD ARKTIKH_MARTYRIA_VARYS_DIATONIKOS FTHOGGOS_GA_WORD FTHORA_I_YFESIS_TETARTIMORION ;

varysZwSklirosArktikiMArtyria :
		HXOS_WORD ARKTIKH_MARTYRIA_VARYS_DIATONIKOS FTHOGGOS_ZW_WORD FTHORA_I_YFESIS_TETARTIMORION ;

varysZwSklirosEptafwnosArktikiMArtyria :
		HXOS_WORD ARKTIKH_MARTYRIA_VARYS_DIATONIKOS YPSILI_OVER_KENTIMA_OVER_OLIGON FTHORA_I_YFESIS_TETARTIMORION ;

varysDiatonikosArktikiMartyria :
		HXOS_WORD ARKTIKH_MARTYRIA_VARYS_DIATONIKOS FTHOGGOS_ZW_WORD ;

varysDiatonikosEptafwnosArktikiMartyria :
		HXOS_WORD ARKTIKH_MARTYRIA_VARYS_DIATONIKOS YPSILI_OVER_KENTIMA_OVER_OLIGON FTHORA_DIATONIKI_ZO ;

varysDiatonikosEptafwnosAlliArktikiMartyria :
		HXOS_WORD ARKTIKO_ZO MARTYRIA_VARYS_ICHOS YPSILI_OVER_KENTIMA_OVER_OLIGON ;

varysDiatonikosTetrafwnosArktikiMartyria :
		HXOS_WORD ARKTIKO_ZO MARTYRIA_VARYS_ICHOS YPSILI_AT_RIGHT_END_OF_OLIGON ;

varysDiatonikosPentafwnosArktikiMartyria :
		HXOS_WORD ARKTIKO_ZO MARTYRIA_VARYS_ICHOS YPSILI_AT_LEFT_END_OF_OLIGON ;

plagiosTetartoyArktikiMartyria :
		HXOS_WORD MARTYRIA_PLAGIOS_ICHOS ARKTIKH_MARTYRIA_TETARTOS_ICHOS FTHOGGOS_NH_WORD ;

plagiosTetartouAlliArktikiMartyria :
		HXOS_WORD MARTYRIA_PLAGIOS_ICHOS ARKTIKH_MARTYRIA_TETARTOS_ICHOS FTHOGGOS_NH_WORD FTHORA_DIATONIKI_NI_KATO ;

plagiosTetartouTrifwnosArktikiMartyria :
		HXOS_WORD MARTYRIA_PLAGIOS_ICHOS ARKTIKH_MARTYRIA_TETARTOS_ICHOS KENTIMA_OVER_OLIGON FTHORA_DIATONIKI_NI_KATO ;

plagiosTetartouTrifwnosAlliArktikiMartyria :
		HXOS_WORD MARTYRIA_PLAGIOS_ICHOS ARKTIKH_MARTYRIA_TETARTOS_ICHOS FTHOGGOS_GA_WORD ;

plagiosTetartouEptafwnosArktikiMartyria :
		HXOS_WORD MARTYRIA_PLAGIOS_ICHOS ARKTIKH_MARTYRIA_TETARTOS_ICHOS FTHOGGOS_NH_WORD YPSILI_OVER_KENTIMA_OVER_OLIGON FTHORA_DIATONIKI_NI_ANO ;

plagiosTetartouEptafwnosXrwmatikosArktikiMartyria :
		HXOS_WORD MARTYRIA_PLAGIOS_ICHOS ARKTIKH_MARTYRIA_TETARTOS_ICHOS FTHOGGOS_NH_WORD YPSILI_OVER_KENTIMA_OVER_OLIGON FTHORA_NENANO ;
