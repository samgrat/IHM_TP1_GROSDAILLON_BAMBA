import java.awt.geom.Point2D;

public class Home {
	
	private Point2D.Double position;
	private int pieces;
	private int valeur;
	
	/**
	 * Données d'un bien immobillier
	 * @param pX position x
	 * @param pY position y
	 * @param pieces nombre de pieces
	 * @param valeur valeur du bien
	 */
	public Home(double pX, double pY, int pieces, int valeur) {
		
		this.position = new Point2D.Double(pX, pY);
		this.pieces = pieces;
		this.valeur = valeur;
		
	}
	
	public double getX() {
		return position.getX();
	}
	
	public void setX(double x) {
		position.setLocation(x, position.getY());
	}
	
	public double getY() {
		return position.getY();
	}
	
	public void setY(double y) {
		position.setLocation(position.getX(), y);
	}
	
	public double getPieces() {
		return this.pieces;
	}
	
	public void setPieces(int pieces) {
		this.pieces = pieces;
	}
	
	public double getValeur() {
		return this.valeur;
	}
	
	public void setValeur(int valeur) {
		this.valeur = valeur;
	}

}
