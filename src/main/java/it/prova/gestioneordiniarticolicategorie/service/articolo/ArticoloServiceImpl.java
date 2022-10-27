package it.prova.gestioneordiniarticolicategorie.service.articolo;

import java.util.List;

import javax.persistence.EntityManager;

import it.prova.gestioneordiniarticolicategorie.dao.EntityManagerUtil;
import it.prova.gestioneordiniarticolicategorie.dao.articolo.ArticoloDAO;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;

public class ArticoloServiceImpl implements ArticoloService{

	private ArticoloDAO articoloDAO;
	
	@Override
	public void setArticoloDAO(ArticoloDAO articoloDAO) {
		this.articoloDAO = articoloDAO;
	}

	@Override
	public List<Articolo> listAll() throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		try {
			
			articoloDAO.setEntityManager(entityManager);
			
			return articoloDAO.list();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
