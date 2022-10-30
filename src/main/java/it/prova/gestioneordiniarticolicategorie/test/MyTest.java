package it.prova.gestioneordiniarticolicategorie.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.EntityManagerUtil;
import it.prova.gestioneordiniarticolicategorie.exception.OrdineConArticoliAssociatiException;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;
import it.prova.gestioneordiniarticolicategorie.service.MyServiceFactory;
import it.prova.gestioneordiniarticolicategorie.service.articolo.ArticoloService;
import it.prova.gestioneordiniarticolicategorie.service.categoria.CategoriaService;
import it.prova.gestioneordiniarticolicategorie.service.ordine.OrdineService;

public class MyTest {
	public static void main(String[] args) {
		OrdineService ordineService = MyServiceFactory.getOrdineServiceInstance();
		ArticoloService articoloService = MyServiceFactory.getArticoloServiceInstance();
		CategoriaService categoriaService = MyServiceFactory.getCategoriaService();

		try {
			System.out.println("*************************INIZIO BATTERIA TEST*****************************");
			System.out.println("elementi in ordine: " + ordineService.listAll().size());
			System.out.println("elementi in ordine: " + articoloService.listAll().size());
			System.out.println("elementi in ordine: " + categoriaService.listAll().size());

			testInserimentoOrdine(ordineService);

			testAggiornamentoOrdine(ordineService);

			testInserimentoArticolo(articoloService, ordineService);
			
			testAggiornamentoArticolo(articoloService);
			
			testCollegaArticoloAOrdineEsistene(ordineService, articoloService);
			
			testRimozioneArticoloLegatoAOrdineMaNonACategoria(ordineService, articoloService);
			
			testInserimentoArticolo(articoloService, ordineService);
			
			testInserimentoCategoria(categoriaService);
			
			testAggiornamentoCategoria(categoriaService);
			
			testAggiungiArticoloEsistenteACategoriaEsistente(articoloService, categoriaService);
			
			testAggiungiCategoriaEsistenteAdArticoloEsistente(articoloService, categoriaService);
			
			testRimozioneArticolo(articoloService);
			
			testRimozioneOrdine(ordineService);
			
			testRimozioneCategoria(categoriaService);
			
			testTrovaTuttiGliOrdiniAppartenentiAdUnaCaategoria(categoriaService, ordineService, articoloService);
			
			testTrovaTutteLeCategorieDistinteDaUnOrdine(categoriaService, ordineService, articoloService);
			
			testTrovaLaSommaDelPrezzoDiTuttiGliArticoliDiUnaCategoria(categoriaService, ordineService, articoloService);
			
			testTrovaLOrdinePiuRecenteInTerminiDiSpedizioneDataUnaCategoria(categoriaService, ordineService, articoloService);
			
			testTrovaTuttiIcodiciDiCategorieDiOrdiniEffettuatiInUnDatoMese(categoriaService, ordineService, articoloService);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			EntityManagerUtil.shutdown();
		}
	}

	private static void testInserimentoOrdine(OrdineService ordineServiceInstance) throws Exception {
		System.out.println("-------------testInserimentoOrdine INIZIO-----------");

		Ordine nuovoOrdine = new Ordine("Mario Rossi", "Via mosca 52",
				new SimpleDateFormat("yyyy/MM/dd").parse("2023/02/20"));
		ordineServiceInstance.inserisciNuovo(nuovoOrdine);

		if (nuovoOrdine.getId() == null)
			throw new RuntimeException("testInserimentoOrdine: FALLITO inserimento non avvenuto");

		System.out.println("-------------testInserimentoOrdine PASSED-----------");
	}

	private static void testAggiornamentoOrdine(OrdineService ordineServiceInstance) throws Exception {
		System.out.println("-------------testAggiornamentoOrdine INIZIO-----------");

		if(ordineServiceInstance.listAll().size() < 1)
			throw new RuntimeException("testRimozioneOrdine: FALLITO non ci sono ordini nel DB");

		Ordine ordineEsistente = ordineServiceInstance.listAll().get(0);
		Date dataSpedizione = new SimpleDateFormat("yyyy/MM/dd").parse("2023/01/01");
		if (dataSpedizione.after(ordineEsistente.getDataScadenza()))
			throw new RuntimeException("testAggiornamentoOrdine: FALLITO pacco spedito dopo la data di scadenza");
		ordineEsistente.setDataSpedizione(dataSpedizione);
		ordineServiceInstance.aggiorna(ordineEsistente);

		System.out.println("-------------testAggiornamentoOrdine PASSED-----------");
	}

	private static void testInserimentoArticolo(ArticoloService articoloServiceInstance,
			OrdineService ordineServiceInstService) throws Exception {
		System.out.println("-------------testInserimentoArticolo INIZIO-----------");

		Articolo nuovoArticolo = new Articolo("articolo1", "numeroSeriale1", 21,
				new SimpleDateFormat("yyyy/MM/dd").parse("2023/01/01"), ordineServiceInstService.listAll().get(0));
		articoloServiceInstance.inserisciNuovo(nuovoArticolo);
		
		if (nuovoArticolo.getId() == null)
			throw new RuntimeException("testInserimentoArticolo: FALLITO inserimento non avvenuto");

		System.out.println("-------------testInserimentoArticolo PASSED-----------");
	}

