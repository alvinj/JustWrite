import java.awt.*;
import javax.swing.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
/*
 * Created by JFormDesigner on Sat Apr 17 20:08:49 AKDT 2010
 */



/**
 * @author Alvin Alexander
 */
public class ApplyLicenseDialog extends JDialog {
	public ApplyLicenseDialog(Frame owner) {
		super(owner);
		initComponents();
	}

	public ApplyLicenseDialog(Dialog owner) {
		super(owner);
		initComponents();
	}

	public JPanel getDialogPane() {
		return dialogPane;
	}

	public JPanel getContentPanel() {
		return contentPanel;
	}

	public JLabel getHeaderTitle() {
		return headerTitle;
	}

	public JLabel getHeaderHelpText() {
		return headerHelpText;
	}

	public JLabel getFileTextfieldLabel() {
		return fileTextfieldLabel;
	}

	public JTextField getFileTextField() {
		return fileTextField;
	}

	public JButton getBrowseButton() {
		return browseButton;
	}

	public JLabel getNeedALicenseLabel() {
		return needALicenseLabel;
	}

	public JPanel getButtonBar() {
		return buttonBar;
	}

	public JButton getApplyLicenseButton() {
		return applyLicenseButton;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		headerTitle = new JLabel();
		headerHelpText = new JLabel();
		fileTextfieldLabel = new JLabel();
		fileTextField = new JTextField();
		browseButton = new JButton();
		needALicenseLabel = new JLabel();
		buttonBar = new JPanel();
		cancelButton = new JButton();
		applyLicenseButton = new JButton();
		CellConstraints cc = new CellConstraints();

		//======== this ========
		setTitle("Desktop Curtain Licensing");
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== dialogPane ========
		{
			dialogPane.setBorder(Borders.DIALOG_BORDER);
			dialogPane.setLayout(new BorderLayout());

			//======== contentPanel ========
			{
				contentPanel.setLayout(new FormLayout(
					new ColumnSpec[] {
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						new ColumnSpec(ColumnSpec.FILL, Sizes.DEFAULT, FormSpec.DEFAULT_GROW),
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC
					},
					new RowSpec[] {
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.LINE_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.LINE_GAP_ROWSPEC,
						FormFactory.PARAGRAPH_GAP_ROWSPEC,
						FormFactory.LINE_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.LINE_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.LINE_GAP_ROWSPEC,
						FormFactory.PARAGRAPH_GAP_ROWSPEC,
						FormFactory.LINE_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.LINE_GAP_ROWSPEC,
						FormFactory.PARAGRAPH_GAP_ROWSPEC
					}));

				//---- headerTitle ----
				headerTitle.setText("Licensing");
				headerTitle.setFont(new Font("Lucida Grande", Font.BOLD, 14));
				contentPanel.add(headerTitle, cc.xywh(1, 1, 5, 1));

				//---- headerHelpText ----
				headerHelpText.setText("Select the license file from your system, and click \"Apply License\".");
				contentPanel.add(headerHelpText, cc.xywh(1, 3, 5, 1));

				//---- fileTextfieldLabel ----
				fileTextfieldLabel.setText("Choose license file:");
				contentPanel.add(fileTextfieldLabel, cc.xywh(1, 7, 5, 1));
				contentPanel.add(fileTextField, cc.xywh(1, 9, 4, 1));

				//---- browseButton ----
				browseButton.setText("Browse...");
				contentPanel.add(browseButton, cc.xy(5, 9));

				//---- needALicenseLabel ----
				needALicenseLabel.setText("Need a license? Click here to get started.");
				needALicenseLabel.setForeground(Color.gray);
				contentPanel.add(needALicenseLabel, cc.xywh(1, 13, 5, 1));
			}
			dialogPane.add(contentPanel, BorderLayout.CENTER);

			//======== buttonBar ========
			{
				buttonBar.setBorder(Borders.BUTTON_BAR_GAP_BORDER);
				buttonBar.setLayout(new FormLayout(
					new ColumnSpec[] {
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.GLUE_COLSPEC,
						FormFactory.BUTTON_COLSPEC,
						FormFactory.MIN_COLSPEC,
						FormFactory.BUTTON_COLSPEC
					},
					RowSpec.decodeSpecs("pref")));

				//---- cancelButton ----
				cancelButton.setText("Cancel");
				buttonBar.add(cancelButton, cc.xy(4, 1));

				//---- applyLicenseButton ----
				applyLicenseButton.setText("Apply License");
				buttonBar.add(applyLicenseButton, cc.xy(6, 1));
			}
			dialogPane.add(buttonBar, BorderLayout.SOUTH);
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel dialogPane;
	private JPanel contentPanel;
	private JLabel headerTitle;
	private JLabel headerHelpText;
	private JLabel fileTextfieldLabel;
	private JTextField fileTextField;
	private JButton browseButton;
	private JLabel needALicenseLabel;
	private JPanel buttonBar;
	private JButton cancelButton;
	private JButton applyLicenseButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
