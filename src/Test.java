import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;

public class Test {
	public static RangeSlider s = new RangeSlider(-200, -50);
	public static JLabel min_max = new JLabel("min : " + s.getMin() + " - max : " + s.getMax(), SwingConstants.CENTER);
	public static JLabel slider_values = new JLabel("min range : " + s.getMinRange() + " - max range : " + s.getMaxRange(), SwingConstants.CENTER);

	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
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
				app.add(s);
				
				app.pack(); //la taille de la fenetre est définie en fonction des composants à l'intérieur.
				app.setVisible(true);

			}
		});
	}
}
