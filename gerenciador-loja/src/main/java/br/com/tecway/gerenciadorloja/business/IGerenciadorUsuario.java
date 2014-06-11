package br.com.tecway.gerenciadorloja.business;

import java.util.List;

import br.com.tecway.gerenciadorloja.entity.UsuarioEntity;
import br.com.tecway.gerenciadorloja.exception.BusinessException;
import br.com.tecway.gerenciadorloja.exception.DAOException;

/**
 * 
 * @author Ricardo Cafalchio
 * @since 27/11/2013
 */
public interface IGerenciadorUsuario {


	/**
	 * Cadastra o usuário
	 * 
	 * @param usuarioEntity
	 * @throws BusinessException
	 * @throws DAOException
	 */
	void cadastrarUsuario(UsuarioEntity usuarioEntity) throws BusinessException, DAOException;

	/**
	 * Verifica se pode autenticar o usuário
	 * 
	 * @param login
	 * @param senha
	 * @return UsuarioEntity
	 * @throws DAOException
	 * @throws BusinessException
	 */
	UsuarioEntity autenticarUsuario(String login, String senha) throws DAOException, BusinessException;

	/**
	 * Busca todos os usuários cadastrados no sistema
	 * 
	 * @return List<UsuarioEntity>
	 */
	List<UsuarioEntity> buscarTodosUsuarios();

}
