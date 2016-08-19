package src.main.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class BoardButtons2 extends JPanel implements ActionListener {

	private JButton suggestion;
	private JButton accusation;
	private JButton finishTurn;
	private JButton useStairway;
	
	private CluedoCanvas canvas;
	
	public BoardButtons2(CluedoCanvas canvas){
		setSize(400,10);
		setLocation(590, 570);
		canvas.add(this);
		canvas.revalidate();
		canvas.repaint();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
