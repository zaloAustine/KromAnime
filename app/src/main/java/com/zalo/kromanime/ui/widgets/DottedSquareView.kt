import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.net.Uri
import android.util.AttributeSet
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide

class DottedSquareView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val imageView: ImageView
    private val paint: Paint = Paint()

    init {
        imageView = ImageView(context)
        val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        imageView.layoutParams = params
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP

        addView(imageView)

        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5f // Adjust the width of the dotted border
        paint.pathEffect = DashPathEffect(floatArrayOf(10f, 10f), 0f) // 10 pixels on, 10 pixels off (adjust as needed)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()

        // Create a path for the square
        val path = Path()
        path.moveTo(0f, 0f)
        path.lineTo(width, 0f)
        path.lineTo(width, height)
        path.lineTo(0f, height)
        path.close()

        // Draw the square with a dashed border
        canvas.drawPath(path, paint)
    }

    // Function to set the image URI or URL using Glide
    fun setImageUri(uri: Uri?) {
        uri?.let {
            Glide.with(context)
                .load(uri)
                .into(imageView)
        }
    }

    fun setImageUrl(url: String?) {
        url?.let {
            Glide.with(context)
                .load(url)
                .into(imageView)
        }
    }
}
