package components;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DateFormatter;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Properties;

public class StatiscialContent {
	private JPanel statiscialContentPanel;

	private JPanel headerAndDatePickerPanel;
	private JPanel tableNamePanel;
	private JLabel tableName;
	private JLabel from;
	private JDatePicker startDate;
	private JLabel to;
	private JDatePicker endDate;
	private final String datePattern = "dd/MM/yyyy";
	private JButton btnDisplay;

	private JPanel detailTablePanel;
	private JTable detailTable;
	private JScrollPane scrollPane;
	private DefaultTableModel defaultTableModel;
	private String[] columnTitles = { "Số thứ tự", "Từ", "Số lần tra cứu", "Loại tra cứu", "Lựa chọn" };

	public Properties setValuei18nStrings() {
		Properties p = new Properties();
		p.setProperty("text.today", "Today");
		p.setProperty("text.month", "Month");
		p.setProperty("text.year", "Year");
		p.setProperty("text.clear", "Clear");
		p.setProperty("button.today", "Today");
		p.setProperty("button.clear", "Clear");
		p.setProperty("format.today", datePattern);
		p.setProperty("format.month", "MM");
		p.setProperty("format.year", "yyyy");
		p.setProperty("format.clear", "");
		return p;
	}

	public StatiscialContent() {
		statiscialContentPanel = new JPanel(new BorderLayout());
		statiscialContentPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));

		headerAndDatePickerPanel = new JPanel(new BorderLayout());
		tableNamePanel = new JPanel(new FlowLayout());
		tableName = new JLabel("THỐNG KÊ TRA CỨU");
		tableName.setFont(new Font("Arial", Font.BOLD, 20));

		from = new JLabel("Từ: ");
		to = new JLabel("Đến: ");

		DateFormatter dateFormatter = new DateFormatter(new SimpleDateFormat(datePattern));
		UtilDateModel model = new UtilDateModel();
		startDate = new JDatePickerImpl(new JDatePanelImpl(model, setValuei18nStrings()), dateFormatter);
		endDate = new JDatePickerImpl(new JDatePanelImpl(model, setValuei18nStrings()), dateFormatter);
		btnDisplay = new JButton("Hiển thị");
		btnDisplay.setBackground(Color.getHSBColor(120f / 360f, 0.5f, 0.8f));

		String[][] testData = {
				{ "1", "Anh", "9", "abc", "" },
				{ "2", "Yêu", "8", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
				{ "3", "Em", "7", "abc", "" },
		};
		detailTablePanel = new JPanel(new FlowLayout());
		detailTable = new JTable(testData, columnTitles);
		detailTable.setSize(100, 20);
		scrollPane = new JScrollPane(detailTable);
		// defaultTableModel = new DefaultTableModel(columnTitles, 0);
		// detailTable.setModel(defaultTableModel);
	}

	public JPanel getStatiscialContentPanel() {
		tableNamePanel.add(tableName);
		headerAndDatePickerPanel.add(tableNamePanel, BorderLayout.NORTH);

		JPanel datePickerPanel = new JPanel(new FlowLayout());
		datePickerPanel.add(from);
		datePickerPanel.add((JDatePickerImpl) startDate);
		datePickerPanel.add(to);
		datePickerPanel.add((JDatePickerImpl) endDate);
		JPanel btnDisplayPanel = new JPanel(new FlowLayout());
		btnDisplayPanel.add(btnDisplay);
		JPanel datePickerAndBtnPanel = new JPanel(new GridLayout(1, 2));
		datePickerAndBtnPanel.add(datePickerPanel);
		datePickerAndBtnPanel.add(btnDisplayPanel);

		headerAndDatePickerPanel.add(datePickerAndBtnPanel, BorderLayout.SOUTH);
		statiscialContentPanel.add(headerAndDatePickerPanel, BorderLayout.NORTH);

		detailTablePanel.add(scrollPane);
		statiscialContentPanel.add(detailTablePanel, BorderLayout.CENTER);

		registerListenerHandlers();
		return statiscialContentPanel;
	}

	private void registerListenerHandlers() {
		btnDisplay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
	}
}