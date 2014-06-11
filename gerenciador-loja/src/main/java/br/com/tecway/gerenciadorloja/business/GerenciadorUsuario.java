package br.com.tecway.gerenciadorloja.business;

import java.util.List;

import br.com.tecway.gerenciadorloja.dao.UsuarioDAO;
import br.com.tecway.gerenciadorloja.dao.UsuarioDAOImpl;
import br.com.tecway.gerenciadorloja.entity.UsuarioEntity;
import br.com.tecway.gerenciadorloja.exception.BusinessException;
import br.com.tecway.gerenciadorloja.exception.DAOException;
import br.com.tecway.gerenciadorloja.utils.SegurancaUtils;
import br.com.tecway.gerenciadorloja.utils.DAOUtils;

public class GerenciadorUsuario implements IGerenciadorUsuario {

	@Override
	public void cadastrarUsuario(UsuarioEntity usuarioEntity) throws BusinessException, DAOException {

		final UsuarioDAO usuarioDAO = new UsuarioDAOImpl(DAOUtils.getEntityManager());

		if (usuarioDAO.buscarUsuarioPorLogin(usuarioEntity.getLogin()) != null) {
			throw new BusinessException("Login já existente na base, digite outro login!");
		}

		final String senha = usuarioEntity.getSenha();
		usuarioEntity.setSenha(SegurancaUtils.geraCriptografia(senha));
		// Grava o usuário
		usuarioDAO.save(usuarioEntity);

	}

	@Override
	public UsuarioEntity autenticarUsuario(String login, String senha) throws DAOException, BusinessException {

		final UsuarioDAO usuarioDAO = new UsuarioDAOImpl(DAOUtils.getEntityManager());
		final UsuarioEntity usuarioEntity = usuarioDAO.buscarUsuarioPorLogin(login);

		if (usuarioEntity == null) {

			throw new BusinessException("Usuário não cadastrado!");

		}

		if (!usuarioEntity.getSenha().equals(SegurancaUtils.geraCriptografia(senha))) {

			throw new BusinessException("Senha incorreta!");

		}

		return usuarioEntity;

	}

	@Override
	public List<UsuarioEntity> buscarTodosUsuarios() {
		final UsuarioDAO usuarioDAO = new UsuarioDAOImpl(DAOUtils.getEntityManager());
		return usuarioDAO.findAll();
	}
}
