package com.devdaily.justwrite.view.actions;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
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
public class OpenFileAction extends AbstractAction
{
  MainFrameController mainFrameController;
  JustWriteMainFrame mainFrame;
  
  public OpenFileAction(MainFrameController mainFrameController, JustWriteMainFrame mainFrame, String name, Integer mnemonic)
  {
    super(name, null);
    this.mainFrameController = mainFrameController;
    this.mainFrame = mainFrame;
    putValue(MNEMONIC_KEY, mnemonic);
  }

  public void actionPerformed(ActionEvent e)
  {
    mainFrameController.doOpenFileAction();
  }
}
