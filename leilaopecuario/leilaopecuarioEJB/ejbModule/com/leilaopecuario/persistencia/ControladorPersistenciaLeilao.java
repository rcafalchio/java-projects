package com.leilaopecuario.persistencia;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import com.leilaopecuario.entidades.Animal;
import com.leilaopecuario.entidades.InformacoesFotos;
import com.leilaopecuario.entidades.Leilao;
import com.leilaopecuario.entidades.Usuario;
import com.leilaopecuario.entidades.Vacina;
import com.leilaopecuario.exception.PersistenciaException;
import com.leilaopecuario.vo.AnimalVO;
import com.leilaopecuario.vo.LeilaoVO;
import com.leilaopecuario.vo.UsuarioVO;
import com.leilaopecuario.vo.VacinaVO;

@Stateless
public class ControladorPersistenciaLeilao implements ControladorPersistenciaLeilaoLocal {

	private static Logger LOGGER = Logger.getLogger(ControladorPersistenciaLeilao.class);

	@PersistenceContext(name = "leilao")
	private EntityManager entityManager;

	@Override
	public void inserirLeilao(final LeilaoVO leilaoVO) throws PersistenciaException {

		try {
			// final Leilao leilao = new Leilao();
			final Leilao leilao = new Leilao();

			leilao.setTitulo(leilaoVO.getTitulo());
			leilao.setDescricao(leilaoVO.getDescricao());
			leilao.setLanceInicial(leilaoVO.getLanceInicial());
			leilao.setDataCadastro(leilaoVO.getDataCadastro());
			leilao.setDataEHoraFinal(leilaoVO.getDataEHoraFinal());
			final Animal animal = this.populaAnimal(leilaoVO);

			leilao.setAnimal(animal);

			animal.setLeilao(leilao);

			leilao.setUsuario(this.populaUsuario(leilaoVO));

			entityManager.persist(leilao);
			leilaoVO.setCodigo(leilao.getCodigo());

		} catch (Exception e) {
			LOGGER.error("Erro no método inserirLeilao", e);
			throw new PersistenciaException(e);
		}
	}

	private Animal populaAnimal(LeilaoVO leilaoVO) throws InvocationTargetException, IllegalAccessException,
			NoSuchMethodException {

		final AnimalVO animalVO = leilaoVO.getAnimalVO();
		final Animal animal = new Animal();
		PropertyUtils.copyProperties(animal, animalVO);
		animal.setVacinas(new ArrayList<Vacina>());

		// copia Vacinas
		for (VacinaVO vacinaVO : animalVO.getVacinas()) {
			final Vacina vacina = entityManager.find(Vacina.class, vacinaVO.getCodigoVacina());
			PropertyUtils.copyProperties(vacina, vacinaVO);
			animal.getVacinas().add(vacina);
			List<Animal> animais = new ArrayList<Animal>();
			animais.add(animal);
			vacina.setAnimais(animais);
		}
		return animal;
	}

	private Usuario populaUsuario(LeilaoVO leilaoVO) throws PersistenciaException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {

		final UsuarioVO usuarioVO = leilaoVO.getUsuarioVO();
		final Usuario usuario = new Usuario();
		usuario.setCodigo(usuarioVO.getCodigo());

		return usuario;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Leilao> buscarLeiloes() throws PersistenciaException {

		List<Leilao> leiloes = null;

		try {
			final String queryString = "SELECT l FROM Leilao l ";
			Query query = entityManager.createQuery(queryString);
			leiloes = query.getResultList();
		} catch (NoResultException e) {
			LOGGER.info("Não Existem Leilões Cadastrados");
			leiloes = null;
		} catch (Exception e) {
			LOGGER.error("Erro no método buscarLeiloesAtivos", e);
			throw new PersistenciaException(e);
		}

		return leiloes;
	}

	@Override
	public InformacoesFotos obtemCaminhoFoto(Integer codigo) throws PersistenciaException {

		InformacoesFotos caminhosFotos = null;

		try {
			final String queryString = "SELECT c FROM CaminhoFotos c " + "where c.leilao.codigo = :codigo";
			Query query = entityManager.createQuery(queryString);
			query.setParameter("codigo", codigo);
			caminhosFotos = (InformacoesFotos) query.getSingleResult();
		} catch (Exception e) {
			LOGGER.error("Erro no método obtemCaminhoFoto", e);
			throw new PersistenciaException(e);
		}

		return caminhosFotos;
	}

	@Override
	public void atualizarLeilao(LeilaoVO leilaoVO) throws PersistenciaException {

		try {
			Leilao leilao = entityManager.find(Leilao.class, leilaoVO.getCodigo());

			InformacoesFotos caminhoFotos = new InformacoesFotos();
			caminhoFotos.setCaminho(leilaoVO.getCaminhosFotos().getCaminho());
			caminhoFotos.setFotoPrincipal(leilaoVO.getCaminhosFotos().getFotoPrincipal());
			caminhoFotos.setLeilao(leilao);
			leilao.setCaminhoFotos(caminhoFotos);
			entityManager.merge(leilao);
		} catch (Exception e) {
			LOGGER.error("Erro no método atualizarLeilao", e);
			throw new PersistenciaException(e);
		}

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Vacina> obtemTodasVacinas() throws PersistenciaException {

		List<Vacina> lista = null;

		try {
			final String queryString = "SELECT v FROM Vacina v " + "order by v.nomeVacina";
			Query query = entityManager.createQuery(queryString);
			lista = query.getResultList();
		} catch (NoResultException e) {
			lista = null;
		} catch (Exception e) {
			LOGGER.error("Erro no método obtemTodasVacinas", e);
			throw new PersistenciaException(e);
		}
		return lista;
	}

	@Override
	public Vacina obtemVacinaPorNome(String nome) throws PersistenciaException {
		Vacina vacina = null;
		try {
			final String queryString = "SELECT v FROM Vacina v " + "WHERE v.nomeVacina = :nome";
			Query query = entityManager.createQuery(queryString);
			query.setMaxResults(1);
			query.setParameter("nome", nome);
			vacina = (Vacina) query.getSingleResult();
		} catch (NoResultException e) {
			vacina = null;
		} catch (Exception e) {
			LOGGER.error("Erro no método obtemVacinaPorNome", e);
			throw new PersistenciaException(e);
		}
		return vacina;
	}

	@Override
	public Leilao recuperaLeilaoPorCodigo(Integer codigoLeilao) throws PersistenciaException {
		Leilao leilao = null;

		try {
			final String queryString = "SELECT l FROM Leilao l " + " WHERE l.codigo = :codigo";
			Query query = entityManager.createQuery(queryString);
			query.setMaxResults(1);
			query.setParameter("codigo", codigoLeilao);
			leilao = (Leilao) query.getSingleResult();
		} catch (NoResultException e) {
			leilao = null;
		} catch (Exception e) {
			LOGGER.error("Erro no método recuperaLeilaoPorCodigo", e);
			throw new PersistenciaException(e);
		}

		return leilao;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Leilao> obtemLeiloesPorUsuario(Integer codigo) throws PersistenciaException {

		List<Leilao> listaLeilao = null;

		try {
			final String queryString = "SELECT l FROM Leilao l " + " WHERE l.usuario.codigo = :codigo";
			Query query = entityManager.createQuery(queryString);
			query.setParameter("codigo", codigo);
			listaLeilao = query.getResultList();
		} catch (NoResultException e) {
			listaLeilao = null;
		} catch (Exception e) {
			LOGGER.error("Erro no método obtemLeiloesPorUsuario", e);
			throw new PersistenciaException(e);
		}

		return listaLeilao;
	}
}
