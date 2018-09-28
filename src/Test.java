import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;

public class Test {
	// Nombre de maisons devant être initilisées
	public static final int M = 10;
	// Quelques bornes
	public static final float X_MAX = 100;
	public static final float Y_MAX = 100;
	public static final int PIECES_MIN = 1;
	public static final int PIECES_MAX = 10;
	public static final int VALEUR_MIN = 10000;
	public static final int VALEUR_MAX = 1000000;	

	// Notre liste de home aleatoires
	public static ArrayList<Home> listeMaisons = new ArrayList<Home>();
	
	public static RangeSlider piecesSlider = new RangeSlider(PIECES_MIN, PIECES_MAX);
	public static RangeSlider valeurSlider = new RangeSlider(VALEUR_MIN, VALEUR_MAX);
	
	public static JLabel pieces_min_max = new JLabel("min : " + piecesSlider.getMin() + " - max : " + piecesSlider.getMax(), SwingConstants.CENTER);
	public static JLabel pieces_slider_values = new JLabel("min range : " + piecesSlider.getMinRange() + " - max range : " + piecesSlider.getMaxRange(), SwingConstants.CENTER);
	public static JLabel valeur_min_max = new JLabel("min : " + valeurSlider.getMin() + " - max : " + valeurSlider.getMax(), SwingConstants.CENTER);
	public static JLabel valeur_slider_values = new JLabel("min range : " + valeurSlider.getMinRange() + " - max range : " + valeurSlider.getMaxRange(), SwingConstants.CENTER);
	
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			
			public void run(){
				
				JFrame app = new JFrame();
				BorderLayout all = new BorderLayout();
				app.setLayout(all);
				
				// On commence par remplir notre liste avec des maisons aleatoires
				for(int i = 0; i < M; i++) {
					ajouteMaison(listeMaisons);
				}
				
				//On crée une nouvelle instance de notre JDialog				
				piecesSlider.setUI(new RangeSliderUI(piecesSlider, pieces_slider_values));
				valeurSlider.setUI(new RangeSliderUI(valeurSlider, valeur_slider_values));		
				
				JPanel pieces_labels = new JPanel(new BorderLayout());
				pieces_labels.add(pieces_min_max,BorderLayout.NORTH);	
				pieces_labels.add(pieces_slider_values,BorderLayout.SOUTH);
				
				JPanel valeur_labels = new JPanel(new BorderLayout());
				valeur_labels.add(valeur_min_max,BorderLayout.NORTH);	
				valeur_labels.add(valeur_slider_values,BorderLayout.SOUTH);
				
				// On creee des JPanel a part pour nos RangeSlider
				JPanel slider = new JPanel(new BorderLayout());
				slider.add(pieces_labels, BorderLayout.NORTH);
				slider.add(piecesSlider, BorderLayout.CENTER);
				
				JPanel slider2 = new JPanel(new BorderLayout());
				slider2.add(valeur_labels, BorderLayout.NORTH);
				slider2.add(valeurSlider, BorderLayout.CENTER);
				
				// Puis un autre JPanel les englobant
				JPanel controls = new JPanel(new BorderLayout());
				controls.add(slider, BorderLayout.NORTH);
				controls.add(slider2, BorderLayout.CENTER);
				
				// Ensuite on cree notre carte TODO
				Map map = new Map(new BorderLayout(), listeMaisons);
				
				
				// On organise nos composants
				app.add(controls,BorderLayout.EAST);
				app.add(map,BorderLayout.WEST);
				
				app.pack(); //la taille de la fenetre est définie en fonction des composants à l'intérieur.
				app.setVisible(true);
				

			}
		});
	}

	/**
	 * Génère une maison aleatoire et l'ajoute a la liste
	 * @param liste la liste a completer
	 */
	protected static void ajouteMaison(ArrayList<Home> liste) {
		float x;
		float y;
		int pieces;
		int valeur;
		Random r = new Random();
		
		x = r.nextFloat()*X_MAX;
		y = r.nextFloat()*Y_MAX;
		pieces = r.nextInt(PIECES_MAX-PIECES_MIN) + PIECES_MIN;
		valeur = r.nextInt(VALEUR_MAX-VALEUR_MIN) + VALEUR_MIN;
		
		//System.out.println("Ajout Maison("+x+", "+y+", "+pieces+", "+valeur+")");
		Home maison = new Home(x,y,pieces,valeur);
		
		liste.add(maison);
	}
}
