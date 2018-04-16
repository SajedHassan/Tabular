package TabularFunctions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Formatter;
import java.awt.Point;


import LinkedList.SLinkedList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
public class main extends Application {
	
	public static void main(String[] args) {

		launch(args);
	}

	Scene displayScene;
	String minResult;
	String AllminCost;
	TextArea text = new TextArea();
	TextArea minSolution = new TextArea();
	TextArea minCost = new TextArea();
	FileChooser fileChooser = new FileChooser();
	Label newline = new Label("\n");
	VBox layout3 = new VBox(4);
	Scene displayScene2 = null;
	TabularEvaluation test;
	Scene Evaluate;
	Scene Menu;
	Scene Guide;
	TextArea Steps;
	GridPane layout2 = new GridPane();
	VBox guideLayout = new VBox();
	VBox layout = new VBox(4);
	String result = null;
	File file;
	String allSteps="";
	@Override
	public void start(Stage mainStage) throws Exception {
		mainStage.setTitle("Tabular Evaluation");
		Label newline = new Label("\n");
		Label menu = new Label("Main menu");

		Button button = new Button("Start");
		button.setOnAction(e -> {
			test = new TabularEvaluation();
			mainStage.setScene(Evaluate);
		});
		button.setMinWidth(250);
		button.setMinHeight(70);
		Button guideButton = new Button("Show user guide");
		guideButton.setOnAction(e -> {
			mainStage.setScene(Guide);
		});
		guideButton.setMinWidth(250);
		guideButton.setMinHeight(70);

		Button exitButton = new Button("Exit");
		exitButton.setOnAction(e -> {
			boolean choice = ConfirmationBox.errorMessage("exit", "Are you sure?");
			if (choice) {
				mainStage.close();
			} else {
				e.consume();
			}
		});
		exitButton.setMinWidth(250);
		exitButton.setMinHeight(70);
		layout.setAlignment(Pos.CENTER);

		layout.getChildren().addAll(menu, button, guideButton, exitButton);
		VBox.setMargin(button, new Insets(10, 10, 10, 10));
		VBox.setMargin(exitButton, new Insets(10, 10, 10, 10));
		VBox.setMargin(guideButton, new Insets(10, 10, 10, 10));

		Menu = new Scene(layout, 400, 400);

		Label appHead = new Label("Please fill the following");

		Label noOfVariables = new Label("Number of variables = ");

		TextField noOfVar = new TextField();

		noOfVar.setPromptText("Enter the number here");
		noOfVar.setMinWidth(50);
		Label enterMin = new Label("Minterms = ");
		Label enterDont = new Label("Don't Cares = ");
		TextField minterms = new TextField();
		minterms.setPromptText("Enter minterms comma separated");
		minterms.setMinWidth(300);
		TextField dontCare = new TextField();
		dontCare.setPromptText("optional");
		dontCare.setMinWidth(300);
		Button back = new Button("Back");
		back.setOnAction(e -> {
			minterms.clear();
			dontCare.clear();
			noOfVar.clear();
			mainStage.setScene(Menu);
		});
		back.setMinWidth(300);
		back.setMinHeight(20);

		Button compute = new Button("Compute");
		compute.setOnAction(e -> {
			String tmp = noOfVar.getText();
			String tmpminS = minterms.getText();

			String tmpDontS = dontCare.getText();

			int numberOfVariables;
			int[] tmpminf;
			int[] tmpDont = null;
			try {
				numberOfVariables = Integer.parseInt(tmp);
				tmpminf = test.setMinterms(tmpminS, numberOfVariables);
				test.setRealMinterms(tmpminf);
				if (tmpDontS != null &&tmpDontS.length() != 0) {
					tmpDont = test.setDontCares(tmpDontS, numberOfVariables);
					if (!test.isValidInput(tmpminf, tmpDont)) {
						throw null;
					}
				}
				
				
				int[] tmpmin;
				if (tmpDont != null && tmpDont.length != 0) {
					tmpmin = new int[tmpminf.length + tmpDont.length];
				for (int i = 0 ; i < tmpminf.length ; i++) {
					tmpmin[i] = tmpminf[i]; 
				}
				for (int i = tmpminf.length ; i < tmpminf.length + tmpDont.length ; i++) {
					tmpmin[i] = tmpDont[i-tmpminf.length];
				}
				} else {
					tmpmin = tmpminf;
				}
				
				

				test.setParameters(tmpmin, numberOfVariables, tmpDont);
				SLinkedList tmp2 = test.findPrimeImplicants();
				result = test.mapPrimeImplicants(tmp2);
				String[] allPrimeImplicants = result.split("\\s*,\\s*");

				SLinkedList pet = test.rowColumnChart(tmp2);
				String[] allSolutions = test.mapAllSolutions(pet, allPrimeImplicants);
				
				SLinkedList min = test.getMinSolutions(pet);
				String[] minSolutions = test.mapAllSolutions(min, allPrimeImplicants);
				
				String newline2 = System.getProperty("line.separator");
				StringBuilder finalRes = new StringBuilder();
				for (int i = 0; i < allSolutions.length; i++) {
					finalRes.append(allSolutions[i] + "  " +newline2);
				}
				
				
				
				
				
				
				String[] reservedMinSolutions = new String[minSolutions.length];
				for (int i = 0; i < minSolutions.length ; i++) {
					reservedMinSolutions[i] = minSolutions[i];
				}
				
				SLinkedList minCosting = new SLinkedList();
				minSolutions[0] = minSolutions[0].replace("'", "");
				minSolutions[0] = minSolutions[0].replace("+", "");
				minSolutions[0] = minSolutions[0].replace(" ", "");
				minCosting.add(new Point(minSolutions[0].length(), 0));
				for (int i = 1; i < minSolutions.length ; i++) {
					minSolutions[i] = minSolutions[i].replace("'", "");
					minSolutions[i] = minSolutions[i].replace("+", "");
					minSolutions[i] = minSolutions[i].replace(" ", "");
					if (minSolutions[i].length() == ((Point)minCosting.get(0)).x) {
						minCosting.add(new Point(minSolutions[i].length(), i));
					} else if (minSolutions[i].length() < ((Point)minCosting.get(0)).x) {
						minCosting.clear();
						minCosting.add(new Point(minSolutions[i].length(), i));
					}
				}
				StringBuilder finalMin = new StringBuilder();
				for (int i = 0 ; i < minCosting.size() ; i++) {
					finalMin.append(reservedMinSolutions[((Point)minCosting.get(i)).y] + newline2);
				}
				text.setText(result);
				minSolution.setText(finalRes.toString());
				minResult = finalRes.toString();
				
				allSteps =test.getSteps();
				System.out.println(allSteps);
				
				minCost.setText(finalMin.toString());
				AllminCost = finalMin.toString();
				
				mainStage.setScene(displayScene);

				test = new TabularEvaluation();
			} catch (Exception E) {
				System.out.println(E.getMessage());
				ErrorWindow.errorMessage("Invalid input",
						"You entered an in valid entry\n Please make sure of the following :"
								+ "\n 1 - Enter comma separated values only in minterms " + "\n       and dont cares"
								+ "\n 2 - Dont enter a minterm or a dont care > 2^number" + "\n       of variables"
								+ "\n 3 - fill all the required fields with valid inputs");
			}

		});
		compute.setMinWidth(300);
		compute.setMinHeight(20);
		
		
		
		
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text File", "*txt"));
		Button ChooseFile = new Button("ChooseFile");
		ChooseFile.setOnAction(e -> {
			file = fileChooser.showOpenDialog(mainStage);
			if (file != null) {
				BufferedReader br=null;
				try {
					br = new BufferedReader(new FileReader(file));
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				
				String tmp = null;
				String tmpminS = null;
				String tmpDontS = null;
				try {
					tmp = br.readLine();
					tmpminS = br.readLine();

					tmpDontS = br.readLine();

				} catch (IOException e1) {
					e1.printStackTrace();
				}
								
				
				int numberOfVariables;
				
				int[] tmpminf;
				int[] tmpDont = null;
				try {
					numberOfVariables = Integer.parseInt(tmp);
					tmpminf = test.setMinterms(tmpminS, numberOfVariables);
					test.setRealMinterms(tmpminf);
					if (tmpDontS != null &&tmpDontS.length() != 0) {
						tmpDont = test.setDontCares(tmpDontS, numberOfVariables);
						if (!test.isValidInput(tmpminf, tmpDont)) {
							throw null;
						}
					}
					
					int[] tmpmin;
					if (tmpDont != null && tmpDont.length != 0) {
						tmpmin = new int[tmpminf.length + tmpDont.length];
					for (int i = 0 ; i < tmpminf.length ; i++) {
						tmpmin[i] = tmpminf[i]; 
					}
					for (int i = tmpminf.length ; i < tmpminf.length + tmpDont.length ; i++) {
						tmpmin[i] = tmpDont[i-tmpminf.length];
					}
					} else {
						tmpmin = tmpminf;
					}
					
					

					test.setParameters(tmpmin, numberOfVariables, tmpDont);
					SLinkedList tmp2 = test.findPrimeImplicants();
					result = test.mapPrimeImplicants(tmp2);
					String[] allPrimeImplicants = result.split("\\s*,\\s*");

					SLinkedList pet = test.rowColumnChart(tmp2);
					String[] allSolutions = test.mapAllSolutions(pet, allPrimeImplicants);
					SLinkedList min = test.getMinSolutions(pet);
					String[] minSolutions = test.mapAllSolutions(min, allPrimeImplicants);
		            String newline2 = System.getProperty("line.separator");
					StringBuilder finalRes = new StringBuilder();
					for (int i = 0; i < allSolutions.length; i++) {
						finalRes.append(allSolutions[i] + "  " + newline2);
					}
					text.setText(result);
					minSolution.setText(finalRes.toString());
					// hna
					
					String[] reservedMinSolutions = new String[minSolutions.length];
					for (int i = 0; i < minSolutions.length ; i++) {
						reservedMinSolutions[i] = minSolutions[i];
					}
					
					SLinkedList minCosting = new SLinkedList();
					minSolutions[0] = minSolutions[0].replace("'", "");
					minSolutions[0] = minSolutions[0].replace("+", "");
					minSolutions[0] = minSolutions[0].replace(" ", "");
					minCosting.add(new Point(minSolutions[0].length(), 0));
					for (int i = 1; i < minSolutions.length ; i++) {
						minSolutions[i] = minSolutions[i].replace("'", "");
						minSolutions[i] = minSolutions[i].replace("+", "");
						minSolutions[i] = minSolutions[i].replace(" ", "");
						if (minSolutions[i].length() == ((Point)minCosting.get(0)).x) {
							minCosting.add(new Point(minSolutions[i].length(), i));
						} else if (minSolutions[i].length() < ((Point)minCosting.get(0)).x) {
							minCosting.clear();
							minCosting.add(new Point(minSolutions[i].length(), i));
						}
					}
					StringBuilder finalMin = new StringBuilder();
					for (int i = 0 ; i < minCosting.size() ; i++) {
						finalMin.append(reservedMinSolutions[((Point)minCosting.get(i)).y] + newline2);
					}
					
					System.out.println(allSteps);
					allSteps =test.getSteps();
					Steps.setText(allSteps);
					minResult = finalRes.toString();
					AllminCost = finalMin.toString();
					minCost.setText(finalMin.toString());
					mainStage.setScene(displayScene);

					test = new TabularEvaluation();
				} catch (Exception E) {
					System.out.println(E.getMessage());
					ErrorWindow.errorMessage("Invalid iput",
							"You entered an in valid entry\n Please make sure of the following :"
									+ "\n 1 - Enter comma separated values only in minterms "
									+ "\n       and dont cares on 2 separate lines"
									+ "\n 2 - first line you must enter the number of variables" );
				}
			}
		});
		ChooseFile.setMinWidth(250);
		ChooseFile.setMinHeight(20);

		GridPane.setConstraints(appHead, 0, 0);
		GridPane.setConstraints(noOfVariables, 0, 1);
		GridPane.setConstraints(noOfVar, 1, 1);
		GridPane.setConstraints(enterMin, 0, 2);
		GridPane.setConstraints(minterms, 1, 2);
		GridPane.setConstraints(enterDont, 0, 3);
		GridPane.setConstraints(dontCare, 1, 3);
		GridPane.setConstraints(ChooseFile, 0, 4);
		GridPane.setConstraints(compute, 1, 4);
		GridPane.setConstraints(back, 1, 5);
		
		layout2.setAlignment(Pos.CENTER);
		layout2.getChildren().addAll(noOfVar, noOfVariables, appHead, enterMin, minterms, enterDont, dontCare,
				ChooseFile,compute, back);
		layout2.setPadding(new Insets(10, 10, 10, 10));
		layout2.setVgap(8);
		layout2.setHgap(10);

		Evaluate = new Scene(layout2, 700, 300);

		Label guidelabel = new Label("this is a quick tour :"
				+ "\nOn clicking start button you have to enter 3 values :"
				+ "\n  1 - the number of variables of your function ."
				+ "\n  2 - the minterms in a comma separated form." + "\n  3 - optionally you can enter the dont cares "
				+ "\n       also in a comma separated way" + "\n the back button will return you to the main menu again"
				+ "\n On clicking compute a window will appear and\n" + " tells you the answer to this function.\n"
				+ "You can return to the main menu Or"
				+ "\n On Clicking show steps it will show you the steps of the solution .");
		Button guideBack = new Button("Back to main menu");
		guideBack.setOnAction(e -> {
			mainStage.setScene(Menu);
		});
		guideBack.setMinSize(200, 40);

		guideLayout.getChildren().addAll(guidelabel, newline, guideBack);
		guideLayout.setAlignment(Pos.CENTER);
		guideLayout.setPadding(new Insets(10, 10, 10, 10));

		Guide = new Scene(guideLayout, 500, 400);

		Label steps = new Label("The steps\n");
		steps.setWrapText(true);
		steps.setFont(new Font("Arial", 30));

		steps.setAlignment(Pos.CENTER);

		// hot hna yad al steps
		
		Steps = new TextArea();
		Steps.setEditable(false);
		Steps.setMaxWidth(600);
		Steps.setMaxHeight(500);
		Label label = new Label("All the prime implicats are :\n");
		label.setMaxWidth(650);
		label.setWrapText(true);
		label.setFont(new Font("Arial", 30));
		label.setAlignment(Pos.CENTER);

		Label labelformin = new Label("All the solutions :\n");
		labelformin.setMaxWidth(650);
		labelformin.setWrapText(true);
		labelformin.setFont(new Font("Arial", 30));
		labelformin.setAlignment(Pos.CENTER);

		text.setEditable(false);
		text.setMaxWidth(600);
		text.setMaxHeight(50);
		minSolution.setEditable(false);
		minSolution.setMaxWidth(600);
		minSolution.setMaxHeight(200);

		Button button3 = new Button("Go Back");
		button3.setOnAction(e -> {
			minterms.clear();
			dontCare.clear();
			noOfVar.clear();
			mainStage.setScene(Evaluate);
		});
		button3.setMaxSize(300, 40);
		Button button2 = new Button("Show Steps");
		button2.setOnAction(e -> {
			Steps.setText(allSteps);
			mainStage.setScene(displayScene2);
		});
		button2.setMaxSize(300, 40);
		Button button4 = new Button("Save to file");
		button4.setOnAction(e -> {
			this.file = fileChooser.showSaveDialog(mainStage);
			try {
				
	            Formatter fileWriter = null;
	            fileWriter = new Formatter(file);
	            String newline2 = System.getProperty("line.separator");
	            fileWriter.format("%s", "all the prime implicats are :" +newline2+ result +newline2+ "all the solutions are :"+ newline2+ minResult+newline2+ "all the minimum results are :"+ newline2+ AllminCost +newline2+"steps"+newline2+ allSteps );
	            fileWriter.close();
	        } catch (IOException ex) {
	            
	        }
		});
		minCost.setMaxWidth(600);
		minCost.setMaxHeight(100);
		Label minCost = new Label("The primes of minimum cost :");
		minCost.setMaxWidth(650);
		minCost.setWrapText(true);
		minCost.setFont(new Font("Arial", 30));
		minCost.setAlignment(Pos.CENTER);

		
		button4.setMaxSize(300, 40);
		VBox layout = new VBox(5);
		layout.setAlignment(Pos.CENTER);
		layout.getChildren().addAll(label, text, labelformin, minSolution, minCost,this.minCost, button2, newline, button3 ,button4);
		displayScene = new Scene(layout, 700, 700);

		Button buttonA = new Button("Go Back");
		buttonA.setOnAction(e -> {
			mainStage.setScene(displayScene);

		});
		buttonA.setMaxSize(300, 40);

		layout3.setAlignment(Pos.CENTER);
		layout3.getChildren().addAll(steps, newline, Steps, buttonA);
		displayScene2 = new Scene(layout3, 900, 500);

		mainStage.setScene(Menu);
		mainStage.show();
		mainStage.setOnCloseRequest(e -> {
			boolean choice = ConfirmationBox.errorMessage("exit", "Are you sure?");
			if (choice) {
				mainStage.close();
			} else {
				e.consume();
			}
		});
	}
}
