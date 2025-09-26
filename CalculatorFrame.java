import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.KeyStroke;

import java.util.*;

/**
 * Program Name: CalculatorFrame.java
 * Purpose: This is project #2 calculator. This calculator can do arithmetic and binary operations
 * 					using numbers with base 2, 8, 10 and 16. The toogle menu on the left let the user select
 * 					the preferred base type for the input. Once the user change the base the buttons, the
 * 					accumulator and the register will update accordingly converting the numbers and allowing
 * 					only the buttons that belong to the selected base.
 * @Author: Rodrigo Baiocchi Ferreira 1288669
 * @Date: Aug 08, 2025
 */

@SuppressWarnings("serial")
public class CalculatorFrame extends JFrame {
	
	private JRadioButton decRB, hexRB, octRB, binRB;
	private JLabel resultDisplay, middleDisplay, bottomDisplay;
	enum Representations { DEC, HEX, OCT, BIN };
	Representations currentRepresentation = Representations.DEC;
	HashMap<String, JButton> buttonsMap = new HashMap<String, JButton>();
	JPanel radioPanel = null, buttonPanel = null;
	String[] buttons = {
			"and", "nand", "or", "nor", "xor", "not",
			"*", "/", "+", "-", "<<", ">>",
			"7", "8", "9", "A", "D", "ClrA",
			"4", "5", "6", "B", "E", "ClrR",
			"1", "2", "3", "C", "F", "CLR",
			"+/-", "0", "back", "=", "mod", "Exit"
	};
	private int FRAME_WIDTH, FRAME_HEIGHT, FONT_SIZE;
	
	Calculator calculator = new Calculator();
	
	private CalculatorFrame() {
		FRAME_WIDTH = 500;
		FRAME_HEIGHT = 300;
		FONT_SIZE = 14;
	}
	
	/**
	 * Following functions (convertNumberToBase10, convertNumberFromBase10) 
	 * allow the display to be responsive and convert register and accumulator 
	 * every time user toggles between different bases.
	 * All the calculations are done as decimals and the display gets converted
	 * to the appropriate base.
	 * */
	
	long convertNumberToBase10(String input) {
		if (currentRepresentation == Representations.BIN)
			return Long.parseLong(input, 2);
		else if (currentRepresentation == Representations.DEC)
			return Long.parseLong(input, 10);
		else if (currentRepresentation == Representations.OCT)
			return Long.parseLong(input, 8);
		else if (currentRepresentation == Representations.HEX)
			return Long.parseLong(input, 16);
		else {
			System.out.println("Not available base, select between BIN, OCT, DEC, HEX");
			return Long.MIN_VALUE;
		}
	}
	
	String convertNumberFromBase10(Long number) {
		if (currentRepresentation == Representations.BIN)
			return Long.toBinaryString(number);
		else if (currentRepresentation == Representations.DEC)
			return String.format("%d", number);
		else if (currentRepresentation == Representations.OCT)
			return Long.toOctalString(number);
		else if (currentRepresentation == Representations.HEX)
			return Long.toHexString(number);
		else {
			System.out.println("Not available base, select between BIN, OCT, DEC, HEX");
			return "ERROR: Invalid number";
		}
	}
	
	class buttonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton clickedButton = (JButton) e.getSource();
			
			if (clickedButton.equals(buttonsMap.get("ClrR")))
				bottomDisplay.setText("0");
			
			if (clickedButton.equals(buttonsMap.get("CLR"))) {
				
				calculator.setAccumulator(0);
				calculator.setCurrentOperator("");
				calculator.setRegister(0);
				resultDisplay.setText("");
				middleDisplay.setText("");
				bottomDisplay.setText("0");
			}
			if (clickedButton.equals(buttonsMap.get("ClrA"))) {
				// reset accumulator
				calculator.setAccumulator(0);
				resultDisplay.setText("");
			}
			
