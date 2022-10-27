package it.prova.gestioneordiniarticolicategorie.dao.categoria;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.prova.gestioneordiniarticolicategorie.model.Categoria;

public class CategoriaDAOImpl implements CategoriaDAO{

	private EntityManager entityManager;
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public List<Categoria> list() throws Exception {
		return entityManager.createQuery("from Categoria", Categoria.class).getResultList();
	}

	@Override
	public Categoria get(Long id) throws Exception {
		return entityManager.find(Categoria.class, id);
	}

	@Override
	public void update(Categoria input) throws Exception {
		if (input == null) {
			throw new Exception("input non valido");
		}
		input = entityManager.merge(input);
	}

	@Override
	public void insert(Categoria input) throws Exception {
		if (input == null) {
			throw new Exception("input non valido");
		}
		entityManager.persist(input);
	}

	@Override
	public void delete(Categoria input) throws Exception {
		if (input == null) {
			throw new Exception("input non valido");
		}
		entityManager.remove(entityManager.merge(input));
	}

	@Override
	public void disissocia(Long idCategoria) throws Exception {
		entityManager.createNativeQuery("delete from categoria_articolo where categoria_id = :categoriaID").setParameter("categoriaID", idCategoria).executeUpdate();
	}

	@Override
	public Categoria findByIdFetchingArticoli(Long id) throws Exception {
		TypedQuery<Categoria> query = entityManager
				.createQuery("select c FROM Categoria c left join fetch c.articoli a where c.id = :idCategoria", Categoria.class);
		query.setParameter("idCategoria", id);
		return query.getResultList().stream().findFirst().orElse(null);
	}
}
