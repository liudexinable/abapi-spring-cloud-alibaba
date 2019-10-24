package com.abapi.cloud.web.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


public class MasterSlaveRoutingDataSource extends AbstractRoutingDataSource{

	@Override
	protected Object determineCurrentLookupKey() {
		 return HandlerDataSource.getDataSource();
	}

}
