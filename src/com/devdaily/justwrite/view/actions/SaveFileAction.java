package com.devdaily.justwrite.view.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import com.devdaily.justwrite.controller.MainFrameController;

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
public class SaveFileAction extends AbstractAction
{
  private MainFrameController mainFrameController;

  public SaveFileAction(MainFrameController mainFrameController, String name, Integer mnemonic)
  {
    super(name, null);
    this.mainFrameController = mainFrameController;
    putValue(MNEMONIC_KEY, mnemonic);
  }

  public SaveFileAction(String name, ImageIcon icon)
  {
    super(name, icon);
  }

  public void actionPerformed(ActionEvent e)
  {
    mainFrameController.doSaveFileAction();
  }
}
