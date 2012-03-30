package telnet;

import java.util.ArrayList;

public class CommandList{
	private ArrayList<String> cmds = new ArrayList<String>();
	public int pos;
	
	public String next(){
		pos+=pos==cmds.size()?0:1;
		return pos<cmds.size()?cmds.get(pos):"";
	}
	
	public String prev(){
		pos-=pos==0?0:1;
		return pos>=0?cmds.get(pos):"";
	}
	
	public void add(String cmd){
		if (cmds.size()==0 || !cmds.get(cmds.size()-1).equals(cmd)){
			cmds.add(cmd);
		}
		
		pos = cmds.size();
				
	}
	
	public static void main (String[] args){
		 CommandList c =  new  CommandList();
		 

		 c.add("Test");
		 c.add("Test2");
		 System.out.println(c.prev() + c.pos);
		 System.out.println(c.prev() + c.pos);
		 System.out.println(c.prev() + c.pos);
		 System.out.println(c.next() + c.pos);
		 System.out.println(c.next() + c.pos);
		 
		
	}
}
