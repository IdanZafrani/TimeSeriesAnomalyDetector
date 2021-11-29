package test;
import java.lang.Math;

public class StatLib {

	public static float avg(float[] x){
		float sum=0;
		for(int i=0;i<x.length;i++) {
			sum+=x[i];
		}
		float avg = sum/(float)x.length;
		return avg;
	}

	// returns the variance of X and Y
	public static float var(float[] x){
		float N=0;
		for(int i=0;i<x.length;i++){
			N+=x[i]*x[i];
		}
		float Miu=avg(x);
		return (1/(float)x.length)*(N)-(Miu*Miu);
	}

	// returns the covariance of X and Y
	public static float cov(float[] x, float[] y){

		float sumExy[]=new float[x.length];
		for(int i=0;i<(x.length);i++){
			sumExy[i]=(x[i]*y[i]);
		}
		float avgExy=avg(sumExy);
		float avgX=avg(x);
		float avgY=avg(y);
		float covariance=avgExy-(avgX*avgY);
		return covariance;

	}

	// returns the Pearson correlation coefficient of X and Y
	public static float pearson(float[] x, float[] y){
		double SdX=Math.sqrt(var(x)); //Standard deviation
		double SdY=Math.sqrt(var(y)); //Standard deviation
		float pear=cov(x,y)/(float)(SdX*SdY);

		return pear;
	}

	// performs a linear regression and returns the line equation
	public static Line linear_reg(Point[] points){
		float x[]=new float[points.length];
		float y[]=new float[points.length];	
		for(int i=0;i<points.length;i++){
			x[i]=points[i].x;
			y[i]=points[i].y;
		}
		float A=cov(x,y)/var(x);
		float B=avg(y)-(A*avg(x));
		Line lineReg=new Line(A,B);
		return lineReg;
	}

	// returns the deviation between point p and the line equation of the points
	public static float dev(Point p,Point[] points){
		Line line=linear_reg(points);
		float deviation=dev(p,line);
		return deviation;
	}

	// returns the deviation between point p and the line
	public static float dev(Point p,Line l){
		float deviation;
		deviation=Math.abs((l.a*p.x+l.b)-p.y);
		return deviation;
	}


}
