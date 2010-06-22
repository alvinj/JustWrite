package com.devdaily.justwrite.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

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
 * This class is used to make it easy to handle the [ESC] and
 * [Enter] keys in a dialog. A possibly better way to do this is to
 * use panels instead of dialogs, and then use the JOptionPane.show*
 * methods.
 *
 */
public abstract class DDDialog
extends JDialog
implements ContainerListener, KeyListener
{
  JPanel panel = new JPanel();
  BorderLayout borderLayout = new BorderLayout();

  public DDDialog(Frame frame, String title, boolean modal)
  {
    super(frame, title, modal);
    try 
    {
      panel.setLayout(borderLayout);
      getContentPane().add(panel);

      addKeyAndContainerListenerRecursively(this);
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public DDDialog(Frame frame)
  {
    this(frame, "", true);
  }

  public DDDialog()
  {
    this(null, "", true);
  }

  /**
   * This method is called when the user hits the [Enter] key
   * (or "OK" command).The idea is for you to override this
   * and implement your own functionality in your subclass.
   *
   * @param e KeyEvent
   */
  public abstract void okCommandPerformed(KeyEvent e);

  /**
   * This method is called when the user hits the [Esc] key.
   * The idea is for you to override this and implement your own
   * functionality in your subclass.
   *
   * @param e KeyEvent
   */
  public abstract void escapeCommandPerformed(KeyEvent e);

  protected void addKeyAndContainerListenerRecursively(Component c)
  {
    //Add KeyListener to the Component passed as an argument
    c.addKeyListener(this);

    //Check if the Component is a Container
    if (c instanceof Container) {

      //Component c is a Container. The following cast is safe.
      Container cont = (Container) c;

      //Add ContainerListener to the Container.
      cont.addContainerListener(this);

      //Get the Container's array of children Components.
      Component[] children = cont.getComponents();

      //For every child repeat the above operation.
      for (int i = 0; i < children.length; i++) {
        addKeyAndContainerListenerRecursively(children[i]);
      }
    }
  }

//The following function is the same as the function above with the exception that it does exactly the opposite - removes this Dialog
//from the listener lists of Components.

  protected void removeKeyAndContainerListenerRecursively(Component c)
  {
    c.removeKeyListener(this);
    if (c instanceof Container) {
      Container cont = (Container) c;
      cont.removeContainerListener(this);
      Component[] children = cont.getComponents();
      for (int i = 0; i < children.length; i++) {
        removeKeyAndContainerListenerRecursively(children[i]);
      }
    }
  }

  public void keyPressed(KeyEvent e) {
    int keyCode = e.getKeyCode();
    if (keyCode == KeyEvent.VK_ESCAPE) {
      escapeCommandPerformed(e);
    }
    else if (keyCode == KeyEvent.VK_ENTER) {
      okCommandPerformed(e);
    }
  }

  public void keyReleased(KeyEvent e)
  {
  }

  public void keyTyped(KeyEvent e)
  {
  }

  public void componentAdded(ContainerEvent e)
  {
    addKeyAndContainerListenerRecursively(e.getChild());
  }

  public void componentRemoved(ContainerEvent e)
  {
    removeKeyAndContainerListenerRecursively(e.getChild());
  }

} // end of class


