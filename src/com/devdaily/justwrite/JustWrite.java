package com.devdaily.justwrite;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import ch.randelshofer.quaqua.QuaquaManager;
import com.devdaily.justwrite.controller.MainFrameController;
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
public class JustWrite
{
  private JustWriteMainFrame mainFrame;
  private MainFrameController mainFrameController;
  
  // TODO implement this as a file chooser
  FileDialog fileDialog;
  
  // for debugging
  private static final String DEBUG_FILENAME = "dd-fullscreen.debug";
  private String homeDir;
  private String canonDebugFilename;
  private File debugFile;
  private PrintWriter debugFileWriter;

  // recent file list
  // TODO see this file in the MyEditor project: RecentFilesMenuController
  private static final String RECENT_FILE_LIST_FILENAME = ".justWriteRecentFileList";
  private static final int MAX_NUM_FILES_IN_LIST        = 15;
  
  public static void main(String[] args)
  {
    new JustWrite();
  }
  
  public JustWrite()
  {
    // TODO i keep these here to run the app from eclipse w/o using the ant build script;
    //      there may be a better way to do this
    System.setProperty("apple.awt.graphics.EnableQ2DX", "true");
    System.setProperty("apple.laf.useScreenMenuBar", "true");
    System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Just Write");
    System.setProperty("apple.awt.textantialiasing", "true");

    // TODO experimenting with different ways of getting the look and feel i want,
    //      particularly making the scrollbar darker
//    UIManager.getLookAndFeelDefaults().put( "ScrollBar.thumb", Color.black );
//    UIManager.getLookAndFeelDefaults().put( "ScrollBar.thumbShadow", Color.black );
//    UIManager.getLookAndFeelDefaults().put( "ScrollBar.thumbHighlight", Color.black );
//    UIManager.getLookAndFeelDefaults().put( "ScrollBar.background", Color.black );
//    UIManager.getLookAndFeelDefaults().put( "ScrollBar.thumbDarkShadow", Color.black );
//    UIManager.getLookAndFeelDefaults().put( "ScrollBar.shadow", Color.black );
//    UIManager.getLookAndFeelDefaults().put( "ScrollBar.highlight", Color.black );
//    UIManager.getLookAndFeelDefaults().put( "ScrollBar.darkShadow", Color.black );
//    UIManager.getLookAndFeelDefaults().put( "ScrollBar.foreground", Color.black );
//    UIManager.getLookAndFeelDefaults().put( "ScrollBar.track", Color.black );
//    UIManager.getLookAndFeelDefaults().put( "ScrollBar.trackHighlight", Color.black );
    
    // determine the name of our debug file
    createDebugFileWriter();
    
    // note: this mainFrame is null at this point
    mainFrameController = new MainFrameController(this);

    // currently i just want to override the ColorChooser
    Set includes = new HashSet();
    includes.add("ColorChooser");
    QuaquaManager.setIncludedUIs(includes);
    
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        try
        {
          // did this to get the quaqua jcolorchooser; if it creates a problem, switch back
          UIManager.setLookAndFeel("ch.randelshofer.quaqua.QuaquaLookAndFeel");
          //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
          mainFrame = new JustWriteMainFrame(mainFrameController);
          mainFrameController.setMainFrame(mainFrame);
          mainFrame.setLocationRelativeTo(null);
          mainFrame.setVisible(true);
          mainFrame.getScrollPane().updateUI();
          mainFrame.validate();
        }
        catch (Exception exception)
        {
          // TODO remove this before going live
          exception.printStackTrace();
          if (debugFileWriter!=null) exception.printStackTrace(debugFileWriter);
        }
      }
    });
  }
  
  private void createDebugFileWriter()
  {
    homeDir = System.getProperty("user.home");
    canonDebugFilename = homeDir + System.getProperty("file.separator") + DEBUG_FILENAME;
    debugFile = new File(canonDebugFilename);
    try
    {
      debugFileWriter = new PrintWriter(debugFile);
    }
    catch (FileNotFoundException e)
    {
      // just going to ignore this one; if we can't write a debug file,
      // we can't write.
    }
  }
  
  public PrintWriter getDebugFileWriter()
  {
    return this.debugFileWriter;
  }
  
  public JustWriteMainFrame getMainFrame()
  {
    return this.mainFrame;
  }
  


}











