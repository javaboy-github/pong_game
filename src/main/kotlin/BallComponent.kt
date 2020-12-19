import com.almasb.fxgl.dsl.getAppHeight
import com.almasb.fxgl.dsl.getAppWidth
import com.almasb.fxgl.entity.Entity
import com.almasb.fxgl.entity.component.Component
import javafx.geometry.Point2D

class BallComponent(
	private val speed : Double  = 5.0
) : Component(){
	private var direction = Point2D(1.0, 1.0)

	override fun onUpdate(tpf: Double) {
		getEntity().translateX(direction.x * speed)
		getEntity().translateY(direction.y * speed)
		if (getEntity().x < 0 || getEntity().x > getAppWidth()) {
			direction = Point2D(direction.x * -1, direction.y)
		}
		if (getEntity().y < 0 || getEntity().y > getAppHeight()) {
			direction = Point2D(direction.x, direction.y * -1)
		}
	}

	fun collision(paddle: Entity) {
		direction = Point2D(direction.x * -1, direction.y)
		direction = Point2D(direction.x, direction.y * -1)
	}
}