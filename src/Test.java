import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSliderUI;

public class Test {
	// Nombre de maisons devant être initilisées
	public static final int M = 100;
	// Quelques bornes
	public static final float X_MAX = 400;
	public static final float Y_MAX = 400;
	public static final int PIECES_MIN = 1;
	public static final int PIECES_MAX = 10;
	public static final int VALEUR_MIN = 10000;
	public static final int VALEUR_MAX = 1000000;	

	// Notre liste de home aleatoires
	public static ArrayList<Home> listeMaisons = new ArrayList<Home>();
	
	public static RangeSlider piecesSlider = new RangeSlider(PIECES_MIN, PIECES_MAX);
	public static RangeSlider valeurSlider = new RangeSlider(VALEUR_MIN, VALEUR_MAX);
	public static JSlider rayonASlider = new JSlider(JSlider.HORIZONTAL, 0, (int) X_MAX, 0);
	public static JSlider rayonBSlider = new JSlider(JSlider.HORIZONTAL, 0, (int) X_MAX, 0);
	
	public static JLabel rayonA_title = new JLabel("Rayon A", SwingConstants.CENTER);
	public static JLabel rayonB_title = new JLabel("Rayon B", SwingConstants.CENTER);
	
	public static JLabel pieces_title = new JLabel("Pieces", SwingConstants.CENTER);
	public static JLabel pieces_min_max = new JLabel("min : " + piecesSlider.getMin() + " - max : " + piecesSlider.getMax(), SwingConstants.CENTER);
	public static JLabel pieces_slider_values = new JLabel("min range : " + piecesSlider.getMinRange() + " - max range : " + piecesSlider.getMaxRange(), SwingConstants.CENTER);
	
	public static JLabel valeur_title = new JLabel("Prix", SwingConstants.CENTER);
	public static JLabel valeur_min_max = new JLabel("min : " + valeurSlider.getMin() + " - max : " + valeurSlider.getMax(), SwingConstants.CENTER);
	public static JLabel valeur_slider_values = new JLabel("min range : " + valeurSlider.getMinRange() + " - max range : " + valeurSlider.getMaxRange(), SwingConstants.CENTER);
	
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			
			public void run(){
				
				JFrame app = new JFrame();
				BorderLayout all = new BorderLayout();
				app.setLayout(all);
				app.setSize(760, 428);
				app.setResizable(false);
				
				// On commence par remplir notre liste avec des maisons aleatoires
				for(int i = 0; i < M; i++) {
					ajouteMaison(listeMaisons);
				}
				
				// Ensuite on cree notre carte
				Map map = new Map(listeMaisons, piecesSlider, valeurSlider, rayonASlider, rayonBSlider);

				//On définit les UI de nos RangeSliders et de nos sliders pour les distances des points a et b.				
				piecesSlider.setUI(new RangeSliderUI(piecesSlider, pieces_slider_values, map));
				valeurSlider.setUI(new RangeSliderUI(valeurSlider, valeur_slider_values, map));		
				rayonASlider.setUI(new BasicSliderUI(rayonASlider));
				rayonBSlider.setUI(new BasicSliderUI(rayonASlider));
				
				// On ajoute un listener pour redessiner notre carte en cas de changement de valeur
				rayonASlider.addChangeListener(new ChangeListener() {
				      public void stateChanged(ChangeEvent event) {
				    	  map.repaint();
				      }
				    });
				rayonBSlider.addChangeListener(new ChangeListener() {
				      public void stateChanged(ChangeEvent event) {
				        map.repaint();
				      }
				    });
				
				
				JPanel pieces_labels = new JPanel(new BorderLayout());
				pieces_labels.add(pieces_title,BorderLayout.NORTH);	
				pieces_labels.add(pieces_min_max,BorderLayout.CENTER);	
				pieces_labels.add(pieces_slider_values,BorderLayout.SOUTH);
				
				JPanel valeur_labels = new JPanel(new BorderLayout());
				valeur_labels.add(valeur_title,BorderLayout.NORTH);
				valeur_labels.add(valeur_min_max,BorderLayout.CENTER);	
				valeur_labels.add(valeur_slider_values,BorderLayout.SOUTH);
				
				// On creee des JPanel a part pour nos RangeSlider
				JPanel slider = new JPanel(new BorderLayout());
				slider.add(pieces_labels, BorderLayout.NORTH);
				slider.add(piecesSlider, BorderLayout.SOUTH);
				
				JPanel slider2 = new JPanel(new BorderLayout());
				slider2.add(valeur_labels, BorderLayout.NORTH);
				slider2.add(valeurSlider, BorderLayout.SOUTH);
				
				// ainsi que nos Basic Sliders
				JPanel slider3 = new JPanel(new BorderLayout());
				slider3.add(rayonA_title, BorderLayout.NORTH);
				slider3.add(rayonASlider, BorderLayout.SOUTH);

				JPanel slider4 = new JPanel(new BorderLayout());
				slider4.add(rayonB_title, BorderLayout.NORTH);
				slider4.add(rayonBSlider, BorderLayout.SOUTH);
				
				// Puis un autre JPanel les englobant
				JPanel sliders = new JPanel(new BorderLayout());
				sliders.add(slider, BorderLayout.NORTH);
				sliders.add(slider2, BorderLayout.SOUTH);
				
				// Et un autre
				JPanel basicSliders = new JPanel(new BorderLayout());
				basicSliders.add(slider3, BorderLayout.NORTH);
				basicSliders.add(slider4, BorderLayout.SOUTH);
					
				// Puis un autre JPanel les englobant
				JPanel controls = new JPanel(new BorderLayout());
				controls.add(sliders, BorderLayout.NORTH);
				controls.add(basicSliders, BorderLayout.SOUTH);
				
				JLabel realisation = new JLabel("Réalisé par Samuel BAMBA et Hugo GROS-DAILLON");
				
				//JPanel pour afficher l'ensemble des sliders au nord.
				JPanel elements = new JPanel(new BorderLayout());
				elements.add(controls, BorderLayout.NORTH);
				elements.add(realisation, BorderLayout.SOUTH);
				
				// On organise nos composants
				app.add(elements,BorderLayout.EAST);
				app.add(map,BorderLayout.CENTER);
				
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
