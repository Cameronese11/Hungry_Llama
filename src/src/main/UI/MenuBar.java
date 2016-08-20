package src.main.UI;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

public class MenuBar extends JMenuBar {

	 JMenuBar menuBar;
     JMenu menu, submenu;
     JMenuItem menuItem;
     JRadioButtonMenuItem rbMenuItem;
     JCheckBoxMenuItem cbMenuItem;
	
	public MenuBar(){
		
	 
	}
	
	public JMenuBar createMenuBar() {
       
 
        //Create the menu bar.
        menuBar = new JMenuBar();
 
        //Build the first menu.
        menu = new JMenu("A Menu");
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        menuBar.add(menu);
 
        //a group of JMenuItems
        menuItem = new JMenuItem("A text-only menu item");
      
        menuItem.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        menu.add(menuItem);
 
        
        menuItem = new JMenuItem("Both text and icon");
        menu.add(menuItem);
 
        menuItem = new JMenuItem("another item");
        menu.add(menuItem);
 
        //a group of radio button menu items
        menu.addSeparator();
        ButtonGroup group = new ButtonGroup();
 
        rbMenuItem = new JRadioButtonMenuItem("A radio button menu item");
        rbMenuItem.setSelected(true);
        group.add(rbMenuItem);
        menu.add(rbMenuItem);
 
        rbMenuItem = new JRadioButtonMenuItem("Another one");
        group.add(rbMenuItem);
        menu.add(rbMenuItem);
 
        //a group of check box menu items
        menu.addSeparator();
        cbMenuItem = new JCheckBoxMenuItem("A check box menu item");
        menu.add(cbMenuItem);
 
        cbMenuItem = new JCheckBoxMenuItem("Another one");
        menu.add(cbMenuItem);
 
        //a submenu
        menu.addSeparator();
        submenu = new JMenu("A submenu");
 
        menuItem = new JMenuItem("An item in the submenu");
        submenu.add(menuItem);
 
        menuItem = new JMenuItem("Another item");
        submenu.add(menuItem);
        menu.add(submenu);
 
        //Build second menu in the menu bar.
        menu = new JMenu("Another Menu");
        menu.getAccessibleContext().setAccessibleDescription(
                "This menu does nothing");
        menuBar.add(menu);
 
        return menuBar;
    }
 
    
}
