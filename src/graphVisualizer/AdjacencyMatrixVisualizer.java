package graphVisualizer;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

public class AdjacencyMatrixVisualizer extends Pane {

    //raw graph data
	private final int[][] matrix;
    
	//visual rules
    private final double radius = 20;
    private final double layoutRadius = 100;
    
    //conteiner
    private static class VisualNode{
    	Circle circle;
    	Text label;
    	
    	VisualNode(Circle circle, Text label){
    		this.circle = circle;
    		this.label = label;
    		
    	}
    }
    
    public AdjacencyMatrixVisualizer(int[][] matrix) {
    	this.matrix = matrix;
    	
    	//loads in css from external file
    	String cssPath = getClass().getResource("/renderGraphs.css").toExternalForm();
    	this.getStylesheets().add(cssPath);
    	
    	this.getStyleClass().add("graph-canvas");
    	
    	//add a border so that nodes cannot go outside of it
    	javafx.scene.shape.Rectangle border = new javafx.scene.shape.Rectangle();
    	
    	border.widthProperty().bind(this.widthProperty());
    	border.heightProperty().bind(this.heightProperty());
    	this.setClip(border);
    	
    	this.widthProperty().addListener(_ -> triggerRender());
    	this.heightProperty().addListener(_ -> triggerRender());
    	
    }
    
    private void triggerRender() {
    	
    	if(getWidth() > 0 && getHeight() > 0) {
    		this.getChildren().clear();
    		renderGraph();
    	}
    }
    
    private void renderGraph() {
    	int numNodes = matrix.length;
    	double centerX = getWidth() / 2;
    	double centerY = getHeight() / 2;
    	
    	VisualNode[] visualNodes = new VisualNode[numNodes];
    	
    	//Calculate node positions sand some syle and text
    	for(int i = 0; i < numNodes; i++) {
    		double angle = i * (2 * Math.PI / numNodes);
    		double x = centerX + layoutRadius * Math.cos(angle);
    		double y = centerY + layoutRadius * Math.sin(angle);
    		
    		Circle circle = new Circle(x, y, radius);
    		circle.getStyleClass().add("graph-node");
    		
    		
    		Text text = new Text(String.valueOf(i));
    		text.getStyleClass().add("node-label");
    		text.setX(x - 5);
    		text.setY(y + 5);
    		text.setMouseTransparent(true);
    		
    		visualNodes[i] = new VisualNode(circle, text);
    		
    		makeDraggable(visualNodes[i]);
    	}
    	
    	boolean[][] visited = new boolean[numNodes][numNodes];
    	
    	for(int i = 0; i < numNodes; i++) {
    		for(int j = 0; j < numNodes; j++) {
    			
    			//selfLoop
    			if(i == j && matrix[i][j] > 0) {
    				SelfLoop loop = new SelfLoop(visualNodes[i].circle, radius);
    				this.getChildren().add(loop);
    				continue;
    			}
    			
    			//connecting two nodes
    			if(matrix[i][j] > 0 && !visited[i][j]) {
    				boolean bidirectional = matrix[j][i] > 0;
    				
    				if(bidirectional) {
    					//connected to both
    					Line line = new Line();
    					line.getStyleClass().add("graph-edge");
    					
    					line.startXProperty().bind(visualNodes[i].circle.centerXProperty());
    					line.startYProperty().bind(visualNodes[i].circle.centerYProperty());
    					line.endXProperty().bind(visualNodes[j].circle.centerXProperty());
    					line.endYProperty().bind(visualNodes[j].circle.centerYProperty());

    					this.getChildren().add(line);
    					
    					visited[i][j] = true;
    					visited[j][i] = true;
    				}else {
    					//connected to one of the nodes
    					Arrow arrow = new Arrow(radius);
                        arrow.getLine().startXProperty().bind(visualNodes[i].circle.centerXProperty());
                        arrow.getLine().startYProperty().bind(visualNodes[i].circle.centerYProperty());
                        arrow.getLine().endXProperty().bind(visualNodes[j].circle.centerXProperty());
                        arrow.getLine().endYProperty().bind(visualNodes[j].circle.centerYProperty());

                        this.getChildren().add(arrow);
                        visited[i][j] = true;
    				}
    			}
    		}
    	}
    	
    	for(VisualNode node : visualNodes) {
    		this.getChildren().addAll(node.circle, node.label);
    	}
    	
    }
    
    private void makeDraggable(VisualNode node) {
    	Circle circle = node.circle;
    	Text label = node.label;
    	
    	
    	/**
    	 * * @apiNote
    	 * Do not know why css didnt work here so the hovering is manually implemented here
    	 */
    	circle.setOnMouseEntered(_ -> {
            circle.setFill(Color.web("#60a5fa"));
            circle.setCursor(javafx.scene.Cursor.HAND);
        });
        
        circle.setOnMouseExited(_ -> {
            circle.setFill(Color.web("#2f80ed"));
            circle.setCursor(javafx.scene.Cursor.DEFAULT);
        });
        
    	circle.setOnMouseDragged(event -> {
    		double newX = Math.max(radius, Math.min(event.getX(), getWidth() - radius));
    		double newY = Math.max(radius, Math.min(event.getY(), getHeight() - radius));
    		
    		circle.setCenterX(newX);
    		circle.setCenterY(newY);
    		
    		label.setX(newX - 5);
    		label.setY(newY + 5);

    		
    	});
    }
    
