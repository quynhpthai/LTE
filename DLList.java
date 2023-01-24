
/*Name: Alvie Thai
 * Instructor: Micheal Scherger
 * Due Date: Oct 11
 * This is the dllist method, used to store the lines in the buffer */
class DLList<T> {

    private class DLListNode<T>{ //because private, do not need getters/setters

        //data members

        public T data;

        public DLListNode<T> previous;

        public DLListNode<T> next;

        DLListNode(T value){

            data = value;

            previous = null;

            next = null;

        }

        //member functions (Methods)
        
   

    }

//=======================================

 

    //data members

    private DLListNode<T> front;

    private DLListNode<T> back;

    private DLListNode<T> current;

    int size;

    int index;

 

    //=======================================

    //member functions (Methods)

    //=======================================

 

    //deafult constructor

    public DLList(){

        clear();

    }

 

    //copy constructor (deep copy -> two separete lists with the exact same info in the,, simply seperate)

    public DLList(DLList<T> other){

    	this.front=other.front;
    	this.back=other.back;
    	this.current=other.current;
    	this.size=other.size;
    	this.index=other.index;

    }

 

    //clear list method, its purpose is to set front to null, back to null, current to null, size to 0, and index to -1

    public void clear(){

        front = null;

        back = null;

        current = null;

        size = 0;

        index = -1;

    }

 

    //get size method

    public int getSize(){

        return size;

    }

 

    //get index method

    public int getIndex(){

        return index;

    }

   

    //is empty method

    public boolean isEmpty(){

        return getSize() == 0;

        //also, return size == 0;

    }

 

    //is at first node method, is current reference poiting at first node?

    public boolean atFirst(){

        return getIndex() == 0;

    }

 

    //is at last node method

    public boolean atLast(){

        return (getIndex() == getSize()-1);

    }

 

    //get data at current method return data or reference to data

    public T getData(){

        if(!isEmpty()){

            return current.data;

        }

        else{

            return null;

        }

    }

 

    //set data at current method, take something of type T and return a reference if successful

    public T setData(T x){

        if(!isEmpty()){

            current.data = x;

            return x;

        }

        else{

            return null;

        }

    }

 

    //seek to first node Method

    public boolean first(){

        return seek(0);

    }

 

    //seek to the next node Method, if fail return false

    public boolean next(){

        return seek(getIndex() + 1);

    }

 

    //seek to previous node Method

    public boolean previous(){

        return seek(getIndex() -1);

       

    }

 

    //go to last node

    public boolean last(){

        return seek(getIndex());

       

    }

 

    //seek method

    public boolean seek(int loc){

        //local var

        boolean retval = false;

 

        //test is the list empty?

        if(isEmpty()){

            retval = false;
            return retval;

        }

        //is loc in range?

        else if(loc < 0 || loc >= getSize()){

            retval = false;
            return retval;

        }

 

        //is loc == 0?

        else if(loc == 0){

            current = front;

            index = 0;

            retval = true;
            

        }

       

        //is loc == last index?

        else if(loc == getSize() - 1){

            current = back;

            index = getSize() -1;

            retval = true;

        }

        //is loc < current index then

        if(loc < getIndex()){

            for(; getIndex() != loc; index--){ //no initialization step, stopping condition is when current index is = to location, if not move to previous node
            	//System.out.println(index);
                current = current.previous;

            }

            retval = true;

        }

 

        //is loc > current index?

        else if(loc > getIndex()){

            for(; getIndex() != loc; index++) {
            	//System.out.println(index);

                current = current.next;
            }
            //System.out.println(index);
            retval = true;

        }

 

        //else ... loc is at the current index ... nothing to do

        else

            retval = true;

 

       return(retval);

    }
    
    public void printList() {//list tranversal
    	current=front;
    	index=0;
    	while(current!=null) {
    		System.out.println(index+1+" "+current.data);
    		current=current.next;
    		index++;
    	}
    	seek(0);
    }
    //insert first method
    
   public boolean insertFirst(T item){//insert of first node
	   if(size==0) {
		   return insertAt(item);
	   }
        seek(0);
        return(insertAt(item));

    }

 


   public boolean insertAt(T item){//insert before the node
	   boolean val=false;
	   DLListNode<T> newHead=new DLListNode<T>(item);
	   if(size==0) {
		   val=true;
		   front=newHead;
		   current=newHead;
		   back=newHead;
		   index++;
		   size++;
	   }
	   else if(index==0) {
		   val=true;
		   newHead.next=front;
		   newHead.previous=null;

		   if(front!=null) {
		   front.previous=newHead;
		   }
		   front=newHead;
		   size++;
		   current=newHead;
		   
	   }
	   else {
		   newHead.next=current;
		   newHead.previous=current.previous;
		   current.previous.next=newHead;
		   current.previous=newHead;
		   current=newHead;
		   size++;
		   
	   }
	   
	   return val;
 

    }
   public boolean appendAt(T item) {//insert after the current node
	   boolean val=false;
	   DLListNode<T> newTail=new DLListNode<T>(item);
	   if(size==0) {
		   front=newTail;
		   current=newTail;
		   back=newTail;
		   index++;
		   size++;
           return true;
	   }
	   else if (index==size-1) {
		   val=true;
		   newTail.previous=back;
		   newTail.next=null;
		   if(back!=null) {
		   back.next=newTail;
		   }
		   index++;
		   size++;
		   current=current.next;
		   back=newTail;
	   }
	   else {
		   val=true;
		   newTail.previous=current;
		   newTail.next=current.next;
		   current.next.previous=newTail;
		   current.next=newTail;
		   current=newTail;
		   size++;
		   index++;
	   }
	   return val;
   }
   
   public boolean appendLast(T item) {//add on the last node
	   if(size==0)return appendAt(item);
	   seek(size-1);
	   return appendAt(item);
   }
   public T printHead(){//return the head node
	   return front.data;
   }
  
   

    //delete first method

   public boolean deleteFirst(){
	   seek(0);
	   return deleteAt();
 

    }

 

    //delete at current location method

   public boolean deleteAt(){
	   boolean val=false;
	 
	   if(size==0) { 
		   return false;
	   }
	   if((index==0)&&(size==1)){
        clear();
        return true;
       }
	   else if(index==0) {
		   //seek(0);
		   current=current.next;
		 //  System.out.println(current.data);
            size--;
		   front.next=null;
		   front.previous=null;
		   
		   front=current;
		   
		 
	   }
	   else   if(index==size-1) {
		   current=current.previous;
		   back.previous=null;
		   back.next=null;
		   back=current;
		   size--;
           index--;
		   
	   }else {
		   current.previous.next=current.next;
		   current.next.previous=current.previous;
		   current=current.next;
		   size--;
	   }
	   
	   return val;
    }

 

    //delete last method

    public boolean deleteLast(){
    	seek(size-1);
    	return deleteAt();
 

    }
 
    

}