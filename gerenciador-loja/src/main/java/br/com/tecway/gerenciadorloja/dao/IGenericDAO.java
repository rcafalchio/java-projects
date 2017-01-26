package br.com.tecway.gerenciadorloja.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import br.com.tecway.gerenciadorloja.entity.AbstractEntity;
import br.com.tecway.gerenciadorloja.exception.DAOException;

public interface IGenericDAO <T extends AbstractEntity>{
	
	/**
	 * M�todo utilizado para persistir um novo objeto no banco de dados.
	 * 
	 * @param entity
	 */
	void save(T entity) throws DAOException;

	/**
	 * M�todo utilizado para persistir uma lista de novos objetos no banco de
	 * dados.
	 * 
	 * @param list
	 *            Collection<T>
	 */
	void save(Collection<T> list) throws DAOException;

	/**
	 * M�todo utilizado para atualizar um objeto no banco de dados.
	 * 
	 * @param entity
	 */
	void update(T entity) throws DAOException;

	/**
	 * M�todo utilizado para atualizar um objeto no banco de dados.
	 * 
	 * @param entity
	 */
	T merge(T entity) throws DAOException;

	/**
	 * M�todo utilizado para apagar um objeto no banco de dados.
	 * 
	 * @param entity
	 */
	void delete(T entity) throws DAOException;

	/**
	 * M�todo utilizado para apagar um objeto no banco de dados baseado pela PK.
	 * 
	 * @param primaryKey
	 *            Chave prim�ria do objeto
	 */
	void deleteByPk(Serializable primaryKey) throws DAOException;

	/**
	 * M�todo utilizado para apagar uma lista de objetos no banco de dados.
	 * 
	 * @param list
	 *            Collection<T>
	 */
	void delete(Collection<T> list) throws DAOException;

	/**
	 * Atualiza o estado do objeto a partir da base de dados, substituindo as
	 * altera��es feitas para a entidade, se houver.
	 * 
	 * @param entity
	 */
	void refresh(T entity);

	/**
	 * Sincronizar o contexto de persist�ncia no banco de dados.
	 */
	void flush();

	/**
	 * M�todo de pesquisa pela chave prim�ria da entidade.
	 */
	T findByPk(Serializable primaryKey);

	/**
	 * M�todo de pesquisa para retornar todas os registros da tabela.
	 */
	List<T> findAll();


}
