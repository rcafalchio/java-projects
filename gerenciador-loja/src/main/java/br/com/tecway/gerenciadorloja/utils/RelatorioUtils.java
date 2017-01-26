package br.com.tecway.gerenciadorloja.utils;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import br.com.tecway.gerenciadorloja.common.DadosArquivoVO;
import br.com.tecway.gerenciadorloja.common.ObjectReport;
import br.com.tecway.gerenciadorloja.constants.RelatorioEnum;

/**
 * Classe responsável por tratar os dados de relatórios
 * 
 * @author Ricardo Cafalchio
 * @since 25/01/2014
 */
public class RelatorioUtils {

	private static final List<String> TIPOS_OBJETOS_RELATORIO = new ArrayList<String>();
	static {
		TIPOS_OBJETOS_RELATORIO.add("MarcaVO");
		TIPOS_OBJETOS_RELATORIO.add("EstoqueTableVO");
		TIPOS_OBJETOS_RELATORIO.add("ProdutoVO");
	}

	public static DadosArquivoVO atribuirDadosArquivo(TableView tableView, List<ObjectReport> listaObjetos)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		final DadosArquivoVO dadosArquivoVO = new DadosArquivoVO();
		final String[] titulosColunas = new String[tableView.getColumns().size()];
		final String[][] dados = new String[tableView.getItems().size()][tableView.getColumns().size()];

		for (int i = 0; i < tableView.getColumns().size(); i++) {
			final TableColumn tableColumn = (TableColumn) tableView.getColumns().get(i);
			titulosColunas[i] = tableColumn.getText();
		}
		dadosArquivoVO.setTitulosColunas(titulosColunas);
		Integer i = 0;
		Integer j = 0;

		for (Object object : tableView.getItems()) {
			for (ObjectReport objectReport : listaObjetos) {
				final Object objectSelect = recuperarObject(objectReport, object);
				for (String getMethod : objectReport.getMethodsReport()) {
					j = recuperarValorObjeto(objectSelect, dados, i, j, getMethod);
				}
			}
			j = 0;
			i++;
		}

		dadosArquivoVO.setDados(dados);
		return dadosArquivoVO;
	}

	private static int recuperarValorObjeto(Object object, String[][] dados, Integer i, Integer j, String parametro)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		for (Method method : object.getClass().getMethods()) {
			if (method.getName().startsWith(parametro)) {
				String valor = method.invoke(object, new Object[] {}).toString();
				dados[i][j] = valor;
				j++;
			}
		}
		return j;
	}


	private static Object recuperarObject(ObjectReport objectReport, Object object) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Object objectRetorno = null;

		if (object.getClass().getSimpleName().equals(objectReport.getClassName())) {
			objectRetorno = object;
		} else {
			for (Method method : object.getClass().getMethods()) {
				if (method.getName().startsWith("get")) {
					Object object2 = method.invoke(object, new Object[] {});
					if (object2 != null && TIPOS_OBJETOS_RELATORIO.contains(object2.getClass().getSimpleName())) {
						if (object2.getClass().getSimpleName().equals(objectReport.getClassName())) {
							objectRetorno = object2;
							break;
						} else {
							objectRetorno = RelatorioUtils.recuperarObject(objectReport, object2);
							if (objectRetorno != null) {
								break;
							}
						}
					}
				}
			}
		}
		return objectRetorno;
	}

	private static ObjectReport recuperarObjectReport(List<ObjectReport> listaObjetos, Object object) {
		ObjectReport objectReportRecuperado = null;
		for (ObjectReport objectReport : listaObjetos) {
			if (objectReport.getClassName().equals(object.getClass().getSimpleName())) {
				objectReportRecuperado = objectReport;
			}
		}
		return objectReportRecuperado;
	}

	public static List<ObjectReport> recuperarParametrosRelatorio(final RelatorioEnum relatorioEnum) {
		final List<ObjectReport> lista = new ArrayList<ObjectReport>();

		if (RelatorioEnum.CAIXA.equals(relatorioEnum)) {
			lista.add(criarObjectReport("CaixaTableVO", "getValorData", "getValorDinheiro", "getValorDebito",
					"getValorCredito"));
		} else if (RelatorioEnum.ESTOQUE.equals(relatorioEnum)) {
			lista.add(criarObjectReport("MarcaVO", "getMarca"));
			lista.add(criarObjectReport("ProdutoVO", "getNome"));
			lista.add(criarObjectReport("EstoqueTableVO", "getQuantidade", "getCodigoBarras"));
		}
		return lista;
	}

	private static ObjectReport criarObjectReport(String className, String... methods) {
		return new ObjectReport(methods, className);
	}
}