			if (clickedButton.equals(buttonsMap.get("back"))) {
				double input = Double.parseDouble(bottomDisplay.getText()) / 10;
				calculator.setRegister((long) input);
				bottomDisplay.setText(String.format("%d", calculator.getRegister()));
			}
				
			if ((clickedButton.equals(buttonsMap.get("0")) && !bottomDisplay.getText().equals("0")) ||
					clickedButton.equals(buttonsMap.get("1")) ||
					clickedButton.equals(buttonsMap.get("2")) ||
					clickedButton.equals(buttonsMap.get("3")) ||
					clickedButton.equals(buttonsMap.get("4")) ||
					clickedButton.equals(buttonsMap.get("5")) ||
					clickedButton.equals(buttonsMap.get("6")) ||
					clickedButton.equals(buttonsMap.get("7")) ||
					clickedButton.equals(buttonsMap.get("8")) ||
					clickedButton.equals(buttonsMap.get("9")) ||
					clickedButton.equals(buttonsMap.get("A")) ||
					clickedButton.equals(buttonsMap.get("B")) ||
					clickedButton.equals(buttonsMap.get("C")) ||
					clickedButton.equals(buttonsMap.get("D")) ||
					clickedButton.equals(buttonsMap.get("E")) ||
					clickedButton.equals(buttonsMap.get("F"))) {
				String inputNumber = bottomDisplay.getText() + clickedButton.getText();
				long convertedInput = convertNumberToBase10(inputNumber.trim());
				System.out.println("convertedInput: " + convertedInput);
				bottomDisplay.setText(convertNumberFromBase10(convertedInput));
				calculator.setRegister(convertedInput);
			}
				
			if (clickedButton.equals(buttonsMap.get("*")) ||
					clickedButton.equals(buttonsMap.get("/")) ||
					clickedButton.equals(buttonsMap.get("+")) ||
					clickedButton.equals(buttonsMap.get("-")) ||
					clickedButton.equals(buttonsMap.get("mod")) ||
					clickedButton.equals(buttonsMap.get("and")) ||
					clickedButton.equals(buttonsMap.get("nand")) ||
					clickedButton.equals(buttonsMap.get("or")) ||
					clickedButton.equals(buttonsMap.get("nor")) ||
					clickedButton.equals(buttonsMap.get("xor")) ||
					clickedButton.equals(buttonsMap.get("<<")) ||
					clickedButton.equals(buttonsMap.get(">>"))) {
				calculator.setCurrentOperator(clickedButton.getText());
				if (bottomDisplay.getText().isEmpty()) {
					middleDisplay.setText(convertNumberFromBase10(calculator.getAccumulator()) + " " + clickedButton.getText());
					System.out.println("Accumulator: " + calculator.getAccumulator());
					System.out.println("Operator: " + calculator.getCurrentOperator());
					System.out.println("Register: " + calculator.getRegister());
				}
				else {
					middleDisplay.setText(convertNumberFromBase10(calculator.getRegister()) + " " + clickedButton.getText());
					bottomDisplay.setText("");
					calculator.setAccumulator(calculator.getRegister());
					calculator.setRegister(0);
					System.out.println("Operand 1: " + calculator.getRegister());
				}
			}
			
			if (clickedButton.equals(buttonsMap.get("not"))) {
				calculator.setCurrentOperator(clickedButton.getText());
				if (bottomDisplay.getText().isEmpty()) {
					middleDisplay.setText(clickedButton.getText() + " " + calculator.getAccumulator());
				}
				else {
					middleDisplay.setText(clickedButton.getText() + " " + calculator.getRegister());
					calculator.setAccumulator(calculator.getRegister());
				}
				calculator.calculateResult();
				resultDisplay.setText(String.format("%d", calculator.getAccumulator()));
				bottomDisplay.setText("");
				calculator.setRegister(0);
			}
			
