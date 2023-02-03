package com.mindhub.cerveceria;

import com.mindhub.cerveceria.entidades.*;
import com.mindhub.cerveceria.repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class CerveceriaApplication {

	@Autowired
	RepositorioCliente repositorioCliente;

	@Autowired
	RepositorioCerveza repositorioCerveza;

	@Autowired
	RepositorioCompra repositorioCompra;

	@Autowired
	RepositorioPedidoCerveza repositorioPedidoCerveza;

	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(CerveceriaApplication.class, args);
	}

	@Bean
	public CommandLineRunner iniciar(){
		return (args) -> {

			Cliente cliente1 = new Cliente("juan@mindhub.com", "Juan", "Sarmiento", passwordEncoder.encode("1234"), LocalDate.of(1996, 07, 02),
					"Tandil", "Argentina", 7000);
			cliente1.setAdmin(true);
			cliente1.setEstaActivado(true);
			repositorioCliente.save(cliente1);

			//QUIlMES
			String descripcionQuilmesRubia = "Cerveza lager argentina, elaborada con ingredientes nacionales. Con equilibrio entre el suave amargor del lúpulo y el sabor del cereal." +
					" Color amarillo dorado brillante. Es una cerveza equilibrada, de gran refrescancia y cuerpo balanceado, que marida con platos más bien grasosos o pesados." +
					" La gasificación de esta cerveza barre el paladar manteniendo las notas de sabores en la boca, y su gran refrescancia invita a comer acompañando siempre con la bebida.";

			Cerveza quilmesRubiaPorron = new Cerveza("Rubia Clásica",
					4.8D,
					15D,
					descripcionQuilmesRubia,
					300D,
					"Quilmes",
					TipoCerveza.LAGER,
					PresentacionCerveza.BOTELLA,
					"/web/assets/imagenes/catalogo/quilmes-clasica-porron.png");
			repositorioCerveza.save(quilmesRubiaPorron);

			Cerveza quilmesRubiaLata = new Cerveza("Rubia Clásica",
					4.8D,
					15D,
					descripcionQuilmesRubia,
					260D,
					"Quilmes",
					TipoCerveza.LAGER,
					PresentacionCerveza.LATA,
					"/web/assets/imagenes/catalogo/quilmes-clasica-lata.png");
			repositorioCerveza.save(quilmesRubiaLata);

			Cerveza quilmesRubiaBotella = new Cerveza("Rubia Clásica",
					4.8D,
					15D,
					descripcionQuilmesRubia,
					300D,
					"Quilmes",
					TipoCerveza.LAGER,
					PresentacionCerveza.BOTELLA,
					"/web/assets/imagenes/catalogo/quilmes-clasica-litro.png");
			repositorioCerveza.save(quilmesRubiaBotella);

			String descripcionQuilmesStout = "Cerveza negra, de cuerpo y espuma cremosa, su intenso amargor se compensa con notas de chocolate y café provenientes del golpe de fuego que" +
					" recibe la malta al momento de ser tostada. Es una opción ideal para maridar con postres dulces, platos fuertes, ahumados, tostados o picantes.Más información:" +
					" www.quilmes.com.ar";

			Cerveza quilmesStoutLata = new Cerveza("Stout",
					5D,
					15D,
					descripcionQuilmesStout,
					288D,
					"Quilmes",
					TipoCerveza.STOUT,
					PresentacionCerveza.LATA,
					"/web/assets/imagenes/catalogo/quilmes-stout-lata.png");
			repositorioCerveza.save(quilmesStoutLata);

			Cerveza quilmesStoutBotella = new Cerveza("Stout",
					5D,
					15D,
					descripcionQuilmesStout,
					288D,
					"Quilmes",
					TipoCerveza.STOUT,
					PresentacionCerveza.BOTELLA,
					"/web/assets/imagenes/catalogo/quilmes-stout-botella.png");
			repositorioCerveza.save(quilmesStoutBotella);

			String descripcionQuilmesBock = "Cerveza " +
					"negra-borravino, robusta y persistente. Sabor acaramelado y tostado amargo, debido a la selección de cebadas malteadas con las que se produce." +
					" Marida a la perfección con platos fuertes y reducciones oscuras, con sabores más bien intensos y de cierta complejidad.Más información: www.quilmes.com.ar";

			Cerveza quilmesBockLata = new Cerveza("Bock",
					6.3D,
					19D,
					descripcionQuilmesBock,
					288D,
					"Quilmes",
					TipoCerveza.STOUT,
					PresentacionCerveza.LATA,
					"/web/assets/imagenes/catalogo/quilmes-bock-lata.png");
			repositorioCerveza.save(quilmesBockLata);

			Cerveza quilmesBockLitro = new Cerveza("Bock",
					6.3D,
					19D,
					descripcionQuilmesBock,
					288D,
					"Quilmes",
					TipoCerveza.STOUT,
					PresentacionCerveza.BOTELLA,
					"/web/assets/imagenes/catalogo/quilmes-bock-botella.png");
			repositorioCerveza.save(quilmesBockLitro);


			//IMPERIAL*************************************************************************


			String descripcionImperialStout = "Imperial Cream Stout, una cerveza especial y distinguida." +
					" De un profundo color oscuro, con aromas a tostado y a café. Presenta un exquisito sabor intenso " +
					"y un cuerpo y espuma cremosa. Elaborada a partir de una cuidadosa selección ingredientes 100% naturales, " +
					"pasión y cuidado logrando una cerveza única de calidad premium.";

			Cerveza imperialStoutLata = new Cerveza("Cream Stout",
					6D,
					14D,
					descripcionImperialStout,
					200D,
					"Imperial",
					TipoCerveza.STOUT,
					PresentacionCerveza.LATA,
					"/web/assets/imagenes/catalogo/imperial-stout-lata.png");
			repositorioCerveza.save(imperialStoutLata);

			Cerveza imperialStoutBotella = new Cerveza("Cream Stout",
					6D,
					14D,
					descripcionImperialStout,
					483D,
					"Imperial",
					TipoCerveza.STOUT,
					PresentacionCerveza.BOTELLA,
					"/web/assets/imagenes/catalogo/imperial-stout-botella.png");
			repositorioCerveza.save(imperialStoutBotella);

			String descripcionImperialLager = "Imperial Lager, " +
					"cerveza extra con ingredientes 100% naturales. Cerveza dorada, con espuma blanca de buena retención. Con un perfecto equilibrio entre el lúpulo y la malta." +
					" Baja presencia de lúpulo y ligeramente maltosa. Sabor limpio y de intensidad ideal. No debe sentirse astringencia. Con un final seco, fresco y limpio.";

			Cerveza imperialLagerLata = new Cerveza("Lager",
					5.5D,
					15D,
					descripcionImperialLager,
					490D,
					"Imperial",
					TipoCerveza.LAGER,
					PresentacionCerveza.LATA,
					"/web/assets/imagenes/catalogo/imperial-lager-lata.png");
			repositorioCerveza.save(imperialLagerLata);

			Cerveza imperialLagerBotella = new Cerveza("Lager",
					5.5D,
					15D,
					descripcionImperialLager,
					490D,
					"Imperial",
					TipoCerveza.LAGER,
					PresentacionCerveza.BOTELLA,
					"/web/assets/imagenes/catalogo/imperial-lager-botella.png");
			repositorioCerveza.save(imperialLagerBotella);

			String descripcionImperialAmberLager = "Imperial Roja, cerveza de color cobrizo claro brillante, con " +
					"espuma clara y de buena persistencia. Posee aroma fresco, " +
					"que nos recuerda los lúpulos herbales, bien acompañado por las maltas caramelizadas. En boca se percibe un dulzor delicado, acompañado por un amargo suave. " +
					"Es una cerveza con sabores balanceados y cuerpo medio que tiene una buena tomabilidad.";

			Cerveza imperialAmberLagerLata = new Cerveza("Amber Lager",
					4.8D,
					15D,
					descripcionImperialAmberLager,
					299D,
					"Imperial",
					TipoCerveza.LAGER,
					PresentacionCerveza.LATA,
					"/web/assets/imagenes/catalogo/imperial-roja-lata.png");
			repositorioCerveza.save(imperialAmberLagerLata);

			Cerveza imperialAmberLagerBotella = new Cerveza("Amber Lager",
					4.8D,
					15D,
					descripcionImperialAmberLager,
					533D,
					"Imperial",
					TipoCerveza.LAGER,
					PresentacionCerveza.BOTELLA,
					"/web/assets/imagenes/catalogo/imperial-roja-botella.png");
			repositorioCerveza.save(imperialAmberLagerBotella);


			//LA PALOMA*************************************************************************


			String descripcionGoldenCoffe = "Cerveza rubia con un " +
					"carácter fresco de granos de café recién molidos que se perciben en aroma y sabor.";

			Cerveza laPalomaGoldenCoffe = new Cerveza("Golden Coffe",
					5D,
					18D,
					descripcionGoldenCoffe,
					385D,
					"La Paloma",
					TipoCerveza.LAGER,
					PresentacionCerveza.LATA,
					"/web/assets/imagenes/catalogo/lapaloma-golden-coffee.png");
			repositorioCerveza.save(laPalomaGoldenCoffe);

			String descripcionRuta11 = "Session Wheat IPA. El agregado de trigo y lúpulos cítricos y frutales otorgan una gran frescura, su cuerpo liviano, una gran tomabilidad.";

			Cerveza laPalomaRuta11 = new Cerveza("Ruta 11",
					4.3D,
					25D,
					descripcionRuta11,
					376D,
					"La Paloma",
					TipoCerveza.IPA,
					PresentacionCerveza.LATA,
					"/web/assets/imagenes/catalogo/lapaloma-ruta11.png");
			repositorioCerveza.save(laPalomaRuta11);

			String descripcionCreamAle = "Cerveza rubia refrescante de buen cuerpo y bajo amargor, con notas dulces de maltas y maíz.";

			Cerveza laPalomaCreamAle = new Cerveza("Cream Ale",
					5.2D,
					20D,
					descripcionCreamAle,
					337D,
					"La Paloma",
					TipoCerveza.ALE,
					PresentacionCerveza.LATA,
					"/web/assets/imagenes/catalogo/lapaloma-cream-ale.png");
			repositorioCerveza.save(laPalomaCreamAle);

			String descripcionAmberAle = "Color ámbar con notas dulces de malta caramelo que se combinan en balance con el aporte sutil de lúpulos americanos.";

			Cerveza laPalomaAmberAle = new Cerveza("Amber Ale",
					5.5D,
					18D,
					descripcionAmberAle,
					337D,
					"La Paloma",
					TipoCerveza.ALE,
					PresentacionCerveza.LATA,
					"/web/assets/imagenes/catalogo/lapaloma-amber-ale.png");
			repositorioCerveza.save(laPalomaAmberAle);

			String descripcionExportStout = "Negra intensa, seca y tostada con notas de café.";

			Cerveza laPalomaExportStout = new Cerveza("Export Stout",
					5.3D,
					35D,
					descripcionExportStout,
					350D,
					"La Paloma",
					TipoCerveza.STOUT,
					PresentacionCerveza.LATA,
					"/web/assets/imagenes/catalogo/lapaloma-export-stout.png");
			repositorioCerveza.save(laPalomaExportStout);

			String descripcionSummerIpa = "Es una cerveza de edición limitada, super refrescante. De bajo contenido alcohólico y muy aromática, ideal para tomar durante el verano," +
					" a cualquier hora del día.";

			Cerveza laPalomaSummerIpa = new Cerveza("Summer Ipa",
					3D,
					5D,
					descripcionSummerIpa,
					350D,
					"La Paloma",
					TipoCerveza.IPA,
					PresentacionCerveza.LATA,
					"/web/assets/imagenes/catalogo/lapaloma-summer-ipa.png");
			repositorioCerveza.save(laPalomaSummerIpa);


			//ANTARES*************************************************************************


			String descripcionKolsch = "Existen muchas cervezas doradas y refrescantes. Pero frutadas y con destellos finales de lúpulo, sólo hay un estilo: la Kölsch." +
					" En Antares rescatamos la antigua receta de la cerveza favorita de los bebedores en colonia, Alemania, y la honramos desde 1998. En nuestra cocina, su legado sigue intacto.";

			Cerveza antaresKolschPorron = new Cerveza("Kolsch",
					5D,
					22D,
					descripcionKolsch,
					420D,
					"Antares",
					TipoCerveza.LAGER,
					PresentacionCerveza.BOTELLA,
					"/web/assets/imagenes/catalogo/antares-kolsch-botella.png");
			repositorioCerveza.save(antaresKolschPorron);

			Cerveza antaresKolschLata = new Cerveza("Kolsch",
					5D,
					22D,
					descripcionKolsch,
					390D,
					"Antares",
					TipoCerveza.LAGER,
					PresentacionCerveza.LATA,
					"/web/assets/imagenes/catalogo/antares-kolsch-lata.png");
			repositorioCerveza.save(antaresKolschLata);

			String descripcionScotch = "Escocia es tierra de cebada y la Scotch Ale lleva ese paisaje impregnado en su código genético. Rubí intenso. Seis grados de alcohol." +
					" Dulce y maltosa. La Antares más servida en nuestro Brewpub. Una fórmula a prueba del paso del tiempo.";

			Cerveza antaresScotchPorron = new Cerveza("Scotch",
					6D,
					18D,
					descripcionScotch,
					420D,
					"Antares",
					TipoCerveza.ALE,
					PresentacionCerveza.BOTELLA,
					"/web/assets/imagenes/catalogo/antares-scotch-botella.png");
			repositorioCerveza.save(antaresScotchPorron);

			Cerveza antaresScotchLata = new Cerveza("Scotch",
					6D,
					18D,
					descripcionScotch,
					390D,
					"Antares",
					TipoCerveza.ALE,
					PresentacionCerveza.LATA,
					"/web/assets/imagenes/catalogo/antares-scotch-lata.png");
			repositorioCerveza.save(antaresScotchLata);

			String descripcionAntaresPorter = "Maltas oscuras. " +
					"Sabor y aroma penetrante y nocturno. Chocolate, azúcar negro y café. La Porter es la cerveza tributo de Antares a la cultura de los primeros pubs en el puerto de Londres." +
					" Cheers.";

			Cerveza antaresPorterPorron = new Cerveza("Porter",
					5.5D,
					27D,
					descripcionAntaresPorter,
					420D,
					"Antares",
					TipoCerveza.PORTER,
					PresentacionCerveza.BOTELLA,
					"/web/assets/imagenes/catalogo/antares-porter-botella.png");
			repositorioCerveza.save(antaresPorterPorron);

			String descripcionAntaresImperialStout = "Catalina la Grande amaba las emociones fuertes. Por eso, la Imperial Stout, negra y tostada, empapada de alcohol y pasas, amarga y ahumada," +
					" era su cerveza favorita. Esencia inglesa de exportación. Tímidos, abstenerse.";

			Cerveza antaresImperialStoutPorron = new Cerveza(
					"Imperial Stout",
					8.5D,
					49D,
					descripcionAntaresImperialStout,
					460D,
					"Antares",
					TipoCerveza.STOUT,
					PresentacionCerveza.BOTELLA,
					"/web/assets/imagenes/catalogo/antares-stout-botella.png");
			repositorioCerveza.save(antaresImperialStoutPorron);

			String descripcionHoneyBeer = "Babilonia antigua. Tras la boda, el padre de la novia convida al futuro yerno con cerveza de miel a lo largo de un mes. Así lo dicta la tradición." +
					" Nuestra Honey Beer recoge la historia que dio origen a “la luna de miel” y lo celebra con notas mento-ladas y frutales. Y, por supuesto, una inmersión de miel pura para" +
					" abrir los corazones.";

			Cerveza antaresImperialHoneyBeerPorron = new Cerveza(
					"Honey Beer",
					7.5D,
					22D,
					descripcionHoneyBeer,
					460D,
					"Antares",
					TipoCerveza.HONEY,
					PresentacionCerveza.BOTELLA,
					"/web/assets/imagenes/catalogo/antares-honey-botella.png");
			repositorioCerveza.save(antaresImperialHoneyBeerPorron);

			Cerveza antaresImperialHoneyBeerLata = new Cerveza(
					"Honey Beer",
					7.5D,
					22D,
					descripcionHoneyBeer,
					400D,
					"Antares",
					TipoCerveza.HONEY,
					PresentacionCerveza.LATA,
					"/web/assets/imagenes/catalogo/antares-honey-lata.png");
			repositorioCerveza.save(antaresImperialHoneyBeerLata);

			String descripcionAntaresIndiaPaleAle = "De Inglaterra a India hay un largo recorrido. En 1780, Mr. Hodgson descubrió que elevando el lúpulo y la graduación alcohólica," +
					" la cerveza llegaba a destino intacta. Bautizó a su fórmula India Pale Ale. En Antares, le sumamos lúpulos americanos con presencia de flores y cítricos. Nuestra cerveza viajera.";

			Cerveza antaresIndiaPaleAleLata = new Cerveza(
					"India Pale Ale",
					6.6D,
					58D,
					descripcionAntaresIndiaPaleAle,
					430D,
					"Antares",
					TipoCerveza.IPA,
					PresentacionCerveza.LATA,
					"/web/assets/imagenes/catalogo/antares-ipa-lata.png");
			repositorioCerveza.save(antaresIndiaPaleAleLata);

			String descripcionAntaresBarleyWine = "Nuestra cerveza " +
					"de mayor graduación alcohólica. Una hermandad de malta y licor, con rasgos de nuez, caramelo y dulce de leche. Barley Wine, cuando la vid se vuelve cebada.";

			Cerveza antaresBarleyWine = new Cerveza(
					"Barley Wine",
					10D,
					53D,
					descripcionAntaresBarleyWine,
					460D,
					"Antares",
					TipoCerveza.LAGER,
					PresentacionCerveza.BOTELLA,
					"/web/assets/imagenes/catalogo/antares-barleywine-botella.png");
			repositorioCerveza.save(antaresBarleyWine);

			String descripcionAntaresCaravana = "Llega la primavera. Cambiamos la ropa, hacemos más deporte. El lúpulo empieza a brotar apasionado." +
					" En Mar del Plata sacamos la bicicleta y salimos en Caravana. Una cerveza con alcohol bajo y muchísimo aroma y sabor a lúpulo, inspirada en las Session IPA americanas." +
					"Para refrescarnos y seguir pedaleando.";

			Cerveza antaresCaravana = new Cerveza(
					"Caravana",
					4.2D,
					30D,
					descripcionAntaresCaravana,
					430D,
					"Antares",
					TipoCerveza.IPA,
					PresentacionCerveza.LATA,
					"/web/assets/imagenes/catalogo/antares-caravana-ipa.png");
			repositorioCerveza.save(antaresCaravana);


			//GLUCK*************************************************************************


			String descripcionGluckFaro = "Cerveza extra. La roja clásica; el emblema de nuestras cervezas. de comienzo caramelizado y final tostado y suave.";

			Cerveza gluckFaro = new Cerveza(
					"Faro Irish Red",
					5.8D,
					21D,
					descripcionGluckFaro,
					410D,
					"Gluck",
					TipoCerveza.LAGER,
					PresentacionCerveza.LATA,
					"/web/assets/imagenes/catalogo/gluck-faro-irish.png");
			repositorioCerveza.save(gluckFaro);

			String descripcionGluckHoney = "Cerveza extra cobriza, con sabor y aroma a miel. De amargor bajo y cuerpo suave aportado por la combinación de maltas caramelo y miel artesanal.";

			Cerveza gluckHoney = new Cerveza(
					"Honey",
					6.3D,
					18D,
					descripcionGluckHoney,
					460D,
					"Gluck",
					TipoCerveza.HONEY,
					PresentacionCerveza.LATA,
					"/web/assets/imagenes/catalogo/gluck-honey.png");
			repositorioCerveza.save(gluckHoney);

			String descripcionGluckRobustPorter = "Cerveza extra negra, maltosa con un complejo carácter tostado. Destaca aroma a maltas tostadas con notas de café. cuerpo robusto y final seco. amargor medio.";

			Cerveza gluckRobustPorter = new Cerveza(
					"Robust Porter",
					5.7D,
					28D,
					descripcionGluckRobustPorter,
					410D,
					"Gluck",
					TipoCerveza.PORTER,
					PresentacionCerveza.LATA,
					"/web/assets/imagenes/catalogo/gluck-robust-porter.png");
			repositorioCerveza.save(gluckRobustPorter);

			String descripcionGluckIpaTemploDorado = "Cerveza extra " +
					"color oro, cítrica, muy refrescante. de aroma " +
					"penetrante e intenso a lúpulo y cierto frutal. " +
					"Es una cerveza para todo tipo de paladares.";

			Cerveza gluckIpaTemploDorado = new Cerveza(
					"Ipa Templo" +
							" " +
							"Dorado",
					5.7D,
					44D,
					descripcionGluckIpaTemploDorado,
					460D,
					"Gluck",
					TipoCerveza.IPA,
					PresentacionCerveza.LATA,
					"/web/assets/imagenes/catalogo/gluck-templodorado-ipa.png");
			repositorioCerveza.save(gluckIpaTemploDorado);

			String descripcionGluckMajestadSeissionIpa = "Cerveza liviana rubia y refrescante con aroma y sabor marcado a lúpulo. De amargor moderado, con notas cítricas y frutales con final suave.";

			Cerveza gluckMajestadSessionIpa = new Cerveza(
					"Majestad " +
							"Session Ipa",
					4D,
					15D,
					descripcionGluckMajestadSeissionIpa,
					450D,
					"Gluck",
					TipoCerveza.IPA,
					PresentacionCerveza.LATA,
					"/web/assets/imagenes/catalogo/gluck-majestad-ipa.png");
			repositorioCerveza.save(gluckMajestadSessionIpa);

			String descripcionGluckHomeroWitbier = "Cerveza liviana, rubia, algo turbia, pálida, cítrica y trigueña. Aroma y sabor a naranja y coriandro. Es un estilo suave y fácil de tomar.";

			Cerveza gluckHomeroWitbier = new Cerveza(
					"Homero Witbier",
					4.8D,
					12D,
					descripcionGluckHomeroWitbier,
					400D,
					"Gluck",
					TipoCerveza.LAGER,
					PresentacionCerveza.LATA,
					"/web/assets/imagenes/catalogo/gluck-homero-lager.png");
			repositorioCerveza.save(gluckHomeroWitbier);

			String descripcionGluckWeeHeavyKm213 = "Cerveza fuerte, roja, con maltas y caramelo que resaltan en su aroma y sabor. De buen cuerpo y alcohol moderado con un final de caramelo y frutas secas. Se sirve en copa.";

			Cerveza gluckWeeHeavyKm213 = new Cerveza(
					"Wee Heavy Km 213",
					9D,
					17D,
					descripcionGluckWeeHeavyKm213,
					410D,
					"Gluck",
					TipoCerveza.LAGER,
					PresentacionCerveza.LATA,
					"/web/assets/imagenes/catalogo/gluck-weeheavy-lager.png");
			repositorioCerveza.save(gluckWeeHeavyKm213);

			String descripcionWtfMonkeyMangoNeipa = "Jugo lupulado. Cerveza color amarillo pajizo, turbia y refrescante. Lupulada con aroma a mango. es una cerveza de muy bajo amargor y muy aromática.";

			Cerveza gluckWtfMonkeyMangoNeipa = new Cerveza(
					"Wtf Monkey Mango Neipa",
					4.9D,
					15D,
					descripcionWtfMonkeyMangoNeipa,
					410D,
					"Gluck",
					TipoCerveza.LAGER,
					PresentacionCerveza.LATA,
					"/web/assets/imagenes/catalogo/gluck-wtfmonkey-lager.png");
			repositorioCerveza.save(gluckWtfMonkeyMangoNeipa);

			String descripcionLucernaBarleyWine = "Cerveza FUERTE Maltosa, alcohólica, compleja y de sabor intenso. Madurada en barricas de roble. de aroma y sabor a caramelo y carácter a frutos secos. SE sirve en copa.";

			Cerveza gluckLucernaBarleyWine = new Cerveza(
					"Lucerna Barley Wine",
					9.3D,
					45D,
					descripcionLucernaBarleyWine,
					410D,
					"Gluck",
					TipoCerveza.LAGER,
					PresentacionCerveza.LATA,
					"/web/assets/imagenes/catalogo/gluck-lucerna-lager.png");
			repositorioCerveza.save(gluckLucernaBarleyWine);


			//TEMPLE*************************************************************************


			String descripcionWolfIpa = "Cuando un lobo aúlla, es porque está pidiendo una Wolf IPA. Con un perfil muy americano, es una IPA que no vas a poder parar de tomar." +
					" Cítrica y frutal consecuencia del dry hopping con lúpulo Mosaic, tiene una tremendo balance entre aroma, sabor, amargor y tomabilidad. ";

			Cerveza templeWolfIpa = new Cerveza(
					"Wolf Ipa",
					6D,
					50D,
					descripcionWolfIpa,
					468D,
					"Temple",
					TipoCerveza.IPA,
					PresentacionCerveza.LATA,
					"/web/assets/imagenes/catalogo/temple-wolf-ipa.png");
			repositorioCerveza.save(templeWolfIpa);

			String descripcionCosmica = "Un frutado cítrico, un ligero y especiado aroma a trigo. Sabor ácido al inicio," +
					" y un toque especiado a cilantro y naranja al final. Un birra de otro planeta.";

			Cerveza templeCosmica = new Cerveza(
					"Cosmica",
					4.5D,
					20D,
					descripcionCosmica,
					401D,
					"Temple",
					TipoCerveza.LAGER,
					PresentacionCerveza.LATA,
					"/web/assets/imagenes/catalogo/temple-cosmica-lager.png");
			repositorioCerveza.save(templeCosmica);

			String descripcionTempleHoney = "Probablemente el estilo de cerveza más tomado hoy en día, nuestra Honey " +
					"llegó para ganar corazones." +
					" Con nuestra (casi) obsesión por la sustentabilidad, usamos miel orgánica que viene directamente desde el Delta del Río Paraná," +
					" y la agregamos en la etapa de maduración, evitando que se transforme en alcohol, resalte el aroma y el sabor. ";

			Cerveza templeHoney = new Cerveza(
					"Honey",
					5.5D,
					19D,
					descripcionTempleHoney,
					580D,
					"Temple",
					TipoCerveza.HONEY,
					PresentacionCerveza.LATA,
					"/web/assets/imagenes/catalogo/temple-honey.png");
			repositorioCerveza.save(templeHoney);

			String descripcionTempleScottish = "Con un color cobrizo que refleja su elegancia y complejidad en sus " +
					"aromas," +
					" es una birra para frenarse a mirar, oler y después tomar. Combinación de maltas especiales que le dan un perfil a tostado y caramelo.";

			Cerveza templeScottish = new Cerveza(
					"Scottish",
					5.5D,
					24D,
					descripcionTempleScottish,
					468D,
					"Temple",
					TipoCerveza.ALE,
					PresentacionCerveza.LATA,
					"/web/assets/imagenes/catalogo/temple-scottish.png");
			repositorioCerveza.save(templeScottish);

			String descripcionTempleGolden = "De esas birras que le gustan a todo el mundo. Fresca, ligera, donde la " +
					"malta está presente en boca y en nariz de una manera única." +
					" La reina en esta birra es la malta Pilsen, que le aporta unas notas muy amigables y le dan ese color dorado claro que todos identificamos como BIRRA! De muuucha tomabilidad.";

			Cerveza templeGolden = new Cerveza(
					"Golden",
					5.5D,
					19D,
					descripcionTempleGolden,
					600D,
					"Temple",
					TipoCerveza.LAGER,
					PresentacionCerveza.LATA,
					"/web/assets/imagenes/catalogo/temple-golden.png");
			repositorioCerveza.save(templeGolden);

			String descripcionTempleStout = "Ella completa la familia, y es una birra para aquellos que buscan un amor para toda la vida." +
					" Con una danza perfecta entre amargor y sus tonos dulces, el aroma a café y la cremosidad de la espuma generan la profundidad de un alma negra.";

			Cerveza templeStout = new Cerveza(
					"Black Soul Stout",
					4.8D,
					17D,
					descripcionTempleStout,
					600D,
					"Temple",
					TipoCerveza.STOUT,
					PresentacionCerveza.LATA,
					"/web/assets/imagenes/catalogo/temple-blacksoul-stout.png");
			repositorioCerveza.save(templeStout);

			String descripcionTempleFlowApa = "Cerveza dorada, de bajo amargor y alta tomabilidad. Con agregado de lúpulos americanos en maduración (Mosaic, Citra, Amarillo y Sorachi)" +
					" que aportan notas cítricas a limón y pomelo.";

			Cerveza templeFlowApa = new Cerveza(
					"Flow Apa",
					5D,
					30D,
					descripcionTempleFlowApa,
					600D,
					"Temple",
					TipoCerveza.APA,
					PresentacionCerveza.LATA,
					"/web/assets/imagenes/catalogo/temple-flow-apa.png");
			repositorioCerveza.save(templeFlowApa);

			String descripcionTempleAlphaI = "Esta primera edición de Alpha, es resultado de una Wolf Ipa madurada " +
					"en" +
					" " +
					"frío y" +
					" añejada en barricas de roble durante un año. Dandole notas de madera, vainilla y un leve amargor" +
					". Lúpulos: Hérkules, Cascade y Mosaic. Maltas: Pilsen, Special W y Munich.\n" +
					"\n";

			Cerveza templeAlphaI = new Cerveza(
					"Alpha I",
					12.3D,
					50D,
					descripcionTempleAlphaI,
					550D,
					"Temple",
					TipoCerveza.APA,
					PresentacionCerveza.BOTELLA,
					"/web/assets/imagenes/catalogo/temple-alphaI-ipa.png");
			repositorioCerveza.save(templeAlphaI);

			String descripcionTempleAlphaII = "Proyecto Alpha, para quien sabe esperar. Esta segunda edición de " +
					"Alpha, " +
					"es resultado de una Wolf Ipa madurada en frío y añejada en barricas de roble durante 14 meses. Dandole notas de madera," +
					" vainilla y un leve amargor. Lúpulos: Hérkules, Cascade y Mosaic. Maltas: Pilsen, Special W y Munich.";

			Cerveza templeAlphaII = new Cerveza(
					"Alpha II",
					12.3D,
					50D,
					descripcionTempleAlphaII,
					550D,
					"Temple",
					TipoCerveza.APA,
					PresentacionCerveza.BOTELLA,
					"/web/assets/imagenes/catalogo/temple-alphaII-ipa.png");
			repositorioCerveza.save(templeAlphaII);

			String descripcionTempleAlphaIII = "El resultado de la Wolf Ipa madurada en barricas de roble frances por un año.";

			Cerveza templeAlphaIII = new Cerveza(
					"Alpha III",
					5.3D,
					50D,
					descripcionTempleAlphaIII,
					1750D,
					"Temple",
					TipoCerveza.APA,
					PresentacionCerveza.BOTELLA,
					"/web/assets/imagenes/catalogo/temple-alphaIII-ipa.png");
			repositorioCerveza.save(templeAlphaIII);


			//PENON DEL AGUILA*************************************************************************


			String descripcionSchwarzbier = "Cerveza legendaria de larga data en la cultura cerveza del norte aleman" +
					". Esta lager destaca por su elegancia y sutileza, por lo que se la conoce como la Pilsen negra. " +
					"Las maltas oscuras aportan su color sepia profundo y notas sutiles a pan tostado y chocolate con" +
					" un buen balance de lupulo pero de extrema delicadeza. De alcohol moderado, cuerpo medio bajo y " +
					"final seco, es una cerveza amable y de alta tomabilidad.";

			Cerveza penonDelAguilaSchwarzbier = new Cerveza(
					"Schwarzbier",
					5D,
					24D,
					descripcionSchwarzbier,
					379D,
					"Penon del Aguila",
					TipoCerveza.STOUT,
					PresentacionCerveza.LATA,
					"/web/assets/imagenes/catalogo/schwarzbier-penon-aguila.png");
			repositorioCerveza.save(penonDelAguilaSchwarzbier);

			String descripcionAmericanAmberLager = "Esta lager de inteso colo ambar, con destellos de rubi y una " +
					"espuma compacta y clara presenta un aroma complejo, donde la fruta tropical, durazno y citricos " +
					"de los lupulos americanos se combinan con notas a caramelo de las maltas especiales.";

			Cerveza penonDelAguilaAmericanAmberLager = new Cerveza(
					"American Amber Lager",
					5.5D,
					32D,
					descripcionAmericanAmberLager,
					440D,
					"Penon del Aguila",
					TipoCerveza.LAGER,
					PresentacionCerveza.LATA,
					"/web/assets/imagenes/catalogo/penondelaguila-amber-lager.png");
			repositorioCerveza.save(penonDelAguilaAmericanAmberLager);

			String descripcionMexicanLager = "Pensada para aliviar el efecto del inteso calor, es de cuerpo ligero " +
					"por el agregado de maiz como adjuinto, de conmtenido alcohgolico restringido y el amargor justo " +
					"para dar el balance final. Se caracteriza por su extrema tomabilidad, por su delicado balance y " +
					"por las notas ligeramente dulces de maiz.";

			Cerveza penonDelAguilaMexicanLager = new Cerveza(
					"Mexican Lager",
					4.6D,
					18D,
					descripcionMexicanLager,
					370D,
					"Penon del Aguila",
					TipoCerveza.LAGER,
					PresentacionCerveza.LATA,
					"/web/assets/imagenes/catalogo/penondelaguila-mexican-lager.png");
			repositorioCerveza.save(penonDelAguilaMexicanLager);

			String descripcionHonigbier = "Sobre la base de una lager tipo Helles, agregamos miel natural en la " +
					"fermentacion haciendo que esta cerveza de inteso color dorado presente el aroma y sabor " +
					"caracteristicos del dulce nectar con un contenido alcoholico algo mayuor y un cuerpo mas liviano" +
					" que la media. Con delicadas notas licorosas y el sutil dulzor de la miel es una cerveza " +
					"elegante, ligera en boca y de bajo amargor";

			Cerveza penonDelAguilaHonigbier = new Cerveza(
					"Honigbier",
					6D,
					12D,
					descripcionHonigbier,
					370D,
					"Penon del Aguila",
					TipoCerveza.HONEY,
					PresentacionCerveza.LATA,
					"/web/assets/imagenes/catalogo/penondelaguila-honigbier-honey.png");
			repositorioCerveza.save(penonDelAguilaHonigbier);

			String descripcionOktoberfest = "Esta cerveza de color cobre cristalino, destaca por su perfil orientado " +
					"a la maltas especiales que aportan delicadas notas a caramelo y un leve tostado. Los lupulos " +
					"nobles proporcionan el balance en esta cerveza limpia, de bajo amargor, alcohol restringido y un" +
					" final relativamente seco.";

			Cerveza penonDelAguilaOktoberfest = new Cerveza(
					"Oktoberfest",
					4.8D,
					18D,
					descripcionOktoberfest,
					370D,
					"Penon del Aguila",
					TipoCerveza.LAGER,
					PresentacionCerveza.LATA,
					"/web/assets/imagenes/catalogo/oktoberfest-penon-aguila.png");
			repositorioCerveza.save(penonDelAguilaOktoberfest);

			String descripcionPenonDelAguilaKolsch = "Fermentada con levadura del tipo Ale pero a baja temperatura, " +
					"lo que aporta un perfil caracteristico con notas a fruta y azufradas similares a las de un vino " +
					"blanco. De color dorado palido, su perfil de malta es extremadamente limpio y encuetra un " +
					"delicado equilibrio con el sutil amargor y sabor floral de los lupulos nobles alemanes.";

			Cerveza penonDelAguilaKolsch= new Cerveza(
					"Kolsch",
					4.8D,
					24D,
					descripcionPenonDelAguilaKolsch,
					379D,
					"Penon del Aguila",
					TipoCerveza.ALE,
					PresentacionCerveza.LATA,
					"/web/assets/imagenes/catalogo/kolsch-penon-aguila.png");
			repositorioCerveza.save(penonDelAguilaKolsch);


			//*************************************************************************


			Compra compra1 = new Compra(cliente1);
			compra1.setEstado(EstadoCompra.PROGRESO);
			repositorioCompra.save(compra1);

			PedidoCerveza pedidoCerveza1 = new PedidoCerveza(6, imperialStoutLata);
			pedidoCerveza1.setCompra(compra1);
			repositorioPedidoCerveza.save(pedidoCerveza1);

			PedidoCerveza pedidoCerveza2 = new PedidoCerveza(2, imperialStoutLata);
			pedidoCerveza2.setCompra(compra1);
			repositorioPedidoCerveza.save(pedidoCerveza2);


			Compra compra2 = new Compra(cliente1);
			compra2.setEstado(EstadoCompra.CONFIRMADA);
			repositorioCompra.save(compra2);

			PedidoCerveza pedidoCerveza3 = new PedidoCerveza(3, laPalomaGoldenCoffe);
			pedidoCerveza3.setCompra(compra2);
			repositorioPedidoCerveza.save(pedidoCerveza3);









		};
	}

}
