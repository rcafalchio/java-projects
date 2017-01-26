package com.leilaopecuario.persistencia;

import com.leilaopecuario.entidades.Usuario;
import com.leilaopecuario.exception.PersistenciaException;
import com.leilaopecuario.vo.UsuarioVO;

public interface IControladorPersistenciaUsuario {

	/**
	 * Realiza a persistencia do usuário
	 * 
	 * @param usuarioVO
	 * @throws PersistenciaException
	 */
	public void inserirUsuario(UsuarioVO usuarioVO) throws PersistenciaException;

	/**
	 * Verifica se o login já existe
	 * 
	 * @param login
	 * @return true/false
	 * @throws PersistenciaException
	 */
	public boolean verificaExistenciaLogin(String login) throws PersistenciaException;

	/**
	 * Recupera o usuario pelo login
	 * 
	 * @param login
	 * @return Usuario
	 * @throws PersistenciaException
	 */
	public Usuario recuperaUsuarioPorLogin(String login) throws PersistenciaException;

}
