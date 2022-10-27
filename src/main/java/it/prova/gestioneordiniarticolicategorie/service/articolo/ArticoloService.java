package it.prova.gestioneordiniarticolicategorie.service.articolo;

import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.articolo.ArticoloDAO;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public interface ArticoloService {
	public void setArticoloDAO(ArticoloDAO articoloDAO);
	
	public List<Articolo> listAll() throws Exception;
	
	public Articolo caricaSingoloElemento(Long id) throws Exception;
	
	public void aggiorna(Articolo articoloInstance) throws Exception;

	public void inserisciNuovo(Articolo articoloInstance) throws Exception;

	public void rimuovi(Long idArticolo) throws Exception;
	
	public void collegaOrdine(Ordine ordine, Articolo articolo) throws Exception;
	
	public void aggiungiCategoria(Categoria categoria, Articolo articolo) throws Exception;
	
	public void disassocia(Long idArticolo) throws Exception;
	
	public Articolo caricaSingoloElementoEager(Long idLong) throws Exception;
}