    public static class SelfLoop extends Group{
    	private final CubicCurve curve = new CubicCurve();
    	private final Polygon arrowHead = new Polygon();
    	
    	public SelfLoop(Circle node, double nodeRadius) {
    		curve.setFill(null);
    		curve.getStyleClass().add("graph-edge");
    		arrowHead.getStyleClass().add("graph-arrow");
    		
    		this.setMouseTransparent(true);
    		
    		this.getChildren().addAll(curve, arrowHead);
    		
    		curve.startXProperty().bind(node.centerXProperty());
            curve.startYProperty().bind(node.centerYProperty());
            curve.endXProperty().bind(node.centerXProperty());
            curve.endYProperty().bind(node.centerYProperty());
            
            DoubleBinding binding = new DoubleBinding() {
            	{
            		super.bind(node.centerXProperty(), node.centerYProperty());
            	}
            	@Override
            	protected double computeValue() {
            		updateSelfLoop(nodeRadius);
            		return 0;
            	}
            };
            this.opacityProperty().bind(binding.add(1));
    	}
    	
    	private void updateSelfLoop(double nodeRadius) {
        	double cx = curve.getStartX();
        	double cy = curve.getStartY();
        	
        	curve.setControlX1(cx - 30);
            curve.setControlY1(cy - 60);
            curve.setControlX2(cx + 30);
            curve.setControlY2(cy - 60);
            
            // Find the exact intersection parameter 't' of the curve and circle boundary
            double low = 0.5;
            double high = 1.0;
            double t = 0.9;
            for (int k = 0; k < 15; k++) {
                double mid = (low + high) / 2;
                double u = mid * (1 - mid);
                double dx = 30 * u * (6 * mid - 3);
                double dy = -180 * u;
                double dist = Math.hypot(dx, dy);
                if (dist > nodeRadius) {
                    low = mid;
                } else {
                    high = mid;
                }
                t = mid;
            }
            
            // Exact re-entry coordinates on the boundary
            double u = t * (1 - t);
            double reenterX = cx + 30 * u * (6 * t - 3);
            double reenterY = cy - 180 * u;
            
            // Tangent direction vectors using calculus derivatives of the cubic curve
            double tx = -90 * (6 * t * t - 6 * t + 1);
            double ty = 180 * (2 * t - 1);
            
            double len = Math.hypot(tx, ty);
            double dirX = tx / len;
            double dirY = ty / len;
            
            // Align arrowhead structure exactly with the curve's entry path
            double arrowLength = 10;
            double arrowWidth = 5;
            
            double bx = reenterX - arrowLength * dirX;
            double by = reenterY - arrowLength * dirY;
            
            double leftX = bx + arrowWidth * (-dirY);
            double leftY = by + arrowWidth * dirX;
            
            double rightX = bx - arrowWidth * (-dirY);
            double rightY = by - arrowWidth * dirX;

            arrowHead.getPoints().setAll(
                reenterX, reenterY,
                leftX, leftY,
                rightX, rightY
            );
        }
    }
    
    public static class Arrow extends Group{
    	private final Line line = new Line();
    	private final Polygon arrowHead = new Polygon();
    	
    	public Arrow(double nodeRadius) {
    		line.getStyleClass().add("graph-edge");
    		arrowHead.getStyleClass().add("graph-arrow");
    		
    		this.setMouseTransparent(true);
    		this.getChildren().addAll(line, arrowHead);
    		
    		DoubleBinding updateBinding = new DoubleBinding() {
                {
                    super.bind(line.startXProperty(), line.startYProperty(), 
                               line.endXProperty(), line.endYProperty());
                }

                @Override
                protected double computeValue() {
                    updateArrowStructure(nodeRadius);
                    return 0;
                }
            };
            this.opacityProperty().bind(updateBinding.add(1.0));
    	}
    	
    	private void updateArrowStructure(double nodeRadius) {
    		double sx = line.getStartX();
    		double sy = line.getStartY();
    		double ex = line.getEndX();
    		double ey = line.getEndY();
    		
    		double dx = ex - sx;
    		double dy = ey - sy;
    		double distance = Math.hypot(dx, dy);
    		
    		if(distance == 0) return;
    		
    		double targetX = ex - (nodeRadius * dx / distance);
    		double targetY = ey - (nodeRadius * dy / distance);
    		
    		double arrowLength = 15;
    		double arrowWidth = 8;
    		
    		double factorX = dx / distance;
    		double factorY = dy / distance;
    		
    		double baseLeftX = targetX - arrowLength * factorX + arrowWidth * factorY;
            double baseLeftY = targetY - arrowLength * factorY - arrowWidth * factorX;

            double baseRightX = targetX - arrowLength * factorX - arrowWidth * factorY;
            double baseRightY = targetY - arrowLength * factorY + arrowWidth * factorX;
            
            arrowHead.getPoints().setAll(
            	targetX, targetY,
            	baseLeftX, baseLeftY,
            	baseRightX, baseRightY
            );
    	}
    	
    	public Line getLine() {
    		return line;
    	}
    }
}