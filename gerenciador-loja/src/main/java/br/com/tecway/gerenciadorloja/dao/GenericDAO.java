package br.com.tecway.gerenciadorloja.dao;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.Id;
import javax.persistence.Query;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import br.com.tecway.gerenciadorloja.entity.AbstractEntity;
import br.com.tecway.gerenciadorloja.exception.DAOException;

public class GenericDAO<T extends AbstractEntity> implements IGenericDAO<T> {

	protected static final Logger LOGGER = LogManager.getLogger(GenericDAO.class);

	protected EntityManager entityManager;

	public GenericDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * Método utilizado para persistir um novo objeto no banco de dados.
	 * 
	 * @param entity
	 * @throws DAOException
	 */
	@Override
	public void save(final T entity) throws DAOException {
		try {
			this.entityManager.getTransaction().begin();
			this.entityManager.persist(entity);
			entityManager.getTransaction().commit();
			// entityManager.close();
		} catch (final Exception e) {
			throw new DAOException(LOGGER, e);
		}
	}

	/**
	 * Método utilizado para persistir uma lista de novos objetos no banco de dados.
	 * 
	 * @param list
	 *            Collection<T>
	 */
	@Override
	public void save(final Collection<T> list) throws DAOException {
		for (final T entity : list) {
			this.entityManager.getTransaction().begin();
			this.save(entity);
			this.entityManager.getTransaction().commit();
			// entityManager.close();
		}
	}

	/**
	 * Método utilizado para atualizar um objeto no banco de dados.
	 * 
	 * @param entity
	 */
	@Override
	public void update(final T entity) throws DAOException {
		try {
			this.entityManager.getTransaction().begin();
			this.merge(entity);
			this.entityManager.getTransaction().commit();
			// entityManager.close();
		} catch (final Exception e) {
			throw new DAOException(LOGGER, e);
		}
	}

	/**
	 * Método utilizado para atualizar um objeto no banco de dados.
	 * 
	 * @param entity
	 */
	@Override
	public T merge(final T entity) throws DAOException {

		this.entityManager.getTransaction().begin();
		T t = this.entityManager.merge(entity);
		this.entityManager.getTransaction().commit();
		// entityManager.close();
		return t;

	}

	/**
	 * Método utilizado para apagar um objeto no banco de dados.
	 * 
	 * @param entity
	 */
	@Override
	public void delete(final T entity) throws DAOException {
//		this.entityManager.getTransaction().begin();
		this.deleteByPk(this.getPK(entity));
//		this.entityManager.getTransaction().commit();
		// entityManager.close();
	}

	/**
	 * Método utilizado para apagar um objeto no banco de dados baseado pela PK.
	 * 
	 * @param primaryKey
	 *            Chave primária do objeto
	 */
	@Override
	public void deleteByPk(final Serializable primaryKey) throws DAOException {
		try {
			this.entityManager.getTransaction().begin();
			this.entityManager.remove(this.entityManager.getReference(this.getGenericArgumentClass(), primaryKey));
			this.entityManager.getTransaction().commit();
		} catch (final Exception e) {
			throw new DAOException(LOGGER, e);
		}
	}

	/**
	 * Método utilizado para apagar uma lista de objetos no banco de dados.
	 * 
	 * @param list
	 *            Collection<T>
	 */
	@Override
	public void delete(final Collection<T> list) throws DAOException {
		this.entityManager.getTransaction().begin();
		for (final T entity : list) {
			this.delete(entity);
		}
		this.entityManager.getTransaction().commit();
		// entityManager.close();
	}

	/**
	 * Atualiza o estado do objeto a partir da base de dados, substituindo as alterações feitas para a entidade, se
	 * houver.
	 * 
	 * @param entity
	 */
	@Override
	public void refresh(final T entity) {
		this.entityManager.getTransaction().begin();
		this.entityManager.refresh(entity);
		this.entityManager.getTransaction().commit();
		// entityManager.close();
	}

	/**
	 * Sincronizar o contexto de persistência no banco de dados.
	 */
	@Override
	public void flush() {
		this.entityManager.setFlushMode(FlushModeType.COMMIT);
		this.entityManager.flush();
	}

	/**
	 * Método de pesquisa pela chave primária da entidade.
	 */
	@Override
	public T findByPk(final Serializable primaryKey) {
		return this.entityManager.find(this.getGenericArgumentClass(), primaryKey);
	}

	/**
	 * Método de pesquisa para retornar todas os registros da tabela.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll() {
		final Query query = this.entityManager.createNamedQuery(this.getGenericArgumentClass().getSimpleName()
				+ ".findAll");
		return query.getResultList();
	}

	// ***************************
	// Métodos auxiliares
	// ***************************

	/**
	 * Método utilizado para retornar a classe genérica enviada via parâmetro na super classe.
	 * 
	 * @author F0110415 - Henrique Guedes
	 * 
	 * @return {@link Class<T>}
	 */
	@SuppressWarnings("unchecked")
	private Class<T> getGenericArgumentClass() {
		return (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	/**
	 * Método responsável por retornar o value da PK da entity através de reflexão pela anotação de @Id ou @EmbeddedId
	 * 
	 * @author F0110415 - Henrique Guedes
	 * @since 18/10/2012
	 * @param entity
	 *            Entidade genérica
	 * @return {@link Serializable}
	 */
	private Serializable getPK(final T entity) {
		final Field[] fields = this.getGenericArgumentClass().getDeclaredFields();

		if (fields != null && fields.length > 0) {
			for (final Field field : fields) {
				final Annotation[] annotationsField = field.getAnnotations();
				for (final Annotation annotation : annotationsField) {
					if (annotation.annotationType().equals(EmbeddedId.class)
							|| annotation.annotationType().equals(Id.class)) {
						try {
							final String getMethodName = "get" + field.getName().substring(0, 1).toUpperCase()
									+ field.getName().substring(1);
							final Method getMethod = this.getGenericArgumentClass().getMethod(getMethodName);

							return (Serializable) getMethod.invoke(entity);
						} catch (final NoSuchMethodException e) {
							e.printStackTrace();
						} catch (final IllegalAccessException e) {
							e.printStackTrace();
						} catch (final IllegalArgumentException e) {
							e.printStackTrace();
						} catch (final InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}

		return null;
	}


}
