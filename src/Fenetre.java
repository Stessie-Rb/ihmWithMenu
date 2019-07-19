import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

public class Fenetre extends JFrame {

	private Panneau pan = new Panneau();
	private JPanel container = new JPanel();
	private int compteur = 0;
	private boolean animated = true;
	private boolean backX, backY;
	private int x, y;
	private Thread t;
	private JMenuBar menuBar = new JMenuBar();

	private JMenu animation = new JMenu("Animation"), forme = new JMenu("Forme"),
			typeForme = new JMenu("Type de forme"), aPropos = new JMenu("A propos");

	private JMenuItem lancer = new JMenuItem("Lancer l'animation"), arreter = new JMenuItem("Arrêter l'animation"),
			quitter = new JMenuItem("Quitter"), aProposItem = new JMenuItem("?");
	private JCheckBoxMenuItem morph = new JCheckBoxMenuItem("Morphing");
	private JRadioButtonMenuItem carre = new JRadioButtonMenuItem("Carré"), rond = new JRadioButtonMenuItem("Rond"),
			triangle = new JRadioButtonMenuItem("Triangle"), etoile = new JRadioButtonMenuItem("Etoile");
	private ButtonGroup bg = new ButtonGroup();

	// menu contextuel
	private JPopupMenu jpm = new JPopupMenu();
	private JMenu background = new JMenu("Couleur de fond");
	private JMenu couleur = new JMenu("Couleur de la forme");

	private JMenuItem launch = new JMenuItem("Lancer l'animation");
	private JMenuItem stop = new JMenuItem("Arrêter l'animation");
	private JMenuItem rouge = new JMenuItem("Rouge"), bleu = new JMenuItem("Bleu"), vert = new JMenuItem("Vert"),
			blanc = new JMenuItem("Blanc"), rougeBack = new JMenuItem("Rouge"), bleuBack = new JMenuItem("Bleu"),
			vertBack = new JMenuItem("Vert"), blancBack = new JMenuItem("Blanc");

	// listener globaux
	private StopAnimationListener stopAnimation = new StopAnimationListener();
	private StartAnimationListener startAnimation = new StartAnimationListener();
	// listener pour les couleurs
	private CouleurFondListener bgColor = new CouleurFondListener();
	private CouleurFormeListener frmColor = new CouleurFormeListener();

	// Barre d'outils
	private JToolBar toolBar = new JToolBar();

	// Boutons de la barre d'outils
	private JButton play = new JButton(new ImageIcon("play.png")), cancel = new JButton(new ImageIcon("stop.png")),
			square = new JButton(new ImageIcon("carre.png")), tri = new JButton(new ImageIcon("triangle.png")),
			circle = new JButton(new ImageIcon("circle.png")), star = new JButton(new ImageIcon("star.png"));

	private Color fondBouton = Color.white;
	private FormeListener fListener = new FormeListener();

