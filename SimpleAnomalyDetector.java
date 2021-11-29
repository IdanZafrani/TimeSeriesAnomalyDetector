package test;
import java.util.*;

public class SimpleAnomalyDetector implements TimeSeriesAnomalyDetector {

    List<CorrelatedFeatures>whoIscorrelate=new ArrayList<>();
	List<AnomalyReport>myReport=new ArrayList<>();
	float correlationTreshHold= 0.9f;
	@Override
	public void learnNormal(TimeSeries ts) {
		float p;
		for(int i=0;i<ts.columns.keySet().size();i++){
			int c=-1;
			float m=0f;
			for(int j=i+1;j<ts.columns.keySet().size();j++){
				if((p=Math.abs(StatLib.pearson(ts.oneColumn(ts.columnNames.get(i)),ts.oneColumn(ts.columnNames.get(j)))))>m){
					m=p;
					c=j;
				}
			}
			if(c!=-1&&m>correlationTreshHold){
				int size=ts.oneColumn(ts.columnNames.get(i)).length;
				Point[] myNewPoints=new Point[size];
				for(int k=0;k<size;k++){
					myNewPoints[k]=new Point(ts.oneColumn(ts.columnNames.get(i))[k],ts.oneColumn(ts.columnNames.get(c))[k]);
				}
				float maxDev=0;
				float counter=0;
				Line line=StatLib.linear_reg(myNewPoints);
				for(int k=0;k<size;k++){
					counter=StatLib.dev(myNewPoints[k],line);
					if(counter>maxDev)
						maxDev=counter;
				}
				String[] fe1=(String[]) ts.columns.keySet().toArray(new String[i]);
				CorrelatedFeatures correlated=new CorrelatedFeatures(fe1[i],fe1[c],m,line,maxDev*(1.1f));
				whoIscorrelate.add(correlated);
			}
		}
	}


	@Override
	public List<AnomalyReport> detect(TimeSeries ts) {
		int count=1;
		for(int i=0;i<whoIscorrelate.size();i++) {
			float[] feat1=ts.oneColumn(whoIscorrelate.get(i).feature1);
			float[] feat2=ts.oneColumn(whoIscorrelate.get(i).feature2);
			int size = feat1.length;
			Point[] myNewPoints = new Point[size];
			for (int k = 0; k < size; k++) {
				myNewPoints[k] = new Point(feat1[k], feat2[k]);
			}
			float maxDev = 0;
			float counter = 0;
			for (int k = 0; k < size; k++) {
				counter = StatLib.dev(myNewPoints[k], whoIscorrelate.get(i).lin_reg);
				if (counter > whoIscorrelate.get(i).threshold) {
					String newRep = whoIscorrelate.get(i).feature1 + "-" + whoIscorrelate.get(i).feature2;
					AnomalyReport newReport = new AnomalyReport(newRep, count);
					myReport.add(newReport);
					count++;
				}
				count++;
			}
		}
		return myReport;
	}
	
	public List<CorrelatedFeatures> getNormalModel(){
		return this.whoIscorrelate;
	}
}
