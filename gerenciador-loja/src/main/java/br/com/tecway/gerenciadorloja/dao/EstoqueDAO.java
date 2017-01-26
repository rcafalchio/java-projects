package br.com.tecway.gerenciadorloja.dao;

import java.util.List;

import br.com.tecway.gerenciadorloja.entity.EstoqueEntity;
import br.com.tecway.gerenciadorloja.exception.DAOException;

public interface EstoqueDAO extends IGenericDAO<EstoqueEntity> {

	List<EstoqueEntity> buscarUnidadesDisponiveis(Integer codigoProduto) throws DAOException;

}
