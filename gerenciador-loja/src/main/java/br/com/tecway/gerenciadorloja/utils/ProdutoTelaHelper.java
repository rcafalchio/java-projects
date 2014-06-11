package br.com.tecway.gerenciadorloja.utils;

import java.util.List;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import br.com.tecway.gerenciadorloja.common.ProdutoVO;
import br.com.tecway.gerenciadorloja.dao.ProdutoDAO;
import br.com.tecway.gerenciadorloja.dao.ProdutoDAOImpl;
import br.com.tecway.gerenciadorloja.entity.ProdutoEntity;
import br.com.tecway.gerenciadorloja.exception.ConverterException;
import br.com.tecway.gerenciadorloja.exception.TelaException;

public class ProdutoTelaHelper extends MontadorTelaHelper {

	public ProdutoTelaHelper(Node nodeRaiz) {
		super(nodeRaiz);
	}

	@Override
	public Node montarTela() throws TelaException {
		final BorderPane borderPaneProdutos = (BorderPane) nodeRaiz;
		try {
			// Carrega a lista de produtos cadastrados
			final ProdutoDAO produtoDAO = new ProdutoDAOImpl(DAOUtils.getEntityManager());
			final List<ProdutoEntity> listaProdutos = produtoDAO.findAll();
			final VBox vBox = (VBox) borderPaneProdutos.getCenter();
			final HBox hBox = (HBox) vBox.getChildren().get(0);
			final TableView<ProdutoVO> tableViewProdutos = (TableView<ProdutoVO>) hBox.getChildren().get(2);
			montarTableViewProdutos(ConverterUtils.deProdutoEntityParaProdutoVO(listaProdutos), tableViewProdutos);
		} catch (Exception e) {
			throw new TelaException(e);
		}
		return borderPaneProdutos;
	}

	public static void montarTableViewProdutos(List<ProdutoVO> listaProdutos,
			TableView<ProdutoVO> produtoEntityTableView) throws ConverterException {
		// Cria a coluna Marca
		final TableColumn<ProdutoVO, String> columnMarca = new TableColumn<ProdutoVO, String>("Marca");
		columnMarca.setCellValueFactory(new Callback<CellDataFeatures<ProdutoVO, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(final CellDataFeatures<ProdutoVO, String> p) {
				return new ReadOnlyObjectWrapper(p.getValue().getMarca().getMarca());
			}
		});
		columnMarca.setPrefWidth(100.0);
		// Cria a coluna Nome do produto
		final TableColumn<ProdutoVO, String> columnNome = new TableColumn<ProdutoVO, String>("Nome do produto");
		columnNome.setCellValueFactory(new Callback<CellDataFeatures<ProdutoVO, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(final CellDataFeatures<ProdutoVO, String> p) {
				return new ReadOnlyObjectWrapper(p.getValue().getNome());
			}
		});
		columnNome.setPrefWidth(200.0);
		// Cria a coluda Preço
		final TableColumn<ProdutoVO, String> columnPreco = new TableColumn<ProdutoVO, String>("Preço");
		columnPreco.setCellValueFactory(new Callback<CellDataFeatures<ProdutoVO, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(final CellDataFeatures<ProdutoVO, String> p) {
				return new ReadOnlyObjectWrapper(p.getValue().getPreco());
			}
		});
		columnPreco.setPrefWidth(70.0);
		// Cria a coluda Preço
		final TableColumn<ProdutoVO, String> columnCodigoBarras = new TableColumn<ProdutoVO, String>("Código de Barras");
		columnCodigoBarras
				.setCellValueFactory(new Callback<CellDataFeatures<ProdutoVO, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(final CellDataFeatures<ProdutoVO, String> p) {
						return new ReadOnlyObjectWrapper(p.getValue().getCodigoBarras());
					}
				});
		columnCodigoBarras.setPrefWidth(130.0);
		produtoEntityTableView.getColumns().addAll(columnMarca, columnNome, columnPreco, columnCodigoBarras);
		produtoEntityTableView.getItems().addAll(listaProdutos);

	}


}
