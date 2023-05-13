/**
 * 
 */
package com.csr.utils;

/**
 * @author akaushi3
 *
 */
public class Queries {

	public static String getOrderDetails(String userID) {

		return "select Orders_Id , Round(TotalProduct+TotalShipping+TotalAdjustment,5) As \"Order Total\", "
				+ "CASE when Status= 'M' then 'Submitted'" + "when Status='X' then 'Cancelled'"
				+ "When Status='S' then 'Shipped'" + "When Status='P' then 'Pending'"
				+ "When Status='R' then 'Released'"
				+ "else 'No Status' end \"Order Status\" from orders where member_id=" + userID
				+ " and Status <> 'J' order by Orders_id Desc";
	}

	public static String getNumberOfOrders(String userID) {

		return "select Count(*) from orders where member_id=" + userID + " and TransferStatus=2";
	}

	public static String getOrderAttributes(String orderID) {

		return "Select b.Orders_id, " + "case when b.status='M' then 'Submitted' "
				+ "When b.status='X' then 'Cancelled' " + "When b.Status='S' then 'Shipped' "
				+ "When b.status='P' then 'Pending' " + "Else 'Released' END as Status, "
				+ "varchar_format((select timeplaced from orders where Orders_id='" + orderID + "'), 'Month dd, yyyy') "
				+ "OrderDate,Concat(Concat(a.Address1,' '), Concat(a.City, Concat(' ',Concat(a.STATE, Concat(' ',a.ZIPCODE))))) ShipTo, "
				+ "case when x.value='0' then 'Rover' Else 'OSP' END as FulfillmentSystem, "
				+ " a.phone1, a.email1 from address a "
				+ "Inner Join OrderItems b on a.address_id=b.address_id "
				+ " Inner Join xorderattr x on x.orders_id=b.orders_id and name='DMEnableOSP' "
				+ " where b.orders_id='"+orderID + "' FETCH First 1 Row Only";
	}

	public static String getAddressId(String address, String city, String postCode) {

		return "Select Address_ID from Address where Address1 = '" + address + "' and City = '" + city
				+ "' and ZipCode = '" + postCode + "'";
	}

	public static String getOrderDate(String addressId) {

		return "Select VARCHAR_FORMAT(LASTCREATE , 'Month dd, yyyy') DateField from OrderItems where Address_ID = '"
				+ addressId + "' and field1=2";
	}

	/**
	 * 
	 * @param addressID
	 * @return
	 */
	public static String getOrdersFromAddressID(String addressID) {

		return "Select b.Orders_id ,VARCHAR_FORMAT(b.LASTCREATE , 'Month dd, yyyy') DateField ,Concat(Concat(a.Address1,' '), Concat(a.City, Concat(' ',Concat(a.STATE, Concat(' ',a.ZIPCODE))))), a.phone1, a.email1 from address a "
				+ " Inner Join OrderItems b" + " on a.address_id=b.address_id" + " where b.field1=2 and b.address_id='"
				+ addressID + "'";
	}

	/**
	 * 
	 * @param ordersID
	 * @return
	 */
	public static String getOrderSummary(String ordersID) {

		return "select Concat(INITCAP(firstname),concat(' ', INITCAP(lastname))) as OrderedBy ,varchar_format(orderitems.LastCreate , 'Mon dd, yyyy hh:mi AM') as OrderedAt, "
				+ " varchar_format(orderitems.LastUpdate , 'Mon dd, yyyy hh:mi AM') as UpdatedAt,"
				+ " CASE when orderitems.Status= 'M' then 'Submitted' when orderitems.Status = 'X' then 'Cancelled' When orderitems.Status = 'S' then 'Shipped' When OrderItems.Status ='P' then 'Pending'"
				+ " When orderitems.Status = 'R' then 'Released' else 'No Status' end as StatusOfOrder"
				+ " from orderitems" + " Inner Join address" + " on orderitems.address_id = address.address_id"
				+ " where orderitems.Orders_id=" + ordersID + " Order By OrderedAt Desc Fetch First 1 row only";
	}

