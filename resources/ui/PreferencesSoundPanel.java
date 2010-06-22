import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
/*
 * Created by JFormDesigner on Thu Apr 22 20:01:59 AKDT 2010
 */



/**
 * @author Alvin Alexander
 */
public class PreferencesSoundPanel extends JPanel {
	public PreferencesSoundPanel() {
		initComponents();
	}

	public JLabel getHeaderLabel() {
		return headerLabel;
	}

	public JScrollPane getSoundsScrollPane() {
		return soundsScrollPane;
	}

	public JTable getSoundsTable() {
		return soundsTable;
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		headerLabel = new JLabel();
		label1 = new JLabel();
		soundsScrollPane = new JScrollPane();
		soundsTable = new JTable();
		CellConstraints cc = new CellConstraints();

		//======== this ========
		setLayout(new FormLayout(
			new ColumnSpec[] {
				FormFactory.UNRELATED_GAP_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.PREF_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				new ColumnSpec(ColumnSpec.FILL, Sizes.PREFERRED, FormSpec.DEFAULT_GROW),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.UNRELATED_GAP_COLSPEC
			},
			new RowSpec[] {
				FormFactory.PARAGRAPH_GAP_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.PARAGRAPH_GAP_ROWSPEC,
				new RowSpec(RowSpec.FILL, Sizes.DEFAULT, FormSpec.DEFAULT_GROW),
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.PARAGRAPH_GAP_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				new RowSpec(RowSpec.CENTER, Sizes.DEFAULT, FormSpec.DEFAULT_GROW)
			}));

		//---- headerLabel ----
		headerLabel.setText("Sound Effects");
		headerLabel.setFont(UIManager.getFont("InternalFrame.titleFont"));
		add(headerLabel, cc.xywh(3, 3, 3, 1));

		//---- label1 ----
		label1.setText("Select the sound effects you want to hear for the events shown.");
		label1.setFont(UIManager.getFont("Table.font"));
		add(label1, cc.xywh(3, 5, 3, 1));

		//======== soundsScrollPane ========
		{
			soundsScrollPane.setForeground(Color.white);
			soundsScrollPane.setMinimumSize(new Dimension(450, 300));
			soundsScrollPane.setPreferredSize(new Dimension(454, 300));
			soundsScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

			//---- soundsTable ----
			soundsTable.setModel(new DefaultTableModel(
				new Object[][] {
					{"Sample", true, true, true},
					{"Description 1", null, null, null},
					{"Description 1", null, null, null},
				},
				new String[] {
					"Available Sound Effects", "Startup Sound", "Re-Fill Sound", "Shutdown Sound"
				}
			) {
				Class[] columnTypes = new Class[] {
					Object.class, Boolean.class, Boolean.class, Boolean.class
				};
				@Override
				public Class<?> getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			});
			soundsTable.setMinimumSize(new Dimension(450, 400));
			soundsTable.setPreferredSize(new Dimension(450, 400));
			soundsTable.setGridColor(SystemColor.windowBorder);
			soundsTable.setSelectionBackground(UIManager.getColor("Table.selectionBackground"));
			soundsScrollPane.setViewportView(soundsTable);
		}
		add(soundsScrollPane, cc.xywh(3, 7, 3, 1));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JLabel headerLabel;
	private JLabel label1;
	private JScrollPane soundsScrollPane;
	private JTable soundsTable;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
