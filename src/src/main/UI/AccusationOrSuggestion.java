package src.main.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static src.main.UI.CluedoCanvas.loadImage;
import javax.swing.JFrame;

import src.main.Cluedo.Game;

public class AccusationOrSuggestion extends javax.swing.JPanel implements ActionListener{

	private Game game;
	private JFrame frame;
	private CluedoCanvas canvas;
	private static final Image BLACK_OPACITY = loadImage("blackOpacity.png");
	
	public AccusationOrSuggestion(Game game, JFrame frame, CluedoCanvas canvas){
		super();
		this.game = game;
		this.frame = frame;
		this.canvas = canvas;
		setBackground(new Color(0,0,0,0));
		this.setLayout(null);
	}
	
	
	
	@Override
	public void paintComponents(Graphics g){
		g.drawRect(10, 10, 100, 100);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}