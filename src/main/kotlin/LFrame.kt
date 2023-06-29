import java.awt.BorderLayout
import java.awt.MouseInfo
import java.awt.Point
import java.awt.Toolkit
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JFrame
import javax.swing.Timer

class LFrame : JFrame() {
    var mouseBtnPress = false
    var mousePositionRelative = Point(0,0)
    var mousePositionAbsolute = Point(0,0)
    var removeDot = false
    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        location = Point((screenSize.width - frameDimension.width) / 2, (screenSize.height - frameDimension.height) / 2)


        mainPanel.preferredSize = frameDimension
        mainPanel.layout = null

        val cp = contentPane
        cp.add(mainPanel, BorderLayout.CENTER)
        pack()
        isVisible = true

        val t = Timer(timerInterval) {
            timerAction()
        }
        addKeyListener(object : KeyAdapter(){
            override fun keyReleased(e: KeyEvent?) {
                super.keyReleased(e)
                if(e?.keyCode == KeyEvent.VK_SPACE){
                    if(t.isRunning){
                        t.stop()
                    }
                    else {
                        currentTime = System.currentTimeMillis()
                        t.start()
                    }
                }
            }
        })

            mainPanel.addMouseListener(object : MouseAdapter(){
            override fun mousePressed(e: MouseEvent?) {
                super.mousePressed(e)
                if(e?.button == 1){

                    mousePositionRelative = Point(e.x, e.y)
                    mousePositionAbsolute = Point(e.xOnScreen, e.yOnScreen)
                    removeDot = false
                    mouseBtnPress = true
                }
                if(e?.button == 3){

                    mousePositionRelative = Point(e.x, e.y)
                    mousePositionAbsolute = Point(e.xOnScreen, e.yOnScreen)
                    removeDot = true
                    mouseBtnPress = true
                }
            }

            override fun mouseReleased(e: MouseEvent?) {
                super.mouseReleased(e)
                if(e?.button == 1){
                    mouseBtnPress = false
                }
                if(e?.button == 3){
                    mouseBtnPress = false
                }
            }
        })

        val mouseTimer = Timer(1){
            if(mouseBtnPress){

                val curremtMousePosition = MouseInfo.getPointerInfo().location

                val xLast = mousePositionAbsolute.x - curremtMousePosition.x
                val yLast = mousePositionAbsolute.y - curremtMousePosition.y
                mousePositionAbsolute = curremtMousePosition
                val x = mousePositionRelative.x - xLast
                val y = mousePositionRelative.y - yLast

                if(x < frameDimension.width && x > 0 && y < frameDimension.height && y > 0){
                    pixelArray[y / pixelMultiplier][x / pixelMultiplier]?.isAlive = !removeDot
                }

                mousePositionRelative = Point(x,y)
            }
        }
        mouseTimer.start()
    }
}