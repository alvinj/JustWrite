import java.awt.*;
import javax.swing.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
/*
 * Created by JFormDesigner on Thu Apr 22 20:23:56 AKDT 2010
 */



/**
 * @author Alvin Alexander
 */
public class PreferencesDialog extends JDialog {
	public PreferencesDialog(Frame owner) {
		super(owner);
		initComponents();
	}

	public PreferencesDialog(Dialog owner) {
		super(owner);
		initComponents();
	}

	public JPanel getNorthPanel() {
		return northPanel;
	}

	public JPanel getWestPanel() {
		return westPanel;
	}

	public JPanel getSouthPanel() {
		return southPanel;
	}

	public JPanel getEastPanel() {
		return eastPanel;
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		northPanel = new JPanel();
		westPanel = new JPanel();
		southPanel = new JPanel();
		eastPanel = new JPanel();
		tabbedPane = new JTabbedPane();
		CellConstraints cc = new CellConstraints();

		//======== this ========
		setTitle("DesktopCurtain Preferences");
		setModal(true);
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== northPanel ========
		{
			northPanel.setLayout(new FormLayout(
				new ColumnSpec[] {
					FormFactory.DEFAULT_COLSPEC,
					FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
					FormFactory.DEFAULT_COLSPEC
				},
				new RowSpec[] {
					FormFactory.PARAGRAPH_GAP_ROWSPEC
				}));
		}
		contentPane.add(northPanel, BorderLayout.NORTH);

		//======== westPanel ========
		{
			westPanel.setLayout(new FormLayout(
				new ColumnSpec[] {
					FormFactory.UNRELATED_GAP_COLSPEC,
					FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
					FormFactory.DEFAULT_COLSPEC
				},
				new RowSpec[] {
					FormFactory.DEFAULT_ROWSPEC,
					FormFactory.LINE_GAP_ROWSPEC,
					FormFactory.DEFAULT_ROWSPEC,
					FormFactory.LINE_GAP_ROWSPEC,
					FormFactory.DEFAULT_ROWSPEC
				}));
		}
		contentPane.add(westPanel, BorderLayout.WEST);

		//======== southPanel ========
		{
			southPanel.setLayout(new FormLayout(
				new ColumnSpec[] {
					FormFactory.DEFAULT_COLSPEC,
					FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
					FormFactory.DEFAULT_COLSPEC
				},
				new RowSpec[] {
					FormFactory.DEFAULT_ROWSPEC,
					FormFactory.LINE_GAP_ROWSPEC,
					FormFactory.PARAGRAPH_GAP_ROWSPEC,
					FormFactory.LINE_GAP_ROWSPEC,
					FormFactory.DEFAULT_ROWSPEC
				}));
		}
		contentPane.add(southPanel, BorderLayout.SOUTH);

		//======== eastPanel ========
		{
			eastPanel.setLayout(new FormLayout(
				new ColumnSpec[] {
					FormFactory.UNRELATED_GAP_COLSPEC,
					FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
					FormFactory.DEFAULT_COLSPEC
				},
				new RowSpec[] {
					FormFactory.DEFAULT_ROWSPEC,
					FormFactory.LINE_GAP_ROWSPEC,
					FormFactory.DEFAULT_ROWSPEC,
					FormFactory.LINE_GAP_ROWSPEC,
					FormFactory.DEFAULT_ROWSPEC
				}));
		}
		contentPane.add(eastPanel, BorderLayout.EAST);

		//======== tabbedPane ========
		{
			tabbedPane.setMinimumSize(new Dimension(400, 300));
			tabbedPane.setPreferredSize(new Dimension(400, 300));
		}
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel northPanel;
	private JPanel westPanel;
	private JPanel southPanel;
	private JPanel eastPanel;
	private JTabbedPane tabbedPane;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