	private static void testAggiornamentoArticolo(ArticoloService articoloServiceInstance) throws Exception {
		System.out.println("-------------testAggiornamentoArticolo INIZIO-----------");

		if(articoloServiceInstance.listAll().size() < 1)
			throw new RuntimeException("testRimozioneOrdine: FALLITO non ci sono articoli nel DB");

		Articolo articoloEsistente = articoloServiceInstance.listAll().get(0);
		articoloEsistente.setDescrizione("articolo2");
		articoloServiceInstance.aggiorna(articoloEsistente);

		System.out.println("-------------testAggiornamentoArticolo PASSED-----------");
	}
	
	private static void testCollegaArticoloAOrdineEsistene(OrdineService ordineServiceInstance, ArticoloService articoloServiceInstance) throws Exception{
		System.out.println("-------------testCollegaArticoloAOrdineEsistene INIZIO-----------");
		
		Ordine nuovoOrdineTestCollegaArticoloAOrdineEsistente = new Ordine("Mario Rossi", "Via mosca 52",
				new SimpleDateFormat("yyyy/MM/dd").parse("2023/02/20"));
		ordineServiceInstance.inserisciNuovo(nuovoOrdineTestCollegaArticoloAOrdineEsistente);
		
		if(articoloServiceInstance.listAll().size() < 1)
			throw new RuntimeException("testRimozioneOrdine: FALLITO non ci sono articoli nel DB");
		
		Articolo articoloEsistenteTestCollegaArticoloAOrdineEsistente = articoloServiceInstance.listAll().get(0);
		
		articoloServiceInstance.collegaOrdine(nuovoOrdineTestCollegaArticoloAOrdineEsistente, articoloEsistenteTestCollegaArticoloAOrdineEsistente);
		
		System.out.println("-------------testCollegaArticoloAOrdineEsistene PASSED-----------");
	}
	
	private static void testRimozioneArticoloLegatoAOrdineMaNonACategoria(OrdineService ordineServiceInstance, ArticoloService articoloServiceInstance) throws Exception{
		System.out.println("-------------testScollegaArticoloDaOrdineEsistente INIZIO-----------");
		
		if(articoloServiceInstance.listAll().size() < 1)
			throw new RuntimeException("testRimozioneOrdine: FALLITO non ci sono articoli nel DB");
		
		Articolo nuovoArticolo = articoloServiceInstance.listAll().get(0);
		
		articoloServiceInstance.rimuovi(nuovoArticolo.getId());
		
		System.out.println("-------------testScollegaArticoloDaOrdineEsistente PASSED-----------");
	}
	
	private static void testInserimentoCategoria(CategoriaService categoriaService) throws Exception{
		System.out.println("-------------testInserisciCategoria INIZIO-----------");
		
		Categoria nuovaCategoria = new Categoria("descrizione1","codice1");
		categoriaService.inserisciNuovo(nuovaCategoria);
		if(nuovaCategoria.getId() == null)
			throw new RuntimeException("testInserisciCategoria: FALLITO inserimento categoria non riuscito");
		
		System.out.println("-------------testInserisciCategoria PASSED-----------");
	}
	
	private static void testAggiornamentoCategoria(CategoriaService categoriaService) throws Exception{
		System.out.println("-------------testAggiornamentoCategoria INIZIO-----------");
		
		if(categoriaService.listAll().size() < 1)
			throw new RuntimeException("testRimozioneOrdine: FALLITO non ci sono articoli nel DB");
		
		Categoria categoriaEsistente = categoriaService.listAll().get(0);
		categoriaEsistente.setDescrizione("nuovaDescrizione");
		categoriaService.aggiorna(categoriaEsistente);
		
		System.out.println("-------------testAggiornamentoCategoria PASSED-----------");
	}
	
	private static void testAggiungiArticoloEsistenteACategoriaEsistente(ArticoloService articoloService, CategoriaService categoriaService) throws Exception{
		System.out.println("-------------testAggiungiArticoloEsistenteACategoriaEsistente INIZIO-----------");
		
		if(categoriaService.listAll().size() < 1)
			throw new RuntimeException("testRimozioneOrdine: FALLITO non ci sono articoli nel DB");
		
		Categoria categoriaEsistente = categoriaService.listAll().get(0);
		
		if(articoloService.listAll().size() < 1)
			throw new RuntimeException("testRimozioneOrdine: FALLITO non ci sono articoli nel DB");
		
		Articolo articoloEsistente = articoloService.listAll().get(0);
		
		categoriaService.aggiungiArticolo(categoriaEsistente, articoloEsistente);
		
		Categoria categoriaReloaded = categoriaService.caricaSingoloElementoEager(categoriaEsistente.getId());
		if(categoriaReloaded.getArticoli().isEmpty())
			throw new RuntimeException("testAggiungiArticoloEsistenteACategoriaEsistente: FALLITO record non collegati");
		
		System.out.println("-------------testAggiungiArticoloEsistenteACategoriaEsistente PASSED-----------");
	}
	
