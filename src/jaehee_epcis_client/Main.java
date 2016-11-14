package jaehee_epcis_client;

import jaehee_epcis_client.api.EPCISCaptureClient;
import jaehee_epcis_client.api.EPCISQueryClient;

/**
* The EPCIS Client API is the program for Java programmed devices using EPCIS.
* First of all, please configure EPCISConfiguration.java file.
*
* @author  Jaehee Ha
* @version 2.0
* @since   2016.11.14 
*/

public class Main {
	public static void main(String[] args) {
		
		//EPCIS Query Client Example
		EPCISQueryClient queryClient = new EPCISQueryClient();
		//= new EPCISQueryClient(EPCISConfiguration.EPCIS_AC_Server_URL,EPCISConfiguration.EPCISname,EPCISConfiguration.Username,EPCISConfiguration.EPCIS_Clienttoken);
		
		String query = "eventType=TransformationEvent&GE_eventTime=2013-10-31T14:58:56.591Z";
		queryClient.setQuery(query);
		String queryResult = queryClient.doQuery();
		System.out.println(queryResult);

		
		
		//EPCIS Capture Client Example
		EPCISCaptureClient captureClient = new EPCISCaptureClient();
		//= new EPCISCaptureClient(EPCISConfiguration.EPCIS_AC_Server_URL,EPCISConfiguration.EPCISname,EPCISConfiguration.Username,EPCISConfiguration.EPCIS_Clienttoken);


		String epcisevent = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
							+ "<!DOCTYPE project>"
							+ "<epcis:EPCISDocument schemaVersion=\"1.2\" "
							+ "creationDate=\"2013-06-04T14:59:02.099+02:00\" xmlns:epcis=\"urn:epcglobal:epcis:xsd:1\" "
								+ "xmlns:example=\"http://ns.example.com/epcis\">"
								+ "<EPCISBody>"
									+ "<EventList>"
										+ "<extension>"
											+ "<TransformationEvent>"
												+ "<eventTime>2013-10-31T14:58:56.591Z</eventTime>"
												+ "<eventTimeZoneOffset>+02:00</eventTimeZoneOffset>"
												+ "<inputEPCList>"
													+ "<epc>urn:epc:id:sgtin:4012345.011122.25</epc>"
													+ "<epc>urn:epc:id:sgtin:4000001.065432.99886655</epc>"
												+ "</inputEPCList>"
												+ "<inputQuantityList>"
													+ "<quantityElement>"
														+ "<epcClass>urn:epc:class:lgtin:4012345.011111.4444</epcClass>"
														+ "<quantity>10</quantity>"
														+ "<uom>KGM</uom>"
													+ "</quantityElement>"
													+ "<quantityElement>"
														+ "<epcClass>urn:epc:class:lgtin:0614141.077777.987</epcClass>"
														+ "<quantity>30</quantity>"
														+ "<!-- As the uom field has been omitted, 30 instances (e.g., pieces) "
															+ "of GTIN '00614141777778' belonging to lot '987' have been used. -->"
													+ "</quantityElement>"
													+ "<quantityElement>"
														+ "<epcClass>urn:epc:idpat:sgtin:4012345.066666.*</epcClass>"
														+ "<quantity>220</quantity>"
														+ "<!-- As the uom field has been omitted and as an EPC pattern is indicated, "
															+ "220 instances (e.g., pieces) of GTIN '04012345666663' have been used. -->"
													+ "</quantityElement>"
												+ "</inputQuantityList>"
												+ "<outputEPCList>"
													+ "<epc>urn:epc:id:sgtin:4012345.077889.25</epc>"
													+ "<epc>urn:epc:id:sgtin:4012345.077889.26</epc>"
													+ "<epc>urn:epc:id:sgtin:4012345.077889.27</epc>"
													+ "<epc>urn:epc:id:sgtin:4012345.077889.28</epc>"
												+ "</outputEPCList>"
												+ "<bizStep>urn:epcglobal:cbv:bizstep:transforming</bizStep>"
												+ "<disposition>urn:epcglobal:cbv:disp:in_progress</disposition>"
												+ "<readPoint>"
													+ "<id>urn:epc:id:sgln:4012345.00001.0</id>"
												+ "</readPoint>"
												+ "<ilmd>"
													+ "<!-- Section, in which the instance/ lot master data referring to the "
														+ "objects indicated in the outputEPCList are defined. -->"
													+ "<example:bestBeforeDate>2014-12-10</example:bestBeforeDate>"
													+ "<!-- The namespace 'example' is just a placeholder for the domain under "
														+ "which the ILMD attributes are defined (for instance, by a GS1 working group). "
														+ "Meaning: the best before date of the above GTIN + lot is the 10th December "
														+ "2014. -->"
													+ "<example:batch>XYZ</example:batch>"
												+ "</ilmd>"
												+ "<example:myField>Example of a vendor/user extension</example:myField>"
											+ "</TransformationEvent>"
										+ "</extension>"
									+ "</EventList>"
								+ "</EPCISBody>"
							+ "</epcis:EPCISDocument>";
		captureClient.setEvent(epcisevent);
		String captureResult = captureClient.doCapture();
		System.out.println(captureResult); 
		
	}
}
