package com.devdaily.justwrite.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
/*
 * Created by JFormDesigner on Fri Feb 05 10:18:08 EST 2010
 */


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
 * TODO - re-implement this code w/o using JFormDesigner.
 *
 */
public class ColorChooserDialog extends JDialog {
  public ColorChooserDialog(Frame owner) {
    super(owner);
    initComponents();
  }

  public ColorChooserDialog(Dialog owner) {
    super(owner);
    initComponents();
  }

  public JPanel getContentPanel() {
    return contentPanel;
  }

  public JLabel getFontColorSwatch() {
    return fontColorSwatch;
  }

  public JLabel getFontColorLabel() {
    return fontColorLabel;
  }

  public JLabel getBackgroundColorSwatch() {
    return backgroundColorSwatch;
  }

  public JLabel getBackgroundColorLabel() {
    return backgroundColorLabel;
  }

  public JScrollPane getTextAreaScrollPane() {
    return textAreaScrollPane;
  }

  public JTextPane getSampleTextArea() {
    return sampleTextArea;
  }

  public JPanel getButtonBar() {
    return buttonBar;
  }

  public JButton getOkButton() {
    return okButton;
  }

  public JButton getCancelButton() {
    return cancelButton;
  }

  public JPanel getDialogPane() {
    return dialogPane;
  }

  public JButton getResetButton() {
    return resetButton;
  }

  private void initComponents() {
    // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
    dialogPane = new JPanel();
    contentPanel = new JPanel();
    fontColorSwatch = new JLabel();
    fontColorLabel = new JLabel();
    backgroundColorSwatch = new JLabel();
    backgroundColorLabel = new JLabel();
    textAreaScrollPane = new JScrollPane();
    sampleTextArea = new JTextPane();
    buttonBar = new JPanel();
    resetButton = new JButton();
    okButton = new JButton();
    cancelButton = new JButton();
    CellConstraints cc = new CellConstraints();

    //======== this ========
    setTitle("Modify the Editor Colors");
    setResizable(false);
    Container contentPane = getContentPane();
    contentPane.setLayout(new BorderLayout());

    //======== dialogPane ========
    {
      dialogPane.setBorder(Borders.DIALOG_BORDER);
      dialogPane.setPreferredSize(new Dimension(388, 237));
      dialogPane.setLayout(new BorderLayout());

      //======== contentPanel ========
      {
        contentPanel.setLayout(new FormLayout(
          new ColumnSpec[] {
            FormFactory.DEFAULT_COLSPEC,
            FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
            FormFactory.DEFAULT_COLSPEC,
            FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
            new ColumnSpec(ColumnSpec.FILL, Sizes.DEFAULT, FormSpec.DEFAULT_GROW),
            FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
            FormFactory.DEFAULT_COLSPEC,
            FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
            FormFactory.DEFAULT_COLSPEC
          },
          new RowSpec[] {
            FormFactory.UNRELATED_GAP_ROWSPEC,
            FormFactory.LINE_GAP_ROWSPEC,
            FormFactory.DEFAULT_ROWSPEC,
            FormFactory.LINE_GAP_ROWSPEC,
            FormFactory.DEFAULT_ROWSPEC,
            FormFactory.PARAGRAPH_GAP_ROWSPEC,
            new RowSpec(RowSpec.CENTER, Sizes.PREFERRED, FormSpec.DEFAULT_GROW),
            FormFactory.PARAGRAPH_GAP_ROWSPEC,
            FormFactory.DEFAULT_ROWSPEC,
            FormFactory.LINE_GAP_ROWSPEC,
            FormFactory.DEFAULT_ROWSPEC
          }));

        //---- fontColorSwatch ----
        fontColorSwatch.setPreferredSize(new Dimension(60, 20));
        fontColorSwatch.setForeground(Color.green);
        fontColorSwatch.setBackground(Color.green);
        fontColorSwatch.setOpaque(true);
        fontColorSwatch.setBorder(new EtchedBorder());
        fontColorSwatch.setToolTipText("Click to change the font color");
        contentPanel.add(fontColorSwatch, cc.xy(3, 3));

        //---- fontColorLabel ----
        fontColorLabel.setText("Font Color");
        fontColorLabel.setToolTipText("Click to change the font color");
        contentPanel.add(fontColorLabel, cc.xy(5, 3));

        //---- backgroundColorSwatch ----
        backgroundColorSwatch.setPreferredSize(new Dimension(60, 20));
        backgroundColorSwatch.setBackground(Color.black);
        backgroundColorSwatch.setOpaque(true);
        backgroundColorSwatch.setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
        backgroundColorSwatch.setToolTipText("Click to change the background color");
        contentPanel.add(backgroundColorSwatch, cc.xy(3, 5));

        //---- backgroundColorLabel ----
        backgroundColorLabel.setText("Background Color");
        backgroundColorLabel.setToolTipText("Click to change the background color");
        contentPanel.add(backgroundColorLabel, cc.xy(5, 5));

        //======== textAreaScrollPane ========
        {
          textAreaScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
          textAreaScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
          textAreaScrollPane.setToolTipText("This is an example of your colors with the current font.");
          textAreaScrollPane.setPreferredSize(new Dimension(300, 60));

          //---- sampleTextArea ----
          sampleTextArea.setText("Four score and seven years ago");
          sampleTextArea.setEditable(false);
          sampleTextArea.setPreferredSize(new Dimension(200, 60));
          sampleTextArea.setToolTipText("Sample area to demonstrate your color choices");
          textAreaScrollPane.setViewportView(sampleTextArea);
        }
        contentPanel.add(textAreaScrollPane, cc.xywh(3, 7, 3, 2));
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
            FormFactory.RELATED_GAP_COLSPEC,
            FormFactory.BUTTON_COLSPEC
          },
          RowSpec.decodeSpecs("pref")));

        //---- resetButton ----
        resetButton.setText("Reset");
        resetButton.setToolTipText("Reset to the system default colors");
        buttonBar.add(resetButton, cc.xy(2, 1));

        //---- okButton ----
        okButton.setText("OK");
        okButton.setToolTipText("Accept your color changes");
        buttonBar.add(okButton, cc.xy(4, 1));

        //---- cancelButton ----
        cancelButton.setText("Cancel");
        cancelButton.setToolTipText("Cancel your color changes");
        buttonBar.add(cancelButton, cc.xy(6, 1));
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
  private JLabel fontColorSwatch;
  private JLabel fontColorLabel;
  private JLabel backgroundColorSwatch;
  private JLabel backgroundColorLabel;
  private JScrollPane textAreaScrollPane;
  private JTextPane sampleTextArea;
  private JPanel buttonBar;
  private JButton resetButton;
  private JButton okButton;
  private JButton cancelButton;
  // JFormDesigner - End of variables declaration  //GEN-END:variables
}

