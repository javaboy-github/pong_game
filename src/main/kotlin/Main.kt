import com.almasb.fxgl.app.GameApplication
import com.almasb.fxgl.app.GameSettings
import com.almasb.fxgl.dsl.*
import com.almasb.fxgl.entity.Entity
import com.almasb.fxgl.entity.getComponent
import javafx.scene.input.KeyCode
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.shape.Rectangle

fun main() = GameApplication.launch(Main::class.java, emptyArray<String>())

class Main : GameApplication(){
	private val paddleWidth = 30.0
	private val paddleHeight = 100.0
	private val ballSize = 20.0
	private val ballSpeed = 5.0

	private lateinit var paddle1: Entity
	private lateinit var paddle2: Entity
	private lateinit var ball: Entity

	private lateinit var texts:List<String>

	private lateinit var component1: PaddleComponent
	private lateinit var component2: PaddleComponent

	enum class Type {
		PADDLE, BALL
	}

	override fun initGame() {
		texts = text("texts.txt")
		paddle2 = spawnBat(getAppWidth() - paddleWidth, getAppHeight() / 2 - paddleHeight / 2, Color.LIGHTGREEN)
		paddle1 = spawnBat(0.0, getAppHeight() / 2 - paddleHeight / 2, Color.RED)
		ball = spawnBall(getAppWidth() / 2 - ballSize / 2, getAppHeight() / 2 - ballSize / 2, Color.ORANGE)

		component1 = paddle1.getComponent(PaddleComponent::class.java)
		component2 = paddle2.getComponent(PaddleComponent::class.java)
	}

	override fun initGameVars(vars: MutableMap<String, Any>) {
		vars["score"] = 0
	}

	override fun initInput() {
		onKey(KeyCode.W, "Up paddle1") {component1.up()}
		onKey(KeyCode.S, "Down paddle1") {component1.down()}
		onKey(KeyCode.D, "Right paddle1") {component1.right()}
		onKey(KeyCode.A, "Left paddle1") {component1.left()}

		onKey(KeyCode.UP, "Up paddle2") {component2.up()}
		onKey(KeyCode.DOWN, "Down paddle2") {component2.down()}
		onKey(KeyCode.RIGHT, "Right paddle2") {component2.right()}
		onKey(KeyCode.LEFT, "Left paddle2") {component2.left()}
	}

	override fun initSettings(settings: GameSettings) {
		with(settings) {
			width = 800
			height = 600
			title = "対戦型PONGゲーム"
			isDeveloperMenuEnabled = true
		}
	}

	override fun initPhysics() {
		onCollision(Type.PADDLE, Type.BALL) {p, b ->
			b.getComponent(BallComponent::class.java).collision(p)
		}
	}

	private fun spawnBat(x: Double, y: Double, color: Color): Entity {
		return entityBuilder()
			.type(Type.PADDLE)
			.with(PaddleComponent())
			.at(x, y)
			.viewWithBBox(Rectangle(paddleWidth, paddleHeight).apply{fill = color})
//			.viewWithBBox(ImageView(image("ゆうと.jpg", paddleWidth, paddleHeight)))
			.collidable()
			.buildAndAttach()
	}

	private fun spawnBall(x: Double, y: Double, color: Color): Entity {
		return entityBuilder()
			.type(Type.BALL)
			.at(x, y)
			.viewWithBBox(Circle(10.0).apply{fill = color})
			.with(BallComponent())
			.collidable()
			.buildAndAttach()
	}
}