package it.prova.gestioneordiniarticolicategorie.dao.articolo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.prova.gestioneordiniarticolicategorie.model.Articolo;

public class ArticoloDAOImpl implements ArticoloDAO{

	private EntityManager entityManager;
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Articolo> list() throws Exception {
		return entityManager.createQuery("from Articolo", Articolo.class).getResultList();
	}

	@Override
	public Articolo get(Long id) throws Exception {
		return entityManager.find(Articolo.class, id);
	}

	@Override
	public void update(Articolo input) throws Exception {
		if (input == null) {
			throw new Exception("input non valido");
		}
		input = entityManager.merge(input);
	}

	@Override
	public void insert(Articolo input) throws Exception {
		if (input == null) {
			throw new Exception("input non valido");
		}
		entityManager.persist(input);
	}

	@Override
	public void delete(Articolo input) throws Exception {
		if (input == null) {
			throw new Exception("input non valido");
		}
		entityManager.remove(entityManager.merge(input));
	}

	@Override
	public void scollegaArticoloCategoria(Long idArticolo) throws Exception {
		entityManager.createNativeQuery("delete from categoria_articolo where articolo_id = :articoloID").setParameter("articoloID", idArticolo).executeUpdate();
	}
	
	@Override
	public Articolo findByFetchingCategorie(Long idLong) throws Exception {
		TypedQuery<Articolo> query = entityManager
				.createQuery("select a FROM Articolo a left join fetch a.categorie c where a.id = :idArticolo", Articolo.class);
		query.setParameter("idArticolo", idLong);
		return query.getResultList().stream().findFirst().orElse(null);
	}
	
	
}
