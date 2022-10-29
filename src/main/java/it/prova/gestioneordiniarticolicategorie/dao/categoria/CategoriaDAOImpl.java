package it.prova.gestioneordiniarticolicategorie.dao.categoria;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public class CategoriaDAOImpl implements CategoriaDAO {

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
		entityManager.createNativeQuery("delete from categoria_articolo where categoria_id = :categoriaID")
				.setParameter("categoriaID", idCategoria).executeUpdate();
	}

	@Override
	public Categoria findByIdFetchingArticoli(Long id) throws Exception {
		TypedQuery<Categoria> query = entityManager.createQuery(
				"select c FROM Categoria c left join fetch c.articoli a where c.id = :idCategoria", Categoria.class);
		query.setParameter("idCategoria", id);
		return query.getResultList().stream().findFirst().orElse(null);
	}

	@Override
	public List<Ordine> findAllOrdiniByCategoria(Categoria categoria) throws Exception {
		TypedQuery<Ordine> query = entityManager.createQuery(
				"select o from Ordine o join o.articoli a join a.categorie c where c.descrizione = :descrizione and c.codice = :codice",
				Ordine.class);
		query.setParameter("descrizione", categoria.getDescrizione());
		query.setParameter("codice", categoria.getCodice());
		return query.getResultList();
	}

	@Override
	public List<Categoria> findAllDistinctCategorieFromOrdine(Ordine ordine) throws Exception {
		TypedQuery<Categoria> query = entityManager.createQuery(
				"select distinct c from Categoria c join c.articoli a join a.ordine o where o = :ordine",
				Categoria.class);
		query.setParameter("ordine", ordine);
		return query.getResultList();
	}

	@Override
	public Long findTheTotalSumOfTheArticoliDiUnaCategoria(Categoria categoria) throws Exception {
		TypedQuery<Long> query = entityManager.createQuery("select sum(a.prezzoSingolo) from Articolo a join a.categorie c where c = :categoria", Long.class);
		query.setParameter("categoria", categoria);
		return query.getSingleResult().longValue();
	}
}
