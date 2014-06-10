package com.leilaopecuario.persistencia;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.leilaopecuario.entidades.ChaveLance;
import com.leilaopecuario.entidades.InformacoesLanceLeilao;
import com.leilaopecuario.entidades.Lance;
import com.leilaopecuario.entidades.Leilao;
import com.leilaopecuario.entidades.Usuario;
import com.leilaopecuario.exception.PersistenciaException;
import com.leilaopecuario.vo.LanceVO;

@Stateless
public class ControladorPersistenciaLance implements ControladorPersistenciaLanceLocal {

	private static Logger LOGGER = Logger.getLogger(ControladorPersistenciaLance.class);

	@PersistenceContext(name = "leilao")
	private EntityManager entityManager;

	@Override
	public Double recuperaLanceAtual(Integer codigo) throws PersistenciaException {

		Double retorno = null;

		try {
			final String queryString = "SELECT i.lanceAtual FROM InformacoesLanceLeilao i "
					+ "where i.codigoLeilao = :codigo";
			Query query = entityManager.createQuery(queryString);
			query.setParameter("codigo", codigo);
			retorno = (Double) query.getSingleResult();
		} catch (NoResultException e) {
			retorno = null;
		} catch (Exception e) {
			LOGGER.error("Erro no método buscarLeiloesAtivos", e);
			throw new PersistenciaException(e);
		}
		return retorno;

	}

	@Override
	public void atualizarLance(LanceVO lanceVO) throws PersistenciaException {

		InformacoesLanceLeilao informacoesLanceLeilao = null;

		try {
			final String queryString = "SELECT i FROM InformacoesLanceLeilao i " + "where i.codigoLeilao = :codigo";
			final Query query = entityManager.createQuery(queryString);
			query.setParameter("codigo", lanceVO.getLeilaoVO().getCodigo());
			informacoesLanceLeilao = (InformacoesLanceLeilao) query.getSingleResult();
			// Soma um nos lances efetuados
			informacoesLanceLeilao.setQuantidadeLancesEfetuados(informacoesLanceLeilao.getQuantidadeLancesEfetuados()
					.intValue() + 1);
			// Atualiza o lance vencedor até o momento
			informacoesLanceLeilao.setLanceAtual(lanceVO.getLance());
			// Atualiza o usuário que efetuou o lance
			final Usuario usuario = entityManager.find(Usuario.class, lanceVO.getUsuarioVO().getCodigo());
			informacoesLanceLeilao.setUsuario(usuario);
		} catch (NoResultException e) {
			informacoesLanceLeilao = new InformacoesLanceLeilao();
			informacoesLanceLeilao.setCodigoLeilao(lanceVO.getLeilaoVO().getCodigo());
			informacoesLanceLeilao.setQuantidadeLancesEfetuados(1);
			informacoesLanceLeilao.setLanceAtual(lanceVO.getLance());
			final Leilao leilao = entityManager.find(Leilao.class, lanceVO.getLeilaoVO().getCodigo());
			final Usuario usuario = entityManager.find(Usuario.class, lanceVO.getUsuarioVO().getCodigo());
			informacoesLanceLeilao.setLeilao(leilao);
			informacoesLanceLeilao.setUsuario(usuario);
			entityManager.persist(informacoesLanceLeilao);
		} catch (Exception e) {
			LOGGER.error("Erro no método atualizarLance", e);
			throw new PersistenciaException(e);
		} finally {
			try {
				// Registra o lance do usuário para aquele leilão
				ChaveLance chaveLance = new ChaveLance();
				chaveLance.setCodigoLeilao(lanceVO.getLeilaoVO().getCodigo());
				chaveLance.setCodigoUsuario(lanceVO.getUsuarioVO().getCodigo());
				Lance lance = entityManager.find(Lance.class, chaveLance);
				if (lance == null) {
					lance = new Lance();
				}
				lance.setChaveLance(chaveLance);
				lance.setLance(lanceVO.getLance());
				lance.setDataLance(new Date());
				entityManager.merge(lance);
			} catch (Exception e) {
				LOGGER.error("Erro no método atualizarLance", e);
				throw new PersistenciaException(e);
			}

		}

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Lance> recuperaLancesPorUsuario(Integer codigo) throws PersistenciaException {

		List<Lance> retorno = null;

		try {
			final String queryString = "SELECT l FROM Lance l " + "where l.chaveLance.codigoUsuario = :codigo";
			Query query = entityManager.createQuery(queryString);
			query.setParameter("codigo", codigo);
			retorno = query.getResultList();
		} catch (NoResultException e) {
			retorno = null;
		} catch (Exception e) {
			LOGGER.error("Erro no método buscarLeiloesAtivos", e);
			throw new PersistenciaException(e);
		}
		return retorno;

	}

}
