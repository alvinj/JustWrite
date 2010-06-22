package com.devdaily.justwrite.controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.FontMetrics;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.StyledDocument;
import com.devdaily.justwrite.JustWrite;
import com.devdaily.justwrite.view.FileUtils;
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
public class MainFrameController
{
  public static final Color DEFAULT_FONT_COLOR = Color.green;
  public static final Color DEFAULT_BACKGROUND_COLOR = Color.black;
  public static final Color DEFAULT_SELECTED_TEXT_COLOR = Color.darkGray;

  private JustWrite justWriteMain;
  private JustWriteMainFrame mainFrame;
  private JTextPane editingArea;
  
  private OpenRecentFileController openRecentFileController;
  private ColorChooserController colorChooserController;
  
  private String currentFilename;
  
  // preferences
  private String lastDirectoryAccessed;
  private Color currentBackgroundColor;
  private Color currentFontColor;
  private int lastTextareaWidth;
  private int lastSpaceAboveTextarea;
  private int lastSpaceBelowTextarea;
  private Font lastFont;
  
  public MainFrameController(JustWrite justWriteMain)
  {
    this.justWriteMain = justWriteMain;
    // TODO get these from the user preferences
  }
  
  public void setMainFrame(JustWriteMainFrame mainFrame)
  {
    // TODO doing things like this is a kludge, but i need that mainFrame reference,
    //      and it's a bit of a catch-22.
    this.mainFrame = mainFrame;
    editingArea = mainFrame.getEditorPane();
    openRecentFileController = new OpenRecentFileController(this, mainFrame);
    colorChooserController = new ColorChooserController(this, mainFrame);
    setCurrentForegroundColor(DEFAULT_FONT_COLOR);
    setCurrentBackgroundColor(DEFAULT_BACKGROUND_COLOR);
    // TODO this should probably be done with some bitmapping or xor function i don't currently
    // know about
    editingArea.setSelectionColor(DEFAULT_SELECTED_TEXT_COLOR);
  }
  
  public void doOpenFileAction()
  {
    if (!okToAbandon())
    {
      return;
    }

    // show open file dialog; user selects file, or cancels
    FileDialog fileDialog = new FileDialog(mainFrame, "Select a File to Open", FileDialog.LOAD);
    if (lastDirectoryAccessed!=null && !lastDirectoryAccessed.trim().equals(""))
    {
      fileDialog.setDirectory(lastDirectoryAccessed);
    }
    fileDialog.setModal(true);
    fileDialog.setVisible(true);
    if (fileDialog.getFile() == null) return;

    // doesn't need a File.separator on Mac
    lastDirectoryAccessed = fileDialog.getDirectory();
    currentFilename = fileDialog.getDirectory() + fileDialog.getFile();

    // if user did not cancel, get the file
    loadFileContentsIntoEditorAndUpdateEverything(currentFilename);
  }
  
