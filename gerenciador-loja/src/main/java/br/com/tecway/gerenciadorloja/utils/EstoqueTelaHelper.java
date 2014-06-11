package br.com.tecway.gerenciadorloja.utils;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import br.com.tecway.gerenciadorloja.common.EstoqueTableVO;
import br.com.tecway.gerenciadorloja.constants.TipoEstoqueEnum;
import br.com.tecway.gerenciadorloja.dao.ProdutoDAO;
import br.com.tecway.gerenciadorloja.dao.ProdutoDAOImpl;
import br.com.tecway.gerenciadorloja.entity.EstoqueCasaEntity;
import br.com.tecway.gerenciadorloja.entity.EstoqueEntity;
import br.com.tecway.gerenciadorloja.entity.ProdutoEntity;
import br.com.tecway.gerenciadorloja.exception.ConverterException;
import br.com.tecway.gerenciadorloja.exception.TelaException;

public class EstoqueTelaHelper extends MontadorTelaHelper {

	private TipoEstoqueEnum tipoEstoqueEnum;

	public EstoqueTelaHelper(Node nodeRaiz, TipoEstoqueEnum tipoEstoqueEnum) {
		super(nodeRaiz);
		this.tipoEstoqueEnum = tipoEstoqueEnum;
	}

	@Override
	public Node montarTela() throws TelaException {
		final BorderPane borderPaneEstoque = (BorderPane) nodeRaiz;
		return borderPaneEstoque;

	}

	/**
	 * Cria a table view de acordo com o tipo de estoque
	 * 
	 * @param tipoEstoqueEnum
	 * @param estoqueTableView
	 * @return
	 * @throws ConverterException
	 */
	public static void criarTableView(TipoEstoqueEnum tipoEstoqueEnum, TableView<EstoqueTableVO> estoqueTableView)
			throws ConverterException {
		final ProdutoDAO produtoDAO = new ProdutoDAOImpl(DAOUtils.getEntityManager());
		final List<ProdutoEntity> listaProdutos = produtoDAO.findAll();
		final List<EstoqueTableVO> listaEstoqueTableVO = new ArrayList<EstoqueTableVO>();
		EstoqueTableVO estoqueTableVO = null;

		for (ProdutoEntity produtoEntity : listaProdutos) {
			estoqueTableVO = new EstoqueTableVO();
			estoqueTableVO.setProdutoTableVO(ConverterUtils.deProdutoEntityParaProdutoVO(produtoEntity));
			estoqueTableVO.setCodigoBarras(produtoEntity.getCodigoBarras());
			estoqueTableVO.setQuantidade(0);

			if (TipoEstoqueEnum.CASA.equals(tipoEstoqueEnum)) {
				if (produtoEntity.getEstoquesCasa() != null && !produtoEntity.getEstoquesCasa().isEmpty()) {
					for (EstoqueCasaEntity estoqueEntity : produtoEntity.getEstoquesCasa()) {
						estoqueTableVO.somar();
					}
				}
			} else if (TipoEstoqueEnum.LOJA.equals(tipoEstoqueEnum)) {
				if (produtoEntity.getEstoques() != null && !produtoEntity.getEstoques().isEmpty()) {
					for (EstoqueEntity estoqueEntity : produtoEntity.getEstoques()) {
						estoqueTableVO.somar();
					}
				}
			} else {
				if (produtoEntity.getEstoquesCasa() != null && !produtoEntity.getEstoquesCasa().isEmpty()) {
					for (EstoqueCasaEntity estoqueEntity : produtoEntity.getEstoquesCasa()) {
						estoqueTableVO.somar();
					}
				}
				if (produtoEntity.getEstoques() != null && !produtoEntity.getEstoques().isEmpty()) {
					for (EstoqueEntity estoqueEntity : produtoEntity.getEstoques()) {
						estoqueTableVO.somar();
					}
				}
			}


			listaEstoqueTableVO.add(estoqueTableVO);

		}

		final TableColumn<EstoqueTableVO, String> columnMarca = new TableColumn<EstoqueTableVO, String>("Marca");
		columnMarca
				.setCellValueFactory(new Callback<CellDataFeatures<EstoqueTableVO, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(final CellDataFeatures<EstoqueTableVO, String> p) {
						return new ReadOnlyObjectWrapper(p.getValue().getProdutoTableVO().getMarca().getMarca());
					}
				});
		columnMarca.setPrefWidth(150.0);

		// Cria a coluna Nome
		final TableColumn<EstoqueTableVO, String> columnNome = new TableColumn<EstoqueTableVO, String>("Nome");
		columnNome
				.setCellValueFactory(new Callback<CellDataFeatures<EstoqueTableVO, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(final CellDataFeatures<EstoqueTableVO, String> p) {
						return new ReadOnlyObjectWrapper(p.getValue().getProdutoTableVO().getNome());
					}
				});

		columnNome.setPrefWidth(300.0);

		// Cria a coluna Quantidade
		final TableColumn<EstoqueTableVO, String> columnQuantidade = new TableColumn<EstoqueTableVO, String>(
				"Quantidade");
		columnQuantidade
				.setCellValueFactory(new Callback<CellDataFeatures<EstoqueTableVO, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(final CellDataFeatures<EstoqueTableVO, String> p) {
						return new ReadOnlyObjectWrapper(p.getValue().getQuantidade());
					}
				});

		columnQuantidade.setPrefWidth(100.0);

		// Cria a coluna código de barras
		final TableColumn<EstoqueTableVO, String> columnCodigoBarras = new TableColumn<EstoqueTableVO, String>(
				"Código de barras");
		columnCodigoBarras
				.setCellValueFactory(new Callback<CellDataFeatures<EstoqueTableVO, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(final CellDataFeatures<EstoqueTableVO, String> p) {
						return new ReadOnlyObjectWrapper(p.getValue().getCodigoBarras());
					}
				});

		columnCodigoBarras.setPrefWidth(150.0);
		estoqueTableView.getColumns().addAll(columnMarca, columnNome, columnQuantidade, columnCodigoBarras);

		for (EstoqueTableVO estoqueTableVO1 : listaEstoqueTableVO) {
			estoqueTableView.getItems().add(estoqueTableVO1);
		}

	}

}
