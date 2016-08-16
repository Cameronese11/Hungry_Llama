package src.main.UI;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar {

	JMenu menu;
	JMenuItem menuItem;
	
	public MenuBar(){
		super();
		 	menu = new JMenu("A Menu");
	       
	        menu.getAccessibleContext().setAccessibleDescription(
	                "The only menu in this program that has menu items");
	        add(menu);
	 
	        //a group of JMenuItems
	        menuItem = new JMenuItem("A text-only menu item");
	        //menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
	       
	        menuItem.getAccessibleContext().setAccessibleDescription(
	                "This doesn't really do anything");
	        menu.add(menuItem);
	 
	}
	
	
}
