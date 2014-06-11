package br.com.tecway.gerenciadorloja.utils;

import java.util.ArrayList;
import java.util.List;

import br.com.tecway.gerenciadorloja.common.MarcaVO;
import br.com.tecway.gerenciadorloja.common.ProdutoVO;
import br.com.tecway.gerenciadorloja.common.UsuarioVO;
import br.com.tecway.gerenciadorloja.common.VendaVO;
import br.com.tecway.gerenciadorloja.constants.TipoPagamentoEnum;
import br.com.tecway.gerenciadorloja.constants.TypeNumberEnum;
import br.com.tecway.gerenciadorloja.entity.MarcaProdutoEntity;
import br.com.tecway.gerenciadorloja.entity.ProdutoEntity;
import br.com.tecway.gerenciadorloja.entity.UsuarioEntity;
import br.com.tecway.gerenciadorloja.entity.VendaEntity;
import br.com.tecway.gerenciadorloja.exception.ConverterException;
import br.com.tecway.gerenciadorloja.fx.components.NumberTextField;

public final class ConverterUtils {

	public static MarcaVO deMarcaProdutoEntityParaMarcaVO(final MarcaProdutoEntity marcaProdutoEntity)
			throws ConverterException {
		final MarcaVO marcaVO = new MarcaVO();
		try {
			marcaVO.setMarca(marcaProdutoEntity.getMarca());
			marcaVO.setCodigo(marcaProdutoEntity.getCodigo());
		} catch (Exception e) {
			throw new ConverterException(e);
		}
		return marcaVO;
	}

	public static ProdutoVO deProdutoEntityParaProdutoVO(final ProdutoEntity produtoEntity) throws ConverterException {
		final ProdutoVO produtoVO = new ProdutoVO();
		try {
			if (produtoEntity.getMarca() != null) {
				produtoVO.setMarca(ConverterUtils.deMarcaProdutoEntityParaMarcaVO(produtoEntity.getMarca()));
			}
			produtoVO.setCodigo(produtoEntity.getCodigo());
			produtoVO.setCodigoBarras(produtoEntity.getCodigoBarras());
			produtoVO.setCodigoTipoProduto(produtoEntity.getCodigoTipoProduto());
			produtoVO.setDescricao(produtoEntity.getDescricao());
			produtoVO.setNome(produtoEntity.getNome());
			String valor = "0.0";

			if (produtoEntity.getPreco() != null) {
				valor = produtoEntity.getPreco().toString();
			}

			if (produtoEntity.getPreco() != null) {
				produtoVO.setPreco(NumberTextField.aplicarMascara(TypeNumberEnum.CURRENCY, valor));
			}
		} catch (Exception e) {
			throw new ConverterException(e);
		}
		return produtoVO;
	}

	public static List<ProdutoVO> deProdutoEntityParaProdutoVO(final List<ProdutoEntity> produtos)
			throws ConverterException {
		final List<ProdutoVO> lista = new ArrayList<ProdutoVO>();
		try {
			ProdutoVO produtoVO;
			for (ProdutoEntity produtoEntity : produtos) {
				produtoVO = new ProdutoVO();
				if (produtoEntity.getMarca() != null) {
					produtoVO.setMarca(ConverterUtils.deMarcaProdutoEntityParaMarcaVO(produtoEntity.getMarca()));
				}
				produtoVO.setCodigo(produtoEntity.getCodigo());
				produtoVO.setCodigoBarras(produtoEntity.getCodigoBarras());
				produtoVO.setCodigoTipoProduto(produtoEntity.getCodigoTipoProduto());
				produtoVO.setDescricao(produtoEntity.getDescricao());
				produtoVO.setNome(produtoEntity.getNome());
				String valor = "0.0";

				if (produtoEntity.getPreco() != null) {
					valor = produtoEntity.getPreco().toString();
				}

				if (produtoEntity.getPreco() != null) {
					produtoVO.setPreco(NumberTextField.aplicarMascara(TypeNumberEnum.CURRENCY, valor));
				}
				lista.add(produtoVO);
			}
		} catch (Exception e) {
			throw new ConverterException(e);
		}
		return lista;
	}

	public static List<UsuarioEntity> deUsuarioVOParaUsuarioEntity(final List<UsuarioVO> usuarios)
			throws ConverterException {
		final List<UsuarioEntity> lista = new ArrayList<UsuarioEntity>();
		try {
			for (UsuarioVO usuarioVO : usuarios) {
				lista.add(ConverterUtils.deUsuarioVOParaUsuarioEntity(usuarioVO));
			}
		} catch (Exception e) {
			throw new ConverterException(e);
		}
		return lista;
	}

	public static UsuarioEntity deUsuarioVOParaUsuarioEntity(final UsuarioVO usuarioVO) throws ConverterException {
		final UsuarioEntity usuarioEntity = new UsuarioEntity();
		try {
			usuarioEntity.setCodigo(usuarioVO.getCodigo());
			usuarioEntity.setNome(usuarioVO.getNome());
		} catch (Exception e) {
			throw new ConverterException(e);
		}
		return usuarioEntity;
	}

	public static List<UsuarioVO> deUsuarioEntityParaUsuarioVO(final List<UsuarioEntity> usuariosEntity)
			throws ConverterException {
		final List<UsuarioVO> listaUsuarios = new ArrayList<UsuarioVO>();
		try {
			UsuarioVO usuarioVO;
			for (UsuarioEntity usuarioEntity : usuariosEntity) {
				usuarioVO = new UsuarioVO();
				usuarioVO.setCodigo(usuarioEntity.getCodigo());
				usuarioVO.setNome(usuarioEntity.getNome());
				listaUsuarios.add(usuarioVO);
			}
		} catch (Exception e) {
			throw new ConverterException(e);
		}
		return listaUsuarios;
	}

	public static List<VendaVO> deVendaEntityParaVendaVO(List<VendaEntity> vendasEntity) throws ConverterException {
		final List<VendaVO> lista = new ArrayList<VendaVO>();
		VendaVO vendaVO = null;
		for (VendaEntity vendaEntity : vendasEntity) {
			vendaVO = new VendaVO();
			vendaVO.setDataVenda(vendaEntity.getDataVenda());
			vendaVO.setPercentualDesconto(vendaEntity.getPercentualDesconto());
			vendaVO.setProdutos(ConverterUtils.deProdutoEntityParaProdutoVO(vendaEntity.getProdutos()));
			vendaVO.setTipoPagamentoEnum(TipoPagamentoEnum.getPagamento(vendaEntity.getTipoPagamento()));
			vendaVO.setValorBruto(vendaEntity.getValorBruto());
			vendaVO.setValorLiquido(vendaEntity.getValorLiquido());
			final List<UsuarioEntity> usuariosEntitie = new ArrayList<UsuarioEntity>();
			usuariosEntitie.add(vendaEntity.getVendedor());
			final List<UsuarioVO> vendedores = ConverterUtils.deUsuarioEntityParaUsuarioVO(usuariosEntitie);
			vendaVO.setVendedor(vendedores.get(0));
			lista.add(vendaVO);
		}
		return lista;
	}
}
