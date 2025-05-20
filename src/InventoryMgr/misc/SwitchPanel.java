package InventoryMgr.misc;

import javax.swing.*;
import java.awt.*;

public class SwitchPanel extends JPanel {
    private final JPanel[] panels;
    private int currentIndex = 0;
    private Timer switchTimer;

    public SwitchPanel(int switchIntervalMs, JPanel... panels) {
        this.panels = panels;
        setLayout(new CardLayout());

        for (int i = 0; i < panels.length; i++) {
            add(panels[i], String.valueOf(i));
        }

        showPanel(0);

        switchTimer = new Timer(switchIntervalMs, e -> switchToNextPanel());
        switchTimer.start();
    }

    private void showPanel(int index) {
        CardLayout cl = (CardLayout) getLayout();
        cl.show(this, String.valueOf(index));
    }

    private void switchToNextPanel() {
        currentIndex = (currentIndex + 1) % panels.length;
        showPanel(currentIndex);
    }

    public void stopSwitching() {
        if (switchTimer != null) {
            switchTimer.stop();
        }
    }

    public void startSwitching() {
        if (switchTimer != null && !switchTimer.isRunning()) {
            switchTimer.start();
        }
    }
}
