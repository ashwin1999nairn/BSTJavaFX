package application;

import javafx.application.Application;
import static javafx.application.Application.launch;
import static javafx.application.Platform.exit;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import static javafx.scene.text.TextAlignment.CENTER;

//THIS IS THE NODE CLASS
class node
{
	int data;//THE NODE'S DATA
	node left;//THE LEFT CHILD
	node right;//THE RIGHT CHILD
	node(int a)
	{
		data = a;
	}
	node Lchild()
	{
		return left;
	}
	node Rchild()
	{
		return right;
	}
	int get_Val()
	{
		return data;
	}
	node setLchild(int a)
	{
		left = new node(a);
		return left;
	}
	node setRchild(int a)
	{
		right = new node(a);
		return right;
	}
}


class Binary_Search_Tree//BINARY SEARCH TREE CLASS WITH ALL NECCESSARY FUNCTIONS
{
	node root = null;
	String in = "";
	int count = 0;

	node getRoot()//GETS THE ROOT OF THE NODE
	{
		return root;
	}

	public void addNode(int v)//ADDS NODE
	{
		root = addToNode(root, v);
	}

	public node addToNode(node root, int v)
	{
		if (root == null)
		{
			root = new node(v);
			count++;
			return root;
		}
		if (v < root.data)
			root.left = addToNode(root.left, v);
		else if (v > root.data)
			root.right = addToNode(root.right, v);
		return root;
	}

	public node deleteNode(int v, node root)
	{
		if (root == null)
			return root;
		else if (v < root.get_Val())//data lies in the left subtree
			root.left = deleteNode(v, root.left);
		else if (v > root.get_Val())//data lies in the right subtree
			root.right = deleteNode(v, root.right);
		else
		{
			if (root.left == null && root.right == null)//no child case
			{
				root = null;
				count--;
			}
			// one child case
			else if (root.left == null)
			{
				node temp = root;
				root = root.right;
				temp = null;
				count--;
			}
			//one right child
			else if (root.right == null)
			{
				node temp = root;
				root = root.left;
				temp = null;
				count--;
			}

			else//both children present
			{
				node temp = inOrderSuccesor(root.right);
				root.data = temp.data;
				root.right = deleteNode(temp.get_Val(), root.right);
			}
		}
		return root;
	}

	node inOrderSuccesor(node root)
	{
		while (root.left != null)
			root = root.left;
		return root;
	}

	int height(node n)//height of tree
	{
		if (n == null)
        return 0;
        else if(n.left == null && n.right == null)
        	return 0;
        else
        {
        	int lHeight = height(n.left);
        	int rHeight = height(n.right);
        	if (lHeight > rHeight)
        		return (lHeight +1);
        	else
        		return (rHeight+1);
        }
	}

	boolean search(node root, int v)//searches for an element
	{
		while (root != null)
		{
			if (v > root.data)
				root = root.right;
			else if (v < root.data)
				root = root.left;
			else
				return true;
		}
		return false;
	}
}

public class bst_javafx extends Application
{
	int s = 30;//this variable is for the size of the node
	Stage stage;//stage of application
	Scene scene1,scene2;//the 2 scenes of the application
  Canvas canvas = new Canvas(1100, 550);//canvas
  GraphicsContext gc = canvas.getGraphicsContext2D();
  void drawTheNode(int x, int y, int data)//draws the node
  {
    Color c1 = Color.rgb(255,153,153);
    Color black = Color.rgb(0,0,0);
    gc.setFill(c1);
    gc.fillOval(x, y, s, s);
    gc.setFill(black);
    gc.fillText(Integer.toString(data), x + s / 4 + 4, y + s / 2 + 4);
  }

  void drawTheTree(node root, int x, int y, int X)//draws the tree using indivisual nodes
  {
    if (root == null)
    return;
    drawTheNode(x, y, root.get_Val());
    drawTheTree(root.Lchild(), x - (X/2), y + 75, X / 2);
    drawTheTree(root.Rchild(), x + (X/2), y + 75, X / 2);
  }

