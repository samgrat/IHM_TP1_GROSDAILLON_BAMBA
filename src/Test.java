import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;

public class Test {
	public static RangeSlider s = new RangeSlider(-200, -50);
	public static JLabel min_max = new JLabel("min : " + s.getMin() + " - max : " + s.getMax(), SwingConstants.CENTER);
	public static JLabel slider_values = new JLabel("min range : " + s.getMinRange() + " - max range : " + s.getMaxRange(), SwingConstants.CENTER);
	
	// Notre liste de home aleatoires
	public static ArrayList<Home> listeMaisons = new ArrayList<Home>();
	// Nombre de maisons devant être initilisées
	public static final int M = 10;
	// Quelques bornes pour la génération aleatoire
	public static final float X_MAX = 100;
	public static final float Y_MAX = 100;
	public static final int PIECES_MIN = 1;
	public static final int PIECES_MAX = 10;
	public static final int VALEUR_MIN = 10000;
	public static final int VALEUR_MAX = 1000000;
	
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			
			public void run(){
				
				// On commence par remplir notre liste avec des maisons aleatoires
				for(int i = 0; i < M; i++) {
					ajouteMaison(listeMaisons);
				}
				
				//On crée une nouvelle instance de notre JDialog				
				s.setUI(new RangeSliderUI(s));
				
				JFrame app = new JFrame();
				
				
				JPanel labels = new JPanel(new BorderLayout());
				labels.add(min_max,BorderLayout.NORTH);	
				labels.add(slider_values,BorderLayout.SOUTH);
				
				BorderLayout all = new BorderLayout();
				app.setLayout(all);
				app.add(labels, BorderLayout.NORTH);
				app.add(s,BorderLayout.CENTER);	
				
				// J'ai commenté cette seconde addition de s
				//app.add(s);
				
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
