package app;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import structures.Stack;

public class Expression {

	public static String delims = " \t*+-/()[]";
			
    /**
     * Populates the vars list with simple variables, and arrays lists with arrays
     * in the expression. For every variable (simple or array), a SINGLE instance is created 
     * and stored, even if it appears more than once in the expression.
     * At this time, values for all variables and all array items are set to
     * zero - they will be loaded from a file in the loadVariableValues method.
     * 
     * @param expr The expression
     * @param vars The variables array list - already created by the caller
     * @param arrays The arrays array list - already created by the caller
     */
    public static void 
    makeVariableLists(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
    	/** COMPLETE THIS METHOD **/
    	/** DO NOT create new vars and arrays - they are already created before being sent in
    	 ** to this method - you just need to fill them in.
    	 **/
    	String temp = "";
    	String operators = "+-*/()]";
    	for (int i = 0; i < expr.length(); i++) {
    		if(Character.isDigit(expr.charAt(i))) {
    			continue;
    		}
    		if(Character.isWhitespace(expr.charAt(i))) {
    			continue;
    		}
    		if(delims.contains(Character.toString(expr.charAt(i))) != true) {
    			temp += expr.charAt(i);
    		}else if(operators.contains(Character.toString(expr.charAt(i)))) {
    			if(temp != "") {
    				Variable v = new Variable(temp);
    				if(vars.contains(v) != true) {
    					vars.add(v);
    				}
    			temp = "";
    			}
    		}else if (expr.charAt(i) == '[') {
    			if(temp != "") {
    				Array a = new Array(temp);
    				arrays.add(a);
    				temp = "";
    			}
    		}
    	}
        
//        System.out.println(Arrays.deepToString(arrays.toArray())); //take this out before submitting, helps see what is being printed 
//        System.out.println(Arrays.deepToString(vars.toArray()));
        
        Variable v = new Variable (temp);
        if((vars.contains(v) != true) && (delims.contains(temp) != true)) {
        	vars.add(v);
        }
    }
    
    /**
     * Loads values for variables and arrays in the expression
     * 
     * @param sc Scanner for values input
     * @throws IOException If there is a problem with the input 
     * @param vars The variables array list, previously populated by makeVariableLists
     * @param arrays The arrays array list - previously populated by makeVariableLists
     */
    public static void 
    loadVariableValues(Scanner sc, ArrayList<Variable> vars, ArrayList<Array> arrays) 
    throws IOException {
        while (sc.hasNextLine()) {
            StringTokenizer st = new StringTokenizer(sc.nextLine().trim());
            int numTokens = st.countTokens();
            String tok = st.nextToken();
            Variable var = new Variable(tok);
            Array arr = new Array(tok);
            int vari = vars.indexOf(var);
            int arri = arrays.indexOf(arr);
            if (vari == -1 && arri == -1) {
            	continue;
            }
            int num = Integer.parseInt(st.nextToken());
            if (numTokens == 2) { // scalar symbol
                vars.get(vari).value = num;
            } else { // array symbol
            	arr = arrays.get(arri);
            	arr.values = new int[num];
                // following are (index,val) pairs
                while (st.hasMoreTokens()) {
                    tok = st.nextToken();
                    StringTokenizer stt = new StringTokenizer(tok," (,)");
                    int index = Integer.parseInt(stt.nextToken());
                    int val = Integer.parseInt(stt.nextToken());
                    arr.values[index] = val;              
                }
            }
        }
    }
    