	/**
	 * 
	 * @param OrderID
	 * @return
	 */
	public static String getOrderCharges(String OrderID) {

		return "select concat('$',concat(' ',TotalProduct)) as Marchandise,concat('$',concat(' ',TOTALADJUSTMENT)) as TotalDiscount, "
				+ "concat('$',concat(' ',TotalTax)) as Tax," + "concat('$',concat(' ',TotalShipping)) as Shipping , "
				+ "concat('$',concat(' ',TotalTaxShipping)) as ShippingTax " + "from orders where orders_id=" + OrderID
				+ "";
	}

	public static String getOrdersId_coupons(String member_id) {

		return "select Orders_id from PX_COUPON where users_id=" + member_id + " "
				+ " Order By  Effective DESC FETCH FIRST 1 ROWS only";
	}

	/**
	 * Get Active Subscriber Details
	 * 
	 * @param subscriptionID
	 * @return
	 */
	public static String getActiveSubscriberDetails(String subscriptionID) {

		return "select subscription_id, description, varchar_format(startdate, 'yyyy-mm-dd') as ActivationDate,"
				+ "CAST(TotalCost as INT) as Charge, varchar_format(nextpaymentdate, 'yyyy-mm-dd') as nextbillingdate,"
				+ "cancelDate from subscription where Subscription_id=" + subscriptionID + "";
	}

	public static String getCancelledSubscriberDetails(String subscriptionID) {

		return "select subscription_id, description, varchar_format(startdate, 'yyyy-mm-dd') as ActivationDate, CAST(TotalCost as INT) as Charge, "
				+ "varchar_format(nextpaymentdate, 'yyyy-mm-dd') as nextbillingdate,cancelDate , comments from subscription Inner Join ORComment  "
				+ "on subscription.orders_id = orcomment.orders_id where Subscription_id=" + subscriptionID + "";
	}

	/**
	 * 
	 * @return
	 */
	public static String getRandomSubmittedOrder() {

		return "select orders_id from orderitems where Status='M' and field1=2 FETCH FIRST 1 ROWS ONLY";
	}

	/**
	 * 
	 * @return
	 */
	public static String getEmailId_Order(String status) {

		return "select field1 from users where users_id In (select member_id from orderItems where Status='" + status
				+ "' and field1=2 Order By member_id desc Fetch First 1 Row Only)";
	}

	public static String countOfOrders(String email) {

		return "select count(Orders_id) from orders where member_id IN (select users_id from users where field1='"
				+ email + "') and Status <> 'J'";
	}

	/**
	 * Get enable customer email id
	 * 
	 * @return
	 */
	public static String getEnableCustomerEmail() {

		return	" select field1 from users Inner Join userreg on users.users_id = userreg.users_id where userreg.status = 1 and users.profiletype='C'and userreg.LogonPassword is not null Fetch First 1 row only ";
		//return "select field1 from users where users_id = (select users_id from userreg where status = 1 and LogonPassword is not null Fetch First 1 row only)";
	}

	/**
	 * Get disable customer email id
	 * 
	 * @return
	 */
	public static String getDisableCustomerEmail() {

		return	"select field1 from users Inner Join userreg on users.users_id = userreg.users_id where userreg.status = 0 and users.profiletype='C' and userreg.LogonPassword is not null Fetch First 1 row only";
		//	return "select field1 from users where users_id = (select users_id from userreg where status = 0 and LogonPassword is not null Fetch First 1 row only)";
	}

	/**
	 * Secondary Attributes
	 * 
	 * @param email
	 * @param databaseFieldName
	 * @return
	 */
	public static String getSecondaryDetails(String email, String databaseFieldName) {

		return "select " + databaseFieldName + " from address where email1='" + email
				+ "' and Status = 'P' and AddressType='B'";
	}