  private void loadFileContentsIntoEditorAndUpdateEverything(final String filename)
  {
    // TODO make sure i'm not about to try to read binary data

    // TODO do this with a SwingWorker or SwingUtilities ???
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        try
        {
          String fileContents;
          try
          {
            // 1 - reade the file contents
            fileContents = FileUtils.readFileAsString(filename);
            // 2 - set the content in the mainframe editor
            mainFrame.setEditorText(fileContents);
            // 3 - update everything
            currentFilename = filename;
            openRecentFileController.addFilenameToRecentFilelist(currentFilename);
            mainFrame.setDirty(false);
            mainFrame.setFocusInEditingArea();
          }
          catch (IOException e)
          {
            // TODO improve this; add a title, show message in scrolling text area
            JOptionPane.showMessageDialog(mainFrame, e.getMessage());
          }
        }
        catch (Exception exception)
        {
          // do nothing
        }
      }
    });
  }
  
  /**
   * Called when the user indicates they want to save the current file.
   * We may already have a filename, and we may not.
   */
  public void doSaveFileAction()
  {
    // no filename? do save-as action
    if (currentFilename == null)
    {
      doSaveFileAsAction();
      mainFrame.setFocusInEditingArea();
      return;
    }
    
    // just write the contents to the current filename
    try
    {
      FileUtils.writeFile(currentFilename, mainFrame.getEditorText());
      mainFrame.setDirty(false);
      File f = new File(currentFilename);
      // TODO set the lastDirectoryAccessed here
      lastDirectoryAccessed = f.getCanonicalPath();
      mainFrame.setFocusInEditingArea();
    }
    catch (IOException e)
    {
      // TODO improve this; add a title, put text in a scrolling textarea
      // "had a problem trying to save your file"
      JOptionPane.showMessageDialog(mainFrame, e.getMessage());
    }
  }

  /**
   * The user wants to do a Save-As action, or we need to do a Save-As
   * action because they want to save their content to a file, and we
   * don't have a filename.
   */
  public void doSaveFileAsAction()
  {
    saveAsFile();
    mainFrame.setFocusInEditingArea();
  }

  /**
   * Save the editor content to the current filename.
   * If we don't have a filename, the saveAsFile method will be called.
   */
  boolean saveEditorContentToCurrentFilename()
  {
    // handle the case where we don't have a file name yet.
    if (currentFilename == null) 
    {
      return saveAsFile();
    }

    try
    {
      // save the text in the editing area to the current filename
      writeFileAndUpdateEverything(currentFilename, mainFrame.getEditorText());
      return true;
    }
    catch (IOException e)
    {
      showFileSaveErrorOccurredDialog(currentFilename, e);
    }
    return false;
  }
  
  /**
   * Write the given text to the desired filename, and then do whatever needs to be
   * done to keep the editor in sync with this change.
   */
  private void writeFileAndUpdateEverything(String filename, String text) throws IOException
  {
    FileUtils.writeFile(currentFilename, mainFrame.getEditorText());
    // do the maintenance work
    mainFrame.setDirty(false);
    lastDirectoryAccessed = new File(filename).getCanonicalPath();
    mainFrame.setFocusInEditingArea();
  }
  
  /**
   * A convenience method, created so we get consistent error messages.
   */
  private void showFileSaveErrorOccurredDialog(String filename, Exception e)
  {
    String message = "An error happened trying to save '" + filename + "'. Message follows.\n" + e.getMessage();
    JOptionPane.showMessageDialog(mainFrame, message);
  }

  /**
   * Do the File Save-As thing. Return true if the editor content was saved to a file.
   */
  boolean saveAsFile() 
  {
    FileDialog fileDialog = new FileDialog(mainFrame);
    fileDialog.setMode(FileDialog.SAVE);
    fileDialog.setTitle("Save Current Editor to File");
    if (lastDirectoryAccessed!=null && !lastDirectoryAccessed.trim().equals(""))
    {
      fileDialog.setDirectory(lastDirectoryAccessed);
    }
    fileDialog.setVisible(true);
  
    // after the dialog is used ...
    if (fileDialog.getDirectory() != null && fileDialog.getFile() != null)
    {
      // we got a filename; go ahead and save the editor content to this file
      currentFilename = fileDialog.getDirectory() + fileDialog.getFile();
      try
      {
        // save the text in the editing area to the current filename
        writeFileAndUpdateEverything(currentFilename, mainFrame.getEditorText());
        openRecentFileController.addFilenameToRecentFilelist(currentFilename);
        return true;
      }
      catch (IOException e)
      {
        showFileSaveErrorOccurredDialog(currentFilename, e);
        mainFrame.setFocusInEditingArea();
        return false;
      }
    }
    else
    {
      return false;
    }
  }

  boolean okToAbandon()
  {
    // if the current content is not dirty, it's okay to abandon it
    if (!mainFrame.isDirty())
    {
      return true;
    }
    
    // otherwise, we need to ask if it's okay to abandon it
    int value =  JOptionPane.showConfirmDialog(mainFrame,
                                               "Save changes to current editor contents?",
                                               "Save Changes?",
                                               JOptionPane.YES_NO_CANCEL_OPTION) ;
    mainFrame.getEditorPane().requestFocusInWindow();
    switch (value)
    {
       case JOptionPane.YES_OPTION:
         return saveEditorContentToCurrentFilename();

       case JOptionPane.NO_OPTION:
         // 'no' means abandon edits; return true without saving
         return true;

       case JOptionPane.CANCEL_OPTION:
         return false;

       default:
         // cancel
         return false;
    }
  }

  public String getCurrentFilename()
  {
    return currentFilename;
  }

  /**
   * this is the start of a color chooser; really need a color-chooser
   * dialog that lets the user choose the different colors i make available.
   */
  public void doChooseColorAction()
  {
    colorChooserController.showColorChooserDialog(currentFontColor, currentBackgroundColor, true);
    if (colorChooserController.fontColorWasChanged())
    {
      setCurrentForegroundColor(colorChooserController.getNewFontColor());
      editingArea.setForeground(currentFontColor);
    }
    if (colorChooserController.backgroundColorWasChanged())
    {
      setCurrentBackgroundColor(colorChooserController.getNewBackgroundColor());
      editingArea.setBackground(currentBackgroundColor);
    }
    if (colorChooserController.fontColorWasChanged() || colorChooserController.backgroundColorWasChanged())
    {
      mainFrame.getEditorPane().updateUI();
    }
    mainFrame.setFocusInEditingArea();
  }
  
  private void setCurrentForegroundColor(Color c)
  {
    this.currentFontColor = c;
    // TODO update preferences, or tell someone else to
  }

  private void setCurrentBackgroundColor(Color c)
  {
    this.currentBackgroundColor = c;
    // TODO update preferences, or tell someone else to
  }

  /**
   * Reduce the size of the font in the editor area.
   */
  public void doSmallerFontSizeAction()
  {
    // get the current font
    Font f = editingArea.getFont();
    // create a new, smaller font from the current font
    Font f2 = new Font(f.getFontName(), f.getStyle(), f.getSize()-1);
    // set the new font in the editing area
    editingArea.setFont(f2);
  }

  public void doLargerFontSizeAction()
  {
    // get the current font
    Font f = editingArea.getFont();
    // create a new, smaller font from the current font
    Font f2 = new Font(f.getFontName(), f.getStyle(), f.getSize()+1);
    // set the new font in the editing area
    editingArea.setFont(f2);
  }
  
  // ////////////  handle the "close window" process /////////////
  
  public void doCloseCurrentFileAction()
  {
    // if the current editing area is not dirty, just set the text to blank,
    // and make sure the currentFilename is null or blank
    if (!mainFrame.isDirty())
    {
      clearEditorContentsAndUpdateEverything();
      return;
    }
    
    // current editor contents are dirty; prompt user to save them
    if (okToAbandon())
    {
      clearEditorContentsAndUpdateEverything();
    }
  }

  void clearEditorContentsAndUpdateEverything()
  {
    currentFilename = null;
    mainFrame.getEditorPane().setText("");
    mainFrame.setDirty(false);
    mainFrame.setFocusInEditingArea();
  }

  public void doOpenRecentFileAction()
  {
    openRecentFileController.doDisplayDialogAction();
    String filename = openRecentFileController.getSelectedFilename();
//    System.err.println("filename = " + filename);
    if (filename == null)
    {
      mainFrame.setFocusInEditingArea();
      return;
    }
    if (okToAbandon())
    {
//      System.err.println("About to do 'load' action");
      loadFileContentsIntoEditorAndUpdateEverything(filename);
    }
  }


}








