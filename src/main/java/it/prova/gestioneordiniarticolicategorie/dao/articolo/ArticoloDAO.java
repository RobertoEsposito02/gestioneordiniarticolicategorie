package it.prova.gestioneordiniarticolicategorie.dao.articolo;

import it.prova.gestioneordiniarticolicategorie.dao.IBaseDAO;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public interface ArticoloDAO extends IBaseDAO<Articolo>{
	public void scollegaArticoloCategoria(Long idArticolo) throws Exception;
	
	public Articolo findByFetchingCategorie(Long idLong) throws Exception;
	
	public Long findAllPrezziDiArticoliDiUnDestinatario(Ordine ordine) throws Exception;
}
