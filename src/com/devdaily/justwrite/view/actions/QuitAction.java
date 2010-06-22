package com.devdaily.justwrite.view.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
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
public class QuitAction extends AbstractAction
{
  JustWriteMainFrame mainFrame;
  
  public QuitAction(JustWriteMainFrame mainFrame, String name, Integer mnemonic)
  {
    super(name, null);
    this.mainFrame = mainFrame;
    // TODO re-enable this when ready
    //putValue(MNEMONIC_KEY, mnemonic);
  }

  public void actionPerformed(ActionEvent e)
  {
    String title = null;
    String message = null;
    
    if (!mainFrame.isDirty())
    {
      System.exit(0);
    }
    
    title = "Editor changes not saved - Quit?";
    message = "Changes to the text in the editor have not been saved. Really quit?";
    
    int choice = JOptionPane.showOptionDialog(null, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
    if (choice == JOptionPane.YES_OPTION)
    {
      System.exit(0);
    }
    {
      // kludge to get focus back in the mainframe
      mainFrame.setFocusInEditingArea();
    }
  }
}


