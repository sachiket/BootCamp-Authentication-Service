package com.cg.iter.authenticationservice.service;

import com.cg.iter.authenticationservice.entity.ProductDTO;

public interface MerchantService {

	

	/****************************************************************************************************************************************
	 * - Function Name : addProduct <br>
	 * - Description : Only merchant can add a new product and return status. <br>
	 * 
	 * @param ProductDTO product
	 * @return String
	 ****************************************************************************************************************************************/
	String addProduct(ProductDTO product);

	
	

	/****************************************************************************************************************************************
	 * - Function Name : deleteProduct <br>
	 * - Description : Only merchant can delete a product by product Id and return status. <br>
	 * 
	 * @param String productId
	 * @return String
	 ****************************************************************************************************************************************/
	String deleteProduct(String productId);
	
	

}