	public Fenetre() {
		this.setTitle("Animation");
		this.setSize(300, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		container.setBackground(Color.white);
		container.setLayout(new BorderLayout());

		stop.setEnabled(false);
		stop.addActionListener(stopAnimation);
		launch.addActionListener(startAnimation);

		// affectation des écouteurs aux points de menu
		rouge.addActionListener(frmColor);
		bleu.addActionListener(frmColor);
		vert.addActionListener(frmColor);
		blanc.addActionListener(frmColor);

		rougeBack.addActionListener(bgColor);
		bleuBack.addActionListener(bgColor);
		vertBack.addActionListener(bgColor);
		blancBack.addActionListener(bgColor);

		// On crée et on passe l'écouteur pour afficher le menu contextuel
		// création d'une implémentation de MouseAdapter
		// avec redéfinition de la méthode adéquate
		pan.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent event) {
				// seulement si c'est un clic droit
				// if(evet.getButton() == MouseEvent.Button3
				if (event.isPopupTrigger()) {
					background.add(blancBack);
					background.add(rougeBack);
					background.add(bleuBack);
					background.add(vertBack);

					couleur.add(blanc);
					couleur.add(rouge);
					couleur.add(bleu);
					couleur.add(vert);

					jpm.add(launch);
					jpm.add(stop);
					jpm.add(couleur);
					jpm.add(background);

					// méthode qui affiche le menu
					jpm.show(pan, event.getX(), event.getY());
				}
			}
		});

		container.add(pan, BorderLayout.CENTER);

		this.setContentPane(container);
		this.initToolBar();
		this.initMenu();
		this.setVisible(true);
		// go();
	}

	private void initToolBar() {
		this.cancel.setEnabled(false);
		this.cancel.addActionListener(stopAnimation);
		this.cancel.setBackground(fondBouton);
		this.play.addActionListener(startAnimation);
		this.setBackground(fondBouton);
		
		this.toolBar.add(play);
		this.toolBar.add(cancel);
		this.toolBar.addSeparator();
		
		//On ajoute les listener
		this.circle.addActionListener(fListener);
		this.circle.setBackground(fondBouton);
		this.toolBar.add(circle);
		
		this.square.addActionListener(fListener);
		this.square.setBackground(fondBouton);
		this.toolBar.add(square);
		
		this.tri.addActionListener(fListener);
		this.tri.setBackground(fondBouton);
		this.toolBar.add(tri);
		
		this.star.addActionListener(fListener);
		this.star.setBackground(fondBouton);
		this.toolBar.add(star);
		
		this.add(toolBar, BorderLayout.NORTH);
		
	}
	private void initMenu() {

		// LISTENER GLOBAL pour lancer l'animation
		lancer.addActionListener(startAnimation);

		// Cette instruction ajoute l'accélérateur 'c' à notre objet
		// avec un C majuscule il serait obligatoire d'appuyer sur shif + c ou d'activer
		// la touche maj
		// lancer.setAccelerator(KeyStroke.getKeyStroke('c'));
		// ou
		lancer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_MASK));
		// Menu animation
		animation.addSeparator();
		animation.add(lancer);

		// LISTENER GLOBAL pour arrêter l'animation

		arreter.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK));
		arreter.addActionListener(stopAnimation);
		arreter.setEnabled(false);
		animation.add(arreter);
		// Pour quitter l'application
		quitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		animation.add(quitter);

		// Menu forme
		bg.add(carre);
		bg.add(triangle);
		bg.add(rond);
		bg.add(etoile);

		// création d'un nouvel écouteur
		FormeListener f1 = new FormeListener();
		carre.addActionListener(f1);
		rond.addActionListener(f1);
		triangle.addActionListener(f1);
		etoile.addActionListener(f1);

		typeForme.add(rond);
		typeForme.add(carre);
		typeForme.add(triangle);
		typeForme.add(etoile);

		rond.setSelected(true);

		forme.add(typeForme);

		// Listener du morphing
		morph.addActionListener(new MorphListener());
		forme.add(morph);

		// Menu à propos
		aProposItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane jop = new JOptionPane();
				// ImageIcon img = new ImageIcon("images/cysboy.gif");
				String mess = "Merci ! \n J'espère que vous vous amusez bien !\n";
				mess += "Je crois qu'il est temps d'ajouter des accélérateurs et des " + " mnémoniques dans tout ça…\n";
				mess += "\n Allez, c'est parti !";
				jop.showMessageDialog(null, mess, "À propos", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		aPropos.add(aProposItem);

		// Ajout des menus dans la barrre de menus
		animation.setMnemonic('A');
		menuBar.add(animation);
		forme.setMnemonic('F');
		menuBar.add(forme);
		aPropos.setMnemonic('P');
		menuBar.add(aPropos);

		// Ajouter la barre de menu sur la fenetre
		this.setJMenuBar(menuBar);
	}

	private void go() {
		// Les coordonnées de départ de notre rond
		x = pan.getPosX();
		y = pan.getPosY();
		// Dans cet exemple, j'utilise une boucle while
		// Vous verrez qu'elle fonctionne très bien
		while (this.animated) {

			// Si le mode morphing est activé, on utilise la taille actuelle de la forme
			if (pan.isMorph()) {
				if (x < 1)
					backX = false;
				if (x > pan.getWidth() - pan.getDrawSize())
					backX = true;
				if (y < 1)
					backY = false;
				if (y > pan.getHeight() - pan.getDrawSize())
					backY = true;
			}

			// sinon on fait comme d'habitude
			if (x < 1)
				backX = false;
			if (x > pan.getWidth() - 50)
				backX = true;
			if (y < 1)
				backY = false;
			if (y > pan.getHeight() - 50)
				backY = true;
			if (!backX)
				pan.setPosX(++x);
			else
				pan.setPosX(--x);
			if (!backY)
				pan.setPosY(++y);
			else
				pan.setPosY(--y);
			pan.repaint();

			try {
				Thread.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	class PlayAnimation implements Runnable {
		public void run() {
			go();
		}
	}

	public class StartAnimationListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			JOptionPane jop = new JOptionPane();
			int option = jop.showConfirmDialog(null, "Voulez-vous lancer l'animation", "Lancement de l'animation",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (option == JOptionPane.OK_OPTION) {
				lancer.setEnabled(false);
				arreter.setEnabled(true);

				// ajout de l'instruction pour le menu contextuel
				launch.setEnabled(false);
				stop.setEnabled(true);
				
				play.setEnabled(false);
				cancel.setEnabled(true);

				animated = true;
				t = new Thread(new PlayAnimation());
				t.start();
			}
		}
	}

	class StopAnimationListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JOptionPane jop = new JOptionPane();
			int option = jop.showConfirmDialog(null, "Voulez-vous arrêter l'animation?", "Arrêt de l'animation",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (option != JOptionPane.NO_OPTION && option != JOptionPane.CANCEL_OPTION
					&& option != JOptionPane.CLOSED_OPTION) {
				animated = false;
				// on rempace nos boutons par les JMenuItem
				lancer.setEnabled(true);
				arreter.setEnabled(false);

				
				// instructions pour le menu contextuel
				launch.setEnabled(true);
				stop.setEnabled(false);
				
				play.setEnabled(true);
				cancel.setEnabled(false);
			}
		}
	}

	class FormeListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().getClass().getName().equals("javax.swing.JRadioButtonMenuItem"))
				pan.setForme(((JRadioButtonMenuItem) e.getSource()).getText());
			else {
				if(e.getSource()== square) {
					carre.doClick();
				}
				else if(e.getSource()== tri) {
					triangle.doClick();
				}
				else if(e.getSource()== star) {
					etoile.doClick();
				}
				else {
					rond.doClick();
				}
			}
		}
	}

	class MorphListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Si la case est cochée, activation du mode morphing
			if (morph.isSelected())
				pan.setMorph(true);
			// Sinon rien !
			else
				pan.setMorph(false);
		}
	}

	// Écoute le changement de couleur du fond
	class CouleurFondListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == vertBack)
				pan.setCouleurFond(Color.green);
			else if (e.getSource() == bleuBack)
				pan.setCouleurFond(Color.blue);
			else if (e.getSource() == rougeBack)
				pan.setCouleurFond(Color.red);
			else
				pan.setCouleurFond(Color.white);
		}
	}

	// Écoute le changement de couleur du fond
	class CouleurFormeListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == vert)
				pan.setCouleurForme(Color.green);
			else if (e.getSource() == bleu)
				pan.setCouleurForme(Color.blue);
			else if (e.getSource() == rouge)
				pan.setCouleurForme(Color.red);
			else
				pan.setCouleurForme(Color.white);
		}
	}
}