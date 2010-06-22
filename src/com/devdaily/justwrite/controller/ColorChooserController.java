package com.devdaily.justwrite.controller;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import ch.randelshofer.quaqua.colorchooser.ColorChooserMainPanel;
import ch.randelshofer.quaqua.colorchooser.ColorWheel;
import com.devdaily.justwrite.view.ColorChooserDialog;
import com.devdaily.justwrite.view.JustWriteMainFrame;

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
 */
public class ColorChooserController
{
  // things in this app we talk to
  private MainFrameController mainFrameController;
  private JustWriteMainFrame mainFrame;
  
  // components from the ColorChooserDialog
  private ColorChooserDialog colorChooserDialog;
  private JButton okButton;
  private JButton cancelButton;
  private JButton resetButton;
  private JLabel fontColorSwatch;
  private JLabel backgroundColorSwatch;
  private JLabel fontColorLabel;
  private JLabel backgroundColorLabel;
  private JTextPane sampleEditorArea;

  private static final String sampleText = "Four score and seven years ago,\n"
    + "our fathers set forth ...";
  
  private Color oldFontColor;
  private Color oldBackgroundColor;
  private Color newFontColor;
  private Color newBackgroundColor;

  private boolean fontColorWasChanged;
  private boolean backgroundColorWasChanged;
  private boolean okButtonWasPressed;

  public ColorChooserController(MainFrameController mainFrameController, JustWriteMainFrame mainFrame)
  {
    this.mainFrameController = mainFrameController;
    this.mainFrame = mainFrame;
    colorChooserDialog = new ColorChooserDialog(mainFrame);
    colorChooserDialog.setModal(true);
    okButton = colorChooserDialog.getOkButton();
    cancelButton = colorChooserDialog.getCancelButton();
    resetButton = colorChooserDialog.getResetButton();
    fontColorSwatch = colorChooserDialog.getFontColorSwatch();
    backgroundColorSwatch = colorChooserDialog.getBackgroundColorSwatch();
    fontColorLabel = colorChooserDialog.getFontColorLabel();
    backgroundColorLabel = colorChooserDialog.getBackgroundColorLabel();
    sampleEditorArea = colorChooserDialog.getSampleTextArea();
    // give the sample area a margin and some sample text
    sampleEditorArea.setMargin(new Insets(5, 5, 5, 5));
    sampleEditorArea.setText(sampleText);
    addListenersToWidgets();
  }
  
