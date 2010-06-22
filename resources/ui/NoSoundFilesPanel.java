import javax.swing.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
/*
 * Created by JFormDesigner on Fri Apr 23 20:06:10 AKDT 2010
 */



/**
 * @author Alvin Alexander
 */
public class NoSoundFilesPanel extends JPanel {
	public NoSoundFilesPanel() {
		initComponents();
	}

	public JButton getImportILifeSoundsButton() {
		return importILifeSoundsButton;
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		label1 = new JLabel();
		label2 = new JLabel();
		label3 = new JLabel();
		label4 = new JLabel();
		label5 = new JLabel();
		label6 = new JLabel();
		importILifeSoundsButton = new JButton();
		CellConstraints cc = new CellConstraints();

		//======== this ========
		setLayout(new FormLayout(
			new ColumnSpec[] {
				FormFactory.UNRELATED_GAP_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				new ColumnSpec(ColumnSpec.LEFT, Sizes.DEFAULT, FormSpec.NO_GROW),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				new ColumnSpec(ColumnSpec.FILL, Sizes.PREFERRED, FormSpec.DEFAULT_GROW),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.UNRELATED_GAP_COLSPEC
			},
			new RowSpec[] {
				FormFactory.PARAGRAPH_GAP_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.PARAGRAPH_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.PARAGRAPH_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.PARAGRAPH_GAP_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.UNRELATED_GAP_ROWSPEC
			}));

		//---- label1 ----
		label1.setText("Sound Files");
		label1.setFont(UIManager.getFont("InternalFrame.optionDialogTitleFont"));
		add(label1, cc.xywh(3, 3, 3, 1));

		//---- label2 ----
		label2.setText("There are no sound files in our workspace. If you have iLife, click the button");
		label2.setFont(UIManager.getFont("List.font"));
		add(label2, cc.xywh(3, 5, 3, 1));

		//---- label3 ----
		label3.setText("below, and we'll try to import some sound effects from there.");
		label3.setFont(UIManager.getFont("List.font"));
		add(label3, cc.xywh(3, 7, 3, 1));

		//---- label4 ----
		label4.setText("If you don't own iLife, or just want to use your own sound effects,");
		label4.setFont(UIManager.getFont("List.font"));
		add(label4, cc.xywh(3, 9, 3, 1));

		//---- label5 ----
		label5.setText("please read our User's Manual to learn how to add your own sound effects");
		label5.setFont(UIManager.getFont("List.font"));
		add(label5, cc.xywh(3, 11, 3, 1));

		//---- label6 ----
		label6.setText("for use with the Desktop Curtain.");
		label6.setFont(UIManager.getFont("List.font"));
		add(label6, cc.xywh(3, 13, 3, 1));

		//---- importILifeSoundsButton ----
		importILifeSoundsButton.setText("Import iLife Sound Effects");
		add(importILifeSoundsButton, cc.xy(3, 19));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JLabel label4;
	private JLabel label5;
	private JLabel label6;
	private JButton importILifeSoundsButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
