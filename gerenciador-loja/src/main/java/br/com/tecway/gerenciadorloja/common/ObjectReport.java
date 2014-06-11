package br.com.tecway.gerenciadorloja.common;


public class ObjectReport {

	// Metodos para pegar os dados para exportar o relatório solicitado;
	private String[] methodsReport;
	private String className;

	public ObjectReport(String[] methodsReport, String className) {
		super();
		this.methodsReport = methodsReport;
		this.className = className;
	}

	public String[] getMethodsReport() {
		return methodsReport;
	}

	public String getClassName() {
		return className;
	}

}
