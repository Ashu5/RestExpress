package com.kickstart;

import org.jboss.netty.handler.codec.http.HttpMethod;

import com.strategicgains.restexpress.Parameters;
import com.strategicgains.restexpress.route.RouteDeclaration;

/**
 * @author toddf
 * @since May 21, 2010
 */
public class Routes
extends RouteDeclaration
{
	private Configuration config;
	
	public Routes(Configuration config)
	{
		super();
		this.config = config;
	}
	
	@Override
	protected void defineRoutes()
	{
		// Maps /kickstart uri with optional format ('json' or 'xml'), accepting
		// POST HTTP method only.  Calls KickStartService.create(Request, Reply).
		uri("/kickstart.{format}", config.getKickStartController())
			.method(HttpMethod.POST);

		// Maps /kickstart uri with required orderId and optional format identifier
		// to the KickStartService.  Accepts only GET, PUT, DELETE HTTP methods.
		// Names this route to allow returning links from read resources in
		// KickStartService methods via call to LinkUtils.asLinks().
		uri("/kickstart/{orderId}.{format}", config.getKickStartController())
			.method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
			.name(Constants.KICKSTART_ORDER_URI)
			.parameter(Parameters.Cache.MAX_AGE, 3600);		// Cache for 3600 seconds (1 hour).
//			.flag(Flags.Cache.DONT_CACHE);					// Expressly deny cache-ability.
	}
}
