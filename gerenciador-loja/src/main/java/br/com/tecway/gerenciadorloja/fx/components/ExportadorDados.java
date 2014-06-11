package br.com.tecway.gerenciadorloja.fx.components;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import br.com.tecway.gerenciadorloja.common.DadosArquivoVO;
import br.com.tecway.gerenciadorloja.constants.TipoArquivoEnum;
import br.com.tecway.gerenciadorloja.exception.ExportadorDadosException;
import br.com.tecway.gerenciadorloja.utils.AppConstants;

/**
 * 
 * @author Ricardo Cafalchio
 * @since 14/01/2014
 */
public class ExportadorDados {

	public static void exportarDados(final DadosArquivoVO dadosArquivoVO) throws ExportadorDadosException {
		try {
			validarEntrada(dadosArquivoVO);
			if (dadosArquivoVO.getTipoArquivoEnum().equals(TipoArquivoEnum.EXCEL)) {
				exportarExcel(dadosArquivoVO);
			} else {
				exportarTXT(dadosArquivoVO);
			}
		} catch (FileNotFoundException e) {
			throw new ExportadorDadosException("Caminho inválido", e);
		} catch (IOException e) {
			throw new ExportadorDadosException(e);
		}
	}

	private static void exportarTXT(final DadosArquivoVO dadosArquivoVO) throws FileNotFoundException, IOException {
		final StringBuffer buffer = new StringBuffer("");

		if (dadosArquivoVO.getTitulosColunas() != null) {
			for (int i = 0; i < dadosArquivoVO.getTitulosColunas().length; i++) {
				buffer.append(dadosArquivoVO.getTitulosColunas()[i]);
				if (i == dadosArquivoVO.getTitulosColunas().length - 1) {
					buffer.append(AppConstants.NEXT_LINE);
				} else {
					buffer.append(AppConstants.PIPE_SEPARATOR);
				}
			}
		}

		if (dadosArquivoVO.getDados() != null) {
			for (int i = 0; i < dadosArquivoVO.getDados().length; i++) {
				if (dadosArquivoVO.getDados()[i] == null) {
					continue;
				}

				for (int j = 0; j < dadosArquivoVO.getDados()[i].length; j++) {
					buffer.append(dadosArquivoVO.getDados()[i][j]);
					if (!(j == dadosArquivoVO.getDados()[i].length - 1)) {
						buffer.append(AppConstants.PIPE_SEPARATOR);
					}
				}
				// Pula linha
				buffer.append(AppConstants.NEXT_LINE);
			}
		}

		// Grava o arquivo
		FileOutputStream out = new FileOutputStream(dadosArquivoVO.getCaminhoArquivo());
		out.write(buffer.toString().getBytes());
		out.flush();
		out.close();

	}

	private static void exportarExcel(DadosArquivoVO dadosArquivoVO) throws FileNotFoundException, IOException {
		final Workbook workbook = new HSSFWorkbook();
		final Sheet sheet = workbook.createSheet(dadosArquivoVO.getTitulo());
		montarDados(dadosArquivoVO, sheet, workbook);
		sheet.setAutobreaks(true);
		// Grava o arquivo
		FileOutputStream out = new FileOutputStream(dadosArquivoVO.getCaminhoArquivo());
		workbook.write(out);
		out.close();
	}

	private static void validarEntrada(DadosArquivoVO dadosArquivoVO) {
		// Ajusta o nome do arquivo
		if (dadosArquivoVO.getCaminhoArquivo().endsWith("/") || dadosArquivoVO.getCaminhoArquivo().endsWith("\\")) {
			if (dadosArquivoVO.getTipoArquivoEnum().equals(TipoArquivoEnum.EXCEL)) {
				dadosArquivoVO.setCaminhoArquivo(dadosArquivoVO.getCaminhoArquivo() + "planilha.xls");
			} else {
				dadosArquivoVO.setCaminhoArquivo(dadosArquivoVO.getCaminhoArquivo() + "planilha.txt");
			}
		}
		// Verifica a extenção
		if (dadosArquivoVO.getTipoArquivoEnum().equals(TipoArquivoEnum.EXCEL)) {
			if (dadosArquivoVO.getCaminhoArquivo().indexOf(".xls") < 0) {
				dadosArquivoVO.setCaminhoArquivo(dadosArquivoVO.getCaminhoArquivo().concat(".xls"));
			}
		} else {
			if (dadosArquivoVO.getCaminhoArquivo().indexOf(".txt") < 0) {
				dadosArquivoVO.setCaminhoArquivo(dadosArquivoVO.getCaminhoArquivo().concat(".txt"));
			}
		}

	}

