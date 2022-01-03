package test;
import java.io.*;
import java.util.*;

public class TimeSeries{

	public Map<String, List<Float>> columns2 = new HashMap<>();

	public List<String> columnNames = new ArrayList<>();

	public TimeSeries(String csvFileName) {
			try {
				Scanner scanner = new Scanner(new File(csvFileName));
				String[] keys = scanner.nextLine().split(",");
				for (int i = 0; i < keys.length; i++) {
					List<Float>list=new ArrayList<>();
					columnNames.add(keys[i]);
					columns2.put(keys[i],list);
				}
				while (scanner.hasNextLine()) {
					String[] values = scanner.nextLine().split(",");
					for(int i=0;i< keys.length;i++){
						columns2.get(keys[i]).add(Float.parseFloat(values[i]));
					}
				}
			}catch (IOException e){e.printStackTrace();}
	}

    public float[] oneColumn(String columName){
		int size=columns2.get(columName).size();
		float[] changeCol=new float[size];
		for(int i=0;i<size;i++){
			changeCol[i]=columns2.get(columName).get(i);
		}
		return changeCol;
	}
}