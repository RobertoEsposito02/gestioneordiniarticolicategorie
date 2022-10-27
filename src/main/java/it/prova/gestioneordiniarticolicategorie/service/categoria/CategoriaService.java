package it.prova.gestioneordiniarticolicategorie.service.categoria;

import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.categoria.CategoriaDAO;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public interface CategoriaService {
	public void setCategoriaDAO(CategoriaDAO categoriaDAO);
	
	public List<Categoria> listAll() throws Exception;
	
	public Categoria caricaSingoloElemento(Long id) throws Exception;
	
	public Categoria caricaSingoloElementoEager(Long id) throws Exception;

	public void aggiorna(Categoria categoriaInstance) throws Exception;

	public void inserisciNuovo(Categoria categoriaInstance) throws Exception;

	public void rimuovi(Long idCategoria) throws Exception;
	
	public void aggiungiArticolo(Categoria categoria, Articolo articolo) throws Exception;
	
	public void disassociaCategoriaArticolo(Long idCategoria) throws Exception;
}
