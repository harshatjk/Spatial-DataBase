
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;   
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
 
    public class Populate {
        public static void main(String args[]) {
        	Populate populate = new Populate();
           populate.run(args);
       }
        
   
       private void run(String args1[]) {
           Connection con = null;
           try {
               con = openConnection();
               publishDataForBuilding(con, args1[0]);
               publishDataForHydrant(con, args1[1]);
               publishDataForFireBuilding(con, args1[2]);
           } catch (SQLException e) {
               System.err.println("Errors occurs when communicating with the database server: " + e.getMessage());
           } catch (ClassNotFoundException e) {
               System.err.println("Cannot find the database driver");
           } finally {
               // Never forget to close database connection
               closeConnection(con);
           }
       }
       
       public String removeElements(String[] line2) {
   		String newLine = "";
   		List<String> line3 = new ArrayList<String>(Arrays.asList(line2));
   		line3.removeAll(Arrays.asList(line2[0]));
   		line3.removeAll(Arrays.asList(line2[1]));
   		line3.removeAll(Arrays.asList(line2[2]));
   		for(String s : line3) {
   			newLine = newLine + s + ",";
   		}
   		newLine += (String)line3.get(0);
   		newLine += ",";
   		newLine += (String)line3.get(1);
   		
   		newLine = newLine.substring(0, (newLine.length()));
   		return newLine;
   	}
   
       public String removeElements1(String[] line4) {
      		String newLine1 = "";
      		List<String> line5 = new ArrayList<String>(Arrays.asList(line4));
      		line5.removeAll(Arrays.asList(line4[0]));
      		for(String s : line5) {
      			newLine1 = newLine1 + s + ",";
      		}
      		
      		newLine1 = newLine1.substring(0, (newLine1.length()-1));
      		return newLine1;
      	}
       
       /**
        * This method will first clean up the table and then
        * populate it with new data.
        * @param con
        * @throws SQLException
        */
       private void publishDataForBuilding(Connection con, String args2) throws SQLException {
    	   String insertCommand = "";
           List<String> insertStatement1 = new ArrayList<String>();
    	   try {
   			File file = new File(args2);
   			FileReader fileReader = new FileReader(file);
   			BufferedReader bufferedReader = new BufferedReader(fileReader);
   			StringBuffer stringBuffer = new StringBuffer();
   			String line;
   			
   			while ((line = bufferedReader.readLine()) != null) {
   				stringBuffer.append(line);
   				line = stringBuffer.toString();
   				stringBuffer.replace(0, stringBuffer.length(), "");
   				String[] line1 = line.split(", ");
   				String line5 = removeElements(line1);
   				insertCommand = "INSERT INTO BUILDING VALUES('"+line1[0]+"','"+line1[1]+"',"+ line1[2]+",SDO_GEOMETRY(2003,NULL,NULL,SDO_ELEM_INFO_ARRAY(1,2003,1),SDO_ORDINATE_ARRAY("+line5+")))";
   		           System.out.println(insertCommand);
                           insertStatement1.add(insertCommand);
   			}
   			fileReader.close();
   		} catch (IOException e) {
   			e.printStackTrace();
   		}
   	
           Statement stmt1 = con.createStatement();
           System.out.println("Deleting previous entries from table BUILDING");
           stmt1.executeUpdate("delete from BUILDING");
           System.out.println("Inserting Data ...");
          for(int k = 0; k < insertStatement1.size(); k++){
           stmt1.executeUpdate(insertStatement1.get(k));
          }
           System.out.println("InsertDone");
           stmt1.close();
          System.out.println();
       }
   
       private void publishDataForHydrant(Connection con, String args3) throws SQLException {
    	   String insertCommand1 = "";
           List<String> insertStatement = new ArrayList<String>();
    	   try {
   			File file = new File(args3);
   			FileReader fileReader = new FileReader(file);
   			BufferedReader bufferedReader = new BufferedReader(fileReader);
   			StringBuffer stringBuffer = new StringBuffer();
   			String line1;
   			
   			while ((line1 = bufferedReader.readLine()) != null) {
   				stringBuffer.append(line1);
   				line1 = stringBuffer.toString();
   				stringBuffer.replace(0, stringBuffer.length(), "");
   				String[] line2 = line1.split(",");
   				String line9 = removeElements1(line2);
   				insertCommand1 = "INSERT INTO HYDRANT VALUES ('" + line2[0] + "'," + "SDO_GEOMETRY(2001,NULL,SDO_POINT_TYPE(" + line9 + ",NULL),NULL,NULL))";
   		           System.out.println(insertCommand1);
                           insertStatement.add(insertCommand1);
   			}	
   			fileReader.close();
   		} catch (IOException e) {
   			e.printStackTrace();
   		}
   	
           Statement stmt = con.createStatement();
           System.out.println("Deleting previous entries from table BUILDING");
           stmt.executeUpdate("delete from HYDRANT");
           System.out.println("Inserting Data ...");
           for(int q = 0; q < insertStatement.size(); q++){
           stmt.executeUpdate(insertStatement.get(q));
           }
           System.out.println("Insert Done");
           stmt.close();
          System.out.println();
       }
       
       private void publishDataForFireBuilding(Connection con, String args4) throws SQLException {
    	   String insertCommand1 = "";
    	   try {
   			File file = new File(args4);
   			FileReader fileReader = new FileReader(file);
   			BufferedReader bufferedReader = new BufferedReader(fileReader);
   			StringBuffer stringBuffer = new StringBuffer();
   			String line1;
   			
   			while ((line1 = bufferedReader.readLine()) != null) {
   				stringBuffer.append(line1);
   				line1 = stringBuffer.toString();
   				stringBuffer.replace(0, stringBuffer.length(), "");
   				insertCommand1 = "INSERT INTO FIREBUILDING VALUES('"+ line1+"')";
                                
   		           System.out.println(insertCommand1);
   			}
   			fileReader.close();
   		} catch (IOException e) {
   			e.printStackTrace();
   		}
   	
           Statement stmt = con.createStatement();
   
           System.out.println("Deleting previous entries from table BUILDING");
           stmt.executeUpdate("delete from FIREBUILDING");
   
           System.out.println("Inserting Data ...");
           stmt.executeUpdate("INSERT INTO FIREBUILDING VALUES('OHE')");
           stmt.executeUpdate("INSERT INTO FIREBUILDING VALUES('ACP')");
           stmt.executeUpdate("INSERT INTO FIREBUILDING VALUES('TSC')");
           System.out.println("Insert Done ...");
           stmt.close();
       }
          
       /**
        *
        * @return a database connection
        * @throws java.sql.SQLException when there is an error when trying to connect database
        * @throws ClassNotFoundException when the database driver is not found.
        */
       public Connection openConnection() throws SQLException, ClassNotFoundException
        {
           // Load the Oracle database driver
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
  
           String host = "dagobah.engr.scu.edu";
           String port = "1521";
           String dbName = "db11g";
           String userName = "ssardesa";
           String password = "oracle";
   
          // Construct the JDBC URL
          String dbURL = "jdbc:oracle:thin:@" + host + ":" + port + ":" + dbName;
          return DriverManager.getConnection(dbURL, userName, password);
                         
      }
  
      /**
       * Close the database connection
       * @param con
       */
      private void closeConnection(Connection con) {
          try {
              con.close();
          } catch (SQLException e) {
              System.err.println("Cannot close connection: " + e.getMessage());
          }
      }
  }