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
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ArrayList<Home> listeMaisons;
	private RangeSlider slider_pieces;
	private RangeSlider slider_prix;
	
	
	// Quelques constantes graphiques
	private static final Color COULEUR_POINTS = Color.yellow;
	private static final Color COULEUR_FOND = Color.black;
	
	public Map( ArrayList<Home> maisons, RangeSlider nombre_de_pieces, RangeSlider valeur_maison) {
		//super(borderLayout);
		//setBackground(COULEUR_FOND);
		listeMaisons = maisons;
		setBounds(0,0,100,100);
		this.slider_pieces = nombre_de_pieces;
		this.slider_prix = valeur_maison;
	}
	
	@Override
	protected void paintComponent(Graphics g)
    {
		g.setColor(COULEUR_FOND);
        g.fillRect(0, 0, 400, 400);
        System.out.println("heeey");
       Graphics2D g2 = (Graphics2D)g;
        
        Ellipse2D e;
        
        for(int j = 0; j < listeMaisons.size(); j++)
        {
        	if (listeMaisons.get(j).getPieces() >= slider_pieces.getMinRange() && 
        			listeMaisons.get(j).getPieces() <= slider_pieces.getMaxRange() &&
        			listeMaisons.get(j).getValeur() >= slider_prix.getMinRange() &&
        			listeMaisons.get(j).getValeur() <= slider_prix.getMaxRange()) {
                e = new Ellipse2D.Double((listeMaisons.get(j)).getX(), (listeMaisons.get(j)).getY(), 1, 1);
                g.setColor(COULEUR_POINTS);
                g2.draw(e);
                
        	}

        }

    }
}
