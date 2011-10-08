package com.islamsharabash.cumtd;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
/**
 * MtdXmlHandler walks through and assigns bus objects to stop
 * @author islam
 *
 */
public class MtdXmlHandler extends DefaultHandler {
	Stop mStop;
	
	MtdXmlHandler(Stop _stop) {
		mStop = _stop;
	}
	
	private boolean in_bus = false;
	private boolean in_routename = false;
	private boolean in_ttdepart = false;
	private boolean in_err = false;
	public int err_code = -1;
	
	Bus cBus = null;
		
	@Override
	public void startElement(String namespaceURI, String localName,
							String qName, Attributes atts) throws SAXException {
		
		if (localName.equals("ErrResponse")) {
			this.in_err = true;
			err_code = Integer.parseInt(atts.getValue("code"));
			
		}else if (localName.equals("Bus")) {
			this.in_bus = true;
			cBus = new Bus();
			
        }else if (localName.equals("RouteName")) {
        		this.in_routename = true;
        		
        }else if (localName.equals("TimeTillDeparture")) {
    		this.in_ttdepart = true;
       }
	}
	
	
	/** Gets be called on closing tags like:
     * </tag> */
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
                    throws SAXException {
            if (localName.equals("ErrResponse")) {
                    this.in_err = false;
            }else if (localName.equals("Bus")) {
                    this.in_bus = false;
                    mStop.results.add(cBus);
// TODO might be probs here...                    
                    cBus = null;
                    
            }else if (localName.equals("RouteName")) {
                    this.in_routename = false;
                    
            }else if (localName.equals("TimeTillDeparture")) {
        		this.in_ttdepart = false;
        		
            }
    }
	
	
    /** Gets be called on the following structure:
     * <tag>characters</tag> */
	@Override
	public void characters(char ch[], int start, int length) {
		String textBetween = new String(ch, start, length);
		
		if (this.in_routename) {
    		cBus.setRouteName(textBetween);
    		
		} else if (this.in_ttdepart) {
    		cBus.setTimeTill(textBetween);
		}
		
	}

	
	

}
