package it.prova.gestioneordiniarticolicategorie.dao.categoria;

import java.util.Date;
import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.IBaseDAO;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public interface CategoriaDAO extends IBaseDAO<Categoria>{
	public void disissocia(Long idCategoria) throws Exception;
	
	public Categoria findByIdFetchingArticoli(Long id) throws Exception;
	
	public List<Ordine> findAllOrdiniByCategoria(Categoria categoria) throws Exception;
	
	public List<Categoria> findAllDistinctCategorieFromOrdine(Ordine ordine) throws Exception;
	
	public Long findTheTotalSumOfTheArticoliDiUnaCategoria(Categoria categoria) throws Exception;
	
	public List<String> findAllCodiciDistintiDiCategorieDiOrdinEffettuatiInUnMese(Date date) throws Exception;
}
