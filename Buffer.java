/*Name: Alvie Thai
 * Instructor: Micheal Scherger
 * Due Date: Oct 11
 * This is the buffer class, used to stored current file name, the dllist and the dirty bit

 */
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Buffer {
	//require data structure
	private String curFile;
	 DLList<String> dlline;
	 private boolean dirty;
	public Buffer() {//the constructor to start a new buffer
		curFile=null;
		dlline=new DLList<String>();
		dirty=false;
	}
	public void empty() {
		curFile=null;
		dlline=null;
		dirty=false;
	}
	public int size(){
		return dlline.getSize();
	}
	public boolean isEmpty() {
		if(curFile==null) {
			return true;
		}
		else {
			return false;
		}
	}
	public int getIndex() {
		return dlline.getIndex();
	}
	public String getFileName() {//getters method for filename
		return curFile;
	}
	public void setFile(String newFile) {//set filename to a new one
		curFile=newFile;
	}
	public boolean getDirty() {//get the dirty bit
		return dirty;
	}
	public void setDirty(boolean a) {//set the dirty bit
		dirty=a;
	}
	public  Buffer(String file) {//set the current buffer to a file
		curFile=file;
		dirty=true;
	}
	public String getData() {//get the data in the current node
		return dlline.getData();
	}
	
	public  void push(String line) {//push the data after the last line
		dlline.appendLast(line);
	}
	public int ind() {//get the current line
		return dlline.getIndex()+1;
	}
	
	public  String peek(int k) {//return the content of line number k
		dlline.seek(k);
		return dlline.getData();
	}
	public int dlSize() {//take the total number of line
		return dlline.getSize();
	}
	public void clear() {//clear the buffer
		dlline.clear();
		curFile=null;
		dirty=false;
	}
	
	public void firstL() {//go to first line
		dlline.seek(0);
	}
	public void lastL() {//go to last line
		dlline.seek(dlline.getSize()-1);
		//System.out.println(dlline.getSize());
	}
	public void print() {//print the currentline
		System.out.println(dlline.getData());
	}
	public boolean jumpL(int k) {//go to line number k
		if(dlline.seek(k-1)){
			return true;
		}
		return false;
		//System.out.println(dlline.getData());
	}
	public boolean nextLine() {//go to next line
		int index=dlline.getIndex();
		return dlline.seek(index+1);
	}
	public boolean prevLine() {//go to previous line
		int index=dlline.getIndex();
		return dlline.seek(index-1);
	}
	public int lineNum() {// get the current line
		return dlline.getIndex()+1;
	}
	public void printMu(int start,int end) {//print line in the range from start to end
		if((start<0)||(end>dlline.getSize())) {
			System.out.println("==>> RANGE ERROR -start stop MUST BE [1..n] <<==");
			return;
		}
		int curIndex=dlline.getIndex();
		for(int i=start-1;i<=end-1;i++) {
			dlline.seek(i);
			print();
		}
		dlline.seek(curIndex);
	}
	public int countChar() {//count the total character of the file
		int currentIn=dlline.getIndex();
		int count=0;
    	for(int i=0;i<=dlline.getSize()-1;i++) {
    		dlline.seek(i);
    		count=count+dlline.getData().length();
    	}
    	dlline.seek(currentIn);
    	return count;
	}
	public String searchIndex(String text1,String text2,String currentLine) {//this method replace the occurence of text1 to text2 in the currentline
		if(currentLine.length()<text1.length()) {
			return currentLine;
		}
		if(currentLine.length()==text1.length()) {
			if(currentLine.equals(text1)) {
				return text2;
			}
		}
		int length=text1.length();
		String comp=currentLine.substring(0,length);
		if(comp.equals(text1)) {
			return text2+searchIndex(text1,text2,currentLine.substring(length));
		}
		return currentLine.substring(0,1)+searchIndex(text1,text2,currentLine.substring(1));
		
	}
	public void setNew(String text1,String text2,int start,int end) {//return the occurence of text1 to text2 from start to end
		if((start>end)||(start<1)||(end>dlline.getSize())) {
			System.out.println("==>> INDICES OUT OF RANGE <<==");
			return;
		}
		int index=dlline.getIndex();
		for(int i=start-1;i<=end-1;i++) {
			dlline.seek(i);
			dlline.setData(searchIndex(text1,text2,dlline.getData()));
		}
		dlline.seek(index);
	}
	public void deleteLine(Buffer clip) {//delete the current line and put it into clipboard
		if(clip.dirty==true) {
			clip.clear();
		}
		clip.push(dlline.getData());
		clip.setDirty(true);
		dlline.deleteAt();
	}
	public void delLines(int start,int end, Buffer clip) {//delete lines from start to end and put it into clipboard
		if((start>end)||(start<1)||(end>dlline.getSize())) {
			System.out.println("==>> INDICES OUT OF RANGE <<==");
			return;
		}		
		dlline.seek(start-1);
		if(clip.dirty==true) {
			clip.clear();
		}
		for(int i=start-1;i<=end-1;i++) {
		
			clip.push(dlline.getData());
			dlline.deleteAt();
		}
		clip.setDirty(true);

	}
	public void copyRange(int start,int end,Buffer clip) {
		if((start>end)||(start<1)||(end>dlline.getSize())) {
			System.out.println("==>> INDICES OUT OF RANGE <<==");
			return;
		}		
		dlline.seek(start-1);
		if(clip.dirty==true) {
			clip.clear();
		}
		for(int i=start-1;i<end;i++) {
			dlline.seek(i);
			String line=dlline.getData();
			clip.append(line);
		}
	}
	public void insertAt(Buffer clip) {//paste the content of the clipboard above the current line
	
		if(clip.dlline.isEmpty()) {
			System.out.println("==> CLIPBOARD EMPTY <<==");
			return;
		}
		
		for(int i=clip.dlline.getSize()-1;i>=0;i--) {
			clip.dlline.seek(i);
			dlline.insertAt(clip.dlline.getData());
			
			
		}
		
	}
	public void insertAbove(String inp) {//insert the contents above the current line
		int ind=dlline.getIndex();
		dlline.insertAt(inp);
		dlline.seek(ind+1);
	}
	public void appendAt(Buffer clip) {//copy the content of clipboard after the current line 
		if(clip.dlline.isEmpty()) {
			System.out.println("==> CLIPBOARD EMPTY <<==");
			return;
		}
		for(int i=0;i<=clip.dlline.getSize()-1;i++) {
			clip.dlline.seek(i);
			dlline.appendAt(clip.dlline.getData());
			
			
		}
		
		
	}
	public void delAt() {//delete current line
		dlline.deleteAt();
	}

	public void append(String dat) {//insert the line after the current line
		dlline.appendAt(dat);
	}
	public void searchPatA(String pattern, Scanner sc) {//search for pattern above current line
		if(dlline.getIndex()==0) {
			System.out.println("‘==>> ALREADY AT TOP OF BUFFER <<==");
		}
		int pos=dlline.getIndex();
		Pattern r = Pattern.compile(pattern);
		
		for(int i=dlline.getIndex()-1;i>=0;i--) {
			dlline.seek(i);
			String dat=dlline.getData();
		      Matcher m = r.matcher(dat);
		      if(m.matches()) {
		    	  int line=dlline.getIndex()+1;
		    	 System.out.println("found on line "+line+" to continue searching press \"y\"");
		    	 pos=line-1;
		    	 if(!sc.nextLine().equals("y")) {
		    	 break;
		    	 }
		      }
		      if(i==0) {
		    	  dlline.seek(pos);
		    	  System.out.println("==>> STRING pattern NOT FOUND <<==");
		      }
		     
		}
		
	}
	public void searchPatB(String pattern, Scanner sc) {//search for pattern bellow current line
			int pos=dlline.getIndex();
			Pattern r = Pattern.compile(pattern);
			for(int i=dlline.getIndex();i<dlline.getSize();i++) {
				dlline.seek(i);
				String dat=dlline.getData();
			      Matcher m = r.matcher(dat);
			      if(m.matches()) {
			    	  int line=dlline.getIndex()+1;
				    	 System.out.println("found on line "+line+" to continue searching press \"y\"");
				    	 pos=line-1;
				    	 if(!sc.nextLine().equals("y")) {
				    	 break;
				    	 }
			      }
			      if(i==dlline.getSize()-1) {
			    	  dlline.seek(pos);
			    	  System.out.println("==>> STRING pattern NOT FOUND <<==");
			      }
			     
			}
			
		}
	}