  private void addListenersToWidgets()
  {
    okButton.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(final ActionEvent e)
      {
        doOkButtonAction();
      }
    });
    cancelButton.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(final ActionEvent e)
      {
        doCancelButtonAction();
      }
    });
    resetButton.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(final ActionEvent e)
      {
        doResetButtonAction();
      }
    });
    // let the user click the color swatch or label
    fontColorSwatch.addMouseListener(new MouseAdapter()
    {
      public void mouseClicked(MouseEvent me)
      {
        doFontColorClicked();
      }
    });
    fontColorLabel.addMouseListener(new MouseAdapter()
    {
      public void mouseClicked(MouseEvent me)
      {
        doFontColorClicked();
      }
    });
    // let the user click the color swatch or label
    backgroundColorSwatch.addMouseListener(new MouseAdapter()
    {
      public void mouseClicked(MouseEvent me)
      {
        doBackgroundColorClicked();
      }
    });
    backgroundColorLabel.addMouseListener(new MouseAdapter()
    {
      public void mouseClicked(MouseEvent me)
      {
        doBackgroundColorClicked();
      }
    });
  }
  
  public void doFontColorClicked()
  {
    JColorChooser chooser = new JColorChooser();
    int choice = showJColorChooserDialog("Select a Font Color", chooser, oldFontColor);

    // if user clicked ok, get the new color; otherwise, ignore
    if (choice == JOptionPane.OK_OPTION)
    {
      Color c = chooser.getColor();
      // set this in case they go back and forth between the two dialogs
      // several times
      oldFontColor = c;
      fontColorSwatch.setBackground(c);
      fontColorSwatch.setForeground(c);
      sampleEditorArea.setForeground(c);
      this.newFontColor = c;
      this.fontColorWasChanged = true;
      this.okButtonWasPressed = true;
    }
  }

  public void doBackgroundColorClicked()
  {
    JColorChooser chooser = new JColorChooser();
    int choice = showJColorChooserDialog("Select a Background Color", chooser, oldBackgroundColor);

    // if user clicked ok, get the new color; otherwise, ignore
    if (choice == JOptionPane.OK_OPTION)
    {
      Color c = chooser.getColor();
      // set this in case they go back and forth between the two dialogs
      // several times
      oldBackgroundColor = c;
      backgroundColorSwatch.setBackground(c);
      backgroundColorSwatch.setForeground(c);
      sampleEditorArea.setBackground(c);
      this.newBackgroundColor = c;
      this.backgroundColorWasChanged = true;
      this.okButtonWasPressed = true;
    }
  }
  
  private int showJColorChooserDialog(String title, JColorChooser colorChooser, Color defaultColor)
  {
    colorChooser.setColor(defaultColor);
    int choice = JOptionPane.showOptionDialog(null, 
        colorChooser,
        title, 
        JOptionPane.OK_CANCEL_OPTION, 
        JOptionPane.PLAIN_MESSAGE, 
        null, null, null);
    return choice;
  }
  
  public Color getNewBackgroundColor()
  {
    return this.newBackgroundColor;
  }

  public Color getNewFontColor()
  {
    return this.newFontColor;
  }
  
  /*
   * check this before accessing the font color.
   */
  public boolean fontColorWasChanged()
  {
    if (okButtonWasPressed)
    {
      return this.fontColorWasChanged;
    }
    else
    {
      return false;
    }
  }

  /*
   * check this before accessing the background color.
   */
  public boolean backgroundColorWasChanged()
  {
    if (okButtonWasPressed)
    {
      return this.backgroundColorWasChanged;
    }
    else
    {
      return false;
    }
  }

  public void doResetButtonAction()
  {
    backgroundColorSwatch.setBackground(MainFrameController.DEFAULT_BACKGROUND_COLOR);
    fontColorSwatch.setBackground(MainFrameController.DEFAULT_FONT_COLOR);
    sampleEditorArea.setBackground(MainFrameController.DEFAULT_BACKGROUND_COLOR);
    sampleEditorArea.setForeground(MainFrameController.DEFAULT_FONT_COLOR);
    newBackgroundColor = MainFrameController.DEFAULT_BACKGROUND_COLOR;
    newFontColor = MainFrameController.DEFAULT_FONT_COLOR;
    fontColorWasChanged = true;
    backgroundColorWasChanged = true;
  }

  public void doCancelButtonAction()
  {
    colorChooserDialog.setVisible(false);
    okButtonWasPressed = false;
  }

  public void doOkButtonAction()
  {
    // set the colors that the user selected
    colorChooserDialog.setVisible(false);
    okButtonWasPressed = true;
  }

  public void showColorChooserDialog(Color initialFontColor, Color initialBackgroundColor, boolean b)
  {
    resetOurFields();
    oldFontColor = initialFontColor;
    oldBackgroundColor = initialBackgroundColor;
    // set the color swatches
    fontColorSwatch.setBackground(initialFontColor);
    backgroundColorSwatch.setBackground(initialBackgroundColor);
    // set the color in our sample text area
    sampleEditorArea.setForeground(initialFontColor);
    sampleEditorArea.setBackground(initialBackgroundColor);
    colorChooserDialog.setVisible(b);
  }
  
  private void resetOurFields()
  {
    okButtonWasPressed = false;
    fontColorWasChanged = false;
    backgroundColorWasChanged = false;
    newFontColor = null;
    newBackgroundColor = null;
  }

}