	private static void testAggiungiCategoriaEsistenteAdArticoloEsistente(ArticoloService articoloService, CategoriaService categoriaService) throws Exception{
		System.out.println("-------------testAggiungiCategoriaEsistenteAdArticoloEsistente INIZIO-----------");
		
		Categoria categoriaTestAggiungiArticoloACategoria = new Categoria("descrizione1","codice1");
		categoriaService.inserisciNuovo(categoriaTestAggiungiArticoloACategoria);
		
		if(articoloService.listAll().size() < 1)
			throw new RuntimeException("testRimozioneOrdine: FALLITO non ci sono articoli nel DB");
		
		Articolo articoloEsistente = articoloService.listAll().get(0);
		articoloService.aggiungiCategoria(categoriaTestAggiungiArticoloACategoria, articoloEsistente);
		
		Articolo articoloReloaded = articoloService.caricaSingoloElementoEager(articoloEsistente.getId());
		if(articoloReloaded.getCategorie().isEmpty())
			throw new RuntimeException("testAggiungiCategoriaEsistenteAdArticoloEsistente: FALLITO collegamento non avvenuto");
		
		
		System.out.println("-------------testAggiungiCategoriaEsistenteAdArticoloEsistente PASSED-----------");
	}
	
	private static void testRimozioneArticolo(ArticoloService articoloService) throws Exception{
		System.out.println("-------------testRimozioneArticolo INIZIO-----------");
		
		if(articoloService.listAll().size() < 1)
			throw new RuntimeException("testRimozioneOrdine: FALLITO non ci sono articoli nel DB");
		
		Articolo articoloEsistente = articoloService.listAll().get(0);
		articoloService.disassocia(articoloEsistente.getId());
		articoloService.rimuovi(articoloEsistente.getId());
		
		System.out.println("-------------testRimozioneArticolo PASSED-----------");
	}
	
	private static void testRimozioneOrdine(OrdineService ordineService) throws Exception{
		System.out.println("-------------testRimozioneOrdine INIZIO-----------");
		
		if(ordineService.listAll().size() < 1)
			throw new RuntimeException("testRimozioneOrdine: FALLITO non ci sono ordini nel DB");
		
		Ordine ordineEsistente = ordineService.listAll().get(0);
		if(!(ordineEsistente.getArticoli().isEmpty()))
			throw new OrdineConArticoliAssociatiException("testRimozioneOrdine: FALLITO impossibile eliminare un ordine se ha articoli collegati");
		
		ordineService.rimuovi(ordineEsistente.getId());
		
		System.out.println("-------------testRimozioneOrdine PASSED-----------");
	}
	
	private static void testRimozioneCategoria(CategoriaService categoriaService) throws Exception{
		System.out.println("-------------testRimozioneCategoria INIZIO-----------");
		
		if(categoriaService.listAll().size() < 1)
			throw new RuntimeException("testRimozioneCategoria: FALLITO non ci sono categorie nel DB");
		
		Categoria categoriaEsistente = categoriaService.listAll().get(0);
		categoriaService.rimuovi(categoriaEsistente.getId());
		
		System.out.println("-------------testRimozioneCategoria PASSED-----------");
	}
	
	public static void testTrovaTuttiGliOrdiniAppartenentiAdUnaCaategoria(CategoriaService categoriaService, OrdineService ordineService, ArticoloService articoloService) throws Exception{
		System.out.println("-------------testTrovaTuttiGliOrdiniAppartenentiAdUnaCaategoria INIZIO-----------");
		
		/* creazione e inserimento ordine in DB */
		Ordine nuovoOrdine = new Ordine("Niko Pandetta", "Via mosca 52",
				new SimpleDateFormat("yyyy/MM/dd").parse("2023/02/20"));
		ordineService.inserisciNuovo(nuovoOrdine);
		/* controllo inserimento effettuato correttamente */
		if (nuovoOrdine.getId() == null)
			throw new RuntimeException("testTrovaTuttiGliOrdiniAppartenentiAdUnaCaategoria: FALLITO inserimento ordine non avvenuto");

		/* creazione e inserimento articolo in DB */
		Articolo nuovoArticolo = new Articolo("articolo1", "numeroSeriale1", 21,
				new SimpleDateFormat("yyyy/MM/dd").parse("2023/01/01"), ordineService.caricaSingoloElemento(nuovoOrdine.getId()));
		articoloService.inserisciNuovo(nuovoArticolo);
		/* controllo inserimento effettuato correttamente */
		if (nuovoArticolo.getId() == null)
			throw new RuntimeException("testTrovaTuttiGliOrdiniAppartenentiAdUnaCaategoria: FALLITO inserimento articolo non avvenuto");

		/* creazione e inserimento categoria */
		Categoria nuovaCategoria = new Categoria("descrizione1","codice1");
		categoriaService.inserisciNuovo(nuovaCategoria);
		/* controllo inserimento effettuato correttamente */
		if(nuovaCategoria.getId() == null)
			throw new RuntimeException("testTrovaTuttiGliOrdiniAppartenentiAdUnaCaategoria: FALLITO inserimento categoria non riuscito");
		
		/* collegamento articolo-categoria */
		categoriaService.aggiungiArticolo(nuovaCategoria, nuovoArticolo);
		/* controllo aggiunta effettuata correttamente */
		Categoria categoriaReloaded = categoriaService.caricaSingoloElementoEager(nuovaCategoria.getId());
		if(categoriaReloaded.getArticoli().isEmpty())
			throw new RuntimeException("testTrovaTuttiGliOrdiniAppartenentiAdUnaCaategoria: FALLITO record non collegati");

		/* chiamata metodo per ottenre tutti gli ordini di una categoria */
		List<Ordine> ordiniDiUnaCategoria = categoriaService.trovaTuttiGliOrdiniAppartenentiAdUnaCategoria(nuovaCategoria);
		/* controllo che il metodo funzioni correttamente */
		if(ordiniDiUnaCategoria.isEmpty())
			throw new RuntimeException("testTrovaTuttiGliOrdiniAppartenentiAdUnaCaategoria: FALLITO lista vuota");
	
		/* rimozione articolo */
		articoloService.disassocia(nuovoArticolo.getId());
		articoloService.rimuovi(nuovoArticolo.getId());
		
		/* rimozione ordine */
		ordineService.rimuovi(nuovoOrdine.getId());
		
		/* rimozione categoria */
		categoriaService.rimuovi(nuovaCategoria.getId());
		
		
		System.out.println("-------------testTrovaTuttiGliOrdiniAppartenentiAdUnaCaategoria PASSED-----------");
	}
	
