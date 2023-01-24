/*Name: Alvie Thai
 * Instructor: Micheal Scherger
 * Due Date: Oct 11
 * This is the main method, used to read command from scanner and process the command

 */
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
class LTE {
	static Buffer buffer=new Buffer();
	static Buffer clipboard=new Buffer();
	static boolean mode=false;
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        String inp;
        int count=1;
		if(args.length==1){
			String temp=args[0];
			CommandLine now=new CommandLine("r "+temp);
			now.initialize();
			read(now);
			
		}
		if(args.length>1){
			System.out.println("Error: only 1 file accepted");
			sc.close();
			return;
		}
		System.out.print("<LTE:"+buffer.getFileName()+":"+count+">");
		count++;
        while(!((inp=sc.nextLine()).equals("q!"))) {//check if the user want to quit
        	
        	if(!inp.equals("")) {
        	CommandLine cd=new CommandLine(inp);
       	 	cd.initialize();
       	 	if((cd.command).equals("q")) {
       	 	 if(buffer.getDirty()==true) {
        		 System.out.println("save the current buffer to a file");
        		 
        	 }
       	 	 
        	 else {
        		 break;
        	 }
       	 	   	 	}
       	 	
        	process_command(cd,sc,count);
			count++;

        }

        	}	

        }
          
    public static void process_command(CommandLine cd,Scanner sc,int count) {//process the command
         boolean temp=cd.checkInp();
         if(temp) {
    	 switch(cd.command) {
    	 case "h"://display help screen
    		 help();
    		 break;
    	 case"r"://read the file
    		 read(cd);
    		 break;
    	 case "w"://write on file
    		 write(cd);
    		 break;
    	 case "f"://change file name
    		 change(cd);
    		 break;
    	 case "t"://go to first line
    		 gotoFirst();
    		 break;
    	 case "b"://go to bottom line
    		 gotoLast();
    		 break;
    	 case "g"://go to indicated line
    		gotoLine(cd);
    		 break;
    	 case "-"://go to previous line
    		 goToPreviousLine();
    		 break;
    	 case "+"://go to next line
    		goToNextLine();
    		 break;
    	 case "="://print current line num
    		 printCurrentNum();
    		 break;
    	 case "p"://print current line
    		 print();
    		 break;
    	 case "n"://toggle line display
    		 mode=toggle(mode);
    		 break;
    	 case "#"://print to total line and char
    		printLineCharNum();
    		 break;
    	 case "pr"://print range
    		 printRange(cd);
    		 break;
    	 case "?"://search for pattern above
    		 searchPaA(cd,sc);
    		 break;
    	 case "/"://search pattern below
    		 searchPaB(cd,sc);
    		 break;
    	 case "s"://substitute
    		substitute(cd);
    		break;
    	 case "sr":// substitute range
    		 substituteRange(cd);
    		 break;
    	 case "d":
    		 deleteLine();//delete current line
    		 break;
    	 case "dr":
    		deleteRange(cd);
    		 break;
    	 case "c"://copy current line
    		 copy();
    		 break;
    	 case "cr"://copy range
    		 copyRange(cd);
    		 break;
    	 case "pa"://paste above
    		 pasteAbove();
    		 break;
    	 case "pb"://paste below
    		 pasteBelow();
    		 break;
    	 case "ia" ://insert above
    		insertAbove(sc);
    		 break;
    	 case "ic"://insert on current line
    		replace(sc);
    		 break;
    	 case "ib"://insert below current
    		insertBelow(sc);
    		 
    		 break;
    		 
    	 }
    	 }
    	 
    	 
         
         else {
        	 System.out.println("Wrong syntax");
         }
         if(!mode) {
         System.out.print("<LTE:"+buffer.getFileName()+":"+count+">");
         }
         if(mode) {
             System.out.print("<LTE:"+buffer.getFileName()+":"+count+":"+buffer.ind()+">");
 
         }
    }
    public static void help() {
    	
    	for(int i=0;i<3;i++) {
    	System.out.print("--------------------------");
    	}
    	System.out.println("");
    	System.out.println("|Command|"+"       Arguments       |"+"        Description                         |");    	
    	for(int i=0;i<3;i++) {
        	System.out.print("--------------------------");
       }
    	System.out.println("");
    	System.out.println("|  h    |"+"                       |"+"          display help                      |");
    	System.out.println("| r,f   |"+"      file spec        |"+"    read file or change name of buffer      |");
    	System.out.println("| w     |"+"                       |"+"   write lines to current buffer file       |");
    	System.out.println("|q, q!  |"+"                       |"+"quit line editor/  quiit without saving     |");
    	System.out.println("|t, b   |"+"                       |"+"    go to first/ last line                  |");
    	System.out.println("|  g    |"+"       num             |"+"    go to line number                       |");
    	System.out.println("| -, +  |"+"                       |"+" go to previous/next line                   |");
    	System.out.println("|=, p   |"+"                       |"+" print current line number/current line     |");
    	System.out.println("|  n    |"+"                       |"+" togglle line number displayed              |");
    	System.out.println("|  #    |"+"                       |"+" print number of lines and characters       |");
    	System.out.println("| pr    |"+"  start stop           |"+" print serveral lines                       |");
    	System.out.println("|?, /   |"+"   pattern             |"+" search backwards/forwards for pattern      |");
    	System.out.println("|s      |"+" text1 text2           |"+"substitute all text1 to text2 current line  |");
    	System.out.println("| sr    |"+"text1 text2 start stop |"+"substitute all text1 to text2 start to stop |");
    	System.out.println("| d,c   |"+"                       |"+"cut/ copy current line to clipboard         |");
    	System.out.println("|dr, cr |"+" start stop            |"+"cut/ copy from start to stop to clipboard   |");
    	System.out.println("| ia,ib |"+"                       |"+"insert text above/below current line until .|");
    	System.out.println("| pa,pb |"+"                       |"+"paste clipboard above/below current line    |");
    	System.out.println("| ic    |"+"                       |"+"insert text at current line untii . appear  |");
    	for(int i=0;i<3;i++) {
        	System.out.print("--------------------------");
        	}
    		System.out.println("");
    	
    }
   public static void read(CommandLine cd) {//read file method
	   if(buffer.getDirty()) {
   		System.out.println("Save your work first");
   		return;
   	}
	  
	   String fileName=cd.line1;    	
    	if (fileName != null) {
    		try {
    			BufferedReader in = new BufferedReader(new FileReader(fileName));
    			String line;
				buffer.clear();
				buffer.setFile(fileName);
    			while ((line=in.readLine()) != null) {
    				buffer.push(line);
    			}
			in.close();
    	}catch(IOException e) {
    		System.out.println("==> FILE DOES NOT EXIST <==");
			return;
    	}
    	
    }
	

    	
    }
     public static void write(CommandLine cd) {// write file method
    	  
      if(!buffer.getDirty()){
			return;
	  }
		try {
        	 if(buffer.isEmpty()) {
        		 System.out.println("==>BUFFER IS EMPTY<==");
        		 //buffer.setDirty(false);
        		 return;
        	 }
             // Creates a FileWriter
          String path=buffer.getFileName();
        	 FileWriter output
                 = new FileWriter(path);
   
             // Writes the string to the file
        	 
        	 int curIndex=buffer.dlline.getIndex();
             for(int i=0;i<buffer.dlSize();i++) {
                 output.write(buffer.peek(i)+"\n");

             }
             buffer.dlline.seek(curIndex);
             // Closes the writer
             buffer.setDirty(false);
             output.close();
         }
   
         catch (Exception e) {
             e.getStackTrace();
             System.out.println("==>NO FILE EXISTS<==");
             //buffer.setDirty(false);
         }
     }
     public static void change(CommandLine cd) {// change name of buffer
    	 String newPath=cd.line1;
    	 buffer.setFile(newPath);
     }
     public static boolean toggle(Boolean stage) {//toggle line display
    	 return !stage;
     }
     public static void printLineCharNum() {//print number of line and character
    	 int line= buffer.dlline.getSize();
		 int cha=buffer.countChar();
		 System.out.println("Total line: " +line);

		 System.out.println("Total character: " +cha);
     }
     public static void gotoFirst() {//go to first line
    	 buffer.firstL();
    	 
     }
     public static void gotoLine(CommandLine cd) {//go to specified line
    	 int start=cd.start;
		 if(!buffer.jumpL(start)){
			 System.out.println("‘==>> RANGE ERROR - num MUST BE [1..n] <<==");
		 }
     }
     
     public static void gotoLast() {// go to last line
    	 buffer.lastL();
     }
     public static void goToPreviousLine() {//go to previous line
    	 if(!buffer.prevLine()) {  
     		System.out.println("==>> ALREADY AT TOP OF BUFFER <<==");
     		 }
     }
     public static void goToNextLine() {//go to next line
    	 if(!buffer.nextLine()) {
     		System.out.println("==>> ALREADY AT BOTTOM OF BUFFER <<==");
     		 }
     }
     public static void printCurrentNum() {//print the current line number
		 System.out.println(buffer.lineNum());

     }
     public static void print() {//print current line
    	 buffer.print();
     }
     public static void substitute(CommandLine cd) {//search and substitue word
    	 String text1=cd.line1;
		 String text2=cd.line2;
		String replaced= buffer.searchIndex(text1,text2,buffer.dlline.getData());
		buffer.dlline.setData(replaced);
		buffer.setDirty(true);
     }
     public static void substituteRange(CommandLine cd) {//substitute word in a range
		 buffer.setNew(cd.line1, cd.line2, cd.start,cd.end);
		 buffer.setDirty(true);

     }
     public static void deleteLine() {//delete the current line
		 buffer.deleteLine(clipboard);
		 buffer.setDirty(true);
     }
     public static void deleteRange(CommandLine cd) {//delete the range of line
    	 int begin=cd.start;
		 int endL=cd.end;
		 buffer.delLines(begin,endL,clipboard);
		 buffer.setDirty(true);
     }
     public static void printRange(CommandLine cd) {
    	 int start1=cd.start;
		 int end=cd.end;
		
		 buffer.printMu(start1,end);
     }
     public static void copy() {
    	 if(clipboard.getDirty()) {
    		 clipboard.clear();
    	 }
    	 String data=buffer.getData();
    	 
    	 clipboard.append(data);
    	 clipboard.setDirty(true);
     }
     public static void copyRange(CommandLine cd) {
    	 buffer.copyRange(cd.start,cd.end, clipboard);
    	 clipboard.setDirty(true);
     }
     public static void pasteAbove() {//paste above
		
		buffer.insertAt(clipboard);
		 buffer.setDirty(true);
		

     }
     public static void pasteBelow() {//paste below 
		 buffer.appendAt(clipboard);
		 buffer.setDirty(true);
     }
     public static void insertAbove(Scanner sc) {//insert above current line
    	 String tline;
		
		 int loc=buffer.getIndex();
		 
		 if(buffer.size()==0){
			insertBelow(sc);
			if(buffer.size()!=0){
				buffer.jumpL(1);
				buffer.setDirty(true);
				return;
			}
		 }
		 else{
		 while(!(tline=sc.nextLine()).equals(".")) {
			
			buffer.insertAbove(tline);
			 
		 }
		}
		 
		// buffer.insertAbove(tline);
		if(buffer.size()!=0){
		 buffer.jumpL(loc+1);
		 buffer.setDirty(true);
		}
     }
     public static void replace(Scanner sc) {//replace current line with insert
		
    	 int loc=buffer.getIndex();
    	 insertBelow(sc);
		 if(buffer.size()!=0){
    	 buffer.jumpL(loc+1);    	 
		 buffer.setDirty(true);
		 }
     }
     public static void insertBelow(Scanner sc) {// insert below the current line
		 String tline;
		 while(!(tline=sc.nextLine()).equals(".")) {

			 buffer.append(tline);
			 
		 }
		 buffer.setDirty(true);
     }
     
    public static void searchPaA(CommandLine cd,Scanner sc) {//search for pattern above
    	buffer.searchPatA(cd.line1,sc);
        

    }
    public static void searchPaB(CommandLine cd, Scanner sc) {//search for pattern on current line and below
    	buffer.searchPatB(cd.line1,sc);
    }
   
}
    
