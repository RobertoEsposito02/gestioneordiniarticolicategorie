package it.prova.gestioneordiniarticolicategorie.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import it.prova.gestioneordiniarticolicategorie.dao.EntityManagerUtil;
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
			
			
			
			testInserimentoCategoria(categoriaService);
			
			testAggiornamentoCategoria(categoriaService);
			
			testAggiungiArticoloEsistenteACategoriaEsistente(articoloService, categoriaService);
			
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
		
		Articolo articoloEsistenteTestCollegaArticoloAOrdineEsistente = articoloServiceInstance.listAll().get(0);
		
		articoloServiceInstance.collegaOrdine(nuovoOrdineTestCollegaArticoloAOrdineEsistente, articoloEsistenteTestCollegaArticoloAOrdineEsistente);
		
		System.out.println("-------------testCollegaArticoloAOrdineEsistene PASSED-----------");
	}
	
	private static void testScollegaArticoloDaOrdineEsistente(OrdineService ordineServiceInstance, ArticoloService articoloServiceInstance) throws Exception{
		System.out.println("-------------testScollegaArticoloDaOrdineEsistente INIZIO-----------");
		
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
		
		Categoria categoriaEsistente = categoriaService.listAll().get(0);
		categoriaEsistente.setDescrizione("nuovaDescrizione");
		categoriaService.aggiorna(categoriaEsistente);
		
		System.out.println("-------------testAggiornamentoCategoria PASSED-----------");
	}
	
	private static void testAggiungiArticoloEsistenteACategoriaEsistente(ArticoloService articoloService, CategoriaService categoriaService) throws Exception{
		System.out.println("-------------testAggiungiArticoloEsistenteACategoriaEsistente INIZIO-----------");
		
		Categoria categoriaEsistente = categoriaService.listAll().get(0);
		
		Articolo articoloEsistente = articoloService.listAll().get(0);
		
		categoriaService.aggiungiArticolo(categoriaEsistente, articoloEsistente);
		
		
		System.out.println("-------------testAggiungiArticoloEsistenteACategoriaEsistente PASSED-----------");
	}
	
	private static void testAggiungiCategoriaEsistenteAdArticoloEsistente(ArticoloService articoloService, CategoriaService categoriaService) throws Exception{
		System.out.println("-------------testAggiungiCategoriaEsistenteAdArticoloEsistente INIZIO-----------");
		
		
		
		System.out.println("-------------testAggiungiCategoriaEsistenteAdArticoloEsistente PASSED-----------");
	}
}
