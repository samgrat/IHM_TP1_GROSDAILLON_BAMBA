import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;

public class Test {
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				//On crée une nouvelle instance de notre JDialog
				SimpleFenetre fenetre = new SimpleFenetre();
				fenetre.setVisible(true);//On la rend visible
				JSlider s = new JSlider();
				s.setUI(new BasicSliderUI(s));
				fenetre.add(s);
				//s.setVisible(true);
			}
		});
	}
}
