package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {

	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage
	 * format of the polynomial is:
	 * 
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * 
	 * with the guarantee that degrees will be in descending order. For example:
	 * 
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * 
	 * which represents the polynomial:
	 * 
	 * <pre>
	 * 4 * x ^ 5 - 2 * x ^ 3 + 2 * x + 3
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients
	 *         and degrees read from scanner
	 */
	public static Node read(Scanner sc) throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
 
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input
	 * polynomials. The returned polynomial MUST have all new nodes. In other words,
	 * none of the nodes of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the
	 *         returned node is the front of the result polynomial
	 */
	public static Node add(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		//memory should be ascending 
		float coeff = 0;
        int degree = 0;
        
        Node pointer1 = poly1;
        Node pointer2 = poly2;
        
        Node polyAdd = null;
        Node temp = null;
        Node front = null;
       // Node end = null;
       //polyAdd = new Node(coeff, degree, null); // creates a new node b/c can't touch poly1&poly2
        if (pointer1 == null) {
        	front = pointer2;
        }
        if (pointer2 == null) {
        	front = pointer1;
        }

        while (pointer1 != null && pointer2 != null) {
            if (pointer1.term.degree == pointer2.term.degree) {
                if(pointer1.term.coeff + pointer2.term.coeff != 0) {
                	degree = pointer1.term.degree;
                	coeff = (pointer1.term.coeff + pointer2.term.coeff);
                	polyAdd = new Node (coeff, degree, null);
                }
                pointer1 = pointer1.next;
                pointer2 = pointer2.next;
                //check if cowff1 + coeff2 = 0, node should be null 

            } else if (pointer1.term.degree < pointer2.term.degree) {
                degree = pointer1.term.degree;
                coeff = pointer1.term.coeff;
                polyAdd = new Node (coeff, degree, null);
                pointer1 = pointer1.next;

            } else {
                degree = pointer2.term.degree;
                coeff = pointer2.term.coeff;
                polyAdd = new Node (coeff, degree, null);
                pointer2 = pointer2.next;
            }
            
           // polyAdd = new Node(coeff, degree, null);
            if(temp != null) {
            	temp.next = polyAdd;
            } else {
            	front = polyAdd;
            }
            temp = polyAdd;
            // pointer for polyAdd then pointer stays there
        }
             	/*if(head == null) {
                 	polyAdd = head;
                 } else {	
                 }  */     
        
        // this is linking it to the previous
//        if (pointer1 != null) {
//            polyAdd.next = pointer1;
//        } else if (pointer2 != null) {
//            polyAdd.next = pointer2;
//        }
        while (pointer1 != null) {
        	polyAdd = new Node(pointer1.term.coeff, pointer1.term.degree, null); 
        		if(temp == null) {
        			front = polyAdd;
        		} else {
        			temp.next = polyAdd;
        		}
        		temp = polyAdd;
        		pointer1 = pointer1.next;
        	}
        while (pointer2 != null) {
            	polyAdd = new Node(pointer2.term.coeff, pointer2.term.degree, null);
            		if(temp == null) {
            			front = polyAdd;
            		} else {
            			temp.next = polyAdd;
            		}
            		temp = polyAdd;
            		pointer2 = pointer2.next;
            }
            	return front;
		}      
//in the while he has it resetting , poly temp gets reversed,  
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input
	 * polynomials. The returned polynomial MUST have all new nodes. In other words,
	 * none of the nodes of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the
	 *         returned node is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		//nested for loop , everything from one node multiplying to the next 
		//reset to add them
		//first node times every other node
		//second node times every other node and add them all 
		//instead of pointing to null, its pointing to itself 
		//you're not adding to the end, you're adding to the front
		Node pointer1 = poly1;
		Node pointer2 = poly2;

		//int max = 0;
		Node temp = null; //putting all the terms in

		if (poly1 == null || poly2 == null) {
			return null;
		}

		while (pointer1 != null) {
			
			Node polyMult = null;
			pointer2 = poly2;
			
				while(pointer2 != null) {
				polyMult = new Node(pointer1.term.coeff * pointer2.term.coeff,
						pointer1.term.degree + pointer2.term.degree, polyMult);
				pointer2 = pointer2.next;
			}
	
			pointer1 = pointer1.next;
			polyMult = reverse(polyMult);
			temp = add(polyMult, temp);
		}
		
		return temp;
	}	
		
	private static Node reverse(Node product) { 
		Node prev = null;
		Node current = product;
		Node next = null;
		while (current != null) {
			next = current.next;
			current.next = prev;
			prev = current;
			current = next;
		}
			product = prev;
			return product;
		}
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x    Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) { // Node will be assigned to poly & float will be assigned to x /
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		float ret = 0;
		if ( poly == null)
			return ret;
		
		for (Node p = poly; p != null; p = p.next) {
			ret += (p.term.coeff * Math.pow(x, p.term.degree));
		}
		return ret;
	}

	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		}

		String retval = poly.term.toString();
		for (Node current = poly.next; current != null; current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}
}
