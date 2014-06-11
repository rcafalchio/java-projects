package br.com.tecway.gerenciadorloja.fx.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import br.com.tecway.gerenciadorloja.business.GerenciadorMarcaBusiness;
import br.com.tecway.gerenciadorloja.business.IGerenciadorMarcaBusiness;
import br.com.tecway.gerenciadorloja.constants.ConstantesMensagens;
import br.com.tecway.gerenciadorloja.dao.MarcaProdutoDAO;
import br.com.tecway.gerenciadorloja.dao.MarcaProdutoDAOImpl;
import br.com.tecway.gerenciadorloja.entity.MarcaProdutoEntity;
import br.com.tecway.gerenciadorloja.exception.BusinessException;
import br.com.tecway.gerenciadorloja.exception.ControllerException;
import br.com.tecway.gerenciadorloja.fx.components.StageAlert;
import br.com.tecway.gerenciadorloja.fx.components.StageAlert.Severity;
import br.com.tecway.gerenciadorloja.utils.AppConstants;
import br.com.tecway.gerenciadorloja.utils.DAOUtils;
import br.com.tecway.gerenciadorloja.utils.TelaUtilitarios;

public class CadastroMarcaController extends PrincipalController {

	private static final Logger LOGGER = LogManager.getLogger(CadastroMarcaController.class);

	@FXML
	private TextField textNomeMarca;

	@FXML
	private TableView<MarcaProdutoEntity> marcaTableView;

	@FXML
	private HBox botoesHBox;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		marcaTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(final MouseEvent event) {

				final MarcaProdutoEntity marcaProdutoEntity = marcaTableView.getSelectionModel().getSelectedItem();
				if (marcaProdutoEntity != null) {
					tratarOnClick();
				}

			}


		});
	}

	@Override
	public void customInitialize() throws ApplicationException {
		// TODO Auto-generated method stub

	}

	/**
	 * Adiciona o produto com o ENTER
	 * 
	 * @param event
	 */
	public void adicionarProduto(final KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER)) {
			this.cadastrar();
		}
	}

	private void tratarOnClick() {

		final MarcaProdutoEntity marcaProdutoEntity = marcaTableView.getSelectionModel().getSelectedItem();
		textNomeMarca.setText(marcaProdutoEntity.getMarca());

		// Botão excluir
		final Button buttonExcluir = new Button("Excluir");

		buttonExcluir.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				excluir(marcaProdutoEntity);

			}

		});

		// Botão Atualizar
		final Button buttonAtualizar = new Button("Atualizar");

		buttonAtualizar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				atualizar(marcaProdutoEntity);

			}

		});

		botoesHBox.getChildren().clear();
		botoesHBox.getChildren().add(buttonAtualizar);
		final Region region = new Region();
		region.setPrefWidth(20);
		botoesHBox.getChildren().add(region);
		botoesHBox.getChildren().add(buttonExcluir);

	}

	private void atualizar(MarcaProdutoEntity marcaProdutoEntity) {
		try {

			final List<String> listaMensagens = validarDadosMarca();
			// Se não encontrou erros atualiza a marca
			if (listaMensagens.isEmpty()) {
				final StageAlert alert = new StageAlert(AppConstants.AVISO, "Deseja alterar a marca selecionada?",
						Severity.WARN, Boolean.TRUE, AppConstants.SIM, AppConstants.NAO);
				final int response = alert.showAndWaitResponse();

				if (response == 0) {

					marcaProdutoEntity.setMarca(textNomeMarca.getText());
					final IGerenciadorMarcaBusiness gerenciadorMarcaBusiness = new GerenciadorMarcaBusiness();
					gerenciadorMarcaBusiness.atualizarMarca(marcaProdutoEntity);
					final StageAlert alertS = new StageAlert(AppConstants.AVISO,
							ConstantesMensagens.MENSAGEM_GENERICA_SUCESSO, Severity.INFO, Boolean.TRUE,
							AppConstants.BOTAO_OK);
					alertS.show();
					super.carregarTelaCadastroMarca(null);

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

	private void excluir(MarcaProdutoEntity marcaProdutoEntity) {
		try {

			final StageAlert alert = new StageAlert(AppConstants.AVISO, "Deseja excluir a marca selecionada?",
					Severity.WARN, Boolean.TRUE, AppConstants.SIM, AppConstants.NAO);
			final int response = alert.showAndWaitResponse();

			if (response == 0) {

				final IGerenciadorMarcaBusiness gerenciadorMarcaBusiness = new GerenciadorMarcaBusiness();
				gerenciadorMarcaBusiness.excluirMarca(marcaProdutoEntity);
				final StageAlert alertS = new StageAlert(AppConstants.AVISO,
						ConstantesMensagens.MENSAGEM_GENERICA_SUCESSO, Severity.INFO, Boolean.TRUE,
						AppConstants.BOTAO_OK);
				alertS.show();
				super.carregarTelaCadastroMarca(null);

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

	public void cadastrar() {

		try {

			LOGGER.info("Cadastrando a marca!");
			final List<String> listaMensagens = validarDadosMarca();

			if (listaMensagens.isEmpty()) {

				final MarcaProdutoEntity marcaProduto = criarMarcaProduto();
				final StageAlert alert = new StageAlert(AppConstants.AVISO, "Confirma o cadastro da marca?",
						Severity.WARN, Boolean.TRUE, AppConstants.SIM, AppConstants.NAO);
				final int response = alert.showAndWaitResponse();

				if (response == 0) {
					// Grava o usuário
					final MarcaProdutoDAO marcaProdutoDAO = new MarcaProdutoDAOImpl(DAOUtils.getEntityManager());
					marcaProdutoDAO.save(marcaProduto);
					final StageAlert alertS = new StageAlert(AppConstants.AVISO,
							ConstantesMensagens.MENSAGEM_GENERICA_SUCESSO, Severity.INFO, Boolean.TRUE,
							AppConstants.BOTAO_OK);
					alertS.show();
				}

				super.carregarTelaCadastroMarca(null);

			} else {

				CadastroHelper.gerarMensagensCadastro(listaMensagens);

			}
		} catch (Exception e) {
			ControllerException.registrarErro(LOGGER, e);
		}


	}


	/**
	 * Valida os dados inseridos na tela
	 * 
	 * @param marcaProduto
	 * @return
	 */
	private List<String> validarDadosMarca() {

		final List<String> lista = new ArrayList<String>();

		if ("".equals(textNomeMarca.getText())) {

			lista.add(ConstantesMensagens.CAMPO_NOME + ConstantesMensagens.MENSAGEM_CAMPO_NAO_PREENCHIDO);

		}

		return lista;
	}

	/**
	 * Busca os dados do form e popular o objeto MarcaProduto
	 * 
	 * @return
	 */
	private MarcaProdutoEntity criarMarcaProduto() {

		final MarcaProdutoEntity marcaProduto = new MarcaProdutoEntity();
		marcaProduto.setMarca(TelaUtilitarios.recuperarValorTextField(textNomeMarca).toUpperCase());
		return marcaProduto;

	}

}