  void drawConnection(node root, int x, int y, int dx, int dy, int X)//draws the connection berween nodes
  {
    gc.setStroke(Color.BEIGE);
    gc.strokeLine(100,100,610,110);
    gc.setLineWidth(20);
    if (root == null)
    	return;
  	Color c1 = Color.rgb(51,255,153);
    gc.setStroke(c1);
    gc.setLineWidth(2);
    gc.strokeLine(x,y,dx,dy);
    drawConnection(root.Lchild(), x - (X/2), y + 75, x, y, X / 2);
    drawConnection(root.Rchild(), x + (X/2), y + 75, x, y, X / 2);
   }

@Override
	public void start(Stage primaryStage)
	{
		stage = primaryStage;
		TextField text1 = new TextField();
		Button button1 = new Button("Insert");
		TextField text2 = new TextField();
		Button button2 = new Button("Delete");
		TextField text3 = new TextField();
		Button button3 = new Button("Find");
		Button button4 = new Button("Print tree");
		Button next = new Button("GO TO CODE");
		next.setOnAction(e->stage.setScene(scene2));
		Label author = new Label("Welcome To");
    author.setAlignment(Pos.TOP_CENTER);
    Label l = new Label("Binary Search Tree - By Adhyan and Ashwin");
    l.setAlignment(Pos.CENTER);
    Label label1 = new Label("");
    label1.setAlignment(Pos.BOTTOM_CENTER);
    Label label2 = new Label("");
    label2.setAlignment(Pos.BOTTOM_CENTER);
    Label label3 = new Label("");
    Color c1 = Color.rgb(0,0,0);
    Color c_fore = Color.rgb(255,153,255);
    ((Label) l).setStyle("-fx-border-color: black;");
    ((Label) l).setMinSize(400, 35);
    ((Label) l).setTextAlignment(TextAlignment.CENTER);
    ((Label) l).setFont(Font.font(20));
    author.setBackground(new Background(new BackgroundFill(c_fore, new CornerRadii(3), new Insets(0))));
    l.setBackground(new Background(new BackgroundFill(c_fore, new CornerRadii(3), new Insets(0))));
    ((Label) author).setStyle("-fx-border-color: black;");
    ((Label) author).setMinSize(400, 35);
    ((Label) author).setTextAlignment(TextAlignment.CENTER);
    ((Label) author).setFont(Font.font(40));
    Font myfont = new Font("TimesRoman",35);
    Font myfont1 = new Font("TimesRoman",16);
    Font myfont2 = new Font("TimesRoman",40);
    l.setFont(myfont);
    //author.setFont(myfont2);
    label1.setTextFill(c1);
    label2.setTextFill(c1);
    button1.setTextFill(c1);
    button1.setStyle("-fx-background-color: #33FF33;");
    button2.setTextFill(c1);
    button2.setStyle("-fx-background-color: #33FF33;");
    button3.setTextFill(c1);
    button3.setStyle("-fx-background-color: #33FF33;");
    button4.setTextFill(c1);
    button4.setStyle("-fx-background-color: #33FF33;");
    label1.setFont(myfont1);
    label2.setFont(myfont1);
    label3.setFont(myfont1);
    label3.setTextFill(c1);
    Binary_Search_Tree obj = new Binary_Search_Tree();
    button1.setOnAction(e->
    {
    	int x = Integer.parseInt(text1.getText());
    	obj.addNode(x);
    	text1.setText("");
    	label1.setText("Height is: " + obj.height(obj.root));
    	label2.setText(" || Number of Vertices: " + obj.count);
        drawConnection(obj.getRoot(), 600+10, 100+10, 100 , 100 ,175);
        drawTheTree(obj.getRoot(), 600, 100, 175);
    });
    button2.setOnAction(e->
    {
    	gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
        int x = Integer.parseInt(text2.getText());
        obj.deleteNode(x, obj.getRoot());
        text2.setText("");
        label1.setText("Height is: " + obj.height(obj.root));
        label2.setText("|| Number of Vertices: " + obj.count);
        drawConnection(obj.getRoot(), 600+10, 100+10, 100 , 100 ,175);
        drawTheTree(obj.getRoot(), 600, 100, 175);
        if(obj.root.left==null && obj.root.right==null)
        gc.clearRect(0, 0, canvas.getWidth(),canvas.getHeight());
    });
    button4.setOnAction(e->
    {
    	drawConnection(obj.getRoot(), 600+10, 100+10, 100 , 100 ,175);
    	drawTheTree(obj.getRoot(), 600, 100, 175);
    });
    button3.setOnAction(e->
    {
    	int x = Integer.parseInt(text3.getText());
    	boolean b = obj.search(obj.getRoot(), x);
    	if(b)
    	{
    		drawTheNode(1,1,x);
    		Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("Find Successful");
    		alert.setHeaderText("Find Successful");
    		alert.setContentText(x + " is found in the TREE:)");
    		alert.showAndWait();
    	}
    	else
    	{
    		Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("Find Unsuccessful");
    		alert.setHeaderText("Find Unsuccessful");
    		alert.setContentText(x + " is NOT found in the TREE:(");
    		alert.showAndWait();
    	}
    });
    VBox layout1 = new VBox(40);
    layout1.getChildren().addAll(author,l,next);
    layout1.setBackground(new Background(new BackgroundFill(Color.AQUA,CornerRadii.EMPTY,Insets.EMPTY)));
    layout1.setAlignment(Pos.CENTER);
    scene1 = new Scene(layout1,1100,600);
    HBox temp = new HBox(20);
    BorderPane layout2 = new BorderPane();
    temp.getChildren().addAll(text1,button1,text2,button2,text3,button3,button4,label1,label2);
    layout2.setPadding(new Insets(0,20,10,20));
    layout2.setTop(temp);
    layout2.setCenter(canvas);
    layout2.setBackground(new Background(new BackgroundFill(Color.BEIGE,CornerRadii.EMPTY,Insets.EMPTY)));
    scene2 = new Scene(layout2,1100,600);
    stage.setScene(scene1);
    stage.setTitle("BINARY SEARCH TREE -by Adhyan and Ashwin");
    stage.show();
	}

public static void main(String[] args)
{
	launch(args);
}
}