	private static void montarDados(DadosArquivoVO dadosArquivoVO, Sheet sheet, Workbook workbook) {
		final Map<String, CellStyle> styles = createStyles(workbook);
		// CABEÇALHO
		final Row headerRow = sheet.createRow(0);
		for (int i = 0; i < dadosArquivoVO.getTitulosColunas().length; i++) {
			final Cell cell = headerRow.createCell(i);
			cell.setCellValue(dadosArquivoVO.getTitulosColunas()[i]);
			cell.setCellStyle(styles.get("header"));
		}

		Row row;
		Cell cell;
		int rownum = 1;
		String[][] data = dadosArquivoVO.getDados();
		for (int i = 0; i < data.length; i++, rownum++) {
			row = sheet.createRow(rownum);
			
			if (data[i] == null) {
				continue;
			}

			for (int j = 0; j < data[i].length; j++) {
				cell = row.createCell(j);
				cell.setCellValue(data[i][j]);
			}
		}
	}

//	public static void main(String[] args) throws ExportadorDadosException {
//		String[] titulosColunas = { "COLUNA 1", "COLUNA 2", "COLUNA 3", "COLUNA 4" };
//		String[][] dados = { { "LINHA 1", "LINHA 1", "LINHA 1", "LINHA 1" },
//				{ "LINHA 2", "LINHA 2", "LINHA 2", "LINHA 2" } };
//		final DadosArquivoVO dadosArquivoVO = new DadosArquivoVO();
//		dadosArquivoVO.setTitulo("TESTE TITULO");
//		dadosArquivoVO.setTipoArquivoEnum(TipoArquivoEnum.EXCEL);
//		dadosArquivoVO.setCaminhoArquivo("D:\\teste\\");
//		dadosArquivoVO.setDados(dados);
//		dadosArquivoVO.setTitulosColunas(titulosColunas);
//		ExportadorDados.exportarDados(dadosArquivoVO);
//	}

	/**
	 * create a library of cell styles
	 */
	private static Map<String, CellStyle> createStyles(Workbook wb) {
		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
		DataFormat df = wb.createDataFormat();

		CellStyle style;
		Font headerFont = wb.createFont();
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(headerFont);
		styles.put("header", style);

		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(headerFont);
		style.setDataFormat(df.getFormat("dd/MM/yyyy"));
		styles.put("header_date", style);

		Font font1 = wb.createFont();
		font1.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setFont(font1);
		styles.put("cell_b", style);

		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setFont(font1);
		styles.put("cell_b_centered", style);

		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_RIGHT);
		style.setFont(font1);
		style.setDataFormat(df.getFormat("d-mmm"));
		styles.put("cell_b_date", style);

		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_RIGHT);
		style.setFont(font1);
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setDataFormat(df.getFormat("d-mmm"));
		styles.put("cell_g", style);

		Font font2 = wb.createFont();
		font2.setColor(IndexedColors.BLUE.getIndex());
		font2.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setFont(font2);
		styles.put("cell_bb", style);

		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_RIGHT);
		style.setFont(font1);
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setDataFormat(df.getFormat("d-mmm"));
		styles.put("cell_bg", style);

		Font font3 = wb.createFont();
		font3.setFontHeightInPoints((short) 14);
		font3.setColor(IndexedColors.DARK_BLUE.getIndex());
		font3.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setFont(font3);
		style.setWrapText(true);
		styles.put("cell_h", style);

		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setWrapText(true);
		styles.put("cell_normal", style);

		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setWrapText(true);
		styles.put("cell_normal_centered", style);

		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_RIGHT);
		style.setWrapText(true);
		style.setDataFormat(df.getFormat("d-mmm"));
		styles.put("cell_normal_date", style);

		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setIndention((short) 1);
		style.setWrapText(true);
		styles.put("cell_indented", style);

		style = createBorderedStyle(wb);
		style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		styles.put("cell_blue", style);

		return styles;
	}

	private static CellStyle createBorderedStyle(Workbook wb) {
		CellStyle style = wb.createCellStyle();
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		return style;
	}
}
