package src.main.UI;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;

import src.main.Cluedo.Game;

import static src.main.UI.CluedoCanvas.loadImage;

public class Dice {

	private static final Image DICE = loadImage("rollingDice2.png").getScaledInstance(146, 94, 0);
	private static final Image SELECTED_DICE = loadImage("selectedDice2.png").getScaledInstance(146, 94, 0);
	
	private static final Image FACE1 = loadImage("diceface1.png").getScaledInstance(146, 94, 0);
	private static final Image FACE2 = loadImage("diceface2.png").getScaledInstance(146, 94, 0);
	private static final Image FACE3 = loadImage("diceface3.png").getScaledInstance(146, 94, 0);
	private static final Image FACE4 = loadImage("diceface4.png").getScaledInstance(146, 94, 0);
	private static final Image FACE5 = loadImage("diceface5.png").getScaledInstance(146, 94, 0);
	private static final Image FACE6 = loadImage("diceface6.png").getScaledInstance(146, 94, 0);
	
	private int roll;
	
	private int x = 700;
	private int y = 30;
	
	private int[] diceXS = {x+4,x+20,x+50,x+66,x+70,x+07,x+138,x+130,x+94,x+78,x+72,x+39};
	private int[] diceYS = {y+71,y+30,y+25,y+34,y+15,y+5,y+29,y+68,y+81,y+71,y+85,y+89};
	private Polygon dicePolygon = new Polygon(diceXS, diceYS, diceXS.length);
	
	private CluedoCanvas canvas;
	private Game game;
	
	public Dice(Game game, CluedoCanvas canvas){
		this.canvas = canvas;
		this.game = game;
		this.roll = 0;
	}

	
	public void paint(Graphics g){
		if(roll == 0){
			Point p = canvas.getMousePosition();
			if(p != null){
				if(dicePolygon.contains(canvas.getMousePosition())){
					g.drawImage(SELECTED_DICE, x, y, null);
				}else{
					g.drawImage(DICE, x, y, null);
				}
			}
		}else{
				g.drawImage(getDiceFace(roll), x, y, null);
			
		}
	}
	
	public int diceClicked(){
		if(roll == 0){
			roll = game.rollDice();
		}
		return roll;
	}
	
	public Image getDiceFace(int i){
		switch(i){
			case 1: return FACE1;
			case 2: return FACE2;
			case 3: return FACE3;
			case 4: return FACE4;
			case 5: return FACE5;
			case 6: return FACE6;
		}
		return null;
	}
	public boolean contains(Point p){
		return dicePolygon.contains(p);
	}
	

}