    /**
     * Evaluates the expression.
     * 
     * @param vars The variables array list, with values for all variables in the expression
     * @param arrays The arrays array list, with values for all array items
     * @return Result of evaluation
     */
    public static float 
    evaluate(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
    	/** COMPLETE THIS METHOD **/
    	// following line just a placeholder for compilation
    	// trim the expression
    	expr.trim();
    	// create stack opbjects of Character, float, and tring
    	Stack<Character> operatorStk = new Stack<>();
    	Stack<Float> operandStk = new Stack<>();
    	Stack<String> arrStk = new Stack<>();

    	StringBuffer str_buf = new StringBuffer("");
    	float operand = 0;
    	int i = 0;
    	// iterate over the length of the expression
    	while (i < expr.length()) {// get the character of the expression
    		switch (expr.charAt(i)) {
    			case '(':
    	// if the character is open brace then add to the
    	// operator stack.
    			operatorStk.push(expr.charAt(i));
    			break;
    			case ')':
    	// if the character is closed brace then
    	// iterate the operator stack until the open brace found
    	while (!operatorStk.isEmpty() && !operandStk.isEmpty() && (operatorStk.peek() != '(')) {
    	// call the method findOperationResult
    		findOperationResult(operatorStk, operandStk);
    	}
    	// if the peek of the operator stack is open brace
    	if (operatorStk.peek() == '(') {
    	// then pop the value from the operator stack
    		operatorStk.pop();
    	}
    		break;
    		case '[':
    	// if the character is open brace '['
    	// push the value into the arrStk
    		arrStk.push(str_buf.toString());
    	// set the lenght of the string buffer object to 0
    		str_buf.setLength(0);
    	// push the character of the expression into operatorStk
    		operatorStk.push(expr.charAt(i));
    		break;
    		case ']':
    	// if the character is closed brace ']'
    	// iterate the operator stack until the open brace found
    	while (!operatorStk.isEmpty() && !operandStk.isEmpty() && (operatorStk.peek() != '[')) {
    	// call the method findOperationResult
    		findOperationResult(operatorStk, operandStk);
    	}
    	// if the peek of the operator stack is open brace
    		if (operatorStk.peek() == '[') {
    	// then pop the value from the operator stack
    			operatorStk.pop();
    	}
    	// get the index value of the operandStk first element
    		int idx = operandStk.pop().intValue();
    	// create an object of the array iterator
    		Iterator<Array> itr = arrays.iterator();
    	while (itr.hasNext()) {
    		Array arr = itr.next();
    		if (arr.name.equals(arrStk.peek())) {
    			operandStk.push((float) arr.values[idx]);
    			arrStk.pop();
    			break;
    		}
    	}
    	break;
    	case ' ':
    	break;
    	case '+':
    	case '-':
    	case '*':
    	case '/':
    	// check for empty, peek value and Precedence(
    	while (!operatorStk.isEmpty() && (operatorStk.peek() != '(') && (operatorStk.peek() != '[')
    	&& isPrecedenceLow(expr.charAt(i), operatorStk.peek())) {
    	// call the method findOperationResult
    	findOperationResult(operatorStk, operandStk);
    	}
    	operatorStk.push(expr.charAt(i));
    	break;
    	default:
    	// if the expression is characters from a to z or A to Z
    	if ((expr.charAt(i) >= 'a' && expr.charAt(i) <= 'z')
    	|| (expr.charAt(i) >= 'A' && expr.charAt(i) <= 'Z')) {
    	// append the characters to string buffer
    		str_buf.append(expr.charAt(i));
    	if (i + 1 < expr.length()) {
    	// check if the character of the expr
    	if (expr.charAt(i + 1) == '+' || expr.charAt(i + 1) == '-' || expr.charAt(i + 1) == '*'
    	|| expr.charAt(i + 1) == '/' || expr.charAt(i + 1) == ')' || expr.charAt(i + 1) == ']'
    	|| expr.charAt(i + 1) == ' ') {
    	// create an object for the Variable var
    		Variable var = new Variable(str_buf.toString());
    		int idxVar = vars.indexOf(var);
    		operand = vars.get(idxVar).value;
    	// Add operand to operandStk
    		operandStk.push(operand);
    		str_buf.setLength(0);

    	}

    	} else {
    	// the last variable of the expression
    	// convert to integer value;
    			Variable var = new Variable(str_buf.toString());
    			int varIndex = vars.indexOf(var);
    			operand = vars.get(varIndex).value;
    	// Add operand to operandStk
    			operandStk.push(operand);
    	// System.out.println("variable=" + str_buf.toString()
    	// + " value=" + fValue);
    			str_buf.setLength(0);
    	}

    	} else if (expr.charAt(i) >= '0' && expr.charAt(i) <= '9') {
    	// collect all the digits of the constant bConst = true;
    		str_buf.append(expr.charAt(i));
    			if (i + 1 < expr.length()) {
    	if (expr.charAt(i + 1) == '+' || expr.charAt(i + 1) == '-' || expr.charAt(i + 1) == '*'
    	|| expr.charAt(i + 1) == '/' || expr.charAt(i + 1) == ')' || expr.charAt(i + 1) == ']'
    	|| expr.charAt(i + 1) == ' ') {
    	// all the digits are collect convert from string to
    	// integer
    	operand = Integer.parseInt(str_buf.toString());
    	// Add operand to operandStk
    	operandStk.push(operand);
    	// System.out.println("constant=" + fValue);
    	str_buf.setLength(0);
    	}
    	} else {
    	// convert the last constant of the expression to integer
    			operand = Float.parseFloat(str_buf.toString());
    	// Add operand to operandStk
    			operandStk.push(operand);
    			str_buf.setLength(0);
    		}
    	}
    	break;
    	}
    	i++;
    	}
    	Float result = Float.valueOf(0);
    	if (i == expr.length()) {
    		while (operatorStk.size() > 0 && operandStk.size() > 1) {
    	// call the method findOperationResult
    			findOperationResult(operatorStk, operandStk);
    	}
    	if (operandStk.size() > 0) {
    		result = operandStk.pop();
    	}
    	}
    	return result.floatValue();

    	}

    	// definition of the method findOperationResult()
    	private static void findOperationResult(Stack<Character> operatorStk, Stack<Float> operandStk) {
    	Float reuslt = Float.valueOf(0);
    	if (operatorStk.size() > 0 && operandStk.size() > 1) {
    	// get the two operands
    		Float operand1 = operandStk.pop().floatValue();
    		Float operand2 = operandStk.pop().floatValue();
    	// perform the operations
    		switch (operatorStk.pop()) {
    		case '+':
    			reuslt = operand2 + operand1;
    			break;
    		case '-':
    			reuslt = operand2 - operand1;
    			break;
    		case '*':
    			reuslt = operand2 * operand1;
    			break;
    		case '/':
    			reuslt = operand2 / operand1;
    	break;
    	}
    	operandStk.push(reuslt);

    	} else if (operandStk.size() > 0) {
    	reuslt = operandStk.pop();
    	// push the value intp operand stack
    	operandStk.push(operandStk.pop().floatValue());
    	}
    	}
    	// definition of the method isPrecedenceLow()
    	private static boolean isPrecedenceLow(char ch1, char ch2) {
    	if ((ch1 == '*' || ch1 == '/') && (ch2 == '+' || ch2 == '-')) {
    	return false;
    	}
    	return true;
    	}	
}