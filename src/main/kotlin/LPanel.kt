import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.JPanel

class LPanel : JPanel() {
    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        val g2d = g as Graphics2D

        for(column in pixelArray){
            for(pixel in column){
                g2d.color = Color.white
                if(pixel?.isAlive == true){
                    g2d.color = Color.black
                }

                val x = column.indexOf(pixel)
                val y = pixelArray.indexOf(column)
                g2d.fillRect(x * pixelMultiplier,y * pixelMultiplier,pixelMultiplier,pixelMultiplier)
            }
        }
        repaint()
    }
}