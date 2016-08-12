package src.main.UI;

import static src.main.UI.CluedoCanvas.loadImage;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;


import src.main.Cluedo.Board;
import src.main.Cluedo.Game;

import src.main.GameObject.Player;
import src.main.Location.Room;

public class CluedoCanvas extends JPanel implements MouseListener, MouseMotionListener{

	private static final String IMAGE_PATH = "images/";
	private static final Image PAGE = loadImage("setupPageHome.png");
	private static final Image DICE = loadImage("rollingDice2.png").getScaledInstance(146, 94, 0);
	private static final Image SELECTED_DICE = loadImage("selectedDice2.png").getScaledInstance(146, 94, 0);
	
	private Game game;
	private Board board;
	
	private int mouseX;
	private int mouseY;
	
	private int[] diceXS = {704,720,750,766,770,807,838,830,794,778,772,739};
	private int[] diceYS = {101,60,55,64,45,35,59,98,111,101,115,119};
	private Polygon dicePolygon = new Polygon(diceXS, diceYS, diceXS.length);
	
	public CluedoCanvas(Game game, Board board, JFrame frame){
		this.game = game;
		this.board = board;
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(board.getWidth(),board.getHeight());
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		}
	
	@Override
	public void paint(Graphics g){
		if(Game.gameState == Game.State.SETUP_MENU
				|| Game.gameState == Game.State.SETUP_PLAYER){
			g.drawImage(PAGE, 0, 0, null);
			
		}else if(Game.gameState == Game.State.RUNNING){
			board.paint(g);
			for(Player p: game.getPlayers())
				p.paint(g);
			if(dicePolygon.contains(new Point(mouseX, mouseY))){
					g.drawImage(SELECTED_DICE, 700, 30, null);
			}else{
				g.drawImage(DICE, 700, 30, null);
			}
		
		
		
		
		
		
		
		
		}
		
		
	}
	
	/**
	 * Load an image from the file system, using a given filename.
	 * 
	 * @param filename
	 * @return
	 */
	public static Image loadImage(String filename) {
		// using the URL means the image loads when stored
		// in a jar or expanded into individual files.
		java.net.URL imageURL = CluedoCanvas.class.getResource(IMAGE_PATH
				+ filename);

		try {
			Image img = ImageIO.read(imageURL);
			return img;
		} catch (IOException e) {
			// we've encountered an error loading the image. There's not much we
			// can actually do at this point, except to abort the game.
			throw new RuntimeException("Unable to load image: " + filename);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
			if(dicePolygon.contains(new Point(e.getX(), e.getY()))){
				int roll = game.rollDice();
			}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("MousePressed");	
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		System.out.println("MouseReleased");	
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		System.out.println("MouseEntered");	
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		System.out.println("MouseExited");
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		System.out.println("MouseDragging");
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		System.out.println(mouseX + ", "  + mouseY);
		repaint();
		
		
	}





}
