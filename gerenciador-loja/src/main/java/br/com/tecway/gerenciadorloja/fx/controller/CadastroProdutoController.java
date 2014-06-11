package br.com.tecway.gerenciadorloja.fx.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import br.com.tecway.gerenciadorloja.business.GerenciadorProduto;
import br.com.tecway.gerenciadorloja.business.IGerenciadorProduto;
import br.com.tecway.gerenciadorloja.common.MarcaComboVO;
import br.com.tecway.gerenciadorloja.common.ProdutoVO;
import br.com.tecway.gerenciadorloja.constants.ConstantesMensagens;
import br.com.tecway.gerenciadorloja.constants.TipoProdutoEnum;
import br.com.tecway.gerenciadorloja.dao.MarcaProdutoDAO;
import br.com.tecway.gerenciadorloja.dao.MarcaProdutoDAOImpl;
import br.com.tecway.gerenciadorloja.entity.MarcaProdutoEntity;
import br.com.tecway.gerenciadorloja.entity.ProdutoEntity;
import br.com.tecway.gerenciadorloja.exception.BusinessException;
import br.com.tecway.gerenciadorloja.exception.ControllerException;
import br.com.tecway.gerenciadorloja.fx.components.NumberTextField;
import br.com.tecway.gerenciadorloja.fx.components.StageAlert;
import br.com.tecway.gerenciadorloja.fx.components.StageAlert.Severity;
import br.com.tecway.gerenciadorloja.utils.AppConstants;
import br.com.tecway.gerenciadorloja.utils.DAOUtils;
import br.com.tecway.gerenciadorloja.utils.TelaUtilitarios;
import br.com.tecway.gerenciadorloja.utils.TextUtils;

/**
 * 
 * @author Ricardo Cafalchio
 * @since 26/11/2013
 */
public class CadastroProdutoController extends PrincipalController {

	private static final Logger LOGGER = LogManager.getLogger(CadastroProdutoController.class);

	@FXML
	private ComboBox<TipoProdutoEnum> comboTipoProduto;
	@FXML
	private ComboBox<MarcaComboVO> comboMarcaProduto;
	@FXML
	private TextField textNome;
	@FXML
	private TextArea textDescricao;
	@FXML
	private TextField textPreco;
	@FXML
	private TextField codigoBarrasText;
	@FXML
	private TableView<ProdutoVO> produtosTableView;
	@FXML
	private HBox hBoxBotoes;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Carrega a lista de marcas
		comboTipoProduto.setItems(FXCollections.observableList(Arrays.asList(TipoProdutoEnum.values())));
		final MarcaProdutoDAO marcaProdutoDAO = new MarcaProdutoDAOImpl(DAOUtils.getEntityManager());
		// Carrega a lista de marcas cadastradas
		final List<MarcaProdutoEntity> listaMarcas = marcaProdutoDAO.findAll();
		comboMarcaProduto.getItems().clear();
		MarcaComboVO marcaComboVO = null;

		for (MarcaProdutoEntity marcaProdutoEntity : listaMarcas) {
			marcaComboVO = new MarcaComboVO();
			marcaComboVO.setCodigo(marcaProdutoEntity.getCodigo());
			marcaComboVO.setNome(marcaProdutoEntity.getMarca());
			comboMarcaProduto.getItems().add(marcaComboVO);
		}

