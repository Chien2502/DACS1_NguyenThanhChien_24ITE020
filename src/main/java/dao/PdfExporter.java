package dao;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import javax.swing.*;
import javax.swing.table.TableModel;

import java.awt.Color;
import java.io.FileOutputStream;

public class PdfExporter {

	public void exportTableToPDF(JTable table, String filePath, String documentType) {
	    try {
	        Document document = new Document(PageSize.A4.rotate());
	        PdfWriter.getInstance(document, new FileOutputStream(filePath));
	        document.open();

	        BaseFont bf = BaseFont.createFont(
	            Thread.currentThread().getContextClassLoader()
	                .getResource("font-times-new-roman/font-times-new-roman.ttf")
	                .toString(),
	            BaseFont.IDENTITY_H,
	            BaseFont.EMBEDDED
	        );
	        Font font = new Font(bf, 12);
	        Font titleFont = new Font(bf, 16, Font.BOLD);

	        // 👉 In dòng đầu tiên là tiêu đề loại tài liệu
	        Paragraph title = new Paragraph("LIST " + documentType.toUpperCase(), titleFont);
	        title.setAlignment(Paragraph.ALIGN_CENTER);
	        document.add(title);

	        document.add(new Paragraph(" ")); // Khoảng trắng

	        PdfPTable pdfTable = new PdfPTable(table.getColumnCount());
	        pdfTable.setWidthPercentage(100);

	        // Thêm tiêu đề cột
	        for (int i = 0; i < table.getColumnCount(); i++) {
	            PdfPCell cell = new PdfPCell(new Phrase(table.getColumnName(i), font));
	            cell.setBackgroundColor(Color.LIGHT_GRAY);
	            pdfTable.addCell(cell);
	        }

	        // Thêm dữ liệu hàng
	        for (int row = 0; row < table.getRowCount(); row++) {
	            for (int col = 0; col < table.getColumnCount(); col++) {
	                Object value = table.getValueAt(row, col);
	                pdfTable.addCell(new Phrase(value != null ? value.toString() : "", font));
	            }
	        }

	        document.add(pdfTable);
	        document.close();

	        JOptionPane.showMessageDialog(null, "Save file sucessfully!");

	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "An error has occurred: " + e.getMessage());
	    }
	}

}
