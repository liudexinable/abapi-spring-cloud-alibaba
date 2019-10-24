package com.abapi.cloud.common.utils;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;

public class LocationUtils {

	/**
	 * 通过经纬度获取距离(单位：米)
	 *
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return 距离
	 */
	public static double getDistance(double lat1, double lng1, double lat2,
									 double lng2) {
		GlobalCoordinates source = new GlobalCoordinates(lat1, lng1);
		GlobalCoordinates target = new GlobalCoordinates(lat2, lng2);
		GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(Ellipsoid.Sphere,source, target);
		return geoCurve.getEllipsoidalDistance();
	}


	public static double getDistance(String gpsFrom,String gpsTo,ComLocation form) {
		GlobalCoordinates source = new GlobalCoordinates(parseLat(gpsFrom,form), parseLon(gpsFrom,form));
		GlobalCoordinates target = new GlobalCoordinates(parseLat(gpsTo,form), parseLon(gpsTo,form));
		GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(Ellipsoid.Sphere,source, target);
		return geoCurve.getEllipsoidalDistance();
	}

	public static Double parseLon(String locationString,ComLocation comLocation){
		if(comLocation.equals(ComLocation.LAT_LON))
			return parse(locationString,1);
		return parse(locationString,0);
	}

	public static Double parseLat(String locationString,ComLocation comLocation){
		if(comLocation.equals(ComLocation.LAT_LON))
			return parse(locationString,0);
		return parse(locationString,1);
	}

	//解析精度
	private static Double parse(String locationString,int index) {
		String location = locationString.split(",")[index];
		return Double.parseDouble(location);
	}

	public enum ComLocation{
		LON_LAT,LAT_LON
	}

}
