package it.prova.gestioneordiniarticolicategorie.dao.ordine;

import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.IBaseDAO;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public interface OrdineDAO extends IBaseDAO<Ordine>{
	public Ordine findLOrdinePiuRecenteSpeditoDiUnaCategoria(Categoria categoria) throws Exception;
	
	public List<String> findAllIndirizziDiOrdiniCheHannoArticoliConIlNumeroSerialeCheIniziaCon(String input) throws Exception;
}
