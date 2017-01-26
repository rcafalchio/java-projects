package br.com.tecway.gerenciadorloja.business;

import org.hibernate.exception.ConstraintViolationException;

import br.com.tecway.gerenciadorloja.dao.MarcaProdutoDAO;
import br.com.tecway.gerenciadorloja.dao.MarcaProdutoDAOImpl;
import br.com.tecway.gerenciadorloja.entity.MarcaProdutoEntity;
import br.com.tecway.gerenciadorloja.exception.BusinessException;
import br.com.tecway.gerenciadorloja.exception.DAOException;
import br.com.tecway.gerenciadorloja.utils.ApplicationUtils;
import br.com.tecway.gerenciadorloja.utils.DAOUtils;

/**
 * 
 * @author Ricardo Cafalchio
 * @since 26/11/2013
 */
public class GerenciadorMarcaBusiness implements IGerenciadorMarcaBusiness {

	@Override
	public MarcaProdutoEntity atualizarMarca(MarcaProdutoEntity marcaProdutoEntity) throws BusinessException,
			DAOException {

		MarcaProdutoEntity retorno = null;
		final MarcaProdutoDAO marcaProdutoDAO = new MarcaProdutoDAOImpl(DAOUtils.getEntityManager());
		retorno = marcaProdutoDAO.merge(marcaProdutoEntity);

		return retorno;

	}

	@Override
	public void excluirMarca(MarcaProdutoEntity marcaProdutoEntity) throws BusinessException, DAOException {
		try {
			final MarcaProdutoDAO marcaProdutoDAO = new MarcaProdutoDAOImpl(DAOUtils.getEntityManager());
			marcaProdutoDAO.delete(marcaProdutoEntity);
		} catch (DAOException e) {
			if (ApplicationUtils.verificarException(e.getCause(), ConstraintViolationException.class)) {
				throw new BusinessException("Não é possivel excluir pois existem produtos cadastrados com essa marca!");
			}
		}
	}

}
