import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import javax.swing.plaf.basic.BasicSliderUI;
import javax.swing.plaf.basic.BasicSliderUI.TrackListener;

public class RangeSliderUI extends BasicSliderUI {
	
	private Rectangle curseur_gauche;
	private Rectangle curseur_droit;
	private RangeSlider slider;
	private enum Etats {IDLE, CLIC_PROCHE_GAUCHE, CLIC_PROCHE_DROIT, DRAG_GAUCHE, DRAG_DROIT};
	private Etats etat;
	private JLabel labels;
	private Map map;

	/**
	 * RangeSliderUI constructeur
	 * @param slider le slider
	 * @param labels les labels à evoluer en même temps que le slider
	 */
	public RangeSliderUI(RangeSlider slider, JLabel labels, Map map) {
		super(slider);
		this.curseur_gauche = new Rectangle(0, 0,10,20);
		this.curseur_droit = new Rectangle(slider.getMax(),0,10,20);
		this.slider = slider;
		this.etat = Etats.IDLE;
		this.labels = labels;
		this.map = map;
		
		// TODO Auto-generated constructor stub
	}
	@Override
	public void paint( Graphics g, JComponent c ) {
		//nous plaçons ici dans l'indice x des curseurs la valeur de ces curseur (minRange et maxRange), ce n'est donc pas une coordonnée swing mais bien une valeur.
		this.curseur_gauche.setLocation(this.slider.getMinRange(), this.curseur_gauche.y);
		this.curseur_droit.setLocation(this.slider.getMaxRange(), this.curseur_droit.y);
		super.paint(g,c); //appel de paint du parent pour qu'elle appelle notre méthode paintThumb.
	}
	@Override
	public void paintThumb(Graphics g) {
		/*les rectangles sont déssinés par rapport à la largeur de la barre du slider (tickRect.width)
		on retrouve le x (en coordonnées cette fois ci) en faisant :
		
			( (valeur - min) * tickRect.width ) / ( max - min )
		
		ce qui donne en java :
		
			( (curseur_gauche.x - slider.getMin()) * this.tickRect.width ) / (slider.getMax() - slider.getMin()
		
		avec valeur, la valeur du curseur (minRange ou maxRange) que nous avons placé dans le champ x des variables curseur_droit et curseur_gauche)
		min et max les valeurs minimales et maximales de notre RangeSlider (pas les valeurs des curseurs).
		*/
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(((curseur_gauche.x - slider.getMin())*this.tickRect.width)/(slider.getMax()-slider.getMin()), this.curseur_gauche.y, this.curseur_gauche.width, this.curseur_gauche.height);
		g.fillRect(((curseur_droit.x - slider.getMin())*this.tickRect.width)/(slider.getMax()-slider.getMin()), this.curseur_droit.y, this.curseur_droit.width, this.curseur_droit.height);
	       
	
		//dessin du rectangle bleu entre les deux curseurs.
		g.setColor(Color.cyan);
		g.fillRect(((curseur_gauche.x - slider.getMin())*this.tickRect.width)/(slider.getMax()-slider.getMin())+10, this.curseur_gauche.y+7, ((curseur_droit.x - slider.getMin())*this.tickRect.width)/(slider.getMax()-slider.getMin())-((curseur_gauche.x - slider.getMin())*this.tickRect.width)/(slider.getMax()-slider.getMin())-10, this.curseur_gauche.height/3);
	       
	}
	
