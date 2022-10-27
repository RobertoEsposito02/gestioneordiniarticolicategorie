package it.prova.gestioneordiniarticolicategorie.service.articolo;

import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.articolo.ArticoloDAO;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;

public interface ArticoloService {
	public void setArticoloDAO(ArticoloDAO articoloDAO);
	
	public List<Articolo> listAll() throws Exception;
}
