import java.awt.Dimension


const val timerInterval = 30
val pixelMultiplier = 5
var frameDimension = Dimension(800,800)

var pixelArray = Array(frameDimension.height / pixelMultiplier) {arrayOfNulls<LPixel>(frameDimension.width / pixelMultiplier)}
var currentTime: Long = 0
val mainPanel = LPanel()



fun main() {
    for(column in pixelArray){
        for(row in column){
            column[column.indexOf(row)] = LPixel(false)
        }
    }
//    for(i in 20 .. 60){
//        for(j in 20 .. 60){
//            if((i + j) % 2 == 0){
//                pixelArray[i][j]!!.isAlive = true
//            }
//        }
//    }
    LFrame()


}

fun timerAction(){
    val millisNow = System.currentTimeMillis() - currentTime
    currentTime += millisNow
    for (j in 1 .. millisNow / timerInterval){
        for(i in pixelArray.size - 1 downTo 0){
            val column = pixelArray[i]
            for(pixel in column){
                if(pixel!!.isAlive){
                    val x = column.indexOf(pixel)
                    val y = pixelArray.indexOf(column)

                    if(y < pixelArray.size - 1 && x > 0 && x < pixelArray[y].size - 1){
                        if (!pixelArray[y + 1][x]!!.isAlive) {
                            pixel.isAlive = false
                            pixelArray[y + 1][x]!!.isAlive = true
                        }
                        else if (!pixelArray[y + 1][x + 1]!!.isAlive) {
                            pixel.isAlive = false
                            pixelArray[y + 1][x + 1]!!.isAlive = true
                        }
                        else if (!pixelArray[y + 1][x - 1]!!.isAlive ) {
                            pixel.isAlive = false
                            pixelArray[y + 1][x - 1]!!.isAlive = true
                        }
                    }
                }
            }
//            mainPanel.repaint()
        }
    }
}