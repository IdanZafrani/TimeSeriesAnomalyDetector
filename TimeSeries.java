package test;
import java.io.*;
import java.util.*;


public class TimeSeries {
	public Map<String, List<String>> columns = new HashMap<>();
	//column names
	public List<String> columnNames = new ArrayList<>();
	public TimeSeries(String csvFileName) {
		try {
			String CurrentLine;
			BufferedReader br = new BufferedReader(new FileReader(csvFileName));
			boolean flag = true;
			while ((CurrentLine = br.readLine()) != null) {
				String[] fields = CurrentLine.split(",");
				for (int i = 0; i < fields.length; i++) {
					if(flag==true) {
						//array list as null, since we don't have values yet
						columns.put(fields[i], null);
						//also seperately store the column names to be used to as key to get the list
						columnNames.add(fields[i]);
						//reached end of the header line, set header to false
						if(i == (fields.length-1))
							flag = false;
					} else {
						//retrieve the list using the column names
						List<String> tempList = columns.get(columnNames.get(i));

						if(tempList != null) {
							//if not null then already element there
							tempList.add(fields[i]);
						} else {
							//if null then initialize the list
							tempList = new ArrayList<String>();
							tempList.add(fields[i]);
						}
						//add modified list back to the map
						columns.put(columnNames.get(i), tempList);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    public float[] oneColumn(String columName){
		String[] bigFloatCol=new String[columns.get(columName).size()];
		for(int i=0;i<columns.get(columName).size();i++){
			bigFloatCol[i]=columns.get(columName).get(i);
		}
		float[] floatCol= new float[bigFloatCol.length];
		for(int j=0;j<bigFloatCol.length;j++){
			floatCol[j]=Float.parseFloat(bigFloatCol[j]);
		}

		return floatCol;
	}







}