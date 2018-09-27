import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.JPanel;

// https://coderanch.com/t/338737/java/draw-points-Java
public class Map extends JPanel {
	
	private ArrayList<Home> listeMaisons;
	
	// Quelques constantes graphiques
	private static final Color COULEUR_POINTS = Color.yellow;
	private static final Color COULEUR_FOND = Color.black;
	
	public JPanel affiche(){
        this.setBounds(10,10,100,100);
        this.setBackground(COULEUR_FOND);
        return this;
    }
 
    public  void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(COULEUR_POINTS);
        g.drawRect(10,10,200,200);
 
    }
	
	public Map(BorderLayout borderLayout, ArrayList<Home> maisons) {
		super(borderLayout);
		
		listeMaisons = maisons;
//		setBounds(0, 0, 100, 100);
//		setBackground(COULEUR_FOND);
	}
//	
//	
//	protected void paintComponent(Graphics g)
//    {
//        super.paintComponent(g);
//        Graphics2D g2 = (Graphics2D)g;
//        //g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
//        Ellipse2D e;
//        
//        for(int j = 0; j < listeMaisons.size(); j++)
//        {
//            e = new Ellipse2D.Double((listeMaisons.get(j)).getX(), (listeMaisons.get(j)).getY(), 1, 1);
//            //g2.setPaint(COULEUR_POINTS);
//            //g2.fill(e);
//            g.setColor(COULEUR_POINTS);
//            g2.draw(e);
//        }
//    }
}
