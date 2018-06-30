package firstProject;


import java.io.*;
import java.util.*;
 
class infixPrefix {
	static String reverse(String text) {
		String temp="";
		for(int i = text.length();i>0;i--) {
			temp+=text.charAt(i-1);
		}
		return temp;
	}

	static int getPriority(char c)
	{
	    if (c == '-' || c == '+')
	        return 1;
	    else if (c == '*' || c == '/')
	        return 2;
	    return 0;
	}
	
	static String infixToPrefix(String infix) {
		String prefix = reverse(infix);
		
		prefix='('+prefix+')';
		int lengthofinfix=prefix.length();
		Stack<Character> stackofprefix = new Stack<>();
		String temp="";
		
		for(int i=0;i<lengthofinfix;i++) {
			if(Character.isDigit(prefix.charAt(i)))
				temp+=prefix.charAt(i);
			else if(prefix.charAt(i)=='(')
				stackofprefix.push(prefix.charAt(i));
			else if(prefix.charAt(i)==')') {
				while(stackofprefix.lastElement()!='(') {
					temp+=stackofprefix.lastElement();
					stackofprefix.pop();
				}
				stackofprefix.pop();
			}
			else {
					while( getPriority(prefix.charAt(i))<=getPriority(stackofprefix.lastElement()) ) {
						temp+=stackofprefix.lastElement();
						stackofprefix.pop();
					}
					stackofprefix.push(prefix.charAt(i));
			}
			
		}
		
		prefix=reverse(temp);
		return prefix;
	}
	
 
static boolean isOperand(char c)
{
    return (c >= 48 && c <= 57);
}
  
static double evaluatePrefix(String text)
{
    Stack<Double> temp = new Stack<Double>();
  
    for (int j = text.length() - 1; j >= 0; j--) {
        if (isOperand(text.charAt(j))) 
            temp.push((double)(text.charAt(j) - 48));
        else {
            double topof1 = temp.lastElement();
            temp.pop();
            double topof2 = temp.lastElement();
            temp.pop();
            switch (text.charAt(j)) {
	            case '+':
	                temp.push(topof1 + topof2);break;
	            case '-':
	                temp.push(topof1 - topof2);break;
	            case '*':
	                temp.push(topof1 * topof2);break;
	            case '/':
	                temp.push(topof1 / topof2);break;
            }
        }
    }
    return temp.lastElement();
}
 
public static void main(String[] args)
{
	
	//System.out.println(Character.isAlphabetic('k'));
   String text = "-2+/4*225";
   System.out.println(evaluatePrefix(text));
   System.out.println(infixToPrefix("2+5-3*4/2"));
   System.out.println(evaluatePrefix(infixToPrefix("2+5-3*4/2")));
}
}
 