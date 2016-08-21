package src.main.UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;

public class MenuBar extends JMenuBar implements ActionListener{

	private CluedoFrame frame;
	private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem menuItem1;
    private JMenuItem menuItem2;
    
	
	public MenuBar(CluedoFrame frame){
		this.frame = frame;
	 
	}
	
	public JMenuBar createMenuBar() {
       
 
        //Create the menu bar.
        menuBar = new JMenuBar();
 
        //Build the first menu.
        menu = new JMenu("Cluedo");
        menuBar.add(menu);
 
        //a group of JMenuItems
        menuItem1 = new JMenuItem("New Game");
        menuItem2 = new JMenuItem("Quit Game");
        menuItem1.addActionListener(this);
        menuItem2.addActionListener(this);
        menu.add(menuItem1);
        menu.add(menuItem2);

        return menuBar;
    }
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(menuItem1)){
			int n = JOptionPane.showConfirmDialog(
					frame,
					"Are you sure you want to start a new game?\n"
					+ "All progress will be lost", "Are you sure?"
					,JOptionPane.YES_NO_OPTION);
			if(n == 0)
				frame.newCluedoGame();
		}else if(e.getSource().equals(menuItem2)){
			int n = JOptionPane.showConfirmDialog(
					frame,
					"Are you sure you want to quit?\n"
					+ "All progress will be lost", "Are you sure?"
					,JOptionPane.YES_NO_OPTION);
			if(n == 0)
				System.exit(0);
		}
		
		
	}
 
    
}
