//Timothy Dusek
//Written November 2019
import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Parse {
	
	public static int tokenCount = 1 ;
	public static int counter = 0;
	public static Hashtable<String, Integer> iTable = new Hashtable<String, Integer>(); //int = 1 and char = 2
	public static class bracketNode{ //creates bracketNode structure
	    String token;
	    String lexeme;
	}
	
	public static void program(bracketNode[] input){
		if (input[tokenCount].token.equals("programsym")){
			//System.out.println("Programsym found");
			tokenCount++;
			if (input[tokenCount].token.equals("identifier")){
				//System.out.println("Identifier found");
				tokenCount++;
				if (input[tokenCount].token.equals("lparen")){
					//System.out.println("L parenthesis found");
					tokenCount++;
					identifier_list_programsym(input);
					if (input[tokenCount].token.equals("rparen")){
						//System.out.println("R parenthesis found");
						tokenCount++;
						if (input[tokenCount].token.equals("semicolon")){
							//System.out.println("Semicolon found");
							tokenCount++;
							System.out.println(".data");
							declarations(input);
							compound_statement(input);
						}
						else{
							System.out.println("ERROR: Missing semicolon");
						}
					}
					else{
						System.out.println("ERROR: Missing R parenthesis");
					}
				}
				else{
					System.out.println("ERROR: Missing L parenthesis");
				}
			}
			else{
				System.out.println("Error: ID must follow program");
			}
		}
		
		else {
			System.out.println("Error: Program must start with the word 'Program'");
			}
		}
		
		public static void identifier_list_programsym(bracketNode[] input){
			if (input[tokenCount].token.equals("identifier")){
				//System.out.println("Identifier found");
				//iTable.put(input[tokenCount].lexeme, 1);
				tokenCount++;
				if (input[tokenCount].token.equals("comma")){
					//System.out.println("Comma found");
					tokenCount++;
					if (input[tokenCount].token.equals("identifier")){
						identifier_list_programsym(input);
					}
					else{
						System.out.println("ERROR: ID not listed after the comma");
					}
				}
			}
			
		}
		
		public static void identifier_list(bracketNode[] input){
			if (input[tokenCount].token.equals("identifier")){
				//System.out.println("Identifier found and added - Int");
				iTable.put(input[tokenCount].lexeme, 1);
				System.out.println(input[tokenCount].lexeme + ": .word 0");
				//System.out.println(input[tokenCount].lexeme + " .word 0);
				tokenCount++;
				if (input[tokenCount].token.equals("comma")){
					//System.out.println("Comma found");
					tokenCount++;
					if (input[tokenCount].token.equals("identifier")){
						identifier_list(input);
					}
					else{
						System.out.println("ERROR: ID not listed after the comma");
					}
				}
			}
			
		}
		
		public static void identifier_list_char(bracketNode[] input){
			if (input[tokenCount].token.equals("identifier")){
				//System.out.println("Identifier found and added - Char");
				iTable.put(input[tokenCount].lexeme, 2);
				System.out.println(input[tokenCount].lexeme + ": .byte 0");
				//System.out.println(input[tokenCount].lexeme + " .byte 'a');
				tokenCount++;
				if (input[tokenCount].token.equals("comma")){
					//System.out.println("Comma found");
					tokenCount++;
					if (input[tokenCount].token.equals("identifier")){
						identifier_list_char(input);
					}
					else{
						System.out.println("ERROR: ID not listed after the comma");
					}
				}
			}
			
		}
		
		public static void declarations(bracketNode[] input){
			if (input[tokenCount].token.equals("varsym")){
				//System.out.println("varsym found");
				tokenCount++;
				identifier_list(input);
				if (input[tokenCount].token.equals("colon")){
					//System.out.println("colon found");
					tokenCount++;
					type(input);
					if (input[tokenCount].token.equals("semicolon")){
						//System.out.println("semicolon found");
						tokenCount++;
					}
					else{
						System.out.println("ERROR: Missing semicolon");
					}
				}
				else{
					System.out.println("ERROR: Missing colon");
				}
				declarations_char(input);
			}
		}
		
		public static void declarations_char(bracketNode[] input){
			if (input[tokenCount].token.equals("varsym")){
				//System.out.println("varsym found");
				tokenCount++;
				identifier_list_char(input);
				if (input[tokenCount].token.equals("colon")){
					//System.out.println("colon found");
					tokenCount++;
					type(input);
					if (input[tokenCount].token.equals("semicolon")){
						//System.out.println("semicolon found");
						tokenCount++;
					}
					else{
						System.out.println("ERROR: Missing semicolon");
					}
				}
				else{
					System.out.println("ERROR: Missing colon");
				}
			}
		}
		
		public static void type(bracketNode[] input){
			standard_type(input);
		}
		
		public static void standard_type(bracketNode[] input){
			if (input[tokenCount].token.equals("integersym")){
				//System.out.println("integersym found");
				tokenCount++;
			}
			else if(input[tokenCount].token.equals("charsym")){
				//System.out.println("charsym found");
				tokenCount++;
			}
		}
		
		/* THIS MAY NOT BE NEEDED BECAUSE IT IS FOR SUBPROGRAM STUFF
		public static void subprogram_declarations(bracketNode[] input){
			if (input[tokenCount].token.equals("functionsym")){
				subprogram_declaration(input);
				subprogram_declarations(input);
			}
		}
		
		public static void subprogram_declarations(bracketNode[] input){
			subprogram_head(input);
			declarations(input);
			compound_statement(input);
		}
		*/
		////////////////////////////////////////////////////////////
		public static void compound_statement(bracketNode[] input){
		if (input[tokenCount].token.equals("beginsym")){
			//System.out.println("Beginsym found");
			System.out.println(".text\n");
			tokenCount++;
			optional_statements(input);
			if (input[tokenCount].token.equals("endsym")){
				//System.out.println("Endsym found");
				tokenCount++;
				if (input[tokenCount].token.equals("period")){
					//System.out.println("Period found");
					tokenCount++;
					}
				}
			else{
				System.out.println("Endsym not found");
				}
			}
		else{
			System.out.println("Beginsym missing");
			}		
		}
		
		public static void optional_statements(bracketNode[] input){
			statement_list(input);
		}
		
		public static void statement_list(bracketNode[] input){
			if (input[tokenCount].token.equals("ifsym")){
				System.out.println("ifsym found");
				tokenCount++;
				if (input[tokenCount].token.equals("lparen")){
					//System.out.println("lparen found");
					tokenCount++;
					if_statement_list(input);
					if (input[tokenCount].token.equals("rparen")){
						//System.out.println("rparen found");
						System.out.println("L1:");
						tokenCount++;
						if (input[tokenCount].token.equals("thensym")){
							//System.out.println("Thensym found");
							System.out.println("L2:");
							tokenCount++;
							startParse(input);
						}
					}
					else{
						System.out.println("rparen not found");
					}
				}
				else {
					System.out.println("lparen not found");
				}
			}
			else{
				startParse(input);
			}
		}
		
		public static void if_statement_list(bracketNode[] input){
			if (input[tokenCount].token.equals("identifier")){
				//System.out.println("ID found");
				tokenCount++;
				if (input[tokenCount].lexeme.equals("<")){ //add more here for more if stmnt exprs
					//System.out.println("Opsym found");
					tokenCount++;
					if (input[tokenCount].token.equals("number")){
						//System.out.println("number found");
						System.out.println("jump L1"); //error check this
						tokenCount++;
					}
				}
			}
			else{
				System.out.println("Missing expression inside of IF statement");
			}
		}
	
		/////////////////////////////////////
	public static void startParse(bracketNode[] input){ //START AREA
		
		if (!input[tokenCount].token.equals("endsym")){
			if (input[tokenCount].token.equals("readsym")){
				read(input);
				semicolonCheck(input);
				startParse(input);
			}
			
			else if(input[tokenCount].token.equals("elsesym")){
				else_check(input);
				startParse(input);
			}
			
			else if (input[tokenCount].token.equals("writesym")){
				write(input);
				semicolonCheck(input);
				startParse(input);
			}
			
			else if (input[tokenCount].token.equals("writelnsym")){
				writelnsym(input);
				semicolonCheck(input);
				startParse(input);
			}
			
			else{
				var_check(input);
				equals_check(input);
				expr(input);
				semicolonCheck(input);
				startParse(input);
			}
		}
	}
	
	public static void writelnsym(bracketNode[] input){
		tokenCount = tokenCount + 8;
		System.out.println("li $v0, 4\nla $a0, "+ msg(msgint)+ "\nsyscall");
	}
	
	public static void read(bracketNode[] input){
		if (input[tokenCount].token.equals("readsym")){
			System.out.println("li $v0, 12\nsyscall");
			tokenCount++;
			tokenCount++;
			if (input[tokenCount].token.equals("identifier")){
				if (iTable.containsKey(input[tokenCount].lexeme)){
					//System.out.println("Identifier Key found");
					System.out.println("sb $v0, "+input[tokenCount].lexeme+"\nsyscall");
					tokenCount++;
					tokenCount++;
				}
				else {
					System.out.println("ERROR: ID not declared: " + input[tokenCount].lexeme);
					System.exit(0);
				}
			}
		}
	}
	
	public static void else_check(bracketNode[] input){
		if (input[tokenCount].token.equals("elsesym")){
			System.out.println("elsesym found");
			//System.out.println("L2:");
			tokenCount++;
		}
	}
	
	public static void write(bracketNode[] input){
		if (input[tokenCount].token.equals("writesym")){
			//System.out.println("Write found");
			tokenCount++;
			if (input[tokenCount].token.equals("identifier")){
				if (iTable.containsKey(input[tokenCount].lexeme)){
					//System.out.println("Identifier found");
					System.out.println("li $v0, 4\nla $a0, "+input[tokenCount].lexeme+"\nsyscall");
					tokenCount++;
				}
				else{
					System.out.println("ERROR: ID not declared: " + input[tokenCount].lexeme);
					System.exit(0);
				}
			}
		}
	}
	
	public static void semicolonCheck(bracketNode[] input){
		if (input[tokenCount].lexeme.equals(";")){
			//System.out.println("semicolon found");
			tokenCount++;
		}
		else{
			System.out.println("semicolon missing on input number: "+ tokenCount);
		}		
	}
	
	public static void var_check(bracketNode[] input){
		if (input[tokenCount].token.equals("identifier")){
			if (iTable.containsKey(input[tokenCount].lexeme)){
				//System.out.println("Variable found");
				//System.out.println("$t1");
				tokenCount++;
			}
			else {
				System.out.println("ERROR: ID not declared: " + input[tokenCount].lexeme);
				System.exit(0);
			}
		}
	}
	
	public static void equals_check(bracketNode[] input){
		if (input[tokenCount].lexeme.equals("=")){
				System.out.println("Equals Found");
				tokenCount++;
			}
		else {
				System.out.println("ERROR: Equals not found");
				System.exit(0);
			}
		}
		
	public static void expr(bracketNode[] input){
			if (input[tokenCount].token.equals("identifier")){
				if (iTable.containsKey(input[tokenCount].lexeme)){
					//System.out.println("identifier Found");
					System.out.println("la $t0, " + input[tokenCount].lexeme);
					System.out.println("syscall");
					tokenCount++;
				}
				else{
					System.out.println("ERROR: ID not declared: " + input[tokenCount].lexeme);
					System.exit(0);
				}
				else if (input[tokenCount].lexeme.equals("+")){
					//System.out.println("Addop Found");
					tokenCount++;
					if (input[tokenCount].token.equals("identifier")){
						if (iTable.containsKey(input[tokenCount].lexeme)){
							if (iTable.get(input[tokenCount].lexeme) == iTable.get(input[tokenCount-2].lexeme)){
								//System.out.println("identifier Found");
								System.out.println("la $t1, " + input[tokenCount].lexeme);
								System.out.println("add, $t3, $t0, $t1");
								System.out.println("syscall");
								System.out.println("sb $t3, "+ input[tokenCount-4].lexeme);
								tokenCount++;
							}
							else{
								System.out.println("ERROR: Incompatible types for variables: "+ input[tokenCount].lexeme + " & " + input[tokenCount-2].lexeme);
								System.exit(0);
							}
							
							
						}
						else {
							System.out.println("ERROR: ID not declared: " + input[tokenCount].lexeme);
							System.exit(0);
						}
					}
					
					else if (input[tokenCount].lexeme.equals("*")){
						//System.out.println("Addop Found");
						tokenCount++;
						if (input[tokenCount].token.equals("identifier")){
							if (iTable.containsKey(input[tokenCount].lexeme)){
								if (iTable.get(input[tokenCount].lexeme) == iTable.get(input[tokenCount-2].lexeme)){
									//System.out.println("identifier Found");
									System.out.println("la $t1, " + input[tokenCount].lexeme);
									System.out.println("mult, $t3, $t0, $t1");
									System.out.println("syscall");
									System.out.println("sb $t3, "+ input[tokenCount-4].lexeme);
									tokenCount++;
								}
				}
			}
			
			if (input[tokenCount].lexeme.equals("/")){
				//System.out.println("Addop Found");
				tokenCount++;
				if (input[tokenCount].token.equals("identifier")){
					if (iTable.containsKey(input[tokenCount].lexeme)){
						if (iTable.get(input[tokenCount].lexeme) == iTable.get(input[tokenCount-2].lexeme)){
							//System.out.println("identifier Found");
							System.out.println("la $t1, " + input[tokenCount].lexeme);
							System.out.println("div, $t3, $t0, $t1");
							System.out.println("syscall");
							System.out.println("sb $t3, "+ input[tokenCount-4].lexeme);
							tokenCount++;
						}
		}
		
		if (input[tokenCount].lexeme.equals("-")){
			//System.out.println("Addop Found");
			tokenCount++;
			if (input[tokenCount].token.equals("identifier")){
				if (iTable.containsKey(input[tokenCount].lexeme)){
					if (iTable.get(input[tokenCount].lexeme) == iTable.get(input[tokenCount-2].lexeme)){
						//System.out.println("identifier Found");
						System.out.println("la $t1, " + input[tokenCount].lexeme);
						System.out.println("sub, $t3, $t0, $t1");
						System.out.println("syscall");
						System.out.println("sb $t3, "+ input[tokenCount-4].lexeme);
						tokenCount++;
					}
	
	public static void variable(bracketNode[] input){ //variable done;
		if (input[tokenCount].token.equals("identifier")){
			System.out.println("Identifier found");
			if (!iTable.containsKey(input[tokenCount].lexeme)){
				System.out.println("ERROR: Integer "+ input[tokenCount].lexeme + " was not declared.");
				}
			tokenCount++;
			expr(input);
		}
	}
	
	public static void unsignedNumber(bracketNode[] input){ //DONE
		if (input[tokenCount].token.equals("number")){
		System.out.println("unsigned number found");
		tokenCount++;
		}
	}
	
	public static void expr(bracketNode[] input){ //EXPR DONE
		term(input);
		if (input[tokenCount].lexeme.equals("+")){
			System.out.println("+ (adop) found");
			tokenCount++;
			term(input);
		}
		
		else if (input[tokenCount].lexeme.equals("-")){
			System.out.println("- (adop) found");
			tokenCount++;
			term(input);
		}
		
	}
	
	public static void term(bracketNode[] input){ //TERM DONE
		factor(input);
		
		if (input[tokenCount].lexeme.equals("*")){
			System.out.println("* (multop) found\n");
			tokenCount++;
			factor(input);
		}
		
		else if (input[tokenCount].lexeme.equals("/")){
			System.out.println("* (multop) found");
			tokenCount++;
			factor(input);
		}
		
	}	
	
		
	public static void factor(bracketNode[] input){ //This starts the parsing	
		if (input[tokenCount].token.equals("identifier")){
			variable(input);
		}
		
		else if(input[tokenCount].token.equals("number")){
			unsignedNumber(input);
		}
		
		else if (input[tokenCount].token.equals("lparen")){
			System.out.println("lparen found");
			tokenCount++;
			expr(input);
			
			
			if (input[tokenCount].token.equals("rparen")){
				System.out.println("rparen found");
				tokenCount++;
			}
		}
	}*/
	/////////////////////////////////////////////////////////////
		
	public static void main(String args[]) { 
		try { //this section takes the tokenized input then seperates the token and lexeme to put into a struct
			InputStream ins = null; // raw byte-stream
			Reader r = null; // cooked reader
			BufferedReader br = null; // buffered for readLine()
		    String s;
		    ins = new FileInputStream("ParseInput.txt");
		    r = new InputStreamReader(ins, "UTF-8"); // leave charset out for default
		    br = new BufferedReader(r);
			bracketNode inputNodes[] = new bracketNode[100];
			int counter = 0; //may need to declare int here
		    while ((s = br.readLine()) != null) { //while loop putting everything into struct
				String[] values = s.split("\\s+");
				
				inputNodes[counter] = new bracketNode();
				inputNodes[counter].token = values[0];
				inputNodes[counter].lexeme = values[1];
				counter = counter + 1;
				}
				
				program(inputNodes);
				if (tokenCount+1!=counter){
					System.out.println("Fatal Error at line "+ tokenCount);
					System.exit(0);
				}
			}
			
		
		catch (Exception e)
		{
		    System.out.print("The file was not found. The program will now stop\n"); // exception message
		
		}
		
		System.out.println("li $v0, 10\nsyscall");
		//Here is where extra code can go
	}	
}