	private static void testTrovaTutteLeCategorieDistinteDaUnOrdine(CategoriaService categoriaService, OrdineService ordineService, ArticoloService articoloService) throws Exception{
		System.out.println("-------------testTrovaTutteLeCategorieDistinteDaUnOrdine INIZIO-----------");
		
		/* creazione e inserimento ordine in DB */
		Ordine nuovoOrdine = new Ordine("Niko Pandetta", "Via mosca 52",
				new SimpleDateFormat("yyyy/MM/dd").parse("2023/02/20"));
		ordineService.inserisciNuovo(nuovoOrdine);
		/* controllo inserimento effettuato correttamente */
		if (nuovoOrdine.getId() == null)
			throw new RuntimeException("testTrovaTutteLeCategorieDistinteDaUnOrdine: FALLITO inserimento ordine non avvenuto");

		/* creazione e inserimento articolo in DB */
		Articolo nuovoArticolo = new Articolo("articolo1", "numeroSeriale1", 21,
				new SimpleDateFormat("yyyy/MM/dd").parse("2023/01/01"), ordineService.caricaSingoloElemento(nuovoOrdine.getId()));
		articoloService.inserisciNuovo(nuovoArticolo);
		/* controllo inserimento effettuato correttamente */
		if (nuovoArticolo.getId() == null)
			throw new RuntimeException("testTrovaTutteLeCategorieDistinteDaUnOrdine: FALLITO inserimento articolo non avvenuto");

		/* creazione e inserimento articolo in DB */
		Articolo nuovoArticolo2 = new Articolo("articolo2", "numeroSeriale2", 17,
				new SimpleDateFormat("yyyy/MM/dd").parse("2023/01/01"), ordineService.caricaSingoloElemento(nuovoOrdine.getId()));
		articoloService.inserisciNuovo(nuovoArticolo2);
		/* controllo inserimento effettuato correttamente */
		if (nuovoArticolo2.getId() == null)
			throw new RuntimeException("testTrovaTutteLeCategorieDistinteDaUnOrdine: FALLITO inserimento articolo non avvenuto");
		
		/* creazione e inserimento articolo in DB */
		Articolo nuovoArticolo3 = new Articolo("articolo3", "numeroSeriale3", 15,
				new SimpleDateFormat("yyyy/MM/dd").parse("2023/01/01"), ordineService.caricaSingoloElemento(nuovoOrdine.getId()));
		articoloService.inserisciNuovo(nuovoArticolo3);
		/* controllo inserimento effettuato correttamente */
		if (nuovoArticolo3.getId() == null)
			throw new RuntimeException("testTrovaTutteLeCategorieDistinteDaUnOrdine: FALLITO inserimento articolo non avvenuto");
		
		/* creazione e inserimento categoria */
		Categoria nuovaCategoria = new Categoria("descrizione1","codice1");
		categoriaService.inserisciNuovo(nuovaCategoria);
		/* controllo inserimento effettuato correttamente */
		if(nuovaCategoria.getId() == null)
			throw new RuntimeException("testTrovaTutteLeCategorieDistinteDaUnOrdine: FALLITO inserimento categoria non riuscito");
		
		/* creazione e inserimento categoria */
		Categoria nuovaCategoria2 = new Categoria("descrizione2","codice2");
		categoriaService.inserisciNuovo(nuovaCategoria2);
		/* controllo inserimento effettuato correttamente */
		if(nuovaCategoria2.getId() == null)
			throw new RuntimeException("testTrovaTutteLeCategorieDistinteDaUnOrdine: FALLITO inserimento categoria non riuscito");
		
		/* primo collegamento articolo-categoria */
		categoriaService.aggiungiArticolo(nuovaCategoria, nuovoArticolo);
		/* controllo aggiunta effettuata correttamente */
		Categoria categoriaReloaded = categoriaService.caricaSingoloElementoEager(nuovaCategoria.getId());
		if(categoriaReloaded.getArticoli().isEmpty())
			throw new RuntimeException("testTrovaTutteLeCategorieDistinteDaUnOrdine: FALLITO record non collegati");

		/* secondo collegamento articolo-categoria */
		categoriaService.aggiungiArticolo(nuovaCategoria2, nuovoArticolo2);
		/* controllo aggiunta effettuata correttamente */
		Categoria categoriaReloaded2 = categoriaService.caricaSingoloElementoEager(nuovaCategoria2.getId());
		if(categoriaReloaded2.getArticoli().isEmpty())
			throw new RuntimeException("testTrovaTutteLeCategorieDistinteDaUnOrdine: FALLITO record non collegati");

		/* terzo collegamento articolo-categoria */
		categoriaService.aggiungiArticolo(nuovaCategoria, nuovoArticolo3);
		/* controllo aggiunta effettuata correttamente */
		Categoria categoriaReloaded3 = categoriaService.caricaSingoloElementoEager(nuovaCategoria.getId());
		if(categoriaReloaded3.getArticoli().isEmpty())
			throw new RuntimeException("testTrovaTutteLeCategorieDistinteDaUnOrdine: FALLITO record non collegati");
	
		/* chiamata metodo per trovare tutte le categorie distinte  */
		List<Categoria> categorieDiUnOrdine = categoriaService.trovaTutteLeCategorieDistinreDaUnOrdine(nuovoOrdine);
		if(categorieDiUnOrdine.isEmpty())
			throw new RuntimeException("testTrovaTutteLeCategorieDistinteDaUnOrdine: FALLITO lista vuota");
		
		/* rimozione articolo */
		articoloService.disassocia(nuovoArticolo.getId());
		articoloService.disassocia(nuovoArticolo2.getId());
		articoloService.disassocia(nuovoArticolo3.getId());
		articoloService.rimuovi(nuovoArticolo.getId());
		articoloService.rimuovi(nuovoArticolo2.getId());
		articoloService.rimuovi(nuovoArticolo3.getId());
		
		/* rimozione ordine */
		ordineService.rimuovi(nuovoOrdine.getId());
		
		/* rimozione categoria */
		categoriaService.rimuovi(nuovaCategoria.getId());
		categoriaService.rimuovi(nuovaCategoria2.getId());
		
		System.out.println("-------------testTrovaTutteLeCategorieDistinteDaUnOrdine PASSED-----------");
	}
	
