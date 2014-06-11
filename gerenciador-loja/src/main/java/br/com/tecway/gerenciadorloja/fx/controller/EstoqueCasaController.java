package br.com.tecway.gerenciadorloja.fx.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import br.com.tecway.gerenciadorloja.business.GerenciadorEstoqueCasa;
import br.com.tecway.gerenciadorloja.business.IGerenciadorEstoqueCasa;
import br.com.tecway.gerenciadorloja.common.EstoqueTableVO;
import br.com.tecway.gerenciadorloja.constants.ConstantesMensagens;
import br.com.tecway.gerenciadorloja.exception.BusinessException;
import br.com.tecway.gerenciadorloja.exception.ControllerException;
import br.com.tecway.gerenciadorloja.fx.components.StageAlert;
import br.com.tecway.gerenciadorloja.fx.components.StageAlert.Severity;
import br.com.tecway.gerenciadorloja.utils.AppConstants;
import br.com.tecway.gerenciadorloja.utils.TelaUtilitarios;

public class EstoqueCasaController extends PrincipalController {

	private static final Logger LOGGER = LogManager.getLogger(EstoqueCasaController.class);

	@FXML
	private TextField quantidadeText;

	@FXML
	private TableView<EstoqueTableVO> estoqueTableView;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	@Override
	public void customInitialize() throws ApplicationException {

	}

	public void adicionar() {

		try {

			final List<String> listaMensagens = validarAtualizacao();
			// Se não encontrou erros atualiza a marca
			if (listaMensagens.isEmpty()) {
				final IGerenciadorEstoqueCasa gerenciadorEstoqueCasa = new GerenciadorEstoqueCasa();
				final StageAlert alert = new StageAlert(AppConstants.AVISO, "Deseja adiconar "
						+ TelaUtilitarios.recuperarValorTextField(quantidadeText) + " produtos ("
						+ estoqueTableView.getSelectionModel().getSelectedItem().getProdutoTableVO().getNome() + ") ?",
						Severity.WARN, Boolean.TRUE, AppConstants.SIM, AppConstants.NAO);
				final int response = alert.showAndWaitResponse();

				if (response == 0) {
					gerenciadorEstoqueCasa.adicionarEstoque(estoqueTableView.getSelectionModel().getSelectedItem(),
							TelaUtilitarios.recuperarValorIntegerTextField(quantidadeText));

					final StageAlert alertS = new StageAlert(AppConstants.AVISO,
							ConstantesMensagens.MENSAGEM_GENERICA_SUCESSO, Severity.INFO, Boolean.TRUE,
							AppConstants.BOTAO_OK);
					alertS.show();
					super.carregarTelaEstoqueCasa(null);
				}
			} else {
				CadastroHelper.gerarMensagensCadastro(listaMensagens);
			}
		} catch (BusinessException e) {
			final StageAlert alert = new StageAlert(AppConstants.AVISO, e.getMensagemNegocio(), Severity.WARN,
					Boolean.TRUE, AppConstants.BOTAO_OK);
			alert.show();
			LOGGER.error(e.getMensagemNegocio(), e);
		} catch (Exception e) {
			ControllerException.registrarErro(LOGGER, e);
		}
	}

	public void remover() {

		try {

			final List<String> listaMensagens = validarAtualizacao();

			if (TelaUtilitarios.recuperarValorIntegerTextField(quantidadeText) > estoqueTableView.getSelectionModel()
					.getSelectedItem().getQuantidade()) {
				throw new BusinessException("A quantidade digitada é maior do que o número de produtos no estoque!");
			}
			// Se não encontrou erros atualiza a marca
			if (listaMensagens.isEmpty()) {

				final IGerenciadorEstoqueCasa GerenciadorEstoqueCasa = new GerenciadorEstoqueCasa();

				final StageAlert alert = new StageAlert(AppConstants.AVISO, "Deseja remover "
						+ TelaUtilitarios.recuperarValorTextField(quantidadeText) + " produtos ("
						+ estoqueTableView.getSelectionModel().getSelectedItem().getProdutoTableVO().getNome() + ") ?",
						Severity.WARN, Boolean.TRUE, AppConstants.SIM, AppConstants.NAO);
				final int response = alert.showAndWaitResponse();

				if (response == 0) {
					GerenciadorEstoqueCasa.removerEstoque(estoqueTableView.getSelectionModel().getSelectedItem(),
							TelaUtilitarios.recuperarValorIntegerTextField(quantidadeText));

					final StageAlert alertS = new StageAlert(AppConstants.AVISO,
							ConstantesMensagens.MENSAGEM_GENERICA_SUCESSO, Severity.INFO, Boolean.TRUE,
							AppConstants.BOTAO_OK);
					alertS.show();
					super.carregarTelaEstoqueCasa(null);

				}

			} else {

				CadastroHelper.gerarMensagensCadastro(listaMensagens);

			}

		} catch (BusinessException e) {

			final StageAlert alert = new StageAlert(AppConstants.AVISO, e.getMensagemNegocio(), Severity.WARN,
					Boolean.TRUE, AppConstants.BOTAO_OK);
			alert.show();
			LOGGER.error(e.getMensagemNegocio(), e);

		} catch (Exception e) {

			ControllerException.registrarErro(LOGGER, e);

		}

	}

	private List<String> validarAtualizacao() {

		final List<String> lista = new ArrayList<String>();

		if (estoqueTableView == null || estoqueTableView.getSelectionModel().getSelectedItem() == null) {
			lista.add("Selecione um produto da lista para cadastrar a unidade!");
		}

		if (TelaUtilitarios.recuperarValorIntegerTextField(quantidadeText) == null) {
			lista.add(ConstantesMensagens.CAMPO_QUANTIDADE + ConstantesMensagens.MENSAGEM_CAMPO_NAO_PREENCHIDO);
		}

		return lista;
	}

}
