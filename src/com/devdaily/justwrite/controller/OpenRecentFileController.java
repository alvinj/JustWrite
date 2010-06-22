package com.devdaily.justwrite.controller;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import com.devdaily.justwrite.view.JustWriteMainFrame;
import com.devdaily.justwrite.view.OpenRecentFileDialog;

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
public class OpenRecentFileController
{
  // things in this app we talk to
  MainFrameController mainFrameController;
  JustWriteMainFrame mainFrame;
  
  // our filelist things
  private static final String RECENT_FILE_LIST_FILENAME = ".justWriteRecentFilelist";
  private static final int MAX_NUM_FILES_IN_LIST        = 15;
  
  File recentFileListFile;
  List<String> recentFileList = new ArrayList<String>();
  String filenameSelected;

  // our things we know about
  OpenRecentFileDialog openRecentFileDialog;
  JButton okButton;
  JButton cancelButton;
  List<String> recentFilesList;
 
  public OpenRecentFileController(MainFrameController mainFrameController, JustWriteMainFrame mainFrame)
  {
    this.mainFrame = mainFrame;
    // set up our dialog, but don't load any data yet
    openRecentFileDialog = new OpenRecentFileDialog(mainFrame, this);
    openRecentFileDialog.setModal(true);
    okButton = openRecentFileDialog.getOKButton();
    cancelButton = openRecentFileDialog.getCancelButton();
    addListenersToButtons();
  }

  private void addListenersToButtons()
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
  }
  
  public void doCancelButtonAction()
  {
    openRecentFileDialog.setVisible(false);
  }

  public void doOkButtonAction()
  {
    // TODO it would be an error for this to be null; don't allow it
    filenameSelected = openRecentFileDialog.getSelectedFilename();
    openRecentFileDialog.setVisible(false);
  }

  /**
   * Call this method after displaying the dialog to get the
   * filename the user selected.
   */
  public String getSelectedFilename()
  {
    return filenameSelected;
  }

  /**
   * The program that calls us should call this method to tell 
   * us to display the RecentFilesDialog.
   * It should then call the getSelectedFilename() method
   * to get the filename the user selected.
   */
  public void doDisplayDialogAction()
  {
    recentFilesList = getRecentFilesList();
    openRecentFileDialog.setListOfFiles(recentFilesList);
    openRecentFileDialog.pack();
    openRecentFileDialog.setLocationRelativeTo(mainFrame);
    openRecentFileDialog.setFocusOnListOfFiles();
    openRecentFileDialog.setVisible(true);
  }

  private void loadRecentFilesList() throws IOException
  {
    String fullFilename = getDirectoryRecentFileListFilename() + RECENT_FILE_LIST_FILENAME;
    recentFileListFile = new File(fullFilename);
    recentFileList = new ArrayList<String>();
    if (recentFileListFile.exists())
    {
      // read it and create the list; each line is a new filename
      BufferedReader br = new BufferedReader(new FileReader(recentFileListFile));
      String line = null;
      int count = 0;
      while ( (line = br.readLine()) != null )
      {
        // limit the number of files in the list
        if (++count > MAX_NUM_FILES_IN_LIST) break;
        if (!line.trim().equals(""))
        {
          // add the line to the list
          recentFileList.add(line);
        }
      }
      br.close();
    }
    else
    {
      // if it doesn't exist, create it now
      recentFileListFile.createNewFile();
    }
  }

  private String getDirectoryRecentFileListFilename()
  {
    String homeDir = System.getProperty("user.home");
    String fileSep = System.getProperty("file.separator");
    return homeDir + fileSep;
  }
  
  public void addFilenameToRecentFilelist(String currentFilename)
  {
    String recentFilelistFilename = getDirectoryRecentFileListFilename() + RECENT_FILE_LIST_FILENAME;
    try
    {
      // if there is an old entry, remove it; then add the
      // currentFilename to the top of the list
      recentFileList.remove(currentFilename);
      recentFileList.add(0, currentFilename);
      
      // write the list to disk (just replace old file contents)
      File file = new File (recentFilelistFilename);
      FileWriter out = new FileWriter(file);
      for (int i=0; i<recentFileList.size(); i++)
      {
        out.write((String)recentFileList.get(i) + "\n");
        if (i > MAX_NUM_FILES_IN_LIST) break;
      }
      out.close();
    }
    catch (IOException e) {
      // TODO can i log this on a mac somewhere???
    }    
  }

  public List<String> getRecentFilesList()
  {
    try
    {
      loadRecentFilesList();
    }
    catch (IOException e)
    {
      JOptionPane.showMessageDialog(mainFrame, "Got an exception trying to open the recent filelist.");
    }
    return this.recentFileList;
  }
  
  
  
}







