package it.prova.gestioneordiniarticolicategorie.test;

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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
