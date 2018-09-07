import javax.swing.*;

public class Test {
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				//On crée une nouvelle instance de notre JDialog
				SimpleFenetre fenetre = new SimpleFenetre();
				fenetre.setVisible(true);//On la rend visible
			}
		});
	}
}
