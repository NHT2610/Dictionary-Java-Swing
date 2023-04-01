package components;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import app.App;
import controllers.MainHandler;
import models.Dictionary;

import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class StatiscialContent {
	private JPanel statiscialContentPanel;

	private JPanel headerAndDatePickerPanel;
	private JPanel tableNamePanel;
	private JLabel tableName;
	private JLabel from;
	private UtilDateModel model1;
	private JDatePickerImpl startDate;
	private JLabel to;
	private UtilDateModel model2;
	private JDatePickerImpl endDate;
	private final String datePattern = "dd/MM/yyyy";
	private JButton btnDisplay;

	private JPanel detailTablePanel;
	private static DefaultTableModel tableModel;
	private JTable detailTable;
	private JScrollPane scrollPane;
	private static final String[] columnTitles = { "STT", "Từ", "Loại tra cứu", "Số lần tra cứu", "Ghi chú" };
	private static ArrayList<StatiscialItem> tableDataStored;

	private JPanel optionPanel;
	private JButton favorite;
	private JButton delete;

	private final Color BUTTON_COLOR = Color.getHSBColor(120f / 360f, 0.5f, 0.8f);
	private final Color FAVORITE_BUTTON_COLOR = Color.getHSBColor(204, 18, 69);
	private final Color DELETE_BUTTON_COLOR = Color.getHSBColor(0, 33, 78);

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

		AbstractFormatter dateFormatter = new AbstractFormatter() {
			@Override
			public Object stringToValue(String text) throws ParseException {
				throw new UnsupportedOperationException("Unimplemented method 'stringToValue'");
			}

			@Override
			public String valueToString(Object value) throws ParseException {
				if (value != null) {
					Calendar calendar = (Calendar) value;
					String strDate = App.DATE_FORMAT.format(calendar.getTime());
					return strDate;
				}
				return "";
			}
		};

		Calendar startDateCalendar = Calendar.getInstance();
		startDateCalendar.set(2023, 2, 29);
		model1 = new UtilDateModel(startDateCalendar.getTime());
		startDate = new JDatePickerImpl(new JDatePanelImpl(model1, setValuei18nStrings()), dateFormatter);

		Calendar endDateCalendar = Calendar.getInstance();
		endDateCalendar.set(
				endDateCalendar.get(Calendar.YEAR),
				endDateCalendar.get(Calendar.MONTH),
				endDateCalendar.get(Calendar.DAY_OF_MONTH));
		model2 = new UtilDateModel(endDateCalendar.getTime());
		endDate = new JDatePickerImpl(new JDatePanelImpl(model2, setValuei18nStrings()), dateFormatter);
		btnDisplay = new JButton("Hiển thị");
		btnDisplay.setBackground(BUTTON_COLOR);

		detailTablePanel = new JPanel(new FlowLayout());
		tableDataStored = new ArrayList<StatiscialItem>();
		String[][] tableData = StatiscialItem.convertArrayListToArray(tableDataStored);
		tableModel = new DefaultTableModel(tableData, columnTitles);
		detailTable = new JTable(tableModel);
		detailTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		// Cài font chữ đậm cho header columns
		JTableHeader tableHeader = detailTable.getTableHeader();
		tableHeader.setFont(tableHeader.getFont().deriveFont(Font.BOLD));

		detailTable.setPreferredScrollableViewportSize(new Dimension(800, 230));
		scrollPane = new JScrollPane(detailTable);
		detailTable.setFillsViewportHeight(true);

		optionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		favorite = new JButton("Yêu thích");
		favorite.setBackground(FAVORITE_BUTTON_COLOR);
		delete = new JButton("Xóa khỏi từ điển");
		delete.setBackground(DELETE_BUTTON_COLOR);

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

		optionPanel.add(favorite);
		optionPanel.add(delete);
		statiscialContentPanel.add(optionPanel, BorderLayout.SOUTH);

		registerListenerHandlers();
		return statiscialContentPanel;
	}

	private void registerListenerHandlers() {
		// Button display statiscial table
		btnDisplay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Lấy ra ngày trong các JDatePicker
				Date startDate = (Date) model1.getValue();
				Date endDate = (Date) model2.getValue();
				// Cập nhật dữ liệu lên giao diện
				updateTable(startDate, endDate);
			}
		});

		// Button favortite
		favorite.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int[] selectedRows = detailTable.getSelectedRows();
				for (int i = selectedRows.length - 1; i >= 0; i--) {
					String word = (String) detailTable.getValueAt(selectedRows[i], 1);
					String lookupType = (String) detailTable.getValueAt(selectedRows[i], 2);
					int result = MainHandler.addAWordToFavoritesList(word, lookupType, 0);
					if (result == 0) {
						JOptionPane.showMessageDialog(
								null,
								"Từ \"" + word + "\" đã tồn tại trong danh sách yêu thích trước đó",
								"Thông báo",
								JOptionPane.INFORMATION_MESSAGE);
					} else if (result == 1) {
						System.out.println("Added the word \"" + word + "\" to favorites list!");
					}
				}
			}
		});

		// Button delete
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int[] selectedRows = detailTable.getSelectedRows();
				for (int i = selectedRows.length - 1; i >= 0; i--) {
					String word = (String) detailTable.getValueAt(selectedRows[i], 1);
					String lookupType = (String) detailTable.getValueAt(selectedRows[i], 2);
					tableModel.removeRow(selectedRows[i]);

					Dictionary dictionary = new Dictionary();
					if (lookupType.equals("Anh->Việt")) {
						dictionary = App.getDictionaryEngViet();
					} else if (lookupType.equals("Việt->Anh")) {
						dictionary = App.getDictionaryVietEng();
					}
					boolean result = MainHandler.removeAWordFromDictionary(dictionary, word, lookupType);
					if (result) {
						System.out.println("Delete the word \"" + word + "\" successfully!");
					} else {
						JOptionPane.showMessageDialog(
								null,
								"Không thể xóa từ \"" + word + "\"\n"
										+ "Từ \"" + word + "\" có thể không tồn tại trong từ điển \nhoặc đã bị xóa trước đó",
								"Lỗi",
								JOptionPane.ERROR_MESSAGE);
					}
				}
				updateTable((Date) model1.getValue(), (Date) model2.getValue());
			}
		});
	}

	public static void updateTable(Date start, Date end) {
		tableDataStored = StatiscialItem.getStatiscialList(start, end);
		String[][] tableData = StatiscialItem.convertArrayListToArray(tableDataStored);
		tableModel.setDataVector(tableData, columnTitles);
	}
}