	private static void testTrovaLaSommaDelPrezzoDiTuttiGliArticoliDiUnaCategoria(CategoriaService categoriaService, OrdineService ordineService, ArticoloService articoloService) throws Exception{
		System.out.println("-------------testTrovaLaSommaDelPrezzoDiTuttiGliArticoliDiUnaCategoria INIZIO-----------");
		
		/* creazione e inserimento ordine in DB */
		Ordine nuovoOrdine = new Ordine("Niko Pandetta", "Via mosca 52",
				new SimpleDateFormat("yyyy/MM/dd").parse("2023/02/20"));
		ordineService.inserisciNuovo(nuovoOrdine);
		/* controllo inserimento effettuato correttamente */
		if (nuovoOrdine.getId() == null)
			throw new RuntimeException("testTrovaLaSommaDelPrezzoDiTuttiGliArticoliDiUnaCategoria: FALLITO inserimento ordine non avvenuto");

		/* creazione e inserimento ordine in DB */
		Ordine nuovoOrdine2 = new Ordine("Niko Pandetta", "Via mosca 52",
				new SimpleDateFormat("yyyy/MM/dd").parse("2023/02/20"));
		ordineService.inserisciNuovo(nuovoOrdine2);
		/* controllo inserimento effettuato correttamente */
		if (nuovoOrdine2.getId() == null)
			throw new RuntimeException("testTrovaLaSommaDelPrezzoDiTuttiGliArticoliDiUnaCategoria: FALLITO inserimento ordine non avvenuto");

		/* creazione e inserimento articolo in DB */
		Articolo nuovoArticolo = new Articolo("articolo1", "numeroSeriale1", 21,
				new SimpleDateFormat("yyyy/MM/dd").parse("2023/01/01"), ordineService.caricaSingoloElemento(nuovoOrdine.getId()));
		articoloService.inserisciNuovo(nuovoArticolo);
		/* controllo inserimento effettuato correttamente */
		if (nuovoArticolo.getId() == null)
			throw new RuntimeException("testTrovaLaSommaDelPrezzoDiTuttiGliArticoliDiUnaCategoria: FALLITO inserimento articolo non avvenuto");

		/* creazione e inserimento articolo in DB */
		Articolo nuovoArticolo2 = new Articolo("articolo2", "numeroSeriale2", 17,
				new SimpleDateFormat("yyyy/MM/dd").parse("2023/01/01"), ordineService.caricaSingoloElemento(nuovoOrdine.getId()));
		articoloService.inserisciNuovo(nuovoArticolo2);
		/* controllo inserimento effettuato correttamente */
		if (nuovoArticolo2.getId() == null)
			throw new RuntimeException("testTrovaLaSommaDelPrezzoDiTuttiGliArticoliDiUnaCategoria: FALLITO inserimento articolo non avvenuto");
		
		/* creazione e inserimento articolo in DB */
		Articolo nuovoArticolo3 = new Articolo("articolo3", "numeroSeriale3", 15,
				new SimpleDateFormat("yyyy/MM/dd").parse("2023/01/01"), ordineService.caricaSingoloElemento(nuovoOrdine2.getId()));
		articoloService.inserisciNuovo(nuovoArticolo3);
		/* controllo inserimento effettuato correttamente */
		if (nuovoArticolo3.getId() == null)
			throw new RuntimeException("testTrovaLaSommaDelPrezzoDiTuttiGliArticoliDiUnaCategoria: FALLITO inserimento articolo non avvenuto");
		
		/* creazione e inserimento categoria */
		Categoria nuovaCategoria = new Categoria("descrizione1","codice1");
		categoriaService.inserisciNuovo(nuovaCategoria);
		/* controllo inserimento effettuato correttamente */
		if(nuovaCategoria.getId() == null)
			throw new RuntimeException("testTrovaLaSommaDelPrezzoDiTuttiGliArticoliDiUnaCategoria: FALLITO inserimento categoria non riuscito");
		
		/* primo collegamento articolo-categoria */
		categoriaService.aggiungiArticolo(nuovaCategoria, nuovoArticolo);
		/* controllo aggiunta effettuata correttamente */
		Categoria categoriaReloaded = categoriaService.caricaSingoloElementoEager(nuovaCategoria.getId());
		if(categoriaReloaded.getArticoli().isEmpty())
			throw new RuntimeException("testTrovaLaSommaDelPrezzoDiTuttiGliArticoliDiUnaCategoria: FALLITO record non collegati");

		/* secondo collegamento articolo-categoria */
		categoriaService.aggiungiArticolo(nuovaCategoria, nuovoArticolo2);
		/* controllo aggiunta effettuata correttamente */
		Categoria categoriaReloaded2 = categoriaService.caricaSingoloElementoEager(nuovaCategoria.getId());
		if(categoriaReloaded2.getArticoli().isEmpty())
			throw new RuntimeException("testTrovaLaSommaDelPrezzoDiTuttiGliArticoliDiUnaCategoria: FALLITO record non collegati");
		
		/* terzo collegamento articolo-categoria */
		categoriaService.aggiungiArticolo(nuovaCategoria, nuovoArticolo3);
		/* controllo aggiunta effettuata correttamente */
		Categoria categoriaReloaded3 = categoriaService.caricaSingoloElementoEager(nuovaCategoria.getId());
		if(categoriaReloaded3.getArticoli().isEmpty())
			throw new RuntimeException("testTrovaLaSommaDelPrezzoDiTuttiGliArticoliDiUnaCategoria: FALLITO record non collegati");
	
		categoriaService.trovaLaSommaDelPrezzoDiTuttiGliArticoliDiUnaCategoria(nuovaCategoria);

		/* rimozione articolo */
		articoloService.disassocia(nuovoArticolo.getId());
		articoloService.disassocia(nuovoArticolo2.getId());
		articoloService.disassocia(nuovoArticolo3.getId());
		articoloService.rimuovi(nuovoArticolo.getId());
		articoloService.rimuovi(nuovoArticolo2.getId());
		articoloService.rimuovi(nuovoArticolo3.getId());
		
		/* rimozione ordine */
		ordineService.rimuovi(nuovoOrdine.getId());
		ordineService.rimuovi(nuovoOrdine2.getId());
		
		/* rimozione categoria */
		categoriaService.rimuovi(nuovaCategoria.getId());
		
		System.out.println("-------------testTrovaLaSommaDelPrezzoDiTuttiGliArticoliDiUnaCategoria PASSED-----------");
	}
	
