package InventoryMgr.misc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

public class FadePanel extends JPanel {
    private final JPanel[] panels;
    private int currentIndex = 0;
    private float alpha = 1f;
    private BufferedImage prevImage;
    private BufferedImage nextImage;
    private boolean fading = false;
    private boolean fadingOut = true;

    public FadePanel(JPanel... panels) {
        this.panels = panels;
        setLayout(new CardLayout());

        for (int i = 0; i < panels.length; i++) {
            add(panels[i], String.valueOf(i));
        }

        showPanel(0);

        SwingUtilities.invokeLater(() -> {
            Timer initialDelay = new Timer(200, e -> startTimer());
            initialDelay.setRepeats(false);
            initialDelay.start();
        });
    }

    private void showPanel(int index) {
        if (fading) {
            // Don't change panels during fade to avoid flicker
            return;
        }
        removeAll();
        add(panels[index]);
        revalidate();
        repaint();
    }

    private void startTimer() {
        Timer switchTimer = new Timer(5000, e -> startFade());
        switchTimer.start();
    }

    private void startFade() {
        if (getWidth() <= 0 || getHeight() <= 0) {
            System.out.println("Width or height 0, skipping fade");
            return;
        }

        int nextIndex = (currentIndex + 1) % panels.length;

        prevImage = renderPanel(panels[currentIndex]);
        nextImage = renderPanel(panels[nextIndex]);

        if (prevImage == null || nextImage == null) {
            System.out.println("One of the images is null, skipping fade");
            return;
        }

        alpha = 1f;
        fading = true;
        fadingOut = true;

        setOpaque(false);

        Timer fadeTimer = new Timer(50, null);
        fadeTimer.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fadingOut) {
                    alpha -= 0.05f;
                    if (alpha <= 0f) {
                        alpha = 0f;
                        fadingOut = false;
                        ((Timer) e.getSource()).stop();

                        // 150 ms pause with only background visible
                        Timer pauseTimer = new Timer(150, e2 -> {
                            currentIndex = nextIndex;
                            prevImage = nextImage;
                            nextImage = null;
                            alpha = 0f;
                            ((Timer) e2.getSource()).stop();
                            startFadeIn();
                        });

                        repaint();
                        pauseTimer.setRepeats(false);
                        pauseTimer.start();
                        return;
                    }
                }
                repaint();
            }
        });
        fadeTimer.start();
    }

    private void startFadeIn() {
        Timer fadeInTimer = new Timer(50, null);
        fadeInTimer.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alpha += 0.05f;
                if (alpha >= 1f) {
                    alpha = 1f;
                    fading = false;
                    setOpaque(true);
                    showPanel(currentIndex);
                    repaint();
                    ((Timer) e.getSource()).stop();
                    return;
                }
                repaint();
            }
        });
        fadeInTimer.start();
    }

    private BufferedImage renderPanel(JPanel panel) {
        int w = getWidth();
        int h = getHeight();
        if (w <= 0 || h <= 0) {
            return null;
        }
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        panel.setSize(w, h);
        panel.doLayout();
        panel.paint(g2);
        g2.dispose();
        return img;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Fill background first
        g2.setColor(getBackground());
        g2.fillRect(0, 0, getWidth(), getHeight());

        if (!fading || prevImage == null) {
            // No fade, draw normally
            super.paintComponent(g);
            g2.dispose();
            return;
        }

        if (fadingOut) {
            // Fade out previous panel
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.drawImage(prevImage, 0, 0, null);
        } else {
            // Fade in next panel
            if (nextImage != null) {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                g2.drawImage(nextImage, 0, 0, null);
            }
            // else only background shown during pause
        }

        g2.dispose();
    }
}
