package components;

import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JWindow {
	private final String SPLASH_SCREEN_IMAGE_PATH = "./res/imgs/splash-screen.png";
	private ImageIcon splashScreen;
	private JLabel information;
	private JLabel display;

	public SplashScreen() {
		splashScreen = new ImageIcon(SPLASH_SCREEN_IMAGE_PATH);
		display = new JLabel(splashScreen);
		information = new JLabel("Nguyễn Hữu Trực - 20120608");
		getContentPane().add(display, BorderLayout.CENTER);
		JPanel footer = new JPanel(new FlowLayout());
		footer.add(information);
		getContentPane().add(footer, BorderLayout.SOUTH);
		pack();
		setVisible(true);
		setLocationRelativeTo(null);
	}
}
