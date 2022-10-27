package it.prova.gestioneordiniarticolicategorie.service.ordine;

import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.ordine.OrdineDAO;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public interface OrdineService {
	public void setOrdineDAO(OrdineDAO ordineDAO);
	
	public List<Ordine> listAll() throws Exception;
}