	@Override
	protected TrackListener createTrackListener(JSlider slider) {
        return new ClicEventListener();
    }

	
	
	
	private class ClicEventListener extends TrackListener {
		@Override
		public void mousePressed(MouseEvent e) {
			switch(etat) {
			case IDLE:
				//ce "if" permet de déterminer quel curseur est le plus près de la position de notre souris pour ne changer que la valeur du curseur le plus proche de notre pointeur de souris.
				if (Math.abs(((e.getX()*(slider.getMax()-slider.getMin()))/tickRect.width + slider.getMin())-curseur_gauche.getX()) < Math.abs(((e.getX()*(slider.getMax()-slider.getMin()))/tickRect.width + slider.getMin())-curseur_droit.getX())) {
					etat = Etats.CLIC_PROCHE_GAUCHE;
					/*
					  ici, nous calculons une valeur pour le curseur le plus proche de la souris dans notre interval entre min et max du RangeSlider.
					  
					  pour cela, la formule est :
					  
					  	position_souris_X * (max - min) / tickRect.width + min
					  
					  ce qui donne en java : 
					  
					  	e.getX() * (slider.getMax() - slider.getMin())) / tickRect.width + slider.getMin()
					  
					  Cette formule à été calculée à partir de la formule définie dans la méthode paintThumb.
					  
					  min et max sont les valeurs minimales et maximales de notre RangeSlider (pas les valeurs des curseurs).

					  tickRect.width est la largeur de la barre du slider 
					 */
					
					//on vérifie que l'on peut déplacer le curseur a cet endroit (pas en dehors de la fenetre et on ne chevauche pas l'autre curseur) avant d'affecter la range a notre RangeSlider.
					if (((e.getX()*(slider.getMax()-slider.getMin()))/tickRect.width + slider.getMin()) < slider.getMaxRange() && ((e.getX()*(slider.getMax()-slider.getMin()))/tickRect.width + slider.getMin()) >= slider.getMin()) {
						slider.setMinRange((e.getX()*(slider.getMax()-slider.getMin()))/tickRect.width + slider.getMin());
						labels.setText("min range : " + slider.getMinRange() + " - max range : " + slider.getMaxRange());
						slider.repaint();
						map.repaint();
					}
				//c'est l'autre curseur qui était plus près.		
				} else {
					etat = Etats.CLIC_PROCHE_DROIT;
					//on vérifie que l'on peut déplacer le curseur a cet endroit (pas en dehors de la fenetre et on ne chevauche pas l'autre curseur) avant d'affecter la range a notre RangeSlider.
					if (((e.getX()*(slider.getMax()-slider.getMin()))/tickRect.width + slider.getMin()) > slider.getMinRange() && ((e.getX()*(slider.getMax()-slider.getMin()))/tickRect.width + slider.getMin()) <= slider.getMax()) {
						slider.setMaxRange((e.getX()*(slider.getMax()-slider.getMin()))/tickRect.width + slider.getMin());
						labels.setText("min range : " + slider.getMinRange() + " - max range : " + slider.getMaxRange());
						slider.repaint();
						map.repaint();
					}

				}
				break;
			case CLIC_PROCHE_GAUCHE:
				//on vérifie que l'on peut déplacer le curseur a cet endroit (pas en dehors de la fenetre et on ne chevauche pas l'autre curseur) avant d'affecter la range a notre RangeSlider.
				if (((e.getX()*(slider.getMax()-slider.getMin()))/tickRect.width + slider.getMin()) < slider.getMaxRange() && ((e.getX()*(slider.getMax()-slider.getMin()))/tickRect.width + slider.getMin()) >= slider.getMin()) {
					slider.setMinRange((e.getX()*(slider.getMax()-slider.getMin()))/tickRect.width + slider.getMin());
					labels.setText("min range : " + slider.getMinRange() + " - max range : " + slider.getMaxRange());
					slider.repaint();
					map.repaint();
				}
				break;
			case CLIC_PROCHE_DROIT:
				//on vérifie que l'on peut déplacer le curseur a cet endroit (pas en dehors de la fenetre et on ne chevauche pas l'autre curseur) avant d'affecter la range a notre RangeSlider.
				if (((e.getX()*(slider.getMax()-slider.getMin()))/tickRect.width + slider.getMin()) > slider.getMinRange() && ((e.getX()*(slider.getMax()-slider.getMin()))/tickRect.width + slider.getMin()) <= slider.getMax()) {
					slider.setMaxRange((e.getX()*(slider.getMax()-slider.getMin()))/tickRect.width + slider.getMin());
					labels.setText("min range : " + slider.getMinRange() + " - max range : " + slider.getMaxRange());
					slider.repaint();
					map.repaint();
				}
				break;
			}
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			switch(etat) {
			case CLIC_PROCHE_GAUCHE:
				etat = Etats.DRAG_GAUCHE;
				//on vérifie que l'on peut déplacer le curseur a cet endroit (pas en dehors de la fenetre et on ne chevauche pas l'autre curseur) avant d'affecter la range a notre RangeSlider.
				if (((e.getX()*(slider.getMax()-slider.getMin()))/tickRect.width + slider.getMin()) < slider.getMaxRange() && ((e.getX()*(slider.getMax()-slider.getMin()))/tickRect.width + slider.getMin()) >= slider.getMin()) {
					slider.setMinRange((e.getX()*(slider.getMax()-slider.getMin()))/tickRect.width + slider.getMin());
					labels.setText("min range : " + slider.getMinRange() + " - max range : " + slider.getMaxRange());
					slider.repaint();
					map.repaint();
				}
				break;
				
			case CLIC_PROCHE_DROIT:
				etat = Etats.DRAG_DROIT;
				//on vérifie que l'on peut déplacer le curseur a cet endroit (pas en dehors de la fenetre et on ne chevauche pas l'autre curseur) avant d'affecter la range a notre RangeSlider.
				if (((e.getX()*(slider.getMax()-slider.getMin()))/tickRect.width + slider.getMin()) > slider.getMinRange() && ((e.getX()*(slider.getMax()-slider.getMin()))/tickRect.width + slider.getMin()) <= slider.getMax()) {
					slider.setMaxRange((e.getX()*(slider.getMax()-slider.getMin()))/tickRect.width + slider.getMin());
					labels.setText("min range : " + slider.getMinRange() + " - max range : " + slider.getMaxRange());
					slider.repaint();
					map.repaint();
				}
				break;
				
			case DRAG_GAUCHE:
				etat = Etats.DRAG_GAUCHE;
				//on vérifie que l'on peut déplacer le curseur a cet endroit (pas en dehors de la fenetre et on ne chevauche pas l'autre curseur) avant d'affecter la range a notre RangeSlider.
				if (((e.getX()*(slider.getMax()-slider.getMin()))/tickRect.width + slider.getMin()) < slider.getMaxRange() && ((e.getX()*(slider.getMax()-slider.getMin()))/tickRect.width + slider.getMin()) >= slider.getMin()) {
					slider.setMinRange((e.getX()*(slider.getMax()-slider.getMin()))/tickRect.width + slider.getMin());
					labels.setText("min range : " + slider.getMinRange() + " - max range : " + slider.getMaxRange());
					slider.repaint();
					map.repaint();
				}
				break;
				
			case DRAG_DROIT:
				etat = Etats.DRAG_DROIT;
				//on vérifie que l'on peut déplacer le curseur a cet endroit (pas en dehors de la fenetre et on ne chevauche pas l'autre curseur) avant d'affecter la range a notre RangeSlider.
				if (((e.getX()*(slider.getMax()-slider.getMin()))/tickRect.width + slider.getMin()) > slider.getMinRange() && ((e.getX()*(slider.getMax()-slider.getMin()))/tickRect.width + slider.getMin()) <= slider.getMax()) {
					slider.setMaxRange((e.getX()*(slider.getMax()-slider.getMin()))/tickRect.width + slider.getMin());
					labels.setText("min range : " + slider.getMinRange() + " - max range : " + slider.getMaxRange());
					slider.repaint();
					map.repaint();
				}
				break;
			}

		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			etat = Etats.IDLE;
		}
	}

}
