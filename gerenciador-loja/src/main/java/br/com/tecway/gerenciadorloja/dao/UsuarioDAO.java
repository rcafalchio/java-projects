package br.com.tecway.gerenciadorloja.dao;

import br.com.tecway.gerenciadorloja.entity.UsuarioEntity;
import br.com.tecway.gerenciadorloja.exception.DAOException;

/**
 * 
 * @author Ricardo Cafalchio
 * @since 13/12/2013
 */
public interface UsuarioDAO extends IGenericDAO<UsuarioEntity> {

	/**
	 * Recupera o usuário atraves do login
	 * 
	 * @param login
	 * @return
	 * @throws DAOException
	 */
	UsuarioEntity buscarUsuarioPorLogin(String login) throws DAOException;

}
