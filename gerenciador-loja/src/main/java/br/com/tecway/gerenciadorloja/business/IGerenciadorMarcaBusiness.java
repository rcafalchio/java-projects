package br.com.tecway.gerenciadorloja.business;

import br.com.tecway.gerenciadorloja.entity.MarcaProdutoEntity;
import br.com.tecway.gerenciadorloja.exception.BusinessException;
import br.com.tecway.gerenciadorloja.exception.DAOException;

public interface IGerenciadorMarcaBusiness {

	MarcaProdutoEntity atualizarMarca(MarcaProdutoEntity marcaProdutoEntity) throws BusinessException, DAOException;

	void excluirMarca(MarcaProdutoEntity marcaProdutoEntity) throws BusinessException, DAOException;
}