	private static void testTrovaLOrdinePiuRecenteInTerminiDiSpedizioneDataUnaCategoria(CategoriaService categoriaService, OrdineService ordineService, ArticoloService articoloService) throws Exception{
		System.out.println("-------------testTrovaLOrdinePiuRecenteInTerminiDiSpedizioneDataUnaCategoria INIZIO-----------");
		
		/* creazione e inserimento ordine in DB */
		Ordine nuovoOrdine = new Ordine("Niko Pandetta", "Via mosca 52",
				new SimpleDateFormat("yyyy/MM/dd").parse("2023/02/20"));
		nuovoOrdine.setDataSpedizione(new SimpleDateFormat("yyyy/MM/dd").parse("2023/01/01"));
		ordineService.inserisciNuovo(nuovoOrdine);
		/* controllo inserimento effettuato correttamente */
		if (nuovoOrdine.getId() == null)
			throw new RuntimeException("testTrovaLOrdinePiuRecenteInTerminiDiSpedizioneDataUnaCategoria: FALLITO inserimento ordine non avvenuto");

		/* creazione e inserimento ordine in DB */
		Ordine nuovoOrdine2 = new Ordine("Gigi Buffon", "Via mosca 52",
				new SimpleDateFormat("yyyy/MM/dd").parse("2023/02/20"));
		nuovoOrdine2.setDataSpedizione(new SimpleDateFormat("yyyy/MM/dd").parse("2022/12/24"));
		ordineService.inserisciNuovo(nuovoOrdine2);
		/* controllo inserimento effettuato correttamente */
		if (nuovoOrdine2.getId() == null)
			throw new RuntimeException("testTrovaLOrdinePiuRecenteInTerminiDiSpedizioneDataUnaCategoria: FALLITO inserimento ordine non avvenuto");

		/* creazione e inserimento articolo in DB */
		Articolo nuovoArticolo = new Articolo("articolo1", "numeroSeriale1", 21,
				new SimpleDateFormat("yyyy/MM/dd").parse("2023/01/01"), ordineService.caricaSingoloElemento(nuovoOrdine.getId()));
		articoloService.inserisciNuovo(nuovoArticolo);
		/* controllo inserimento effettuato correttamente */
		if (nuovoArticolo.getId() == null)
			throw new RuntimeException("testTrovaLOrdinePiuRecenteInTerminiDiSpedizioneDataUnaCategoria: FALLITO inserimento articolo non avvenuto");

		/* creazione e inserimento articolo in DB */
		Articolo nuovoArticolo2 = new Articolo("articolo2", "numeroSeriale2", 17,
				new SimpleDateFormat("yyyy/MM/dd").parse("2023/01/01"), ordineService.caricaSingoloElemento(nuovoOrdine2.getId()));
		articoloService.inserisciNuovo(nuovoArticolo2);
		/* controllo inserimento effettuato correttamente */
		if (nuovoArticolo2.getId() == null)
			throw new RuntimeException("testTrovaLOrdinePiuRecenteInTerminiDiSpedizioneDataUnaCategoria: FALLITO inserimento articolo non avvenuto");
		
		/* creazione e inserimento categoria */
		Categoria nuovaCategoria = new Categoria("descrizione1","codice1");
		categoriaService.inserisciNuovo(nuovaCategoria);
		/* controllo inserimento effettuato correttamente */
		if(nuovaCategoria.getId() == null)
			throw new RuntimeException("testTrovaLOrdinePiuRecenteInTerminiDiSpedizioneDataUnaCategoria: FALLITO inserimento categoria non riuscito");
	
		/* primo collegamento articolo-categoria */
		categoriaService.aggiungiArticolo(nuovaCategoria, nuovoArticolo);
		/* controllo aggiunta effettuata correttamente */
		Categoria categoriaReloaded = categoriaService.caricaSingoloElementoEager(nuovaCategoria.getId());
		if(categoriaReloaded.getArticoli().isEmpty())
			throw new RuntimeException("testTrovaLOrdinePiuRecenteInTerminiDiSpedizioneDataUnaCategoria: FALLITO record non collegati");

		/* secondo collegamento articolo-categoria */
		categoriaService.aggiungiArticolo(nuovaCategoria, nuovoArticolo2);
		/* controllo aggiunta effettuata correttamente */
		Categoria categoriaReloaded2 = categoriaService.caricaSingoloElementoEager(nuovaCategoria.getId());
		if(categoriaReloaded2.getArticoli().isEmpty())
			throw new RuntimeException("testTrovaLOrdinePiuRecenteInTerminiDiSpedizioneDataUnaCategoria: FALLITO record non collegati");
	
		if(ordineService.trovaLOrdinePiuRecenteInTerminiDiSpedizioneDataUnaCategoria(nuovaCategoria) == null)
			throw new RuntimeException("testTrovaLOrdinePiuRecenteInTerminiDiSpedizioneDataUnaCategoria: FALLITO nessun ordine restituito");
			
		/* rimozione articolo */
		articoloService.disassocia(nuovoArticolo.getId());
		articoloService.disassocia(nuovoArticolo2.getId());
		articoloService.rimuovi(nuovoArticolo.getId());
		articoloService.rimuovi(nuovoArticolo2.getId());
	
		/* rimozione ordine */
		ordineService.rimuovi(nuovoOrdine.getId());
		ordineService.rimuovi(nuovoOrdine2.getId());
		
		/* rimozione categoria */
		categoriaService.rimuovi(nuovaCategoria.getId());
		
		System.out.println("-------------testTrovaLOrdinePiuRecenteInTerminiDiSpedizioneDataUnaCategoria PASSED-----------");
	}
	
