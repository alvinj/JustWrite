package com.devdaily.justwrite.view;

import java.awt.*;
import javax.swing.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;

/**
 * Copyright 2010, Alvin Alexander, http://devdaily.com.
 * This software is distributed under the terms of the 
 * GNU General Public License.
 *
 * This file is part of an application named JustWrite.
 *
 * JustWrite is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JustWrite is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JustWrite. If not, see <http://www.gnu.org/licenses/>.
 * 
 * TODO - re-implement this class w/o using JFormDesigner.
 *
 */
public class FullScreenEditorFrame extends JFrame {
  public FullScreenEditorFrame() {
    initComponents();
  }

  public JScrollPane getScrollPane() {
    return scrollPane;
  }

  // NEW
  public LookAheadTextPane getEditorPane() {
    return editorPane;
  }

  private void initComponents() {
    // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
    scrollPane = new JScrollPane();

    // NEW - this was a JTextPane
    DocumentLookAhead lookAhead = new DocumentLookAhead();
    editorPane = new LookAheadTextPane(lookAhead);

    CellConstraints cc = new CellConstraints();

    //======== this ========
    setBackground(Color.black);
    Container contentPane = getContentPane();
    contentPane.setLayout(new FormLayout(
      new ColumnSpec[] {
        new ColumnSpec(ColumnSpec.FILL, Sizes.DEFAULT, 0.30000000000000004),
        FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
        new ColumnSpec("660px"),
        FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
        new ColumnSpec(ColumnSpec.FILL, Sizes.DEFAULT, 0.30000000000000004)
      },
      new RowSpec[] {
        // NEW
        new RowSpec("96px"),
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
      scrollPane.setRequestFocusEnabled(false);
      scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
      scrollPane.setViewportBorder(null);

      //---- editorPane ----
      editorPane.setBackground(new Color(12, 12, 12));
      editorPane.setForeground(Color.green);
      editorPane.setFont(new Font("Monaco", Font.PLAIN, 13));
      editorPane.setCaretColor(Color.green);
      editorPane.setSelectedTextColor(Color.green);
      editorPane.setBorder(null);
      editorPane.setSelectionColor(Color.darkGray);
      scrollPane.setViewportView(editorPane);
    }
    contentPane.add(scrollPane, cc.xy(3, 3));
    // JFormDesigner - End of component initialization  //GEN-END:initComponents
  }

  // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
  private JScrollPane scrollPane;

  // NEW - this was a JTextPane
  private LookAheadTextPane editorPane;
  // JFormDesigner - End of variables declaration  //GEN-END:variables
}




