import com.almasb.fxgl.dsl.getAppHeight
import com.almasb.fxgl.dsl.getAppWidth
import com.almasb.fxgl.entity.component.Component

class PaddleComponent(
	private val speed: Double = 10.0
) : Component(){
	fun up() {
		getEntity().translateY(-speed)
		if (getEntity().y < 0)
			getEntity().y = 0.0
	}
	fun down() {
		getEntity().translateY(speed)
		if (getEntity().y + getEntity().height > getAppHeight())
			getEntity().y = getAppHeight() - getEntity().height
	}
	fun right() {
		if (getEntity().x < 180.0 - getEntity().width|| (getEntity().x >= getAppWidth() - 180.0 && getEntity().x < getAppWidth() - getEntity().width)) {
			getEntity().translateX(speed)
		}
	}
	fun left() {
		if ((getEntity().x > 0 && getEntity().x < 180.0) || getEntity().x > getAppWidth() - 180.0) {
			getEntity().translateX(-speed)
		}
	}
}