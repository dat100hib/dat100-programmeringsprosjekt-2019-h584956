package no.hvl.dat100ptc.oppgave4;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

public class GPSComputer {
	
	private GPSPoint[] gpspoints;
	
	public GPSComputer(String filename) {

		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();

	}

	public GPSComputer(GPSPoint[] gpspoints) {
		this.gpspoints = gpspoints;
	}
	
	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}
	
	// beregn total distances (i meter)
	public double totalDistance() {

		double distance = 0;

		// TODO - START
		
		
		for (int i = 0; i < gpspoints.length-1; i++) {
			distance+= GPSUtils.distance(gpspoints[i],gpspoints[i+1]);
			
		}
		
		return distance;
		

		// TODO - SLUTT

	}

	// beregn totale høydemeter (i meter)
	public double totalElevation() {

		double elevation = 0;

		// TODO - START
		for (int i = 0; i < gpspoints.length-1; i++) {
			
			double elevation1 = gpspoints[i].getElevation();
			double elevation2 = gpspoints[i+1].getElevation();
			
			if(elevation2 > elevation1) {
				elevation += elevation2-elevation1;
			}
			
		}
		return elevation;

		// TODO - SLUTT

	}

	// beregn total tiden for hele turen (i sekunder)
	public int totalTime() {
		int time = 0;
		
		for (int i = 0; i < gpspoints.length-1; i++) {
			
			int time1 = gpspoints[i].getTime();
			int time2 = gpspoints[i+1].getTime();
			
			time += time2-time1;
		}
		
		return time;
	}
		
	// beregn gjennomsnitshastighets mellom hver av gps punktene

	public double[] speeds() {
		
		// TODO - START		// OPPGAVE - START
		double[]speeds = new double[gpspoints.length-1];
		
		for (int i = 0; i < speeds.length; i++) {
			speeds[i] = GPSUtils.speed(gpspoints[i],gpspoints[i+1]);
			
		}
		
		return speeds;

		// TODO - SLUTT

	}
	
	public double maxSpeed() {
		
		double maxspeed = 0;
		
		// TODO - START
		
		maxspeed = GPSUtils.findMax(speeds());
		return maxspeed;
		
		// TODO - SLUTT
		
	}

	public double averageSpeed() {

		double average = 0;
		
		// TODO - START
		int time = totalTime();
		double distance = totalDistance();
		
		average = distance/time;
		average *= 3.6;
		
		return average;
		
		
		// TODO - SLUTT
		
	}

	/*
	 * bicycling, <10 mph, leisure, to work or for pleasure 4.0 bicycling,
	 * general 8.0 bicycling, 10-11.9 mph, leisure, slow, light effort 6.0
	 * bicycling, 12-13.9 mph, leisure, moderate effort 8.0 bicycling, 14-15.9
	 * mph, racing or leisure, fast, vigorous effort 10.0 bicycling, 16-19 mph,
	 * racing/not drafting or >19 mph drafting, very fast, racing general 12.0
	 * bicycling, >20 mph, racing, not drafting 16.0
	 */

	// conversion factor m/s to miles per hour
	public static double MS = 2.236936;

	// beregn kcal gitt weight og tid der kjøres med en gitt hastighet
	public double kcal(double weight, int secs, double speed) {

		double kcal;

		// MET: Metabolic equivalent of task angir (kcal x kg-1 x h-1)
		double met = 0;		
		double speedmph = speed * MS;

		// TODO - START
		double h = (double)secs/3600;
		
		if (speedmph < 10) {
			met = 4;
		}
		else if(speedmph >= 10 && speedmph < 12) {
			met = 6;
		}
		else if(speedmph >= 12 && speedmph < 14) {
			met = 8;
		}
		else if(speedmph >= 14 && speedmph < 16) {
			met = 10;
		}
		else if(speedmph >= 16 && speedmph < 20) {
			met = 12;
		}
		else {
			met = 16;
		}
		
		kcal = met * weight * h;
		
		
		
		return kcal;
		// TODO - SLUTT
		
	}

	public double totalKcal(double weight) {

		double totalkcal = 0;

		// TODO - START
		int time = totalTime();
		double speed = averageSpeed();
		
		
		totalkcal = kcal(weight,time,speed);
		
		
		return totalkcal;

		// TODO - SLUTT
		
	}
	
	private static double WEIGHT = 80.0;
	
	public void displayStatistics() {

		System.out.println("==============================================");

		// TODO - START
		
		int secs = totalTime();
		String timeFormat = GPSUtils.formatTime(secs);
		
		double distance = totalDistance() / 1000;
		String km = GPSUtils.formatDouble(distance);
		
		double elevation = totalElevation();
		String elev = GPSUtils.formatDouble(elevation);
		
		double ms = maxSpeed();
		String maxSpeed = GPSUtils.formatDouble(ms);
		
		double as = averageSpeed();
		String averageSpeed = GPSUtils.formatDouble(as);
		
		double tk = totalKcal(WEIGHT);
		String totalKcal = GPSUtils.formatDouble(tk);
		
		
		System.out.println("Total time     :  " + timeFormat);
		System.out.println("Total distance : " + km + " km");
		System.out.println("Total elevation: " + elev + " m");
		System.out.println("Max speed      : " + maxSpeed + " km/t");
		System.out.println("Average speed  : " + totalKcal + " kcal");
		
		// TODO - SLUTT
		
	}

}
