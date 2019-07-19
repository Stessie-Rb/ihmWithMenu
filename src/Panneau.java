import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class Panneau extends JPanel {

	private int posX = -50;
	private int posY = -50;
	private int drawSize = 50;
	// Un booleen pour savoir si le morphing est activé l'autre pour savoir si la
	// taille est réduite
	private boolean morph = false, reduce = false;
	private String forme = "ROND";
	// compteur de raffraîchissement
	private int increment = 0;
	private Color couleurForme= Color.red;
	private Color couleurFond= Color.white;
	

	public void paintComponent(Graphics g) {
		// phrase qui apparaît chaque fois que la méthode est exécutée
		// System.out.println("Je suis exécutée");
		// g.fillOval(20, 20, 75, 75);
		// Graphics2D g2d = (Graphics2D)g;
		// GradientPaint gp, gp2, gp3, gp4, gp5, gp6;
		// gp = new GradientPaint(0, 0, Color.RED, 20, 0, Color.magenta, true);
		// g2d.setPaint(gp);
		// g2d.fillRect(0, 0, 20, this.getHeight());

		// On choisit une couleur de fond pour le rectangle
		g.setColor(couleurFond);
		// On le dessine de sorte qu'il occupe toute la surface
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(couleurForme);

		if (this.morph)
			drawMorph(g);
		else
			draw(g);
	}

	public void setCouleurFond(Color color) {
		this.couleurFond= color;
	}
	
	public void setCouleurForme(Color color) {
		this.couleurForme= color;
	}
	private void draw(Graphics g) {
		if (this.forme.equals("ROND")) {
			g.fillOval(posX, posY, 50, 50);
		}
		if (this.forme.equals("CARRE") || this.forme.contentEquals("CARRÉ")) {
			g.fillRect(posX, posY, 50, 50);
		}
		if (this.forme.equals("TRIANGLE")) {
			// On calcul les sommets
			// Le sommet 1 se situe à la moitié du côté supérieur du carré
			int s1X = posX + 25;
			int s1Y = posY;

			// Sommet 2 en bas à droite
			int s2X = posX + 50;
			int s2Y = posY + 50;

			// Sommet 3 en bas à gauche
			int s3X = posX;
			int s3Y = posY + 50;
			// tableaux de coordonnées
			int[] ptsX = { s1X, s2X, s3X };
			int[] ptsY = { s1Y, s2Y, s3Y };

			g.fillPolygon(ptsX, ptsY, 3);
		}
		if (this.forme.equals("ETOILE")) {
			// tracer des lignes dans un carré
			int s1X = posX + 25;
			int s1Y = posY;
			int s2X = posX + 50;
			int s2Y = posY + 50;
			g.drawLine(s1X, s1Y, s2X, s2Y);
			int s3X = posX;
			int s3Y = posY + 17;
			g.drawLine(s2X, s2Y, s3X, s3Y);
			int s4X = posX + 50;
			int s4Y = posY + 17;
			g.drawLine(s3X, s3Y, s4X, s4Y);
			int s5X = posX;
			int s5Y = posY + 50;
			g.drawLine(s4X, s4Y, s5X, s5Y);
			g.drawLine(s5X, s5Y, s1X, s1Y);
		}
	}

	// Morphing
	private void drawMorph(Graphics g) {
		increment++;
		// on verifie si on doit réduire ou non
		if (drawSize >= 50)
			reduce = true;
		if (drawSize <= 10)
			reduce = false;
		if (reduce)
			drawSize = drawSize - getUsedSize();
		else
			drawSize = drawSize + getUsedSize();
		if (this.forme.equals("ROND")) {
			g.fillOval(posX, posY, drawSize, drawSize);
		}
		if (this.forme.equals("CARRE")) {
			g.fillRect(posX, posY, drawSize, drawSize);
		}
		if (this.forme.equals("TRIANGLE")) {
			int s1X = posX + drawSize / 2;
			int s1Y = posY;
			int s2X = posX + drawSize;
			int s2Y = posY + drawSize;
			int s3X = posX;
			int s3Y = posY + drawSize;
			int[] ptsX = { s1X, s2X, s3X };
			int[] ptsY = { s1Y, s2Y, s3Y };
			g.fillPolygon(ptsX, ptsY, 3);
		}
		if (this.forme.equals("ETOILE")) {
			int s1X = posX + drawSize / 2;
			int s1Y = posY;
			int s2X = posX + drawSize;
			int s2Y = posY + drawSize;
			g.drawLine(s1X, s1Y, s2X, s2Y);
			int s3X = posX;
			int s3Y = posY + drawSize / 3;
			g.drawLine(s2X, s2Y, s3X, s3Y);
			int s4X = posX + drawSize;
			int s4Y = posY + drawSize / 3;
			g.drawLine(s3X, s3Y, s4X, s4Y);
			int s5X = posX;
			int s5Y = posY + drawSize;
			g.drawLine(s4X, s4Y, s5X, s5Y);
			g.drawLine(s5X, s5Y, s1X, s1Y);
		}
	}

	private int getUsedSize() {
		int res = 0;
		// si le nbre de tours est de 10; on retourne à 1
		if (increment / 10 == 1) {
			increment = 0;
			res = 1;
		}
		return res;
	}

	public int getDrawSize() {
		return drawSize;
	}

	public boolean isMorph() {
		return morph;
	}

	public void setMorph(boolean bool) {
		this.morph = bool;
		// on réinitialise la taille
		drawSize = 50;
	}

	public void setForme(String form) {
		this.forme = form.toUpperCase();
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}
}