	/**
	 * Get Order Id By Service Type
	 * 
	 * @param serviceType
	 * @return
	 */
	public static String getOrderByServiceType(String serviceType, String orderType) {

		
		return "SELECT ORDERS_ID FROM (SELECT DISTINCT ORD.ORDERS_ID,ORD.TIMEPLACED,"
				+ "COALESCE((SELECT XORDERATTR.VALUE FROM XORDERATTR WHERE "
				+ "XORDERATTR.ORDERS_ID=ORD.ORDERS_ID AND XORDERATTR.NAME='DMEnableOSP'),'0' ) "
				+ "AS OSPENABLED,COALESCE((SELECT XORDERATTR.VALUE FROM XORDERATTR WHERE "
				+ "XORDERATTR.ORDERS_ID=ORD.ORDERS_ID AND XORDERATTR.NAME='OrderSubServiceType'),'NA') "
				+ "AS SERVICETYPE FROM ORDERS ORD JOIN XORDERATTR XORD ON "
				+ "ORD.ORDERS_ID = XORD.ORDERS_ID WHERE ORD.STATUS IN ('" + orderType + "') AND "
				+ "ORD.STOREENT_ID='10401' AND ORD.TIMEPLACED > CURRENT TIMESTAMP - 30 DAYS with ur) "
				+ "WHERE OSPENABLED = 0 AND SERVICETYPE = '" + serviceType +"' "
				+ "FETCH FIRST 1 ROWS ONLY WITH UR";
	}

	/**
	 * Get Order Id By Service Type
	 * 
	 * @param serviceType
	 * @return
	 */
	public static String getOrder_ServiceType(String serviceType, String orderType) {

		return "select orders.Orders_id from orders " + "inner Join Xorderattr "
				+ "On Orders.Orders_id = Xorderattr.Orders_id "
				+ "where Xorderattr.Name = 'OrderSubServiceType' and Xorderattr.value='" + serviceType
				+ "' and orders.Status='" + orderType + "' order By orders.Orders_id Desc Fetch First 10 Row Only";
	}

	public static String getOrders_Redelivery(String serviceType, String orderType) {

		return "select orderitems.orders_id from orderitems inner join address " + " on orderitems.address_id = address.address_id "
				+ " inner join xorderattr " + " on orderitems.orders_id=Xorderattr.Orders_id "
				+ " where address.zipcode='0820' and orderitems.status='" + orderType
				+ "'and Xorderattr.Name = 'OrderSubServiceType' and Xorderattr.value='" + serviceType + "' order By orderitems.Orders_id Desc Fetch First 10 Row Only";
	}

	/**
	 * Get Customer For Coupons
	 * 
	 * @return
	 */
	public static String getCustomerForCoupons() {

		return "select field1 from users where users_id = (select member_id from orders where Status = 'S' Order By rand() Fetch first 1 row only)";
	}

	public static String getOrdersId_shipped(String email) {

		return "select orders_id from orders where Status='S' and member_id = (select users_id from users where field1='"
				+ email + "') fetch First 1 row only";
	}

	/**
	 * Get Orders Id
	 * 
	 * @param status
	 * @return
	 */
	public static String getOrdersId(String status) {

		return "select Orders_id from orders where Status = '" + status
				+ "' Order By Orders_id asc Fetch First 1 Row Only";
	}

	/**
	 * Get Rosie Flag Details
	 * 
	 * @return
	 */
	public static String getRosieFlagOptions() {

		return "select Code from xreasoncode where Type='RRC' Order By Code";
	}

	/**
	 * Get Comments For Coupons
	 * 
	 * @param orderId
	 * @return
	 */
	public static String getCouponComments(String customerId) {

		return "select createtimestamp, commentdetail from ccomment where customerId = '" + customerId
				+ "' Order By CreateTimeStamp DESC Fetch First 1 Row Only";
	}

	/**
	 * Get Email Id Of Customer
	 * 
	 * @return
	 */
	public static String getEmailId() {

		return "select field1 from users where users_id=(select member_id from orders where Status in ('S') order by rand() Fetch First 1 row only)";
	}

