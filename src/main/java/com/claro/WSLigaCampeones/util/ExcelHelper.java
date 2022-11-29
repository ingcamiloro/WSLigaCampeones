package com.claro.WSLigaCampeones.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.claro.WSLigaCampeones.dto.TblAuxActDetallePedidoDto;
import com.claro.WSLigaCampeones.util.configuracion.Constantes;
import com.claro.WSLigaCampeones.util.configuracion.Propiedades;

public class ExcelHelper {
	
	private static Propiedades prop = Propiedades.getInstance();

	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

	private Workbook workbook;
	private Iterator<Row> rows;
	private Sheet sheet;
	private DataFormatter formatter;
	private int contadorFila;
	CellStyle styleDefaul ;
	Map<String[], CellStyle > map;
	
	ArrayList<CellStyle> listaEstilos;

	public ExcelHelper() {
		formatter = new DataFormatter(java.util.Locale.US);
	}

	public void crearDocEnBlanco() {
		this.workbook = new XSSFWorkbook();
		this.contadorFila = 0;
		styleDefaul = workbook.createCellStyle();
		listaEstilos=new ArrayList<>();
		 map = new HashMap();
	}

	public Sheet crearHoja(String nombreHoja) {
		return this.workbook.createSheet(nombreHoja);
	}
	
	public void crearFila(Sheet hoja, List<String> valores, String[] stilos) {
		this.contadorFila++;
		Row row = hoja.createRow(this.contadorFila);
		int columna = 0;

		for (String s : valores) {
			Cell cell = row.createCell(columna);
			cell.setCellValue(s);
			agregarEstilos(cell, stilos);

			columna++;
		}
	}

	public void crearFilaUsuarios(Sheet hoja, List<String> valores, String[] stilos) {
		this.contadorFila++;
		Row row = hoja.createRow(this.contadorFila);
		int columna = 0;
		int columnaClave = 0;

		for (String s : valores) {
			if(columnaClave!=7) {
			Cell cell = row.createCell(columna);
			cell.setCellValue(s);
			agregarEstilos(cell, stilos);
			columna++;
			}
			columnaClave++;
			
			
		}
	}
	
	public void crearFila(Sheet hoja,Row invalidos, String[] stilos) {
		this.contadorFila++;
		Row row = hoja.createRow(this.contadorFila);
		int columna = 0;
		Iterator<Cell> cellsInRow = invalidos.iterator();
		while (cellsInRow.hasNext() && !Objects.equals(invalidos, 23)) {
			Cell currentCell = cellsInRow.next();
			
			Cell cell = row.createCell(columna);
				cell.setCellValue(currentCell.toString());
	
			agregarEstilos(cell, stilos);
			
			 cell = row.createCell(22);
			cell.setCellValue("Estado Invalido");

		agregarEstilos(cell, stilos);

			columna++;
			
			
		}
	}

	public void agregarEstilos(Cell cell,String[] stilos ) {

		
		if(map.containsKey(stilos)) {
			cell.setCellStyle(map.get(stilos));
		}else {
			CellStyle style = workbook.createCellStyle();// Create style
			for(int i=0;i<stilos.length;i++) {
				
				switch (stilos[i]) {
				case Constantes.CENTRAR:
					style.setVerticalAlignment(VerticalAlignment.CENTER);
					style.setAlignment(HorizontalAlignment.CENTER);
					break;
				case Constantes.APLICAR_BORDES:
					style.setBorderBottom(BorderStyle.MEDIUM);
					style.setBorderTop(BorderStyle.MEDIUM);
					style.setBorderRight(BorderStyle.MEDIUM);
					style.setBorderLeft(BorderStyle.MEDIUM);
					break;
				case Constantes.NEGRITA:
					Font font = workbook.createFont();// Create font
					font.setBold(true);// Make font bold
					style.setFont(font);// set it to bold
					break;
				default:
					break;
				}
				
			}
			map.put(stilos, style);
			cell.setCellStyle(style);

		}



	}

