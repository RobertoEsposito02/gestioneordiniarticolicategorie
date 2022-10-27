package it.prova.gestioneordiniarticolicategorie.service.categoria;

import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.categoria.CategoriaDAO;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;

public interface CategoriaService {
	public void setCategoriaDAO(CategoriaDAO categoriaDAO);
	
	public List<Categoria> listAll() throws Exception;
}