	public static String getEmailIdForB2B() {	
		return "select field1 from users where users.profiletype='B'";	
	}	
	public static String getDistinguishName(String emailId)	
	{	
		return "SELECT DN FROM USERS WHERE FIELD1='" + emailId + "'";	
	}	
	public static String getOrgNameFromDB(String orgDN)	
	{	
		return " SELECT ORGENTITYNAME FROM ORGENTITY WHERE DN='" + orgDN + "'";	
	}	
	public static String getEmailIdForB2BFromDB()	
	{	
		return "SELECT FIELD1 FROM USERS Inner Join userreg on users.users_id = userreg.users_id where userreg.status = 1 and users.profiletype='B'AND USERS.DN LIKE '%o=coles b2b buyer organization,o=cgl' AND length(SUBSTR(users.DN,17)) >38 AND USERS.FIELD2 IS NULL order by users.registrationupdate desc Fetch First 1 row only";	
	}
	
	
	/**
	 * Get Delivery Type Of the Order
	 * 
	 * @param orderId
	 * @return
	 */
	public static String getOrderDeliveryType(String orderId) {

		return "select value from xorderattr where orders_id='" + orderId + "' and name='DMServiceType'";
	}

	/**
	 * Get RMA Comments from OrderId
	 * 
	 * @param orderId
	 * @return
	 */
	public static String getRmaComments(String orderId) {

		return "select RMA_ID, TotalCredit, Status from rma where member_id=(select member_id from orders where orders_id='"
				+ orderId + "') order by RMADATE DESC Fetch First 1 row only";
	}

	/**
	 * Get RMA Details
	 * 
	 * @param rmaid
	 * @return
	 */
	public static String getRMATableData(String rmaid) {

		return "select TotalCredit, case when Status='APP' then 'Approved' when status='CAN' then 'Cancelled' else 'Pending' end from rma where rma_id='"
				+ rmaid + "'";
	}

	/**
	 * RMAItems Data
	 * 
	 * @param rmaid
	 * @return
	 */
	public static String getRMAItemsData(String rmaid) {

		return "select TotalCredit, Quantity,  Adjustment, case When Status='APP' then 'Approved' When Status='CAN' then 'Cancelled' When Status='EDT' then 'Pending' When Status='MAN' then 'Manually Approved' Else 'Not available' end from rmaitem where rma_id='"
				+ rmaid + "'";
	}

	/**
	 * Get Xrma data
	 * 
	 * @param rmaID
	 * @return
	 */
	public static String getXrmaData(String rmaID) {

		return "select method, order_id_redelivery from xrma where rma_id='" + rmaID + "'";
	}

	/**
	 * 
	 * @param orderid
	 * @param name
	 * @return
	 */
	public static String getxOrderAttrData(String orderid, String name) {

		return "select value from xorderattr where orders_id='" + orderid + "' and Name='" + name + "'";
	}

	/**
	 * Get Time From Xorderattr
	 * 
	 * @param orderID
	 * @param fieldName
	 * @return
	 */
	public static String getTimeFromXorderattr(String orderID, String fieldName) {

		return "select VARCHAR_FORMAT(value, 'HH24:MM') from xorderattr where orders_id='" + orderID + "' and Name='"
				+ fieldName + "'";
	}

	/**
	 * 
	 * @param orderType
	 * @return
	 */
	public static String getCustomer_IssueCoupon(String orderType) {

		return "select (select field1 from users where users_id = orders.member_id) from orders inner Join Xorderattr "
				+ "On Orders.Orders_id = Xorderattr.Orders_id "
				+ "where Xorderattr.Name = 'OrderSubServiceType' and Xorderattr.value='" + orderType
				+ "' and orders.Status='S' order By orders.orders_id desc Fetch First 1 Row Only";
	}

	/**
	 * 
	 * @param status
	 * @return
	 */
	public static String getSuscriptionCustomer(String status) {

		return "select field1 from users where users_id = (Select member_id from subscription where status = '" + status
				+ "' and Description Like '%Coles Plus%' Order By member_id Fetch First 1 Row Only)";
	}
}
