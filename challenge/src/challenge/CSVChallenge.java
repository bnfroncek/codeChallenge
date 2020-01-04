package challenge;

import java.io.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;




public class CSVChallenge {
	public static void ParseCSV(String fileName) {

		//Define number of columns (can change if more columns added)
		final int numCols = 10;
		//Open file
		String filename = "C:/Users/user/eclipse-workspace/challenge/" + fileName + ".csv";
		String outfilename = "C:/Users/user/eclipse-workspace/challenge/" + fileName + "-bad.csv";
		//Buffered Reader lets us read line by line
		Reader bufReader = null;
		//Used as buffer for the read line
		String[] readInLine = null;
		//Character for splitting each read line up
		String splitChar = ",";
		int numRecords = 0;
		int goodRecords = 0;
		int badRecords = 0;
		
		try {
			FileWriter outwriter = new FileWriter(outfilename);
			CSVWriter writer = new CSVWriter(outwriter);
			bufReader = new BufferedReader(new FileReader(filename));
			CSVReader Reader = new CSVReaderBuilder(bufReader).withSkipLines(1).build();
			
			//Read line by line
			while ((readInLine = Reader.readNext()) != null) {
				System.out.println("Record Number: " + numRecords);
				numRecords++;
				int recordErrors = 0;
				
				//String[] bufLine = readInLine.split(splitChar);
				for (int y = 0; y < readInLine.length-1;y++) {
					//System.out.println(bufLine[y]);
					if (readInLine[y].contentEquals("")) {
						recordErrors++;
					}
					
				}
					//Good Entry
				if (recordErrors ==0 && readInLine.length == 10) {
					goodRecords++;
					insert(fileName,readInLine);
					
					
				} //Bad Entry
				else {
					badRecords++;
				  
				        writer.writeNext(readInLine);
				        System.out.println("Bad Entry Added");
				      
					
					
					

					
				}

				
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e){
			e.printStackTrace();
		} catch (CsvValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 finally {
	            if (bufReader != null) {
	                try {
	                    bufReader.close();

	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
		 }
		//Print log file
	    try (PrintWriter writer = new PrintWriter(new File("C:/Users/user/eclipse-workspace/challenge/" + fileName +".log"))) {

	        StringBuilder sb = new StringBuilder();
	        sb.append(numRecords + " Number of Records Received\n");
	        sb.append(goodRecords + " Number of Records Succesful\n");
	        sb.append(badRecords + " Number of Records Unsuccesful\n");
			sb.append("\n");
	        writer.write(sb.toString());

	        System.out.println("Log File Printed");

	      } catch (FileNotFoundException e) {
	        System.out.println(e.getMessage());
	      }
		

	}

    public static void createNewDatabase(String fileName) {
    	 
        String url = "jdbc:sqlite:C:/Users/user/eclipse-workspace/challenge/src/db/" +fileName+".db";
        String sql = "CREATE TABLE IF NOT EXISTS tableBase (\n"
                + "    a String NOT NULL,\n"
                + "    b String NOT NULL,\n"
                + "    c String NOT NULL,\n"
                + "    d String NOT NULL,\n"
                + "    e String NOT NULL,\n"
                + "    f String NOT NULL,\n"
                + "    g String NOT NULL,\n"
                + "    h String NOT NULL,\n"
                + "    i String NOT NULL,\n"
                + "    j String NOT NULL\n"
                + ");";
 
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()){
                	stmt.execute(sql);
                	//System.out.println("Working");
            
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    
    
	public static void insert(String fileName, String[] inArray) {
		String url = "jdbc:sqlite:C:/Users/user/eclipse-workspace/challenge/src/db/" +fileName+".db";
		String sql = "INSERT INTO tableBase(a,b,c,d,e,f,g,h,i,j) VALUES(?,?,?,?,?,?,?,?,?,?)";
        if (inArray.length == 0) {
        	System.exit(1);
        }
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
            	
            	//PreparedStatement pstmt = conn.prepareStatement(sql);
                //DatabaseMetaData meta = conn.getMetaData();
            conn.beginRequest();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            int q = 0;
            int arraySize = inArray.length;
            //Dont call .length in loops slows it down
            //Add each entry in the line to a statement and then send it to database
            for (q = 0; q < arraySize; q++) {
            	pstmt.setString(q+1, inArray[q]);
            }
            pstmt.executeUpdate();

            }
            conn.endRequest();
            System.out.println("Good Entry Added");
	
        }catch (SQLException e1) {
            System.out.println(e1.getMessage());
        }
	}
	
	
	
	public static void main(String[] args) {
		String fileName ="";
		Scanner user = new Scanner(System.in);
		System.out.println("Please input the filename (Please do not include .csv)");
		fileName = user.nextLine().trim();
		createNewDatabase(fileName);
		ParseCSV(fileName);
		
		
	}
}
