import java.io.*;

public class MainDemo {
    
    public static void main(String[] args) throws IOException {
  
         int i, j;
         int processes, resources;
         int[][] allocatedMatrix;
         int[][] maxMatrix;
         int[] available;
         int[]	total;
  
         File file = new File("D:/Assignment/input.txt");
         
        try( BufferedReader buffer = new BufferedReader(new FileReader(file)) ) {		// Try with Resource 
	String line = buffer.readLine();
        	char ch = line.charAt(line.length()-1);
        	processes = Integer.parseInt(String.valueOf(ch));         // Number of Processes
        	System.out.println("Number of Processes : "+processes );

        	line = buffer.readLine();
       	 ch = line.charAt(line.length()-1);
        	resources = Integer.parseInt(String.valueOf(ch));           // Number of Resources
        	System.out.println("\nNumber of Resource Types : "+resources );
        
        	allocatedMatrix = new int[processes][resources];
        	maxMatrix  = new int[processes][resources];
        	available = new int[resources];
        	total = new int[resources];
        
        	buffer.readLine();		// to skip blank lines
        	buffer.readLine();
        
	// Reading the Allocation matrix and storing in the variable allocatedResources
        	for (i = 0; i < processes; i++) {
            		line =  buffer.readLine();
            		String[] data = line.split(" ");
            		for (j = 0; j < resources; j++) {
                			allocatedMatrix[i][j] = Integer.parseInt(data[j]);
            		}
        	}
        
       	 buffer.readLine();		// to skip blank lines
        	buffer.readLine();
        
	// Reading the Maximum Resources matrix and storing in the variable maxResources
       	 for (i = 0; i < processes; i++) {
            	line =  buffer.readLine();
             		String[] data = line.split(" ");
            		for (j = 0; j < resources; j++) {
                			maxMatrix[i][j] = Integer.parseInt(data[j]);
            		}
        	}  

        	buffer.readLine();		// to skip blank lines
        	buffer.readLine();
        
	// Reading the Total Resources data and storing in the variable totalResources
        	line =  buffer.readLine();
        	String[] data = line.split(" ");
        	for (i = 0; i < resources; i++) {
            		total[i] = Integer.parseInt(data[i]);
        	}
  
        	buffer.readLine();		// to skip blank lines
        	buffer.readLine();
        
	// Reading the avaialable Resources data and storing in the variable totalResources
       	 line =  buffer.readLine();
       	data = line.split(" ");
	for (i = 0; i < resources; i++) {
            		available[i] = Integer.parseInt(data[i]);
       	 }
        
       	 System.out.println("\nAllocation");
        	for (i = 0; i < processes; i++) {
            		for (j = 0; j < resources; j++) {
                			System.out.print(allocatedMatrix[i][j]+"  ");
           		}
            		System.out.println();
        	}
        
        	System.out.println("\nMax");
        	for (i = 0; i < processes; i++) {
            		for (j = 0; j < resources; j++) {
                			System.out.print(maxMatrix[i][j]+"  ");
            		}
            	System.out.println();
        	}
    
	System.out.println("\nTotal ");
        	for (i = 0; i < resources; i++) {
            		System.out.print(total[i]+"  ");
        	}
        	System.out.println();    

        	System.out.println("\nAvailable ");
        	for (i = 0; i <resources;  i++) {
            		System.out.print(available[i]+"  ");
        	}
	System.out.println();    
        }
	// calling the Banker Algorithm method
        BankersAlgoImplementation banker = new BankersAlgoImplementation();
        boolean safe = banker.getSystemState(processes, resources, allocatedMatrix, maxMatrix, available);
       
        if (safe) {
                System.out.println("\nThe process is in safe state.");
                 banker.showExecutionOrder();
         }
         else {
                System.out.println("\nThe processes are in unsafe state.");
            }
    }

}
 
class BankersAlgoImplementation {
    int[] executionOrder = new int[5];
    int processes;
    int resources;

    public boolean getSystemState(int processes, int resources, int holdingResources[][], int requestResources[][], int availableResources[]) { 
        
        this.processes = processes;
        this.resources = resources;
        int i, j,k=0;
        int count = 0;
        int running[]= new int[5];
        boolean safe = false, exec=false;
        
         for (i = 0; i < processes; i++) {
            running[i] = 1;
            count++;
        }
         
        while (count != 0) {
            safe = false;
            for (i = 0; i < processes; i++) {
                if (running[i]!=0) {
                    exec = true;
                    for (j = 0; j < resources; j++) {
                        if (requestResources[i][j] - holdingResources[i][j] > availableResources[j]) {
                            exec = false;
                           break;
                        }
                    }
 
                    if (exec) {
                        System.out.println("\nProcess "+(i+1)+" is running.");
                        executionOrder[k++] = i+1;
                        running[i] = 0;
                        count--;
                        safe = true;
                        for (j = 0; j < resources; j++) {
                            availableResources[j] += holdingResources[i][j];
                        }
                        break;
                    }
                }
            }

            if(!safe) {
	return false;
            }

            System.out.print("Available Resources: ");
            for (i = 0; i < resources; i++) {
                System.out.print("  "+availableResources[i]);
            }  
            System.out.println();          
           
          
        }
       return safe;
    }

    public void showExecutionOrder() {
	System.out.println("\nExecution Order of Processes");
	for(int i=0;i<processes;i++) {
		System.out.print("   "+executionOrder[i]);              // Process order is displayed
	}
    }

}


