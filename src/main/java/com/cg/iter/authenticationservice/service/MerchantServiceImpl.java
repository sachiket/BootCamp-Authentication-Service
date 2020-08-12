package com.cg.iter.authenticationservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import com.cg.iter.authenticationservice.entity.ProductDTO;

@Service
public class MerchantServiceImpl implements MerchantService{

	@Autowired
	RestTemplate restTemplate;
	private String productURL = "http://product-ms/product";


	
	
	
	/****************************************************************************************************************************************
	 * - Function Name : addProduct <br>
	 * - Description : Only merchant can add a new product and return status. Here we are calling productms to add new product. <br>
	 * 
	 * @param ProductDTO product
	 * @return String
	 ****************************************************************************************************************************************/
	@HystrixCommand(fallbackMethod = "getFallbackAddProduct",
			commandProperties = {
					@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
			})	
	@Override
	public String addProduct(ProductDTO product) {
		return restTemplate.postForObject(productURL+"/addProduct",product, String.class);
	}

	
	
	
	
	
	/****************************************************************************************************************************************
	 * - Function Name : deleteProduct <br>
	 * - Description : Only merchant can delete a product by product Id and return status ,
	 * 					here we are calling productms to delete product with productId. <br>
	 * 
	 * @param String productId
	 * @return String
	 ****************************************************************************************************************************************/
	
	@HystrixCommand(fallbackMethod = "getFallbackDeleteProduct",
			commandProperties = {
					@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
			})
	@Override
	public String deleteProduct(String productId) {
		
		MultiValueMap<String, String> parametersMap = new LinkedMultiValueMap<String, String>();
		parametersMap.add("productId",productId);
		
		return restTemplate.postForObject(productURL+"/deleteProduct",parametersMap,String.class);
	}
	
	
	
	
	//Fallback method for add product
	String getFallbackAddProduct(ProductDTO product) {
		return "Add product service is not responding...";
	}
	
	
	//Fallback method for delete product
	String getFallbackDeleteProduct(String productId) {
		return "Delete product service is not responding...";
	}

}
