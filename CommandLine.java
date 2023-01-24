import java.util.StringTokenizer;

/*Name: Alvie Thai
 * Instructor: Micheal Scherger
 * Due Date: Oct 11
 * This is the maiCommandLine method, used to split standard string to arguments */
public class CommandLine {
	//String input;
	String input;
	String command;
	String[]com;
    boolean stage=true;;
	int start=-1;
	int end=-1;
	String line1="";
	String line2="";
	CommandLine(String input){//constructor method
		this.input=input;
		com=new String[29];//list all the commands
	    com[0]="h";
	    com[1]="r";
	    com[2]="w";
	    com[3]="f";
	    com[4]="q";
	    com[5]="q!";
	    com[6]="t";
	    com[7]="b";
	    com[8]="g";
	    com[9]="-";
	    com[10]="+";
	    com[11]="=";
	    com[12]="n";
	    com[13]="#";
	    com[14]="p";
	    com[15]="pr";
	    com[16]="?";
	    com[17]="/";
	    com[18]="s";
	    com[19]="sr";
	    com[20]="d";
	    com[21]="dr";
	    com[22]="c";
	    com[23]="cr";
	    com[24]="pa";
	    com[25]="pb";
	    com[26]="ia";
	    com[27]="ic";
	    com[28]="ib";
	    
	    		
	    //System.out.println(com[15]);
	}
	public void setInp(String data) {//set the input
		input=data;
	}
	public Boolean checkInp() {//check if input is valid or not
		//StringTokenizer tok=new StringTokenizer(input," ");
		if((checkComm())&&checkArgu()&&stage) {
			return true;
		}
		
		return false;
	}
	public void initialize() {//initialize the tokenizer, split string into small parts
		
	    
		StringTokenizer tok=new StringTokenizer(input," ");
		int count=1;
		while(tok.hasMoreElements()) {
			if(count>5) {
				stage=false;
				return;
			
			}
			switch(count) {
			case 1:
				command=tok.nextToken();
				break;
			case 2:
				line1=tok.nextToken();
				//start=Integer.parseInt(line1);
				break;
			case 3:
				line2=tok.nextToken();
				break;
			case 4:
				try {
				start=Integer.parseInt(tok.nextToken());
				}catch(NumberFormatException e){
					//System.out.println("Wrong Syntax");
					stage=false;
					return;

				}
				break;
			case 5:
				try {
				end=Integer.parseInt(tok.nextToken());
				}catch(NumberFormatException e) {
					//System.out.println("Wrong Syntax");
					stage=false;
					return;
				}
				break;
			}

			
		count++;
		
		}
		
	}
	public boolean checkR() {//check if there is an r
		if(com[1].equals(command)) {
			return true;
		}
		return false;
	}
	public boolean checkComm() {//check if it is a valid command
		for(int i=0;i<29;i++) {
			if(com[i].equals(command)) {
				return true;
			}
		}
		return false;
	}
	public boolean checkArgu() {//check if it is a valid argument
		if((command.equals("h"))||(command.equals("d"))||(command.equals("c"))) {
			if(!((start==-1)&&(end==-1)&&(line1.equals(""))&&(line2.equals("")))) {
				return false;
			}
		}
		for(int i=4;i<=7;i++) {
			if(command.equals(com[i])) {
				if(!((start==-1)&&(end==-1)&&(line1.equals(""))&&(line2.equals("")))) {
					return false;
				}
			}
		}
		for(int i=9;i<=14;i++) {
			if(command.equals(com[i])) {
				if(!((start==-1)&&(end==-1)&&(line1.equals(""))&&(line2.equals("")))) {
					return false;
				}
			}
		}
		for(int i=24;i<=25;i++) {
			if(command.equals(com[i])) {
				if(!((start==-1)&&(end==-1)&&(line1.equals(""))&&(line2.equals("")))) {
					return false;
				}
			}
		}
		if(command.equals("g")) {
			try {
			start=Integer.parseInt(line1);
			}
			catch(NumberFormatException e) {
				
				return false;
			}
			if(!((end==-1)&&(line2.equals("")))) {
				return false;
			}
			
		}
		if((command.equals("pr"))||(command.equals("cr"))||(command.equals("dr"))) {
			try {
			start=Integer.parseInt(line1);
			end=Integer.parseInt(line2);
			}
			catch(NumberFormatException e) {
				return false;
			}
			if(start>end) {
				return false;
			}
			
		}
		if(command.equals("r")) {
			if (!line2.equals("")) {
				return false;
			}
		}
		if(command.equals("s")) {
			if(!((start==-1)&&(end==-1))) {
				return false;
			}
		}
		return true;
	}
	}
