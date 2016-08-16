package src.main.UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;

import src.main.Cluedo.Game;

import static src.main.UI.CluedoCanvas.loadImage;

public class BoardButtons {

	
	private static final Image ACCUSATION1 = loadImage("Picture1.png").getScaledInstance(117, 40, 0);
	private static final Image SUGGESTION1 = loadImage("Picture2.png").getScaledInstance(136, 47, 0);
	private static final Image FINISH_TURN1 =loadImage("Picture3.png").getScaledInstance(108, 37, 0);
	private static final Image ACCUSATION2 = loadImage("Picture4.png").getScaledInstance(117, 40, 0);
	private static final Image SUGGESTION2 = loadImage("Picture5.png").getScaledInstance(136, 47, 0);
	private static final Image FINISH_TURN2 = loadImage("Picture6.png").getScaledInstance(108, 37, 0);
	
	
	private int accX = 600, accY = 540;
	private int sugX = 720, sugY = 536;
	private int finX = 860, finY = 540;
	
	private boolean accPressed = false;
	private boolean sugPressed = false;
	private boolean finPressed = false;
	
	private int[] accXS = {605,708,708,605};
	private int[] accYS = {544,544,577,577};
	private int[] sugXS = {729,849,849,729};
	private int[] sugYS = {544,544,577,577};
	private int[] finXS = {864,961,961,864};
	private int[] finYS = {544,544,577,577};
	Polygon acc = new Polygon(accXS, accYS, accXS.length);
	Polygon sug = new Polygon(sugXS, sugYS, sugXS.length);
	Polygon fin = new Polygon(finXS, finYS, finXS.length);
	
	private CluedoCanvas canvas;
	private Game game;
	
	public BoardButtons(Game game, CluedoCanvas canvas){
		this.canvas = canvas;
		this.game = game;
	}

	
	public void paint(Graphics g){
		if(accPressed)
			g.drawImage(ACCUSATION2, accX, accY, null);
		else
			g.drawImage(ACCUSATION1, accX, accY, null);
		if(sugPressed)
			g.drawImage(SUGGESTION2, sugX, sugY, null);
		else
			g.drawImage(SUGGESTION1, sugX, sugY, null);
		if(finPressed)
			g.drawImage(FINISH_TURN2, finX, finY, null);
		else
			g.drawImage(FINISH_TURN1, finX, finY, null);
	}
	
	public void buttonClicked(String button){
		if(button.equals("acc"))
			System.out.println("make an accusation");
		if(button.equals("sug"))
			System.out.println("make an suggestion");
		if(button.equals("fin")){
			game.setCurrentPlayer(game.nextTurn());
			canvas.resetDice();
		}
		canvas.repaint();
	}
	
	public void buttonPressed(String button){
		if(button.equals("acc"))
			accPressed = true;
		if(button.equals("sug"))
			sugPressed = true;
		if(button.equals("fin")){
			finPressed = true;
		}
		canvas.repaint();
	}
	
	public void buttonReleased(String button){
		if(button.equals("acc"))
			accPressed = false;
		if(button.equals("sug"))
			sugPressed = false;
		if(button.equals("fin")){
			finPressed = false;
		}
		canvas.repaint();
	}
	
	
	public String contains(Point p){
		if(acc.contains(p))
			return "acc";
		if(sug.contains(p))
			return "sug";
		if(fin.contains(p))
			return "fin";
		return null;
	}
	
}
	