	private static void testTrovaTuttiIcodiciDiCategorieDiOrdiniEffettuatiInUnDatoMese(CategoriaService categoriaService, OrdineService ordineService, ArticoloService articoloService) throws Exception{
		System.out.println("-------------testTrovaTuttiIcodiciDiCategorieDiOrdiniEffettuatiInUnDatoMese INIZIO-----------");
		
		/* creazione e inserimento ordine in DB */
		Ordine nuovoOrdine = new Ordine("Niko Pandetta", "Via mosca 52",
				new SimpleDateFormat("yyyy/MM/dd").parse("2023/02/20"));
		nuovoOrdine.setDataSpedizione(new SimpleDateFormat("yyyy/MM/dd").parse("2022/11/11"));
		ordineService.inserisciNuovo(nuovoOrdine);
		/* controllo inserimento effettuato correttamente */
		if (nuovoOrdine.getId() == null)
			throw new RuntimeException("testTrovaTuttiIcodiciDiCategorieDiOrdiniEffettuatiInUnDatoMese: FALLITO inserimento ordine non avvenuto");

		/* creazione e inserimento ordine in DB */
		Ordine nuovoOrdine2 = new Ordine("Gigi Buffon", "Via mosca 52",
				new SimpleDateFormat("yyyy/MM/dd").parse("2023/02/20"));
		nuovoOrdine2.setDataSpedizione(new SimpleDateFormat("yyyy/MM/dd").parse("2022/11/12"));
		ordineService.inserisciNuovo(nuovoOrdine2);
		/* controllo inserimento effettuato correttamente */
		if (nuovoOrdine2.getId() == null)
			throw new RuntimeException("testTrovaTuttiIcodiciDiCategorieDiOrdiniEffettuatiInUnDatoMese: FALLITO inserimento ordine non avvenuto");

		/* creazione e inserimento articolo in DB */
		Articolo nuovoArticolo = new Articolo("articolo1", "numeroSeriale1", 21,
				new SimpleDateFormat("yyyy/MM/dd").parse("2023/01/01"), ordineService.caricaSingoloElemento(nuovoOrdine.getId()));
		articoloService.inserisciNuovo(nuovoArticolo);
		/* controllo inserimento effettuato correttamente */
		if (nuovoArticolo.getId() == null)
			throw new RuntimeException("testTrovaTuttiIcodiciDiCategorieDiOrdiniEffettuatiInUnDatoMese: FALLITO inserimento articolo non avvenuto");

		/* creazione e inserimento articolo in DB */
		Articolo nuovoArticolo2 = new Articolo("articolo2", "numeroSeriale2", 17,
				new SimpleDateFormat("yyyy/MM/dd").parse("2023/01/01"), ordineService.caricaSingoloElemento(nuovoOrdine2.getId()));
		articoloService.inserisciNuovo(nuovoArticolo2);
		/* controllo inserimento effettuato correttamente */
		if (nuovoArticolo2.getId() == null)
			throw new RuntimeException("testTrovaTuttiIcodiciDiCategorieDiOrdiniEffettuatiInUnDatoMese: FALLITO inserimento articolo non avvenuto");
		
		/* creazione e inserimento categoria */
		Categoria nuovaCategoria = new Categoria("descrizione1","codice1");
		categoriaService.inserisciNuovo(nuovaCategoria);
		/* controllo inserimento effettuato correttamente */
		if(nuovaCategoria.getId() == null)
			throw new RuntimeException("testTrovaTuttiIcodiciDiCategorieDiOrdiniEffettuatiInUnDatoMese: FALLITO inserimento categoria non riuscito");
	
		/* creazione e inserimento categoria */
		Categoria nuovaCategoria2 = new Categoria("descrizione1","codice2");
		categoriaService.inserisciNuovo(nuovaCategoria2);
		/* controllo inserimento effettuato correttamente */
		if(nuovaCategoria2.getId() == null)
			throw new RuntimeException("testTrovaTuttiIcodiciDiCategorieDiOrdiniEffettuatiInUnDatoMese: FALLITO inserimento categoria non riuscito");
	
		/* primo collegamento articolo-categoria */
		categoriaService.aggiungiArticolo(nuovaCategoria, nuovoArticolo);
		/* controllo aggiunta effettuata correttamente */
		Categoria categoriaReloaded = categoriaService.caricaSingoloElementoEager(nuovaCategoria.getId());
		if(categoriaReloaded.getArticoli().isEmpty())
			throw new RuntimeException("testTrovaTuttiIcodiciDiCategorieDiOrdiniEffettuatiInUnDatoMese: FALLITO record non collegati");

		/* secondo collegamento articolo-categoria */
		categoriaService.aggiungiArticolo(nuovaCategoria2, nuovoArticolo2);
		/* controllo aggiunta effettuata correttamente */
		Categoria categoriaReloaded2 = categoriaService.caricaSingoloElementoEager(nuovaCategoria.getId());
		if(categoriaReloaded2.getArticoli().isEmpty())
			throw new RuntimeException("testTrovaTuttiIcodiciDiCategorieDiOrdiniEffettuatiInUnDatoMese: FALLITO record non collegati");
	
		Date dataInput = new SimpleDateFormat("yyyy/MM/dd").parse("2022/11/12");
		if(categoriaService.trovaTuttiIcodiciDiCategorieDiOrdiniEffettuatiInUnDatoMese(dataInput).size() < 1)
			throw new RuntimeException("testTrovaTuttiIcodiciDiCategorieDiOrdiniEffettuatiInUnDatoMese: FALLITO risultato errato");
		
		/* rimozione articolo */
		articoloService.disassocia(nuovoArticolo.getId());
		articoloService.disassocia(nuovoArticolo2.getId());
		articoloService.rimuovi(nuovoArticolo.getId());
		articoloService.rimuovi(nuovoArticolo2.getId());
	
		/* rimozione ordine */
		ordineService.rimuovi(nuovoOrdine.getId());
		ordineService.rimuovi(nuovoOrdine2.getId());
		
		/* rimozione categoria */
		categoriaService.rimuovi(nuovaCategoria.getId());
		categoriaService.rimuovi(nuovaCategoria2.getId());
		
		System.out.println("-------------testTrovaTuttiIcodiciDiCategorieDiOrdiniEffettuatiInUnDatoMese PASSED-----------");
	}
}
