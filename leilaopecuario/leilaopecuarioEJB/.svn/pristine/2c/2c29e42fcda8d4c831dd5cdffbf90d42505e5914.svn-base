package com.leilaopecuario.persistencia;

import java.util.List;

import javax.ejb.Local;

import com.leilaopecuario.entidades.Lance;
import com.leilaopecuario.exception.PersistenciaException;
import com.leilaopecuario.vo.LanceVO;

@Local
public interface ControladorPersistenciaLanceLocal {

	/**
	 * Recupera o lance atual daquele leilão
	 * 
	 * @param codigo
	 * @return
	 * @throws PersistenciaException
	 */
	public Double recuperaLanceAtual(Integer codigo) throws PersistenciaException;

	/**
	 * Efetua a persistencia do lance do usuário
	 * 
	 * @param lanceVO
	 */
	public void atualizarLance(LanceVO lanceVO) throws PersistenciaException;

	/**
	 * Retorna a lista de lances do usuário
	 * 
	 * @param integer
	 * @return lista de lances do usuário
	 * @throws PersistenciaException 
	 */
	public List<Lance> recuperaLancesPorUsuario(Integer integer) throws PersistenciaException;
	
}
