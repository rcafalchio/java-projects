package br.com.tecway.gerenciadorloja.utils;

import java.util.List;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import br.com.tecway.gerenciadorloja.common.VendaVO;
import br.com.tecway.gerenciadorloja.constants.TypeNumberEnum;
import br.com.tecway.gerenciadorloja.exception.ConverterException;
import br.com.tecway.gerenciadorloja.fx.components.NumberTextField;

public class VendaTelaHelper {

	public static void montarVendaTableView(TableView<VendaVO> vendaTableView, List<VendaVO> vendas, Boolean agrupada)
			throws ConverterException {

		final TableColumn<VendaVO, String> columnData = new TableColumn<VendaVO, String>("Data");
		columnData.setCellValueFactory(new Callback<CellDataFeatures<VendaVO, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(final CellDataFeatures<VendaVO, String> p) {
				return new ReadOnlyObjectWrapper(DataUtils.deUtilDateParaString(p.getValue().getDataVenda(),
						DataUtils.FORMATO_DATA_PADRAO));
			}
		});
		columnData.setPrefWidth(150.0);

		// Cria a coluna código de barras
		final TableColumn<VendaVO, String> columnVendedor = new TableColumn<VendaVO, String>("Vendedor");
		columnVendedor.setCellValueFactory(new Callback<CellDataFeatures<VendaVO, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(final CellDataFeatures<VendaVO, String> p) {
				return new ReadOnlyObjectWrapper(p.getValue().getVendedor().getNome());
			}
		});

		columnVendedor.setPrefWidth(150.0);


		// Cria a coluna Nome
		final TableColumn<VendaVO, String> columnValorBruto = new TableColumn<VendaVO, String>("Valor Bruto da Venda");
		columnValorBruto
				.setCellValueFactory(new Callback<CellDataFeatures<VendaVO, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(final CellDataFeatures<VendaVO, String> p) {
						return new ReadOnlyObjectWrapper(NumberTextField.aplicarMascara(TypeNumberEnum.CURRENCY, p
								.getValue().getValorBruto().toString()));
					}
				});

		columnValorBruto.setPrefWidth(300.0);

		// Cria a coluna Quantidade
		final TableColumn<VendaVO, String> columnDesconto = new TableColumn<VendaVO, String>("Desconto");
		columnDesconto.setCellValueFactory(new Callback<CellDataFeatures<VendaVO, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(final CellDataFeatures<VendaVO, String> p) {
				return new ReadOnlyObjectWrapper(NumberTextField.aplicarMascara(TypeNumberEnum.PERCENTAGE, p.getValue()
						.getPercentualDesconto().toString()));
			}
		});

		// Cria a coluna Nome
		final TableColumn<VendaVO, String> columnValorLiquido = new TableColumn<VendaVO, String>(
				"Valor Liquido da Venda");
		columnValorLiquido
				.setCellValueFactory(new Callback<CellDataFeatures<VendaVO, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(final CellDataFeatures<VendaVO, String> p) {
						return new ReadOnlyObjectWrapper(NumberTextField.aplicarMascara(TypeNumberEnum.CURRENCY, p
								.getValue().getValorLiquido().toString()));
					}
				});

		columnValorLiquido.setPrefWidth(300.0);

		if (agrupada) {
			vendaTableView.getColumns().addAll(columnData, columnValorBruto, columnValorLiquido);
		} else {
			vendaTableView.getColumns().addAll(columnData, columnVendedor, columnValorBruto, columnDesconto,
					columnValorLiquido);
		}

		for (VendaVO vendaVO : vendas) {
			vendaTableView.getItems().add(vendaVO);
		}

	}
}
