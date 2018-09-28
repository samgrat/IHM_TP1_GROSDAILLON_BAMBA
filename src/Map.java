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
	
	public Map(BorderLayout borderLayout, ArrayList<Home> maisons) {
		super(borderLayout);
		
		listeMaisons = maisons;
	}
	
	@Override
	protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        
        Ellipse2D e;
        
        for(int j = 0; j < listeMaisons.size(); j++)
        {
            e = new Ellipse2D.Double((listeMaisons.get(j)).getX(), (listeMaisons.get(j)).getY(), 1, 1);

            g.setColor(COULEUR_POINTS);
            g2.draw(e);
        }
    }
}
