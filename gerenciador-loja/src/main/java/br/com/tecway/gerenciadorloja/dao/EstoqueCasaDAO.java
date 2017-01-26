package br.com.tecway.gerenciadorloja.dao;

import java.util.List;

import br.com.tecway.gerenciadorloja.entity.EstoqueCasaEntity;
import br.com.tecway.gerenciadorloja.exception.DAOException;

public interface EstoqueCasaDAO extends IGenericDAO<EstoqueCasaEntity> {

	List<EstoqueCasaEntity> buscarUnidadesDisponiveis(Integer codigoProduto) throws DAOException;

}
