package it.prova.gestioneordiniarticolicategorie.dao.categoria;

import it.prova.gestioneordiniarticolicategorie.dao.IBaseDAO;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;

public interface CategoriaDAO extends IBaseDAO<Categoria>{
	public void disissocia(Long idCategoria) throws Exception;
	
	public Categoria findByIdFetchingArticoli(Long id) throws Exception;
}