		// Adiciona o envento onclick na lista
		produtosTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(final MouseEvent event) {
				if (produtosTableView.getSelectionModel().getSelectedItem() != null) {
					tratarOnClick();
				}
			}
		});

	}

	@Override
	public void customInitialize() throws ApplicationException {

	}

	private void tratarOnClick() {
		try {
			final ProdutoVO produtoVO = produtosTableView.getSelectionModel().getSelectedItem();

			for (MarcaComboVO marcaComboVO : this.comboMarcaProduto.getItems()) {
				if (marcaComboVO.getCodigo().equals(produtoVO.getMarca().getCodigo())) {
					this.comboMarcaProduto.getSelectionModel().select(marcaComboVO);
					break;
				}
			}
			comboTipoProduto.getSelectionModel().select(
					TipoProdutoEnum.getTipoProdutoEnumPorCodigo(produtoVO.getCodigoTipoProduto()));
			textNome.setText(produtoVO.getNome());
			textDescricao.setText(produtoVO.getDescricao());
			textPreco.setText(produtoVO.getPreco());
			codigoBarrasText.setText(TextUtils.toString(produtoVO.getCodigoBarras()));
			// Botão excluir
			final Button buttonExcluir = new Button("Excluir");
			buttonExcluir.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					final StageAlert alert = new StageAlert(AppConstants.AVISO, "Deseja excluir este produto?",
							Severity.WARN, Boolean.TRUE, AppConstants.SIM, AppConstants.NAO);
					final int response = alert.showAndWaitResponse();
					if (response == 0) {
						excluir(produtoVO);
					}
				}
			});

			// Botão Atualizar
			final Button buttonAtualizar = new Button("Atualizar");
			buttonAtualizar.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					final StageAlert alert = new StageAlert(AppConstants.AVISO, "Deseja atualizar este produto?",
							Severity.WARN, Boolean.TRUE, AppConstants.SIM, AppConstants.NAO);
					final int response = alert.showAndWaitResponse();
					if (response == 0) {
						atualizar(produtoVO);
					}
				}
			});

			// Botão Cadastrar Novo
			final Button buttonCadastrarNovo = new Button("Limpar");
			buttonCadastrarNovo.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					carregarTelaCadastroProduto(null);
				}
			});

			hBoxBotoes.getChildren().clear();
			hBoxBotoes.getChildren().add(buttonExcluir);
			Region region = new Region();
			region.setPrefWidth(20);
			hBoxBotoes.getChildren().add(region);
			hBoxBotoes.getChildren().add(buttonAtualizar);
			region = new Region();
			region.setPrefWidth(20);
			hBoxBotoes.getChildren().add(region);
			hBoxBotoes.getChildren().add(buttonCadastrarNovo);

		} catch (Exception e) {
			ControllerException.registrarErro(LOGGER, e);
		}
	}

	private void excluir(ProdutoVO produtoVO) {
		try {
			final IGerenciadorProduto gerenciadorProduto = new GerenciadorProduto();
			gerenciadorProduto.excluirProduto(produtoVO.getCodigo());
			final StageAlert alertS = new StageAlert(AppConstants.AVISO, ConstantesMensagens.MENSAGEM_GENERICA_SUCESSO,
					Severity.INFO, Boolean.TRUE, AppConstants.BOTAO_OK);
			alertS.show();
			super.carregarTelaCadastroProduto(null);
		} catch (BusinessException e) {
			final StageAlert alert = new StageAlert(AppConstants.AVISO, e.getMensagemNegocio(), Severity.WARN,
					Boolean.TRUE, AppConstants.BOTAO_OK);
			alert.show();
			LOGGER.error(e.getMensagemNegocio(), e);
		} catch (Exception e) {
			ControllerException.registrarErro(LOGGER, e);
		}
	}

	private void atualizar(ProdutoVO produtoVO) {
		try {
			final List<String> listaMensagens = validarDadosProduto();
			if (listaMensagens.isEmpty()) {
				final IGerenciadorProduto gerenciadorProduto = new GerenciadorProduto();
				final ProdutoEntity produtoEntity = criarProduto();
				produtoEntity.setCodigo(produtosTableView.getSelectionModel().getSelectedItem().getCodigo());
				gerenciadorProduto.atualizarProduto(produtoEntity);
				final StageAlert alertS = new StageAlert(AppConstants.AVISO,
						ConstantesMensagens.MENSAGEM_GENERICA_SUCESSO, Severity.INFO, Boolean.TRUE,
						AppConstants.BOTAO_OK);
				alertS.show();
				super.carregarTelaCadastroProduto(null);
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

	public void cadastrar() throws ControllerException {

		try {
			LOGGER.info("Cadastrando o produto!");
			final List<String> listaMensagens = validarDadosProduto();

			if (listaMensagens.isEmpty()) {
				final StageAlert alert = new StageAlert(AppConstants.AVISO, "Confirma o cadastro do produto?",
						Severity.WARN, Boolean.TRUE, AppConstants.SIM, AppConstants.NAO);
				final int response = alert.showAndWaitResponse();

				if (response == 0) {
					final ProdutoEntity produto = criarProduto();
					// Grava o produto
					final IGerenciadorProduto gerenciadorProduto = new GerenciadorProduto();
					gerenciadorProduto.cadastrarProduto(produto);
					final StageAlert alerts = new StageAlert(AppConstants.AVISO,
							ConstantesMensagens.MENSAGEM_GENERICA_SUCESSO, Severity.INFO, Boolean.TRUE,
							AppConstants.BOTAO_OK);
					alerts.show();
					// Carrega novamente a tela
					super.carregarTelaCadastroProduto(null);
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

	/**
	 * Valida os dados inseridos na tela
	 * 
	 * @param produto
	 * @return
	 */
	private List<String> validarDadosProduto() {
		final List<String> lista = new ArrayList<String>();
		// Valida se digitou a senha corretamente
		// Valida os campos obrigatórios
		if (textNome == null || "".equals(textNome.getText())) {
			lista.add(ConstantesMensagens.CAMPO_NOME + ConstantesMensagens.MENSAGEM_CAMPO_NAO_PREENCHIDO);
		}
		if (textPreco == null || "".equals(textPreco.getText())) {
			lista.add(ConstantesMensagens.CAMPO_PRECO + ConstantesMensagens.MENSAGEM_CAMPO_NAO_PREENCHIDO);
		}
		if (comboMarcaProduto == null || comboMarcaProduto.getValue() == null) {
			lista.add(ConstantesMensagens.CAMPO_MARCA + ConstantesMensagens.MENSAGEM_CAMPO_NAO_PREENCHIDO);
		}
		if (comboTipoProduto == null || comboTipoProduto.getValue() == null) {
			lista.add(ConstantesMensagens.CAMPO_TIPO_PRODUTO + ConstantesMensagens.MENSAGEM_CAMPO_NAO_PREENCHIDO);
		}
		if (codigoBarrasText == null || codigoBarrasText.getText() == null) {
			lista.add(ConstantesMensagens.CAMPO_CODIGO_BARRAS + ConstantesMensagens.MENSAGEM_CAMPO_NAO_PREENCHIDO);
		}

		return lista;
	}

	/**
	 * Busca os dados do form e popular o objeto MarcaProduto
	 * 
	 * @param produtoEntity
	 * 
	 * @return ProdutoEntity
	 */
	private ProdutoEntity criarProduto() {
		final ProdutoEntity produtoEntity = new ProdutoEntity();
		produtoEntity.setDescricao(TelaUtilitarios.recuperarValorTextField(textDescricao));
		final MarcaProdutoDAO marcaProdutoDAO = new MarcaProdutoDAOImpl(DAOUtils.getEntityManager());
		final MarcaProdutoEntity marcaProdutoEntity = marcaProdutoDAO
				.findByPk(comboMarcaProduto.getValue().getCodigo());
		produtoEntity.setMarca(marcaProdutoEntity);
		produtoEntity.setNome(TelaUtilitarios.recuperarValorTextField(textNome));
		produtoEntity.setPreco(NumberTextField.converterCurrencyToDouble(TelaUtilitarios
				.recuperarValorTextField(textPreco)));
		produtoEntity.setCodigoBarras(TelaUtilitarios.recuperarValorLongTextField(codigoBarrasText));

		if (comboTipoProduto != null) {
			produtoEntity.setCodigoTipoProduto(comboTipoProduto.getValue().getCodigo());
		}
		return produtoEntity;
	}

	public TextField getTextNome() {
		return textNome;
	}

	public void setTextNome(TextField textNome) {
		this.textNome = textNome;
	}

	public TextArea getTextDescricao() {
		return textDescricao;
	}

	public void setTextDescricao(TextArea textDescricao) {
		this.textDescricao = textDescricao;
	}

	public TextField getTextPreco() {
		return textPreco;
	}

	public void setTextPreco(TextField textPreco) {
		this.textPreco = textPreco;
	}

	public ComboBox<TipoProdutoEnum> getComboTipoProduto() {
		return comboTipoProduto;
	}

	public void setComboTipoProduto(ComboBox<TipoProdutoEnum> comboTipoProduto) {
		this.comboTipoProduto = comboTipoProduto;
	}

	public HBox gethBoxBotoes() {
		return hBoxBotoes;
	}

	public void sethBoxBotoes(HBox hBoxBotoes) {
		this.hBoxBotoes = hBoxBotoes;
	}


}