	public void crearFila(Sheet hoja, String[] valores, String[] stilos) {
		this.contadorFila++;
		Row row = hoja.createRow(this.contadorFila);
		for (int columna = 0; columna < valores.length; columna++) {
			Cell cell = row.createCell(columna);
			cell.setCellValue(valores[columna]);
			agregarEstilos(cell, stilos);

		}

	}

	/* verifica si es un archivo excel */
	public static boolean hasExcelFormat(MultipartFile file) {
		if (!TYPE.equals(file.getContentType())) {
			return false;
		}
		return true;
	}

	/*
	 * Se abre el archivo excel, en la hoja indicada y se omiten las filas de la
	 * cabecera
	 */
	public void loadFileExcel(InputStream is, String nameSheet, int skipHeaders) throws IOException {

		this.workbook = new XSSFWorkbook(is);
		this.sheet = workbook.getSheet(nameSheet);
		this.rows = sheet.iterator();
		int rowNumber = 0;

		// skip header
		while (this.rows.hasNext() && rowNumber < skipHeaders) {
			rows.next();
			rowNumber++;
		}

	}

	/* retorna las filas del archivo cargado */
	public Iterator<Row> getRows() {
		return rows;
	}

	/* Cierra el archivo */
	public void closeFileExcel() {
		try {
			this.workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ByteArrayInputStream getFile() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			this.workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Fallo al importar la data a documento excel " + e.getMessage());
		}
		return new ByteArrayInputStream(out.toByteArray());
	}

	/* se setean los campos al dto ; */
	public TblAuxActDetallePedidoDto obtenerfilaArchivo(Row currentRow, Integer limite) {
		int i = 1;
		TblAuxActDetallePedidoDto tblAuxActDetallePedidoDto = new TblAuxActDetallePedidoDto();

		Iterator<Cell> cellsInRow = currentRow.iterator();
		while (cellsInRow.hasNext() && !Objects.equals(i, limite)) {
			Cell currentCell = cellsInRow.next();
			switch (i) {
			case 18:
				tblAuxActDetallePedidoDto
						.setIdProducto(Long.parseLong(formatter.formatCellValue(currentCell).toString().trim()));
				break;
			case 15:
				tblAuxActDetallePedidoDto
						.setIdPedido(Long.parseLong(formatter.formatCellValue(currentCell).toString().trim()));
				break;
			case 19:

				if (formatter.formatCellValue(currentCell).toString().trim().toUpperCase()
						.equals(prop.getPropiedad(Constantes.ESTADO_EFECTIVO).toUpperCase())) {
					tblAuxActDetallePedidoDto
							.setEstado(Long.parseLong(prop.getPropiedad(Constantes.ESTADO_NUMERICO_EFECTIVO)));
				} else if (formatter.formatCellValue(currentCell).toString().trim().toUpperCase()
						.equals(prop.getPropiedad(Constantes.ESTADO_RECHAZADO).trim().toUpperCase())) {
					tblAuxActDetallePedidoDto
							.setEstado(Long.parseLong(prop.getPropiedad(Constantes.ESTADO_NUMERICO_RECHAZADO)));
				} else if (formatter.formatCellValue(currentCell).toString().trim().toUpperCase()
						.equals(prop.getPropiedad(Constantes.ESTADO_PENDIENTE).trim().toUpperCase())) {
					tblAuxActDetallePedidoDto
							.setEstado(Long.parseLong(prop.getPropiedad(Constantes.ESTADO_NUMERICO_PENDIENTE)));
				}

				break;
			case 14:
				tblAuxActDetallePedidoDto.setMotivoRechazo(formatter.formatCellValue(currentCell).toString().trim());
				break;

			}
			i++;
		}
		return tblAuxActDetallePedidoDto;
	}

	public void escribirCelda(Row currentRow, int celda, String texto) {
		currentRow.createCell(celda).setCellValue(texto);
	}

	public String obtenerValorCelda(Row row, int numCol) {
		return row.getCell(numCol).getStringCellValue();
	}

	public int getContadorFila() {
		return contadorFila;
	}

	public void setContadorFila(int contadorFila) {
		this.contadorFila = contadorFila;
	}

	public void combinarCeldas(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {

		sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));

	}

}
