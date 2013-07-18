/*
    Copyright 2013, Strategic Gains, Inc.

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

		http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
*/
package com.strategicgains.restexpress.serialization;

import com.strategicgains.restexpress.Format;
import com.strategicgains.restexpress.exception.NotAcceptableException;
import com.strategicgains.restexpress.response.ResponseProcessor;
import com.strategicgains.restexpress.response.ResponseWrapper;
import com.strategicgains.restexpress.serialization.json.GsonJsonProcessor;
import com.strategicgains.restexpress.serialization.xml.XstreamXmlProcessor;

/**
 * @author toddf
 * @since Jul 18, 2013
 */
public class GsonSerializationProvider
extends AbstractSerializationProvider
{
	@Override
    public ResponseProcessor newProcessor(String format, ResponseWrapper wrapper)
    {
		if (Format.JSON.equalsIgnoreCase(format))
		{
			return new ResponseProcessor(new GsonJsonProcessor(), wrapper);
		}
		
		if (Format.XML.equalsIgnoreCase(format))
		{
			return new ResponseProcessor(new XstreamXmlProcessor(), wrapper);
		}
		
		throw new NotAcceptableException(format);
    }
}
