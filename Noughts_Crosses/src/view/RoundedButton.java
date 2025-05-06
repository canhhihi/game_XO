package view;


import javax.swing.*;
import java.awt.*;
public class RoundedButton extends JButton {
    private Color backgroundColor = Color.WHITE; // Màu nền bên trong (trắng)
    private Color borderColor = Color.YELLOW;   // Màu viền ngoài (vàng)

    public RoundedButton(String text) {
        super(text);
        setContentAreaFilled(false);  // Tắt vẽ nền mặc định
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);  // Tắt opaque mặc định
        setForeground(Color.BLACK);  // Màu chữ mặc định
    }

    public RoundedButton(Color bgColor, Color borderColor) {
        this("");
        this.backgroundColor = bgColor;
        this.borderColor = borderColor;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Vẽ viền ngoài (bo góc với viền vàng)
        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(5));  // Độ dày của viền
        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20); // Vẽ viền ngoài

        // Vẽ nền bên trong (trắng hoặc trong suốt)
        g2.setColor(backgroundColor);
        g2.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 20, 20);  // Vẽ nền bo góc

        super.paintComponent(g);  // Vẽ text và icon
        g2.dispose();
    }

    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
        repaint();
    }

    public void setBorderColor(Color color) {
        this.borderColor = color;
        repaint();
    }
}
