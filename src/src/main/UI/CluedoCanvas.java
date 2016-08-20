package src.main.UI;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.List;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import src.main.Location.*;
import src.main.Cards.Card;
import src.main.Cluedo.Board;
import src.main.Cluedo.Game;
import src.main.GameObject.Basement;
import src.main.GameObject.Player;
import src.main.GameObject.Weapon;

public class CluedoCanvas extends JPanel implements MouseListener, MouseMotionListener{

	private static final String IMAGE_PATH = "images/";
	private static final Image PAGE = loadImage("setupPageHome.png");
	private static final Image BLACK_OPACITY = loadImage("blackOpacity.png");
	
	private Game game;
	private Board board;
	private JFrame frame;
	
	private int mouseX;
	private int mouseY;
	
	private AccusationOrSuggestion AOS;
	private Dice dice;
	private Card showCard;
	private int accOrSugg;
	private BoardButtons boardButtons;
	private JPanel boardButtons2;
	
	private List<Tile> moveableLocations;
	private Tile selectedTile;
	
	public CluedoCanvas(Game game, Board board, JFrame frame){
		this.game = game;
		this.board = board;
		this.frame = frame;
		setLayout(null);
		setLocation(0,0);
		moveableLocations = new ArrayList<>();
		addMouseListener(this);
		addMouseMotionListener(this);
		dice = new Dice(game, this);
	//	boardButtons = new BoardButtons(game, this, frame);
		BoardButtons2 b2 = new BoardButtons2(game, this);
		boardButtons2 = b2.createPanel();
		add(boardButtons2);
		setVisible(true);
		setOpaque(false);
	}
	
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(board.getWidth(),board.getHeight());
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		}
	public void accusation(Boolean acc){
		setLayout(null);
		if(acc)
			accOrSugg = 1;
		else
			accOrSugg = 3;
		AOS = new AccusationOrSuggestion(game, this);
		JPanel room = AOS.askMurderRoom(acc);
		JPanel weapon = AOS.askMurderWeapon();
		JPanel character = AOS.askMurderCharacter();
		JPanel done = AOS.done();
		add(room);
		add(weapon);
		add(character);
		add(done);
		revalidate();
		repaint();
	}
	
	
	
	
	
	
	@Override
	public void paint(Graphics g){
		
		if(Game.gameState == Game.State.SETUP_MENU
				|| Game.gameState == Game.State.SETUP_PLAYER){
			g.drawImage(PAGE, 0, 0, null);
			
		}else if(Game.gameState == Game.State.RUNNING){
			if(showCard != null || accOrSugg > 0)
				board.paint(g, moveableLocations, null);
			else
				board.paint(g, moveableLocations, new Point(mouseX, mouseY));
			
			for(Player p: game.getPlayers())
				p.paint(g);
			for(Player p: game.getPlayersOut())
				p.paint(g);
			
			dice.paint(g);
//			boardButtons.paint(g);
//			boardButtons2.paint(g);
			g.setColor(Color.white);
			Player p = game.getCurrentPlayer();
			if(p == null){
				int i = 1;
			}
			g.drawImage(p.getImage(), 590, 6,null);
			g.drawString(p.getName().toString(), 800, 20);
			g.drawString("It is your Turn", 800, 40);
			for(Room r: game.getRooms())
				r.paint(g, null, null);
			if(showCard != null || accOrSugg > 0){
				p.paintHand(g, new Point(0,0));
			}else{
				p.paintHand(g, new Point(mouseX, mouseY));
			}
			if(showCard != null){
				g.drawImage(BLACK_OPACITY, 0, 0, 990, 590, null);
				g.drawImage(showCard.getImage(0),315, 33, null);
			}
			if(showCard == null && accOrSugg == 0){
					for(Player player: game.getPlayers()){
			
						Rectangle r = new Rectangle(player.getXPos(), player.getYPos(),19, 19);
						if(r.contains(mouseX, mouseY)){
							player.paintTag(g);
						}
					}
				for(Weapon weapon: game.getWeapons()){
					Rectangle r = new Rectangle(weapon.getXPos(), weapon.getYPos(),19, 19);
					if(r.contains(mouseX, mouseY)){
						weapon.paintTag(g);
					}
				}
			}
			if(accOrSugg == 1){
				g.drawImage(BLACK_OPACITY, 0, 0, 990, 590, null);
				g.setColor(Color.white);
				g.setFont(new Font("Calibri", Font.PLAIN, 30));
				g.drawString("Choose a Room", 95, 180);
				g.drawString("Choose a Weapon", 390, 180);
				g.drawString("Choose a Suspect", 690, 180);
				g.setFont(new Font("Calibri", Font.PLAIN, 10));
				g.setColor(Color.black);
			}
			if(accOrSugg == 2){
				Basement b = game.getBasement();
				g.drawImage(BLACK_OPACITY, 0, 0, 990, 590, null);
				g.setColor(Color.white);
				g.setFont(new Font("Calibri", Font.PLAIN, 40));
				g.drawString("You Lose", 440, 180);
				Font font = new Font("Calibri", Font.PLAIN, 20);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setFont(font);
				String s = "Correct Solution Was " +Game.getCharacterName(b.getMurderCharacter()) + ", with the " + b.getMurderWeapon().getName() + ", in the " + b.getMurderRoom().getName();
				Rectangle2D r = font.getStringBounds(s, g2d.getFontRenderContext());
				g.drawString(s, 495 - (int) r.getWidth()/2, 220);
				g.setFont(new Font("Calibri", Font.PLAIN, 10));
				g.setColor(Color.black);
			}
			if(accOrSugg == 3){
				g.drawImage(BLACK_OPACITY, 0, 0, 990, 590, null);
				g.setColor(Color.white);
				g.setFont(new Font("Calibri", Font.PLAIN, 30));
				g.drawString("Choose a Room", 95, 180);
				g.drawString("Choose a Weapon", 390, 180);
				g.drawString("Choose a Suspect", 690, 180);
				g.setFont(new Font("Calibri", Font.PLAIN, 10));
				g.setColor(Color.black);
			}
			if(accOrSugg == 4){
				g.drawImage(BLACK_OPACITY, 0, 0, 990, 590, null);
				g.setColor(Color.white);
				g.setFont(new Font("Calibri", Font.PLAIN, 40));
				String s = null; //AOS.getRefuteMessage();
				if(s != null)
					g.drawString("Suggestion Refuted", 360, 180);
				else
					g.drawString("Congratulations, Suggestion was not Refuted", 300, 180);
				Font font = new Font("Calibri", Font.PLAIN, 20);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setFont(font);
				Rectangle2D r = font.getStringBounds(s, g2d.getFontRenderContext());
				g.drawString(s, 495 - (int) r.getWidth()/2, 220);
				g.setFont(new Font("Calibri", Font.PLAIN, 10));
				g.setColor(Color.black);
			}
			if(accOrSugg == 5){
				Basement b = game.getBasement();
				g.drawImage(BLACK_OPACITY, 0, 0, 990, 590, null);
				g.setColor(Color.white);
				g.setFont(new Font("Calibri", Font.PLAIN, 40));
				g.drawString("Congratulations, You Win", 315, 180);
				Font font = new Font("Calibri", Font.PLAIN, 20);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setFont(font);
				String s = "Correct Solution Was " +Game.getCharacterName(b.getMurderCharacter()) + ", with the " + b.getMurderWeapon().getName() + ", in the " + b.getMurderRoom().getName();
				Rectangle2D r = font.getStringBounds(s, g2d.getFontRenderContext());
				g.drawString(s, 495 - (int) r.getWidth()/2, 220);
				g.setFont(new Font("Calibri", Font.PLAIN, 10));
				g.setColor(Color.black);
				Game.gameState = Game.State.OVER;
			}
			super.paint(g);
		
		
		
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
		
		if(showCard != null){
			Rectangle r = new Rectangle(315, 33, 360, 524);
			if(!r.contains(mouseX, mouseY)){
				showCard = null;
			}
			
			
		}else{
		
		Player player = game.getCurrentPlayer();
	
			if(dice.contains(new Point(getMouseX(), getMouseY()))){
				
				int roll = dice.diceClicked();
				moveableLocations = game.determineMoveLocations(player, roll);
			}else if(!moveableLocations.isEmpty()){
				for(Tile t: game.getTiles()){
					if(t != null){
						if(t.contains(getMousePosition())){
							if(moveableLocations.contains(t)){
								player.move(selectedTile);
								moveableLocations.clear();;
							}
						}
					}	
				}
			}else if(player.getSelectedCard() != null){
				Card c = player.getSelectedCard();
				showCard = c;
			}else{
				//String button = boardButtons.contains(new Point(getMouseX(), getMouseY()));
				//if(button != null){
				//	boardButtons.buttonClicked(button);
			//	}
				
			}
		}
		
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//String button = boardButtons.contains(new Point(getMouseX(), getMouseY()));
		//if(button != null)
		//	boardButtons.buttonPressed(button);
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//String button = boardButtons.contains(new Point(getMouseX(), getMouseY()));
		//if(button != null)
		//	boardButtons.buttonReleased(button);
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		selectedTile = null;
		for(Tile t: game.getTiles()){
			if(t != null)
				if(t.contains(new Point(mouseX, mouseY)))
					selectedTile = t;	
		}
					
		//System.out.println(mouseX + ", "  + mouseY);
		repaint();
	}

	public void resetDice(){
		dice.resetDice();
	}
	
	public int getMouseX(){
		return mouseX;
	}
	
	public int getMouseY(){
		return mouseY;
	}

	public Dice getDice(){
		return dice;
	}
	
	public List<Tile> getMoveableLocations(){
		return moveableLocations;
	}
	
	public Tile getSelectedTile(){
		return selectedTile;
	}
	
	public JPanel getBoardButtons(){
		return boardButtons2;
	}

	public Card getShowCard(){
		return showCard;
	}

	public int getAccOrSugg() {
		return accOrSugg;		
	}
	
	public void setAccOrSugg(int i){
		accOrSugg = i;
	}


}
