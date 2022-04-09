import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.text.TextAlignment;

public class GameButton extends Button {
	private int num, x, y;
	
	GameButton(int type, int num, int x, int y) {
		if (type == 1)
			this.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #d4af37; -fx-font-size: 48px; -fx-font-family: Comic Sans MS Bold;");
		else
			this.setStyle("-fx-background-color: #a58777;");
		
		this.setTextAlignment(TextAlignment.CENTER);
		this.setMinSize(130.0, 130.0);
		this.setAlignment(Pos.CENTER);
		this.setContentDisplay(ContentDisplay.CENTER);
		
		this.num = num;
		this.x = x;
		this.y = y;
	}
	
	public int getX() { return this.x; }
	public int getY() { return this.y; }
	public int getNum() { return this.num; }
	
}
