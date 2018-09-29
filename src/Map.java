import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.MouseInputAdapter;
import javax.swing.plaf.basic.BasicSliderUI.TrackListener;

// https://coderanch.com/t/338737/java/draw-points-Java
public class Map extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ArrayList<Home> listeMaisons;
	private RangeSlider slider_pieces;
	private RangeSlider slider_prix;
	private JSlider slider_rayonA;
	private JSlider slider_rayonB;

	private Ellipse2D point_A;
	private Ellipse2D point_B;

	private enum Etats {
		IDLE, PLACE_A, PAUSE, PLACE_B
	};

	private Etats etat;

	// Quelques constantes graphiques
	private static final int TAILLE_A_B = 4;
	private static final int TAILLE_CARTE = 400;
	private static final Color COULEUR_A = Color.red;
	private static final Color COULEUR_B = Color.blue;
	private static final Color COULEUR_POINTS = Color.yellow;
	private static final Color COULEUR_FOND = Color.black;

	public Map(ArrayList<Home> maisons, RangeSlider nombre_de_pieces, RangeSlider valeur_maison, JSlider slider_rayonA, JSlider slider_rayonB) {
		// super();
		listeMaisons = maisons;
		this.slider_pieces = nombre_de_pieces;
		this.slider_prix = valeur_maison;
		this.slider_rayonA = slider_rayonA;
		this.slider_rayonB = slider_rayonB;

		this.etat = Etats.IDLE;
		this.point_A = null;
		this.point_B = null;

		// On ajoute notre Mouse listener
		addMouseListener(new ClicEventListener());
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(COULEUR_FOND);
		g.fillRect(0, 0, TAILLE_CARTE, TAILLE_CARTE);
		Graphics2D g2 = (Graphics2D) g;
		Home maison;
		// On initialise les distance au minimum
		double distanceA = 0;
		double distanceB = 0;

		Ellipse2D e;

		// On dessine A et B si ils existent
		if (point_A != null) {
			g.setColor(COULEUR_A);
			g2.draw(point_A);
		}

		if (point_B != null) {
			g.setColor(COULEUR_B);
			g2.draw(point_B);
		}

		for (int j = 0; j < listeMaisons.size(); j++) {
			maison = listeMaisons.get(j);
			
			// On calcule les distance si A et B existent
			if (point_A != null) {
				distanceA =DistanceElipses(maison.getX(), maison.getY(), point_A);
			}
			if (point_B != null) {
				distanceB =DistanceElipses(maison.getX(), maison.getY(), point_B);
			}

			// On dessine les maisons si elles ont un nombre de pieces, un prix, un distance par rapport a A et a B valide
			if (maison.getPieces() >= slider_pieces.getMinRange()
					&& maison.getPieces() <= slider_pieces.getMaxRange()
					&& maison.getValeur() >= slider_prix.getMinRange()
					&& maison.getValeur() <= slider_prix.getMaxRange()
					&& (distanceA  <= slider_rayonA.getValue() || distanceB  <= slider_rayonB.getValue())) {
				
				e = new Ellipse2D.Double((maison).getX(), (maison).getY(), 1, 1);
				g.setColor(COULEUR_POINTS);
				g2.draw(e);

			}

		}

	}
	
	/**
	 * Calcule la distance entre deux coordonnee et un elipse
	 * @param x
	 * @param y
	 * @param e2
	 * @return
	 */
	private double DistanceElipses(double x, double y, Ellipse2D e2) {
		
		return Math.sqrt((e2.getX() - x) * (e2.getX() - x) + (e2.getY() - y)*(e2.getY() - y));
	}

	private class ExceptionEtat extends Exception {
		/**
		* 
		*/
		private static final long serialVersionUID = 1L;

		public ExceptionEtat(MouseEvent e) {
			System.err.println("Evenement non capturé, etat courant = " + etat + " event = " + e);
		}

	}

	private class ClicEventListener extends MouseInputAdapter {

		// Machine a etat pour le placement successif des points A et B
		//
		// |IDLE| -mousePressed-> |PLACE_A @(mouseDragged)| -mouseReleased-------
		// /\ |
		// L-mouseReleased- |PLACE_B @(mouseDragged)| <-mousePressed- |PAUSE|<

		@Override
		public void mousePressed(MouseEvent e) {
			System.out.println("Pressed etat = " + etat);
			switch (etat) {
			case IDLE:
				if (e.getX() <= TAILLE_CARTE && e.getY() <= TAILLE_CARTE) {
					point_A = new Ellipse2D.Double(e.getX(), e.getY(), TAILLE_A_B, TAILLE_A_B);
					repaint();
					etat = Etats.PLACE_A;
				} else {
					System.err.println("Clic hors carte");
				}
				break;
			case PAUSE:
				if (e.getX() <= TAILLE_CARTE && e.getY() <= TAILLE_CARTE) {
					point_B = new Ellipse2D.Double(e.getX(), e.getY(), TAILLE_A_B, TAILLE_A_B);
					repaint();
					etat = Etats.PLACE_B;
				} else {
					System.err.println("Clic hors carte");
				}
				break;
			default:
				new ExceptionEtat(e);
			}
		}

		// TODO Debug n'est jamais enclenché
		@Override
		public void mouseDragged(MouseEvent e) {
			System.out.println("Dragged etat = " + etat);
			switch (etat) {
			case PLACE_A:
				etat = Etats.PLACE_A;
				break;
			case PLACE_B:
				etat = Etats.PLACE_B;
				break;
			default:
				new ExceptionEtat(e);
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			System.out.println("Released etat = " + etat);
			switch (etat) {
			case PLACE_A:
				etat = Etats.PAUSE;
				break;
			case PLACE_B:
				etat = Etats.IDLE;
				break;
			default:
				new ExceptionEtat(e);
			}
		}
	}
}