			if (clickedButton.equals(buttonsMap.get("="))) {
				if (calculator.calculateResult()) {
					resultDisplay.setText("");
					middleDisplay.setText("Cannot divide by zero");
					bottomDisplay.setText("");
					calculator.setAccumulator(0);
				}
				else {
					resultDisplay.setText(convertNumberFromBase10(calculator.getAccumulator()));
					middleDisplay.setText(middleDisplay.getText() + " " + bottomDisplay.getText());
					bottomDisplay.setText("");
				}
				
			}
				
			if (clickedButton.equals(buttonsMap.get("+/-"))) {
				if (currentRepresentation == Representations.DEC) {
					double numberInput;
					if (bottomDisplay.getText().isEmpty())
						numberInput = Double.parseDouble(resultDisplay.getText());
					else
						numberInput = Double.parseDouble(bottomDisplay.getText());
					calculator.setRegister(((long) numberInput) * -1);
					bottomDisplay.setText(String.format("%d", calculator.getRegister()));
				}
			}
			
			if (clickedButton.equals(buttonsMap.get("Exit")))
				dispose();		
		}
	}
	
	void radioListener(ItemEvent ev) {
		JRadioButton clicked = (JRadioButton) ev.getSource();
		if (ev.getStateChange() == 1) {
			if (clicked == decRB) {
				currentRepresentation = Representations.DEC;
				switchNonBinaryNumberButtons(true);
				switchLetterButtons(false);
			}
								
			if (clicked == hexRB) {
				switchLetterButtons(true);
				switchNonBinaryNumberButtons(true);
				currentRepresentation = Representations.HEX;
			}
				
			if (clicked == octRB) {
				currentRepresentation = Representations.OCT;
				switchLetterButtons(false);
				switchNonBinaryNumberButtons(true);
				buttonsMap.get("9").setEnabled(false);
				buttonsMap.get("8").setEnabled(false);
			}
				
			if (clicked == binRB) {
				currentRepresentation = Representations.BIN;
				switchNonBinaryNumberButtons(false);
				switchLetterButtons(false);
			}
				
			if(!resultDisplay.getText().isEmpty()) 
				resultDisplay.setText(convertNumberFromBase10(calculator.getAccumulator()));
			if (!bottomDisplay.getText().isEmpty())
				bottomDisplay.setText(convertNumberFromBase10(calculator.getRegister()));
		}
	}
	
	void switchLetterButtons(boolean on) {
		buttonsMap.get("A").setEnabled(on);
		buttonsMap.get("B").setEnabled(on);
		buttonsMap.get("C").setEnabled(on);
		buttonsMap.get("D").setEnabled(on);
		buttonsMap.get("E").setEnabled(on);
		buttonsMap.get("F").setEnabled(on);
	}
	
	void switchNonBinaryNumberButtons(boolean on) {
		buttonsMap.get("2").setEnabled(on);
		buttonsMap.get("3").setEnabled(on);
		buttonsMap.get("4").setEnabled(on);
		buttonsMap.get("5").setEnabled(on);
		buttonsMap.get("6").setEnabled(on);
		buttonsMap.get("7").setEnabled(on);
		buttonsMap.get("8").setEnabled(on);
		buttonsMap.get("9").setEnabled(on);
	}
	
	public void loadFrame() {
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setTitle("RBF Calculator - Project #2");
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Menu");
		menuBar.add(menu);
			JMenuItem helpMenu = new JMenuItem("Help");
			helpMenu.addActionListener((ActionEvent ae) -> {
				Object[] options = { "Close" };
				JOptionPane.showOptionDialog(this, 
						"""
						Welcome to the my awesome calculator!!!
						
						In this calculator, you are able to do calculations 
						using base 2, 8, 10 and 16. The default selection is
						Decimal, but if you toggle between the different 
						bases the buttons, the register and the accumulator
						will update accordingly.
						
						The calculator performs regular arithmetic operations 
						such as (+, -, *, / and mod) and also binary 
						arithmetic operations (and, nand, etc.).
						
						If you press any of the operators right after you 
						completed an operation, it will use the current
						accumulator as the register for the next operation.
						
						Other buttons functionalities:
						CLR => clears everything
						ClrA => clears the accumulator
						ClrR => clears the register
						back => backspace one key click at a time in register.
						""", 
						"Help", 
						JOptionPane.CLOSED_OPTION, 
						JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			});
			menu.add(helpMenu);
//			helpMenu.setMnemonic(65);
			helpMenu.setAccelerator(KeyStroke.getKeyStroke("control e"));
			JMenuItem aboutMenu = new JMenuItem("About");
			aboutMenu.addActionListener((ActionEvent ae) -> {
				Object[] options = { "Close" };
				JOptionPane.showOptionDialog(this, 
						"""
						Version: 1.0.0
						Project: Project 2 Calculator S2025
						Author: Rodrigo Baiocchi Ferreira
						""", 
						"About", 
						JOptionPane.CLOSED_OPTION, 
						JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			});
			menu.add(aboutMenu);
			
	this.setJMenuBar(menuBar);
		
		Font fontResult = new Font(Font.SERIF, Font.BOLD, FONT_SIZE);
		resultDisplay = new JLabel("");
		resultDisplay.setHorizontalAlignment(JLabel.CENTER);
		resultDisplay.setBounds(0, 0, 30, 25);
		resultDisplay.setFont(fontResult);
		resultDisplay.setForeground(Color.RED);
		
		Font fontOperation = new Font(Font.SANS_SERIF, 0, FONT_SIZE);
		middleDisplay = new JLabel("");
		middleDisplay.setFont(fontOperation);
		middleDisplay.setHorizontalAlignment(JLabel.CENTER);
		middleDisplay.setForeground(Color.GRAY);
		
		Font fontRegister = new Font(Font.DIALOG_INPUT, Font.ITALIC, FONT_SIZE);
		bottomDisplay = new JLabel("");
		bottomDisplay.setFont(fontRegister);
		bottomDisplay.setHorizontalAlignment(JLabel.CENTER);
		
		// create display
		JPanel appPanel = new JPanel();
		appPanel.setLayout(new GridLayout(4, 1, 2, 2));
		appPanel.add(resultDisplay);
		appPanel.add(middleDisplay);
		appPanel.add(bottomDisplay);
		
		// create side radio buttons
		radioPanel = new JPanel();
		radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.Y_AXIS));
		(decRB = new JRadioButton("dec")).addItemListener((ev) -> this.radioListener(ev));
		(hexRB = new JRadioButton("hex")).addItemListener((ev) -> this.radioListener(ev));
		(octRB = new JRadioButton("oct")).addItemListener((ev) -> this.radioListener(ev));
		(binRB = new JRadioButton("bin")).addItemListener((ev) -> this.radioListener(ev));
		System.out.println("DecRB selection: " + decRB.isSelected());
		
		ButtonGroup radioGroup = new ButtonGroup();
		radioGroup.add(decRB);
		radioGroup.add(hexRB);
		radioGroup.add(octRB);
		radioGroup.add(binRB);
		radioPanel.add(decRB);
		radioPanel.add(hexRB);
		radioPanel.add(octRB);
		radioPanel.add(binRB);
		
		// create calculator buttons and populate buttonsMap Map
		buttonPanel = new JPanel(new GridLayout(6, 6, 5, 5));
		for (String s : buttons) {
			JButton button = new JButton(s);
			button.setName(s);
			button.addActionListener(new buttonListener());
			buttonsMap.put(s, button);
			buttonPanel.add(button);
		}
		
		decRB.setSelected(true);
		
		bottomDisplay.setText("0");
		
		this.setLayout(new BorderLayout());
		this.add(appPanel, BorderLayout.NORTH);
		this.add(radioPanel, BorderLayout.WEST);
		this.add(buttonPanel, BorderLayout.CENTER);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.pack();
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		CalculatorFrame myFrame = new CalculatorFrame();
		myFrame.loadFrame();
	}
}
//end class