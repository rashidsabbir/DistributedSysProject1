package raymonds;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Tree {
	private static ArrayList<Process> processes;
	public Tree() throws IOException
	{
		processes = new ArrayList<Process>();
		FileReader fr = new FileReader("tree.txt");
		BufferedReader br = new BufferedReader(fr);
		String input = br.readLine();
		boolean first = true;
		while(input!=null)
		{
			if(first)
			{
				processes.add(new Process(input.substring(1, 2),Process.HolderEnum.Neighbor,false,false));
				processes.get(0).addNeighbor(new Process(input.substring(3, 4),Process.HolderEnum.Neighbor,false,false));
				processes.add(new Process(input.substring(3, 4),Process.HolderEnum.Neighbor,false,false));
				processes.get(1).addNeighbor(new Process(input.substring(1, 2),Process.HolderEnum.Neighbor,false,false));
				first = false;
			}
			else
			{
				int index = 0;
				boolean found=false;
				for(int i=0;i<processes.size();i++)
				{
					if(input.substring(1,2).equals(processes.get(i).getProcessID()))
					{
						found=true;
						index=i;
					}
					
				}
				if(!found)
				{
					processes.add(new Process(input.substring(1, 2),Process.HolderEnum.Neighbor,false,false));
					processes.get(processes.size()-1).addNeighbor(new Process(input.substring(3, 4),Process.HolderEnum.Neighbor,false,false));
				}
				else
				{
					if(!processes.get(index).getNeighbors().contains(new Process(input.substring(3, 4),Process.HolderEnum.Neighbor,false,false)))
					{
						processes.get(index).addNeighbor(new Process(input.substring(3, 4),Process.HolderEnum.Neighbor,false,false));
						System.out.println("CONTAINS 1");
					}
				}
				found=false;
				index = 0;
				for(int i=0;i<processes.size();i++)
				{
					if(input.substring(3,4).equals(processes.get(i).getProcessID()))
					{
						found=true;
						index=i;
					}
				}
				if(!found)
				{
					processes.add(new Process(input.substring(3, 4),Process.HolderEnum.Neighbor,false,false));
					processes.get(processes.size()-1).addNeighbor(new Process(input.substring(1, 2),Process.HolderEnum.Neighbor,false,false));
				}
				else
				{
					if(!processes.get(index).getNeighbors().contains(new Process(input.substring(1, 2),Process.HolderEnum.Neighbor,false,false)))
					{
						processes.get(index).addNeighbor(new Process(input.substring(1, 2),Process.HolderEnum.Neighbor,false,false));
						System.out.println("CONTAINS 2");
					}
				}
			}
			input=br.readLine();
		}
	}
	public static void main(String[] args) throws IOException {
		
	}
}
