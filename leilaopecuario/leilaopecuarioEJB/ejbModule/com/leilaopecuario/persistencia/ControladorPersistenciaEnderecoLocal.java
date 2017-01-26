package com.leilaopecuario.persistencia;

import java.util.List;

import javax.ejb.Local;

import com.leilaopecuario.entidades.Estado;

@Local
public interface ControladorPersistenciaEnderecoLocal {

	/**
	 * Retorna a lista de estados
	 * 
	 * @return List<Estado>
	 */
	public List<Estado> recuperaListaEstados();

}
