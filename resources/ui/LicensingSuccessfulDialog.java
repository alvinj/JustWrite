import java.awt.*;
import javax.swing.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
/*
 * Created by JFormDesigner on Sat Apr 17 20:29:02 AKDT 2010
 */



/**
 * @author Alvin Alexander
 */
public class LicensingSuccessfulDialog extends JDialog {
	public LicensingSuccessfulDialog(Frame owner) {
		super(owner);
		initComponents();
	}

	public LicensingSuccessfulDialog(Dialog owner) {
		super(owner);
		initComponents();
	}

	public JPanel getDialogPane() {
		return dialogPane;
	}

	public JPanel getContentPanel() {
		return contentPanel;
	}

	public JScrollPane getHtmlMessageScrollPane() {
		return htmlMessageScrollPane;
	}

	public JEditorPane getHtmlMessageArea() {
		return htmlMessageArea;
	}

	public JButton getCloseButton() {
		return closeButton;
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		htmlMessageScrollPane = new JScrollPane();
		htmlMessageArea = new JEditorPane();
		closeButton = new JButton();
		CellConstraints cc = new CellConstraints();

		//======== this ========
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
						FormFactory.GLUE_COLSPEC,
						new ColumnSpec(ColumnSpec.LEFT, Sizes.dluX(0), FormSpec.NO_GROW),
						new ColumnSpec(ColumnSpec.FILL, Sizes.DEFAULT, FormSpec.DEFAULT_GROW),
						new ColumnSpec(Sizes.dluX(0)),
						FormFactory.GLUE_COLSPEC
					},
					new RowSpec[] {
						FormFactory.LINE_GAP_ROWSPEC,
						FormFactory.LINE_GAP_ROWSPEC,
						new RowSpec(RowSpec.FILL, Sizes.DEFAULT, FormSpec.DEFAULT_GROW),
						FormFactory.LINE_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC
					}));

				//======== htmlMessageScrollPane ========
				{

					//---- htmlMessageArea ----
					htmlMessageArea.setContentType("text/html");
					htmlMessageArea.setEditable(false);
					htmlMessageArea.setFont(new Font("Monaco", Font.PLAIN, 13));
					htmlMessageArea.setMargin(new Insets(4, 4, 4, 4));
					htmlMessageArea.setMinimumSize(new Dimension(360, 240));
					htmlMessageArea.setPreferredSize(new Dimension(360, 240));
					htmlMessageScrollPane.setViewportView(htmlMessageArea);
				}
				contentPanel.add(htmlMessageScrollPane, cc.xy(3, 3));

				//---- closeButton ----
				closeButton.setText("Close");
				closeButton.setFont(UIManager.getFont("EditorPane.font"));
				contentPanel.add(closeButton, cc.xywh(3, 5, 1, 1, CellConstraints.CENTER, CellConstraints.DEFAULT));
			}
			dialogPane.add(contentPanel, BorderLayout.CENTER);
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel dialogPane;
	private JPanel contentPanel;
	private JScrollPane htmlMessageScrollPane;
	private JEditorPane htmlMessageArea;
	private JButton closeButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
