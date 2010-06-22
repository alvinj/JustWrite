import java.awt.*;
import javax.swing.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
/*
 * Created by JFormDesigner on Sat Jan 30 12:43:43 EST 2010
 */



/**
 * @author Alvin Alexander
 */
public class FullScreenEditorFrame extends JFrame {
	public FullScreenEditorFrame() {
		initComponents();
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public JEditorPane getEditorPane() {
		return editorPane;
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		scrollPane = new JScrollPane();
		editorPane = new JEditorPane();
		CellConstraints cc = new CellConstraints();

		//======== this ========
		setBackground(Color.black);
		Container contentPane = getContentPane();
		contentPane.setLayout(new FormLayout(
			new ColumnSpec[] {
				new ColumnSpec(ColumnSpec.FILL, Sizes.DEFAULT, 0.30000000000000004),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				new ColumnSpec("600px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				new ColumnSpec(ColumnSpec.FILL, Sizes.DEFAULT, 0.30000000000000004)
			},
			new RowSpec[] {
				new RowSpec("80px"),
				FormFactory.LINE_GAP_ROWSPEC,
				new RowSpec(RowSpec.FILL, Sizes.DEFAULT, FormSpec.DEFAULT_GROW),
				FormFactory.LINE_GAP_ROWSPEC,
				new RowSpec("60px")
			}));

		//======== scrollPane ========
		{
			scrollPane.setBackground(Color.black);
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scrollPane.setAutoscrolls(true);
			scrollPane.setBorder(null);
			scrollPane.setFocusable(false);
			scrollPane.setFont(new Font("Monaco", Font.PLAIN, 14));
			scrollPane.setRequestFocusEnabled(false);
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.setViewportBorder(null);

			//---- editorPane ----
			editorPane.setForeground(Color.green);
			editorPane.setFont(new Font("Monaco", Font.PLAIN, 13));
			editorPane.setCaretColor(Color.green);
			editorPane.setSelectedTextColor(Color.green);
			editorPane.setBorder(null);
			editorPane.setSelectionColor(Color.darkGray);
			scrollPane.setViewportView(editorPane);
		}
		contentPane.add(scrollPane, cc.xy(3, 3));
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JScrollPane scrollPane;
	private JEditorPane editorPane;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
